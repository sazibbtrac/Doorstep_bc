package com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterDayReport;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentDayWiseResult.AgentDayListAdapter;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentDayWiseResult.AgentDayPresenter;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentDayWiseResult.AgentDayReport;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.Profile.ActivityProfileAgent;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.Utility.Validation;
import com.btracsolution.deliverysystem.databinding.FragmentWaiterDayReportBinding;
import com.btracsolution.deliverysystem.databinding.RowAgentDatepickerBinding;
import com.btracsolution.deliverysystem.databinding.RowProfileTopBinding;
import com.btracsolution.deliverysystem.databinding.ToolbarBinding;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaiterDayReport#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaiterDayReport extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentWaiterDayReportBinding binding;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WaiterDayReport() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaiterDayReport.
     */
    // TODO: Rename and change types and number of parameters
    public static WaiterDayReport newInstance(String param1, String param2) {
        WaiterDayReport fragment = new WaiterDayReport();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //Toolbar
  /*  @BindView(R.id.tvCount)
    TextView tvCount;
    @BindView(R.id.llBack)
    LinearLayout llBack;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlCart)
    RelativeLayout rlCart;
    @BindView(R.id.ivCart)
    ImageView ivCart;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.recycler_view_day_report)
    RecyclerView recycler_view_day_report;

    @BindView(R.id.tx_today_date)
    TextView tx_today_date;

    @BindView(R.id.rel_no_order)
    RelativeLayout rel_no_order;


    @BindView(R.id.lin_from_date_pick)
    LinearLayout lin_from_date_pick;


    @BindView(R.id.tx_from_date)
    TextView tx_from_date;
*/
    @Inject
    Validation validation;
    private RowAgentDatepickerBinding datepickerBinding;
    private ToolbarBinding toolbarBinding;


    /*
    @BindView(R.id.lin_to_date_pick)
    LinearLayout lin_to_date_pick;


    @BindView(R.id.tx_to_date)
    TextView tx_to_date;*/
    WaiterDayPresenter waiterDayPresenter;
    SharedData sharedData;
    private String fromDate;
    private String toDate;

    private BroadcastReceiver receiver;

    public static String getCalculatedDateBefore(int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

  //  @OnClick(R.id.lin_from_date_pick)
    public void clickFromDate() {

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        datepickerBinding.txFromDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        fromDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        getServerData(fromDate, toDate);

                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");

    }

   // @OnClick(R.id.lin_to_date_pick)
    public void clickToDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        datepickerBinding.txToDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        toDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        getServerData(fromDate, toDate);

                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWaiterDayReportBinding.inflate(inflater, container, false);
        datepickerBinding = RowAgentDatepickerBinding.bind(binding.getRoot().findViewById(R.id.row_agent_datepicker));
        toolbarBinding = ToolbarBinding.bind(binding.getRoot().findViewById(R.id.toolbar));

        setHasOptionsMenu(true);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((DeliveryApp) getActivity().getApplicationContext()).getDoNetworkComponent().inject(this);

        setClickListener();

        sharedData= new SharedData(getContext());
        waiterDayPresenter = new WaiterDayPresenter();
        waiterDayPresenter.attachView(getActivity(), getActivity(), WaiterDayReport.this);

        fromDate = getCalculatedDateBefore(-7);
        toDate = getTodayDate();
        setupRecycleView();
        setUpToolBar();
        getServerData(fromDate, toDate);

    }

    private void setClickListener() {
        datepickerBinding.txFromDate.setOnClickListener(this);
        datepickerBinding.txToDate.setOnClickListener(this);
    }


    public void checkMyCurrentJob() {
        getServerData(fromDate, toDate);
    }

    public void getServerData(String FromDate, String Today) {

        if (fromDate != null && toDate != null) {

            if (!validation.isDatePassed(fromDate, toDate))
                if (waiterDayPresenter != null) {
                    waiterDayPresenter.getDataFromServer(fromDate, toDate);
                    datepickerBinding.txFromDate.setText(FromDate);
                    datepickerBinding.txToDate.setText(Today);
                }
        }
    }

    public String getTodayDate() {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        return date;
    }

    public void setupRecycleView() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerViewDayReport.setLayoutManager(llm);
    }

    public void setAdapterIntoRecyclView(WaiterDayListAdapter waiterDayListAdapter) {
        binding.recyclerViewDayReport.setAdapter(waiterDayListAdapter);
    }


    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void yesOrderFound() {
        binding.relNoOrder.setVisibility(View.GONE);
        binding.recyclerViewDayReport.setVisibility(View.VISIBLE);
    }

    public void noOrderFound() {
        binding.relNoOrder.setVisibility(View.VISIBLE);

        binding.recyclerViewDayReport.setVisibility(View.INVISIBLE);

    }

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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.profile_menu:
//                ActivityProfileAgent.open(getActivity());
//                break;
//        }
//        return false;
//    }

    public void registerReceiver() {

        IntentFilter filter = new IntentFilter();
        filter.addAction("notification");


        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                getServerData(fromDate, toDate);
            }
        };
        try {
            getActivity().registerReceiver(receiver, filter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        registerReceiver();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
            receiver = null;
        }
    }

    private void setUpToolBar() {
        toolbarBinding.ivBack.setVisibility(View.GONE);
        toolbarBinding.rlCart.setVisibility(View.VISIBLE);
        if (sharedData != null) {
//            Toast.makeText(this, sharedData.getMyData(), Toast.LENGTH_SHORT).show();
            if (sharedData.getMyData() != null) {
                // setTitleOfActivity(sharedData.getMyData().getData().getRestaurantInfo().getName() + " " + "➞" + " " + sharedData.getMyData().getData().getBranchInfo().get(0).getBranchName());
                toolbarBinding.tvTitle.setText(sharedData.getMyData().getData().getRestaurantInfo().getName() + " " + "\n" +sharedData.getMyData().getData().getFullName());
                //setSubTitleOfActivity(sharedData.getMyData().getData().getFullName());
            }

        }else{

        }
        //   tvTitle.setText("Order List");
        toolbarBinding.ivBack.setVisibility(View.GONE);
        toolbarBinding.ivCart.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.lin_from_date_pick:
                clickFromDate();
                break;

            case R.id.lin_to_date_pick:
                clickToDate();
                break;


        }

    }
}