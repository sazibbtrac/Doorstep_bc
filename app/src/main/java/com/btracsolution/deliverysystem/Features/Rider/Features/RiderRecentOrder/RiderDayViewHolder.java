package com.btracsolution.deliverysystem.Features.Rider.Features.RiderRecentOrder;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.RowAgentFoodListBinding;
import com.btracsolution.deliverysystem.databinding.RowRiderMyDayItemBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class RiderDayViewHolder extends MainViewHolder implements View.OnClickListener {

    //row_rider_my_day_item
    private RowRiderMyDayItemBinding binding;

    public BaseItemClickListener baseItemClickListener;
    /*@BindView(R.id.tx_name_customer)
    TextView tx_name_customer;

    @BindView(R.id.tx_pmethod)
    TextView tx_pmethod;


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

*/
    int current_position;

    public RiderDayViewHolder(View itemView) {
        super(itemView);
        binding = RowRiderMyDayItemBinding.bind(itemView);
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

    //@OnClick(R.id.tx_view_details)
    public void viewDetailsOfTheProduct() {
        baseItemClickListener.onClickOfListItem(true, current_position, "from-report");
    }

    public void setDataIntoView(Context context, OrderDetailsModel.orderBasicData orderBasicData, BaseItemClickListener baseItemClickListener, int current_position) {
        this.baseItemClickListener = baseItemClickListener;
        this.current_position = current_position;
        binding.txNameCustomer.setText(orderBasicData.getMember().getName());
        binding.txBdtAmount.setText(context.getString(R.string.bdt) + " " + orderBasicData.getTotalamount());
        binding.txDeliveryAddress.setText(fromHtml(orderBasicData.getShippingaddress()));

        if (orderBasicData.getPaymentmethod()!=null && orderBasicData.getPaymentmethod().equals("1")){
            binding.txPmethod.setText("Payment Method: COD");
        }else if(orderBasicData.getPaymentmethod()!=null && orderBasicData.getPaymentmethod().equals("2")){
            binding.txPmethod.setText("Payment Method: CARD");
        }else if(orderBasicData.getPaymentmethod()!=null && orderBasicData.getPaymentmethod().equals("3")){
            binding.txPmethod.setText("Payment Method: bKash");
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
