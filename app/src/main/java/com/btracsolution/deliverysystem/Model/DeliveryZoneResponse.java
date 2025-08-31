package com.btracsolution.deliverysystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryZoneResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("zones")
    @Expose
    private List<Zone> data = null;

    @SerializedName("tableno")
    @Expose
    private List<TableNo> tableNos = null;


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

    public List<Zone> getData() {
        return data;
    }

    public void setData(List<Zone> data) {
        this.data = data;
    }

    public List<TableNo> getTableNos() {
        return tableNos;
    }

    public void setTableNos(List<TableNo> tableNos) {
        this.tableNos = tableNos;
    }

    public class Zone {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("zonename")
        @Expose
        private String zonename;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getZonename() {
            return zonename;
        }

        public void setZonename(String zonename) {
            this.zonename = zonename;
        }

    }


    public class TableNo {

        @SerializedName("tableid")
        @Expose
        private Integer table_id;
        @SerializedName("tablename")
        @Expose
        private String tablename;

        public Integer getTable_id() {
            return table_id;
        }

        public void setTable_id(Integer table_id) {
            this.table_id = table_id;
        }

        public String getTablename() {
            return tablename;
        }

        public void setTablename(String zonename) {
            this.tablename = zonename;
        }

    }
}