package base.types;

import java.util.HashMap;

public class CustPurchaseHistory {


 HashMap<Product, CustPurchases> productTotals;

 public CustPurchaseHistory(){
     this.productTotals = new HashMap<Product, CustPurchases>();
 }

    public HashMap<Product, CustPurchases> getProductTotals() {
        return productTotals;
    }

    public void setProductTotals(HashMap<Product, CustPurchases> productTotals) {
        this.productTotals = productTotals;
    }

}
