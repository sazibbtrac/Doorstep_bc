package com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentRider;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider.AgentRiderListAdapter;
import com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider.AgentSelectRidePresenter;
import com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider.AgentSelectRider;
import com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider.AgentSelectRiderModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.ActivityAllRiderBinding;
import com.btracsolution.deliverysystem.databinding.ActivityDaydetailsBinding;

/*
import butterknife.BindView;
import butterknife.ButterKnife;
*/

public class AllRider extends Fragment implements SearchView.OnQueryTextListener {
 /*   @BindView(R.id.recycler_view)
    RecyclerView recycler_view;*/
    AgentAllRidePresenter agentSelectRidePresenter;
    String order_id;
    private AgentSelectRiderModel adapterIntoRecycleView;
    private ActivityAllRiderBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       /* View v = inflater.inflate(R.layout.activity_all_rider, container, false);
        ButterKnife.bind(this, v);
        setHasOptionsMenu(true);
        setupRecycleView();
        getActivity().setTitle(AllRider.this.getResources().getString(R.string.runner_list));
        return v;*/


        binding = ActivityAllRiderBinding.inflate(inflater, container, false);
        //  topBinding = RowProfileTopBinding.bind(binding.rowProfileTop.getRoot());
        //  bottomBinding = RowProfileBottomBinding.bind(binding.rowProfileBottom.getRoot());
        setHasOptionsMenu(true);
        setupRecycleView();
        setOnclickListener();
        getActivity().setTitle(AllRider.this.getResources().getString(R.string.runner_list));
        return binding.getRoot();
    }

    private void setOnclickListener() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        agentSelectRidePresenter = new AgentAllRidePresenter(getActivity(), AllRider.this);
        agentSelectRidePresenter.getRiderData();

    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public void setadapterIntoRecycleView(AgentAllRiderListAdapter adapterIntoRecycleView) {
        if (adapterIntoRecycleView != null)
            binding.recyclerView.setAdapter(adapterIntoRecycleView);
    }

    public void setupRecycleView() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(llm);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here

        try {
            // inflater = getActivity().getMenuInflater();
            // Inflate menu to add items to action bar if it is present.
            inflater.inflate(R.menu.search_view_top_rider, menu);
            // Associate searchable configuration with the SearchView
            SearchManager searchManager =
                    (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);


            SearchView searchView = (SearchView) menu.findItem(R.id.menu_search_id).getActionView();
            searchView.setOnQueryTextListener(this);
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getActivity().getComponentName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // super.onCreateOptionsMenu(menu, inflater);
        return;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        System.out.println(" respse onsubmit " + query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (binding.recyclerView.getAdapter() == null)
            return false;
        if (!newText.equals("")) {
            ((AgentAllRiderListAdapter) binding.recyclerView.getAdapter()).getFilter().filter(newText.toString());
        } else {
            ((AgentAllRiderListAdapter) binding.recyclerView.getAdapter()).getFilter().filter("");
        }
        System.out.println(" respse " + newText);
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // binding = null;
    }
}
