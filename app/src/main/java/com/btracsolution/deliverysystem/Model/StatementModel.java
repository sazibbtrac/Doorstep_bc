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
public class StatementModel {

    String status;
    String message;

    @SerializedName("data")
    ResponseData responseData;

    @Expose
    LoginModel.ErrorData error;

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

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

    public class ResponseData {

        @SerializedName("total")
        @Expose
        GrandCalculation grandCalculation;


        @SerializedName("datewise")
        @Expose
        ArrayList<IndividualData> individualDates;

        public GrandCalculation getGrandCalculation() {
            return grandCalculation;
        }

        public void setGrandCalculation(GrandCalculation grandCalculation) {
            this.grandCalculation = grandCalculation;
        }

        public ArrayList<IndividualData> getIndividualDates() {
            return individualDates;
        }

        public void setIndividualDates(ArrayList<IndividualData> individualDates) {
            this.individualDates = individualDates;
        }
    }

    public class GrandCalculation {
   /*     total: {
        totalOrder: 3,
                totalAmount: 3553,
                totalVatamount: 0,
                totalDeliverycharge: 180
    },*/

        int totalOrder;
        int subTotal;
        int grandTotal;
        int totalVatamount;
        int totalDeliverycharge;

        public int getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(int subTotal) {
            this.subTotal = subTotal;
        }

        public int getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(int grandTotal) {
            this.grandTotal = grandTotal;
        }

        public int getTotalOrder() {
            return totalOrder;
        }

        public void setTotalOrder(int totalOrder) {
            this.totalOrder = totalOrder;
        }


        public int getTotalVatamount() {
            return totalVatamount;
        }

        public void setTotalVatamount(int totalVatamount) {
            this.totalVatamount = totalVatamount;
        }

        public int getTotalDeliverycharge() {
            return totalDeliverycharge;
        }

        public void setTotalDeliverycharge(int totalDeliverycharge) {
            this.totalDeliverycharge = totalDeliverycharge;
        }
    }

    public class IndividualData {
   /*   orderDate: "2018-03-18",
totalOrder: 1,
totalAmount: 1626,
totalVatamount: 0,
totalDeliverycharge: 60*/

        String orderDate;
        int totalOrder;
        int subTotal;
        int totalVatamount;
        int totalDeliverycharge;
        int grandTotal;

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public int getTotalOrder() {
            return totalOrder;
        }

        public void setTotalOrder(int totalOrder) {
            this.totalOrder = totalOrder;
        }

        public int getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(int subTotal) {
            this.subTotal = subTotal;
        }

        public int getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(int grandTotal) {
            this.grandTotal = grandTotal;
        }

        public int getTotalVatamount() {
            return totalVatamount;
        }

        public void setTotalVatamount(int totalVatamount) {
            this.totalVatamount = totalVatamount;
        }

        public int getTotalDeliverycharge() {
            return totalDeliverycharge;
        }

        public void setTotalDeliverycharge(int totalDeliverycharge) {
            this.totalDeliverycharge = totalDeliverycharge;
        }
    }


}
