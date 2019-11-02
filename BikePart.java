
public class BikePart{
    public String partName="";
    public String partNumber="";
    public String price="";
    public String salesPrice="";
    public boolean onSale=false;
    public int quantity = 0;

    public BikePart(String partName, String partNumber, String price, String salesPrice, boolean onSale,int quantity)
    {
        this.partName=partName;
        this.partNumber=partNumber;
        this.price=price;
        this.salesPrice=salesPrice;
        this.onSale=onSale;
        this.quantity = quantity;
    }

    public String getPartName(){
        return partName;
    }

    public String getPartNumber(){
        return partNumber;
    }

    public void setPartNumber(String number) {partNumber = number;}

    public String getPrice(){
        return price;
    }

    public String getSalesPrice() {
        return salesPrice;
    }

    public boolean isOnSale(){
        return onSale;
    }

    public int getQuantity() {return quantity;}

    public void setQuantity(int i){
        quantity = i;
    }

    public String Stringbikepart(){
        String bikepartstring = new String(this.partName + ", " + this.partNumber + ", " + this.price + ", " + this.salesPrice + ", " + this.onSale
    + ", " + this.quantity);
        return  bikepartstring;
    }

}
