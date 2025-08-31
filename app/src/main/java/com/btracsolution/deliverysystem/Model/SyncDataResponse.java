package com.btracsolution.deliverysystem.Model;

import java.util.ArrayList;

public class SyncDataResponse {
    // import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */

    public String status;

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

    public String message;
    public Data data;

    public class Data{
        public int kotID;
        public String compID;
        public String kotNo;
        public String kotDate;
        public String kotTime;
        public String mem_code;
        public String stid;
        public String empID;
        public String remarks;
        public String st;
        public String userID;
        public String salesID;
        public String dpid;
        public ArrayList<KotDtlsBCL> kotDtlsBCL;
    }

    public class KotDtlsBCL{
        public int kotDID;
        public int kotID;
        public String compID;
        public String kotNo;
        public String typeID;
        public String catID;
        public String prdID;
        public int qty;
        public String stid;
        public String rmks;
        public String prodName;
    }






}
