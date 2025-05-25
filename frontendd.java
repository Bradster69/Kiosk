import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import javax.swing.*;

public class frontendd {

    public static void showWelcomeScreen(JFrame frame, Consumer<String> onOrderTypeSelected) {
        System.out.println("Loading Welcome Screen");
        JPanel backgroundPanel = new BackgroundPanel("assets/bg.png");
        backgroundPanel.setLayout(new BorderLayout());

        // LOGO
        ImageIcon logoIcon = new ImageIcon(frontendd.class.getResource("/assets/logo2.png"));
        if (logoIcon.getImage() == null) {
            System.out.println("Logo not found: /assets/logo2.png");
        }
        Image logoImage = (logoIcon.getImage() != null)
            ? logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)
            : new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setOpaque(false);
        logoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));
        logoPanel.add(logoLabel);

        // Top bar (logo only)
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.add(logoPanel, BorderLayout.WEST);

        // Welcome and Tap Labels
        JLabel welcomeLabel = new JLabel("WELCOME TO KUAGO CAFE!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 48));
        welcomeLabel.setForeground(Color.BLACK);

        JLabel tapLabel = new JLabel("Tap Anywhere to Start", SwingConstants.CENTER);
        tapLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        tapLabel.setForeground(Color.BLACK);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tapLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(Box.createVerticalStrut(30));
        textPanel.add(welcomeLabel);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(tapLabel);
        textPanel.add(Box.createVerticalStrut(30));

        // Carousel
        JLabel carouselLabel = new JLabel();
        carouselLabel.setHorizontalAlignment(SwingConstants.CENTER);
        carouselLabel.setVerticalAlignment(SwingConstants.CENTER);
        carouselLabel.setPreferredSize(new Dimension(400, 300));
        carouselLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] assets = {
            "assets/drinks4.png", "assets/drinks5.png", "assets/drinks6.png",
            "assets/drinks7.png", "assets/foods1.png", "assets/foods2.png", "assets/foods3.png"
        };
        final int[] currentIndex = {0};
        ImageIcon firstImage = resizeImage(assets[0], 350, 250);
        System.out.println("First carousel image loaded: " + (firstImage.getImage() != null ? "Yes" : "No"));
        carouselLabel.setIcon(firstImage);

        Timer carouselTimer = new Timer(1500, e -> {
            currentIndex[0] = (currentIndex[0] + 1) % assets.length;
            System.out.println("Switching to image: " + assets[currentIndex[0]]);
            carouselLabel.setIcon(resizeImage(assets[currentIndex[0]], 350, 250));
        });
        carouselTimer.start();

        // Fade effect on WELCOME label
        Timer fadeTimer = new Timer(40, new ActionListener() {
            private float alpha = 1.0f;
            private boolean fadingOut = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += fadingOut ? -0.05f : 0.05f;
                if (alpha <= 0.2f) fadingOut = false;
                else if (alpha >= 1.0f) fadingOut = true;
                welcomeLabel.setForeground(new Color(0f, 0f, 0f, alpha));
            }
        });
        fadeTimer.start();

        // Center content (text + carousel)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setMinimumSize(new Dimension(800, 600));
        centerPanel.add(textPanel);
        centerPanel.add(carouselLabel);

        // Mouse click to go to next screen
        backgroundPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println("Welcome screen clicked, transitioning to Order Options");
                carouselTimer.stop();
                fadeTimer.stop();
                showOrderOptionsScreen(frame, onOrderTypeSelected);
            }
        });

        backgroundPanel.add(topBar, BorderLayout.NORTH);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        frame.setContentPane(backgroundPanel);
        frame.revalidate();
        frame.repaint();
        System.out.println("Welcome Screen set and repainted");
    }

    private static void showOrderOptionsScreen(JFrame frame, Consumer<String> onOrderTypeSelected) {
        System.out.println("Loading Order Options Screen");
        JPanel backgroundPanel = new BackgroundPanel("assets/bg.png");
        backgroundPanel.setLayout(new BorderLayout());

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setOpaque(false);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        JLabel chooseLabel = new JLabel("Please Choose an Option", SwingConstants.CENTER);
        chooseLabel.setFont(new Font("Arial", Font.BOLD, 36));
        chooseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        chooseLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        ImageIcon dineInIcon = new ImageIcon(frontendd.class.getResource("/assets/dine.png"));
        System.out.println("Dine In icon loaded: " + (dineInIcon.getImage() != null ? "Yes" : "No"));
        ImageIcon takeOutIcon = new ImageIcon(frontendd.class.getResource("/assets/take_out.png"));
        System.out.println("Take Out icon loaded: " + (takeOutIcon.getImage() != null ? "Yes" : "No"));

        Image scaledDineIn = (dineInIcon.getImage() != null)
            ? dineInIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)
            : new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Image scaledTakeOut = (takeOutIcon.getImage() != null)
            ? takeOutIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)
            : new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

        JLabel dineInImage = new JLabel(new ImageIcon(scaledDineIn));
        dineInImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel takeOutImage = new JLabel(new ImageIcon(scaledTakeOut));
        takeOutImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton dineInButton = new JButton("Dine In");
        JButton takeOutButton = new JButton("Take Out");

        Font buttonFont = new Font("Arial", Font.PLAIN, 28);
        Dimension buttonSize = new Dimension(200, 70);

        for (JButton button : new JButton[]{dineInButton, takeOutButton}) {
            button.setFont(buttonFont);
            button.setMaximumSize(buttonSize);
            button.setFocusPainted(false);
            button.setBackground(new Color(230, 230, 250));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        dineInButton.addActionListener(e -> {
            System.out.println("Dine In button clicked");
            onOrderTypeSelected.accept("Dine In");
        });
        takeOutButton.addActionListener(e -> {
            System.out.println("Take Out button clicked");
            onOrderTypeSelected.accept("Take Out");
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 350, 100, 330));

        JPanel dineInPanel = new JPanel();
        dineInPanel.setLayout(new BoxLayout(dineInPanel, BoxLayout.Y_AXIS));
        dineInPanel.setOpaque(false);
        dineInPanel.add(dineInImage);
        dineInPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dineInPanel.add(dineInButton);

        JPanel takeOutPanel = new JPanel();
        takeOutPanel.setLayout(new BoxLayout(takeOutPanel, BoxLayout.Y_AXIS));
        takeOutPanel.setOpaque(false);
        takeOutPanel.add(takeOutImage);
        takeOutPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        takeOutPanel.add(takeOutButton);

        buttonPanel.add(dineInPanel);
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(takeOutPanel);

        optionsPanel.add(Box.createVerticalGlue());
        optionsPanel.add(chooseLabel);
        optionsPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        optionsPanel.add(buttonPanel);
        optionsPanel.add(Box.createVerticalGlue());

        backgroundPanel.add(optionsPanel, BorderLayout.CENTER);

        frame.setContentPane(backgroundPanel);
        frame.revalidate();
        frame.repaint();
        System.out.println("Order Options Screen set and repainted");
    }

    private static ImageIcon resizeImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(frontendd.class.getResource("/" + path));
        if (icon.getImage() == null) {
            System.out.println("Image not found: " + path);
            return new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
        }
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}

class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        ImageIcon icon = new ImageIcon(BackgroundPanel.class.getResource("/" + imagePath));
        this.backgroundImage = (icon.getImage() != null) ? icon.getImage() : null;
        if (backgroundImage == null) {
            System.out.println("Background image not loaded: " + imagePath);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}