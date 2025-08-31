package com.btracsolution.deliverysystem.Features.Waiter.Features.BillHistory;

import android.widget.Toast;

import com.btracsolution.deliverysystem.Base.BaseItemBillSync;
import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Model.BillHistoryResponse;
import com.btracsolution.deliverysystem.Model.MemberInfoResponse;
import com.btracsolution.deliverysystem.Model.MemberListResponse;
import com.btracsolution.deliverysystem.Model.MenuFoodListModel;
import com.btracsolution.deliverysystem.Utility.PrintUtility;

public class BillHistoryPresenter {

    BillHistoryActivity activity;
    MenuFoodListModel menuFoodListModel;
    // AgentMenuDetailsModel agentMenuDetailsModel;
    BillHistoryAdapter billHistoryAdapter;
    BillHistoryRepository billHistoryRepository;
    BillHistoryResponse billHistoryResponse;

    public BillHistoryPresenter(BillHistoryActivity activity) {
        this.activity = activity;
        billHistoryRepository = new BillHistoryRepository(activity, baseServerListener);
    }
    BaseItemClickListener baseItemClickListener = new BaseItemClickListener() {
        @Override
        public void onClickOfListItem(boolean isClick, int position, String extra) {
            if (extra.contentEquals("print") && billHistoryResponse!=null){
                if (activity.mBluetoothSocket!=null && activity.mBluetoothSocket.isConnected() && billHistoryResponse!=null) {
                    PrintUtility.printBillHistory(activity.mBluetoothSocket,billHistoryResponse.getData().get(position),activity);
                }else{
                    Toast.makeText(activity, "Printer not connected", Toast.LENGTH_SHORT).show();
                }
            }
            else if (billHistoryResponse != null) {
              //  BillItemActivity.open(activity, billHistoryResponse.getData().get(position));
            }
        }


    };

    BaseItemBillSync baseItemBillSync = new BaseItemBillSync() {


        @Override
        public void onClickForSync(int position, int billid) {
            billHistoryRepository.setSyncData(true,position,billid);
        }
    };




    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (message != null) {
                if (message == "1" && isSuccess) {
                    MemberListResponse memberInfoResponse = (MemberListResponse) object;
                    activity.memberListResponses.addAll(memberInfoResponse.getMemberData());
                    activity.setupMemberAdapter();
                }
                else if(message == "4" && isSuccess){
                    try{
                       // System.out.println("hdskdff "+activity.memberid);
                        if (!activity.memberid.equals("")){
                            getBillHistoryFromServer(false,activity.memberid);
                        }
                    }
                    catch (Exception e) {
                       // System.out.println("fdsjfsdfkhgf "+e.toString());
                        e.printStackTrace();
                    }
                }
                else if (message=="2" && isSuccess){

                    try {
                        MemberInfoResponse memberInfoResponse= (MemberInfoResponse) object;
                        if (memberInfoResponse!=null){
                            activity.memberid=memberInfoResponse.getData().getMemberId();
                            getBillHistoryFromServer(false,memberInfoResponse.getData().getMemberId());
                            activity.getBinding().etSearch.setText(memberInfoResponse.getData().getMemberId());
                            activity.getBinding().etSearch.dismissDropDown();
                            activity.getBinding().etSearch.clearListSelection();
                            activity.getBinding().etSearch.clearFocus();
                        }
                    } catch (Exception e) {
                       // System.out.println("fdsjfsdfkhgf "+e.toString());
                        e.printStackTrace();
                    }
//                    billActivity.memberListResponses.addAll(memberInfoResponse.getMemberData());
//                    billActivity.setupMemberAdapter();
                }

                else if (message=="3" && isSuccess){
                    billHistoryResponse= (BillHistoryResponse) object;

                    if (billHistoryResponse.getData().size()>0){
                        activity.yesOrderFound();
                        BillHistoryAdapter billAdapterWithHeader = new BillHistoryAdapter(activity, billHistoryResponse.getData(), baseItemClickListener,baseItemBillSync);
                        activity.setAdapterIntoRecyclView(billAdapterWithHeader);
                    }
                    else{
                        activity.noOrderFound();

                    }
                }else{
                    activity.showToast(message);
                }
            }else{
                activity.showToast("Not found");
            }
        }



    };

    public void getSingleMemberData(boolean isLoading,String memid) {
        billHistoryRepository.getSingleMemberData(isLoading,memid);
    }
    public void getAllMemberListFromServer(boolean isLoading) {
        billHistoryRepository.getAllMemberData(isLoading);
    }
    public void getBillHistoryFromServer(boolean isLoading,String mem_id) {
        billHistoryRepository.getBillFromServer(isLoading,mem_id);
    }
}

