package com.btracsolution.deliverysystem.network.api;

import com.btracsolution.deliverysystem.Model.ActivationDataResponse;
import com.btracsolution.deliverysystem.Model.BillHistoryResponse;
import com.btracsolution.deliverysystem.Model.CommonDataResponse;
import com.btracsolution.deliverysystem.Model.DeliveryZoneResponse;
import com.btracsolution.deliverysystem.Model.LoginModel;
import com.btracsolution.deliverysystem.Model.MemberInfoResponse;
import com.btracsolution.deliverysystem.Model.MemberListResponse;
import com.btracsolution.deliverysystem.Model.MemberUpdateResponse;
import com.btracsolution.deliverysystem.Model.MenuFoodListModel;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.Model.OrderSubmitResponse;
import com.btracsolution.deliverysystem.Model.RiderAssignModel;
import com.btracsolution.deliverysystem.Model.RiderListSingleModel;
import com.btracsolution.deliverysystem.Model.StatementModel;
import com.btracsolution.deliverysystem.Model.SyncDataResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by mahmudul.hasan on 1/2/2018.
 */

public interface RetroHubApiInterface {

    @POST("login")
    @FormUrlEncoded
    Call<LoginModel> getLoginData(
            @Field("username") String username,
            @Field("password") String password,
            @Field("gcm_id") String gcm_id,
            @Field("imei") String imei,
            @Field("token") String token,
            @Field("email_or_mobile") String email_or_mobile
    );

    @POST("getbill")
    @FormUrlEncoded
    Call<OrderDetailsModel> getBill(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("member_id") String memberid,
            @Field("kitchenid") String kitchenid
//            @Field("from_date") String from_date,
//            @Field("to_date") String to_date
    );


    @POST("billclose")
    @FormUrlEncoded
    Call<CommonDataResponse> closeBill(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("member_id") String memberid,
            @Field("order_ids") String orderIds,
            @Field("totalbill") String total,
            @Field("paymentmethod") String paymethod


//            @Field("from_date") String from_date,
//            @Field("to_date") String to_date
    );

    @POST("billhistory")
    @FormUrlEncoded
    Call<BillHistoryResponse> getBillHistory(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("member_id") String mem_id


//            @Field("from_date") String from_date,
//            @Field("to_date") String to_date
    );

    @POST("orderdetail")
    @FormUrlEncoded
    Call<OrderDetailsModel> getOrderList(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("kitchenid") String kitchenid,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );

    @POST("member-update")
    @FormUrlEncoded
    Call<MemberUpdateResponse> updateMember(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("orderid") String orderid,
            @Field("member_id") String member_id

    );

    @POST("resend-bill")
    @FormUrlEncoded
    Call<SyncDataResponse> syncBill(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("bill_id") Integer bill_id

    );

    @POST("orderacceptdeny")
    @FormUrlEncoded
    Call<OrderDetailsModel> acceptOrder(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("orderid") String orderid,
            @Field("orderstatus") String orderstatus,
            @Field("shortnote") String shortnote,
            @Field("kitchenid") String kitchenid,
            @Field("orderitemid") String orderIds

            );

    @POST("single-orderdetail")
    @FormUrlEncoded
    Call<OrderDetailsModel> getSingleOrderData(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("orderid") String orderid

    );

    @POST("changepassword")
    @FormUrlEncoded
    Call<OrderDetailsModel> updatePassword(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password
    );

    @POST("menulist")
    @FormUrlEncoded
    Call<MenuFoodListModel> getMenuList(
            @Field("token") String token,
            @Field("userid") String userid
           // @Field("branchid") String branchid
    );

    @POST("order-data")
    @FormUrlEncoded
    Call<OrderSubmitResponse> submitOrder(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("member_id") String memid,
            @Field("locationid") String locationid,
            @Field("paymentmethod") String paymethod,
            @Field("paymentstatus") String payStatus,
            @Field("specialinstruction") String instruction,
            @Field("created_by") String createdby,
            @Field("payload") String foodItems,
            @Field("tableid") String tableid
    );

    @POST("member")
    @FormUrlEncoded
    Call<MemberInfoResponse> getMemberInfo(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("member_id") String memberid
    );

    @POST("member-list")
    @FormUrlEncoded
    Call<MemberListResponse> getMemberList(
            @Field("token") String token,
            @Field("userid") String userid
    );
    @POST("get-member")
    @FormUrlEncoded
    Call<MemberInfoResponse> getMember(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("member_id") String member_id
    );

    @POST("deliveryzone")
    @FormUrlEncoded
    Call<DeliveryZoneResponse> getDeliveryZone(
            @Field("token") String token,
            @Field("userid") String userid
    );

    @POST("changefoodstatus")
    @FormUrlEncoded
    Call<CommonDataResponse> changeOrderStatus(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("foodid") String foodid,
            @Field("foodstatus") String foodstatus
    );

    @POST("getriderinfo")
    @FormUrlEncoded
    Call<RiderListSingleModel> getRiderList(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("kitchenid") String kitchenid
    );

    @POST("assignrider")
    @FormUrlEncoded
    Call<RiderAssignModel> assignRider(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("orderid") String orderid,
            @Field("riderid") String riderid,
            @Field("orderitemid") String orderitemid,
            @Field("orderstatus") String orderstatus

    );

    @POST("updatedeviceid")
    @FormUrlEncoded
    Call<CommonDataResponse> updatedeviceid(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("gcm_id") String gcm_id,
            @Field("imei") String imei
    );


    @POST("riderjoblist")
    @FormUrlEncoded
    Call<OrderDetailsModel> getJobForRider(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("kitchenid") String branchid,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );

    @POST("locationupdate")
    @FormUrlEncoded
    Call<CommonDataResponse> updateLocation(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("lat") double lat,
            @Field("lng") double lng,
            @Field("locationname") String to_date
    );

    @POST("rideracknowledgement")
    @FormUrlEncoded
    Call<OrderDetailsModel> acknowledgement(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("orderid") String orderid,
            @Field("deliverystatus") String orderstatus
    );

    @POST("logout")
    @FormUrlEncoded
    Call<CommonDataResponse> logout(
            @Field("token") String token,
            @Field("userid") String userid
    );

    @POST("forgetpassword")
    @FormUrlEncoded
    Call<ActivationDataResponse> sendActivationCode(
            @Field("username") String username,
            @Field("emailormobile") String emailormobile
    );

    @POST("resetpassword")
    @FormUrlEncoded
    Call<CommonDataResponse> udpateResetPassword(
            @Field("resetcode") String resetcode,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
    );

    @POST("orderstatement")
    @FormUrlEncoded
    Call<StatementModel> getStatementData(
            @Field("token") String token,
            @Field("userid") String userid,
            @Field("branchid") String branchid,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );


}
