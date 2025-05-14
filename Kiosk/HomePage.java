import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePage extends JPanel {

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

        sidebar.add(createCategoryButton("BEST SELLERS", new Color(255, 153, 153)));
        sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(createCategoryButton("SNACKS", new Color(255, 204, 204)));
         sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(createCategoryButton("THE ORIGINALS", new Color(255, 229, 180)));
         sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(createCategoryButton("RICE MEALS", new Color(255, 242, 204)));
         sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(createCategoryButton("MILK TEA", new Color(212, 237, 218)));


        add(sidebar, BorderLayout.WEST);

        // Grid of cards
        JPanel cardsGrid = new JPanel();
        cardsGrid.setOpaque(false);
        cardsGrid.setLayout(new GridLayout(2, 3, 10, 10));
        cardsGrid.setBorder(new EmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < 6; i++) {
            JPanel card = createClickableCard(i);
            cardsGrid.add(card);
        }

        add(cardsGrid, BorderLayout.CENTER);

        // Right sidebar (placeholder)
        JPanel rightSidebar = new JPanel();
        rightSidebar.setPreferredSize(new Dimension(200, 0));
        rightSidebar.setBackground(Color.WHITE);
        rightSidebar.setLayout(new BoxLayout(rightSidebar, BoxLayout.Y_AXIS));
        rightSidebar.setBorder(new EmptyBorder(20, 20, 20, 20));

        add(rightSidebar, BorderLayout.EAST);
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
        card.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(HomePage.this);
                Container contentPane = mainFrame.getContentPane();
                Component homePage = contentPane.getComponent(0);

                JPanel checkoutPanel = new JPanel(new BorderLayout());
                checkoutPanel.setBackground(new Color(240, 240, 240));

                JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JButton backButton = new JButton("←");
                backButton.addActionListener(ev -> {
                    contentPane.removeAll();
                    contentPane.add(homePage);
                    mainFrame.revalidate();
                    mainFrame.repaint();
                });
                backPanel.add(backButton);
                checkoutPanel.add(backPanel, BorderLayout.NORTH);

                JPanel checkoutContent = new JPanel();
                checkoutContent.setBorder(new EmptyBorder(20, 20, 20, 20));
                checkoutPanel.add(checkoutContent, BorderLayout.CENTER);

                contentPane.removeAll();
                contentPane.add(checkoutPanel);
                mainFrame.revalidate();
                mainFrame.repaint();
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

