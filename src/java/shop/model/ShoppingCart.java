package shop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {

    private final List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();

    public void addShoppingCartItem(Product product, int quantity) {
        for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {
            if (shoppingCartItem.getProduct().getId() == product.getId()) {
                int newQuantity = shoppingCartItem.getQuantity() + quantity;
                shoppingCartItem.setQuantity(newQuantity);
                return;
            }
        }
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem(product, quantity);
        shoppingCartItems.add(shoppingCartItem);
    }

    public List<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

}
