package com.btracsolution.deliverysystem.Features.Agent.Fragments.Statement;

import android.os.Build;
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

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Features.Agent.Fragments.Profile.ActivityProfileAgent;
import com.btracsolution.deliverysystem.Model.StatementModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.ActivityDayListStatementAgentBinding;
import com.btracsolution.deliverysystem.databinding.ActivityMyOrderListAgentBinding;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/*import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;*/

/**
 * Created by mahmudul.hasan on 1/4/2018.
 */

public class AgentDayStatement extends Fragment implements View.OnClickListener{
/*    @BindView(R.id.tx_last_week)
    TextView tx_last_week;
    @BindView(R.id.tx_last_month)
    TextView tx_last_month;
    @BindView(R.id.tx_last_year)
    TextView tx_last_year;
    @BindView(R.id.tx_last_custom)
    TextView tx_last_custom;

    @BindView(R.id.tx_from_date)
    TextView tx_from_date;

    @BindView(R.id.tx_to_date)
    TextView tx_to_date;

    @BindView(R.id.tx_grand_total)
    TextView tx_grand_total;

    @BindView(R.id.tx_total_delivery_number)
    TextView tx_total_delivery_number;

    @BindView(R.id.tx_total_subtotal)
    TextView tx_total_subtotal;

    @BindView(R.id.tx_total_vat)
    TextView tx_total_vat;

    @BindView(R.id.tx_total_charge)
    TextView tx_total_charge;


    @BindView(R.id.recycler_view_day_report)
    RecyclerView recycler_view_day_report;


    @BindView(R.id.lin_custom_date_picker)
    LinearLayout lin_custom_date_picker;

    @BindView(R.id.lin_to_date_pick)
    LinearLayout lin_to_date_pick;

    @BindView(R.id.lin_from_date_pick)
    LinearLayout lin_from_date_pick;

    @BindView(R.id.rel_yes)
    RelativeLayout rel_yes;


    @BindView(R.id.rel_no)
    RelativeLayout rel_no;*/

    //activity_day_list_statement_agent
    private ActivityDayListStatementAgentBinding binding;


    AgentStatementPresenter agentStatementPresenter;


    String fromDate;
    String toDate;


  //  @OnClick(R.id.lin_from_date_pick)
    public void clickFromDate() {

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        binding.rowAgentDatepicker.txFromDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        fromDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        getDecisionOfBackGroundAndDatePickerViewer("last_custom");
                        if (agentStatementPresenter != null)
                            agentStatementPresenter.getServerData("last_custom");

                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");

    }

