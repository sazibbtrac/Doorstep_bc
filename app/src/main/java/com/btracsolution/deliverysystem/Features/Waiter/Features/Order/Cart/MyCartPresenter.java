package com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart;

import android.widget.Toast;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Base.FoodItemClickListener;
import com.btracsolution.deliverysystem.Model.CartItem;
import com.btracsolution.deliverysystem.Model.DeliveryZoneResponse;
import com.btracsolution.deliverysystem.Model.MemberInfoResponse;
import com.btracsolution.deliverysystem.Model.MemberListResponse;
import com.btracsolution.deliverysystem.Model.MenuFoodListModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.Utils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class MyCartPresenter {

    MyCartActivity activity;
    MenuFoodListModel menuFoodListModel;
    // AgentMenuDetailsModel agentMenuDetailsModel;
    MyCartAdapter myCartAdapter;
    MyCartRepo myCartRepo;

    FoodItemClickListener baseItemClickListener = new FoodItemClickListener() {

        @Override
        public void onAddFoodItem(boolean isClick, int position, int qty) {
            //Toast.makeText(activity, foodItemListAdapter.getFoodID(position), Toast.LENGTH_SHORT).show();
            addToCart(position,qty);

        }

        @Override
        public void onSubstractFoodItem(boolean isClick, int position, int qty) {
            //Toast.makeText(activity, foodItemListAdapter.getFoodID(position), Toast.LENGTH_SHORT).show();
            removeFromCart(position,qty);

        }
        @Override
        public void onAddRemarks(boolean isClick, int position, String notes) {
            try {
                activity.cartItems.get(position).setRemarks(notes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void addToCart(int position, int qty) {
        try {
            CartItem foodDetails= myCartAdapter.getFoodDetails(position);
            String foodId,foodName,foodDetail,foodPicture,foodStatus,foodPrice;
            int quantity;
            foodId=foodDetails.getFoodId();
            foodName=foodDetails.getFoodName();
            foodDetail=foodDetails.getFoodDetail();
            foodPicture=foodDetails.getFoodPicture();
            foodStatus=foodDetails.getFoodStatus();
            foodPrice=foodDetails.getFoodPrice();
            boolean isExists=false;
            for (int i=0;i<activity.cartItems.size();i++){
                if (activity.cartItems.get(i).getFoodId().equals(foodId)){
                    activity.cartItems.get(i).setQuantity(qty);
                    isExists=true;
                }
            }
            if (isExists==false) {
               // activity.cartItems.add(new CartItem(foodId, foodName, foodDetail, foodPicture, foodStatus, foodPrice, qty));
            }
            activity.countTotalPrice();

            //activity.tvCount.setText(""+activity.cartItems.size());
            // activity.resetSharedDataCart();
            System.out.println("cart"+activity.cartItems);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void removeFromCart(int position, int qty) {
        try {
            CartItem foodDetails= myCartAdapter.getFoodDetails(position);
            String foodId,foodName,foodDetail,foodPicture,foodStatus,foodPrice;
            int quantity;
            foodId=foodDetails.getFoodId();
            foodName=foodDetails.getFoodName();
            foodDetail=foodDetails.getFoodDetail();
            foodPicture=foodDetails.getFoodPicture();
            foodStatus=foodDetails.getFoodStatus();
            foodPrice=foodDetails.getFoodPrice();
            for (int i=0;i<activity.cartItems.size();i++){
                if (activity.cartItems.get(i).getFoodId().equals(foodId)){
                    if (qty==0){
                        activity.cartItems.remove(i);
                        myCartAdapter.notifyItemRemoved(position);
                        myCartAdapter.notifyDataSetChanged();
                    }
                    else {
                        activity.cartItems.get(i).setQuantity(qty);
                    }
                }
            }
            activity.countTotalPrice();
           // activity.tvCount.setText(""+activity.cartItems.size());
            //activity.resetSharedDataCart();
            System.out.println("cart"+activity.cartItems);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (message != null && isSuccess) {
                if (message=="0") {
                    Toast.makeText(activity, "Order Placed", Toast.LENGTH_SHORT).show();
                    activity.cartItems.clear();
                    activity.sharedData.unsetCartData();
                    activity.finish();
                }
                else if (message=="1"){
                    MemberListResponse memberInfoResponse= (MemberListResponse) object;
                    activity.memberListResponses.addAll(memberInfoResponse.getMemberData());
                    activity.setupMemberAdapter();
//                    Glide.with(activity)
//                            .load(memberInfoResponse.getData().getMemberImage())
//                            .centerCrop()
//                            //.placeholder(R.drawable.dr_logo)
//                            //.error(R.drawable.ic_broken_image_black_36dp)
//                            .into(activity.civMem);
//                    activity.etMemId.clearFocus();
//                    Utils.hideKeyboard(activity);

                }
                else if (message=="2"){
                    DeliveryZoneResponse deliveryZoneResponse= (DeliveryZoneResponse) object;
                    activity.deliveryZoneResponseList.clear();
                    activity.deliveryZoneResponseList.addAll(deliveryZoneResponse.getData());

                    activity.deliveryTableNoResponseList.clear();
                    activity.deliveryTableNoResponseList.addAll(deliveryZoneResponse.getTableNos());

                }
                else if (message=="3" && isSuccess){
                    try {
                        MemberInfoResponse memberInfoResponse= (MemberInfoResponse) object;
                        if (memberInfoResponse!=null){
                            activity.memId=memberInfoResponse.getData().getMemberId();
                           // getServerData(false,memberInfoResponse.getData().getMemberId());
                            activity.getBinding().actvMemid.setText(memberInfoResponse.getData().getMemberId());
                            activity.getBinding().tvMemName.setText(memberInfoResponse.getData().getMemberName());
                            activity.getBinding().actvMemid.dismissDropDown();
                            activity.getBinding().actvMemid.clearListSelection();
                            activity.getBinding().actvMemid.clearFocus();

                            Glide.with(activity)
                                    .load(memberInfoResponse.getData().getMemberImage())
                                    .centerCrop()
                                    .into(activity.getBinding().civMem);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    billActivity.memberListResponses.addAll(memberInfoResponse.getMemberData());
//                    billActivity.setupMemberAdapter();
                }

            }else if (message!=null){
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                activity.memId="";
            }

        }
    };
    public void sendDataToServer(boolean isLoading,String memid,String locationid,String payMethod,String payStatus,String tableid) {
        this.myCartRepo.sendOrderToServer(isLoading,memid,locationid,payMethod, payStatus,new Gson().toJson(activity.cartItems),tableid);
    }
    public void getSingleMemberData(boolean isLoading,String memid) {
        this.myCartRepo.getSingleMemberData(isLoading,memid);
    }
    public void getMemberInfoFromServer(boolean isLoading) {
        this.myCartRepo.getMemberData(isLoading,activity.getBinding().etMemId.getText().toString());
    }
    public void getAllMemberListFromServer(boolean isLoading) {
        this.myCartRepo.getAllMemberData(isLoading);
    }
    public MyCartPresenter(MyCartActivity activity) {
        this.activity = activity;
        this.myCartRepo = new MyCartRepo(this.activity, baseServerListener);
        //   agentMenuDetailsModel = new AgentMenuDetailsModel(this.activity, baseServerListener);
    }

    public void prepareDataForAdapter() {

        myCartAdapter = new MyCartAdapter(activity, baseItemClickListener, activity.cartItems);
        activity.setAdapterForRecycleView(myCartAdapter);
    }

    public void getDeliveryZoneData(boolean isLoading) {
        this.myCartRepo.getDeliveryZone(isLoading);

    }
}
