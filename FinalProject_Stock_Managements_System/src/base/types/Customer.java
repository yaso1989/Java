package base.types;

import java.util.Objects;

public class Customer implements DealerInt {

    String cust_code;
    CustPurchaseHistory custPurchaseHistory;

    String name;
     String emailAddress;
    String phoneNumber;
    String address;

    boolean active;
    public Customer(String cust_code,String name, String emailAddress, String phoneNumber, String address){
        this.cust_code = cust_code;
        this.name=name;
        this.emailAddress=emailAddress;
        this.phoneNumber=phoneNumber;
        this.address=address;
        this.active = true;
        this.custPurchaseHistory = new CustPurchaseHistory();
    }
    public String getCust_code() {
        return cust_code;
    }

    public void setCust_code(String cust_code) {
        this.cust_code = cust_code;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "base.types.Customer{" +
                "cust_code='" + cust_code + '\'' +
                ", name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", active=" + active +
                '}';
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public void display() {
        System.out.println("cust_code='" + cust_code + "\n" + "The CustomerName is " + this.name + "\n" +
                "The base.types.Customer address is " + this.address + "\n" +
                "The Customer_EmailAddress is " + this.emailAddress + "\n" +
                "The Customer_phoneNumber is " + this.phoneNumber);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(cust_code, customer.cust_code) ;
    }

    public CustPurchaseHistory getCustPurchaseHistory() {
        return custPurchaseHistory;
    }

    public void setCustPurchaseHistory(CustPurchaseHistory custPurchaseHistory) {
        this.custPurchaseHistory = custPurchaseHistory;
    }
}
