import app.features.SalesAndReturnsManagement;
import app.features.StockManagementSystem;
import base.types.*;
import data.structures.AVLTree;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ApplicationMenu {
    private static final String[] CODE_PREFIXES = {"P", "S", "C"};
    private static final String[] NAMES = {"John", "Alice", "Bob", "Emma", "Mike", "Sarah"};
    private static final String[] EMAIL_DOMAINS = {"example.com", "test.com", "company.com"};
    private static final String[] ADDRESSES = {"123 Main St", "456 Elm St", "789 Oak St"};
    private static final String[] PHONE_PREFIXES = {"123", "456", "789"};
    private static final int MAX_QUANTITY = 1000;
    private static final int MAX_PRICE = 100;
    private static final int MAX_STOCK_LEVEL = 500;
    private static String generateCode(String prefix) {
        Random random = new Random();
        return prefix + String.format("%03d", random.nextInt(1000));
    }
    private void generateTestData(StockManagementSystem stMs){

            Random random = new Random();

            // Create 3 Product objects with random data
            for (int i = 0; i < 3; i++) {
                Product product = new Product(
                        "Product " + i,generateCode("P"),
                        random.nextInt(MAX_QUANTITY),random.nextDouble() * MAX_PRICE,
                        new Date(System.currentTimeMillis() + random.nextInt(365) * 24 * 60 * 60 * 1000),"Product " + (i + 1),
                        random.nextInt(MAX_STOCK_LEVEL),
                        random.nextInt(MAX_STOCK_LEVEL)

                );
                stMs.addProduct(product);
                System.out.println("Product: " + product);
            }

            // Create 3 Supplier objects with random data
            for (int i = 0; i < 3; i++) {
                Supplier supplier = new Supplier(
                        generateCode("S"),
                        "Supplier " + (i + 1),ADDRESSES[random.nextInt(ADDRESSES.length)]
                        , NAMES[random.nextInt(NAMES.length)].toLowerCase() + "@" + EMAIL_DOMAINS[random.nextInt(EMAIL_DOMAINS.length)],
                        PHONE_PREFIXES[random.nextInt(PHONE_PREFIXES.length)] + String.format("%07d", random.nextInt(10000000))
                );
                stMs.addSupplier(supplier);
                System.out.println("Supplier: " + supplier);
            }

            // Create 3 Customer objects with random data
            for (int i = 0; i < 3; i++) {
                Customer customer = new Customer(
                        generateCode("C"),
                        "Customer " + (i + 1),
                        NAMES[random.nextInt(NAMES.length)].toLowerCase() + "@" + EMAIL_DOMAINS[random.nextInt(EMAIL_DOMAINS.length)],
                        PHONE_PREFIXES[random.nextInt(PHONE_PREFIXES.length)] + String.format("%07d", random.nextInt(10000000)),
                        ADDRESSES[random.nextInt(ADDRESSES.length)]
                );
                stMs.addCustomer(customer);
                System.out.println("Customer: " + customer);
            }

    }
    public void mainMenu() {


        Scanner scanner = new Scanner(System.in);

        StockManagementSystem stockMS = new StockManagementSystem();
        SalesAndReturnsManagement srm = new SalesAndReturnsManagement();
        generateTestData(stockMS);
        boolean exit = false;
        while (!exit) {

            System.out.println("Welcome to the **Stock Management System");
            System.out.println("*******************************************");
            System.out.println("1. Add a product to stock");
            System.out.println("2. Modify Product Info");
            System.out.println("3. Delete a Product from stock");
            System.out.println("4. Add a Customer");
            System.out.println("5. Modify Customer Info by Code");
            System.out.println("6. Modify Customer Info by Name");
            System.out.println("7. Delete a Customer by Code");
            System.out.println("8. Delete a Customer by Name");
            System.out.println("9. Add a supplier");
            System.out.println("10. Modify a supplier by Code");
            System.out.println("11. Modify a supplier by Name");
            System.out.println("12. Delete a supplier by Code");
            System.out.println("13. Delete a supplier by Name");
            System.out.println("14. Search for a product by code");
            System.out.println("15. Search for a product by name");
            System.out.println("16. Search for a customer by code");
            System.out.println("17. Search for a customer by name");
            System.out.println("18. Search for a supplier by code");
            System.out.println("19. Search for a supplier by name");
            System.out.println("20. Set minimum and maximum stock levels");
            System.out.println("21. Check Inventory Restocking & Max Level Alerts");
            System.out.println("22. Create Sales Order");
            System.out.println("23. Execute The Next Sales Order");
            System.out.println("24. Show Executed Sales Orders");
            System.out.println("25. Create and excute sales return of products");
            System.out.println("26. Show All Customers");
            System.out.println("27. Show All Suppliers");
            System.out.println("28. Show All Products");
            System.out.println("29. Exit");
            System.out.println("======================================");


            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            Date expirationDate;
            SimpleDateFormat dateFormat = null;
            switch (choice) {
                case 1:
                    // Add a product to stock
                    System.out.println("Enter product details:");
                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Code: ");
                    String code = scanner.nextLine();

                    System.out.print("Quantity: ");
                    int quantity = scanner.nextInt();

                    System.out.print("Price: ");
                    double price = scanner.nextDouble();

                    boolean validDate = false;
                    expirationDate = null;
                    System.out.print("Expiration Date (YYYY-MM-DD): ");
                    while (!validDate) {
                        String expirationDateString = scanner.nextLine(); // Store the user input as a String

                        // Parsing the string to Date format
                        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            expirationDate = dateFormat.parse(expirationDateString);
                            validDate = true; // Set validDate to true if parsing is successful
                        } catch (ParseException e) {
                            // System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                            System.out.print("");

                        }
                    }


                    String supplierCo = null;
                    boolean validSuppCode = false;
                    while (!validSuppCode) {
                        System.out.print("Supplier Code: ");
                        supplierCo = scanner.nextLine();
                        Supplier sup = stockMS.searchSuppliers(supplierCo, false);
                        if (sup != null) {
                            validSuppCode = true;
                        } else {
                            System.out.println("Invalid supplier code. Try again.");
                        }
                    }
                    System.out.print("Minimum Stock Level: ");
                    int minStockLevel = scanner.nextInt();

                    System.out.print("Maximum Stock Level: ");
                    int maxStockLevel = scanner.nextInt();

                    Product newProduct = new Product(name, code, quantity, price, expirationDate, supplierCo, minStockLevel, maxStockLevel);

                    // Add the product to the stock management system
                    if (stockMS.addProduct(newProduct)) {
                        System.out.println("Product added successfully.");
                        System.out.println("======================================"); // Separator line
                    }
                    break;


                case 2:
                    // Modify information about a product
                    System.out.print("Enter the product code to modify: ");
                    String modifyCode = scanner.nextLine();
                    ;

                    // Create a new base.types.Product object with updated information
                    System.out.println("Enter updated product details:");
                    System.out.print("Name: ");
                    String newName = scanner.nextLine();

                    System.out.print("Quantity: ");
                    int newQuantity = scanner.nextInt();

                    System.out.print("Price: ");
                    double newPrice = scanner.nextDouble();

                    validDate = false;
                    expirationDate = null;
                    System.out.print("Expiration Date (YYYY-MM-DD): ");
                    while (!validDate) {
                        String expirationDateString = scanner.nextLine(); // Store the user input as a String

                        // Parsing the string to Date format
                        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            expirationDate = dateFormat.parse(expirationDateString);
                            validDate = true; // Set validDate to true if parsing is successful
                        } catch (ParseException e) {
                            // System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                            System.out.print("");

                        }
                    }

                    System.out.print("Supplier: ");
                    String newSupplier = scanner.nextLine();

                    System.out.print("MinStockLevel: ");
                    int newMinStockLevel = scanner.nextInt();

                    System.out.print("MaxStockLevel: ");
                    int newMaxStockLevel = scanner.nextInt();

                    Product updatedProduct = new Product(newName, modifyCode, newQuantity, newPrice, expirationDate, newSupplier, newMinStockLevel, newMaxStockLevel);

                    // Call the modifyProduct method to update the product
                    stockMS.modifyProduct(modifyCode, updatedProduct);
                    System.out.println("======================================");
                    break;

                case 3:
                    // Delete a product from stock
                    System.out.print("Enter the product code to delete: ");
                    String deleteCode = scanner.nextLine();

                    // Delete the product by code
                    stockMS.deleteProduct(deleteCode);
                    System.out.println("Product deleted successfully.");

                    break;

                case 4:
                    System.out.println("======================================");
                    System.out.println("Enter customer details:");
                    System.out.print("Customer Code: ");
                    String customerCode = scanner.nextLine();

                    System.out.print("Name: ");
                    String customerName = scanner.nextLine();

                    System.out.print("Email Address: ");
                    String customerEmailAddress = scanner.nextLine();

                    System.out.print("Phone Number: ");
                    String customerPhoneNumber = scanner.nextLine();

                    System.out.print("Address: ");
                    String customerAddress = scanner.nextLine();

                    // Create a new customer object
                    Customer newCustomer = new Customer(customerCode, customerName, customerEmailAddress, customerPhoneNumber, customerAddress);

                    // Add the new customer
                    if (stockMS.addCustomer(newCustomer)) {
                        System.out.println("Customer added successfully.");
                    }

                    break;

                case 5:
                    System.out.println("======================================");
                    // Modify information about a customer by code
                    System.out.print("Enter the code of the customer to modify: ");
                    String customerIdentifier = scanner.nextLine();

                    Customer foundCustomer = stockMS.searchCustomers(customerIdentifier, false);
                    // Search for the customer
                    if (foundCustomer != null) {
                        System.out.println("Enter new customer details:");
                        System.out.print("Name: ");
                        String modName = scanner.nextLine();
                        foundCustomer.setName(modName);

                        System.out.print("Address: ");
                        String newAddress = scanner.nextLine();
                        foundCustomer.setAddress(newAddress);

                        System.out.print("Phone Number: ");
                        String newPhoneNumber = scanner.nextLine();
                        foundCustomer.setPhoneNumber(newPhoneNumber);

                        System.out.print("Email Address: ");
                        String newEmailAddress = scanner.nextLine();
                        foundCustomer.setEmailAddress(newEmailAddress);

                        System.out.println("Customer information updated successfully.");

                    } else {
                        System.out.println("Customer was not found in the system based on the code provided");
                    }
                    break;

                case 6:
                    // Modify information about a customer by name
                    System.out.print("Enter the name of the customer to modify: ");
                    customerIdentifier = scanner.nextLine();

                    foundCustomer = stockMS.searchCustomers(customerIdentifier, true);
                    // Search for the customer
                    if (foundCustomer != null) {
                        System.out.println("Enter new customer details:");
                        System.out.print("Name: ");
                        String modName = scanner.nextLine();
                        foundCustomer.setName(modName);

                        System.out.print("Address: ");
                        String newAddress = scanner.nextLine();
                        foundCustomer.setAddress(newAddress);

                        System.out.print("Phone Number: ");
                        String newPhoneNumber = scanner.nextLine();
                        foundCustomer.setPhoneNumber(newPhoneNumber);

                        System.out.print("Email Address: ");
                        String newEmailAddress = scanner.nextLine();
                        foundCustomer.setEmailAddress(newEmailAddress);

                        System.out.println("Customer information updated successfully.");

                    } else {
                        System.out.println("Customer was not found in the system based on the name provided");
                    }

                    break;

                case 7:
                    // Delete a customer by code
                    System.out.print("Enter the code of the customer to delete: ");
                    String cus_code = scanner.nextLine();
                    stockMS.deleteCustomer(cus_code, false);
                    break;

                case 8:
                    // Delete a customer by name
                    System.out.print("Enter the name of the customer to delete: ");
                    String cus_name = scanner.nextLine();
                    stockMS.deleteCustomer(cus_name, true);
                    break;

                case 9:
                    // Add a supplier
                    System.out.println("Enter supplier details:");
                    System.out.print("Code: ");
                    String supp_code = scanner.nextLine();
                    System.out.print("Name: ");
                    String supplierName = scanner.nextLine();
                    System.out.print("Address: ");
                    String supplierAddress = scanner.nextLine();
                    System.out.print("Phone Number: ");
                    String supplierPhoneNumber = scanner.nextLine();
                    System.out.print("Email Address: ");
                    String supplierEmailAddress = scanner.nextLine();

                    Supplier newSup = new Supplier(supp_code, supplierName, supplierAddress, supplierEmailAddress, supplierPhoneNumber);

                    if (stockMS.addSupplier(newSup)) {
                        System.out.println("Supplier added successfully.");
                    }
                    break;

                case 10:
                    // Modify supplier information by Code
                    System.out.print("Enter the code of the supplier to modify: ");
                    String supplierCodeToModify = scanner.nextLine();

                    // Search for the supplier by code
                    Supplier foundSupplier = stockMS.searchSuppliers(supplierCodeToModify, false);

                    // If the supplier is found, proceed with modification
                    if (foundSupplier != null) {
                        System.out.println("Enter updated supplier details:");

                        System.out.print("New Name: ");
                        String updatedName = scanner.nextLine();
                        foundSupplier.setName(updatedName);

                        System.out.print("New Address: ");
                        String updatedAddress = scanner.nextLine();
                        foundSupplier.setAddress(updatedAddress);

                        System.out.print("New Phone Number: ");
                        String updatedPhoneNumber = scanner.nextLine();
                        foundSupplier.setPhoneNumber(updatedPhoneNumber);

                        System.out.print("New Email Address: ");
                        String updatedEmailAddress = scanner.nextLine();
                        foundSupplier.setEmailAddress(updatedEmailAddress);

                        System.out.println("Supplier information updated successfully.");
                    } else {
                        System.out.println("Supplier not found.");
                    }
                    break;
                case 11:
                    // Modify supplier information by Name
                    System.out.print("Enter the Name of the supplier to modify: ");
                    String supplierNameToModify = scanner.nextLine();

                    // Search for the supplier by name
                    foundSupplier = stockMS.searchSuppliers(supplierNameToModify, true);

                    // If the supplier is found, proceed with modification
                    if (foundSupplier != null) {
                        System.out.println("Enter updated supplier details:");

                        System.out.print("New Name: ");
                        String updatedName = scanner.nextLine();
                        foundSupplier.setName(updatedName);

                        System.out.print("New Address: ");
                        String updatedAddress = scanner.nextLine();
                        foundSupplier.setAddress(updatedAddress);

                        System.out.print("New Phone Number: ");
                        String updatedPhoneNumber = scanner.nextLine();
                        foundSupplier.setPhoneNumber(updatedPhoneNumber);

                        System.out.print("New Email Address: ");
                        String updatedEmailAddress = scanner.nextLine();
                        foundSupplier.setEmailAddress(updatedEmailAddress);

                        System.out.println("Supplier information updated successfully.");
                    } else {
                        System.out.println("Supplier not found.");
                    }
                    break;

                case 12:
                    // Delete a supplier by code
                    System.out.print("Enter the code of the Supplier to delete: ");
                    String sup_code = scanner.nextLine();
                    stockMS.deleteSupplier(sup_code, false);
                    break;
                case 13:
                    // Delete a supplier by name
                    System.out.print("Enter the name of the supplier to delete: ");
                    sup_code = scanner.nextLine();
                    stockMS.deleteSupplier(sup_code, true);
                    break;

                case 14:
                    // Search for a product by code
                    System.out.print("Enter the product code: ");
                    String productCodeToSearch = scanner.nextLine();

                    // Search for the product by code
                    Product productByCode = stockMS.searchProductByCode(productCodeToSearch);

                    // If the product is found, display its information
                    if (productByCode != null) {
                        System.out.println("Product found:");
                        productByCode.display();
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;

                case 15:
                    // Search for a product by name
                    System.out.print("Enter the product name: ");
                    String productNameToSearch = scanner.nextLine(); // Trim the input

                    // Search for the product by name
                    Product productByName = stockMS.searchProductByName(productNameToSearch);

                    // If the product is found, display its information
                    if (productByName != null) {                       /*****Enter your choice: 15nEter the product name: Chocolate Product not found.*/
                        System.out.println("Product found:");
                        productByName.display();
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;

                case 16:
                    //Search for a customer by code
                    System.out.print("Enter the customer code: ");
                    String customerCodeToSearch = scanner.nextLine();
                    Customer customerByCode = stockMS.searchCustomers(customerCodeToSearch, false);

                    // If the Customer is found, display its information
                    if (customerByCode != null) {
                        System.out.println("Customer found:");
                        customerByCode.display();
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                case 17:
                    //Search for a customer by name
                    System.out.print("Enter the customer name: ");
                    String customerNameToSearch = scanner.nextLine();

                    Customer customerByName = stockMS.searchCustomers(customerNameToSearch, true);


                    // If the Customer is found, display its information
                    if (customerByName != null) {
                        System.out.println("Customer found:");
                        customerByName.display();
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                case 18:
                    //Search for a supplier by code
                    System.out.print("Enter the supplier code: ");
                    String supplierCodeToSearch = scanner.nextLine();


                    Supplier supplierByCode = stockMS.searchSuppliers(supplierCodeToSearch, false);

                    // If the supplier is found, display its information
                    if (supplierByCode != null) {
                        System.out.println("supplier found:");
                        supplierByCode.display();
                    } else {
                        System.out.println("supplier not found.");
                    }
                    break;

                case 19:
                    //Search for a supplier by name
                    System.out.print("Enter the supplier name: ");
                    String supplierNameToSearch = scanner.nextLine();


                    Supplier supplierByName = stockMS.searchSuppliers(supplierNameToSearch, true);

                    // If the supplier is found, display its information
                    if (supplierByName != null) {
                        System.out.println("supplier found:");
                        supplierByName.display();
                    } else {
                        System.out.println("supplier not found.");
                    }
                    break;

                case 20:
                    // Set minimum and maximum stock levels for a product
                    System.out.print("Enter the product code: ");
                    String productCodeForLevels = scanner.nextLine();

                    // Search for the product by code to set stock levels
                    Product productForLevels = stockMS.searchProductByCode(productCodeForLevels);

                    // If the product is found, prompt the user to enter minimum and maximum stock levels
                    if (productForLevels != null) {
                        boolean validInput = false;
                        minStockLevel = 0;
                        while (!validInput) {
                            try {
                                System.out.print("Enter minimum stock level: ");
                                minStockLevel = Integer.parseInt(scanner.nextLine());
                                validInput = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a valid integer for the minimum stock level.");
                            }
                        }

                        validInput = false;
                        maxStockLevel = 0;
                        while (!validInput) {
                            try {
                                System.out.print("Enter maximum stock level: ");
                                maxStockLevel = Integer.parseInt(scanner.nextLine());
                                validInput = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a valid integer for the maximum stock level.");
                            }
                        }

                        // Set minimum and maximum stock levels for the product
                        productForLevels.setMinStockLevel(minStockLevel);
                        productForLevels.setMaxStockLevel(maxStockLevel);

                        System.out.println("Minimum and maximum stock levels set successfully.");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;

                case 21:
                    Product[] products = stockMS.getProductsArray();
                    if (products != null) {
                        for (int i = 0; i < products.length; i++) {
                            // Check for null before invoking methods
                            if (products[i] != null) {
                                if (products[i].checkRestockingAlert()) {
                                    System.out.println("Product Code: " + products[i].getCode() + " Product Name: " + products[i].getName() + " Needs Restocking");
                                } else if (products[i].checkMaxAlert()) {
                                    System.out.println("Product Code: " + products[i].getCode() + " Product Name: " + products[i].getName() + " Reached Maximum level");
                                }
                            }
                        }
                    } else {
                        System.out.println("No product data available");
                    }
                    break;

                case 22:
                    Customer customerByCo = null;

                    // If the Customer is found, display its information
                    while (customerByCo == null) {
                        //Search for a customer by code
                        System.out.print("Enter the customer code: ");
                        String customerCo = scanner.nextLine();
                        customerByCo = stockMS.searchCustomers(customerCo, false);
                        if (customerByCo != null) {
                            System.out.println("Customer found:");
                            HashMap<Product, SalesInfo> salesLine = new HashMap<Product, SalesInfo>();

                            System.out.print("Enter the number of products in this sales order: ");
                            int numberOfProducts = scanner.nextInt();

                            for (int i = 0; i < numberOfProducts; i++) {
                                Product pr = null;
                                while (pr == null) {
                                    System.out.print("Enter the product code: ");
                                    String productCo = scanner.nextLine();
                                    pr = stockMS.searchProductByCode(productCo);
                                    if (pr != null) {
                                        System.out.println("Product found:");
                                        System.out.print("Enter the product quantity sold: ");
                                        int qty = scanner.nextInt();
                                        scanner.nextLine(); // Consume newline character
                                        System.out.print("Enter the product selling price: ");
                                        double sellPrice = scanner.nextDouble();
                                        scanner.nextLine(); // Consume newline character
                                        System.out.print("Enter the discount given in 100:");
                                        double discount = scanner.nextDouble();
                                        scanner.nextLine(); // Consume newline character
                                        SalesInfo si = new SalesInfo(qty, discount, sellPrice);
                                        salesLine.put(pr, si);

                                    /*else {
                                         System.out.println("Product not found:");
                                         // Skip the "Product not found:" message when the product is actually found
                                        // break;*/
                                    }

                                }

                            }
                            System.out.print("Enter Sales Order Id");
                            String sales_order_id = scanner.nextLine();
                            SalesOrder so = new SalesOrder(sales_order_id,customerByCo, salesLine);
                            srm.insertSalesOrder(so);
                            System.out.println("Sales Order completed succesfully");
                        } else {
                            System.out.println("Customer not found:");
                        }
                    }
                    break;

                case 23:
                    // Execute the next sales order
                    SalesOrder nextOrder = srm.executeNextSalesOrder();
                    break;
                case 24:

                    srm.showExecutedSalesOrders();
                    break;

                case 25:
                    // Execute sales return
                    System.out.print("Enter the customer code: ");
                    customerCode = scanner.nextLine();
                    Customer customer = stockMS.searchCustomers(customerCode, false); //  search for customers

                    if (customer != null) {
                        System.out.print("Enter the product code: ");
                        String productCode = scanner.nextLine();
                        Product product = stockMS.searchProductByCode(productCode); // search for products

                        if (product != null) {
                            System.out.print("Enter Related Sales Order Id");
                            String sales_order_id = scanner.nextLine();

                            SalesOrder so = srm.findExecutedSalesOrderByID(sales_order_id);
                            if (so!=null){
                                System.out.print("Enter the quantity for the sales return: ");
                                quantity = scanner.nextInt();

                                // Execute the sales return
                                boolean salesReturnExecuted = srm.executeSalesReturn(customer, product, quantity);

                                if (salesReturnExecuted) {
                                    System.out.println("Sales return executed successfully.");
                                } else {
                                    System.out.println("No sales return executed or no sales return available.");
                                }
                                }
                            else{
                                System.out.println("Sales Order not found.");
                            }
                        } else {
                            System.out.println("Product not found.");
                        }
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                case 26:
                    stockMS.showCustomers();
                    break;
                case 27:
                    stockMS.showSuppliers();
                    break;
                case 28:
                    stockMS.showProducts();
                    break;
                case 29:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 25");

            }

        }


    }
}
