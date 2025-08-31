package com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterDayReport;

import android.app.Activity;
import android.content.Context;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Features.Agent.AgentOrderDetails.AgentOrderDetailActivity;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentDayWiseResult.AgentDayListAdapter;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentDayWiseResult.AgentDayModel;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentDayWiseResult.AgentDayReport;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJoblistDetail.WaiterOrderDetailActivity;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.Utility.LogoutUtility;

public class WaiterDayPresenter {

    WaiterDayReport waiterDayReport;
    WaiterDayModel waiterDayModel;
    Context context;
    Activity activity;
    private OrderDetailsModel orderDetailsModel;

    BaseItemClickListener baseItemClickListener = new BaseItemClickListener() {
        @Override
        public void onClickOfListItem(boolean isClick, int position, String extra) {
            if (orderDetailsModel != null) {
                WaiterOrderDetailActivity.open(activity, orderDetailsModel.getOrderList().get(position),"joblist","","");
            }
        }
    };
    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (isSuccess) {
                orderDetailsModel = (OrderDetailsModel) object;
                if (orderDetailsModel.getOrderList().size() > 0) {
                    waiterDayReport.yesOrderFound();

                    WaiterDayListAdapter waiterDayListAdapter = new WaiterDayListAdapter(context, orderDetailsModel.getOrderList(), baseItemClickListener);
                    waiterDayReport.setAdapterIntoRecyclView(waiterDayListAdapter);

                } else {
//                    agentDayReport.showToast(context.getString(R.string.no_order));
                    waiterDayReport.noOrderFound();
                }


            } else {
                orderDetailsModel = (OrderDetailsModel) object;
                if (orderDetailsModel == null)
                    waiterDayReport.noOrderFound();
                else if (orderDetailsModel.getError().getError_code().contentEquals("401")) {
                    waiterDayReport.showToast(message);
                    new LogoutUtility().setLoggedOut(activity);
                } else
                    waiterDayReport.showToast(message);
            }
        }
    };

    public void attachView(Context context, Activity activity, WaiterDayReport waiterDayReport) {
        this.context = context;
        this.activity = activity;
        this.waiterDayReport = waiterDayReport;
        waiterDayModel = new WaiterDayModel(context, baseServerListener);
    }

    public void getDataFromServer(String FromDate, String toDate) {
        if (waiterDayModel != null) {
            waiterDayModel.getServerData(FromDate, toDate);
        }
    }
}
