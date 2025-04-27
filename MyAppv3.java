import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.*;

public class MyAppv3 extends JFrame {

    public MyAppv3() {
        setTitle("Starting Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Outer dark panel
        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(new Color(45, 45, 45));

        // Inner white panel
        JPanel mainPanel = new RoundedPanel(20);
        mainPanel.setBackground(new Color(240, 241, 242));
        mainPanel.setPreferredSize(new Dimension(1100, 700));
        mainPanel.setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = createSidebar();

        // Content Area
        JPanel contentArea = createContentArea();

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentArea, BorderLayout.CENTER);
        outerPanel.add(mainPanel);
        add(outerPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.WHITE);
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel appName = new JLabel("MyAppv3");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 28));
        appName.setForeground(new Color(255, 102, 0));
        appName.setAlignmentX(Component.CENTER_ALIGNMENT);
        appName.setBorder(new EmptyBorder(20, 10, 20, 10));

        sidebar.add(appName);
        sidebar.add(createSidebarButton("HOME", true));
        sidebar.add(createSidebarButton("ORDER HISTORY", false));
        sidebar.add(createSidebarButton("MESSAGES", false));
        sidebar.add(createSidebarButton("PRODUCTS", false));
        sidebar.add(createSidebarButton("SETTINGS", false));

        sidebar.add(Box.createVerticalGlue());

        sidebar.add(createSidebarButton("LOGOUT", false));

        return sidebar;
    }

    private JButton createSidebarButton(String text, boolean highlighted) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(180, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (highlighted) {
            button.setBorder(new LineBorder(new Color(255, 102, 0), 2, true));
            button.setBackground(Color.WHITE);
            button.setForeground(new Color(255, 102, 0));
        } else {
            button.setBorder(new EmptyBorder(5, 10, 5, 10));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
        }

        return button;
    }

    private JPanel createContentArea() {
        JPanel contentArea = new JPanel();
        contentArea.setOpaque(false);
        contentArea.setLayout(new BorderLayout());

        // Top buttons
        JPanel topButtons = new JPanel();
        topButtons.setOpaque(false);
        topButtons.setLayout(new GridLayout(1, 6, 20, 10));
        topButtons.setBorder(new EmptyBorder(20, 40, 10, 40));

        for (int i = 0; i < 6; i++) {
            JButton btn = new RoundedButton(15);
            btn.setPreferredSize(new Dimension(100, 40));
            btn.setText("Category " + (i + 1));
            topButtons.add(btn);
        }

        // Cards grid
        JPanel cardsGrid = new JPanel();
        cardsGrid.setOpaque(false);
        cardsGrid.setLayout(new GridLayout(3, 3, 20, 20));
        cardsGrid.setBorder(new EmptyBorder(20, 40, 40, 40));

        for (int i = 0; i < 9; i++) {
            JPanel card = createClickableCard(i);
            cardsGrid.add(card);
        }

        contentArea.add(topButtons, BorderLayout.NORTH);
        contentArea.add(cardsGrid, BorderLayout.CENTER);

        return contentArea;
    }

    private void openPanelForCard(int index) {
        JPanel newPanel = new JPanel();
        newPanel.setBackground(new Color(240, 241, 242));
        newPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("You opened panel for Card " + (index + 1));
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        newPanel.add(label, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        backButton.addActionListener(e -> {
            // Restore the original content area
            getContentPane().removeAll();
            add(createContentArea(), BorderLayout.CENTER);
            revalidate();
            repaint();
        });
        newPanel.add(backButton, BorderLayout.SOUTH);

        // Replace the content area with the new panel
        getContentPane().removeAll();
        add(newPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private JPanel createClickableCard(int index) {
        JPanel card = new RoundedPanel(15);
        card.setBackground(new Color(255, 252, 250));
        card.setBorder(new LineBorder(new Color(200, 230, 200), 1, true));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openPanelForCard(index);
            }
        });
        return card;
    }

    // Custom rounded JPanel
    class RoundedPanel extends JPanel {
        private final int cornerRadius;

        public RoundedPanel(int radius) {
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        }
    }

    // Custom rounded JButton
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
        

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MyAppv3().setVisible(true);
        });
    }
}