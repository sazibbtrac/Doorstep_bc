package com.btracsolution.deliverysystem.Features.Rider.Features.MyJobPack;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Features.Rider.Features.RiderOrderDetails.RiderOrderDetailActivity;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;

import java.util.ArrayList;

/**
 * Created by mahmudul.hasan on 2/26/2018.
 */

public class RiderJobPresenter {
    RiderJobList riderJobList;
    RiderJobModel agentModel;
    Activity activity;
    OrderDetailsModel orderDetailsModel;
    BaseItemClickListener baseItemClickListener = new BaseItemClickListener() {
        @Override
        public void onClickOfListItem(boolean isClick, int position, String extra) {
            if (orderDetailsModel != null) {
                switch (extra) {
                    case "details":
                        RiderOrderDetailActivity.open(activity, orderDetailsModel.getOrderList().get(position),"joblist","");
                        break;
                    case "call":
                        callAction(orderDetailsModel.getOrderList().get(position).getMember().getContactno());
                        break;
                    case "location":
                        location(orderDetailsModel.getOrderList().get(position).getMember().getContactno());
                        break;
                }

            } else {
                riderJobList.showToast(activity.getString(R.string.error_calling_action));
            }
        }
    };
    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (isSuccess) {
                orderDetailsModel = (OrderDetailsModel) object;
                if (orderDetailsModel.getOrderList().size() > 0) {
                    riderJobList.yesOrderFound();


                    ArrayList<OrderDetailsModel.orderBasicData> ArrayOrderBasicDatas = new ArrayList<>();
                    for (OrderDetailsModel.orderBasicData orderBasicData : orderDetailsModel.getOrderList()) {

                        switch (orderBasicData.getOrderstatus()) {
                            case "3":
                                ArrayOrderBasicDatas.add(orderBasicData);
                                break;
                            case "4":
                                ArrayOrderBasicDatas.add(orderBasicData);
                                break;
                            case "13":
                                ArrayOrderBasicDatas.add(orderBasicData);
                                break;
                            case "14":
                                ArrayOrderBasicDatas.add(orderBasicData);
                                break;
                            default:
                                break;
                        }

                    }

                    RiderJobAdapter riderJobadapter = new RiderJobAdapter(activity, ArrayOrderBasicDatas, baseItemClickListener);
                    riderJobList.setAdapterIntoRecyclView(riderJobadapter);

                    orderDetailsModel.setOrderList(ArrayOrderBasicDatas);

                    if (ArrayOrderBasicDatas.size() == 0) {
                        riderJobList.noOrderFound();

                    } else
                        riderJobList.yesOrderFound();

                } else {
//                    agentOrderList.showToast(context.getString(R.string.no_order));
                    riderJobList.noOrderFound();
                }


            } else {
                riderJobList.showToast(message);
                riderJobList.swipRefreshOff();
                System.out.println("respnse "+message);
            }
        }
    };

    public void callAction(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));

        activity.startActivity(intent);
    }


    public void location(String address) {
        String map = "http://maps.google.co.in/maps?q=" + address;
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
        activity.startActivity(i);
    }

    public void attachView(Activity activity, RiderJobList riderJobList) {
        this.activity = activity;
        this.riderJobList = riderJobList;
        agentModel = new RiderJobModel(activity, baseServerListener);
    }

    public void getServerData(boolean isLoading) {
        agentModel.getServerData(isLoading);
    }
}
