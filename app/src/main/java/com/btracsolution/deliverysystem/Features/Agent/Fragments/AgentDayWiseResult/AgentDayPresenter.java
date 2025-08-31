package com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentDayWiseResult;

import android.app.Activity;
import android.content.Context;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Features.Agent.AgentOrderDetails.AgentOrderDetailActivity;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.LogoutUtility;

/**
 * Created by mahmudul.hasan on 2/8/2018.
 */

public class AgentDayPresenter {

    AgentDayReport agentDayReport;
    AgentDayModel agentDayModel;
    Context context;
    Activity activity;
    private OrderDetailsModel orderDetailsModel;

    BaseItemClickListener baseItemClickListener = new BaseItemClickListener() {
        @Override
        public void onClickOfListItem(boolean isClick, int position, String extra) {
            if (orderDetailsModel != null) {
                AgentOrderDetailActivity.open(activity, orderDetailsModel.getOrderList().get(position),"joblist","");
            }
        }
    };
    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (isSuccess) {
                orderDetailsModel = (OrderDetailsModel) object;
                if (orderDetailsModel.getOrderList().size() > 0) {
                    agentDayReport.yesOrderFound();

                    AgentDayListAdapter agentJobListAdapter = new AgentDayListAdapter(context, orderDetailsModel.getOrderList(), baseItemClickListener);
                    agentDayReport.setAdapterIntoRecyclView(agentJobListAdapter);

                } else {
//                    agentDayReport.showToast(context.getString(R.string.no_order));
                    agentDayReport.noOrderFound();
                }


            } else {
                orderDetailsModel = (OrderDetailsModel) object;
                if (orderDetailsModel == null)
                    agentDayReport.noOrderFound();
                else if (orderDetailsModel.getError().getError_code().contentEquals("401")) {
                    agentDayReport.showToast(message);
                    new LogoutUtility().setLoggedOut(activity);
                } else
                    agentDayReport.showToast(message);
            }
        }
    };

    public void attachView(Context context, Activity activity, AgentDayReport agentDayReport) {
        this.context = context;
        this.activity = activity;
        this.agentDayReport = agentDayReport;
        agentDayModel = new AgentDayModel(context, baseServerListener);
    }

    public void getDataFromServer(String FromDate, String toDate) {
        if (agentDayModel != null) {
            agentDayModel.getServerData(FromDate, toDate);
        }
    }

}
