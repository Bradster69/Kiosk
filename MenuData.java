import java.util.HashMap;
import java.util.Map;

public class MenuData {
    public static class MenuItem {
        public final String name;
        public final Integer Regprice; // Nullable: only for food or drinks with a regular/small price
        public final Integer MedPrice; // Owlet (medium), nullable if not available
        public final Integer LrgPrice; // Owl (large), nullable if not available
        public final String description;
        public final String category;
        public final String imagePath; // New field for image path

        // For food or drinks with only one price (Regprice)
        public MenuItem(String name, int Regprice, String description, String category, String imagePath) {
            this.name = name;
            this.Regprice = Regprice;
            this.MedPrice = null;
            this.LrgPrice = null;
            this.description = description;
            this.category = category;
            this.imagePath = imagePath;
        }

        // For drinks with medium and large prices (no Regprice)
        public MenuItem(String name, Integer MedPrice, Integer LrgPrice, String description, String category, String imagePath) {
            this.name = name;
            this.Regprice = null;
            this.MedPrice = MedPrice;
            this.LrgPrice = LrgPrice;
            this.description = description;
            this.category = category;
            this.imagePath = imagePath;
        }
    }

    // Static map of menu items
    public static final Map<Integer, MenuItem> ITEMS = new HashMap<>();
    static {
        // Rice meals
        ITEMS.put(0, new MenuItem("Tapsilog", 95, "Tapa with egg and garlic rice", "RICEMEALS", "assets/tapsilog.png"));
ITEMS.put(1, new MenuItem("Tocilog", 95, "Tocino with egg and garlic rice", "RICEMEALS", "assets/tocilog.png"));
ITEMS.put(2, new MenuItem("Porksilog", 100, "Pork with egg and garlic rice", "RICEMEALS", "assets/porksilog.png"));
ITEMS.put(3, new MenuItem("Cornsilog", 110, "Corned beef with egg and garlic rice", "RICEMEALS", "assets/cornsilog.png"));
ITEMS.put(4, new MenuItem("Spamsilog", 115, "Spam meat with egg and garlic rice", "RICEMEALS", "assets/spamsilog.png"));
ITEMS.put(5, new MenuItem("Chicken in a plate", 95, "Chicken with rice", "RICEMEALS", "assets/chicken_in_a_plate.png"));
ITEMS.put(6, new MenuItem("Cheesy Chicken", 105, "Chicken with cheese and rice", "RICEMEALS", "assets/cheesy_chicken.png"));
ITEMS.put(7, new MenuItem("Chicken Curry", 110, "Chicken curry with rice", "RICEMEALS", "assets/chicken_curry.png"));
ITEMS.put(8, new MenuItem("Chicken Katsudon", 120, "Crispy Chicken with rice", "RICEMEALS", "assets/chicken_katsudon.png"));
ITEMS.put(9, new MenuItem("Pork Katsu", 120, "Pork with rice", "RICEMEALS", "assets/pork_katsu.png"));
ITEMS.put(10, new MenuItem("Pork Gyudon", 120, "Pork something with rice", "RICEMEALS", "assets/pork_gyudon.png"));

ITEMS.put(11, new MenuItem("Cheesy Fries", 60, "Fries with cheese", "SNACKS", "assets/cheesy_fries.png"));
ITEMS.put(12, new MenuItem("BBQ Fries", 60, "Fries with BBQ flavor", "SNACKS", "assets/bbq_fries.png"));
ITEMS.put(13, new MenuItem("SourCream Fries", 60, "Fries with Sour Cream", "SNACKS", "assets/sourcream_fries.png"));
ITEMS.put(14, new MenuItem("Mojos", 75, "Potato Mojos with sauce", "SNACKS", "assets/mojos.png"));
ITEMS.put(15, new MenuItem("Nachos", 85, "Nachos with cheese and sauce", "SNACKS", "assets/nachos.png"));
ITEMS.put(16, new MenuItem("Clubhouse", 110, "Clubhouse Sandwich", "SNACKS", "assets/clubhouse.png"));

ITEMS.put(17, new MenuItem("Ham & cheese w/ egg", 90, "Ham and cheese with egg", "WAFFOWLS", "assets/ham_cheese_w_egg.png"));
ITEMS.put(18, new MenuItem("Bacon & cheese w/ egg", 90, "Bacon and cheese with egg", "WAFFOWLS", "assets/bacon_cheese_w_egg.png"));
ITEMS.put(19, new MenuItem("Combo K1", 280, "Fries, Nachos, Mojos, 1L Lemonade", "WAFFOWLS", "assets/combo_k1.png"));
ITEMS.put(20, new MenuItem("Ham & cheese", 90, "Ham and cheese with egg", "WAFFOWLS", "assets/ham_cheese.png"));
ITEMS.put(21, new MenuItem("Bacon & cheese", 90, "Bacon and cheese with egg", "WAFFOWLS", "assets/bacon_cheese.png"));
ITEMS.put(22, new MenuItem("Combo K2", 280, "Fries, Nachos, Mojos, 1L Lemonade", "WAFFOWLS", "assets/combo_k2.png"));

    }
}