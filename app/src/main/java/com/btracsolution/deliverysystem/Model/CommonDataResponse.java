package com.btracsolution.deliverysystem.Model;

import com.google.gson.annotations.Expose;

/**
 * Created by mahmudul.hasan on 2/14/2018.
 */

public class CommonDataResponse {


    String status;
    String message;

    @Expose
    LoginModel.ErrorData error;

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
}
