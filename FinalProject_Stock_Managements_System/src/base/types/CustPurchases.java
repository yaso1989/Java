package base.types;

public class CustPurchases {



    private int quantity;
    private double salesAmt;

    public CustPurchases(int quantity, double salesAmt) {
        this.quantity = quantity;
        this.salesAmt = salesAmt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSalesAmt() {
        return salesAmt;
    }

    public void setSalesAmt(double salesAmt) {
        this.salesAmt = salesAmt;
    }
}
