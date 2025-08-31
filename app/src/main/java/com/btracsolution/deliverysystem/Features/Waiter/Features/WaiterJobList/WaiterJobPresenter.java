package com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJoblistDetail.WaiterOrderDetailActivity;
import com.btracsolution.deliverysystem.Model.FoodMenuShowListModel;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.AgentGlobal;
import com.btracsolution.deliverysystem.Utility.LogoutUtility;
import com.btracsolution.deliverysystem.Utility.SharedData;

import java.util.ArrayList;

import javax.inject.Inject;

public class WaiterJobPresenter {
    WaiterJobList waiterJobList;
    WaiterJobRepo waiterJobRepo;
    WaiterJobListAdapterWithHeader waiterJobListAdapterWithHeader;
    StatusSortAdapter statusSortAdapter;
    Context context;
    Activity activity;
    OrderDetailsModel orderDetailsModel;
    @Inject
    SharedData sharedData;
    ArrayList<String> statusList=new ArrayList<>();
    ArrayList<OrderDetailsModel.orderBasicData> finalOrderData = new ArrayList<>();


    BaseItemClickListener baseItemClickListener = new BaseItemClickListener() {
        @Override
        public void onClickOfListItem(boolean isClick, int position, String extra) {
            if (extra.contentEquals("sort")){
               // waiterJobList.showToast(statusList.get(position));
                updateView(position);
            }
            else{
                if (finalOrderData != null && finalOrderData.size()>0) {
                    AgentGlobal.listPosition = position;
                    WaiterOrderDetailActivity.open(activity, finalOrderData.get(position),"joblist","","");

                }
            }

        }
    };

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
            }
            else{
                finalOrderData.clear();
                String pos= String.valueOf(position);
                for (int i=0;i<orderDetailsModel.getOrderList().size();i++){
                    if (orderDetailsModel.getOrderList().get(i).getOrderstatus()!=null && orderDetailsModel.getOrderList().get(i).getOrderstatus().contentEquals(pos)){
                        finalOrderData.add(orderDetailsModel.getOrderList().get(i));
                    }
                }
            }
            if (finalOrderData.size()<1){
                waiterJobList.getBinding().relNoOrder.setVisibility(View.VISIBLE);
            }else{
                waiterJobList.getBinding().relNoOrder.setVisibility(View.GONE);
            }
            waiterJobList.getBinding().recyclerView.removeAllViews();
            waiterJobListAdapterWithHeader.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (isSuccess) {
                orderDetailsModel = (OrderDetailsModel) object;
                if (orderDetailsModel.getOrderList().size() > 0) {
                    waiterJobList.yesOrderFound();

//                     Without filter just plane order list
                   /* AgentJobListAdapter agentJobListAdapter = new AgentJobListAdapter(context, orderDetailsModel.getOrderList(), baseItemClickListener);
                    agentOrderList.setAdapterIntoRecyclView(agentJobListAdapter);*/


                    /* With filter just plane order list*/
                    orderDetailsModel.setOrderList(analysisAndSetHeader(orderDetailsModel.getOrderList()));
                    finalOrderData.clear();
                    finalOrderData.addAll(orderDetailsModel.getOrderList());
                    waiterJobListAdapterWithHeader = new WaiterJobListAdapterWithHeader(context, finalOrderData, baseItemClickListener);
                    waiterJobList.setAdapterIntoRecyclView(waiterJobListAdapterWithHeader);

                    waiterJobList.getBinding().rvHorizontal.setVisibility(View.VISIBLE);
                    statusSortAdapter=new StatusSortAdapter(context,statusList,baseItemClickListener);
                    waiterJobList.setAdapterForHoriZontalRecycleView(statusSortAdapter);


                } else {
//                    agentOrderList.showToast(context.getString(R.string.no_order));
                    waiterJobList.noOrderFound();
                    waiterJobList.getBinding().rvHorizontal.setVisibility(View.GONE);
                }


            } else {
                orderDetailsModel = (OrderDetailsModel) object;
                if (orderDetailsModel == null)
                    waiterJobList.noOrderFound();
                else if (orderDetailsModel.getError().getError_code().contentEquals("401")) {
                    waiterJobList.showToast(orderDetailsModel.getMessage());
                    new LogoutUtility().setLoggedOut(activity);
                } else {
                    waiterJobList.showToast(orderDetailsModel.getMessage());
                    waiterJobList.getBinding().pullToRefresh.setRefreshing(false);
                }

            }
        }
    };

    public void attachView(Context context, Activity activity, WaiterJobList waiterJobList) {
        this.context = context;
        this.activity = activity;
        this.waiterJobList = waiterJobList;
        waiterJobRepo = new WaiterJobRepo(context, baseServerListener);
        sharedData = new SharedData(context);
        generateSortArrayList();
    }

    private void generateSortArrayList() {
        statusList.add("100");//All
        statusList.add("99");//paid
        statusList.add("98");//unpaid
        statusList.add("5");
        //statusList.add("4");
    }

    public void getServerData(boolean isLoading) {
        waiterJobRepo.getServerData(isLoading);
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
        return currentStatus == 5;
    }

}
