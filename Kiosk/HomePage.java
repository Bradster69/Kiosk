import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class HomePage extends JPanel {

    private JPanel cardsGrid;
    private List<JPanel> allCards = new ArrayList<>();
    private Map<String, JButton> categoryButtons = new HashMap<>();
    private static final String[] CATEGORIES = {
        "BEST SELLERS", "SNACKS", "THE ORIGINALS", "RICE MEALS", "MILK TEA"
    };
    private static final Map<String, String> CATEGORY_MAP = new HashMap<>();
    static {
        CATEGORY_MAP.put("BEST SELLERS", "BESTSELLERS");
        CATEGORY_MAP.put("SNACKS", "SNACKS");
        CATEGORY_MAP.put("THE ORIGINALS", "THE ORIGINALS");
        CATEGORY_MAP.put("RICE MEALS", "RICEMEALS");
        CATEGORY_MAP.put("MILK TEA", "MILKTEA");
    }

    private CartManager cartManager = new CartManager();
    private JPanel cartPanel; // Panel for cart items
    private JLabel totalLabel; // Shows total price
    private double totalPrice = 0.0;

    public HomePage() {
        setLayout(new BorderLayout());
        setOpaque(false);

        FadeHeaderPanel fadeHeader = new FadeHeaderPanel();
        fadeHeader.setPreferredSize(new Dimension(1000, 60));
        add(fadeHeader, BorderLayout.NORTH);

        // Sidebar with back + categories
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Color.WHITE);
        sidebar.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton backButton = new JButton("← Back");
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(backButton);
        sidebar.add(Box.createVerticalStrut(50));

        for (String cat : CATEGORIES) {
            JButton btn = createCategoryButton(cat, getCategoryColor(cat));
            btn.addActionListener(e -> filterCardsByCategory(cat));
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(30));
            categoryButtons.put(cat, btn);
        }

        add(sidebar, BorderLayout.WEST);

        // Grid of cards (now scrollable)
        cardsGrid = new JPanel();
        cardsGrid.setOpaque(false);
        cardsGrid.setLayout(new GridLayout(0, 3, 10, 10)); // 3 columns, any number of rows
        cardsGrid.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create all cards and store them
        for (int i = 0; i < MenuData.ITEMS.size(); i++) {
            JPanel card = createClickableCard(i);
            allCards.add(card);
        }
        updateCardsGrid(null); // Show all by default

        // Wrap cardsGrid in a scroll pane
        JScrollPane cardsScrollPane = new JScrollPane(cardsGrid);
        cardsScrollPane.setBorder(null);
        cardsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        cardsScrollPane.getVerticalScrollBar().setUnitIncrement(16); // smoother scrolling

        add(cardsScrollPane, BorderLayout.CENTER);

        // Right sidebar (cart system)
        JPanel rightSidebar = new JPanel();
        rightSidebar.setPreferredSize(new Dimension(250, 0));
        rightSidebar.setBackground(Color.WHITE);
        rightSidebar.setLayout(new BorderLayout());
        rightSidebar.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel cartTitle = new JLabel("🛒 Cart");
        cartTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        cartTitle.setBorder(new EmptyBorder(0, 0, 10, 0));

        cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setOpaque(false);

        JScrollPane cartScroll = new JScrollPane(cartPanel);
        cartScroll.setBorder(null);
        cartScroll.setPreferredSize(new Dimension(230, 350));
        cartScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel cartTop = new JPanel();
        cartTop.setOpaque(false);
        cartTop.setLayout(new BorderLayout());
        cartTop.add(cartTitle, BorderLayout.WEST);

        rightSidebar.add(cartTop, BorderLayout.NORTH);
        rightSidebar.add(cartScroll, BorderLayout.CENTER);

        // Bottom panel for total and checkout
        JPanel cartBottom = new JPanel();
        cartBottom.setOpaque(false);
        cartBottom.setLayout(new BoxLayout(cartBottom, BoxLayout.Y_AXIS));
        cartBottom.setBorder(new EmptyBorder(10, 0, 0, 0));

        totalLabel = new JLabel("Total: ₱0.00");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton checkoutBtn = new JButton("Checkout");
        checkoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkoutBtn.setFocusPainted(false);
        checkoutBtn.setMaximumSize(new Dimension(200, 40));
        checkoutBtn.addActionListener(e -> showCheckoutDialog());

        cartBottom.add(totalLabel);
        cartBottom.add(Box.createVerticalStrut(10));
        cartBottom.add(checkoutBtn);

        rightSidebar.add(cartBottom, BorderLayout.SOUTH);

        add(rightSidebar, BorderLayout.EAST);
    }

    private Color getCategoryColor(String cat) {
        switch (cat) {
            case "BEST SELLERS": return new Color(255, 153, 153);
            case "SNACKS": return new Color(255, 204, 204);
            case "THE ORIGINALS": return new Color(255, 229, 180);
            case "RICE MEALS": return new Color(255, 242, 204);
            case "MILK TEA": return new Color(212, 237, 218);
            default: return Color.LIGHT_GRAY;
        }
    }

    private void filterCardsByCategory(String categoryLabel) {
        String mappedCategory = CATEGORY_MAP.get(categoryLabel);
        updateCardsGrid(mappedCategory);
    }

    private void updateCardsGrid(String category) {
        cardsGrid.removeAll();
        int count = 0;
        for (int i = 0; i < MenuData.ITEMS.size(); i++) {
            MenuData.MenuItem item = MenuData.ITEMS.get(i);
            if (category == null || (item != null && item.category.equalsIgnoreCase(category))) {
                cardsGrid.add(allCards.get(i));
                count++;
            }
        }
        // No need to fill empty grid slots for scrollable layout
        cardsGrid.revalidate();
        cardsGrid.repaint();
    }

    private JButton createCategoryButton(String label, Color bgColor) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 50));
        button.setBackground(bgColor);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setFocusPainted(false);
        return button;
    }

    private JPanel createClickableCard(int index) {
        JPanel card = new RoundedPanel(15);
        card.setPreferredSize(new Dimension(150, 150));
        card.setBackground(Color.LIGHT_GRAY);
        card.setBorder(new LineBorder(new Color(200, 230, 200), 1, true));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Get menu item from MenuData
        MenuData.MenuItem item = MenuData.ITEMS.get(index);
        if (item != null) {
            JPanel textPanel = new JPanel();
            textPanel.setOpaque(false);
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            Integer price = item.Regprice != null ? item.Regprice
                        : item.MedPrice != null ? item.MedPrice
                        : item.LrgPrice != null ? item.LrgPrice
                        : 0;
            JLabel nameLabel = new JLabel(item.name + "  ₱" + price);
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
            JLabel descLabel = new JLabel("<html><body style='width:120px'>" + item.description + "</body></html>");
            textPanel.add(nameLabel);
            textPanel.add(descLabel);

            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.add(Box.createVerticalGlue());
            card.add(textPanel);
            card.add(Box.createVerticalGlue());

            // Add mouse listener to the card itself
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // If the item has MedPrice and LrgPrice (no Regprice), ask for size
                    if (item.Regprice == null && item.MedPrice != null && item.LrgPrice != null) {
                        showSizeSelectionDialog(index, item);
                    } else {
                        cartManager.addToCart(index);
                        updateCartPanel();
                        MenuData.MenuItem addedItem = MenuData.ITEMS.get(index);
                        JOptionPane.showMessageDialog(
                            HomePage.this,
                            addedItem.name + " added to cart!",
                            "Added to Cart",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
            });
        }

        return card;
    }

    // Show dialog to pick size for drinks with MedPrice and LrgPrice
    private void showSizeSelectionDialog(int index, MenuData.MenuItem item) {
        // Custom panel for better design
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

        // Always get the window ancestor for the dialog parent
        Window parentWindow = SwingUtilities.getWindowAncestor(HomePage.this);
        final JDialog dialog = new JDialog(parentWindow, "Choose Size", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2, true));
        dialog.setContentPane(panel);

        owletBtn.addActionListener(e -> {
            cartManager.addToCartWithSize(index, "MEDIUM");
            updateCartPanel();
            dialog.dispose();
            JOptionPane.showMessageDialog(
                HomePage.this,
                item.name + " (Owlet) added to cart!",
                "Added to Cart",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        owlBtn.addActionListener(e -> {
            cartManager.addToCartWithSize(index, "LARGE");
            updateCartPanel();
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

    private void updateCartPanel() {
        cartPanel.removeAll();
        totalPrice = 0.0;
        Map<CartManager.CartKey, Integer> items = cartManager.getCartItemsWithSize();
        for (Map.Entry<CartManager.CartKey, Integer> entry : items.entrySet()) {
            CartManager.CartKey key = entry.getKey();
            int idx = key.index;
            int qty = entry.getValue();
            String size = key.size;
            MenuData.MenuItem item = MenuData.ITEMS.get(idx);
            if (item == null || qty <= 0) continue;

            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1, true),
                new EmptyBorder(8, 8, 8, 8)
            ));
            itemPanel.setMaximumSize(new Dimension(210, 60));
            itemPanel.setBackground(new Color(250, 250, 250));

            // Determine price based on size
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

            JLabel nameLabel = new JLabel(displayName + "  ₱" + price);
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

            JPanel qtyPanel = new JPanel();
            qtyPanel.setOpaque(false);
            qtyPanel.setLayout(new BoxLayout(qtyPanel, BoxLayout.X_AXIS));
            JButton minusBtn = new JButton("-");
            minusBtn.setMargin(new Insets(2, 8, 2, 8));
            minusBtn.setFocusPainted(false);
            minusBtn.addActionListener(e -> {
                cartManager.removeFromCartWithSize(idx, size);
                updateCartPanel();
            });
            JLabel qtyLabel = new JLabel(String.valueOf(qty));
            qtyLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
            JButton plusBtn = new JButton("+");
            plusBtn.setMargin(new Insets(2, 8, 2, 8));
            plusBtn.setFocusPainted(false);
            plusBtn.addActionListener(e -> {
                cartManager.addToCartWithSize(idx, size);
                updateCartPanel();
            });

            qtyPanel.add(minusBtn);
            qtyPanel.add(Box.createHorizontalStrut(5));
            qtyPanel.add(qtyLabel);
            qtyPanel.add(Box.createHorizontalStrut(5));
            qtyPanel.add(plusBtn);

            itemPanel.add(nameLabel, BorderLayout.NORTH);
            itemPanel.add(qtyPanel, BorderLayout.SOUTH);

            cartPanel.add(itemPanel);
            cartPanel.add(Box.createVerticalStrut(8));
        }
        totalPrice = cartManager.getTotalPriceWithSize();
        totalLabel.setText(String.format("Total: ₱%.2f", totalPrice));
        cartPanel.revalidate();
        cartPanel.repaint();
    }

    private void showCheckoutDialog() {
        if (cartManager.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!", "Checkout", JOptionPane.WARNING_MESSAGE);
            return;
        }
        new CheckoutDialog(
            SwingUtilities.getWindowAncestor(this),
            cartManager,
            this::updateCartPanel
        );
    }

    // RoundedPanel class
    class RoundedPanel extends JPanel {
        private final int cornerRadius;

        public RoundedPanel(int radius) {
            this.cornerRadius = radius;
            setOpaque(false);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        }
    }

    // RoundedButton (not used here but kept from second file)
    class RoundedButton extends JButton {
        private final int cornerRadius;

        public RoundedButton(int radius) {
            this.cornerRadius = radius;
            setContentAreaFilled(false);
            setFocusPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            super.paintComponent(g);
        }
    }
    
    class FadeHeaderPanel extends JPanel {
        public FadeHeaderPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding inside the header

        JLabel nameLabel = new JLabel("pangalan");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JButton viewCartButton = new JButton("🛒 VIEW CART");
        viewCartButton.setFocusPainted(false);

        add(nameLabel, BorderLayout.WEST);
        add(viewCartButton, BorderLayout.EAST);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        GradientPaint gp = new GradientPaint(
            0, 0, new Color(255, 255, 255, 220),  // Opaque white at top
            0, getHeight(), new Color(255, 255, 255, 0)  // Transparent at bottom
        );
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }
}

    // Test this merged panel
    public static void main(String[] args) {
        JFrame frame = new JFrame("Merged HomePage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setContentPane(new HomePage());
        frame.setVisible(true);
    }
}

