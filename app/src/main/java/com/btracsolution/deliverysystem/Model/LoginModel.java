package com.btracsolution.deliverysystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahmudul.hasan on 2/6/2018.
 */

public class LoginModel {

    String status;
    String message;
    @Expose
    ResponseData data;
    @Expose
    ErrorData error;

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

    public ErrorData getError() {
        return error;
    }

    public void setError(ErrorData error) {
        this.error = error;
    }

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }

    public class ResponseData {
        String apiToken;
        String userId;
        String fullName;
        String mobileNo;
        String userEmail;
        String userType;
        RestaurantInfo restaurantInfo;
        @SerializedName("kitchenInfo")
        @Expose
        private List<KitchenInfo> kitchenInfo = null;

      //  ArrayList<branchInfo> branchInfo;

        public List<KitchenInfo> getKitchenInfo() {
            return kitchenInfo;
        }

        public void setKitchenInfo(List<KitchenInfo> kitchenInfo) {
            this.kitchenInfo = kitchenInfo;
        }
        public String getApiToken() {
            return apiToken;
        }

        public void setApiToken(String apiToken) {
            this.apiToken = apiToken;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public RestaurantInfo getRestaurantInfo() {
            return restaurantInfo;
        }

        public void setRestaurantInfo(RestaurantInfo restaurantInfo) {
            this.restaurantInfo = restaurantInfo;
        }

    }


    public class RestaurantInfo {
        String restaurantid;
        String name;
        String contactno;
        String email;
        String address;
        String websiteurl;

        public String getRestaurantid() {
            return restaurantid;
        }

        public void setRestaurantid(String restaurantid) {
            this.restaurantid = restaurantid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContactno() {
            return contactno;
        }

        public void setContactno(String contactno) {
            this.contactno = contactno;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getWebsiteurl() {
            return websiteurl;
        }

        public void setWebsiteurl(String websiteurl) {
            this.websiteurl = websiteurl;
        }
    }


    public class KitchenInfo {

        @SerializedName("kitchenId")
        @Expose
        private Integer kitchenId;
        @SerializedName("kitchenName")
        @Expose
        private String kitchenName;
        @SerializedName("kitchenAddress")
        @Expose
        private String kitchenAddress;
        @SerializedName("kitchenMobile")
        @Expose
        private String kitchenMobile;
        @SerializedName("kitchenEmail")
        @Expose
        private String kitchenEmail;

        public Integer getKitchenId() {
            return kitchenId;
        }

        public void setKitchenId(Integer kitchenId) {
            this.kitchenId = kitchenId;
        }

        public String getKitchenName() {
            return kitchenName;
        }

        public void setKitchenName(String kitchenName) {
            this.kitchenName = kitchenName;
        }

        public String getKitchenAddress() {
            return kitchenAddress;
        }

        public void setKitchenAddress(String kitchenAddress) {
            this.kitchenAddress = kitchenAddress;
        }

        public String getKitchenMobile() {
            return kitchenMobile;
        }

        public void setKitchenMobile(String kitchenMobile) {
            this.kitchenMobile = kitchenMobile;
        }

        public String getKitchenEmail() {
            return kitchenEmail;
        }

        public void setKitchenEmail(String kitchenEmail) {
            this.kitchenEmail = kitchenEmail;
        }

    }

    public class ErrorData {
        String error_code;
        String error_msg;

        public String getError_code() {
            return error_code;
        }

        public void setError_code(String error_code) {
            this.error_code = error_code;
        }

        public String getError_msg() {
            return error_msg;
        }

        public void setError_msg(String error_msg) {
            this.error_msg = error_msg;
        }
    }

}
