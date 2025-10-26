package restaurant.model;

public class MenuItem extends Item {
    public MenuItem(String name, double price) {
        super(name, price);
    }

    @Override
    public String toString() {
        return getName();
    }
}
