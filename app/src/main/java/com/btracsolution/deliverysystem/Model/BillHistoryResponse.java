package com.btracsolution.deliverysystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BillHistoryResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;

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

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }


    public class Billitem {

        @SerializedName("bill_item_id")
        @Expose
        private Integer billItemId;
        @SerializedName("bill_id")
        @Expose
        private Integer billId;
        @SerializedName("orderid")
        @Expose
        private Integer orderid;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("updated_by")
        @Expose
        private Integer updatedBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("order")
        @Expose
        private ArrayList<Order> order = null;
        @SerializedName("orderitem")
        @Expose
        private ArrayList<Orderitem> orderitem = null;

        public Integer getBillItemId() {
            return billItemId;
        }

        public void setBillItemId(Integer billItemId) {
            this.billItemId = billItemId;
        }

        public Integer getBillId() {
            return billId;
        }

        public void setBillId(Integer billId) {
            this.billId = billId;
        }

        public Integer getOrderid() {
            return orderid;
        }

        public void setOrderid(Integer orderid) {
            this.orderid = orderid;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public Integer getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Integer updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public ArrayList<Orderitem> getOrderitem() {
            return orderitem;
        }

        public void setOrderitem(ArrayList<Orderitem> orderitem) {
            this.orderitem = orderitem;
        }

        public ArrayList<Order> getOrder() {
            return order;
        }

        public void setOrder(ArrayList<Order> order) {
            this.order = order;
        }
    }

    public class Datum {

        @SerializedName("bill_id")
        @Expose
        private Integer billId;
        @SerializedName("order_ids")
        @Expose
        private String orderIds;
        @SerializedName("member_id")
        @Expose
        private String memberId;
        @SerializedName("totalbill")
        @Expose
        private Integer totalbill;
        @SerializedName("totalpaid")
        @Expose
        private Integer totalpaid;
        @SerializedName("currentpaid")
        @Expose
        private Integer currentpaid;
        @SerializedName("totaldue")
        @Expose
        private Integer totaldue;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("paymentmethod")
        @Expose
        private Integer paymentmethod;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("updated_by")
        @Expose
        private String updatedBy;
        @SerializedName("billitem")
        @Expose
        private ArrayList<Billitem> billitem = null;
        @SerializedName("member")
        @Expose
        private Member member;

        @SerializedName("sync")
        @Expose
        private Integer sync;


        public Integer getSyncStatus() {
            return sync;
        }

        public void setSyncStatus(Integer sync) {
            this.sync = sync;
        }

        public Integer getBillId() {
            return billId;
        }

        public void setBillId(Integer billId) {
            this.billId = billId;
        }

        public String getOrderIds() {
            return orderIds;
        }

        public void setOrderIds(String orderIds) {
            this.orderIds = orderIds;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public Integer getTotalbill() {
            return totalbill;
        }

        public void setTotalbill(Integer totalbill) {
            this.totalbill = totalbill;
        }

        public Integer getTotalpaid() {
            return totalpaid;
        }

        public void setTotalpaid(Integer totalpaid) {
            this.totalpaid = totalpaid;
        }

        public Integer getCurrentpaid() {
            return currentpaid;
        }

        public void setCurrentpaid(Integer currentpaid) {
            this.currentpaid = currentpaid;
        }

        public Integer getTotaldue() {
            return totaldue;
        }

        public void setTotaldue(Integer totaldue) {
            this.totaldue = totaldue;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public ArrayList<Billitem> getBillitem() {
            return billitem;
        }

        public void setBillitem(ArrayList<Billitem> billitem) {
            this.billitem = billitem;
        }

        public Member getMember() {
            return member;
        }

        public void setMember(Member member) {
            this.member = member;
        }
        public Integer getPaymentmethod() {
            return paymentmethod;
        }

        public void setPaymentmethod(Integer paymentmethod) {
            this.paymentmethod = paymentmethod;
        }

    }

    public class Foodinfo {

        @SerializedName("foodid")
        @Expose
        private Integer foodid;
        @SerializedName("foodname")
        @Expose
        private String foodname;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("ratio")
        @Expose
        private String ratio;
        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;

        public Integer getFoodid() {
            return foodid;
        }

        public void setFoodid(Integer foodid) {
            this.foodid = foodid;
        }

        public String getFoodname() {
            return foodname;
        }

        public void setFoodname(String foodname) {
            this.foodname = foodname;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

    }

    public class Orderitem {

        @SerializedName("orderitemid")
        @Expose
        private Integer orderitemid;
        @SerializedName("orderid")
        @Expose
        private Integer orderid;
        @SerializedName("ordernumber")
        @Expose
        private String ordernumber;
        @SerializedName("kitchenid")
        @Expose
        private Integer kitchenid;
        @SerializedName("foodid")
        @Expose
        private Integer foodid;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("totalprice")
        @Expose
        private Integer totalprice;
        @SerializedName("remarks")
        @Expose
        private String remarks;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_by")
        @Expose
        private String updatedBy;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("foodinfo")
        @Expose
        private Foodinfo foodinfo;

        public Integer getOrderitemid() {
            return orderitemid;
        }

        public void setOrderitemid(Integer orderitemid) {
            this.orderitemid = orderitemid;
        }

        public Integer getOrderid() {
            return orderid;
        }

        public void setOrderid(Integer orderid) {
            this.orderid = orderid;
        }

        public String getOrdernumber() {
            return ordernumber;
        }

        public void setOrdernumber(String ordernumber) {
            this.ordernumber = ordernumber;
        }

        public Integer getKitchenid() {
            return kitchenid;
        }

        public void setKitchenid(Integer kitchenid) {
            this.kitchenid = kitchenid;
        }

        public Integer getFoodid() {
            return foodid;
        }

        public void setFoodid(Integer foodid) {
            this.foodid = foodid;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public Integer getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(Integer totalprice) {
            this.totalprice = totalprice;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Foodinfo getFoodinfo() {
            return foodinfo;
        }

        public void setFoodinfo(Foodinfo foodinfo) {
            this.foodinfo = foodinfo;
        }

    }

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

    public class Order {

        @SerializedName("orderid")
        @Expose
        private Integer orderid;
        @SerializedName("ordernumber")
        @Expose
        private String ordernumber;
        @SerializedName("kitchenid")
        @Expose
        private Integer kitchenid;
        @SerializedName("member_id")
        @Expose
        private String memberId;
        @SerializedName("locationid")
        @Expose
        private Integer locationid;
        @SerializedName("tableid")
        @Expose
        private Integer tableid;
        @SerializedName("orderstatus")
        @Expose
        private Integer orderstatus;
        @SerializedName("shippingaddress")
        @Expose
        private String shippingaddress;
        @SerializedName("paymentmethod")
        @Expose
        private Integer paymentmethod;
        @SerializedName("paymentstatus")
        @Expose
        private Integer paymentstatus;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("discount")
        @Expose
        private Integer discount;
        @SerializedName("discountamount")
        @Expose
        private String discountamount;
        @SerializedName("vat")
        @Expose
        private Integer vat;
        @SerializedName("vatamount")
        @Expose
        private Integer vatamount;
        @SerializedName("servicecharge")
        @Expose
        private Integer servicecharge;
        @SerializedName("servicechargeamount")
        @Expose
        private Integer servicechargeamount;
        @SerializedName("deliverycharge")
        @Expose
        private Integer deliverycharge;
        @SerializedName("totalamount")
        @Expose
        private Integer totalamount;
        @SerializedName("orderdetail")
        @Expose
        private Object orderdetail;
        @SerializedName("remarks")
        @Expose
        private Object remarks;
        @SerializedName("specialinstruction")
        @Expose
        private Object specialinstruction;
        @SerializedName("deliverydate")
        @Expose
        private Object deliverydate;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_by")
        @Expose
        private Object updatedBy;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("isapporder")
        @Expose
        private Integer isapporder;
        @SerializedName("orderfrom")
        @Expose
        private Integer orderfrom;
        @SerializedName("waiter")
        @Expose
        private Waiter waiter;
        @SerializedName("deliveryzone")
        @Expose
        private Deliveryzone deliveryzone;

        public Integer getOrderid() {
            return orderid;
        }

        public void setOrderid(Integer orderid) {
            this.orderid = orderid;
        }

        public String getOrdernumber() {
            return ordernumber;
        }

        public void setOrdernumber(String ordernumber) {
            this.ordernumber = ordernumber;
        }

        public Integer getKitchenid() {
            return kitchenid;
        }

        public void setKitchenid(Integer kitchenid) {
            this.kitchenid = kitchenid;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public Integer getLocationid() {
            return locationid;
        }

        public void setLocationid(Integer locationid) {
            this.locationid = locationid;
        }

        public Integer getTableid() {
            return tableid;
        }

        public void setTableid(Integer tableid) {
            this.tableid = tableid;
        }

        public Integer getOrderstatus() {
            return orderstatus;
        }

        public void setOrderstatus(Integer orderstatus) {
            this.orderstatus = orderstatus;
        }

        public String getShippingaddress() {
            return shippingaddress;
        }

        public void setShippingaddress(String shippingaddress) {
            this.shippingaddress = shippingaddress;
        }

        public Integer getPaymentmethod() {
            return paymentmethod;
        }

        public void setPaymentmethod(Integer paymentmethod) {
            this.paymentmethod = paymentmethod;
        }

        public Integer getPaymentstatus() {
            return paymentstatus;
        }

        public void setPaymentstatus(Integer paymentstatus) {
            this.paymentstatus = paymentstatus;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }

        public String getDiscountamount() {
            return discountamount;
        }

        public void setDiscountamount(String discountamount) {
            this.discountamount = discountamount;
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

        public Integer getServicecharge() {
            return servicecharge;
        }

        public void setServicecharge(Integer servicecharge) {
            this.servicecharge = servicecharge;
        }

        public Integer getServicechargeamount() {
            return servicechargeamount;
        }

        public void setServicechargeamount(Integer servicechargeamount) {
            this.servicechargeamount = servicechargeamount;
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

        public Object getOrderdetail() {
            return orderdetail;
        }

        public void setOrderdetail(Object orderdetail) {
            this.orderdetail = orderdetail;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public Object getSpecialinstruction() {
            return specialinstruction;
        }

        public void setSpecialinstruction(Object specialinstruction) {
            this.specialinstruction = specialinstruction;
        }

        public Object getDeliverydate() {
            return deliverydate;
        }

        public void setDeliverydate(Object deliverydate) {
            this.deliverydate = deliverydate;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Object updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getIsapporder() {
            return isapporder;
        }

        public void setIsapporder(Integer isapporder) {
            this.isapporder = isapporder;
        }

        public Integer getOrderfrom() {
            return orderfrom;
        }

        public void setOrderfrom(Integer orderfrom) {
            this.orderfrom = orderfrom;
        }

        public Waiter getWaiter() {
            return waiter;
        }

        public void setWaiter(Waiter waiter) {
            this.waiter = waiter;
        }

        public Deliveryzone getDeliveryzone() {
            return deliveryzone;
        }

        public void setDeliveryzone(Deliveryzone deliveryzone) {
            this.deliveryzone = deliveryzone;
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

    public class Waiter {

        @SerializedName("userid")
        @Expose
        private Integer userid;
        @SerializedName("fullname")
        @Expose
        private String fullname;
        @SerializedName("contactno")
        @Expose
        private String contactno;

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getContactno() {
            return contactno;
        }

        public void setContactno(String contactno) {
            this.contactno = contactno;
        }

    }
    }
