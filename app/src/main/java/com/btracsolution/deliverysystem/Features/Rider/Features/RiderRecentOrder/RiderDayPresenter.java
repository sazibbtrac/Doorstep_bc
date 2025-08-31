package com.btracsolution.deliverysystem.Features.Rider.Features.RiderRecentOrder;

import android.app.Activity;
import android.content.Context;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Features.Rider.Features.RiderOrderDetails.RiderOrderDetailActivity;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;

import java.util.ArrayList;

/**
 * Created by mahmudul.hasan on 2/8/2018.
 */

public class RiderDayPresenter {

    RiderDayReport agentDayReport;
    RiderDayModel agentDayModel;
    Context context;
    Activity activity;
    private OrderDetailsModel orderDetailsModel;

    BaseItemClickListener baseItemClickListener = new BaseItemClickListener() {
        @Override
        public void onClickOfListItem(boolean isClick, int position, String extra) {
            if (orderDetailsModel != null) {
                RiderOrderDetailActivity.open(activity, orderDetailsModel.getOrderList().get(position),"joblist","");
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

                    /* Remove the currently going orders */

                    ArrayList<OrderDetailsModel.orderBasicData> ArrayOrderBasicDatas = new ArrayList<>();
                    for (OrderDetailsModel.orderBasicData orderBasicData : orderDetailsModel.getOrderList()) {

                        switch (orderBasicData.getOrderstatus()) {
                            case "3":
                                ArrayOrderBasicDatas.add(orderBasicData); // Temporary view check

                                break;
                            case "4":
                                ArrayOrderBasicDatas.add(orderBasicData); // Temporary view check

                                break;
                            default:
                                ArrayOrderBasicDatas.add(orderBasicData);
                                break;
                        }

                    }
                    RiderDayListAdapter agentJobListAdapter = new RiderDayListAdapter(context, ArrayOrderBasicDatas, baseItemClickListener);
                    agentDayReport.setAdapterIntoRecyclView(agentJobListAdapter);
                    orderDetailsModel.setOrderList(ArrayOrderBasicDatas);

                    if (ArrayOrderBasicDatas.size() == 0) {
                        agentDayReport.noOrderFound();

                    } else
                        agentDayReport.yesOrderFound();


                } else {
//                    agentDayReport.showToast(context.getString(R.string.no_order));
                    agentDayReport.noOrderFound();
                }


            } else
                agentDayReport.showToast(message);
        }
    };

    public void attachView(Context context, Activity activity, RiderDayReport agentDayReport) {
        this.context = context;
        this.activity = activity;
        this.agentDayReport = agentDayReport;
        agentDayModel = new RiderDayModel(context, baseServerListener);
    }

    public void getDataFromServer(String FromDate, String toDate) {
        if (agentDayModel != null) {
            agentDayModel.getServerData(FromDate, toDate);
        }
    }

}
