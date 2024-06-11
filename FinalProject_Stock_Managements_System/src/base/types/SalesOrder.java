package base.types;

import java.util.HashMap;
import java.util.Objects;

public class SalesOrder {
    String order_id;
    Customer customer;
    HashMap<Product,SalesInfo> productsSalesInfo;//quantities sold in the sales order for each product


    public SalesOrder(String sales_order_id,Customer customer, HashMap<Product, SalesInfo> productsSalesInfo){
        this.order_id = sales_order_id;
        this.customer = customer;
        this.productsSalesInfo = productsSalesInfo;
    }
    private boolean checkOrdertoExecute(){
        for (Product product : productsSalesInfo.keySet()) {
            int soldQuantity = productsSalesInfo.get(product).getQuantity();
            if (soldQuantity>product.getQuantity()){
                System.out.println("The sales order cannot be executed since the qty sold of the item :" + product.getCode() +"  is higher than the current stock level");
                return false;
            }

        }
        return true;
    }
    public boolean executeOrder() {
        // Check if the order is ready to be executed
        if (checkOrdertoExecute()) {
            // Initialize the total sales value
            double totalSalesValue = 0;

            // Iterate through each product in the sales order
            for (Product product : productsSalesInfo.keySet()) {
                // Retrieve the sales information for the current product
                SalesInfo salesInfo = productsSalesInfo.get(product);
                int soldQuantity = salesInfo.getQuantity(); // Get the quantity sold

                // Update the product's quantity
                product.setQuantity(product.getQuantity() - soldQuantity);

                // Retrieve customer's purchase history for the current product
                CustPurchases custPurchases = customer.getCustPurchaseHistory().getProductTotals().get(product);
                if (custPurchases == null) {
                    // If purchase history not available, initialize with default values
                    custPurchases = new CustPurchases(0, 0);
                    customer.getCustPurchaseHistory().getProductTotals().put(product, custPurchases);
                }

                // Update the customer's purchase history for the current product
                int cusQty = custPurchases.getQuantity();
                double cusSalesAmt = custPurchases.getSalesAmt();
                double productSalesAmt = soldQuantity * salesInfo.getPrice() * (1 - salesInfo.getDiscount());

                // Update the customer's purchase history with the new quantity and sales amount
                customer.getCustPurchaseHistory().getProductTotals().get(product).setQuantity(cusQty + soldQuantity);
                customer.getCustPurchaseHistory().getProductTotals().get(product).setSalesAmt(cusSalesAmt + productSalesAmt);

                // Update the total sales value
                totalSalesValue += productSalesAmt;

            }
            return true;
        }else{
            return false;
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public HashMap<Product, SalesInfo> getProductsSalesInfo() {
        return productsSalesInfo;
    }

    public void setProductsSalesInfo(HashMap<Product, SalesInfo> productsSalesInfo) {
        this.productsSalesInfo = productsSalesInfo;
    }

    // Method to display information about the sales order
    public void displaySalesOrder() {
        System.out.println("Sales Order Information:");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Products:");

        for (Product product : productsSalesInfo.keySet()) {
            SalesInfo salesInfo = productsSalesInfo.get(product);

            System.out.println("Product: " + product.getName());
            System.out.println("Quantity Sold: " + salesInfo.getQuantity());
            System.out.println("Price: " + salesInfo.getPrice());
            System.out.println("Discount: " + salesInfo.getDiscount());
        }
    }

    public String getOrder_id() {
        return order_id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalesOrder)) return false;
        SalesOrder that = (SalesOrder) o;
        return that.order_id==((SalesOrder) o).order_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, productsSalesInfo);
    }

}
