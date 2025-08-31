package com.btracsolution.deliverysystem.Features.Waiter.Features.Order;

import android.view.View;
import android.widget.Toast;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Base.FoodItemClickListener;
import com.btracsolution.deliverysystem.Model.CartItem;
import com.btracsolution.deliverysystem.Model.FoodMenuShowListModel;
import com.btracsolution.deliverysystem.Model.MenuFoodListModel;

import java.util.ArrayList;

public class FoodItemListPresenter {

    private final int position;
    FoodItemList activity;
    MenuFoodListModel menuFoodListModel;
   // AgentMenuDetailsModel agentMenuDetailsModel;
    FoodItemListAdapter foodItemListAdapter;
    SubCategoryAdapter subCategoryAdapter;
    ArrayList<FoodMenuShowListModel> foodMenuShowListModelsFinal = new ArrayList<>();

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
        public void onAddRemarks(boolean isClick, int position, String extra) {

        }

    };

    private void addToCart(int position, int qty) {
        try {
            FoodMenuShowListModel foodDetails= foodItemListAdapter.getFoodDetails(position);
            String foodId,foodName,foodDetail,foodPicture,foodStatus,foodPrice;
            int quantity,kitchenid;
            foodId=foodDetails.getFoodId();
            foodName=foodDetails.getFoodName();
            foodDetail=foodDetails.getFoodDetail();
            foodPicture=foodDetails.getFoodPicture();
            foodStatus=foodDetails.getFoodStatus();
            foodPrice=foodDetails.getFoodPrice();
            kitchenid=foodDetails.getKitchenid();
            boolean isExists=false;
            for (int i=0;i<activity.cartItems.size();i++){
                if (activity.cartItems.get(i).getFoodId().equals(foodId)){
                        activity.cartItems.get(i).setQuantity(qty);
                        isExists=true;
                }
            }
            if (isExists==false) {
                activity.cartItems.add(new CartItem(foodId, foodName,kitchenid, foodDetail, foodPicture, foodStatus, foodPrice, qty,""));
            }
            activity.getBinding().toolbar.tvCount.setVisibility(View.VISIBLE);
            activity.getBinding().toolbar.tvCount.setText(""+activity.cartItems.size());

            activity.getBinding().llCountBottom.setVisibility(View.VISIBLE);
            activity.getBinding().tvCountBottom.setText(""+activity.cartItems.size());
            activity.countTotalPrice();
           // activity.resetSharedDataCart();
            System.out.println("cart"+activity.cartItems);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void removeFromCart(int position, int qty) {
        try {
            FoodMenuShowListModel foodDetails= foodItemListAdapter.getFoodDetails(position);
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
                    }
                    else {
                        activity.cartItems.get(i).setQuantity(qty);
                    }
                }
            }
            activity.getBinding().toolbar.tvCount.setText(""+activity.cartItems.size());
            activity.getBinding().tvCountBottom.setText(""+activity.cartItems.size());
            activity.countTotalPrice();
            if (activity.cartItems.size()<1){
                activity.getBinding().llCountBottom.setVisibility(View.GONE);
            }
            //activity.resetSharedDataCart();
            System.out.println("cart"+activity.cartItems);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    BaseItemClickListener subCategoryAdapterClick = new BaseItemClickListener() {

        @Override
        public void onClickOfListItem(boolean isClick, int position, String extra) {
            MenuFoodListModel.FoodGroups foodDetails= subCategoryAdapter.getFoodGroupsDetails(position);
            updateFoodItemsList(foodDetails);

        }
    };
    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (message != null)
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();


        }
    };

    public FoodItemListPresenter(FoodItemList activity, MenuFoodListModel menuFoodListModel, int position) {
        this.activity = activity;
        this.menuFoodListModel = menuFoodListModel;
        this.position = position;
     //   agentMenuDetailsModel = new AgentMenuDetailsModel(this.activity, baseServerListener);
    }

    public void prepareDataForAdapter(MenuFoodListModel menuFoodListModel) {

        try {
            ArrayList<MenuFoodListModel.FoodGroups> foodGroupsArrayList = menuFoodListModel.getMenuData().get(position).getFoodgroups();
            for (int i = 0; i < foodGroupsArrayList.size(); i++) {

                FoodMenuShowListModel foodMenuShowListModel = new FoodMenuShowListModel();
                foodMenuShowListModel.setHeader(true);
                foodMenuShowListModel.setHeaderTitle(foodGroupsArrayList.get(i).getFoodgroupName());
                foodMenuShowListModelsFinal.add(foodMenuShowListModel);
                for (int j = 0; j < foodGroupsArrayList.get(i).getFoods().size(); j++) {
                    if (foodGroupsArrayList.get(i).getFoods().get(j).getFoodStatus()!=null && foodGroupsArrayList.get(i).getFoods().get(j).getFoodStatus().contentEquals("1")) {
                        FoodMenuShowListModel foodMenuShowListModelGeneric = new FoodMenuShowListModel();
                        foodMenuShowListModelGeneric.setHeader(false);
                        foodMenuShowListModelGeneric.setFoodId(foodGroupsArrayList.get(i).getFoods().get(j).getFoodId());
                        foodMenuShowListModelGeneric.setFoodName(foodGroupsArrayList.get(i).getFoods().get(j).getFoodName());
                        foodMenuShowListModelGeneric.setFoodDetail(foodGroupsArrayList.get(i).getFoods().get(j).getFoodDetail());
                        foodMenuShowListModelGeneric.setFoodPicture(foodGroupsArrayList.get(i).getFoods().get(j).getFoodPicture());
                        foodMenuShowListModelGeneric.setFoodStatus(foodGroupsArrayList.get(i).getFoods().get(j).getFoodStatus());
                        foodMenuShowListModelGeneric.setFoodPrice(foodGroupsArrayList.get(i).getFoods().get(j).getFoodPrice());
                        foodMenuShowListModelGeneric.setKitchenid(foodGroupsArrayList.get(i).getFoods().get(j).getKitchenid());
                        foodMenuShowListModelsFinal.add(foodMenuShowListModelGeneric);
                    }

                }
            }
            foodItemListAdapter = new FoodItemListAdapter(activity, foodMenuShowListModelsFinal, baseItemClickListener, activity.cartItems);
            activity.setAdapterForRecycleView(foodItemListAdapter);

            foodGroupsArrayList.add(new MenuFoodListModel().new FoodGroups("","All",""));
            subCategoryAdapter = new SubCategoryAdapter(activity, foodGroupsArrayList, subCategoryAdapterClick);
            activity.setAdapterForHoriZontalRecycleView(subCategoryAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateFoodItemsList(MenuFoodListModel.FoodGroups foodGroup){
        try {
            if (foodGroup.getFoodgroupName().contentEquals("All")){
               showAllSubCategoryList();
            }else {
                foodMenuShowListModelsFinal.clear();
                FoodMenuShowListModel foodMenuShowListModel = new FoodMenuShowListModel();
                foodMenuShowListModel.setHeader(true);
                foodMenuShowListModel.setHeaderTitle(foodGroup.getFoodgroupName());
                foodMenuShowListModelsFinal.add(foodMenuShowListModel);
                for (int j = 0; j < foodGroup.getFoods().size(); j++) {
                    if (foodGroup.getFoods().get(j).getFoodStatus()!=null && foodGroup.getFoods().get(j).getFoodStatus().contentEquals("1")) {

                        FoodMenuShowListModel foodMenuShowListModelGeneric = new FoodMenuShowListModel();
                        foodMenuShowListModelGeneric.setHeader(false);
                        foodMenuShowListModelGeneric.setFoodId(foodGroup.getFoods().get(j).getFoodId());
                        foodMenuShowListModelGeneric.setFoodName(foodGroup.getFoods().get(j).getFoodName());
                        foodMenuShowListModelGeneric.setFoodDetail(foodGroup.getFoods().get(j).getFoodDetail());
                        foodMenuShowListModelGeneric.setFoodPicture(foodGroup.getFoods().get(j).getFoodPicture());
                        foodMenuShowListModelGeneric.setFoodStatus(foodGroup.getFoods().get(j).getFoodStatus());
                        foodMenuShowListModelGeneric.setFoodPrice(foodGroup.getFoods().get(j).getFoodPrice());
                        foodMenuShowListModelGeneric.setKitchenid(foodGroup.getFoods().get(j).getKitchenid());
                        foodMenuShowListModelsFinal.add(foodMenuShowListModelGeneric);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        foodItemListAdapter.notifyDataSetChanged();
    }

    private void showAllSubCategoryList() {
        try {
            foodMenuShowListModelsFinal.clear();
            ArrayList<MenuFoodListModel.FoodGroups> foodGroupsArrayList = menuFoodListModel.getMenuData().get(position).getFoodgroups();
            for (int i = 0; i < foodGroupsArrayList.size(); i++) {

                if (foodGroupsArrayList.get(i).getFoodgroupName().contentEquals("All")){
                    continue;
                }

                FoodMenuShowListModel foodMenuShowListModel = new FoodMenuShowListModel();
                foodMenuShowListModel.setHeader(true);
                foodMenuShowListModel.setHeaderTitle(foodGroupsArrayList.get(i).getFoodgroupName());
                foodMenuShowListModelsFinal.add(foodMenuShowListModel);
                for (int j = 0; j < foodGroupsArrayList.get(i).getFoods().size(); j++) {
                    if (foodGroupsArrayList.get(i).getFoods().get(j).getFoodStatus()!=null && foodGroupsArrayList.get(i).getFoods().get(j).getFoodStatus().contentEquals("1")) {
                        FoodMenuShowListModel foodMenuShowListModelGeneric = new FoodMenuShowListModel();
                        foodMenuShowListModelGeneric.setHeader(false);
                        foodMenuShowListModelGeneric.setFoodId(foodGroupsArrayList.get(i).getFoods().get(j).getFoodId());
                        foodMenuShowListModelGeneric.setFoodName(foodGroupsArrayList.get(i).getFoods().get(j).getFoodName());
                        foodMenuShowListModelGeneric.setFoodDetail(foodGroupsArrayList.get(i).getFoods().get(j).getFoodDetail());
                        foodMenuShowListModelGeneric.setFoodPicture(foodGroupsArrayList.get(i).getFoods().get(j).getFoodPicture());
                        foodMenuShowListModelGeneric.setFoodStatus(foodGroupsArrayList.get(i).getFoods().get(j).getFoodStatus());
                        foodMenuShowListModelGeneric.setFoodPrice(foodGroupsArrayList.get(i).getFoods().get(j).getFoodPrice());
                        foodMenuShowListModelGeneric.setKitchenid(foodGroupsArrayList.get(i).getFoods().get(j).getKitchenid());
                        foodMenuShowListModelsFinal.add(foodMenuShowListModelGeneric);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
