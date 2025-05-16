import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage extends JPanel {

    private JPanel cardsGrid;
    private List<JPanel> allCards = new ArrayList<>();
    private Map<String, JButton> categoryButtons = new HashMap<>();
    private List<Integer> cart = new ArrayList<>(); // Store indices of products added to cart
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

        // Grid of cards
        cardsGrid = new JPanel();
        cardsGrid.setOpaque(false);
        cardsGrid.setLayout(new GridLayout(2, 3, 10, 10));
        cardsGrid.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create all cards and store them
        for (int i = 0; i < MenuData.ITEMS.size(); i++) {
            JPanel card = createClickableCard(i);
            allCards.add(card);
        }
        updateCardsGrid(null); // Show all by default

        add(cardsGrid, BorderLayout.CENTER);

        // Right sidebar (placeholder)
        JPanel rightSidebar = new JPanel();
        rightSidebar.setPreferredSize(new Dimension(200, 0));
        rightSidebar.setBackground(Color.WHITE);
        rightSidebar.setLayout(new BoxLayout(rightSidebar, BoxLayout.Y_AXIS));
        rightSidebar.setBorder(new EmptyBorder(20, 20, 20, 20));

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
        // Fill empty grid slots if less than 6
        while (count < 6) {
            cardsGrid.add(Box.createGlue());
            count++;
        }
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
            JLabel nameLabel = new JLabel(item.name);
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
            JLabel priceLabel = new JLabel("₱" + item.price);
            JLabel descLabel = new JLabel("<html><body style='width:120px'>" + item.description + "</body></html>");
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.add(nameLabel);
            card.add(priceLabel);
            card.add(descLabel);
        }

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Add to cart instead of opening a new page
                cart.add(index);
                MenuData.MenuItem addedItem = MenuData.ITEMS.get(index);
                JOptionPane.showMessageDialog(
                    HomePage.this,
                    addedItem.name + " added to cart!",
                    "Added to Cart",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        return card;
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

