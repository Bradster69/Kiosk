import java.awt.*;
import javax.swing.*;

public class MyAppv3 extends JFrame {

    public MyAppv3() {
    setTitle("Kuwago Cafe");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setUndecorated(false);

    // Replace fullscreen with maximized window
    setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
    setLayout(new BorderLayout());

    // Outer background panel
    JPanel outerPanel = new JPanel(new BorderLayout());
    outerPanel.setBackground(new Color(240, 241, 242)); // Light gray

    // Inner rounded white panel
    JPanel mainPanel = new RoundedPanel(30);
    mainPanel.setBackground(Color.WHITE);
    mainPanel.setLayout(new BorderLayout());
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Add HomePage as the content
    JPanel contentArea = new HomePage();
    mainPanel.add(contentArea, BorderLayout.CENTER);

    // Wrapper panel to center and margin the main panel
    JPanel wrapper = new JPanel(new BorderLayout());
    wrapper.setOpaque(false);
    wrapper.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    wrapper.add(mainPanel, BorderLayout.CENTER);

    mainPanel.setMinimumSize(new Dimension(1100, 700));
    wrapper.setMinimumSize(new Dimension(1160, 760));

    setMinimumSize(new Dimension(1200, 800));

    outerPanel.add(wrapper, BorderLayout.CENTER);
    add(outerPanel, BorderLayout.CENTER);
}

    // Custom rounded JPanel
    static class RoundedPanel extends JPanel {
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
