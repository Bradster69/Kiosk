import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class MyAppv3 extends JFrame {

    private String activeTab = "HOME"; // Track the active tab

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

        // Content Area (Home Page)
        JPanel contentArea = new HomePage();

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

        updateButtonStyle(button, text.equals(activeTab)); // Highlight if active

        button.addActionListener(e -> {
            activeTab = text; // Update the active tab
            refreshSidebar(); // Refresh sidebar to update highlights

            if (text.equals("HOME")) {
                // Restore the Home page
                getContentPane().removeAll();
                add(createSidebar(), BorderLayout.WEST);
                add(new HomePage(), BorderLayout.CENTER);
            } else if (text.equals("SETTINGS")) {
                // Replace content area with SettingsTab
                getContentPane().removeAll();
                add(createSidebar(), BorderLayout.WEST);
                add(new SettingsTab(), BorderLayout.CENTER);
            }
            // Add other button actions here if needed

            revalidate();
            repaint();
        });

        return button;
    }

    private void updateButtonStyle(JButton button, boolean isActive) {
        if (isActive) {
            button.setBorder(new LineBorder(new Color(255, 102, 0), 2, true));
            button.setBackground(Color.WHITE);
            button.setForeground(new Color(255, 102, 0));
        } else {
            button.setBorder(new EmptyBorder(5, 10, 5, 10));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
        }
    }

    private void refreshSidebar() {
        getContentPane().removeAll();
        add(createSidebar(), BorderLayout.WEST);
        revalidate();
        repaint();
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
        
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MyAppv3().setVisible(true);
        });
    }
}