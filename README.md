# Kuwago Cafe POS System

A Java Swing-based desktop application designed as a Point of Sale (POS) system for Kuwago Cafe. The application allows users to browse the menu, filter items by category, add items to a cart (with size options for beverages), and complete orders with payment and order type selection.

## Features
- **Fullscreen Interface**: Launches in a non-resizable fullscreen window for a kiosk-like experience.
- **Menu Display**: Shows four 250x250 cards per row, displaying item images (e.g., `tapsilog.png`) or text (name, price, description) if images are unavailable.
- **Categories**: Supports filtering by:
  - BEST SELLERS
  - SNACKS
  - WAFFOWLS
  - BEVERAGE
  - RICE MEALS
  - MILK TEA
  - FRUIT TEA
  - CREAM CHEESE
  - YAKULT
  - COFFEE
- **Cart Management**: Add/remove items, select sizes (Owlet/Medium, Owl/Large) for beverages, and view total price.
- **Checkout Process**: Select payment method (Cash or Cashless), order type (Dine In or Take Out), and view a receipt with order details.
- **Image Support**: Loads item images from `src/main/resources/assets/` (e.g., `tapsilog.png`, `cheesy_chicken.png`).

## Project Structure
```
KuwagoCafePOS/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── MyAppv3.java          # Main entry point, sets up fullscreen window
│   │   │   ├── MenuData.java         # Defines menu items, prices, and image paths
│   │   │   ├── HomePage.java         # Main UI with cards, categories, and cart
│   │   │   ├── CheckoutDialog.java   # Checkout dialog for payment and receipt
│   │   │   ├── CartManager.java      # Manages cart items and price calculations
│   │   ├── resources/
│   │   │   ├── assets/
│   │   │   │   ├── whiteLeaf.png     # Background image for HomePage
│   │   │   │   ├── tapsilog.png      # Menu item images
│   │   │   │   ├── cheesy_chicken.png
│   │   │   │   ├── americano.png
│   │   │   │   └── ... (all images listed in MenuData.java)
├── pom.xml  # Optional: Maven configuration
```

## Setup Instructions
1. **Clone or Download**:
   - Copy the five Java files (`MyAppv3.java`, `MenuData.java`, `HomePage.java`, `CheckoutDialog.java`, `CartManager.java`) into `src/main/java/`.
2. **Add Images**:
   - Place all images referenced in `MenuData.java` (e.g., `tapsilog.png`, `cheesy_chicken.png`, `whiteLeaf.png`) in `src/main/resources/assets/`.
   - Ensure file names match exactly (case-sensitive).

## Usage
1. **Launch**: Run `MyAppv3.java` to start the application in fullscreen mode.
2. **Browse Menu**:
   - Click category buttons (e.g., "RICE MEALS", "MILK TEA") on the left to filter items.
   - Click "← Back" to show all items.
   - Items display as 250x250 cards (four per row) with images or text.
3. **Add to Cart**:
   - Click a card to add an item to the cart.
   - For beverages (e.g., MILK TEA, COFFEE), a dialog prompts for size (Owlet/Medium or Owl/Large).
   - A confirmation message shows (e.g., "Tapsilog added to cart!").
4. **View Cart**:
   - Click "🛒 VIEW CART" in the top bar to show the cart panel.
   - Adjust quantities using "+" and "−" buttons.
5. **Checkout**:
   - Click "Checkout" in the cart panel.
   - Select payment method (Cash or Cashless).
   - Choose order type (Dine In or Take Out).
   - View the receipt with item details, total, and order information.
   - Click "Done" to clear the cart and complete the order, or "Close" to exit.

## Troubleshooting
- **Compilation Error**: "Cannot resolve symbol CartManager" or similar:
  - Ensure all five Java files are in `src/main/java/`.
  - If using a package (e.g., `com.kuwagocafe`), add `package com.kuwagocafe;` to each file and import classes (e.g., `import com.kuwagocafe.MenuData;`).
- **Images Not Loading**: Cards show text instead of images:
  - Verify all images (e.g., `tapsilog.png`) are in `src/main/resources/assets/`.
  - Check console for errors like `Failed to load image for X: ...`.
  - Ensure image names match `MenuData.java` exactly (case-sensitive).
- **Layout Issues**: Cards stretch or misalign:
  - Confirm `HomePage.java` uses `GridLayout(0, 4, 30, 30)` and `cardsScrollPane.setMaximumSize(new Dimension(1090 + 60, Integer.MAX_VALUE))`.
- **Checkout Issues**: Dialog shows incorrect totals or doesn’t appear:
  - Verify `CartManager.getTotalPriceWithSize()` and `CheckoutDialog.showReceiptPage()` logic.
  - Ensure `MenuData.ITEMS` contains all referenced items.
- **Fullscreen Not Working**:
  - Check `MyAppv3.java` uses `GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this)`.

## Notes
- **Image Assets**: Missing images will cause cards to display text (name, price, description) as a fallback.
- **Package Support**: If using a package, add the package declaration to all Java files and update imports accordingly.
- **Extensibility**: To add new menu items, update `MenuData.java` with new `MenuItem` entries and place corresponding images in `assets/`.

For issues or contributions, please contact the project maintainer.