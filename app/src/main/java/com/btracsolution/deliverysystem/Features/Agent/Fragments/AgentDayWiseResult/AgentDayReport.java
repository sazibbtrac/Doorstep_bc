package com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentDayWiseResult;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.Profile.ActivityProfileAgent;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.Validation;
import com.btracsolution.deliverysystem.databinding.ActivityDaydetailsBinding;
import com.btracsolution.deliverysystem.databinding.ActivityProfileBinding;
import com.btracsolution.deliverysystem.databinding.RowProfileBottomBinding;
import com.btracsolution.deliverysystem.databinding.RowProfileTopBinding;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

/*import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;*/

/**
 * Created by mahmudul.hasan on 1/4/2018.
 */

public class AgentDayReport extends Fragment implements View.OnClickListener {

   /* @BindView(R.id.recycler_view_day_report)
    RecyclerView recycler_view_day_report;

    @BindView(R.id.tx_today_date)
    TextView tx_today_date;

    @BindView(R.id.rel_no_order)
    RelativeLayout rel_no_order;


    @BindView(R.id.lin_from_date_pick)
    LinearLayout lin_from_date_pick;


    @BindView(R.id.tx_from_date)
    TextView tx_from_date;
*/    @Inject
    Validation validation;

/*

    @BindView(R.id.lin_to_date_pick)
    LinearLayout lin_to_date_pick;


    @BindView(R.id.tx_to_date)
    TextView tx_to_date;
*/
    AgentDayPresenter agentDayPresenter;
    private String fromDate;
    private String toDate;
    private ActivityDaydetailsBinding binding;

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
                        binding.rowAgentDatepicker.txFromDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // binding = null;
    }

    //  @OnClick(R.id.lin_to_date_pick)
    public void clickToDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        binding.rowAgentDatepicker.txToDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
   /*     View v = inflater.inflate(R.layout.activity_daydetails, container, false);
        ButterKnife.bind(this, v);
        setHasOptionsMenu(true);
        return v;*/

        binding = ActivityDaydetailsBinding.inflate(inflater, container, false);
      //  topBinding = RowProfileTopBinding.bind(binding.rowProfileTop.getRoot());
      //  bottomBinding = RowProfileBottomBinding.bind(binding.rowProfileBottom.getRoot());
        setHasOptionsMenu(true);
        setOnclickListener();
        return binding.getRoot();
    }

    private void setOnclickListener() {
        binding.rowAgentDatepicker.linFromDatePick.setOnClickListener(this);
        binding.rowAgentDatepicker.linToDatePick.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DeliveryApp) getActivity().getApplicationContext()).getDoNetworkComponent().inject(this);


        agentDayPresenter = new AgentDayPresenter();
        agentDayPresenter.attachView(getActivity(), getActivity(), AgentDayReport.this);

        fromDate = getCalculatedDateBefore(-7);
        toDate = getTodayDate();
        setupRecycleView();

        getServerData(fromDate, toDate);

    }

    public void checkMyCurrentJob() {
        getServerData(fromDate, toDate);
    }

    public void getServerData(String FromDate, String Today) {

        if (fromDate != null && toDate != null) {

            if (!validation.isDatePassed(fromDate, toDate))
                if (agentDayPresenter != null) {
                    agentDayPresenter.getDataFromServer(fromDate, toDate);
                    binding.rowAgentDatepicker.txFromDate.setText(FromDate);
                    binding.rowAgentDatepicker.txToDate.setText(Today);
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

    public void setAdapterIntoRecyclView(AgentDayListAdapter agentJobListAdapter) {
        binding.recyclerViewDayReport.setAdapter(agentJobListAdapter);
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
