package app.features;

import base.types.Customer;
import base.types.Product;
import base.types.SalesOrder;
import base.types.SalesReturn;

import java.util.*;


public class SalesAndReturnsManagement {
  private Queue<SalesOrder> salesQueue;
  private List<SalesOrder> executedSalesOrders;
    private List<SalesReturn> executedSalesReturns;
  public SalesAndReturnsManagement(){
      this.salesQueue = new LinkedList<SalesOrder>();
      this.executedSalesOrders = new ArrayList<SalesOrder>();
      this.executedSalesReturns = new ArrayList<SalesReturn>();
  }

  public void insertSalesOrder(SalesOrder so){
      salesQueue.offer(so); // Offer inserts the element at the rear of the queue
  }
  public SalesOrder getNextSalesOrder(){
      if (!salesQueue.isEmpty()) {
          SalesOrder so = salesQueue.poll(); // Poll removes and returns the element at the front of the queue
          return so;
      }
      return null;
  }

  public SalesOrder executeNextSalesOrder(){
      SalesOrder so = getNextSalesOrder();
      System.out.println("Sales Order " + so.getOrder_id() + " is the next order");
      if (so.executeOrder()){
          System.out.println("Sales Order Executed Successfully");
          executedSalesOrders.add(so);
          salesQueue.remove(so);
      }else{
          //in case no quantities found t execute the order remove the order and add it to the last elemnet of the queue
          //then look for the next order in line to be executed
          System.out.println("Sales Order Cannot be Executed");
          salesQueue.remove(so);
          salesQueue.offer(so);
          executeNextSalesOrder();
      }
      return so;
  }

    /**
     * Finds the executed sales order for a given customer and product.
     *
     * @param cust    The customer for which to find the executed sales order.
     * @param product The product for which to find the executed sales order.
     * @return The executed sales order if found, otherwise null.
     */
    private SalesOrder findExecutedSalesOrder(Customer cust, Product product) {
        // Create an iterator to iterate over the executed sales orders
        Iterator<SalesOrder> iterator = executedSalesOrders.iterator();

        // Iterate over each executed sales order
        while (iterator.hasNext()) {
            // Get the next sales order
            SalesOrder so = iterator.next();

            // Check if the sales order belongs to the given customer
            if (so.getCustomer().equals(cust)) {
                // Iterate over each product in the sales order's sales information
                for (Product key : so.getProductsSalesInfo().keySet()) {
                    // Check if the product matches the given product
                    if (key.equals(product)) {
                        // If found, return the sales order
                        return so;
                    }
                }
            }
        }

        // If no matching sales order is found, return null
        return null;
    }

    public SalesOrder findExecutedSalesOrderByID(String sales_order_id) {
        // Create an iterator to iterate over the executed sales orders
        Iterator<SalesOrder> iterator = executedSalesOrders.iterator();

        // Iterate over each executed sales order
        while (iterator.hasNext()) {
            // Get the next sales order
            SalesOrder so = iterator.next();

            // Check if the sales order's ID matches the given ID
            if (so.getOrder_id().equals(sales_order_id)) {
                 // If found, return the sales order
                 return so;

            }
        }

        // If no matching sales order is found, return null
        return null;
    }

  public boolean  executeSalesReturn(Customer cust, Product product, int quantity ){

        SalesOrder so = findExecutedSalesOrder(cust,product);

        if (so!=null){
            System.out.println("Related Sales Order found");
            SalesReturn sr = new SalesReturn(so,product,quantity);
            sr.executeReturn();
            executedSalesReturns.add(sr);
            return true; // Sales return executed successfully
        }else{
            System.out.println("Related Sales Order not found");
        }
      return false; // No executed sales order found
  }

    public void  showExecutedSalesOrders( ){
        Iterator<SalesOrder> it = executedSalesOrders.iterator();
        while (it.hasNext()){
            it.next().displaySalesOrder();
            Iterator<SalesReturn> it1 = executedSalesReturns.iterator();
            while (it1.hasNext()){
                it1.next().displaySalesReturn();
            }
        }

    }


}
