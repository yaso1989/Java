package base.types;

import java.util.Objects;

public class Supplier implements DealerInt{



    String supplier_code;
    String address;
    String name;
    String emailAddress;
    String phoneNumber;
    boolean active;
    public Supplier(String supplier_code,String name,String address,String emailAddress,String phoneNumber){
        this.supplier_code = supplier_code;
        this.address=address;
        this.name=name;
        this.emailAddress=emailAddress;
        this.phoneNumber=phoneNumber;
        this.active= true;
    }

    public String getSupplier_code() {
        return supplier_code;
    }

    public void setSupplier_code(String supplier_code) {
        this.supplier_code = supplier_code;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setActive(boolean active) {
        this.active = active;
    }

    public void display() {
        System.out.println("The supplier address is " + this.address + "\n" +
                "The supplier Name is " + this.name + "\n" +
                "The supplier_EmailAddress is " + this.emailAddress + "\n" +
                "The supplier_phoneNumber is " + this.phoneNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Supplier supplier)) return false;
        return Objects.equals(supplier_code, supplier.supplier_code);
    }


}
