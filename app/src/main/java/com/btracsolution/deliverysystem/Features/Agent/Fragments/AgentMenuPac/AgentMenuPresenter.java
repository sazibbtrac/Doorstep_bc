package com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentMenuPac;

import android.app.Activity;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Features.Agent.AgentMenuList.AgentMenuDetailActivity;
import com.btracsolution.deliverysystem.Model.MenuFoodListModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mahmudul.hasan on 2/13/2018.
 */

public class AgentMenuPresenter {

    Activity activity;

    AgentMenu agentMenu;
    AgentMenuModel agentMenuModel;
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

    public AgentMenuPresenter(Activity activity, AgentMenu agentMenu) {
        this.activity = activity;
        this.agentMenu = agentMenu;
        this.agentMenuModel = new AgentMenuModel(this.activity, baseServerListener);
    }

    public void getServerData(boolean isLoading) {
        this.agentMenuModel.getServerData(isLoading);
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
        AgentMenuDetailActivity.open(activity, foodcategory,position);
    }

}
