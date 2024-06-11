package base.types;

import java.util.Date;
import java.util.Objects;

public class Product {

    String code;
    double price;
    String name;
    String supplier_code;
    int quantity=0;
    Date expirationDate;
    private int minStockLevel;
    private int maxStockLevel;

    public Product(String name, String code, int quantity, double price, Date expirationDate, String supplier_code, int minStockLevel, int maxStockLevel){
        this.name=name;
        this.code= code;
        this.quantity=quantity;
        this.price=price;
        this.expirationDate = expirationDate;
        this.supplier_code=supplier_code;
        this.minStockLevel = minStockLevel;
        this.maxStockLevel = maxStockLevel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupplier_code() {
        return supplier_code;
    }

    public void setSupplier_code(String supplier_code) {
        this.supplier_code = supplier_code;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getMinStockLevel() {
        return minStockLevel;
    }

    public void setMinStockLevel(int minStockLevel) {
        this.minStockLevel = minStockLevel;
    }

    public int getMaxStockLevel() {
        return maxStockLevel;
    }

    public void setMaxStockLevel(int maxStockLevel) {
        this.maxStockLevel = maxStockLevel;
    }


    // Method to check for restocking alerts
    public boolean checkRestockingAlert() {
        if (quantity <= minStockLevel) {
            // Restocking alert: stock level is below or equal to the minimum level
            return true;
        } else {
            // No restocking alert
            return false;
        }
    }
    public boolean checkMaxAlert() {
        if (quantity > maxStockLevel) {
            // Max level reached
            return true;
        } else {

            return false;
        }
    }
    public void display() {
    System.out.println(
            "The product supplier is " + this.supplier_code + "\n" +
            "The product Code is " + this.code + "\n" +
            "The product Name is " + this.name + "\n" +
            "The product Price is " + this.price + "\n" +
            "The product Quantity is " + this.quantity + "\n" +
            "The product expirationDate is " + this.expirationDate+ "\n" +
            "The product MinStockLevel is " + this.minStockLevel+ "\n" +
            "The product MaxStockLevel is " + this.maxStockLevel);
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(code, product.code);
    }


}
