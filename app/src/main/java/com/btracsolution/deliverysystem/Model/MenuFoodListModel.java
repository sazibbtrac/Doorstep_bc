package com.btracsolution.deliverysystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mahmudul.hasan on 2/13/2018.
 */


/*{
        status: "success",
        message: "All Menu List ",
        data: [{
        categoryId: 1,
        categoryName: "FLAME-GRILLED BURGERS",
        categoryDetail: null,
        categoryType: 1,
        categoryStatus: 1,
        foodgroups: {
        foodgroupId: 4,
        foodgroupName: "Creamy Cheeseburger",
        foodgroupDetail: "A signature flame-grilled. beef patty topped with a simple layer of melted American cheese, crinkle cut pickles, yellow mustard, and ketchup on a toasted sesame seed bun.",
        foods: {
        foodId: 1,
        foodName: "Creamy Cheeseburger",
        foodDetail: "A signature flame-grilled. beef patty topped with a simple layer of melted American cheese, crinkle cut pickles, yellow mustard, and ketchup on a toasted sesame seed bun.",
        foodPicture: "http://103.9.185.218/deliverysystem/public/upload/menu/thumbnail_images/1515307862_thumb_Creamy Cheeseburger.png",
        foodStatus: 1
        }
        }
        },
        {
        categoryId: 2,
        categoryName: "CHICKEN",
        categoryDetail: null,
        categoryType: 1,
        categoryStatus: 1,
        foodgroups: {
        foodgroupId: 3,
        foodgroupName: "Test",
        foodgroupDetail: "test",
        foods: {
        foodId: 15,
        foodName: "test",
        foodDetail: "test",
        foodPicture: "http://103.9.185.218/deliverysystem/public/upload/menu/thumbnail_images/1517374235_thumb_test.jpg",
        foodStatus: 1
        }
        }
        }
        ]
        }*/
public class MenuFoodListModel {

    String status;
    String message;
    @SerializedName("data")
    @Expose
    ArrayList<MenuData> menuData;

    @Expose
    LoginModel.ErrorData error;


    public LoginModel.ErrorData getError() {
        return error;
    }

    public void setError(LoginModel.ErrorData error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<MenuData> getMenuData() {
        return menuData;
    }

    public void setMenuData(ArrayList<MenuData> menuData) {
        this.menuData = menuData;
    }

    public class MenuData {
        String categoryId;
        String categoryName;
        String categoryDetail;
        String categoryType;
        String categoryStatus;
        ArrayList<FoodGroups> foodgroups;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryDetail() {
            return categoryDetail;
        }

        public void setCategoryDetail(String categoryDetail) {
            this.categoryDetail = categoryDetail;
        }

        public String getCategoryType() {
            return categoryType;
        }

        public void setCategoryType(String categoryType) {
            this.categoryType = categoryType;
        }

        public String getCategoryStatus() {
            return categoryStatus;
        }

        public void setCategoryStatus(String categoryStatus) {
            this.categoryStatus = categoryStatus;
        }

        public ArrayList<FoodGroups> getFoodgroups() {
            return foodgroups;
        }

        public void setFoodgroups(ArrayList<FoodGroups> foodgroups) {
            this.foodgroups = foodgroups;
        }
    }

    public class FoodGroups {
        String foodgroupId;
        String foodgroupName;
        String foodgroupDetail;
        ArrayList<FoodName> foods;

        public FoodGroups(String foodgroupId, String foodgroupName, String foodgroupDetail) {
            this.foodgroupId = foodgroupId;
            this.foodgroupName = foodgroupName;
            this.foodgroupDetail = foodgroupDetail;
        }

        public String getFoodgroupId() {
            return foodgroupId;
        }

        public void setFoodgroupId(String foodgroupId) {
            this.foodgroupId = foodgroupId;
        }

        public String getFoodgroupName() {
            return foodgroupName;
        }

        public void setFoodgroupName(String foodgroupName) {
            this.foodgroupName = foodgroupName;
        }

        public String getFoodgroupDetail() {
            return foodgroupDetail;
        }

        public void setFoodgroupDetail(String foodgroupDetail) {
            this.foodgroupDetail = foodgroupDetail;
        }

        public ArrayList<FoodName> getFoods() {
            return foods;
        }

        public void setFoods(ArrayList<FoodName> foods) {
            this.foods = foods;
        }
    }

    public class FoodName {
        String foodId;
        String foodName;
        int kitchenid;
        String foodDetail;
        String foodPicture;
        String foodStatus;
        String foodPrice;

        public int getKitchenid() {
            return kitchenid;
        }

        public void setKitchenid(int kitchenid) {
            this.kitchenid = kitchenid;
        }

        public String getFoodPrice() {
            return foodPrice;
        }

        public void setFoodPrice(String foodPrice) {
            this.foodPrice = foodPrice;
        }

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
    }
}
