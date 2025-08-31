package com.btracsolution.deliverysystem.Features.Rider.Features.MyJobPack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.AppUpdateAndShowingEmergencyMessages;
import com.btracsolution.deliverysystem.AppsPackageUpdateChecker.RemoteKeys.FirebaseRemoteKeys;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.RiderGlobal;
import com.btracsolution.deliverysystem.databinding.ActivityDaydetailsBinding;
import com.btracsolution.deliverysystem.databinding.ActivityMyOrderListAgentBinding;
import com.btracsolution.deliverysystem.databinding.ActivityMyOrderListBinding;

/*
import butterknife.BindView;
import butterknife.ButterKnife;
*/

/**
 * Created by mahmudul.hasan on 1/4/2018.
 */

public class RiderJobList extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ActivityMyOrderListBinding binding;

    public RiderJobPresenter riderJobPresenter;
  /*  @BindView(R.id.rel_no_order)
    RelativeLayout rel_no_order;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
*/    RiderJobAdapter riderJobAdapter;


/*    @BindView(R.id.pull_to_refresh)
    SwipeRefreshLayout pull_to_refresh;*/


    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = ActivityMyOrderListBinding.inflate(inflater, container, false);
        //  topBinding = RowProfileTopBinding.bind(binding.rowProfileTop.getRoot());
        //  bottomBinding = RowProfileBottomBinding.bind(binding.rowProfileBottom.getRoot());
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecycleView();
        riderJobPresenter = new RiderJobPresenter();
        riderJobPresenter.attachView(getActivity(), this);
        riderJobPresenter.getServerData(true);
        binding.pullToRefresh.setOnRefreshListener(this);
    }


    public void setupRecycleView() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(llm);
    }

    public void setAdapterIntoRecyclView(RiderJobAdapter riderJobAdapter) {
        this.riderJobAdapter = riderJobAdapter;
        binding.recyclerView.setAdapter(riderJobAdapter);
    }

    public void showToast(String message) {
        try {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void yesOrderFound() {
        binding.relNoOrder.setVisibility(View.GONE);
        binding.pullToRefresh.setRefreshing(false);

    }

    public void noOrderFound() {
        binding.relNoOrder.setVisibility(View.VISIBLE);
        binding.pullToRefresh.setRefreshing(false);

    }

    public void swipRefreshOff() {

        if (binding.pullToRefresh.isRefreshing()) {
            binding.pullToRefresh.setRefreshing(true);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Back", "Worked");
    }

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

    private void checkMyCurrentJob() {
        try {
            if (riderJobAdapter != null && RiderGlobal.isComesFromDetails) {
                RiderGlobal.isComesFromDetails = false;
                if (riderJobPresenter != null)
                    riderJobPresenter.getServerData(false);


            } else {
    //            if (!pull_to_refresh.isRefreshing()) {
    //                pull_to_refresh.setRefreshing(true);
    //            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        if (riderJobPresenter != null)
            riderJobPresenter.getServerData(false);
    }


}
