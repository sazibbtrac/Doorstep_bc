package com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.Profile.ActivityProfileAgent;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.SubCategoryAdapter;
import com.btracsolution.deliverysystem.Features.Waiter.Features.ProfileWaiter.ProfileWaiter;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.AgentGlobal;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.databinding.ActivityProductlistBinding;
import com.btracsolution.deliverysystem.databinding.FragmentWaiterJobListBinding;
import com.btracsolution.deliverysystem.databinding.FragmentWaiterMenuBinding;

import javax.inject.Inject;

/*import butterknife.BindView;
import butterknife.ButterKnife;*/

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaiterJobList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaiterJobList extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
//fragment_waiter_job_list
    private FragmentWaiterJobListBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Inject
    SharedData sharedData;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WaiterJobPresenter waiterJobPresenter;
/*    @BindView(R.id.rel_no_order)
    RelativeLayout rel_no_order;
    @BindView(R.id.rvHorizontal)
    RecyclerView rvHorizontal;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.pull_to_refresh)
    SwipeRefreshLayout pull_to_refresh;*/
    WaiterJobAdapter waiterJobAdapter;
    WaiterJobListAdapterWithHeader waiterJobListAdapterWithHeader;


    View v;

/*
    //Toolbar
    @BindView(R.id.tvCount)
    TextView tvCount;
    @BindView(R.id.llBack)
    LinearLayout llBack;
    @BindView(R.id.rlCart)
    RelativeLayout rlCart;
    @BindView(R.id.ivCart)
    ImageView ivCart;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;*/

    public WaiterJobList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaiterJobList.
     */
    // TODO: Rename and change types and number of parameters
    public static WaiterJobList newInstance(String param1, String param2) {
        WaiterJobList fragment = new WaiterJobList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public FragmentWaiterJobListBinding getBinding() {
        return binding;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      /*  v = inflater.inflate(R.layout.fragment_waiter_job_list, container, false);
        ButterKnife.bind(this, v);
        setHasOptionsMenu(true);
        return v;*/

        binding = FragmentWaiterJobListBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        setOnclickListener();
        return binding.getRoot();
    }

    private void setOnclickListener() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecycleView();
        initHorizontalRecyclerView();
        waiterJobPresenter = new WaiterJobPresenter();
        waiterJobPresenter.attachView(getActivity(), getActivity(), this);
       // waiterJobPresenter.getServerData(true);
//        AgentSelectRider.open(getActivity(), "121", getString(R.string.select_rider), "RIDER");

        binding.pullToRefresh.setOnRefreshListener(this);
        setUpToolBar();

    }

    private void setUpToolBar() {
        sharedData= new SharedData(this.getActivity());
        if (sharedData != null) {
//            Toast.makeText(this, sharedData.getMyData(), Toast.LENGTH_SHORT).show();
            if (sharedData.getMyData() != null) {
                // setTitleOfActivity(sharedData.getMyData().getData().getRestaurantInfo().getName() + " " + "➞" + " " + sharedData.getMyData().getData().getBranchInfo().get(0).getBranchName());
                binding.bar.tvTitle.setText(sharedData.getMyData().getData().getRestaurantInfo().getName() + " " + "\n" +sharedData.getMyData().getData().getFullName());
                //setSubTitleOfActivity(sharedData.getMyData().getData().getFullName());
            }

        }else{

        }
     //   tvTitle.setText("Order List");
        binding.bar.ivBack.setVisibility(View.GONE);
    }
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

    public void setAdapterIntoRecyclView(WaiterJobAdapter waiterJobAdapter) {
        this.waiterJobAdapter = waiterJobAdapter;
        binding.recyclerView.setAdapter(waiterJobAdapter);
    }

    public void setAdapterIntoRecyclView(WaiterJobListAdapterWithHeader waiterJobListAdapterWithHeader) {
        this.waiterJobListAdapterWithHeader = waiterJobListAdapterWithHeader;
        binding.recyclerView.setAdapter(waiterJobListAdapterWithHeader);
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

    public void checkMyCurrentJob() {
        try {
            if (waiterJobAdapter != null && AgentGlobal.isComesFromDetails) {
                AgentGlobal.isComesFromDetails = false;
                waiterJobAdapter.getItemFromThePosition(AgentGlobal.listPosition).setOrderstatus(AgentGlobal.CurrentStatus);
                waiterJobAdapter.getItemFromThePosition(AgentGlobal.listPosition).setOrderstatus(AgentGlobal.CurrentStatus);
                waiterJobAdapter.notifyItemChanged(AgentGlobal.listPosition);
                if (waiterJobPresenter != null)
                    waiterJobPresenter.getServerData(false);

            } else {
                if (!binding.pullToRefresh.isRefreshing()) {
                    binding.pullToRefresh.setRefreshing(true);
                }
                if (waiterJobPresenter != null)
                    waiterJobPresenter.getServerData(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        if (waiterJobPresenter != null)
            waiterJobPresenter.getServerData(false);

    }
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // TODO Add your menu entries here
//
//        try {
//            // inflater = getActivity().getMenuInflater();
//            // Inflate menu to add items to action bar if it is present.
//            inflater.inflate(R.menu.menu_profile, menu);
//            // Associate searchable configuration with the SearchView
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // super.onCreateOptionsMenu(menu, inflater);
//        return;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.profile_menu:
//                ProfileWaiter.open(getActivity());
//                break;
//        }
//        return false;
//    }


}