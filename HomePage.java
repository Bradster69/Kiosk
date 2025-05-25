// Import statements bring in tools (libraries) that the program needs to work.
import java.awt.*; // Tools for creating the visual interface, like colors and layouts.
import java.awt.event.*; // Tools for handling user actions, like clicks.
import java.util.HashMap; // A tool to store pairs of data, like a category name and its button.
import java.util.Map; // A general tool for working with key-value pairs, used by HashMap.
import javax.swing.*; // Tools for building windows, buttons, panels, and other UI elements.
import javax.swing.border.EmptyBorder; // A tool to add empty space (padding) around UI elements.
import javax.swing.border.LineBorder; // A tool to add a colored line border around UI elements.

// This defines the HomePage class, which is the main screen of the app where users see the menu and cart.
public class HomePage extends JPanel {
    // A panel to hold the menu item cards (like product tiles).
    private JPanel cardArea;
    // A storage (HashMap) to keep track of each menu item card, using its ID (index) as the key.
    private Map<Integer, JPanel> cards = new HashMap<>();
    // A storage to keep track of category buttons (e.g., "Waffowls"), using category names as keys.
    private Map<String, JButton> catButtons = new HashMap<>();
    // A list of all category names shown in the app (e.g., "BEST SELLERS", "WAFFOWLS").
    private static final String[] CATEGORIES = {
        "BEST SELLERS", "SNACKS", "WAFFOWLS", "BEVERAGE", "RICE MEALS", "MILK TEA",
        "FRUIT TEA", "CREAM CHEESE", "YAKULT", "COFFEE"
    };
    // A storage that maps display names (e.g., "BEST SELLERS") to internal names (e.g., "BESTSELLERS").
    private static final Map<String, String> CATEGORY_MAP = new HashMap<>();
    // A storage that links each category to its image file (e.g., "WAFFOWLS" to "assets/cheesy_chicken.png").
    private static final Map<String, String> CATEGORY_IMAGES = new HashMap<>();
    // This block runs once when the program starts, filling the CATEGORY_MAP and CATEGORY_IMAGES.
    static {
        // Links display names to internal category names used in MenuData.
        CATEGORY_MAP.put("BEST SELLERS", "BESTSELLERS");
        CATEGORY_MAP.put("WAFFOWLS", "WAFFOWLS");
        CATEGORY_MAP.put("SNACKS", "SNACKS");
        CATEGORY_MAP.put("BEVERAGE", "BEVERAGE");
        CATEGORY_MAP.put("RICE MEALS", "RICEMEALS");
        CATEGORY_MAP.put("MILK TEA", "MILKTEA");
        CATEGORY_MAP.put("FRUIT TEA", "FRUITTEA");
        CATEGORY_MAP.put("CREAM CHEESE", "CREAMCHEESE");
        CATEGORY_MAP.put("YAKULT", "YAKULT");
        CATEGORY_MAP.put("COFFEE", "COFFEE");

        // Assigns an image file to each category for its button (most use "cheesy_chicken.png").
        CATEGORY_IMAGES.put("BEST SELLERS", "aa");
        CATEGORY_IMAGES.put("SNACKS", "aa");
        CATEGORY_IMAGES.put("WAFFOWLS", "aa");
        CATEGORY_IMAGES.put("BEVERAGE", "aa");
        CATEGORY_IMAGES.put("RICE MEALS", "aa");
        CATEGORY_IMAGES.put("MILK TEA", "aa");
        CATEGORY_IMAGES.put("FRUIT TEA", "aa");
        CATEGORY_IMAGES.put("CREAM CHEESE", "aa.png");
        CATEGORY_IMAGES.put("YAKULT", "assets/aa.png");
        CATEGORY_IMAGES.put("COFFEE", "assets/aa.png");
    }
    // A panel to hold the list of items in the cart.
    private JPanel cartList;
    // A text label showing the total price of items in the cart (e.g., "Total: ₱0.00").
    private JLabel totalText;
    // A number to store the total price of the cart.
    private double total;
    // The cart manager that handles adding/removing items and calculating the total.
    private CartManager cart;
    // The left panel that holds the category buttons.
    private JPanel leftPanel;
    // A special panel with rounded corners for the category buttons.
    private RoundedPanel catBox;
    // The right-side panel that shows the cart when toggled.
    private JPanel cartPanel;
    // The panel that holds the menu item cards (inside a scrollable area).
    private JPanel cardPanel;
    // A flag to track if the cart is visible (true) or hidden (false).
    private boolean isCartVisible = false;
    // The background image for the entire screen (light wood texture).
    private ImageIcon bgImage;
    // Keeps track of the currently selected category button for highlighting.
    private JButton selectedButton = null;

