import java.util.HashMap;
import java.util.Map;

public class MenuData {
    public static class MenuItem {
        public final String name;
        public final double price;
        public final String description;
        public final String category;

        public MenuItem(String name, double price, String description, String category) {
            this.name = name;
            this.price = price;
            this.description = description;
            this.category = category;
        }
    }

    // Static map of menu items
    public static final Map<Integer, MenuItem> ITEMS = new HashMap<>();
    static {
        ITEMS.put(0, new MenuItem("Tapsilog", 95, "Tapa with egg and garlic rice", "RICEMEALS"));
        ITEMS.put(1, new MenuItem("Cornsilog", 110, "Corned beef with egg and garlic rice", "RICEMEALS"));
        ITEMS.put(2, new MenuItem("Cheese Cake", 80, "Cheese cake milktea", "MILKTEA"));
        ITEMS.put(3, new MenuItem("Velvet", 110, "Red velvet milk tea", "MILKTEA"));
        ITEMS.put(4, new MenuItem("Taro Milk Tea", 109, "Taro flavored milk tea", "MILKTEA"));
        ITEMS.put(5, new MenuItem("Wintermelon", 70, "Wintermelon flavored milk tea", "MILKTEA"));
    }
}