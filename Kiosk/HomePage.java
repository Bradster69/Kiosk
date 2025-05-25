import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class HomePage extends JPanel {
    private JPanel cardArea;
    private Map<Integer, JPanel> cards = new HashMap<>();
    private Map<String, JButton> catButtons = new HashMap<>();
    private static final String[] CATEGORIES = {
        "BEST SELLERS", "SNACKS", "WAFFOWLS", "BEVERAGE", "RICE MEALS", "MILK TEA"
    };
    private static final Map<String, String> CATEGORY_MAP = new HashMap<>();
    static {
        CATEGORY_MAP.put("BEST SELLERS", "BESTSELLERS");
        CATEGORY_MAP.put("WAFFOWLS", "WAFFOWLS");
        CATEGORY_MAP.put("SNACKS", "SNACKS");
        CATEGORY_MAP.put("BEVERAGE", "BEVERAGE");
        CATEGORY_MAP.put("RICE MEALS", "RICEMEALS");
        CATEGORY_MAP.put("MILK TEA", "MILKTEA");
    }
    private JPanel cartList;
    private JLabel totalText;
    private double total;
    private CartManager cart;
    private JPanel leftPanel;
    private RoundedPanel catBox;
    private JPanel cartPanel;
    private boolean isCartVisible = false;
    private ImageIcon bgImage;

   public HomePage() {
    setLayout(new BorderLayout());
    setOpaque(false);

    // Load background image
    bgImage = new ImageIcon(getClass().getResource("assets/whiteLeaf.png"));

    // Create cart
    cart = new CartManager();

    // Add header (TopBar from v1.2)
    TopBar topBar = new TopBar();
    add(topBar, BorderLayout.NORTH);

    // Left sidebar with categories and back button
    leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    leftPanel.setOpaque(false);
    leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    leftPanel.setPreferredSize(new Dimension(250, getHeight()));

    // Add back button with same styling as category buttons
    JButton backButton = new JButton("← Back");
    backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    backButton.setPreferredSize(new Dimension(200, 10));
    backButton.setMinimumSize(new Dimension(200, 100));
    backButton.setMaximumSize(new Dimension(200, 100));
    backButton.setBackground(new Color(200, 200, 200));
    backButton.setFont(new Font("SansSerif", Font.BOLD, 12));
    backButton.setFocusPainted(false);
    backButton.setOpaque(true);

    // Add ActionListener to back button to show all products
    backButton.addActionListener(e -> {
        for (JButton b : catButtons.values()) {
            b.setForeground(Color.BLACK);
        }
        backButton.setForeground(new Color(0, 120, 0));
        showCards(null);
    });

    catBox = new RoundedPanel(20);
    catBox.setLayout(new BoxLayout(catBox, BoxLayout.Y_AXIS));
    catBox.setBackground(new Color(245, 245, 245, 200));
    catBox.setBorder(new EmptyBorder(20, 20, 20, 20));

    // Add back button to catBox
    catBox.add(backButton);
    catBox.add(Box.createVerticalStrut(20));

    for (String cat : CATEGORIES) {
        JButton btn = makeCatButton(cat, getCatColor(cat));
        btn.addActionListener(e -> {
            for (JButton b : catButtons.values()) {
                b.setForeground(Color.BLACK);
            }
            backButton.setForeground(Color.BLACK);
            btn.setForeground(new Color(0, 120, 0));
            showCards(cat);
        });
        catBox.add(btn);
        catBox.add(Box.createVerticalStrut(20));
        catButtons.put(cat, btn);
    }

    leftPanel.add(catBox);
    add(leftPanel, BorderLayout.WEST);

    // Center area with product cards
    JPanel cardPanel = new JPanel(new BorderLayout());
    cardPanel.setOpaque(false);
    cardPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

    // Keep GridLayout but constrain card sizes
    cardArea = new JPanel(new GridLayout(0, 3, 30, 30));
    cardArea.setOpaque(false);

    // Make product cards using Map keys
    cards = new HashMap<>();
    for (Integer index : MenuData.ITEMS.keySet()) {
        JPanel card = makeCard(index);
        cards.put(index, card);
    }
    showCards(null); // Show all cards at start

    // Wrap cardArea in JScrollPane with size constraints
    JScrollPane cardsScrollPane = new JScrollPane(cardArea);
    cardsScrollPane.setBorder(null);
    cardsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    cardsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
    cardsScrollPane.setOpaque(false);
    cardsScrollPane.getViewport().setOpaque(false);
    // Limit maximum width to prevent card stretching
    int maxGridWidth = 3 * 250 + 2 * 30 + 60; // 3 cards * 250 + 2 gaps * 30 + 60 border
    cardsScrollPane.setMaximumSize(new Dimension(maxGridWidth, Integer.MAX_VALUE));

    cardPanel.add(cardsScrollPane, BorderLayout.CENTER);

    // Popping cart panel
    cartPanel = new JPanel(new BorderLayout());
    cartPanel.setBackground(Color.WHITE);
    cartPanel.setBorder(new EmptyBorder(20, 10, 20, 10));
    cartPanel.setVisible(false);

    JLabel cartTitle = new JLabel("🛒 Cart");
    cartTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
    cartTitle.setBorder(new EmptyBorder(0, 0, 10, 0));

    cartList = new JPanel();
    cartList.setLayout(new BoxLayout(cartList, BoxLayout.Y_AXIS));
    cartList.setOpaque(false);

    JScrollPane cartScroll = new JScrollPane(cartList);
    cartScroll.setBorder(null);
    cartScroll.setPreferredSize(new Dimension(230, 350));
    cartScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    cartScroll.setOpaque(false);
    cartScroll.getViewport().setOpaque(false);

    JPanel cartTop = new JPanel();
    cartTop.setOpaque(false);
    cartTop.setLayout(new BorderLayout());
    cartTop.add(cartTitle, BorderLayout.WEST);

    cartPanel.add(cartTop, BorderLayout.NORTH);
    cartPanel.add(cartScroll, BorderLayout.CENTER);

    JPanel cartBottom = new JPanel();
    cartBottom.setOpaque(false);
    cartBottom.setLayout(new BoxLayout(cartBottom, BoxLayout.Y_AXIS));
    cartBottom.setBorder(new EmptyBorder(10, 0, 0, 0));

    totalText = new JLabel("Total: ₱0.00");
    totalText.setFont(new Font("SansSerif", Font.BOLD, 14));
    totalText.setAlignmentX(Component.CENTER_ALIGNMENT);

    JButton checkoutBtn = new JButton("Checkout");
    checkoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    checkoutBtn.setFocusPainted(false);
    checkoutBtn.setMaximumSize(new Dimension(200, 40));
    checkoutBtn.addActionListener(e -> openCheckout());

    cartBottom.add(totalText);
    cartBottom.add(Box.createVerticalStrut(10));
    cartBottom.add(checkoutBtn);

    cartPanel.add(cartBottom, BorderLayout.SOUTH);

    // Layered pane for cards and cart
    JLayeredPane layeredPane = new JLayeredPane();
    layeredPane.setLayout(null);
    layeredPane.add(cardPanel, JLayeredPane.DEFAULT_LAYER);
    layeredPane.add(cartPanel, JLayeredPane.PALETTE_LAYER);
    add(layeredPane, BorderLayout.CENTER);

    // Position components
    layeredPane.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            cardPanel.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
            cartPanel.setBounds(layeredPane.getWidth() - 250, 0, 250, layeredPane.getHeight());
        }
    });

    // Connect VIEW CART button to toggle cart
    topBar.setCartToggleListener(e -> toggleCart());
}

    // Paint background image across entire panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            Image image = bgImage.getImage();
            // Scale image to fit panel
            Image scaled = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            g.drawImage(scaled, 0, 0, this);
        }
    }

    // Toggle cart visibility
    private void toggleCart() {
        isCartVisible = !isCartVisible;
        cartPanel.setVisible(isCartVisible);
        if (isCartVisible) {
            JLayeredPane layeredPane = (JLayeredPane) cartPanel.getParent();
            cartPanel.setBounds(layeredPane.getWidth() - 250, 0, 250, layeredPane.getHeight());
        }
    }

    // Get color for category button
    private Color getCatColor(String cat) {
        return switch (cat) {
            case "BEST SELLERS" -> new Color(255, 153, 153);
            case "SNACKS" -> new Color(255, 204, 204);
            case "BEVERAGE" -> new Color(255, 229, 180);
            case "WAFFOWLS" -> new Color(204, 229, 255);
            case "RICE MEALS" -> new Color(255, 242, 204);
            case "MILK TEA" -> new Color(212, 237, 218);
            default -> Color.LIGHT_GRAY;
        };
    }

    // Show cards for a category
    private void showCards(String category) {
    cardArea.removeAll(); // Clear current cards
    String mappedCategory = category == null ? null : CATEGORY_MAP.get(category);
    for (Map.Entry<Integer, MenuData.MenuItem> entry : MenuData.ITEMS.entrySet()) {
        Integer index = entry.getKey();
        MenuData.MenuItem item = entry.getValue();
        if (item == null) {
            System.out.println("Warning: Null MenuItem at index " + index);
            continue; // Skip null items
        }
        if (mappedCategory == null || item.category.equalsIgnoreCase(mappedCategory)) {
            JPanel card = cards.get(index);
            if (card != null) {
                cardArea.add(card); // Add card if it matches category or all are shown
            } else {
                System.out.println("Warning: No card found for index " + index);
            }
        }
    }
    cardArea.revalidate();
    cardArea.repaint();
}

    // Make a category button
    private JButton makeCatButton(String label, Color color) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 10));
        button.setMinimumSize(new Dimension(200, 100));
        button.setMaximumSize(new Dimension(200, 100));
        button.setBackground(color);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setOpaque(true); // Ensure buttons are visible
        return button;
    }

    // Make a product card
   private JPanel makeCard(int index) {
    RoundedPanel card = new RoundedPanel(15);
    card.setPreferredSize(new Dimension(250, 250));
    card.setMinimumSize(new Dimension(250, 250)); // Prevent shrinking
    card.setMaximumSize(new Dimension(250, 250)); // Prevent expansion
    card.setBackground(Color.LIGHT_GRAY);
    card.setBorder(new LineBorder(new Color(200, 230, 200), 1, true));
    card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    MenuData.MenuItem item = MenuData.ITEMS.get(index);
    if (item != null) {
        JLabel imageLabel;
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(item.imagePath));
            Image scaledImage = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        } catch (Exception e) {
            System.out.println("Failed to load image for " + item.name + ": " + e.getMessage());
            JPanel text = new JPanel();
            text.setOpaque(false);
            text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
            text.setBorder(new EmptyBorder(10, 10, 10, 10));

            Integer price = item.Regprice != null ? item.Regprice
                        : item.MedPrice != null ? item.MedPrice
                        : item.LrgPrice != null ? item.LrgPrice
                        : 0;
            JLabel name = new JLabel(item.name + "  ₱" + price);
            name.setFont(new Font("SansSerif", Font.BOLD, 16));
            name.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel desc = new JLabel("<html><div style='text-align: center;'>" + item.description + "</div></html>");
            desc.setFont(new Font("SansSerif", Font.PLAIN, 12));
            desc.setAlignmentX(Component.CENTER_ALIGNMENT);

            text.add(Box.createVerticalGlue());
            text.add(name);
            text.add(Box.createVerticalStrut(10));
            text.add(desc);
            text.add(Box.createVerticalGlue());

            card.setLayout(new BorderLayout());
            card.add(text, BorderLayout.CENTER);
            return card;
        }

        card.setLayout(new BorderLayout());
        card.add(imageLabel, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (item.Regprice == null && item.MedPrice != null && item.LrgPrice != null) {
                        showSizeSelectionDialog(index, item);
                    } else {
                        cart.addToCart(index);
                        updateCart();
                        JOptionPane.showMessageDialog(
                            SwingUtilities.getWindowAncestor(HomePage.this),
                            item.name + " added to cart!",
                            "Added",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                    leftPanel.revalidate();
                    leftPanel.repaint();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(HomePage.this),
                        "Error adding item to cart!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    System.out.println("Error clicking product: " + ex.getMessage());
                }
            }
        });
    }

    return card;
}

    // Size selection dialog
    private void showSizeSelectionDialog(int index, MenuData.MenuItem item) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("<html><b>Select size for:</b><br>" + item.name + "</html>");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton owletBtn = new JButton("Owlet (₱" + item.MedPrice + ")");
        owletBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        owletBtn.setBackground(new Color(220, 240, 255));
        owletBtn.setFocusPainted(false);

        JButton owlBtn = new JButton("Owl (₱" + item.LrgPrice + ")");
        owlBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        owlBtn.setBackground(new Color(255, 240, 200));
        owlBtn.setFocusPainted(false);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cancelBtn.setBackground(new Color(240, 240, 240));
        cancelBtn.setFocusPainted(false);

        panel.add(title);
        panel.add(Box.createVerticalStrut(18));
        panel.add(owletBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(owlBtn);
        panel.add(Box.createVerticalStrut(18));
        panel.add(cancelBtn);

        Window parentWindow = SwingUtilities.getWindowAncestor(HomePage.this);
        final JDialog dialog = new JDialog(parentWindow, "Choose Size", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2, true));
        dialog.setContentPane(panel);

        owletBtn.addActionListener(e -> {
            cart.addToCartWithSize(index, "MEDIUM");
            updateCart();
            dialog.dispose();
            JOptionPane.showMessageDialog(
                HomePage.this,
                item.name + " (Owlet) added to cart!",
                "Added to Cart",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        owlBtn.addActionListener(e -> {
            cart.addToCartWithSize(index, "LARGE");
            updateCart();
            dialog.dispose();
            JOptionPane.showMessageDialog(
                HomePage.this,
                item.name + " (Owl) added to cart!",
                "Added to Cart",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        dialog.pack();
        dialog.setLocationRelativeTo(HomePage.this);
        dialog.setVisible(true);
    }

    // Update cart display
    private void updateCart() {
        cartList.removeAll();
        total = 0.0;
        Map<CartManager.CartKey, Integer> items = cart.getCartItemsWithSize();
        for (Map.Entry<CartManager.CartKey, Integer> entry : items.entrySet()) {
            CartManager.CartKey key = entry.getKey();
            int id = key.index;
            int qty = entry.getValue();
            String size = key.size;
            MenuData.MenuItem item = MenuData.ITEMS.get(id);
            if (item == null || qty <= 0) continue;

            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BorderLayout());
            itemPanel.setMaximumSize(new Dimension(230, 60));
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1, true),
                new EmptyBorder(8, 8, 8, 8)
            ));
            itemPanel.setBackground(new Color(250, 250, 250));

            int price = 0;
            String displayName = item.name;
            if (item.Regprice != null) {
                price = item.Regprice;
            } else if ("MEDIUM".equals(size) && item.MedPrice != null) {
                price = item.MedPrice;
                displayName += " (Medium)";
            } else if ("LARGE".equals(size) && item.LrgPrice != null) {
                price = item.LrgPrice;
                displayName += " (Large)";
            }

            JLabel name = new JLabel(displayName + "  ₱" + price);
            name.setFont(new Font("SansSerif", Font.PLAIN, 13));

            JPanel qtyPanel = new JPanel();
            qtyPanel.setOpaque(false);
            JButton minus = new JButton("−");
            minus.setMargin(new Insets(2, 8, 2, 8));
            minus.setFocusPainted(false);
            minus.addActionListener(e -> {
                cart.removeFromCartWithSize(id, size);
                updateCart();
            });
            JLabel qtyText = new JLabel(String.valueOf(qty));
            qtyText.setFont(new Font("SansSerif", Font.BOLD, 13));
            JButton plus = new JButton("+");
            plus.setMargin(new Insets(2, 8, 2, 8));
            plus.setFocusPainted(false);
            plus.addActionListener(e -> {
                cart.addToCartWithSize(id, size);
                updateCart();
            });

            qtyPanel.add(minus);
            qtyPanel.add(Box.createHorizontalStrut(5));
            qtyPanel.add(qtyText);
            qtyPanel.add(Box.createHorizontalStrut(5));
            qtyPanel.add(plus);

            itemPanel.add(name, BorderLayout.NORTH);
            itemPanel.add(qtyPanel, BorderLayout.SOUTH);

            cartList.add(itemPanel);
            cartList.add(Box.createVerticalStrut(8));
        }
        total = cart.getTotalPriceWithSize();
        totalText.setText(String.format("Total: ₱%.2f", total));
        cartList.revalidate();
        cartList.repaint();
    }

    // Open checkout window
    private void openCheckout() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                "Cart is empty!",
                "Checkout",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        new CheckoutDialog(
            SwingUtilities.getWindowAncestor(this),
            cart,
            this::updateCart
        );
    }

    // Rounded panel for nice corners
    class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        }
    }

    // TopBar from v1.2
    class TopBar extends JPanel {
        private ActionListener cartToggleListener;

        public TopBar() {
            setLayout(new BorderLayout());
            setBackground(new Color(50, 50, 50));
            setPreferredSize(new Dimension(1000, 70));

            JLabel name = new JLabel("KUWAGO CAFE");
            name.setFont(new Font("SansSerif", Font.BOLD, 18));
            name.setForeground(Color.WHITE);

            JButton cartBtn = new JButton("🛒 VIEW CART");
            cartBtn.setFocusPainted(false);
            cartBtn.setBackground(Color.BLACK);
            cartBtn.setForeground(Color.WHITE);
            cartBtn.setBorderPainted(false);
            cartBtn.setOpaque(true);
            cartBtn.addActionListener(e -> {
                if (cartToggleListener != null) {
                    cartToggleListener.actionPerformed(e);
                }
            });

            add(name, BorderLayout.WEST);
            add(cartBtn, BorderLayout.EAST);
        }

        public void setCartToggleListener(ActionListener listener) {
            this.cartToggleListener = listener;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            int h = getHeight();
            g2.setPaint(new GradientPaint(0, h - 1, new Color(0, 0, 0, 80), 0, h + 10, new Color(0, 0, 0, 0)));
            g2.fillRect(0, h - 1, getWidth(), 10);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Kuwago Cafe");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setContentPane(new HomePage());
            frame.setVisible(true);
        });
    }
}
