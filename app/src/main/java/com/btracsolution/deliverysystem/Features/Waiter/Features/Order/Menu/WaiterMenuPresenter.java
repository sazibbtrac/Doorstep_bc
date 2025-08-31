package com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Menu;

import android.app.Activity;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.FoodItemList;
import com.btracsolution.deliverysystem.Model.MenuFoodListModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class WaiterMenuPresenter {



    Activity activity;

    WaiterMenuCategory agentMenu;
    WaiterMenuRepo waiterMenuRepo;
    MenuFoodListModel menuFoodListModel;

    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (isSuccess) {
                MenuFoodListModel menuFoodListModel = (MenuFoodListModel) object;
                if (menuFoodListModel != null) {
                    prepareCategories(menuFoodListModel);
                }
            }

        }
    };

    public WaiterMenuPresenter(Activity activity, WaiterMenuCategory agentMenu) {
        this.activity = activity;
        this.agentMenu = agentMenu;
        this.waiterMenuRepo = new WaiterMenuRepo(this.activity, baseServerListener);
    }

    public void getServerData(boolean isLoading) {
        this.waiterMenuRepo.getServerData(isLoading);
    }

    public void prepareCategories(MenuFoodListModel menuFoodListModel) {
        this.menuFoodListModel = menuFoodListModel;
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        for (int i = 0; i < menuFoodListModel.getMenuData().size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put("name", menuFoodListModel.getMenuData().get(i).getCategoryName());
            arrayList.add(hashMap);//add the hashmap into arrayList
        }
        this.agentMenu.setSimpleListView(arrayList);
    }

    public void goRespectiveCategories(int position) {
        String foodcategory = new Gson().toJson(menuFoodListModel);
        FoodItemList.open(activity, foodcategory,position);
    }

}
