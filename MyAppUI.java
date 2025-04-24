import javax.swing.*;
import java.awt.*;

public class MyAppUI extends JFrame {

    public MyAppUI() {
        setTitle("MyApp");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar panel main
JPanel sidebar = new JPanel();
sidebar.setBackground(Color.WHITE);
sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
sidebar.setPreferredSize(new Dimension(180, getHeight()));

// left-aligned FlowLayout Title panel with JPanel
JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
titlePanel.setBackground(Color.WHITE);
titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0)); // Adjust padding
JLabel appTitle = new JLabel("MyApp");
appTitle.setForeground(Color.ORANGE);
appTitle.setFont(new Font("SansSerif", Font.BOLD, 24));

titlePanel.add(appTitle);
sidebar.add(titlePanel);

// sidebar buttons
sidebar.add(createSidebarButton("HOME", true));
sidebar.add(createSidebarButton("ORDER HISTORY", false));
sidebar.add(createSidebarButton("MESSAGES", false));
sidebar.add(createSidebarButton("PRODUCTS", false));
sidebar.add(createSidebarButton("SETTINGS", false));
sidebar.add(Box.createVerticalGlue());
sidebar.add(createSidebarButton("LOGOUT", false));


        // Top panel (search)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(245, 245, 245));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField searchField = new JTextField("Search");
        searchField.setPreferredSize(new Dimension(200, 30));
        topPanel.add(searchField, BorderLayout.WEST);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        for (int i = 0; i < 6; i++) {
            JButton filterBtn = new JButton();
            filterBtn.setPreferredSize(new Dimension(60, 30));
            filterPanel.add(filterBtn);
        }
        topPanel.add(filterPanel, BorderLayout.SOUTH);

        // Grid panel (cards)
        JPanel cardPanel = new JPanel(new GridLayout(3, 4, 20, 20));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardPanel.setBackground(new Color(245, 245, 245));
        for (int i = 0; i < 10; i++) {
            JPanel card = new JPanel();
            card.setPreferredSize(new Dimension(100, 140));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            cardPanel.add(card);
        }

        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createSidebarButton(String label, boolean selected) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(160, 40));
        button.setBackground(selected ? new Color(255, 235, 200) : Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyAppUI ui = new MyAppUI();
            ui.setVisible(true);
        });
    }
}