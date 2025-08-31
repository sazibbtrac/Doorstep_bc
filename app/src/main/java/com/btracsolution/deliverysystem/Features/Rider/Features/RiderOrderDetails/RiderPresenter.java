package com.btracsolution.deliverysystem.Features.Rider.Features.RiderOrderDetails;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.PrintUtility;
import com.google.gson.Gson;

/**
 * Created by mahmudul.hasan on 2/12/2018.
 */

public class RiderPresenter {

    RiderOrderDetailActivity riderOrderDetailActivity;
    RiderJobStatusChangeStatus jobStatusChangeStatus;
    String AcceptType;
    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (isSuccess) {
                if (message.contentEquals("single")) {
                    OrderDetailsModel orderDetailsModel= (OrderDetailsModel) object;
                    String orderData=new Gson().toJson(orderDetailsModel.getOrderList().get(0));

                    riderOrderDetailActivity.orderBasicData = new Gson().fromJson(orderData, OrderDetailsModel.orderBasicData.class);


                    if (riderOrderDetailActivity.orderBasicData != null) {
                        riderOrderDetailActivity.orderBasicData.setDeliverystatus("1");
                        riderOrderDetailActivity.setDataIntoView(riderOrderDetailActivity.orderBasicData);
                        riderOrderDetailActivity.setAdapterDataIntoFoodView();
                    }
                }else {
                    switch (AcceptType) {
                        case "4":
                            riderOrderDetailActivity.showToast(riderOrderDetailActivity.getApplicationContext().getString(R.string.order_pickup_succesfull));
                            jobStatusChangeStatus.onJobStatusChanged(AcceptType, "1");

                            break;
                        case "5":
                            riderOrderDetailActivity.showToast(riderOrderDetailActivity.getApplicationContext().getString(R.string.order_delivery_succesfull));
                            jobStatusChangeStatus.onJobStatusChanged(AcceptType, "1");
                            if (riderOrderDetailActivity != null)
                                riderOrderDetailActivity.finish();

                            break;
                        case "3":
                            riderOrderDetailActivity.showToast(riderOrderDetailActivity.getApplicationContext().getString(R.string.order_accepted_succesfull));
                            jobStatusChangeStatus.onJobStatusChanged(AcceptType, "1");

                            break;
                        case "12":
                            riderOrderDetailActivity.showToast(riderOrderDetailActivity.getApplicationContext().getString(R.string.order_placed_inpantry));
                            jobStatusChangeStatus.onJobStatusChanged(AcceptType, "1");

                            break;

                    }
                }

            } else {
                riderOrderDetailActivity.showToast(message);

            }

        }
    };
    private RiderOrderDetailsModel riderOrderDetailsModel;

    public void attachView(RiderOrderDetailActivity activity, RiderJobStatusChangeStatus jobStatusChangeStatus) {
        this.riderOrderDetailActivity = activity;
        riderOrderDetailsModel = new RiderOrderDetailsModel(activity, baseServerListener);
        this.jobStatusChangeStatus = jobStatusChangeStatus;

    }

    public void acceptOrder(String orderID, String shortNote) {
        AcceptType = "3";
        if (riderOrderDetailsModel != null) {
            riderOrderDetailsModel.sendJobAcknowldegementResponse(orderID, "1");
        }
    }

    public void pickUP(String orderID, String shortNote) {
        AcceptType = "4";
        if (riderOrderDetailsModel != null) {
            riderOrderDetailsModel.sendJobAcceptResponse(AcceptType, orderID, shortNote);
        }
    }

    public void delivered(String orderID, String shortNote) {
        AcceptType = "12";
        if (riderOrderDetailsModel != null) {
            riderOrderDetailsModel.sendJobAcceptResponse(AcceptType, orderID, shortNote);
        }
    }

    public void getSingleOrder(String orderID) {
        if (riderOrderDetailsModel != null && orderID!=null) {
            riderOrderDetailsModel.getSingleOrderData(orderID);
        }
    }
}
