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
        "BEST SELLERS", "SNACKS", "WAFFOWLS", "BEVERAGE", "RICE MEALS", "MILK TEA",
        "FRUIT TEA", "CREAM CHEESE", "YAKULT", "COFFEE"
    };
    private static final Map<String, String> CATEGORY_MAP = new HashMap<>();
    private static final Map<String, String> CATEGORY_IMAGES = new HashMap<>();
    static {
        CATEGORY_MAP.put("BEST SELLERS", "BESTSELLERS");
        CATEGORY_MAP.put("WAFFOWLS", "WAFFOWLS");
        CATEGORY_MAP.put("SNACKS", "SNACKS");
        CATEGORY_MAP.put("BEVERAGE", "BEVERAGE");
        CATEGORY_MAP.put("RICE MEALS", "RICEMEALS");
        CATEGORY_MAP.put("MILK TEA", "MILKTEA");
        CATEGORY_MAP.put("FRUIT TEA", "FRUITTEA");
        CATEGORY_MAP.put("CREAM CHEESE", "CREAMCHEESE");
        CATEGORY_MAP.put("YAKULT", "YAKULT");
        CATEGORY_MAP.put("COFFEE", "COFFEE");

        CATEGORY_IMAGES.put("BEST SELLERS", "assets/category_bestsellers.png");
        CATEGORY_IMAGES.put("SNACKS", "assets/category_snacks.png");
        CATEGORY_IMAGES.put("WAFFOWLS", "assets/category_waffowls.png");
        CATEGORY_IMAGES.put("BEVERAGE", "assets/category_beverage.png");
        CATEGORY_IMAGES.put("RICE MEALS", "assets/category_ricemeals.png");
        CATEGORY_IMAGES.put("MILK TEA", "assets/category_milktea.png");
        CATEGORY_IMAGES.put("FRUIT TEA", "assets/category_fruittea.png");
        CATEGORY_IMAGES.put("CREAM CHEESE", "assets/category_creamcheese.png");
        CATEGORY_IMAGES.put("YAKULT", "assets/category_yakult.png");
        CATEGORY_IMAGES.put("COFFEE", "assets/category_coffee.png");
    }
    private JPanel cartList;
    private JLabel totalText;
    private double total;
    private CartManager cart;
    private JPanel leftPanel;
    private RoundedPanel catBox;
    private JPanel cartPanel;
    private JPanel cardPanel;
    private boolean isCartVisible = false;
    private ImageIcon bgImage;
    private JButton selectedButton = null;

    public HomePage() {
        setLayout(new BorderLayout());
        setOpaque(false);

        bgImage = new ImageIcon(getClass().getResource("assets/whiteLeaf.png"));

        cart = new CartManager();

        TopBar topBar = new TopBar();
        add(topBar, BorderLayout.NORTH);

        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setOpaque(false);
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        leftPanel.setPreferredSize(new Dimension(250, 0));

        catBox = new RoundedPanel(20);
        catBox.setLayout(new BoxLayout(catBox, BoxLayout.Y_AXIS));
        catBox.setBackground(new Color(245, 245, 245, 200));
        catBox.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton backButton = new JButton("← Back");
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(200, 100));
        backButton.setMinimumSize(new Dimension(200, 100));
        backButton.setMaximumSize(new Dimension(200, 100));
        backButton.setBackground(new Color(200, 200, 200));
        backButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        backButton.setFocusPainted(false);
        backButton.setOpaque(true);
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        backButton.addActionListener(e -> {
            updateButtonSelection(backButton);
            showCards(null);
        });

        catBox.add(backButton);
        catBox.add(Box.createVerticalStrut(20));

        for (String cat : CATEGORIES) {
            JButton btn = makeCatButton(cat, getCatColor(cat));
            btn.addActionListener(e -> {
                updateButtonSelection(btn);
                showCards(cat);
            });
            catBox.add(btn);
            catBox.add(Box.createVerticalStrut(20));
        }

        JScrollPane catScroll = new JScrollPane(catBox);
        catScroll.setBorder(null);
        catScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        catScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        catScroll.setOpaque(false);
        catScroll.getViewport().setOpaque(false);
        catScroll.getVerticalScrollBar().setUnitIncrement(16);

        leftPanel.add(catScroll, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);

        cardPanel = new JPanel(new BorderLayout());
        cardPanel.setOpaque(false);
        cardPanel.setBorder(new EmptyBorder(30, 30, 30, 0));

        cardArea = new JPanel(new GridLayout(0, 4, 30, 30));
        cardArea.setOpaque(false);

        cards = new HashMap<>();
        for (Integer index : MenuData.ITEMS.keySet()) {
            JPanel card = makeCard(index);
            cards.put(index, card);
        }
        showCards(null);

        JScrollPane cardsScrollPane = new JScrollPane(cardArea);
        cardsScrollPane.setBorder(null);
        cardsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        cardsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        cardsScrollPane.setOpaque(false);
        cardsScrollPane.getViewport().setOpaque(false);

        cardPanel.add(cardsScrollPane, BorderLayout.CENTER);

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

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.add(cardPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(cartPanel, JLayeredPane.PALETTE_LAYER);
        add(layeredPane, BorderLayout.CENTER);

        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateBounds();
            }
        });

        topBar.setCartToggleListener(e -> {
            System.out.println("Cart button clicked, toggling cart");
            toggleCart();
        });

        SwingUtilities.invokeLater(this::updateBounds);
    }

    private void updateBounds() {
    JLayeredPane layeredPane = (JLayeredPane) cartPanel.getParent();
    int width = layeredPane.getWidth();
    int height = layeredPane.getHeight();
    int cartWidth = 250;

    // Set cardPanel to full size, regardless of cart visibility
    cardPanel.setBounds(0, 0, width, height);
    System.out.println("Card panel bounds set to: 0,0," + width + "," + height);

    // Set cartPanel bounds (right-aligned, full height)
    if (isCartVisible) {
        cartPanel.setBounds(width - cartWidth, 0, cartWidth, height);
        System.out.println("Cart panel bounds set to: " + (width - cartWidth) + ",0," + cartWidth + "," + height);
    }

    // Update card area size based on full width
    updateCardAreaSize(width);
    layeredPane.revalidate();
    layeredPane.repaint();
}

    private void updateCardAreaSize(int availableWidth) {
        int cardWidth = 250;
        int gap = 30;
        int cardsPerRow = 4;
        int requiredWidth = cardsPerRow * cardWidth + (cardsPerRow - 1) * gap;
        int adjustedWidth = Math.min(availableWidth - 30, requiredWidth);
        cardArea.setPreferredSize(new Dimension(adjustedWidth, cardArea.getPreferredSize().height));
        cardArea.setMaximumSize(new Dimension(adjustedWidth, Integer.MAX_VALUE));
        cardArea.setMinimumSize(new Dimension(adjustedWidth, 250));
        cardArea.revalidate();
    }

    private void updateButtonSelection(JButton selected) {
        if (selectedButton != null) {
            selectedButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }
        selectedButton = selected;
        selectedButton.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 0), 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            Image image = bgImage.getImage();
            Image scaled = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            g.drawImage(scaled, 0, 0, this);
        }
    }

    private void toggleCart() {
        isCartVisible = !isCartVisible;
        cartPanel.setVisible(isCartVisible);
        System.out.println("Toggling cart: isCartVisible=" + isCartVisible);
        updateBounds();
        cartPanel.revalidate();
        cartPanel.repaint();
    }

    private Color getCatColor(String cat) {
        return switch (cat) {
            case "BEST SELLERS" -> new Color(255, 153, 153);
            case "SNACKS" -> new Color(255, 204, 204);
            case "BEVERAGE" -> new Color(255, 229, 180);
            case "WAFFOWLS" -> new Color(204, 229, 255);
            case "RICE MEALS" -> new Color(255, 242, 204);
            case "MILK TEA" -> new Color(212, 237, 218);
            case "FRUIT TEA" -> new Color(255, 182, 193);
            case "CREAM CHEESE" -> new Color(221, 160, 221);
            case "YAKULT" -> new Color(173, 216, 230);
            case "COFFEE" -> new Color(210, 180, 140);
            default -> Color.LIGHT_GRAY;
        };
    }

    private void showCards(String category) {
        cardArea.removeAll();
        String mappedCategory = category == null ? null : CATEGORY_MAP.get(category);
        for (Map.Entry<Integer, MenuData.MenuItem> entry : MenuData.ITEMS.entrySet()) {
            Integer index = entry.getKey();
            MenuData.MenuItem item = entry.getValue();
            if (item == null) {
                System.out.println("Warning: Null MenuItem at index " + index);
                continue;
            }
            if (mappedCategory == null || item.category.equalsIgnoreCase(mappedCategory)) {
                JPanel card = cards.get(index);
                if (card != null) {
                    cardArea.add(card);
                } else {
                    System.out.println("Warning: No card found for index " + index);
                }
            }
        }
        int cardCount = cardArea.getComponentCount();
        int rows = (int) Math.ceil((double) cardCount / 4);
        int rowHeight = 250 + 30;
        cardArea.setPreferredSize(new Dimension(cardArea.getPreferredSize().width, rows * rowHeight));
        cardArea.revalidate();
        cardArea.repaint();
    }

    private JButton makeCatButton(String label, Color color) {
        JButton button = new JButton();
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 100));
        button.setMinimumSize(new Dimension(200, 100));
        button.setMaximumSize(new Dimension(200, 100));
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        String imagePath = CATEGORY_IMAGES.get(label);
        if (imagePath != null) {
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
                Image originalImage = icon.getImage();
                int imgWidth = originalImage.getWidth(null);
                int imgHeight = originalImage.getHeight(null);
                int targetWidth = 200;
                int targetHeight = 100;
                double scale = Math.min((double) targetWidth / imgWidth, (double) targetHeight / imgHeight);
                int scaledWidth = (int) (imgWidth * scale);
                int scaledHeight = (int) (imgHeight * scale);
                Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledImage));
                button.setHorizontalAlignment(SwingConstants.CENTER);
                button.setVerticalAlignment(SwingConstants.CENTER);
            } catch (Exception e) {
                System.out.println("Failed to load category image for " + label + ": " + e.getMessage());
                button.setText(label);
                button.setFont(new Font("SansSerif", Font.BOLD, 12));
            }
        } else {
            button.setText(label);
            button.setFont(new Font("SansSerif", Font.BOLD, 12));
        }

        return button;
    }

    private JPanel makeCard(int index) {
        RoundedPanel card = new RoundedPanel(15);
        card.setPreferredSize(new Dimension(250, 250));
        card.setMinimumSize(new Dimension(250, 250));
        card.setMaximumSize(new Dimension(250, 250));
        card.setBackground(Color.LIGHT_GRAY);
        card.setBorder(new LineBorder(new Color(200, 230, 200), 1, true));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        MenuData.MenuItem item = MenuData.ITEMS.get(index);
        if (item != null) {
            JLabel imageLabel;
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(item.imagePath));
                Image originalImage = icon.getImage();
                int imgWidth = originalImage.getWidth(null);
                int imgHeight = originalImage.getHeight(null);
                int targetSize = 250;
                double scale = Math.min((double) targetSize / imgWidth, (double) targetSize / imgHeight);
                int scaledWidth = (int) (imgWidth * scale);
                int scaledHeight = (int) (imgHeight * scale);
                Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
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
                System.out.println("Cart button action triggered");
                if (cartToggleListener != null) {
                    cartToggleListener.actionPerformed(e);
                } else {
                    System.out.println("Warning: cartToggleListener is null");
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
}
