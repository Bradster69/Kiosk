import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsTab extends JPanel {

    public SettingsTab() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 241, 242));

        // Title label
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Settings panel
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(0, 1, 10, 10));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Example setting: Username
        settingsPanel.add(createSettingField("Username", "Enter your username"));

        // Example setting: Password
        settingsPanel.add(createSettingField("Password", "Enter your password"));

        // Save button
        JButton saveButton = new JButton("Save Settings");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle save settings logic here
                JOptionPane.showMessageDialog(SettingsTab.this, "Settings saved successfully!");
            }
        });
        settingsPanel.add(saveButton);

        add(settingsPanel, BorderLayout.CENTER);
    }

    private JPanel createSettingField(String label, String placeholder) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel settingLabel = new JLabel(label);
        JTextField textField = new JTextField(placeholder);
        textField.setForeground(Color.GRAY);
        
        // Placeholder functionality
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });

        panel.add(settingLabel, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.SOUTH);
        return panel;
    }
}