    //@OnClick(R.id.lin_to_date_pick)
    public void clickToDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        binding.rowAgentDatepicker.txToDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        toDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        getDecisionOfBackGroundAndDatePickerViewer("last_custom");
                        if (agentStatementPresenter != null)
                            agentStatementPresenter.getServerData("last_custom");

                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }


   // @OnClick(R.id.tx_last_week)
    public void onLastWeek() {
        getDecisionOfBackGroundAndDatePickerViewer("last_week");
        if (agentStatementPresenter != null)
            agentStatementPresenter.getServerData("last_week");

    }

  //  @OnClick(R.id.tx_last_month)
    public void onLastMonth() {
        getDecisionOfBackGroundAndDatePickerViewer("last_month");
        if (agentStatementPresenter != null)
            agentStatementPresenter.getServerData("last_month");


    }

   // @OnClick(R.id.tx_last_year)
    public void onLastYear() {
        getDecisionOfBackGroundAndDatePickerViewer("last_year");
        if (agentStatementPresenter != null)
            agentStatementPresenter.getServerData("last_year");

    }

   // @OnClick(R.id.tx_last_custom)
    public void onLasCustom() {


        fromDate = agentStatementPresenter.getTodayDate();
        toDate = agentStatementPresenter.getTodayDate();

        if (toDate != null && fromDate != null) {
            binding.rowAgentDatepicker.txToDate.setText(toDate);
            binding.rowAgentDatepicker.txFromDate.setText(fromDate);
        }

        getDecisionOfBackGroundAndDatePickerViewer("last_custom");
        if (agentStatementPresenter != null)
            agentStatementPresenter.getServerData("last_custom");
    }

    public void getDecisionOfBackGroundAndDatePickerViewer(String typeSelect) {
        switch (typeSelect) {
            case "last_week":
                binding.rowAgentDatepicker.linCustomDatePicker.setVisibility(View.GONE);
                setBackgroundAsShape(binding.rowAgentStatementTop.txLastWeek, R.drawable.sl_statemnt_draw_top_start, "h");
                setBackgroundAsShape(binding.rowAgentStatementTop.txLastMonth, R.drawable.statemnt_draw_top_middle, "d");
                setBackgroundAsShape(binding.rowAgentStatementTop.txLastYear, R.drawable.statemnt_draw_top_middle, "d");
                setBackgroundAsShape(binding.rowAgentStatementTop.txLastCustom, R.drawable.statemnt_draw_top_end, "d");
//                tx_last_week.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ready));
                break;
            case "last_month":
                binding.rowAgentDatepicker.linCustomDatePicker.setVisibility(View.GONE);
                setBackgroundAsShape(binding.rowAgentStatementTop.txLastWeek, R.drawable.statemnt_draw_top_start, "d");
                setBackgroundAsShape(binding.rowAgentStatementTop.txLastMonth, R.drawable.sl_statemnt_draw_top_middle, "h");
                setBackgroundAsShape(binding.rowAgentStatementTop.txLastYear, R.drawable.statemnt_draw_top_middle, "d");
                setBackgroundAsShape(binding.rowAgentStatementTop.txLastCustom, R.drawable.statemnt_draw_top_end, "d");

                break;
            case "last_year":
                binding.rowAgentDatepicker.linCustomDatePicker.setVisibility(View.GONE);
                setBackgroundAsShape(binding.rowAgentStatementTop.txLastWeek, R.drawable.statemnt_draw_top_start, "d");
                setBackgroundAsShape(binding.rowAgentStatementTop.txLastMonth, R.drawable.statemnt_draw_top_middle, "d");
                setBackgroundAsShape(binding.rowAgentStatementTop.txLastYear, R.drawable.sl_statemnt_draw_top_middle, "h");
                setBackgroundAsShape(binding.rowAgentStatementTop.txLastCustom, R.drawable.statemnt_draw_top_end, "d");


                break;
            case "last_custom":
                binding.rowAgentDatepicker.linCustomDatePicker.setVisibility(View.VISIBLE);

                setBackgroundAsShape(binding.rowAgentStatementTop.txLastWeek, R.drawable.statemnt_draw_top_start, "d");
                setBackgroundAsShape(binding.rowAgentStatementTop.txLastMonth, R.drawable.statemnt_draw_top_middle, "d");
                setBackgroundAsShape(binding.rowAgentStatementTop.txLastYear, R.drawable.statemnt_draw_top_middle, "d");
                setBackgroundAsShape(binding.rowAgentStatementTop.txLastCustom, R.drawable.sl_statemnt_draw_top_end, "h");

                break;
        }
    }

    public void setBackgroundAsShape(TextView textView, int highlightType, String h) {

        if (h.contentEquals("h"))
            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        else
            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.tx_color));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(ContextCompat.getDrawable(getActivity(), highlightType));
        } else {
            textView.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), highlightType));

        }
        setPaddingDp(textView, 8);
    }

    public void setPaddingDp(TextView textView, int paddingDp) {

        float density = getActivity().getResources().getDisplayMetrics().density;
        int paddingPixel = (int) (paddingDp * density);
        textView.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        agentStatementPresenter = new AgentStatementPresenter(getActivity(), this);

        if (!getActivity().isFinishing()) {
            if (binding.rowAgentStatementTop.txLastWeek != null) {
                binding.rowAgentStatementTop.txLastWeek.performClick();
            }
        }

        fromDate = agentStatementPresenter.getTodayDate();
        toDate = agentStatementPresenter.getTodayDate();

        if (toDate != null && fromDate != null) {
            binding.rowAgentDatepicker.txToDate.setText(toDate);
            binding.rowAgentDatepicker.txFromDate.setText(fromDate);
        }

        setupRecycleView();
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
   /*     View v = inflater.inflate(R.layout.activity_day_list_statement_agent, container, false);
        ButterKnife.bind(this, v);
        return v;*/


        binding = ActivityDayListStatementAgentBinding.inflate(inflater, container, false);

        setOnClickListener();
        return binding.getRoot();

    }

    private void setOnClickListener() {
        binding.rowAgentDatepicker.linToDatePick.setOnClickListener(this);
        binding.rowAgentDatepicker.linFromDatePick.setOnClickListener(this);
        binding.rowAgentStatementTop.txLastWeek.setOnClickListener(this);
        binding.rowAgentStatementTop.txLastMonth.setOnClickListener(this);
        binding.rowAgentStatementTop.txLastYear.setOnClickListener(this);
        binding.rowAgentStatementTop.txLastCustom.setOnClickListener(this);

    }

    public void setDetailsData(StatementModel detailsData) {
        if (detailsData != null) {
            binding.rowAgentStatementDetails.txGrandTotal.setText(getString(R.string.tk_sign) + " " + detailsData.getResponseData().getGrandCalculation().getGrandTotal());
            binding.rowAgentStatementDetails.txTotalDeliveryNumber.setText(detailsData.getResponseData().getGrandCalculation().getTotalOrder() + "");
            binding.rowAgentStatementDetails.txTotalSubtotal.setText(getString(R.string.tk_sign) + " " + detailsData.getResponseData().getGrandCalculation().getSubTotal());
            binding.rowAgentStatementDetails.txTotalVat.setText(getString(R.string.tk_sign) + " " + detailsData.getResponseData().getGrandCalculation().getTotalVatamount());
            binding.rowAgentStatementDetails.txTotalCharge.setText(getString(R.string.tk_sign) + " " + detailsData.getResponseData().getGrandCalculation().getTotalDeliverycharge());
        }
    }


    public void setupRecycleView() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rowAgentStatementDaily.recyclerViewDayReport.setLayoutManager(llm);
    }

    public void setAdapterIntoRecyclView(AgentStatementAdapter agentJobListAdapter) {
        binding.rowAgentStatementDaily.recyclerViewDayReport.setAdapter(agentJobListAdapter);
        binding.rowAgentStatementDaily.recyclerViewDayReport.setNestedScrollingEnabled(false);

    }

    public void noDataFound() {
        binding.relNo.setVisibility(View.VISIBLE);
        binding.relYes.setVisibility(View.GONE);
    }

    public void yesDataFound() {
        binding.relNo.setVisibility(View.GONE);
        binding.relYes.setVisibility(View.VISIBLE);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_from_date_pick:
                clickFromDate();
                break;

            case R.id.tx_last_custom:
                onLasCustom();
                break;

            case R.id.tx_last_year:
                onLastYear();
                break;

            case R.id.tx_last_month:
                onLastMonth();
                break;

            case R.id.tx_last_week:
                onLastWeek();
                break;

            case R.id.lin_to_date_pick:
                clickToDate();
                break;


        }
    }
}
