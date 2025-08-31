package com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJoblistDetail;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart.MyCartActivity;
import com.btracsolution.deliverysystem.Model.MemberInfoResponse;
import com.btracsolution.deliverysystem.Model.MemberUpdateResponse;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.CustomAlertDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;

public class WaiterJobDetailPresenter {

    WaiterOrderDetailActivity waiterOrderDetailActivity;
    JobStatusChangeStatusWaiter jobStatusChangeStatus;
    String AcceptType;

    //CustomDialogView
    EditText etMemidDialog;
    TextView tvMemNameDialog;
    CircleImageView civMemDialog;
    LinearLayout llMemLayout;

    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            try {
                if (isSuccess && message.contentEquals("accpet")) {
                    switch (AcceptType) {
                        case "2":
                            waiterOrderDetailActivity.showToast(waiterOrderDetailActivity.getApplicationContext().getString(R.string.order_accepted_succesfull));
                            break;
                        case "3":
                            waiterOrderDetailActivity.showToast(waiterOrderDetailActivity.getApplicationContext().getString(R.string.order_ready_to_pickup));
                            break;
                        case "5":
                            waiterOrderDetailActivity.showToast(waiterOrderDetailActivity.getApplicationContext().getString(R.string.order_delivered));
                            waiterOrderDetailActivity.hideKeyboard();
                            waiterOrderDetailActivity.finish();
                            break;

                    }
                    jobStatusChangeStatus.onJobStatusChanged(AcceptType);

                } else if (isSuccess && message.contentEquals("single")) {
                    OrderDetailsModel orderDetailsModel = (OrderDetailsModel) object;
                    String orderData = new Gson().toJson(orderDetailsModel.getOrderList().get(0));

                    waiterOrderDetailActivity.orderBasicData = new Gson().fromJson(orderData, OrderDetailsModel.orderBasicData.class);


                    if (waiterOrderDetailActivity.orderBasicData != null) {
                        waiterOrderDetailActivity.orderBasicData.setOrderstatus(waiterOrderDetailActivity.acceptType);
                        waiterOrderDetailActivity.setDataIntoView(waiterOrderDetailActivity.orderBasicData);
                        waiterOrderDetailActivity.setAdapterDataIntoFoodView();
                    }
                } else if (isSuccess && message.contentEquals("memberupdate")) {
                    MemberUpdateResponse memberUpdateResponse = (MemberUpdateResponse) object;
                    waiterOrderDetailActivity.showToast("Member changed successfully");
                    if (memberUpdateResponse != null) {
                        waiterOrderDetailActivity.updateUserName(
                                memberUpdateResponse.getData().getName(),
                                memberUpdateResponse.getData().getMemberId()
                        );
                       // waiterOrderDetailActivity.tx_user_name.setText(memberUpdateResponse.getData().getName() + "(" + memberUpdateResponse.getData().getMemberId() + ")");
                    }
                } else if (isSuccess && message.contentEquals("singlemember")) {
                    try {
                        MemberInfoResponse memberInfoResponse = (MemberInfoResponse) object;
                        if (memberInfoResponse != null) {
                               etMemidDialog.setText(memberInfoResponse.getData().getMemberId());
                               llMemLayout.setVisibility(View.VISIBLE);
                               tvMemNameDialog.setText(memberInfoResponse.getData().getMemberName());
//                            Glide.with(waiterOrderDetailActivity)
//                                    .load(memberInfoResponse.getData().getMemberImage())
//                                    .centerCrop()
//                                    .into(civMemDialog);
//                            etMemidDialog.clearFocus();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    waiterOrderDetailActivity.showToast(message);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
    private WaiterOrderDetailRepo waiterOrderDetailRepo;

    public void attachView(WaiterOrderDetailActivity activity, JobStatusChangeStatusWaiter jobStatusChangeStatus) {
        this.waiterOrderDetailActivity = activity;
        waiterOrderDetailRepo = new WaiterOrderDetailRepo(activity, baseServerListener);
        this.jobStatusChangeStatus = jobStatusChangeStatus;

    }

    public void acceptOrder(String orderID, String shortNote) {
        AcceptType = "2";
        if (waiterOrderDetailRepo != null) {
            //    waiterOrderDetailRepo.sendJobAcceptResponse(AcceptType, orderID, shortNote);
        }
    }
//
//    public void readyToPickUp(String orderID, String shortNote) {
//        AcceptType = "3";
//        if (waiterOrderDetailRepo != null) {
//            waiterOrderDetailRepo.sendJobAcceptResponse(AcceptType, orderID, shortNote);
//        }
//    }

    public void deliveredToMember(String orderID, String shortNote, int kitchenid) {
        AcceptType = "5";
        if (waiterOrderDetailRepo != null) {
            waiterOrderDetailRepo.sendJobAcceptResponse(AcceptType, orderID, shortNote, kitchenid);
        }
    }

    public void declineOrder(String orderID, String shortNote) {
        AcceptType = "11";
        if (waiterOrderDetailRepo != null) {
            //    waiterOrderDetailRepo.sendJobAcceptResponse(AcceptType, orderID, shortNote);
        }
    }

    public void updateMember(String orderid, String memberid) {
        if (waiterOrderDetailRepo != null) {
            waiterOrderDetailRepo.updateMember(orderid, memberid);
        }
    }

    public void getSingleOrder(String orderID) {
        if (waiterOrderDetailRepo != null && orderID != null) {
            waiterOrderDetailRepo.getSingleOrderData(orderID);
        }
    }

    public void getSingleMemberData(boolean isLoading, String memid, EditText etmemid, CircleImageView civmem, TextView tvmemname, LinearLayout llMeminfo) {
        this.etMemidDialog=etmemid;
        this.civMemDialog=civmem;
        this.tvMemNameDialog=tvmemname;
        this.llMemLayout=llMeminfo;
        waiterOrderDetailRepo.getSingleMemberData(isLoading, memid);
    }

}
