package com.btracsolution.deliverysystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class PushModelData {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    @Generated("jsonschema2pojo")
    public class Datum {

        @SerializedName("orderstatus")
        @Expose
        private Integer orderstatus;
        @SerializedName("discount")
        @Expose
        private Integer discount;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("orderfrom")
        @Expose
        private Integer orderfrom;
        @SerializedName("deliverydate")
        @Expose
        private Object deliverydate;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("discountamount")
        @Expose
        private String discountamount;
        @SerializedName("deliveryzone")
        @Expose
        private Deliveryzone deliveryzone;
        @SerializedName("ordernumber")
        @Expose
        private String ordernumber;
        @SerializedName("member")
        @Expose
        private Member member;
        @SerializedName("tableid")
        @Expose
        private Object tableid;
        @SerializedName("paymentstatus")
        @Expose
        private Integer paymentstatus;
        @SerializedName("kitchenid")
        @Expose
        private Integer kitchenid;
        @SerializedName("servicecharge")
        @Expose
        private Integer servicecharge;
        @SerializedName("member_id")
        @Expose
        private String memberId;
        @SerializedName("shippingaddress")
        @Expose
        private String shippingaddress;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("orderid")
        @Expose
        private String orderid;
        @SerializedName("vat")
        @Expose
        private Integer vat;
        @SerializedName("vatamount")
        @Expose
        private Integer vatamount;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("isapporder")
        @Expose
        private Integer isapporder;
        @SerializedName("deliverycharge")
        @Expose
        private Integer deliverycharge;
        @SerializedName("totalamount")
        @Expose
        private Integer totalamount;
        @SerializedName("locationid")
        @Expose
        private Integer locationid;
        @SerializedName("updated_by")
        @Expose
        private Object updatedBy;
        @SerializedName("waiter")
        @Expose
        private Waiter waiter;
        @SerializedName("specialinstruction")
        @Expose
        private Object specialinstruction;
        @SerializedName("paymentmethod")
        @Expose
        private Integer paymentmethod;
        @SerializedName("servicechargeamount")
        @Expose
        private Integer servicechargeamount;
        @SerializedName("remarks")
        @Expose
        private String remarks;
        @SerializedName("orderdetail")
        @Expose
        private String orderdetail;
        @SerializedName("tableno")
        @Expose
        private Tableno tableno;

        public Integer getOrderstatus() {
            return orderstatus;
        }

        public void setOrderstatus(Integer orderstatus) {
            this.orderstatus = orderstatus;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Integer getOrderfrom() {
            return orderfrom;
        }

        public void setOrderfrom(Integer orderfrom) {
            this.orderfrom = orderfrom;
        }

        public Object getDeliverydate() {
            return deliverydate;
        }

        public void setDeliverydate(Object deliverydate) {
            this.deliverydate = deliverydate;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getDiscountamount() {
            return discountamount;
        }

        public void setDiscountamount(String discountamount) {
            this.discountamount = discountamount;
        }

        public Deliveryzone getDeliveryzone() {
            return deliveryzone;
        }

        public void setDeliveryzone(Deliveryzone deliveryzone) {
            this.deliveryzone = deliveryzone;
        }

        public String getOrdernumber() {
            return ordernumber;
        }

        public void setOrdernumber(String ordernumber) {
            this.ordernumber = ordernumber;
        }

        public Member getMember() {
            return member;
        }

        public void setMember(Member member) {
            this.member = member;
        }

        public Object getTableid() {
            return tableid;
        }

        public void setTableid(Object tableid) {
            this.tableid = tableid;
        }

        public Integer getPaymentstatus() {
            return paymentstatus;
        }

        public void setPaymentstatus(Integer paymentstatus) {
            this.paymentstatus = paymentstatus;
        }

        public Integer getKitchenid() {
            return kitchenid;
        }

        public void setKitchenid(Integer kitchenid) {
            this.kitchenid = kitchenid;
        }

        public Integer getServicecharge() {
            return servicecharge;
        }

        public void setServicecharge(Integer servicecharge) {
            this.servicecharge = servicecharge;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getShippingaddress() {
            return shippingaddress;
        }

        public void setShippingaddress(String shippingaddress) {
            this.shippingaddress = shippingaddress;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public Integer getVat() {
            return vat;
        }

        public void setVat(Integer vat) {
            this.vat = vat;
        }

        public Integer getVatamount() {
            return vatamount;
        }

        public void setVatamount(Integer vatamount) {
            this.vatamount = vatamount;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public Integer getIsapporder() {
            return isapporder;
        }

        public void setIsapporder(Integer isapporder) {
            this.isapporder = isapporder;
        }

        public Integer getDeliverycharge() {
            return deliverycharge;
        }

        public void setDeliverycharge(Integer deliverycharge) {
            this.deliverycharge = deliverycharge;
        }

        public Integer getTotalamount() {
            return totalamount;
        }

        public void setTotalamount(Integer totalamount) {
            this.totalamount = totalamount;
        }

        public Integer getLocationid() {
            return locationid;
        }

        public void setLocationid(Integer locationid) {
            this.locationid = locationid;
        }

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Object updatedBy) {
            this.updatedBy = updatedBy;
        }

        public Waiter getWaiter() {
            return waiter;
        }

        public void setWaiter(Waiter waiter) {
            this.waiter = waiter;
        }

        public Object getSpecialinstruction() {
            return specialinstruction;
        }

        public void setSpecialinstruction(Object specialinstruction) {
            this.specialinstruction = specialinstruction;
        }

        public Integer getPaymentmethod() {
            return paymentmethod;
        }

        public void setPaymentmethod(Integer paymentmethod) {
            this.paymentmethod = paymentmethod;
        }

        public Integer getServicechargeamount() {
            return servicechargeamount;
        }

        public void setServicechargeamount(Integer servicechargeamount) {
            this.servicechargeamount = servicechargeamount;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getOrderdetail() {
            return orderdetail;
        }

        public void setOrderdetail(String orderdetail) {
            this.orderdetail = orderdetail;
        }

        public Tableno getTableno() {
            return tableno;
        }

        public void setTableno(Tableno tableno) {
            this.tableno = tableno;
        }

    }

    public class Deliveryzone {

        @SerializedName("zoneid")
        @Expose
        private Integer zoneid;
        @SerializedName("zonename")
        @Expose
        private String zonename;

        public Integer getZoneid() {
            return zoneid;
        }

        public void setZoneid(Integer zoneid) {
            this.zoneid = zoneid;
        }

        public String getZonename() {
            return zonename;
        }

        public void setZonename(String zonename) {
            this.zonename = zonename;
        }

    }

    @Generated("jsonschema2pojo")
    public class Member {

        @SerializedName("member_id")
        @Expose
        private String memberId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("contactno")
        @Expose
        private String contactno;

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
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

    }
    public class Waiter {

        @SerializedName("fullname")
        @Expose
        private String fullname;
        @SerializedName("userid")
        @Expose
        private Integer userid;
        @SerializedName("contactno")
        @Expose
        private String contactno;

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public String getContactno() {
            return contactno;
        }

        public void setContactno(String contactno) {
            this.contactno = contactno;
        }

    }


    @Generated("jsonschema2pojo")
    public class Tableno {

        @SerializedName("tableid")
        @Expose
        private Integer tableid;
        @SerializedName("tablename")
        @Expose
        private String tablename;

        public Integer getTableid() {
            return tableid;
        }

        public void setTableid(Integer tableid) {
            this.tableid = tableid;
        }

        public String getTablename() {
            return tablename;
        }

        public void setTablename(String tablename) {
            this.tablename = tablename;
        }

    }

}