package app.features;

import base.types.Customer;
import base.types.Product;
import base.types.SalesOrder;
import base.types.Supplier;
import data.structures.AVLTree;

import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import  java.util.LinkedList;

// app.features.StockManagementSystem class to manage products, customers, and suppliers
 public class StockManagementSystem {

    //Array: to store information on products, customers, and suppliers.
    private Product[] productsArray;
    private Customer[] customersArray;
    private Supplier[] suppliersArray;


   // Linked List: to store expired products, inactive customers, and inactive suppliers.
    private LinkedList<Product> expiredProductsList;
    private LinkedList<Customer> inactiveCustomersList;
    private LinkedList<Supplier> inactiveSuppliersList;

    private AVLTree productCodeTree; // AVL tree to search products by code
    private AVLTree customerTree; // AVL tree to search customers by code or name
    private AVLTree supplierTree; // AVL tree to search suppliers by name or code


    //. This is because the hash table calculates the index where the product is stored based on the key's hash code,
    // making lookups fast regardless of the size of the data set
    private Hashtable<String, Product> productsHashTable;//This index is calculated based on the hash code of the key,

     // to store orders for products waiting to be executed// which is typically unique for each key.


    public StockManagementSystem(){
        this.productsArray = new Product[50];
        this.customersArray= new Customer[50];
        this.suppliersArray=new Supplier[50];
        this.expiredProductsList=new LinkedList<>();
        this.inactiveCustomersList=new LinkedList<>();
        this.inactiveSuppliersList =new LinkedList<>();
        this.productsHashTable = new Hashtable<>();
        this.productCodeTree = new AVLTree(); // Initialize AVL tree for product codes
        this.customerTree = new AVLTree(); // Initialize AVL tree for customers
        this.supplierTree = new AVLTree(); // Initialize AVL tree for suppliers
    }

    //Add a product to stock:
    // use the put method, specifying the product's name as the key and the product object as the value.
    public boolean addProduct(Product newProduct) {

        //check if the code is not already in use
        if (!product_exists(newProduct.getCode())) {
            // Find an empty spot in the array
            for (int i = 0; i < productsArray.length; i++) {
                if (productsArray[i] == null) {
                    productsArray[i] = newProduct;
                    productCodeTree.insert(newProduct.getCode(), newProduct);
                    productsHashTable.put(newProduct.getName(), newProduct);//The name is the key here to enable the search by name
                    return true; // exit the method once the product is added
                }
            } return false ;
        }else {
            System.out.println("The product code already in use: " + newProduct.getCode());
            return false;
        }
    }

    public boolean product_exists(String code){
        Object pr = productCodeTree.search(code);
        return pr==null?false:true;
    }



    // Modify information about a product
    public void modifyProduct(String code, Product updateProduct) {
        // Search for the product by code in the productCodeTree
        Product foundProduct = (Product) productCodeTree.search(code);

        // If the product with the given code is found
        if (foundProduct != null) {
            // Update the product information
            foundProduct.setName(updateProduct.getName());
            foundProduct.setQuantity(updateProduct.getQuantity());
            foundProduct.setPrice(updateProduct.getPrice());
            foundProduct.setExpirationDate(updateProduct.getExpirationDate());
            foundProduct.setMaxStockLevel(updateProduct.getMaxStockLevel());
            foundProduct.setMinStockLevel(updateProduct.getMinStockLevel());
            foundProduct.setSupplier_code(updateProduct.getSupplier_code());
            //no need to modify the array nor the HashTable since they both reference to the same object

        }
    }


    // Delete a product from stock
    public void deleteProduct(String code) {
        productCodeTree.delete(code);
        productsHashTable.remove(code);
            // Remove the product from the productsArray
            for (int i = 0; i < productsArray.length; i++) {
                if (productsArray[i] != null && productsArray[i].getCode() == code) {
                    productsArray[i] = null;
                    break;
                }
            }
    }
    // Method to search for a product by name
    public Product searchProductByName(String name) {
        Product pr = productsHashTable.get(name);
        return (pr!=null)?(Product)pr:null;

    }
    public Product searchProductByCode(String code) {
        Object pr = productCodeTree.search(code);
        return (pr!=null)?(Product) pr:null;
    }
    public void process_expired_products(){
        for (int i = 0; i < productsArray.length; i++) {
            if (productsArray[i] != null && productsArray[i].getExpirationDate().after(new Date())) {
                Product expired_product = productsArray[i];
                expiredProductsList.add(expired_product);
                productCodeTree.delete(expired_product.getCode());
                productsHashTable.remove(expired_product.getCode());
                productsArray[i] = null;
                break;
            }
        }
    }

    //Add a customer to stock:
    public boolean addCustomer(Customer newCustomer) {
        if (!customer_exists(newCustomer.getCust_code())) {
            for (int i = 0; i < customersArray.length; i++) {
                if (customersArray[i] == null) {
                    // Add the new customer to the array
                    customersArray[i] = newCustomer;
                    // Insert the base.types.Customer into the AVL tree using name and code as key
                    customerTree.insert(newCustomer.getCust_code(), newCustomer);
                    return true; // Exit the loop after adding the customer
                }

            } return false;
        }else {
            System.out.println("The customer code or name already in use: " + newCustomer.getCust_code() + " Name: "+ newCustomer.getName());
            return false;
        }
    }
    public boolean customer_exists(String code){
        Object cus = customerTree.search(code);

        return (cus==null)?false:true;
    }



    // Method to delete a customer by name or code
    public void deleteCustomer(String searchKey,boolean byName) {
        // Search for the customer by name or code
        Object obj = null;
        if (!byName) {
            String code = searchKey;
            obj =  customerTree.search(code);
        } else {
            String name = searchKey;
            obj =  customerTree.searchByName(name);
        }


        // If the customer with the given name or code is found
        if (obj != null) {
            Customer customerToDelete = (Customer)obj;
            // Remove the customer from the customersArray
            for (int i = 0; i < customersArray.length; i++) {
                if (customersArray[i] != null && customersArray[i].equals(customerToDelete)) {
                    customersArray[i] = null;
                    break;
                }
            }
            // Remove the customer from AVL tree
            customerTree.delete(customerToDelete.getCust_code());
            System.out.println("Customer deleted successfully.");
        } else {
            System.out.println("Customer not found.");
        }
    }

    public void inactivate_customer(String cust_code){
        for (int i = 0; i < customersArray.length; i++) {
            if (customersArray[i] != null && customersArray[i].getCust_code().equals(cust_code) ) {
                Customer cust = customersArray[i];
                cust.setActive(false);
                customersArray[i] =null;
                customerTree.delete(cust_code);
                inactiveCustomersList.add(cust);
                break;
            }
        }
    }

    public Customer searchCustomers(String searchKey, boolean byName){
        Object obj = null;
        if (byName) {
          obj = customerTree.searchByName(searchKey);

        }else{
            obj = customerTree.search(searchKey);
        }
        return obj!=null?(Customer)obj:null ;
    }

    public boolean supplier_exists(String code){
        Object sup = supplierTree.search(code);

        return (sup==null)?false:true;
    }

    //Add a supplier
    public boolean addSupplier(Supplier newSupplier){
        if (!customer_exists(newSupplier.getSupplier_code())) {
            for (int i = 0; i < suppliersArray.length; i++) {
                if (suppliersArray[i] == null) {
                    // Add the new supplier to the array
                    suppliersArray[i] = newSupplier;
                    // Insert the supplier into the AVL tree using name and code as key
                    supplierTree.insert(newSupplier.getSupplier_code(), newSupplier);
                    return true; // Exit the loop after adding the supplier
                }
            }
        }else {
            System.out.println("The supplier code or name already in use: " + newSupplier.getSupplier_code() + " Name: "+ newSupplier.getName());
        }
        return false;
    }


    // Modify information about a supplier


    // Method to delete a base.types.Supplier by name or code
    public void deleteSupplier(String searchKey,boolean byName) {
        // Search for the base.types.Supplier by name or code
        Object obj = null;
        if (!byName) {
            String code = searchKey;
            obj =  supplierTree.search(code);
        } else {
            String name = searchKey;
            obj =  supplierTree.searchByName(name);
        }


        // If the base.types.Supplier with the given name or code is found
        if (obj != null) {
            Supplier supplierToDelete = (Supplier)obj;
            // Remove the customer from the customersArray
            for (int i = 0; i < suppliersArray.length; i++) {
                if (suppliersArray[i].equals(supplierToDelete)) {
                    suppliersArray[i] = null;
                    break;
                }
            }
            // Remove the supplier from AVL tree
            supplierTree.delete(supplierToDelete.getSupplier_code());
            System.out.println("supplier deleted successfully.");
        } else {
            System.out.println("supplier not found.");
        }
    }
    public void inactivate_supplier(String supplier_code){
        for (int i = 0; i < suppliersArray.length; i++) {
            if (suppliersArray[i] != null && suppliersArray[i].getSupplier_code().equals(supplier_code) ) {
                Supplier suppl = suppliersArray[i];
                suppl.setActive(false);
                suppliersArray[i] =null;
                supplierTree.delete(supplier_code);
                inactiveSuppliersList.add(suppl);
                break;
            }
        }
    }
    public Supplier searchSuppliers(String searchKey, boolean byName){
        Object obj = null;
        if (byName) {
            obj = supplierTree.searchByName(searchKey);

        }else{
            obj = supplierTree.search(searchKey);
        }
        return obj!=null?(Supplier)obj:null ;
    }

    public Product[] getProductsArray() {
        return productsArray;
    }


    public void  showCustomers( ){

       for(int i=0;i<customersArray.length;i++){
           if (customersArray[i] != null) {
               customersArray[i].display();
           }
       }

    }

    public void  showSuppliers( ){

        for(int i=0;i<suppliersArray.length;i++){
            if (suppliersArray[i] != null) {
                suppliersArray[i].display();
            }
        }

    }
    public void  showProducts( ){

        for(int i=0;i<productsArray.length;i++){
            if (productsArray[i] != null) {
                productsArray[i].display();
            }
        }

    }

}
