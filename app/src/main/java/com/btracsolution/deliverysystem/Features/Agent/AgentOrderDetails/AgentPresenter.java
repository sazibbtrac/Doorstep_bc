package com.btracsolution.deliverysystem.Features.Agent.AgentOrderDetails;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.PrintUtility;
import com.btracsolution.deliverysystem.Utility.Validation;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by mahmudul.hasan on 2/12/2018.
 */

public class AgentPresenter {

    AgentOrderDetailActivity agentOrderDetailActivity;
    JobStatusChangeStatus jobStatusChangeStatus;
    String AcceptType;
    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            try {
                if (isSuccess) {
                    if (message.contentEquals("single")) {
                        OrderDetailsModel orderDetailsModel= (OrderDetailsModel) object;
                        String orderData=new Gson().toJson(orderDetailsModel.getOrderList().get(0));

                        agentOrderDetailActivity.orderBasicData = new Gson().fromJson(orderData, OrderDetailsModel.orderBasicData.class);


                        if (agentOrderDetailActivity.orderBasicData != null) {
                            agentOrderDetailActivity.setDataIntoView(agentOrderDetailActivity.orderBasicData);
                            agentOrderDetailActivity.orderBasicData.setOrderstatus("2");
                            agentOrderDetailActivity.setAdapterDataIntoFoodView();
                            if (agentOrderDetailActivity.mBluetoothSocket!=null && agentOrderDetailActivity.mBluetoothSocket.isConnected()) {
                                  PrintUtility.printKitchen(agentOrderDetailActivity.mBluetoothSocket, agentOrderDetailActivity.orderBasicData);
                            }
                        }
                    } else {
                        switch (AcceptType) {
                            case "2":
                                agentOrderDetailActivity.showToast(agentOrderDetailActivity.getApplicationContext().getString(R.string.order_accepted_succesfull));
                                break;
                            case "3":
                                agentOrderDetailActivity.showToast(agentOrderDetailActivity.getApplicationContext().getString(R.string.order_ready_to_pickup));
                                break;
                            case "11":
                                agentOrderDetailActivity.showToast(agentOrderDetailActivity.getApplicationContext().getString(R.string.order_cancelled));
                                agentOrderDetailActivity.hideKeyboard();
                                break;
                            case "13":
                                agentOrderDetailActivity.showToast(agentOrderDetailActivity.getApplicationContext().getString(R.string.order_ready_to_pickup_partially));
                                agentOrderDetailActivity.hideKeyboard();
                                break;

                        }
                        jobStatusChangeStatus.onJobStatusChanged(AcceptType);
                    }

                } else {
                    agentOrderDetailActivity.showToast(message);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
    private AgentOrderDetailsModel agentDayModel;

    public void attachView(AgentOrderDetailActivity activity, JobStatusChangeStatus jobStatusChangeStatus) {
        this.agentOrderDetailActivity = activity;
        agentDayModel = new AgentOrderDetailsModel(activity, baseServerListener);
        this.jobStatusChangeStatus = jobStatusChangeStatus;

    }

    public void acceptOrder(String orderID, String shortNote) {
        AcceptType = "2";
        if (agentDayModel != null) {
            agentDayModel.sendJobAcceptResponse(AcceptType, orderID, shortNote);
        }
    }

    public void readyToPickUp(String orderID, String shortNote, String orderStatus) {
        AcceptType = orderStatus;
        if (agentDayModel != null) {
            agentDayModel.sendJobAcceptResponse(orderStatus, orderID, shortNote);
        }
    }

    public void declineOrder(String orderID, String shortNote) {
        AcceptType = "11";
        if (agentDayModel != null) {
            agentDayModel.sendJobAcceptResponse(AcceptType, orderID, shortNote);
        }
    }

    public void getSingleOrder(String orderID, String shortNote) {
        if (agentDayModel != null) {
            agentDayModel.getSingleOrderData(orderID);
        }
    }
}
