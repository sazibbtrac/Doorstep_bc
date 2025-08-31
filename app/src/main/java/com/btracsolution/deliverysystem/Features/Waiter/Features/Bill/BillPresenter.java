package com.btracsolution.deliverysystem.Features.Waiter.Features.Bill;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJoblistDetail.WaiterOrderDetailActivity;
import com.btracsolution.deliverysystem.Model.MemberInfoResponse;
import com.btracsolution.deliverysystem.Model.MemberListResponse;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.PrintUtility;
import com.btracsolution.deliverysystem.Utility.SharedData;

import java.util.ArrayList;

import javax.inject.Inject;

public class BillPresenter {

    BillActivity billActivity;
    BillRepo billRepo;
    Context context;
    Activity activity;
    OrderDetailsModel orderDetailsModel;
    @Inject
    SharedData sharedData;

    BaseItemClickListener baseItemClickListener = new BaseItemClickListener() {
        @Override
        public void onClickOfListItem(boolean isClick, int position, String extra) {
            if (extra.contentEquals("AgentJob")) {
                if (orderDetailsModel != null) {
                    WaiterOrderDetailActivity.open(activity, orderDetailsModel.getOrderList().get(position),"bill","","");
                }
            }
            else if (extra.contentEquals("checked")){
                //billActivity.showToast(""+orderDetailsModel.getOrderList().get(position).getTotalOrder());
                calculateTotal(orderDetailsModel.getOrderList().get(position),"1");
                checkallCheckedOrNot();

            }else if (extra.contentEquals("unchecked")){
              //  billActivity.showToast(""+orderDetailsModel.getOrderList().get(position).getTotalOrder());
                calculateTotal(orderDetailsModel.getOrderList().get(position),"0");
                checkallCheckedOrNot();


            }
        }
    };

    private void checkallCheckedOrNot() {
        boolean allChecked=true;
        if (orderDetailsModel.getOrderList().size()>0) {
            for (int i = 0; i < orderDetailsModel.getOrderList().size(); i++) {
                if (orderDetailsModel.getOrderList().get(i).isOrderChecked()) {

                } else {
                    allChecked = false;
                    break;
                }
            }
            if (allChecked) {
                billActivity.getBinding().cbAll.setChecked(true);
            }else{
                billActivity.getBinding().cbAll.setChecked(false);
            }
        }
    }

