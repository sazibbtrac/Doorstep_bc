package com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentOrder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Features.Agent.AgentOrderDetails.AgentOrderDetailActivity;
import com.btracsolution.deliverysystem.Features.Login.LoginActivity;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.StatusSortAdapter;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.AgentGlobal;
import com.btracsolution.deliverysystem.Utility.LogoutUtility;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.google.gson.Gson;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class AgentPresenter {
    AgentOrderList agentOrderList;
    AgentModel agentModel;
    Context context;
    Activity activity;
    OrderDetailsModel orderDetailsModel;
    @Inject
    SharedData sharedData;
    AgentJobListAdapterWithHeader agentJobListAdapterWithHeader;
    StatusSortAdapter statusSortAdapter;
    ArrayList<String> statusList=new ArrayList<>();
    ArrayList<OrderDetailsModel.orderBasicData> finalOrderData = new ArrayList<>();

    BaseItemClickListener baseItemClickListener = new BaseItemClickListener() {
        @Override
        public void onClickOfListItem(boolean isClick, int position, String extra) {
                if (extra.contentEquals("sort")){
                    // waiterJobList.showToast(statusList.get(position));
                    updateView(position);
                }else {
                    if (finalOrderData.size()>0) {
                        AgentGlobal.listPosition = position;
                        AgentOrderDetailActivity.open(activity, finalOrderData.get(position),"joblist","");
                    }
                }
        }
    };
    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (isSuccess) {
                orderDetailsModel = (OrderDetailsModel) object;
                if (orderDetailsModel.getOrderList().size() > 0) {
                    agentOrderList.yesOrderFound();

//                     Without filter just plane order list
                   /* AgentJobListAdapter agentJobListAdapter = new AgentJobListAdapter(context, orderDetailsModel.getOrderList(), baseItemClickListener);
                    agentOrderList.setAdapterIntoRecyclView(agentJobListAdapter);*/


                    /* With filter just plane order list*/
                    orderDetailsModel.setOrderList(analysisAndSetHeader(orderDetailsModel.getOrderList()));
                    finalOrderData.clear();
                    finalOrderData.addAll(orderDetailsModel.getOrderList());
                    agentJobListAdapterWithHeader = new AgentJobListAdapterWithHeader(context, finalOrderData, baseItemClickListener);
                    agentOrderList.setAdapterIntoRecyclView(agentJobListAdapterWithHeader);

                   // agentOrderList.rvHorizontal.setVisibility(View.VISIBLE);
                    agentOrderList.getBinding().rvHorizontal.setVisibility(View.VISIBLE);
                    statusSortAdapter=new StatusSortAdapter(context,statusList,baseItemClickListener);
                    agentOrderList.setAdapterForHoriZontalRecycleView(statusSortAdapter);


                } else {
//                    agentOrderList.showToast(context.getString(R.string.no_order));
                    agentOrderList.getBinding().rvHorizontal.setVisibility(View.GONE);
                    //agentOrderList.rvHorizontal.setVisibility(View.GONE);
                    agentOrderList.noOrderFound();
                }


            } else {
                orderDetailsModel = (OrderDetailsModel) object;
                if (orderDetailsModel == null)
                    agentOrderList.noOrderFound();
                else if (orderDetailsModel.getError().getError_code().contentEquals("401")) {
                    agentOrderList.showToast(orderDetailsModel.getMessage());
                    new LogoutUtility().setLoggedOut(activity);
                } else
                    agentOrderList.showToast(orderDetailsModel.getMessage());

            }
        }
    };

    public void attachView(Context context, Activity activity, AgentOrderList agentOrderList) {
        this.context = context;
        this.activity = activity;
        this.agentOrderList = agentOrderList;
        agentModel = new AgentModel(context, baseServerListener);
        sharedData = new SharedData(context);
        generateSortArrayList();
    }
    private void generateSortArrayList() {
        statusList.add("100");
        statusList.add("99");
        statusList.add("98");
        statusList.add("5");
        //statusList.add("4");
    }


    public void getServerData(boolean isLoading) {
        agentModel.getServerData(isLoading);
    }

    public ArrayList<OrderDetailsModel.orderBasicData> analysisAndSetHeader(ArrayList<OrderDetailsModel.orderBasicData> orderBasicDatas) {

        // Final processing data will contain on this list
        ArrayList<OrderDetailsModel.orderBasicData> AllOrderBasicDatasProcessing = new ArrayList<>();

        // Temporary swap
        ArrayList<OrderDetailsModel.orderBasicData> orderBasicDatasProcessing = new ArrayList<>();
        ArrayList<OrderDetailsModel.orderBasicData> orderBasicDatasLocalCompleted = new ArrayList<>();
        for (OrderDetailsModel.orderBasicData orderBasicDataForEach : orderBasicDatas) {

            if (isProcessed(orderBasicDataForEach)) {
                orderBasicDatasLocalCompleted.add(orderBasicDataForEach);
            } else {
                orderBasicDatasProcessing.add(orderBasicDataForEach);


            }

        }
        if (orderBasicDatasProcessing.size() > 0) {

            /* Set header data*/

            OrderDetailsModel.orderBasicData orderBasicData = new OrderDetailsModel().new orderBasicData();
            orderBasicData.setHeader(true);
            orderBasicData.setTypeOfHeader(activity.getString(R.string.new_order));
            orderBasicData.setTotalOrder(orderBasicDatasProcessing.size() + "");
            AllOrderBasicDatasProcessing.add(orderBasicData);

            /* Set rest of the data into arraylist*/
            AllOrderBasicDatasProcessing.addAll(orderBasicDatasProcessing);

        }
        if (orderBasicDatasLocalCompleted.size() > 0) {

            /* Set header data*/

            OrderDetailsModel.orderBasicData orderBasicData = new OrderDetailsModel().new orderBasicData();
            orderBasicData.setHeader(true);
            orderBasicData.setTypeOfHeader(activity.getString(R.string.processed_order));
            orderBasicData.setTotalOrder(orderBasicDatasLocalCompleted.size() + "");
            AllOrderBasicDatasProcessing.add(orderBasicData);

            /* Set rest of the data into arraylist*/
            AllOrderBasicDatasProcessing.addAll(orderBasicDatasLocalCompleted);

        }

        return AllOrderBasicDatasProcessing;


    }

    public boolean isProcessed(OrderDetailsModel.orderBasicData orderBasicData) {
        int currentStatus = Integer.parseInt(orderBasicData.getOrderstatus());
        return currentStatus ==5 ;
    }

    private void updateView(int position) {
        try {
            if (position==100){
                finalOrderData.clear();
                finalOrderData.addAll(orderDetailsModel.getOrderList());
            }else if(position==99){
                finalOrderData.clear();
                for (int i=0;i<orderDetailsModel.getOrderList().size();i++){
                    if (orderDetailsModel.getOrderList().get(i).getPaymentstatus()!=null && orderDetailsModel.getOrderList().get(i).getPaymentstatus().contentEquals("1")){
                        finalOrderData.add(orderDetailsModel.getOrderList().get(i));
                    }
                }
            }else if(position==98){
                finalOrderData.clear();
                for (int i=0;i<orderDetailsModel.getOrderList().size();i++){
                    if (orderDetailsModel.getOrderList().get(i).getPaymentstatus()!=null && orderDetailsModel.getOrderList().get(i).getOrderstatus().contentEquals("5") && orderDetailsModel.getOrderList().get(i).getPaymentstatus().contentEquals("0")){
                        finalOrderData.add(orderDetailsModel.getOrderList().get(i));
                    }
                }
            }else{
                finalOrderData.clear();
                String pos= String.valueOf(position);
                for (int i=0;i<orderDetailsModel.getOrderList().size();i++){
                  //  if (orderDetailsModel.getOrderList().get(i).getOrderstatus()==null){}
                    if (orderDetailsModel.getOrderList().get(i).getOrderstatus()!=null && orderDetailsModel.getOrderList().get(i).getOrderstatus().contentEquals(pos)){
                        finalOrderData.add(orderDetailsModel.getOrderList().get(i));
                    }
                }
            }
            if (finalOrderData.size()<1){
                agentOrderList.noOrderFound();
            }else{
                agentOrderList.yesOrderFound();
            }
            //agentOrderList.recycler_view.removeAllViews();
            agentOrderList.getBinding().recyclerView.removeAllViews();
            agentJobListAdapterWithHeader.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
//        switch (position){
//            case 100:
//                showAllData();
//                break;
//            case 99:
//                break;
//            case 98:
//                break;
//            case 5:
//                break;
//            case 4:
//                break;
//
//        }

    }

}
