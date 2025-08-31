package com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentOrder;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.btracsolution.deliverysystem.Features.Agent.AgentOrderDetails.AgentOrderDetailActivity;
import com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider.AgentSelectRider;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.Profile.ActivityProfileAgent;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.StatusSortAdapter;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.AgentGlobal;
import com.btracsolution.deliverysystem.databinding.ActivityMenuDesignBinding;
import com.btracsolution.deliverysystem.databinding.ActivityMyOrderListAgentBinding;

/*import butterknife.BindView;
import butterknife.ButterKnife;*/

/**
 * Created by mahmudul.hasan on 1/4/2018.
 */

public class AgentOrderList extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ActivityMyOrderListAgentBinding binding;
    // activity_my_order_list_agent

    public AgentPresenter agentPresenter;
/*    @BindView(R.id.rel_no_order)
    RelativeLayout rel_no_order;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.rvHorizontal)
    RecyclerView rvHorizontal;
    @BindView(R.id.pull_to_refresh)
    SwipeRefreshLayout pull_to_refresh;*/
    AgentJobListAdapter agentJobListAdapter;
    AgentJobListAdapterWithHeader agentJobListAdapterWithHeader;


    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/*        v = inflater.inflate(R.layout.activity_my_order_list_agent, container, false);
        ButterKnife.bind(this, v);
        setHasOptionsMenu(true);
        return v;*/


        binding = ActivityMyOrderListAgentBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        setOnclickListener();
        return binding.getRoot();
    }

    public ActivityMyOrderListAgentBinding getBinding() {
        return binding;
    }

    private void setOnclickListener() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecycleView();
        initHorizontalRecyclerView();
        agentPresenter = new AgentPresenter();
        agentPresenter.attachView(getActivity(), getActivity(), this);
        agentPresenter.getServerData(true);
//        AgentSelectRider.open(getActivity(), "121", getString(R.string.select_rider), "RIDER");

        binding.pullToRefresh.setOnRefreshListener(this);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        setupRecycleView();
//        agentPresenter = new AgentPresenter();
//        agentPresenter.attachView(getActivity(), getActivity(), this);
//        agentPresenter.getServerData(true);
////        AgentSelectRider.open(getActivity(), "121", getString(R.string.select_rider), "RIDER");
//
//        pull_to_refresh.setOnRefreshListener(this);
//    }

    private void initHorizontalRecyclerView(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvHorizontal.setLayoutManager(layoutManager);
    }
    public void setAdapterForHoriZontalRecycleView(StatusSortAdapter statusSortAdapter) {
        binding.rvHorizontal.setAdapter(statusSortAdapter);

    }

    public void setupRecycleView() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(llm);
    }

    public void setAdapterIntoRecyclView(AgentJobListAdapter agentJobListAdapter) {
        this.agentJobListAdapter = agentJobListAdapter;
        binding.recyclerView.setAdapter(agentJobListAdapter);
    }

    public void setAdapterIntoRecyclView(AgentJobListAdapterWithHeader agentJobListAdapter) {
        this.agentJobListAdapterWithHeader = agentJobListAdapter;
        binding.recyclerView.setAdapter(agentJobListAdapter);
    }

    public void showToast(String message) {
        if (getActivity() != null && !getActivity().isFinishing())
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void yesOrderFound() {
        binding.relNoOrder.setVisibility(View.GONE);
        binding.pullToRefresh.setRefreshing(false);
    }

    public void noOrderFound() {
        binding.relNoOrder.setVisibility(View.VISIBLE);
        binding.pullToRefresh.setRefreshing(false);


    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("Back", "Worked");
//    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint() && v != null) {
            return;
        }
        checkMyCurrentJob();

        //INSERT CUSTOM CODE HERE

    }

    public void checkMyCurrentJob() {
        try {
            if (agentJobListAdapter != null && AgentGlobal.isComesFromDetails) {
                AgentGlobal.isComesFromDetails = false;
                agentJobListAdapter.getItemFromThePosition(AgentGlobal.listPosition).setOrderstatus(AgentGlobal.CurrentStatus);
                agentJobListAdapter.getItemFromThePosition(AgentGlobal.listPosition).setOrderstatus(AgentGlobal.CurrentStatus);
                agentJobListAdapter.notifyItemChanged(AgentGlobal.listPosition);
                if (agentPresenter != null)
                    agentPresenter.getServerData(false);

            } else {
                if (! binding.pullToRefresh.isRefreshing()) {
                    binding.pullToRefresh.setRefreshing(true);
                }
                if (agentPresenter != null)
                    agentPresenter.getServerData(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // binding = null;
    }

    @Override
    public void onRefresh() {
        if (agentPresenter != null)
            agentPresenter.getServerData(false);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here

        try {
            // inflater = getActivity().getMenuInflater();
            // Inflate menu to add items to action bar if it is present.
            inflater.inflate(R.menu.menu_profile, menu);
            // Associate searchable configuration with the SearchView
        } catch (Exception e) {
            e.printStackTrace();
        }
        // super.onCreateOptionsMenu(menu, inflater);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_menu:
                ActivityProfileAgent.open(getActivity());
                break;
        }
        return false;
    }
}
