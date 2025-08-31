package com.btracsolution.deliverysystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mahmudul.hasan on 2/6/2018.
 */

public class RiderAssignModel {


    String status;
    String message;
    @Expose
    ErrorData error;

    @SerializedName("data")
    @Expose
    ArrayList<OrderDetailsModel.orderBasicData> orderBasicDatas;

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

    public ArrayList<OrderDetailsModel.orderBasicData> getOrderBasicDatas() {
        return orderBasicDatas;
    }

    public void setOrderBasicDatas(ArrayList<OrderDetailsModel.orderBasicData> orderBasicDatas) {
        this.orderBasicDatas = orderBasicDatas;
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
