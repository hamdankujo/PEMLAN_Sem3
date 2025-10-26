package restaurant.model;

public class OrderItem {
    private final MenuItem menuItem;
    private int quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public String getName() {
        return menuItem.getName();
    }

    public double getPrice() {
        return menuItem.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int q) {
        this.quantity = q;
    }

    public double getSubtotal() {
        return menuItem.getPrice() * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem other = (OrderItem) o;
        return this.getName().equalsIgnoreCase(other.getName()) && this.getPrice() == other.getPrice();
    }

    @Override
    public int hashCode() {
        return (getName().toLowerCase() + getPrice()).hashCode();
    }
}
