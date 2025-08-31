package com.btracsolution.deliverysystem.Features.Rider.Features.RiderRecentOrder;

import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.Validation;
import com.btracsolution.deliverysystem.databinding.ActivityProfileRiderBinding;
import com.btracsolution.deliverysystem.databinding.ActivityRiderDaydetailsBinding;
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

public class RiderDayReport extends Fragment implements View.OnClickListener{
    private ActivityRiderDaydetailsBinding binding;

/*    @BindView(R.id.recycler_view_day_report)
    RecyclerView recycler_view_day_report;

    @BindView(R.id.tx_today_date)
    TextView tx_today_date;

    @BindView(R.id.rel_no_order)
    RelativeLayout rel_no_order;


    @BindView(R.id.lin_from_date_pick)
    LinearLayout lin_from_date_pick;


    @BindView(R.id.tx_from_date)
    TextView tx_from_date;*/
    @Inject
    Validation validation;


/*    @BindView(R.id.lin_to_date_pick)
    LinearLayout lin_to_date_pick;


    @BindView(R.id.tx_to_date)
    TextView tx_to_date;*/
    RiderDayPresenter agentDayPresenter;
    private String fromDate;
    private String toDate;

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
        //binding = null;
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


        binding = ActivityRiderDaydetailsBinding.inflate(inflater, container, false);
        setOnClicklistener();
        return binding.getRoot();
    }

    private void setOnClicklistener() {
        binding.rowAgentDatepicker.linFromDatePick.setOnClickListener(this);
        binding.rowAgentDatepicker.linToDatePick.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DeliveryApp) getActivity().getApplicationContext()).getDoNetworkComponent().inject(this);


        agentDayPresenter = new RiderDayPresenter();
        agentDayPresenter.attachView(getActivity(), getActivity(), RiderDayReport.this);

        fromDate = getCalculatedDateBefore(-7);
        toDate = getTodayDate();
        setupRecycleView();

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

    public void setAdapterIntoRecyclView(RiderDayListAdapter agentJobListAdapter) {
        binding.recyclerViewDayReport.setAdapter(agentJobListAdapter);
    }


    public void showToast(String message) {
        try {
            if (getActivity()!=null) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void yesOrderFound() {
        binding.relNoOrder.setVisibility(View.GONE);
        binding.recyclerViewDayReport.setVisibility(View.VISIBLE);
    }

    public void noOrderFound() {
        binding.relNoOrder.setVisibility(View.VISIBLE);

        binding.recyclerViewDayReport.setVisibility(View.INVISIBLE);

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
