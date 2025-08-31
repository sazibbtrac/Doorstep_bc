package com.btracsolution.deliverysystem.Model;

/**
 * Created by mahmudul.hasan on 3/6/2018.
 */

public class AgentJobListHeaderModel {
    private String TypeOfHeader;
    private String TotalOrder;

    public String getTypeOfHeader() {
        return TypeOfHeader;
    }

    public void setTypeOfHeader(String typeOfHeader) {
        TypeOfHeader = typeOfHeader;
    }

    public String getTotalOrder() {
        return TotalOrder;
    }

    public void setTotalOrder(String totalOrder) {
        TotalOrder = totalOrder;
    }
}
