package base.types;

import java.util.HashMap;

public class SalesReturn {
    // Fields
    private SalesOrder so; // The sales order from which the product is returned
    private Product product; // The product being returned
    private int quantityReturned; // The quantity of the product being returned

    // Constructor
    public SalesReturn(SalesOrder so, Product product, int quantityReturned) {
        this.so = so;
        this.product = product;
        this.quantityReturned = quantityReturned;
    }

    // Getters and setters
    public SalesOrder getSo() {
        return so;
    }

    public void setSo(SalesOrder so) {
        this.so = so;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(int quantityReturned) {
        this.quantityReturned = quantityReturned;
    }

    // Method to execute the return operation
    public void executeReturn() {
        // Get the price at which the product was sold and the discount applied
        double priceSold = so.getProductsSalesInfo().get(product).getPrice();
        double discount = so.getProductsSalesInfo().get(product).getDiscount();

        // Retrieve the customer's purchase history
        CustPurchaseHistory cuh = so.getCustomer().getCustPurchaseHistory();

        // Retrieve the current quantity and sales amount for the product in the customer's purchase history
        int qty = cuh.getProductTotals().get(product).getQuantity();
        double sales = cuh.getProductTotals().get(product).getSalesAmt();

        // Update the quantity and sales amount in the customer's purchase history to reflect the return
        cuh.getProductTotals().put(product, new CustPurchases(qty - quantityReturned, sales - (quantityReturned * priceSold * (1 - discount))));
    }

    // Method to display information about the SalesReturn
    public void displaySalesReturn() {
        System.out.println("Sales Return  Information:");
        System.out.println("Sales Order:");
        System.out.println("Customer: " + so.getCustomer().getName());

        System.out.println("Returned Product:");
        System.out.println("Product: " + product.getName());
        System.out.println("Quantity Returned: " + quantityReturned);
    }



}
