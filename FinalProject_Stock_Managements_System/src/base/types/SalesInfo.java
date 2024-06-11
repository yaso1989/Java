package base.types;

public class SalesInfo {


    private int quantity;
    private double price;
    private double discount;

    public SalesInfo(int quantity, double discount,double price) {
        this.quantity = quantity;
        this.discount = discount;
        this.price = price;
    }

    // Getters and setters for quantity and discount


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}