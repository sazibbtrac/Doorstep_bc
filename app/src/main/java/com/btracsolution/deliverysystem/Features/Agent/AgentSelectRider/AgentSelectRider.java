package com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.Validation;
import com.btracsolution.deliverysystem.databinding.ActivityAgentRiderListBinding;
import com.btracsolution.deliverysystem.databinding.ActivityOrderDetailsAgentBinding;
import com.google.gson.Gson;

/*import butterknife.BindView;*/

/**
 * Created by mahmudul.hasan on 1/4/2018.
 */

public class AgentSelectRider extends BaseActivity<ActivityAgentRiderListBinding> implements SearchView.OnQueryTextListener {
/*    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;*/
    AgentSelectRidePresenter agentSelectRidePresenter;
    String order_id;
    private AgentSelectRiderModel adapterIntoRecycleView;
    public OrderDetailsModel.orderBasicData orderBasicData;

    public static void open(Activity context, OrderDetailsModel.orderBasicData orderBasicData, String type_of_come, String RIDER_SELECT_TYPE) {
        Intent intent = new Intent(context, AgentSelectRider.class);
//        intent.putExtra("order_id", order_id);
        intent.putExtra("order_data", new Gson().toJson(orderBasicData));
        intent.putExtra("type_of_come", type_of_come);
        switch (RIDER_SELECT_TYPE) {
            case "RIDER":
                context.startActivityForResult(intent, 12);

                break;
            case "CHANGE":
                context.startActivityForResult(intent, 13);

                break;
        }
    }

    @Override
    public void initView() {
        super.initView();
        setHomeUpEnable(true);
        setupRecycleView();

        agentSelectRidePresenter = new AgentSelectRidePresenter(AgentSelectRider.this, AgentSelectRider.this);
        agentSelectRidePresenter.getRiderData();

        if (getIntent() != null) {
           // order_id = getIntent().getStringExtra("order_id");
            orderBasicData = new Gson().fromJson(getIntent().getStringExtra("order_data"), OrderDetailsModel.orderBasicData.class);
            order_id= orderBasicData.getOrderid();
            if (getIntent() != null && getIntent() != null) {
                setTitle(getIntent().getStringExtra("type_of_come"));
            }
        } else
            finish();
    }

    @Override
    protected ActivityAgentRiderListBinding getViewBinding() {
        return ActivityAgentRiderListBinding.inflate(getLayoutInflater());
    }

/*    @Override
    protected int getLayoutId() {
        return R.layout.activity_agent_rider_list;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            MenuInflater inflater = getMenuInflater();
            // Inflate menu to add items to action bar if it is present.
            inflater.inflate(R.menu.search_view_top_rider, menu);
            // Associate searchable configuration with the SearchView
            SearchManager searchManager =
                    (SearchManager) getSystemService(Context.SEARCH_SERVICE);


            SearchView searchView = (SearchView) menu.findItem(R.id.menu_search_id).getActionView();
            searchView.setOnQueryTextListener(this);
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));

            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTitle("");
                    //    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    //toolbar.setNavigationIcon(R.drawable.cancel_icon);
                    //mSearchView.setMaxWidth(android.R.attr.width);
                }
            });
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    //     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    setTitle(getIntent().getStringExtra("type_of_come"));
                    Validation.hideSoftKeyboard(AgentSelectRider.this);
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public void setadapterIntoRecycleView(AgentRiderListAdapter adapterIntoRecycleView) {
        if (adapterIntoRecycleView != null)
            binding.recyclerView.setAdapter(adapterIntoRecycleView);
    }

    public void setupRecycleView() {
        LinearLayoutManager llm = new LinearLayoutManager(AgentSelectRider.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(llm);
    }

    public void setDataAndFinishProperly(String order_basic_data) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("order_basic_data", order_basic_data);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void setDataForFinish() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (!newText.equals("")) {
            ((AgentRiderListAdapter) binding.recyclerView.getAdapter()).getFilter().filter(newText.toString());
        } else {
            ((AgentRiderListAdapter) binding.recyclerView.getAdapter()).getFilter().filter("");
        }
        return false;
    }
}
