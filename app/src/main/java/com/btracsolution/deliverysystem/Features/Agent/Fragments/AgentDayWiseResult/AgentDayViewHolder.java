package com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentDayWiseResult;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.RowAgentMyDayItemBinding;
import com.btracsolution.deliverysystem.databinding.RowAgentRiderListBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;*/

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class AgentDayViewHolder extends MainViewHolder implements View.OnClickListener {

    public BaseItemClickListener baseItemClickListener;
    private RowAgentMyDayItemBinding binding;
  /*  @BindView(R.id.tx_name_customer)
    TextView tx_name_customer;


    @BindView(R.id.tx_delivery_address)
    TextView tx_delivery_address;


    @BindView(R.id.tx_status)
    TextView tx_status;


    @BindView(R.id.tx_bdt_amount)
    TextView tx_bdt_amount;


    @BindView(R.id.tx_view_details)
    TextView tx_view_details;


    @BindView(R.id.tx_date)
    TextView tx_date;
    @BindView(R.id.tx_time)
    TextView tx_time;

    @BindView(R.id.tx_rider_name)
    TextView tx_rider_name;

    @BindView(R.id.linearRiderinfo)
    LinearLayout linearRiderinfo;

    @BindView(R.id.tx_order_from)
    TextView tx_order_from;

    @BindView(R.id.tx_pmethod)
    TextView tx_pmethod;

    @BindView(R.id.tx_order_no)
    TextView tx_order_no;*/

    int current_position;

    public AgentDayViewHolder(View itemView) {
        super(itemView);
        binding = RowAgentMyDayItemBinding.bind(itemView);
        binding.txViewDetails.setOnClickListener(this);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

  //  @OnClick(R.id.tx_view_details)
    public void viewDetailsOfTheProduct() {
        baseItemClickListener.onClickOfListItem(true, current_position, "from-report");
    }

    public void setDataIntoView(Context context, OrderDetailsModel.orderBasicData orderBasicData, BaseItemClickListener baseItemClickListener, int current_position) {
        this.baseItemClickListener = baseItemClickListener;
        this.current_position = current_position;
        binding.txOrderNo.setText("Order No: "+orderBasicData.getOrdernumber());
        binding.txNameCustomer.setText(orderBasicData.getMember().getName());
        binding.txBdtAmount.setText(context.getString(R.string.bdt) + " " + orderBasicData.getTotalamount());
        binding.txDeliveryAddress.setText(fromHtml(orderBasicData.getShippingaddress()));
        if (orderBasicData.getOrderfrom()!=null && orderBasicData.getOrderfrom().equals("1")){
            binding.txOrderFrom.setText("From Microsite");
        }else if(orderBasicData.getOrderfrom()!=null && orderBasicData.getOrderfrom().equals("2")){
            binding.txOrderFrom.setText("From Doorstep");
        }else if(orderBasicData.getOrderfrom()!=null && orderBasicData.getOrderfrom().equals("3")){
            binding.txOrderFrom.setText("From Website");
        }
        if (orderBasicData.getRider() != null) {
            binding.linearRiderinfo.setVisibility(View.VISIBLE);
            binding.txRiderName.setText(orderBasicData.getRider().getFullname());
        } else {
            binding.linearRiderinfo.setVisibility(View.GONE);
            binding.txRiderName.setText("");
        }
        switch (orderBasicData.getOrderstatus()) {
            case "0": // Pending
                binding.txStatus.setText(context.getString(R.string.Pending));

                break;
            case "1": // Ordered
                binding.txStatus.setText(context.getString(R.string.Ordered));

                break;
            case "2": // Processing
                binding.txStatus.setText(context.getString(R.string.Processing));

                break;
            case "3": // Ready for Deliver
                binding.txStatus.setText(context.getString(R.string.Deliver));

                break;
            case "4": // On Way
                binding.txStatus.setText(context.getString(R.string.way));

                break;
            case "5": // Delivered
                binding.txStatus.setText(context.getString(R.string.Delivered));

                break;
            case "6": // Canceled by Customer
                binding.txStatus.setText(context.getString(R.string.returned));

                break;
            case "7": // Canceled by Customer
                binding.txStatus.setText(context.getString(R.string.damaged));

                break;
            case "8": // Canceled by Call Center Agent
                binding.txStatus.setText(context.getString(R.string.completed_job));

                break;
            case "9": // Canceled by Branch Agent
                binding.txStatus.setText(context.getString(R.string.cancel_customer));

                break;
            case "10": // Canceled by Branch Agent
                binding.txStatus.setText(context.getString(R.string.cancel_call_agent));

                break;
            case "11": // Canceled by Branch Agent
                binding.txStatus.setText(context.getString(R.string.cancel_by_agent));

                break;
            case "12": // Canceled by Branch Agent
                binding.txStatus.setText(context.getString(R.string.inpantry));

                break;
            case "13": // Canceled by Branch Agent
                binding.txStatus.setText(context.getString(R.string.p_ready_to_pickup));

                break;
        }
        if (orderBasicData.getPaymentmethod()!=null && orderBasicData.getPaymentmethod().equals("1")){
            binding.txPmethod.setText("Payment Method: COD");
        }else if(orderBasicData.getPaymentmethod()!=null && orderBasicData.getPaymentmethod().equals("2")){
            binding.txPmethod.setText("Payment Method: CARD");
        }else if(orderBasicData.getPaymentmethod()!=null && orderBasicData.getPaymentmethod().equals("3")){
            binding.txPmethod.setText("Payment Method: bKash");
        }

        binding.txDate.setText(getDate(orderBasicData.getCreatedAt()));
        binding.txTime.setText(getTime(orderBasicData.getCreatedAt()));


    }


    public String getDate(String dateString) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateString);

            SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMM, yyyy");
            return fmtOut.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }

    }

    public String getTime(String dateString) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateString);

            SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm a");
            return fmtOut.format(date).toUpperCase();
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tx_view_details:
                viewDetailsOfTheProduct();
                break;
        }
    }
}
