package com.btracsolution.deliverysystem.Features.Agent.AgentMenuList;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.Model.MenuFoodListModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.ActivityHomeAgentBinding;
import com.btracsolution.deliverysystem.databinding.ActivityMenuDetailsBinding;
import com.google.gson.Gson;



/**
 * Created by mahmudul.hasan on 1/4/2018.
 */

public class AgentMenuDetailActivity extends BaseActivity<ActivityMenuDetailsBinding> {

/*    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;*/
    AgentMenuPresenter agentMenuPresenter;


    public static void open(Context context, String food_category, int position) {
        Intent intent = new Intent(context, AgentMenuDetailActivity.class);
        intent.putExtra("food_category", food_category);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

  /*  @Override
    protected int getLayoutId() {
        return R.layout.activity_menu_details;
    }*/

    @Override
    public void initView() {
        super.initView();
        initializeToolbar();
        setupRecycleView();
        getIntentData();

    }

    @Override
    protected ActivityMenuDetailsBinding getViewBinding() {
        return ActivityMenuDetailsBinding.inflate(getLayoutInflater());    }

    private void initializeToolbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(AgentMenuDetailActivity.this, R.color.colorAccent));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setupRecycleView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(llm);
    }

    public void setAdapterForRecycleView(AgentMenuDetailsAdapter adapterForRecycleView) {
        binding.recyclerView.setAdapter(adapterForRecycleView);

    }

    public void setTitleOfActivity(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void getIntentData() {
        if (getIntent() != null) {
            MenuFoodListModel menuFoodListModel = new Gson().fromJson(getIntent().getStringExtra("food_category"), MenuFoodListModel.class);
            if (menuFoodListModel != null) {
                agentMenuPresenter = new AgentMenuPresenter(AgentMenuDetailActivity.this, menuFoodListModel, getIntent().getIntExtra("position", 0));
                agentMenuPresenter.prepareDataForAdapter(menuFoodListModel);
                setTitleOfActivity(menuFoodListModel.getMenuData().get(getIntent().getIntExtra("position", 0)).getCategoryName());

            }
        } else
            finish();
    }
}
