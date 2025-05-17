import java.util.HashMap;
import java.util.Map;

public class MenuData {
    public static class MenuItem {
        public final String name;
        public final Integer Regprice; // Nullable: only for food or drinks with a regular/small price
        public final Integer MedPrice;   // Owlet (medium), nullable if not available
        public final Integer LrgPrice;   // Owl (large), nullable if not available
        public final String description;
        public final String category;

        // For food or drinks with only one price (Regprice)
        public MenuItem(String name, int Regprice, String description, String category) {
            this.name = name;
            this.Regprice = Regprice;
            this.MedPrice = null;
            this.LrgPrice = null;
            this.description = description;
            this.category = category;
        }

        // For drinks with medium and large prices (no Regprice)
        public MenuItem(String name, Integer MedPrice, Integer LrgPrice, String description, String category) {
            this.name = name;
            this.Regprice = null;
            this.MedPrice = MedPrice;
            this.LrgPrice = LrgPrice;
            this.description = description;
            this.category = category;
        }
    }

    // Static map of menu items
    public static final Map<Integer, MenuItem> ITEMS = new HashMap<>();
    static {
        // Rice meals
        ITEMS.put(0, new MenuItem("Tapsilog", 95, "Tapa with egg and garlic rice", "RICEMEALS"));
        ITEMS.put(1, new MenuItem("Tocilog", 95, "Tocino with egg and garlic rice", "RICEMEALS"));
        ITEMS.put(2, new MenuItem("Porksilog", 100, "Pork with egg and garlic rice", "RICEMEALS"));
        ITEMS.put(3, new MenuItem("Cornsilog", 110, "Corned beef with egg and garlic rice", "RICEMEALS"));
        ITEMS.put(4, new MenuItem("Spamsilog", 115, "Spam meat with egg and garlic rice", "RICEMEALS"));
        ITEMS.put(5, new MenuItem("Chicken in a plate", 95, "Chicken with rice", "RICEMEALS"));
        ITEMS.put(6, new MenuItem("Cheesy Chicken", 105, "Chicken with cheese and rice", "RICEMEALS"));
        ITEMS.put(7, new MenuItem("Chicken Curry", 110, "Chicken curry with rice", "RICEMEALS"));
        ITEMS.put(8, new MenuItem("Chicken Katsudon", 120, "Crispy Chicken with rice", "RICEMEALS"));
        ITEMS.put(9, new MenuItem("Pork Katsu", 120, "Pork with rice", "RICEMEALS"));
        ITEMS.put(10, new MenuItem("Pork Gyudon", 120, "Pork something with rice", "RICEMEALS"));

        //Snacks
        ITEMS.put(11, new MenuItem("Cheesy Fries", 60, "Fries with cheese", "SNACKS"));
        ITEMS.put(12, new MenuItem("BBQ Fries", 60, "Fries with BBQ flavor", "SNACKS"));
        ITEMS.put(13, new MenuItem("SourCream Fries", 60, "Fries with Sour Cream", "SNACKS"));
        ITEMS.put(14, new MenuItem("Mojos", 75, "Potato Mojos with sauce", "SNACKS"));
        ITEMS.put(15, new MenuItem("Nachos", 85, "Nachos with cheese and sauce", "SNACKS"));
        ITEMS.put(16, new MenuItem("Clubhouse", 110, "Clubhouse Sandwich", "SNACKS"));

        //Waff-Owls
        ITEMS.put(17, new MenuItem("Ham & cheese w/ egg", 90, "Ham and cheese with egg", "WAFFOWLS"));
        ITEMS.put(18, new MenuItem("Bacon & cheese w/ egg", 90, "Bacon and cheese with egg", "WAFFOWLS"));
        ITEMS.put(19, new MenuItem("Combo K1", 280, "Fries, Nachos, Mojos, 1L Lemonade", "WAFFOWLS"));

        // Beverages
        ITEMS.put(20, new MenuItem("Blue Lemonade", 35, 45, "Blue Lemonade", "BEVERAGE"));
        ITEMS.put(21, new MenuItem("Pink Lemonade", 35, 45,"Pink Lemonade", "BEVERAGE"));
        ITEMS.put(22, new MenuItem("Red Iced Tea", 35, 45, "Sweet Red Iced Tea", "BEVERAGE"));
        ITEMS.put(23, new MenuItem("Lemonade pitcher", 100, "Lemonade pitcher", "BEVERAGE"));
        ITEMS.put(24, new MenuItem("Bottled Water", 20, "Bottled water", "BEVERAGE"));
        
        // MilkTea Series(Owlet = Med, Owl = Lrg), no Regprice
        ITEMS.put(25, new MenuItem("Cheese Cake", 70, 85, "Cheese cake flavored milktea", "MILKTEA"));
        ITEMS.put(26, new MenuItem("Chocolate", 70, 85, "Choco flavored milk tea", "MILKTEA"));
        ITEMS.put(27, new MenuItem("Cookies & Cream", 70, 85, "Cookies & Cream flavored milk tea", "MILKTEA"));
        ITEMS.put(28, new MenuItem("Dark Choco", 70, 85, "Dark Chocolate flavored milk tea", "MILKTEA"));
        ITEMS.put(29, new MenuItem("Matcha", 70, 85, "Matcha flavored milk tea", "MILKTEA"));
        ITEMS.put(30, new MenuItem("Okinawa", 70, 85, "Okinawa flavored milk tea", "MILKTEA"));
        ITEMS.put(31, new MenuItem("Taro", 70, 85, "Taro flavored milk tea", "MILKTEA"));
        ITEMS.put(32, new MenuItem("Red Velvet", 70, 85, "Red Velvet flavored milk tea", "MILKTEA"));
        ITEMS.put(33, new MenuItem("Wintermelon", 70, 85, "Wintermelon flavored milk tea", "MILKTEA"));

        //FruitTea Series(Owlet = Med, Owl = Lrg), no Regprice
        ITEMS.put(34, new MenuItem("Blueberry", 70, 85, "Blueberry flavored fruit tea", "FRUITTEA"));
        ITEMS.put(35, new MenuItem("Green Apple", 70, 85, "Green Apple flavored fruit tea", "FRUITTEA"));
        ITEMS.put(36, new MenuItem("Lemon", 70, 85, "Lemon flavored fruit tea", "FRUITTEA"));
        ITEMS.put(37, new MenuItem("Lychee", 70, 85, "Lychee flavored fruit tea", "FRUITTEA"));
        ITEMS.put(38, new MenuItem("Mango", 70, 85, "Mango flavored fruit tea", "FRUITTEA"));
        ITEMS.put(39, new MenuItem("Passion fruit", 70, 85, "Passion fruit flavored fruit tea", "FRUITTEA"));
        ITEMS.put(40, new MenuItem("Strawberry", 70, 85, "Strawberry flavored fruit tea", "FRUITTEA"));
        
        //Cream Cheese Series(Owlet = Med, Owl = Lrg), no Regprice
        ITEMS.put(41, new MenuItem("Cheese Cake Cream Cheese", 80, 95, "Cheese cake flavored fruit tea", "CREAMCHEESE"));
        ITEMS.put(42, new MenuItem("Chocolate Cream Cheese", 80, 95, "Choco flavored fruit tea", "CREAMCHEESE"));
        ITEMS.put(43, new MenuItem("Cookies & Cream Cream Cheese", 80, 95, "Cookies & Cream flavored fruit tea", "CREAMCHEESE"));
        ITEMS.put(44, new MenuItem("Dark Choco Cream Cheese", 80, 95, "Dark Chocolate flavored fruit tea", "CREAMCHEESE"));
        ITEMS.put(45, new MenuItem("Matcha Cream Cheese", 80, 95, "Matcha flavored fruit tea", "CREAMCHEESE"));
        ITEMS.put(46, new MenuItem("Okinawa Cream Cheese", 80, 95, "Okinawa flavored fruit tea", "CREAMCHEESE"));
        ITEMS.put(47, new MenuItem("Taro Cream Cheese", 80, 95, "Taro flavored fruit tea", "CREAMCHEESE"));
        ITEMS.put(48, new MenuItem("Red Velvet Cream Cheese", 80, 95, "Red Velvet flavored fruit tea", "CREAMCHEESE"));
        ITEMS.put(49, new MenuItem("Wintermelon Cream Cheese", 80, 95, "Wintermelon flavored fruit tea", "CREAMCHEESE"));
        
        //Yakult Series(Owlet = Med, Owl = Lrg), no Regprice
        ITEMS.put(50, new MenuItem("Blueberry Yakult", 80, 95, "Blueberry flavored fruit tea", "YAKULT"));
        ITEMS.put(51, new MenuItem("Green Apple Yakult", 80, 95, "Green Apple flavored fruit tea", "YAKULT"));
        ITEMS.put(52, new MenuItem("Lemon Yakult", 80, 95, "Lemon flavored fruit tea", "YAKULT"));
        ITEMS.put(53, new MenuItem("Lychee Yakult", 80, 95, "Lychee flavored fruit tea", "YAKULT"));
        ITEMS.put(54, new MenuItem("Mango Yakult", 80, 95, "Mango flavored fruit tea", "YAKULT"));
        ITEMS.put(55, new MenuItem("Passion fruit Yakult", 80, 95, "Passion fruit flavored fruit tea", "YAKULT"));
        ITEMS.put(56, new MenuItem("Strawberry Yakult", 80, 95, "Strawberry flavored fruit tea", "YAKULT"));

        //Coffee Based Drinks(Owlet = Med, Owl = Lrg), no Regprice
        ITEMS.put(57, new MenuItem("Americano", 70, 90, "Americano-Style coffee", "COFFEE"));
        ITEMS.put(58, new MenuItem("Latte", 90, 110, "Latte coffee", "COFFEE"));
        ITEMS.put(59, new MenuItem("Cappuccino", 90, 110, "Cappuccino coffee", "COFFEE"));
        ITEMS.put(60, new MenuItem("Caramel Macchiato", 110, 130, "Caramel Macchiato coffee", "COFFEE"));
        ITEMS.put(61, new MenuItem("Creamy Latte", 110, 130, "Creamy Latte coffee", "COFFEE"));
        ITEMS.put(62, new MenuItem("Cafe Mocha", 100, 120, "Cafe Mocha coffee", "COFFEE"));
        ITEMS.put(63, new MenuItem("Spanish Latte", 100, 120, "Spanish Latte coffee", "COFFEE"));
        ITEMS.put(64, new MenuItem("White Choco", 110, 130, "White chocolate Mocha coffee", "COFFEE"));


    }
}