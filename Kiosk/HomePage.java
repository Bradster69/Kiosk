import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class HomePage extends JPanel {

    public HomePage() {
        setLayout(new BorderLayout());
        setOpaque(false);

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

        add(topButtons, BorderLayout.NORTH);
        add(cardsGrid, BorderLayout.CENTER);
    }

    private JPanel createClickableCard(int index) {
        JPanel card = new RoundedPanel(15);
        card.setBackground(new Color(255, 252, 250));
        card.setBorder(new LineBorder(new Color(200, 230, 200), 1, true));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(HomePage.this, "You clicked on Card " + (index + 1));
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
}
