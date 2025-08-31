package com.btracsolution.deliverysystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class OrderDetailsModel {

    public static Object orderBasicData;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<orderBasicData> OrderList = null;

    @Expose
    LoginModel.ErrorData error;

    @SerializedName("billDetails")
    @Expose
    private BillDetails billDetails;

    public BillDetails getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(BillDetails billDetails) {
        this.billDetails = billDetails;
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

    public ArrayList<orderBasicData> getOrderList() {
        return OrderList;
    }

    public void setOrderList(ArrayList<orderBasicData> data) {
        this.OrderList = data;
    }

    public class Rider {
        String riderid;
        String fullname;
        String contactno;
        String branchname;
        String profileImage;

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getBranchname() {
            return branchname;
        }

        public void setBranchname(String branchname) {
            this.branchname = branchname;
        }

        public String getRiderid() {
            return riderid;
        }

        public void setRiderid(String riderid) {
            this.riderid = riderid;
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


    public class orderBasicData {

        /* Header set data*/
        boolean isHeader=false;
        private String TypeOfHeader;
        private String TotalOrder;

        @SerializedName("orderid")
        @Expose
        private String orderid;
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
        private String locationid;
        @SerializedName("orderstatus")
        @Expose
        private String orderstatus;
        @SerializedName("shippingaddress")
        @Expose
        private String shippingaddress;
        @SerializedName("paymentmethod")
        @Expose
        private String paymentmethod;
        @SerializedName("paymentstatus")
        @Expose
        private String paymentstatus;
        @SerializedName("amount")
        @Expose
        private String amount;
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
        private String deliverycharge;
        @SerializedName("totalamount")
        @Expose
        private String totalamount;
        @SerializedName("orderdetail")
        @Expose
        private String orderdetail;
        @SerializedName("remarks")
        @Expose
        private String remarks;
        @SerializedName("specialinstruction")
        @Expose
        private String specialinstruction;
        @SerializedName("deliverydate")
        @Expose
        private String deliverydate;
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
        @SerializedName("isapporder")
        @Expose
        private Integer isapporder;
        @SerializedName("orderfrom")
        @Expose
        private Integer orderfrom;
        @SerializedName("deliveryid")
        @Expose
        private String deliveryid;
        @SerializedName("riderid")
        @Expose
        private String riderid;
        @SerializedName("assigntime")
        @Expose
        private String assigntime;
        @SerializedName("pickuptime")
        @Expose
        private String pickuptime;
        @SerializedName("deliverytime")
        @Expose
        private String deliverytime;
        @SerializedName("returntime")
        @Expose
        private String returntime;
        @SerializedName("last_lat")
        @Expose
        private String lastLat;
        @SerializedName("last_lng")
        @Expose
        private String lastLng;
        @SerializedName("deliverystatus")
        @Expose
        private String deliverystatus;
        @SerializedName("remark")
        @Expose
        private String remark;
        @SerializedName("orderitem")
        @Expose
        private ArrayList<OrderItem> orderitem = null;
        @SerializedName("waiter")
        @Expose
        private Waiter waiter;
        @SerializedName("member")
        @Expose
        private Member member;
        @SerializedName("rider")
        @Expose
        private Rider rider;
        @SerializedName("deliveryzone")
        @Expose
        private Deliveryzone deliveryzone;
        @SerializedName("tableno")
        @Expose
        private Tableno tableno;
        boolean orderChecked = false;

        public boolean isOrderChecked() {
            return orderChecked;
        }

        public void setOrderChecked(boolean orderChecked) {
            this.orderChecked = orderChecked;
        }

        public void setDeliveryzone(Deliveryzone deliveryzone) {
            this.deliveryzone = deliveryzone;
        }

        public void setTableno(Tableno tableno) {
            this.tableno = tableno;
        }

        public Deliveryzone getDeliveryzone() {
            return deliveryzone;
        }

        public Tableno getTableno() {
            return tableno;
        }

        public void setWaiter(Waiter waiter) {
            this.waiter = waiter;
        }

        public Waiter getWaiter() {
            return waiter;
        }

        public String getTotalOrder() {
            return TotalOrder;
        }

        public void setTotalOrder(String totalOrder) {
            TotalOrder = totalOrder;
        }

        public boolean isHeader() {
            return isHeader;
        }

        public void setHeader(boolean header) {
            isHeader = header;
        }

        public String getTypeOfHeader() {
            return TypeOfHeader;
        }

        public void setTypeOfHeader(String typeOfHeader) {
            TypeOfHeader = typeOfHeader;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
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

        public String getLocationid() {
            return locationid;
        }

        public void setLocationid(String locationid) {
            this.locationid = locationid;
        }

        public String getOrderstatus() {
            return orderstatus;
        }

        public void setOrderstatus(String orderstatus) {
            this.orderstatus = orderstatus;
        }

        public String getShippingaddress() {
            return shippingaddress;
        }

        public void setShippingaddress(String shippingaddress) {
            this.shippingaddress = shippingaddress;
        }

        public String getPaymentmethod() {
            return paymentmethod;
        }

        public void setPaymentmethod(String paymentmethod) {
            this.paymentmethod = paymentmethod;
        }

        public String getPaymentstatus() {
            return paymentstatus;
        }

        public void setPaymentstatus(String paymentstatus) {
            this.paymentstatus = paymentstatus;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
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

        public String getDeliverycharge() {
            return deliverycharge;
        }

        public void setDeliverycharge(String deliverycharge) {
            this.deliverycharge = deliverycharge;
        }

        public String getTotalamount() {
            return totalamount;
        }

        public void setTotalamount(String totalamount) {
            this.totalamount = totalamount;
        }

        public String getOrderdetail() {
            return orderdetail;
        }

        public void setOrderdetail(String orderdetail) {
            this.orderdetail = orderdetail;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getSpecialinstruction() {
            return specialinstruction;
        }

        public void setSpecialinstruction(String specialinstruction) {
            this.specialinstruction = specialinstruction;
        }

        public String getDeliverydate() {
            return deliverydate;
        }

        public void setDeliverydate(String deliverydate) {
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

        public String getDeliveryid() {
            return deliveryid;
        }

        public void setDeliveryid(String deliveryid) {
            this.deliveryid = deliveryid;
        }

        public String getRiderid() {
            return riderid;
        }

        public void setRiderid(String riderid) {
            this.riderid = riderid;
        }

        public String getAssigntime() {
            return assigntime;
        }

        public void setAssigntime(String assigntime) {
            this.assigntime = assigntime;
        }

        public String getPickuptime() {
            return pickuptime;
        }

        public void setPickuptime(String pickuptime) {
            this.pickuptime = pickuptime;
        }

        public String getDeliverytime() {
            return deliverytime;
        }

        public void setDeliverytime(String deliverytime) {
            this.deliverytime = deliverytime;
        }

        public String getReturntime() {
            return returntime;
        }

        public void setReturntime(String returntime) {
            this.returntime = returntime;
        }

        public String getLastLat() {
            return lastLat;
        }

        public void setLastLat(String lastLat) {
            this.lastLat = lastLat;
        }

        public String getLastLng() {
            return lastLng;
        }

        public void setLastLng(String lastLng) {
            this.lastLng = lastLng;
        }

        public String getDeliverystatus() {
            return deliverystatus;
        }

        public void setDeliverystatus(String deliverystatus) {
            this.deliverystatus = deliverystatus;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public ArrayList<OrderItem> getOrderitem() {
            return orderitem;
        }

        public void setOrderitem(ArrayList<OrderItem> orderitem) {
            this.orderitem = orderitem;
        }

        public Member getMember() {
            return member;
        }

        public void setMember(Member member) {
            this.member = member;
        }

        public Rider getRider() {
            return rider;
        }

        public void setRider(Rider rider) {
            this.rider = rider;
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

    public class Waiter {

        @SerializedName("userid")
        @Expose
        private String waiterid;
        @SerializedName("fullname")
        @Expose
        private String fullName;
        @SerializedName("contactno")
        @Expose
        private String contactno;

        public void setWaiterid(String waiterid) {
            this.waiterid = waiterid;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public void setContactno(String contactno) {
            this.contactno = contactno;
        }

        public String getWaiterid() {
            return waiterid;
        }

        public String getFullName() {
            return fullName;
        }

        public String getContactno() {
            return contactno;
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
        private String price;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
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


    public class OrderItem {

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
        private String quantity;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("totalprice")
        @Expose
        private Integer totalprice;
        @SerializedName("remarks")
        @Expose
        private String itemRemarks;
        @SerializedName("itemstatus")
        @Expose
        private String itemstatus;
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
        boolean isChecked = true;

        public String getItemRemarks() {
            return itemRemarks;
        }

        public void setItemRemarks(String itemRemarks) {
            this.itemRemarks = itemRemarks;
        }

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

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
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

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public String getItemstatus() {
            return itemstatus;
        }

        public void setItemstatus(String itemstatus) {
            this.itemstatus = itemstatus;
        }
    }

    public class BillDetails {

        @SerializedName("totalamount")
        @Expose
        private Integer totalamount;
        @SerializedName("totaldue")
        @Expose
        private Integer totaldue;
        @SerializedName("totalpaid")
        @Expose
        private Integer totalpaid;

        public Integer getTotalamount() {
            return totalamount;
        }

        public void setTotalamount(Integer totalamount) {
            this.totalamount = totalamount;
        }

        public Integer getTotaldue() {
            return totaldue;
        }

        public void setTotaldue(Integer totaldue) {
            this.totaldue = totaldue;
        }

        public Integer getTotalpaid() {
            return totalpaid;
        }

        public void setTotalpaid(Integer totalpaid) {
            this.totalpaid = totalpaid;
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