    // The constructor sets up the HomePage when the app starts.
    public HomePage() {
        // Sets the layout to BorderLayout, which organizes components in 5 areas (North, South, East, West, Center).
        setLayout(new BorderLayout());
        // Makes the panel transparent so the background image can show through.
        setOpaque(false);

        // Loads the background image (light_wood_texture.png) from the assets folder.
        bgImage = new ImageIcon(getClass().getResource("assets/light_wood_texture.png")); // Optional texture
        // Checks if the image loaded successfully.
        if (bgImage.getImage() == null) {
            // If the image didn’t load, prints a warning and sets a beige background color.
            System.out.println("Warning: Background image not found, using fallback color");
            setBackground(new Color(230, 215, 190)); // Light Beige
        }

        // Creates a new CartManager to handle the cart’s items and prices.
        cart = new CartManager();

        // Creates a top bar (with the app name and cart button) and adds it to the top (North) of the screen.
        TopBar topBar = new TopBar();
        add(topBar, BorderLayout.NORTH);

        // Creates the left panel to hold category buttons.
        leftPanel = new JPanel();
        // Uses BorderLayout to organize the category buttons inside the left panel.
        leftPanel.setLayout(new BorderLayout());
        // Makes the left panel transparent to show the background.
        leftPanel.setOpaque(false);
        // Adds 10 pixels of empty space around the left panel for padding.
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        // Sets the left panel’s width to 250 pixels (height adjusts automatically).
        leftPanel.setPreferredSize(new Dimension(250, 0));

        // Creates a rounded panel (catBox) to hold the category buttons.
        catBox = new RoundedPanel(20);
        // Arranges category buttons vertically (one on top of another).
        catBox.setLayout(new BoxLayout(catBox, BoxLayout.Y_AXIS));
        // Sets a semi-transparent beige background for the category box.
        catBox.setBackground(new Color(230, 215, 190, 200)); // Semi-transparent light beige
        // Adds padding (20px top, left, bottom; 40px right) inside the category box.
        catBox.setBorder(new EmptyBorder(20, 20, 20, 40)); // Further increased right padding to 40

        // Creates a "Back" button to show all menu items (no category filter).
        JButton backButton = new JButton("← Back");
        // Aligns the button to the left within the vertical layout.
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Sets the button size to 200x100 pixels.
        backButton.setPreferredSize(new Dimension(200, 100));
        backButton.setMinimumSize(new Dimension(200, 100));
        backButton.setMaximumSize(new Dimension(200, 100));
        // Sets a mustard yellow background for the button.
        backButton.setBackground(new Color(180, 140, 80)); // Mustard Yellow
        // Uses a bold, 12-point font for the button text.
        backButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        // Removes the focus outline when the button is clicked.
        backButton.setFocusPainted(false);
        // Makes the button fully opaque (not transparent).
        backButton.setOpaque(true);
        // Adds a dark brown border around the button.
        backButton.setBorder(BorderFactory.createLineBorder(new Color(70, 45, 30), 1)); // Dark Brown border

        // Defines what happens when the "Back" button is clicked.
        backButton.addActionListener(e -> {
            // Highlights the "Back" button as selected.
            updateButtonSelection(backButton);
            // Shows all menu items (no category filter).
            showCards(null);
        });

        // Adds the "Back" button to the category box.
        catBox.add(backButton);
        // Adds 20 pixels of vertical space below the "Back" button.
        catBox.add(Box.createVerticalStrut(20));

        // Loops through each category (e.g., "WAFFOWLS", "SNACKS") to create buttons.
        for (String cat : CATEGORIES) {
            // Creates a category button with a specific color based on the category.
            JButton btn = makeCatButton(cat, getCatColor(cat));
            // Defines what happens when the category button is clicked.
            btn.addActionListener(e -> {
                // Highlights the clicked category button.
                updateButtonSelection(btn);
                // Shows only the menu items for the selected category.
                showCards(cat);
            });
            // Adds the category button to the category box.
            catBox.add(btn);
            // Adds 20 pixels of vertical space below the button.
            catBox.add(Box.createVerticalStrut(20));
        }

        // Creates a scrollable area for the category buttons (if there are too many to fit).
        JScrollPane catScroll = new JScrollPane(catBox);
        // Removes the border around the scroll pane for a cleaner look.
        catScroll.setBorder(null);
        // Disables horizontal scrolling (only vertical scrolling allowed).
        catScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Enables vertical scrolling when needed.
        catScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        // Makes the scroll pane transparent.
        catScroll.setOpaque(false);
        // Makes the scrollable content area transparent.
        catScroll.getViewport().setOpaque(false);
        // Makes scrolling smoother by moving 16 pixels per scroll step.
        catScroll.getVerticalScrollBar().setUnitIncrement(16);

        // Adds the scrollable category box to the left panel’s center.
        leftPanel.add(catScroll, BorderLayout.CENTER);
        // Adds the left panel to the west (left side) of the main screen.
        add(leftPanel, BorderLayout.WEST);

        // Creates a panel to hold the menu item cards.
        cardPanel = new JPanel(new BorderLayout());
        // Makes the card panel transparent to show the background.
        cardPanel.setOpaque(false);
        // Adds 30px padding (top, left, bottom) and 0px right padding.
        cardPanel.setBorder(new EmptyBorder(30, 30, 30, 0));

        // Creates the area where menu item cards are displayed in a 4-column grid.
        cardArea = new JPanel(new GridLayout(0, 4, 30, 30));
        // Makes the card area transparent.
        cardArea.setOpaque(false);

        // Initializes the storage for menu item cards.
        cards = new HashMap<>();
        // Creates a card for each menu item in MenuData and stores it.
        for (Integer index : MenuData.ITEMS.keySet()) {
            JPanel card = makeCard(index);
            cards.put(index, card);
        }
        // Shows all menu items when the app starts (no category selected).
        showCards(null);

        // Creates a scrollable area for the menu item cards.
        JScrollPane cardsScrollPane = new JScrollPane(cardArea);
        // Removes the border for a cleaner look.
        cardsScrollPane.setBorder(null);
        // Disables horizontal scrolling.
        cardsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Makes scrolling smoother (16 pixels per step).
        cardsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        // Makes the scroll pane transparent.
        cardsScrollPane.setOpaque(false);
        // Makes the scrollable content area transparent.
        cardsScrollPane.getViewport().setOpaque(false);

        // Adds the scrollable card area to the center of the card panel.
        cardPanel.add(cardsScrollPane, BorderLayout.CENTER);

        // Creates the cart panel that appears on the right when toggled.
        cartPanel = new JPanel(new BorderLayout());
        // Sets a semi-transparent beige background for the cart.
        cartPanel.setBackground(new Color(230, 215, 190, 230)); // Semi-transparent light beige
        // Adds a dark brown border with 20px padding inside.
        cartPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 45, 30), 1, true), // Dark Brown border
            new EmptyBorder(20, 10, 20, 10)
        ));
        // Hides the cart panel when the app starts.
        cartPanel.setVisible(false);

        // Creates a label for the cart title with a cart icon.
        JLabel cartTitle = new JLabel("🛒 Cart");
        // Sets a bold, 16-point font for the title.
        cartTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        // Sets the title text color to dark brown.
        cartTitle.setForeground(new Color(70, 45, 30)); // Dark Brown
        // Adds 10px padding below the title.
        cartTitle.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Creates a panel to hold the list of cart items.
        cartList = new JPanel();
        // Arranges cart items vertically.
        cartList.setLayout(new BoxLayout(cartList, BoxLayout.Y_AXIS));
        // Makes the cart list transparent.
        cartList.setOpaque(false);

        // Creates a scrollable area for the cart items.
        JScrollPane cartScroll = new JScrollPane(cartList);
        // Removes the border for a cleaner look.
        cartScroll.setBorder(null);
        // Sets the scrollable area size to 230x350 pixels.
        cartScroll.setPreferredSize(new Dimension(230, 350));
        // Disables horizontal scrolling.
        cartScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Makes the scroll pane transparent.
        cartScroll.setOpaque(false);
        // Makes the scrollable content area transparent.
        cartScroll.getViewport().setOpaque(false);

        // Creates a panel for the cart’s top section (title).
        JPanel cartTop = new JPanel();
        // Makes the top panel transparent.
        cartTop.setOpaque(false);
        // Uses BorderLayout to position the title.
        cartTop.setLayout(new BorderLayout());
        // Adds the cart title to the left (West) of the top panel.
        cartTop.add(cartTitle, BorderLayout.WEST);

        // Adds the top panel (with title) to the top (North) of the cart panel.
        cartPanel.add(cartTop, BorderLayout.NORTH);
        // Adds the scrollable cart items to the center of the cart panel.
        cartPanel.add(cartScroll, BorderLayout.CENTER);

        // Creates a panel for the cart’s bottom section (total and checkout button).
        JPanel cartBottom = new JPanel();
        // Makes the bottom panel transparent.
        cartBottom.setOpaque(false);
        // Arranges components vertically.
        cartBottom.setLayout(new BoxLayout(cartBottom, BoxLayout.Y_AXIS));
        // Adds 10px padding at the top.
        cartBottom.setBorder(new EmptyBorder(10, 0, 0, 0));

        // Creates a label to show the cart’s total price (starts at ₱0.00).
        totalText = new JLabel("Total: ₱0.00");
        // Sets a bold, 14-point font for the total text.
        totalText.setFont(new Font("SansSerif", Font.BOLD, 14));
        // Sets the total text color to dark brown.
        totalText.setForeground(new Color(70, 45, 30)); // Dark Brown
        // Centers the total text horizontally.
        totalText.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Creates a "Checkout" button to proceed with payment.
        JButton checkoutBtn = new JButton("Checkout");
        // Centers the button horizontally.
        checkoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Removes the focus outline when clicked.
        checkoutBtn.setFocusPainted(false);
        // Sets a mustard yellow background.
        checkoutBtn.setBackground(new Color(180, 140, 80)); // Mustard Yellow
        // Sets white text color.
        checkoutBtn.setForeground(Color.WHITE);
        // Limits the button size to 200x40 pixels.
        checkoutBtn.setMaximumSize(new Dimension(200, 40));
        // Defines what happens when the "Checkout" button is clicked.
        checkoutBtn.addActionListener(e -> openCheckout());

        // Adds the total text to the bottom panel.
        cartBottom.add(totalText);
        // Adds 10 pixels of vertical space below the total text.
        cartBottom.add(Box.createVerticalStrut(10));
        // Adds the checkout button to the bottom panel.
        cartBottom.add(checkoutBtn);

        // Adds the bottom panel (total and checkout) to the bottom (South) of the cart panel.
        cartPanel.add(cartBottom, BorderLayout.SOUTH);

        // Creates a layered pane to stack the card panel and cart panel (cart appears on top when visible).
        JLayeredPane layeredPane = new JLayeredPane();
        // Sets a manual layout (null) to position components by exact coordinates.
        layeredPane.setLayout(null);
        // Adds the card panel to the default layer (bottom).
        layeredPane.add(cardPanel, JLayeredPane.DEFAULT_LAYER);
        // Adds the cart panel to a higher layer (appears above cards when visible).
        layeredPane.add(cartPanel, JLayeredPane.PALETTE_LAYER);
        // Adds the layered pane to the center of the main screen.
        add(layeredPane, BorderLayout.CENTER);

        // Listens for window resizing to adjust the card and cart panel sizes.
        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateBounds();
            }
        });

        // Sets up the cart toggle button in the top bar to show/hide the cart.
        topBar.setCartToggleListener(e -> {
            // Prints a message to confirm the cart button was clicked.
            System.out.println("Cart button clicked, toggling cart");
            // Shows or hides the cart.
            toggleCart();
        });

        // Ensures the panel sizes are set correctly after the app starts.
        SwingUtilities.invokeLater(this::updateBounds);
    }

    // Adjusts the sizes and positions of the card and cart panels when the window changes size.
    private void updateBounds() {
        // Gets the layered pane that holds the card and cart panels.
        JLayeredPane layeredPane = (JLayeredPane) cartPanel.getParent();
        // Gets the current width of the layered pane (window width minus left panel).
        int width = layeredPane.getWidth();
        // Gets the current height of the layered pane.
        int height = layeredPane.getHeight();
        // Sets the cart panel width to 250 pixels.
        int cartWidth = 250;

        // Sets the card panel to fill the entire layered pane area.
        cardPanel.setBounds(0, 0, width, height);
        // If the cart is visible, positions it on the right side.
        if (isCartVisible) {
            cartPanel.setBounds(width - cartWidth, 0, cartWidth, height);
        }
        // Adjusts the card area’s size based on the available width.
        updateCardAreaSize(width);
        // Refreshes the layered pane to apply the new sizes.
        layeredPane.revalidate();
        // Redraws the layered pane to show the changes.
        layeredPane.repaint();
    }

    // Adjusts the card area’s size to fit the window and arrange cards properly.
    private void updateCardAreaSize(int availableWidth) {
        // Each card is 250 pixels wide.
        int cardWidth = 250;
        // The gap between cards is 30 pixels.
        int gap = 30;
        // Calculates how many cards can fit in the available width (at least 1).
        int cardsPerRow = Math.max(1, (availableWidth - 30) / (cardWidth + gap));
        // Calculates the total width needed for the cards and gaps.
        int requiredWidth = cardsPerRow * cardWidth + (cardsPerRow - 1) * gap;
        // Uses the smaller of the available width or required width to avoid overflow.
        int adjustedWidth = Math.min(availableWidth - 30, requiredWidth);
        // Sets the card area’s width and keeps its current height.
        cardArea.setPreferredSize(new Dimension(adjustedWidth, cardArea.getPreferredSize().height));
        // Allows the card area to grow taller but not wider.
        cardArea.setMaximumSize(new Dimension(adjustedWidth, Integer.MAX_VALUE));
        // Ensures the card area is at least 250 pixels tall.
        cardArea.setMinimumSize(new Dimension(adjustedWidth, 250));
        // Sets the card area to a grid layout with the calculated number of columns.
        cardArea.setLayout(new GridLayout(0, cardsPerRow, gap, 30));
        // Refreshes the card area to apply the new layout.
        cardArea.revalidate();
    }

    // Highlights the selected category button and unhighlights the previous one.
    private void updateButtonSelection(JButton selected) {
        // If there was a previously selected button, reset its border to dark brown.
        if (selectedButton != null) {
            selectedButton.setBorder(BorderFactory.createLineBorder(new Color(70, 45, 30), 1)); // Dark Brown
        }
        // Sets the new selected button.
        selectedButton = selected;
        // Highlights the selected button with a thicker mustard yellow border.
        selectedButton.setBorder(BorderFactory.createLineBorder(new Color(180, 140, 80), 2)); // Mustard Yellow
    }

    // Draws the background image or a fallback beige color for the HomePage.
    @Override
    protected void paintComponent(Graphics g) {
        // Calls the default drawing method to ensure proper rendering.
        super.paintComponent(g);
        // Checks if the background image exists and is valid.
        if (bgImage != null && bgImage.getImage() != null) {
            // Gets the image from the ImageIcon.
            Image image = bgImage.getImage();
            // Scales the image to fit the panel’s size smoothly.
            Image scaled = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            // Draws the scaled image at position (0,0).
            g.drawImage(scaled, 0, 0, this);
        } else {
            // If no image, uses a Graphics2D object for drawing.
            Graphics2D g2 = (Graphics2D) g;
            // Sets a beige color as the fallback.
            g2.setColor(new Color(230, 215, 190)); // Light Beige
            // Fills the entire panel with the beige color.
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // Shows or hides the cart panel when the cart button is clicked.
    private void toggleCart() {
        // Toggles the visibility flag (true to false, or false to true).
        isCartVisible = !isCartVisible;
        // Sets the cart panel’s visibility based on the flag.
        cartPanel.setVisible(isCartVisible);
        // Updates the panel sizes to position the cart correctly.
        updateBounds();
        // Refreshes the cart panel.
        cartPanel.revalidate();
        // Redraws the cart panel to show or hide it.
        cartPanel.repaint();
    }

    // Returns a color for a category button based on the category name.
    private Color getCatColor(String cat) {
        // Uses a switch statement to assign colors to categories.
        return switch (cat) {
            case "BEST SELLERS" -> new Color(70, 45, 30); // Dark Brown
            case "SNACKS" -> new Color(102, 68, 43); // Medium Brown
            case "BEVERAGE" -> new Color(135, 90, 60); // Rich Brown
            case "WAFFOWLS" -> new Color(180, 140, 80); // Mustard Yellow
            case "RICE MEALS" -> new Color(230, 215, 190); // Light Beige
            case "MILK TEA" -> new Color(70, 45, 30); // Dark Brown
            case "FRUIT TEA" -> new Color(102, 68, 43); // Medium Brown
            case "CREAM CHEESE" -> new Color(135, 90, 60); // Rich Brown
            case "YAKULT" -> new Color(180, 140, 80); // Mustard Yellow
            case "COFFEE" -> new Color(230, 215, 190); // Light Beige
            default -> new Color(230, 215, 190); // Light Beige (fallback)
        };
    }

    // Displays menu item cards for a specific category (or all items if category is null).
    private void showCards(String category) {
        // Clears the card area to show new cards.
        cardArea.removeAll();
        // Gets the internal category name (e.g., "WAFFOWLS" for "WAFFOWLS").
        String mappedCategory = category == null ? null : CATEGORY_MAP.get(category);
        // Loops through all menu items in MenuData.
        for (Map.Entry<Integer, MenuData.MenuItem> entry : MenuData.ITEMS.entrySet()) {
            // Gets the item’s ID (index).
            Integer index = entry.getKey();
            // Gets the item’s details (name, price, etc.).
            MenuData.MenuItem item = entry.getValue();
            // Skips if the item is missing.
            if (item == null) {
                System.out.println("Warning: Null MenuItem at index " + index);
                continue;
            }
            // Shows the item if no category is selected or if it matches the selected category.
            if (mappedCategory == null || item.category.equalsIgnoreCase(mappedCategory)) {
                // Gets the card for this item from the stored cards.
                JPanel card = cards.get(index);
                // Adds the card to the card area if it exists.
                if (card != null) {
                    cardArea.add(card);
                } else {
                    System.out.println("Warning: No card found for index " + index);
                }
            }
        }
        // Counts how many cards are displayed.
        int cardCount = cardArea.getComponentCount();
        // Calculates the number of rows needed (4 cards per row).
        int rows = (int) Math.ceil((double) cardCount / 4);
        // Each row is 250 pixels (card height) plus 30 pixels (gap).
        int rowHeight = 250 + 30;
        // Sets the card area’s height based on the number of rows.
        cardArea.setPreferredSize(new Dimension(cardArea.getPreferredSize().width, rows * rowHeight));
        // Refreshes the card area to show the new cards.
        cardArea.revalidate();
        // Redraws the card area to display the changes.
        cardArea.repaint();
    }

    // Creates a button for a category (e.g., "WAFFOWLS") with an image or text.
    private JButton makeCatButton(String label, Color color) {
        // Creates a new button.
        JButton button = new JButton();
        // Aligns the button to the left in the vertical layout.
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Sets the button size to 200x100 pixels.
        button.setPreferredSize(new Dimension(200, 100));
        button.setMinimumSize(new Dimension(200, 100));
        button.setMaximumSize(new Dimension(200, 100));
        // Sets the button’s background color (e.g., mustard yellow for "WAFFOWLS").
        button.setBackground(color);
        // Removes the focus outline when clicked.
        button.setFocusPainted(false);
        // Makes the button opaque (not transparent).
        button.setOpaque(true);
        // Adds a dark brown border.
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 45, 30), 1)); // Dark Brown
        // Changes the cursor to a hand when hovering over the button.
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Adds a hover effect to brighten the button when the mouse moves over it.
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        // Gets the image path for the category (e.g., "assets/cheesy_chicken.png").
        String imagePath = CATEGORY_IMAGES.get(label);
        // If an image path exists, tries to load the image.
        if (imagePath != null) {
            try {
                // Loads the image from the assets folder.
                ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
                // Gets the original image.
                Image originalImage = icon.getImage();
                // Gets the image’s original width and height.
                int imgWidth = originalImage.getWidth(null);
                int imgHeight = originalImage.getHeight(null);
                // Sets the target size for the button (200x100 pixels).
                int targetWidth = 200;
                int targetHeight = 100;
                // Calculates the scaling factor to fit the image in the button.
                double scale = Math.min((double) targetWidth / imgWidth, (double) targetHeight / imgHeight);
                // Calculates the scaled image size.
                int scaledWidth = (int) (imgWidth * scale);
                int scaledHeight = (int) (imgHeight * scale);
                // Scales the image smoothly to the new size.
                Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                // Sets the scaled image as the button’s icon.
                button.setIcon(new ImageIcon(scaledImage));
                // Centers the image horizontally and vertically.
                button.setHorizontalAlignment(SwingConstants.CENTER);
                button.setVerticalAlignment(SwingConstants.CENTER);
            } catch (Exception e) {
                // If the image fails to load, prints an error and uses text instead.
                System.out.println("Failed to load category image for " + label + ": " + e.getMessage());
                button.setText(label);
                button.setFont(new Font("SansSerif", Font.BOLD, 12));
                button.setForeground(new Color(70, 45, 30)); // Dark Brown
            }
        } else {
            // If no image path, uses the category name as text.
            button.setText(label);
            // Sets a bold, 12-point font.
            button.setFont(new Font("SansSerif", Font.BOLD, 12));
            // Sets dark brown text color.
            button.setForeground(new Color(70, 45, 30)); // Dark Brown
        }

        // Returns the finished button.
        return button;
    }

    // Creates a card (tile) for a menu item (e.g., a Waffowl item).
    private JPanel makeCard(int index) {
        // Creates a standard panel for the card (not rounded, unlike previous versions).
        JPanel card = new JPanel(); // Changed from RoundedPanel to JPanel to remove rounded corners
        // Sets the card size to 250x250 pixels.
        card.setPreferredSize(new Dimension(250, 250));
        card.setMinimumSize(new Dimension(250, 250));
        card.setMaximumSize(new Dimension(250, 250));
        // Sets a beige background for the card.
        card.setBackground(new Color(230, 215, 190)); // Light Beige
        // Adds a mustard yellow border.
        card.setBorder(new LineBorder(new Color(180, 140, 80), 1, true)); // Mustard Yellow
        // Changes the cursor to a hand when hovering over the card.
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Gets the menu item details from MenuData using the item’s ID (index).
        MenuData.MenuItem item = MenuData.ITEMS.get(index);
        // If the item is missing, shows an error message on the card.
        if (item == null) {
            System.out.println("Error: No MenuItem found for index " + index);
            JLabel errorLabel = new JLabel("Item not available");
            errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            errorLabel.setForeground(new Color(70, 45, 30)); // Dark Brown
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            card.setLayout(new BorderLayout());
            card.add(errorLabel, BorderLayout.CENTER);
            return card;
        }

        // Creates a label to hold the item’s image.
        JLabel imageLabel;
        try {
            // Loads the item’s image (e.g., "assets/cheesy_chicken.png").
            ImageIcon icon = new ImageIcon(getClass().getResource(item.imagePath));
            // Gets the original image.
            Image originalImage = icon.getImage();
            // Gets the image’s original width and height.
            int imgWidth = originalImage.getWidth(null);
            int imgHeight = originalImage.getHeight(null);
            // Sets the target size for the image (250 pixels).
            int targetSize = 250;
            // Calculates the scaling factor to fit the image in the card.
            double scale = Math.min((double) targetSize / imgWidth, (double) targetSize / imgHeight);
            // Calculates the scaled image size.
            int scaledWidth = (int) (imgWidth * scale);
            int scaledHeight = (int) (imgHeight * scale);
            // Scales the image smoothly to the new size.
            Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            // Creates a label with the scaled image.
            imageLabel = new JLabel(new ImageIcon(scaledImage));
            // Centers the image horizontally and vertically.
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        } catch (Exception e) {
            // If the image fails to load, shows the item’s name and description instead.
            System.out.println("Failed to load image for " + item.name + ": " + e.getMessage());
            JPanel text = new JPanel();
            text.setOpaque(false);
            text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
            text.setBorder(new EmptyBorder(10, 10, 10, 10));

            // Chooses the price to display (regular, medium, or large).
            Integer price = item.Regprice != null ? item.Regprice
                : item.MedPrice != null ? item.MedPrice
                : item.LrgPrice != null ? item.LrgPrice
                : 0;
            // Creates a label with the item’s name and price.
            JLabel name = new JLabel(item.name + "  ₱" + price);
            name.setFont(new Font("SansSerif", Font.BOLD, 16));
            name.setForeground(new Color(70, 45, 30)); // Dark Brown
            name.setAlignmentX(Component.CENTER_ALIGNMENT);
            // Creates a label with the item’s description, centered.
            JLabel desc = new JLabel("<html><div style='text-align: center;'>" + item.description + "</div></html>");
            desc.setFont(new Font("SansSerif", Font.PLAIN, 12));
            desc.setForeground(new Color(70, 45, 30)); // Dark Brown
            desc.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Adds vertical space and the labels to the text panel.
            text.add(Box.createVerticalGlue());
            text.add(name);
            text.add(Box.createVerticalStrut(10));
            text.add(desc);
            text.add(Box.createVerticalGlue());

            // Sets the card to use BorderLayout and adds the text panel.
            card.setLayout(new BorderLayout());
            card.add(text, BorderLayout.CENTER);
            return card;
        }

        // Sets the card to use BorderLayout and adds the image label.
        card.setLayout(new BorderLayout());
        card.add(imageLabel, BorderLayout.CENTER);

        // Adds a click handler to the card to add the item to the cart.
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    // Finds the main window to show dialogs.
                    Window ancestor = SwingUtilities.getWindowAncestor(HomePage.this);
                    // If no window is found, prints an error and stops.
                    if (ancestor == null) {
                        System.out.println("Error: No window ancestor found for HomePage");
                        return;
                    }
                    // If the item has multiple sizes (e.g., drinks), shows a size selection dialog.
                    if (item.Regprice == null && item.MedPrice != null && item.LrgPrice != null) {
                        System.out.println("Showing size selection dialog for item: " + item.name);
                        showSizeSelectionDialog(index, item);
                    } else {
                        // Otherwise, adds the item directly to the cart.
                        System.out.println("Adding item to cart: " + item.name + ", index: " + index);
                        cart.addToCart(index);
                        // Updates the cart display.
                        updateCart();
                        // Shows a confirmation message.
                        JOptionPane.showMessageDialog(
                            ancestor,
                            item.name + " added to cart!",
                            "Added",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                    // Refreshes the left panel (category buttons).
                    leftPanel.revalidate();
                    leftPanel.repaint();
                } catch (Exception ex) {
                    // If an error occurs, prints it and shows an error message.
                    System.out.println("Error in mouseClicked for item " + item.name + ": " + ex.getMessage());
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(HomePage.this),
                        "Error adding item to cart: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Returns the finished card.
        return card;
    }

    // Shows a dialog to select a size (Medium or Large) for items like drinks.
    private void showSizeSelectionDialog(int index, MenuData.MenuItem item) {
        // Creates a panel for the dialog’s content.
        JPanel panel = new JPanel();
        // Arranges components vertically.
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // Sets a beige background for the dialog.
        panel.setBackground(new Color(230, 215, 190)); // Light Beige
        // Adds 15px padding around the content (20px left/right).
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Creates a title label for the dialog (e.g., "Select size for: Blue Lemonade").
        JLabel title = new JLabel("<html><b>Select size for:</b><br>" + item.name + "</html>");
        // Sets a bold, 16-point font.
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        // Sets dark brown text color.
        title.setForeground(new Color(70, 45, 30)); // Dark Brown
        // Centers the title horizontally.
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Creates a button for the medium size (labeled "Owlet").
        JButton owletBtn = new JButton("Owlet (₱" + item.MedPrice + ")");
        // Sets a bold, 15-point font.
        owletBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        // Sets a rich brown background.
        owletBtn.setBackground(new Color(135, 90, 60)); // Rich Brown
        // Sets white text color.
        owletBtn.setForeground(Color.WHITE);
        // Removes the focus outline.
        owletBtn.setFocusPainted(false);

        // Creates a button for the large size (labeled "Owl").
        JButton owlBtn = new JButton("Owl (₱" + item.LrgPrice + ")");
        // Sets a bold, 15-point font.
        owlBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        // Sets a mustard yellow background.
        owlBtn.setBackground(new Color(180, 140, 80)); // Mustard Yellow
        // Sets white text color.
        owlBtn.setForeground(Color.WHITE);
        // Removes the focus outline.
        owlBtn.setFocusPainted(false);

        // Creates a "Cancel" button to close the dialog without selecting.
        JButton cancelBtn = new JButton("Cancel");
        // Sets a plain, 13-point font.
        cancelBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        // Sets a medium brown background.
        cancelBtn.setBackground(new Color(102, 68, 43)); // Medium Brown
        // Sets white text color.
        cancelBtn.setForeground(Color.WHITE);
        // Removes the focus outline.
        cancelBtn.setFocusPainted(false);

        // Adds the title to the dialog panel.
        panel.add(title);
        // Adds 18 pixels of vertical space.
        panel.add(Box.createVerticalStrut(18));
        // Adds the medium size button.
        panel.add(owletBtn);
        // Adds 10 pixels of vertical space.
        panel.add(Box.createVerticalStrut(10));
        // Adds the large size button.
        panel.add(owlBtn);
        // Adds 18 pixels of vertical space.
        panel.add(Box.createVerticalStrut(18));
        // Adds the cancel button.
        panel.add(cancelBtn);

        // Finds the main window to show the dialog.
        Window parentWindow = SwingUtilities.getWindowAncestor(HomePage.this);
        // Creates a modal dialog (blocks other actions until closed).
        final JDialog dialog = new JDialog(parentWindow, "Choose Size", Dialog.ModalityType.APPLICATION_MODAL);
        // Removes the dialog’s default border for a custom look.
        dialog.setUndecorated(true);
        // Adds a mustard yellow border around the dialog.
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(180, 140, 80), 2, true)); // Mustard Yellow
        // Sets the dialog’s content to the panel.
        dialog.setContentPane(panel);

        // Defines what happens when the "Owlet" (medium) button is clicked.
        owletBtn.addActionListener(e -> {
            // Adds the item to the cart with medium size.
            cart.addToCartWithSize(index, "MEDIUM");
            // Updates the cart display.
            updateCart();
            // Closes the dialog.
            dialog.dispose();
            // Shows a confirmation message.
            JOptionPane.showMessageDialog(
                HomePage.this,
                item.name + " (Owlet) added to cart!",
                "Added to Cart",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Defines what happens when the "Owl" (large) button is clicked.
        owlBtn.addActionListener(e -> {
            // Adds the item to the cart with large size.
            cart.addToCartWithSize(index, "LARGE");
            // Updates the cart display.
            updateCart();
            // Closes the dialog.
            dialog.dispose();
            // Shows a confirmation message.
            JOptionPane.showMessageDialog(
                HomePage.this,
                item.name + " (Owl) added to cart!",
                "Added to Cart",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Closes the dialog when the "Cancel" button is clicked.
        cancelBtn.addActionListener(e -> dialog.dispose());

        // Sizes the dialog to fit its content.
        dialog.pack();
        // Centers the dialog relative to the HomePage panel.
        dialog.setLocationRelativeTo(HomePage.this);
        // Shows the dialog.
        dialog.setVisible(true);
    }

    // Updates the cart panel to show the current items, quantities, and total price.
    private void updateCart() {
        // Clears the cart list to rebuild it.
        cartList.removeAll();
        // Resets the total price to 0.
        total = 0.0;
        // Gets all items in the cart from the CartManager.
        Map<CartManager.CartKey, Integer> items = cart.getCartItemsWithSize();
        // Loops through each item in the cart.
        for (Map.Entry<CartManager.CartKey, Integer> entry : items.entrySet()) {
            // Gets the item’s key (index and size).
            CartManager.CartKey key = entry.getKey();
            // Gets the item’s ID (index).
            int id = key.index;
            // Gets the quantity of the item.
            int qty = entry.getValue();
            // Gets the item’s size (e.g., "MEDIUM", "LARGE", or null).
            String size = key.size;
            // Gets the item’s details from MenuData.
            MenuData.MenuItem item = MenuData.ITEMS.get(id);
            // Skips if the item is missing or quantity is 0.
            if (item == null || qty <= 0) continue;

            // Creates a panel for the cart item.
            JPanel itemPanel = new JPanel();
            // Arranges components vertically.
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            // Limits the panel size to 230x100 pixels.
            itemPanel.setMaximumSize(new Dimension(230, 100));
            // Adds a mustard yellow border with 10px padding.
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 140, 80), 1, true), // Mustard Yellow
                new EmptyBorder(10, 10, 10, 10)
            ));
            // Sets a beige background.
            itemPanel.setBackground(new Color(230, 215, 190)); // Light Beige

            // Sets the default price to 0.
            int price = 0;
            // Sets the display name to the item’s name.
            String displayName = item.name;
            // Chooses the price based on the item’s size or regular price.
            if (item.Regprice != null) {
                price = item.Regprice;
            } else if ("MEDIUM".equals(size) && item.MedPrice != null) {
                price = item.MedPrice;
                displayName += " (Medium)";
            } else if ("LARGE".equals(size) && item.LrgPrice != null) {
                price = item.LrgPrice;
                displayName += " (Large)";
            }

            // Creates a label with the item’s name and price.
            JLabel name = new JLabel(displayName + "  ₱" + price);
            // Sets a plain, 12-point font.
            name.setFont(new Font("SansSerif", Font.PLAIN, 12));
            // Sets dark brown text color.
            name.setForeground(new Color(70, 45, 30)); // Dark Brown
            // Centers the label horizontally.
            name.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Creates a panel for the quantity controls (+ and − buttons).
            JPanel qtyPanel = new JPanel();
            // Makes the panel transparent.
            qtyPanel.setOpaque(false);
            // Arranges buttons horizontally with 5px gaps.
            qtyPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            // Creates a "−" button to reduce quantity.
            JButton minus = new JButton("−");
            // Adds small padding inside the button.
            minus.setMargin(new Insets(2, 8, 2, 8));
            // Removes the focus outline.
            minus.setFocusPainted(false);
            // Sets a medium brown background.
            minus.setBackground(new Color(102, 68, 43)); // Medium Brown
            // Sets white text color.
            minus.setForeground(Color.WHITE);
            // Reduces the item’s quantity when clicked.
            minus.addActionListener(e -> {
                cart.removeFromCartWithSize(id, size);
                updateCart();
            });
            // Creates a label showing the current quantity.
            JLabel qtyText = new JLabel(String.valueOf(qty));
            // Sets a bold, 12-point font.
            qtyText.setFont(new Font("SansSerif", Font.BOLD, 12));
            // Sets dark brown text color.
            qtyText.setForeground(new Color(70, 45, 30)); // Dark Brown
            // Creates a "+" button to increase quantity.
            JButton plus = new JButton("+");
            // Adds small padding inside the button.
            plus.setMargin(new Insets(2, 8, 2, 8));
            // Removes the focus outline.
            plus.setFocusPainted(false);
            // Sets a medium brown background.
            plus.setBackground(new Color(102, 68, 43)); // Medium Brown
            // Sets white text color.
            plus.setForeground(Color.WHITE);
            // Increases the item’s quantity when clicked.
            plus.addActionListener(e -> {
                cart.addToCartWithSize(id, size);
                updateCart();
            });

            // Adds the "−" button, quantity text, and "+" button to the quantity panel.
            qtyPanel.add(minus);
            qtyPanel.add(qtyText);
            qtyPanel.add(plus);

            // Adds the item’s name and quantity panel to the item panel.
            itemPanel.add(name);
            itemPanel.add(Box.createVerticalStrut(15));
            itemPanel.add(qtyPanel);

            // Adds the item panel to the cart list.
            cartList.add(itemPanel);
            // Adds 15 pixels of vertical space below the item.
            cartList.add(Box.createVerticalStrut(15));
        }
        // Calculates the total price of all items in the cart.
        total = cart.getTotalPriceWithSize();
        // Updates the total text with the new total (formatted to 2 decimal places).
        totalText.setText(String.format("Total: ₱%.2f", total));
        // Refreshes the cart list.
        cartList.revalidate();
        // Redraws the cart list to show the changes.
        cartList.repaint();
    }

    // Opens the checkout dialog if the cart has items.
    private void openCheckout() {
        // Checks if the cart is empty.
        if (cart.isEmpty()) {
            // Shows a warning if there are no items.
            JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                "Cart is empty!",
                "Checkout",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        // Opens the checkout dialog, passing the main window, cart, and updateCart method.
        new CheckoutDialog(
            SwingUtilities.getWindowAncestor(this),
            cart,
            this::updateCart
        );
    }

    // A custom panel class that draws rounded corners.
    class RoundedPanel extends JPanel {
        // Stores the radius of the rounded corners (e.g., 20 pixels).
        private int radius;

        // Constructor sets the corner radius.
        public RoundedPanel(int radius) {
            this.radius = radius;
            // Makes the panel transparent to show the rounded background.
            setOpaque(false);
        }

        // Draws the panel with rounded corners.
        @Override
        protected void paintComponent(Graphics g) {
            // Calls the default drawing method.
            super.paintComponent(g);
            // Uses Graphics2D for better drawing quality.
            Graphics2D g2 = (Graphics2D) g;
            // Enables smooth edges (anti-aliasing).
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Sets the panel’s background color.
            g2.setColor(getBackground());
            // Draws a rounded rectangle to fill the panel.
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        }
    }

    // A custom panel for the top bar (app name and cart button).
    class TopBar extends JPanel {
        // Stores the listener for the cart toggle button.
        private ActionListener cartToggleListener;

        // Constructor sets up the top bar.
        public TopBar() {
            // Uses BorderLayout to position the app name and cart button.
            setLayout(new BorderLayout());
            // Sets a beige background.
            setBackground(new Color(230, 215, 190)); // Light Beige
            // Sets the top bar height to 70 pixels (width adjusts automatically).
            setPreferredSize(new Dimension(1000, 70));

            // Creates a label with the app name.
            JLabel name = new JLabel("KUWAGO CAFE");
            // Sets a bold, 18-point font.
            name.setFont(new Font("SansSerif", Font.BOLD, 18));
            // Sets dark brown text color.
            name.setForeground(new Color(70, 45, 30)); // Dark Brown

            // Creates a "View Cart" button with a cart icon.
            JButton cartBtn = new JButton("🛒 VIEW CART");
            // Removes the focus outline.
            cartBtn.setFocusPainted(false);
            // Sets a mustard yellow background.
            cartBtn.setBackground(new Color(180, 140, 80)); // Mustard Yellow
            // Sets white text color.
            cartBtn.setForeground(Color.WHITE);
            // Removes the default border.
            cartBtn.setBorderPainted(false);
            // Makes the button opaque.
            cartBtn.setOpaque(true);
            // Defines what happens when the cart button is clicked.
            cartBtn.addActionListener(e -> {
                // Prints a confirmation message.
                System.out.println("Cart button action triggered");
                // If a listener is set, triggers it to toggle the cart.
                if (cartToggleListener != null) {
                    cartToggleListener.actionPerformed(e);
                } else {
                    // Prints a warning if no listener is set.
                    System.out.println("Warning: cartToggleListener is null");
                }
            });

            // Adds the app name to the left (West) of the top bar.
            add(name, BorderLayout.WEST);
            // Adds the cart button to the right (East) of the top bar.
            add(cartBtn, BorderLayout.EAST);
        }

        // Sets the listener for the cart toggle button.
        public void setCartToggleListener(ActionListener listener) {
            this.cartToggleListener = listener;
        }

        // Draws the top bar with a gradient effect at the bottom.
        @Override
        protected void paintComponent(Graphics g) {
            // Calls the default drawing method.
            super.paintComponent(g);
            // Creates a Graphics2D object for drawing.
            Graphics2D g2 = (Graphics2D) g.create();
            // Gets the top bar’s height.
            int h = getHeight();
            // Creates a gradient from beige to transparent at the bottom.
            g2.setPaint(new GradientPaint(0, h - 1, new Color(230, 215, 190, 80), 0, h + 10, new Color(230, 215, 190, 0))); // Light Beige gradient
            // Draws the gradient at the bottom of the top bar.
            g2.fillRect(0, h - 1, getWidth(), 10);
            // Cleans up the Graphics2D object.
            g2.dispose();
        }
    }
}