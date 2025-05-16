import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CheckoutDialog extends JDialog {
    private final CartManager cartManager;
    private final Runnable onOrderComplete;
    private String paymentMethod = "";
    private String orderType = "";

    public CheckoutDialog(Window parent, CartManager cartManager, Runnable onOrderComplete) {
        super(parent, "Checkout", ModalityType.APPLICATION_MODAL);
        this.cartManager = cartManager;
        this.onOrderComplete = onOrderComplete;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 320);
        setLocationRelativeTo(parent);
        setUndecorated(true);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2, true));
        showPaymentPage();
    }

    private void showPaymentPage() {
        JPanel panel = new GradientPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel label = new JLabel("Select Payment Method");
        label.setFont(new Font("SansSerif", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel total = new JLabel(String.format("Total: ₱%.2f", cartManager.getTotalPrice()));
        total.setFont(new Font("SansSerif", Font.PLAIN, 16));
        total.setAlignmentX(Component.CENTER_ALIGNMENT);
        total.setBorder(new EmptyBorder(10, 0, 20, 0));

        JButton cashBtn = styledButton("💵  Cash", new Color(76, 175, 80), Color.WHITE);
        JButton cashlessBtn = styledButton("💳  Cashless", new Color(33, 150, 243), Color.WHITE);
        JButton cancelBtn = styledButton("Cancel", new Color(220, 220, 220), Color.DARK_GRAY);
        cancelBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));

        cashBtn.addActionListener(e -> {
            paymentMethod = "Cash";
            showOrderTypePage();
        });
        cashlessBtn.addActionListener(e -> {
            paymentMethod = "Cashless";
            showOrderTypePage();
        });
        cancelBtn.addActionListener(e -> dispose());

        panel.add(label);
        panel.add(total);
        panel.add(Box.createVerticalStrut(10));
        panel.add(cashBtn);
        panel.add(Box.createVerticalStrut(15));
        panel.add(cashlessBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(cancelBtn);

        setContentPane(panel);
        revalidate();
        repaint();
        setVisible(true);
    }

    private void showOrderTypePage() {
        JPanel panel = new GradientPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel label = new JLabel("Dine In or Take Out?");
        label.setFont(new Font("SansSerif", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton dineInBtn = styledButton("🍽️  Dine In", new Color(255, 193, 7), Color.DARK_GRAY);
        JButton takeOutBtn = styledButton("🥡  Take Out", new Color(255, 152, 0), Color.DARK_GRAY);
        JButton backBtn = styledButton("Back", new Color(220, 220, 220), Color.DARK_GRAY);
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));

        dineInBtn.addActionListener(e -> {
            orderType = "Dine In";
            showReceiptPage();
        });
        takeOutBtn.addActionListener(e -> {
            orderType = "Take Out";
            showReceiptPage();
        });
        backBtn.addActionListener(e -> showPaymentPage());

        panel.add(label);
        panel.add(Box.createVerticalStrut(30));
        panel.add(dineInBtn);
        panel.add(Box.createVerticalStrut(15));
        panel.add(takeOutBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(backBtn);

        setContentPane(panel);
        revalidate();
        repaint();
    }

    private void showReceiptPage() {
        JPanel panel = new GradientPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel shopName = new JLabel("Kuwago Cafe");
        shopName.setFont(new Font("SansSerif", Font.BOLD, 22));
        shopName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel shopLoc = new JLabel("Shop Location: 123 Main St, City");
        shopLoc.setFont(new Font("SansSerif", Font.PLAIN, 13));
        shopLoc.setAlignmentX(Component.CENTER_ALIGNMENT);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JLabel dateTime = new JLabel("Date/Time: " + sdf.format(new Date()));
        dateTime.setFont(new Font("SansSerif", Font.PLAIN, 13));
        dateTime.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel orderTypeLabel = new JLabel("Order Type: " + orderType);
        orderTypeLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        orderTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel paymentLabel = new JLabel("Payment: " + paymentMethod);
        paymentLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        paymentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea orderArea = new JTextArea();
        orderArea.setEditable(false);
        orderArea.setOpaque(false);
        orderArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        orderArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-18s %5s %8s\n", "Item", "Qty", "Price"));
        sb.append("--------------------------------------\n");
        double total = 0.0;
        for (Map.Entry<Integer, Integer> entry : cartManager.getCartItems().entrySet()) {
            MenuData.MenuItem item = MenuData.ITEMS.get(entry.getKey());
            if (item != null) {
                double line = item.price * entry.getValue();
                sb.append(String.format("%-18s %5d %8.2f\n", item.name, entry.getValue(), line));
                total += line;
            }
        }
        sb.append("--------------------------------------\n");
        sb.append(String.format("%-18s %13.2f\n", "TOTAL:", total));
        orderArea.setText(sb.toString());

        JButton doneBtn = styledButton("Done", new Color(76, 175, 80), Color.WHITE);
        doneBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        doneBtn.addActionListener(e -> {
            cartManager.clearCart();
            if (onOrderComplete != null) onOrderComplete.run();
            dispose();
        });

        panel.add(shopName);
        panel.add(shopLoc);
        panel.add(Box.createVerticalStrut(5));
        panel.add(dateTime);
        panel.add(orderTypeLabel);
        panel.add(paymentLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(orderArea);
        panel.add(Box.createVerticalStrut(10));
        panel.add(doneBtn);

        setContentPane(panel);
        revalidate();
        repaint();
    }

    private JButton styledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(250, 45));
        return btn;
    }

    // Soft vertical gradient panel
    static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(
                0, 0, new Color(255, 255, 255),
                0, getHeight(), new Color(230, 240, 255)
            );
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
