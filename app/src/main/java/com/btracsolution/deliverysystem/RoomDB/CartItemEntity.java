package com.btracsolution.deliverysystem.RoomDB;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CartItemEntity{


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "food_id")
    String foodId;
    @ColumnInfo(name = "name")
    String foodName;
    @ColumnInfo(name = "detail")
    String foodDetail;
    @ColumnInfo(name = "pic")
    String foodPicture;
    @ColumnInfo(name = "status")
    String foodStatus;
    @ColumnInfo(name = "price")
    String foodPrice;
    @ColumnInfo(name = "quantity")
    int quantity;

    public CartItemEntity() {
    }

    public CartItemEntity(String foodId, String foodName, String foodDetail, String foodPicture, String foodStatus, String foodPrice, int qty) {
        this.foodId=foodId;
        this.foodName=foodName;
        this.foodDetail=foodDetail;
        this.foodPicture=foodPicture;
        this.foodStatus=foodStatus;
        this.foodPrice=foodPrice;
        this.quantity=qty;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    boolean isHeader = false;
    String headerTitle;

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
