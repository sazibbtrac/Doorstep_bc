package com.btracsolution.deliverysystem.Features.Agent.AgentMenuList;

import android.widget.Toast;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Model.FoodMenuShowListModel;
import com.btracsolution.deliverysystem.Model.MenuFoodListModel;

import java.util.ArrayList;

/**
 * Created by mahmudul.hasan on 2/13/2018.
 */

public class AgentMenuPresenter {

    private final int position;
    AgentMenuDetailActivity activity;
    MenuFoodListModel menuFoodListModel;
    AgentMenuDetailsModel agentMenuDetailsModel;
    AgentMenuDetailsAdapter agentMenuDetailsAdapter;
    BaseItemClickListener baseItemClickListener = new BaseItemClickListener() {
        @Override
        public void onClickOfListItem(boolean isClick, int position, String extra) {
            if (isClick) {
                switch (extra) {
                    case "checked":
                        agentMenuDetailsModel.changeMenuFoodAvailability(agentMenuDetailsAdapter.getFoodID(position), "1");
                        break;
                    case "unchecked":
                        agentMenuDetailsModel.changeMenuFoodAvailability(agentMenuDetailsAdapter.getFoodID(position), "0");
                        break;
                }
            }
        }
    };
    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (message != null)
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();


        }
    };

    public AgentMenuPresenter(AgentMenuDetailActivity activity, MenuFoodListModel menuFoodListModel, int position) {
        this.activity = activity;
        this.menuFoodListModel = menuFoodListModel;
        this.position = position;
        agentMenuDetailsModel = new AgentMenuDetailsModel(this.activity, baseServerListener);
    }

    public void prepareDataForAdapter(MenuFoodListModel menuFoodListModel) {

        ArrayList<FoodMenuShowListModel> foodMenuShowListModels = new ArrayList<>();

        ArrayList<MenuFoodListModel.FoodGroups> foodGroupsArrayList = menuFoodListModel.getMenuData().get(position).getFoodgroups();
        for (int i = 0; i < foodGroupsArrayList.size(); i++) {

            FoodMenuShowListModel foodMenuShowListModel = new FoodMenuShowListModel();
            foodMenuShowListModel.setHeader(true);
            foodMenuShowListModel.setHeaderTitle(foodGroupsArrayList.get(i).getFoodgroupName());
            foodMenuShowListModels.add(foodMenuShowListModel);
            for (int j = 0; j < foodGroupsArrayList.get(i).getFoods().size(); j++) {
                FoodMenuShowListModel foodMenuShowListModelGeneric = new FoodMenuShowListModel();
                foodMenuShowListModelGeneric.setHeader(false);
                foodMenuShowListModelGeneric.setFoodId(foodGroupsArrayList.get(i).getFoods().get(j).getFoodId());
                foodMenuShowListModelGeneric.setFoodName(foodGroupsArrayList.get(i).getFoods().get(j).getFoodName());
                foodMenuShowListModelGeneric.setFoodDetail(foodGroupsArrayList.get(i).getFoods().get(j).getFoodDetail());
                foodMenuShowListModelGeneric.setFoodPicture(foodGroupsArrayList.get(i).getFoods().get(j).getFoodPicture());
                foodMenuShowListModelGeneric.setFoodStatus(foodGroupsArrayList.get(i).getFoods().get(j).getFoodStatus());
                foodMenuShowListModelGeneric.setFoodPrice(foodGroupsArrayList.get(i).getFoods().get(j).getFoodPrice());
                foodMenuShowListModels.add(foodMenuShowListModelGeneric);

            }
        }
        agentMenuDetailsAdapter = new AgentMenuDetailsAdapter(activity, foodMenuShowListModels, baseItemClickListener);

        activity.setAdapterForRecycleView(agentMenuDetailsAdapter);
    }
}
