import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CartManager {
    // Composite key for item index and size
    public static class CartKey {
        public final int index;
        public final String size; // "MEDIUM", "LARGE", or null

        public CartKey(int index, String size) {
            this.index = index;
            this.size = size;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CartKey)) return false;
            CartKey key = (CartKey) o;
            return index == key.index && Objects.equals(size, key.size);
        }

        @Override
        public int hashCode() {
            return Objects.hash(index, size);
        }
    }

    private final Map<CartKey, Integer> cartMap = new HashMap<>();

    // For food or single-price items
    public void addToCart(int index) {
        addToCartWithSize(index, null);
    }

    public void removeFromCart(int index) {
        removeFromCartWithSize(index, null);
    }

    public void addToCartWithSize(int index, String size) {
        CartKey key = new CartKey(index, size);
        cartMap.put(key, cartMap.getOrDefault(key, 0) + 1);
    }

    public void removeFromCartWithSize(int index, String size) {
        CartKey key = new CartKey(index, size);
        if (cartMap.containsKey(key)) {
            int qty = cartMap.get(key);
            if (qty > 1) {
                cartMap.put(key, qty - 1);
            } else {
                cartMap.remove(key);
            }
        }
    }

    public void clearCart() {
        cartMap.clear();
    }

    // For HomePage cart panel
    public Map<CartKey, Integer> getCartItemsWithSize() {
        return new HashMap<>(cartMap);
    }

    // For legacy code (if needed)
    public Map<Integer, Integer> getCartItems() {
        Map<Integer, Integer> simple = new HashMap<>();
        for (CartKey key : cartMap.keySet()) {
            if (key.size == null) {
                simple.put(key.index, cartMap.get(key));
            }
        }
        return simple;
    }

    public double getTotalPriceWithSize() {
        double total = 0.0;
        for (Map.Entry<CartKey, Integer> entry : cartMap.entrySet()) {
            MenuData.MenuItem item = MenuData.ITEMS.get(entry.getKey().index);
            if (item != null) {
                int price = 0;
                if (item.Regprice != null) {
                    price = item.Regprice;
                } else if ("MEDIUM".equals(entry.getKey().size) && item.MedPrice != null) {
                    price = item.MedPrice;
                } else if ("LARGE".equals(entry.getKey().size) && item.LrgPrice != null) {
                    price = item.LrgPrice;
                }
                total += price * entry.getValue();
            }
        }
        return total;
    }

    // For legacy code (if needed)
    public double getTotalPrice() {
        double total = 0.0;
        for (Map.Entry<CartKey, Integer> entry : cartMap.entrySet()) {
            if (entry.getKey().size == null) {
                MenuData.MenuItem item = MenuData.ITEMS.get(entry.getKey().index);
                if (item != null) {
                    int price = item.Regprice != null ? item.Regprice
                              : item.MedPrice != null ? item.MedPrice
                              : item.LrgPrice != null ? item.LrgPrice
                              : 0;
                    total += price * entry.getValue();
                }
            }
        }
        return total;
    }

    public boolean isEmpty() {
        return cartMap.isEmpty();
    }
}
