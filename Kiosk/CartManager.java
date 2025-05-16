import java.util.HashMap;
import java.util.Map;

public class CartManager {
    private final Map<Integer, Integer> cartMap = new HashMap<>();

    public void addToCart(int index) {
        cartMap.put(index, cartMap.getOrDefault(index, 0) + 1);
    }

    public void removeFromCart(int index) {
        if (cartMap.containsKey(index)) {
            int qty = cartMap.get(index);
            if (qty > 1) {
                cartMap.put(index, qty - 1);
            } else {
                cartMap.remove(index);
            }
        }
    }

    public void clearCart() {
        cartMap.clear();
    }

    public Map<Integer, Integer> getCartItems() {
        return new HashMap<>(cartMap);
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (Map.Entry<Integer, Integer> entry : cartMap.entrySet()) {
            MenuData.MenuItem item = MenuData.ITEMS.get(entry.getKey());
            if (item != null) {
                total += item.price * entry.getValue();
            }
        }
        return total;
    }

    public boolean isEmpty() {
        return cartMap.isEmpty();
    }
}