    public void calculateTotal(OrderDetailsModel.orderBasicData orderBasicData,String addorsub) {
        try {
            int newTotal=0;
            float amount= Float.parseFloat((orderBasicData.getTotalamount()));
            float totalPrev= Float.parseFloat(billActivity.totalBill);
            if (addorsub.contentEquals("1")) {
                 newTotal = (int) (amount + totalPrev);
            }else{
                 newTotal= (int) (totalPrev-amount);
            }
            billActivity.totalBill= String.valueOf(newTotal);
            billActivity.getBinding().tvGrandTotal.setText(String.valueOf(newTotal) + " BDT");
            billActivity.getBinding().etPayAmount.setText(String.valueOf(newTotal) + " BDT");

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (message != null) {
                if (message=="0" && isSuccess) {
                    orderDetailsModel = (OrderDetailsModel) object;
                    if (orderDetailsModel.getOrderList().size() > 0) {
                        billActivity.yesOrderFound();

//                     Without filter just plane order list
                   /* AgentJobListAdapter agentJobListAdapter = new AgentJobListAdapter(context, orderDetailsModel.getOrderList(), baseItemClickListener);
                    agentOrderList.setAdapterIntoRecyclView(agentJobListAdapter);*/
                        billActivity.orderList.clear();
                        billActivity.orderList.addAll(orderDetailsModel.getOrderList());

                        /* With filter just plane order list*/
                        orderDetailsModel.setOrderList(analysisAndSetHeader(orderDetailsModel.getOrderList()));
                        BillAdapterWithHeader billAdapterWithHeader = new BillAdapterWithHeader(context, orderDetailsModel.getOrderList(), baseItemClickListener);
                        billActivity.setAdapterIntoRecyclView(billAdapterWithHeader);

                        billActivity.totalBill="0";
                        billActivity.getBinding().etPayAmount.setText("");
                        billActivity.getBinding().tvGrandTotal.setText("");
                        billActivity.getBinding().cbAll.setChecked(false);
                        billActivity.getBinding().llTotal.setVisibility(View.VISIBLE);
                        billActivity.getBinding().btnPay.setVisibility(View.VISIBLE);
                        billActivity.getBinding().llPaymentOption.setVisibility(View.VISIBLE);
                        billActivity.getBinding().llPaymentOption.setVisibility(View.VISIBLE);
                        billActivity.getBinding().tvPOptionLabel.setVisibility(View.VISIBLE);
                        billActivity.getBinding().cbAll.setVisibility(View.VISIBLE);
                       // billActivity.totalBill=orderDetailsModel.getBillDetails().getTotalamount().toString();
                        //billActivity.etPayAmount.setText(orderDetailsModel.getBillDetails().getTotalamount().toString()+" BDT");
                       // billActivity.tvGrandTotal.setText(orderDetailsModel.getBillDetails().getTotalamount().toString()+" BDT");

//                    BillAdapter billAdapterWithHeader = new BillAdapter(context, orderDetailsModel.getOrderList(), baseItemClickListener);
//                    billActivity.setAdapterIntoRecyclView(billAdapterWithHeader);

                    } else {
//                    agentOrderList.showToast(context.getString(R.string.no_order));
                        billActivity.noOrderFound();
                        billActivity.getBinding().llTotal.setVisibility(View.GONE);
                        billActivity.getBinding().btnPay.setVisibility(View.GONE);
                        billActivity.getBinding().llPaymentOption.setVisibility(View.GONE);
                        billActivity.getBinding().llPaymentOption.setVisibility(View.GONE);
                        billActivity.getBinding().tvPOptionLabel.setVisibility(View.GONE);
                        billActivity.getBinding().cbAll.setVisibility(View.GONE);
                        billActivity.totalBill="0";
                        billActivity.getBinding().cbAll.setChecked(false);
                        billActivity.getBinding().etPayAmount.setText("");
                        billActivity.getBinding().tvGrandTotal.setText("");

                    }


                }
                else if (message=="1" && isSuccess){
                    try {
                        MemberListResponse memberInfoResponse= (MemberListResponse) object;
                        billActivity.memberListResponses.addAll(memberInfoResponse.getMemberData());
                        billActivity.setupMemberAdapter();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (message=="2" && isSuccess){
                    try {
                        MemberInfoResponse memberInfoResponse= (MemberInfoResponse) object;
                        if (memberInfoResponse!=null){
                            billActivity.memberid=memberInfoResponse.getData().getMemberId();
                            getServerData(false,memberInfoResponse.getData().getMemberId());
                            billActivity.getBinding().etSearch.setText(memberInfoResponse.getData().getMemberId());
                            billActivity.getBinding().etSearch.dismissDropDown();
                            billActivity.getBinding().etSearch.clearListSelection();
                            billActivity.getBinding().etSearch.clearFocus();
                            billActivity.totalBill="0";
                            billActivity.getBinding().cbAll.setChecked(false);
                            billActivity.getBinding().etPayAmount.setText("");
                            billActivity.getBinding().tvGrandTotal.setText("");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    billActivity.memberListResponses.addAll(memberInfoResponse.getMemberData());
//                    billActivity.setupMemberAdapter();
                }
                else  if (message=="3" && isSuccess) {
                    billActivity.showToast("Payment Successfull");
                    if (billActivity.mBluetoothSocket!=null && billActivity.mBluetoothSocket.isConnected() && orderDetailsModel!=null) {
                        PrintUtility.printBill(billActivity.mBluetoothSocket,orderDetailsModel,activity,billActivity.payMethod,billActivity.totalBill);
                    }
                    clearView();

                }
                else {

                    billActivity.showToast(message);
                    clearView();

                }
            }else{
                billActivity.showToast("Not found");
            }
        }
    };

    private void clearView() {

        billActivity.noOrderFound();
        billActivity.getBinding().llTotal.setVisibility(View.GONE);
        billActivity.getBinding().btnPay.setVisibility(View.GONE);
        billActivity.getBinding().llPaymentOption.setVisibility(View.GONE);
        billActivity.getBinding().llPaymentOption.setVisibility(View.GONE);
        billActivity.getBinding().tvPOptionLabel.setVisibility(View.GONE);
        billActivity.payMethod="0";
        billActivity.getBinding().llPaymentMobile.setBackground(billActivity.getResources().getDrawable(R.drawable.silver_rounded_bg));
        billActivity.getBinding().llPaymentCash.setBackground(billActivity.getResources().getDrawable(R.drawable.silver_rounded_bg));
        billActivity.getBinding().llPaymentCard.setBackground(billActivity.getResources().getDrawable(R.drawable.silver_rounded_bg));
        billActivity.getBinding().ivCardTick.setVisibility(View.GONE);
        billActivity.getBinding().ivCashTick.setVisibility(View.GONE);
        billActivity.getBinding().ivMobileTick.setVisibility(View.GONE);
        billActivity.getBinding().etSearch.setText("");
        billActivity.totalBill="0";
        billActivity.getBinding().etPayAmount.setText("");
        billActivity.getBinding().tvGrandTotal.setText("");
    }

    public void attachView(Context context, Activity activity, BillActivity billActivity) {
        this.context = context;
        this.activity = activity;
        this.billActivity = billActivity;
        billRepo = new BillRepo(context, baseServerListener);
        sharedData = new SharedData(context);
    }

    public void getServerData(boolean isLoading,String memid) {
        billRepo.getServerData(isLoading,memid);
    }
    public void getSingleMemberData(boolean isLoading,String memid) {
        billRepo.getSingleMemberData(isLoading,memid);
    }
    public void payBillToServer(boolean isLoading,String memid,ArrayList<OrderDetailsModel.orderBasicData> orderBasicData,String totalbill,String payMethod) {
        billRepo.payBillToServer(isLoading,memid,orderBasicData, totalbill,payMethod);
    }
    public void getAllMemberListFromServer(boolean isLoading) {
        billRepo.getAllMemberData(isLoading);
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

//            OrderDetailsModel.orderBasicData orderBasicData = new OrderDetailsModel().new orderBasicData();
//            orderBasicData.setHeader(true);
//            orderBasicData.setTypeOfHeader(activity.getString(R.string.new_order));
//            orderBasicData.setTotalOrder(orderBasicDatasProcessing.size() + "");
//            AllOrderBasicDatasProcessing.add(orderBasicData);

            /* Set rest of the data into arraylist*/
            AllOrderBasicDatasProcessing.addAll(orderBasicDatasProcessing);

        }
        if (orderBasicDatasLocalCompleted.size() > 0) {

            /* Set header data*/

//            OrderDetailsModel.orderBasicData orderBasicData = new OrderDetailsModel().new orderBasicData();
//            orderBasicData.setHeader(true);
//            orderBasicData.setTypeOfHeader(activity.getString(R.string.processed_order));
//            orderBasicData.setTotalOrder(orderBasicDatasLocalCompleted.size() + "");
//            AllOrderBasicDatasProcessing.add(orderBasicData);

            /* Set rest of the data into arraylist*/
            AllOrderBasicDatasProcessing.addAll(orderBasicDatasLocalCompleted);

        }

        return AllOrderBasicDatasProcessing;


    }

    public boolean isProcessed(OrderDetailsModel.orderBasicData orderBasicData) {
        int currentStatus = Integer.parseInt(orderBasicData.getOrderstatus());
        return currentStatus > 4;
    }
}
