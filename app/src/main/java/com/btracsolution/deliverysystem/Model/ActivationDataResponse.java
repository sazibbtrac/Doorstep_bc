package com.btracsolution.deliverysystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahmudul.hasan on 2/14/2018.
 */

public class ActivationDataResponse {


    String status;
    String message;
    @Expose
    LoginModel.ErrorData error;
    @Expose
    @SerializedName("data")
    ResponseData ResponsData;

    public ResponseData getResponsData() {
        return ResponsData;
    }

    public void setResponsData(ResponseData responsData) {
        ResponsData = responsData;
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

    public LoginModel.ErrorData getError() {
        return error;
    }

    public void setError(LoginModel.ErrorData error) {
        this.error = error;
    }

    public class ResponseData {
        String resetcode;
        String userid;

        public String getResetcode() {
            return resetcode;
        }

        public void setResetcode(String resetcode) {
            this.resetcode = resetcode;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
