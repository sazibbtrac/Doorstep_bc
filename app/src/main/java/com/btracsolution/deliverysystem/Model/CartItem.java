package com.btracsolution.deliverysystem.Model;


public class CartItem {



    String foodid;
    String foodName;
    int kitchenid;
    String foodDetail;
    String foodPicture;
    String foodStatus;
    String price;
    int quantity;
    String remarks="";
    boolean isHeader = false;
    String headerTitle;

    public CartItem() {
    }

    public CartItem(String foodId, String foodName,int kitchenid, String foodDetail, String foodPicture, String foodStatus, String foodPrice, int qty,String remarks) {
        this.foodid=foodId;
        this.foodName=foodName;
        this.kitchenid=kitchenid;
        this.foodDetail=foodDetail;
        this.foodPicture=foodPicture;
        this.foodStatus=foodStatus;
        this.price=foodPrice;
        this.quantity=qty;
        this.remarks=remarks;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFoodPrice() {
        return price;
    }

    public void setFoodPrice(String foodPrice) {
        this.price = foodPrice;
    }

    public String getFoodId() {
        return foodid;
    }

    public void setFoodId(String foodId) {
        this.foodid = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDetail() {
        return foodDetail;
    }

    public void setFoodDetail(String foodDetail) {
        this.foodDetail = foodDetail;
    }

    public String getFoodPicture() {
        return foodPicture;
    }

    public void setFoodPicture(String foodPicture) {
        this.foodPicture = foodPicture;
    }

    public String getFoodStatus() {
        return foodStatus;
    }

    public void setFoodStatus(String foodStatus) {
        this.foodStatus = foodStatus;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
}
