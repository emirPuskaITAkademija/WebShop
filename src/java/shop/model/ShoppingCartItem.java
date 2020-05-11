package shop.model;

import java.io.Serializable;

public class ShoppingCartItem implements Serializable {

    private Product product;
    private int quantity;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return quantity * product.getPrice();
    }
}
