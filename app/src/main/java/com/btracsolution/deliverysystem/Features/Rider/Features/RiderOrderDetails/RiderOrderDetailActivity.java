package com.btracsolution.deliverysystem.Features.Rider.Features.RiderOrderDetails;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.CustomAlertDialog;
import com.btracsolution.deliverysystem.Utility.DialogClickInterface;
import com.btracsolution.deliverysystem.Utility.RiderGlobal;
import com.btracsolution.deliverysystem.Utility.Utils;
import com.btracsolution.deliverysystem.databinding.ActivityOrderDetailsRiderBinding;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by mahmudul.hasan on 1/4/2018.
 */

public class RiderOrderDetailActivity extends BaseActivity<ActivityOrderDetailsRiderBinding> implements View.OnClickListener,RiderJobStatusChangeStatus, DialogClickInterface {

    //activity_order_details_rider
   /* @BindView(R.id.tx_order_number)
    TextView tx_order_number;
    @BindView(R.id.tx_order_status)
    TextView tx_order_status;
    @BindView(R.id.tx_time_elaped)
    TextView tx_time_elaped;
    @BindView(R.id.tx_user_call_now)
    TextView tx_user_call_now;
    @BindView(R.id.rel_call_now)
    RelativeLayout rel_call_now;
    @BindView(R.id.lin_email)
    LinearLayout lin_email;
    @BindView(R.id.tx_user_email)
    TextView tx_user_email;
    @BindView(R.id.tx_user_phone)
    TextView tx_user_phone;
    @BindView(R.id.tx_user_address)
    TextView tx_user_address;
    @BindView(R.id.tx_user_name)
    TextView tx_user_name;
    @BindView(R.id.tx_pmethod)
    TextView tx_pmethod;
    @BindView(R.id.tx_subtotal)
    TextView tx_subtotal;
    @BindView(R.id.tx_shipping_price)
    TextView tx_shipping_price;
    @BindView(R.id.tx_grand_total)
    TextView tx_grand_total;
    @BindView(R.id.tx_accept)
    TextView tx_accept;
    @BindView(R.id.tx_decline)
    TextView tx_decline;
    @BindView(R.id.tx_navigate_address)
    TextView tx_navigate_address;
*/
   /* @BindView(R.id.recycler_view_inside_detaila)
    RecyclerView recycler_view_inside_detaila;


    @BindView(R.id.tx_ready)
    TextView tx_ready;


    @BindView(R.id.rel_navigate)
    RelativeLayout rel_navigate;


    @BindView(R.id.lin_two_id)
    LinearLayout lin_two_id;

*/
    OrderDetailsModel.orderBasicData orderBasicData;
    BaseItemClickListener baseItemClickListener = new BaseItemClickListener() {
        @Override
        public void onClickOfListItem(boolean isClick, int position, String extra) {

        }
    };
    private RiderPresenter riderPresenter;

    public static void open(Activity context, OrderDetailsModel.orderBasicData orderBasicData,String from,String orderid) {
        Intent intent = new Intent(context, RiderOrderDetailActivity.class);
        intent.putExtra("order_date", new Gson().toJson(orderBasicData));
        intent.putExtra("from", from);
        intent.putExtra("orderid", orderid);
        context.startActivityForResult(intent, 1);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

 //   @OnClick(R.id.rel_navigate)
    public void navigateToMap() {
        if (orderBasicData != null)
            location(
                    orderBasicData.getShippingaddress()
            );
    }

    //  @OnClick(R.id.tx_ready)
    public void onReadyToPickup() {

        switch (binding.txReady.getTag().toString()) {
            case "PICKUP":


                CustomAlertDialog.getInstance().showConfirmDialog(getString(R.string.confirm_pickup), getString(R.string.confirm_pickup_msg), getString(R.string.confirm), getString(R.string.close), this, 1);

                break;
            case "INPANTRY":
                CustomAlertDialog.getInstance().showConfirmDialog(getString(R.string.confirm_pantry), getString(R.string.confirm_pantry_msg), getString(R.string.confirm), getString(R.string.close), this, 2);

                break;
            case "ACCEPT":
                CustomAlertDialog.getInstance().showConfirmDialog(getString(R.string.confirm_accept), getString(R.string.confirm_accept_msg), getString(R.string.confirm), getString(R.string.close), this, 3);

                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12) {
            if (resultCode == Activity.RESULT_OK) {
                String order_basic_data = data.getStringExtra("order_basic_data");
                if (order_basic_data != null) {
                    orderBasicData = new Gson().fromJson(order_basic_data, OrderDetailsModel.orderBasicData.class);
                    setDataIntoView(orderBasicData);

                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    /*
    @OnClick(R.id.tx_accept)
    public void onClickAccept() {
        if (riderPresenter != null) {
            riderPresenter.acceptOrder(orderBasicData.getOrderid(), "accept");
        }

    }

    @OnClick(R.id.tx_decline)
    public void onClickDecline() {
        if (riderPresenter != null) {
            riderPresenter.delivered(orderBasicData.getOrderid(), "decline");
        }
    }*/

    public void setupRecycleView() {
        LinearLayoutManager llm = new LinearLayoutManager(RiderOrderDetailActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rowMyJobTwo.recyclerViewInsideDetaila.setLayoutManager(llm);
    }

   // @OnClick(R.id.tx_user_call_now)
    public void callPhone() {
        if (orderBasicData != null)
            callAction(orderBasicData.getWaiter().getContactno());
    }

   // @OnClick(R.id.tx_user_email)
    public void email() {
        if (orderBasicData != null)
            emailAction(orderBasicData.getWaiter().getContactno(), orderBasicData.getOrdernumber());
    }

    public void callAction(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));

        startActivity(intent);
    }

    public void emailAction(String emailAddress, String orderNumber) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", emailAddress, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order Query - Order Number: " + orderNumber);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Explain briefly");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public void setDataFromIntent() {
        try {
            if (getIntent() != null && getIntent().getStringExtra("from").contentEquals("joblist") ) {
                orderBasicData = new Gson().fromJson(getIntent().getStringExtra("order_date"), OrderDetailsModel.orderBasicData.class);
                setDataIntoView(orderBasicData);
            } else if (getIntent() != null && getIntent().getStringExtra("from").contentEquals("push")) {
                String orderid=getIntent().getStringExtra("orderid");
                riderPresenter.getSingleOrder(orderid);
            } else {
                finish();
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    public void setDataIntoView(OrderDetailsModel.orderBasicData orderBasicData) {
        binding.rowAgentAboveJobDetails.txOrderStatus.setText(getString(R.string.order_status_accepted) + " " + getOrderStatus(this, orderBasicData.getOrderstatus()));
        binding.rowAgentAboveJobDetails.txOrderNumber.setText(getString(R.string.order_number) + " " + orderBasicData.getOrdernumber());
        setTitle(getOrderStatus(this, orderBasicData.getOrdernumber()));
        binding.rowAgentAboveJobDetails.txOrderStatus.setText(getString(R.string.order_status_accepted) + " " + getOrderStatus(this, orderBasicData.getOrderstatus()));
        binding.rowAgentAboveJobDetails.txTimeElaped.setText(getString(R.string.time_elepted_30_min) + " " + getElapsedTime(orderBasicData.getCreatedAt()));
        binding.rowMyJobOne.txUserName.setText(orderBasicData.getWaiter().getFullName());
        if (orderBasicData.getDeliveryzone() !=null && orderBasicData.getTableno()!=null) {

            binding.rowMyJobOne.txUserAddress.setText(fromHtml(orderBasicData.getDeliveryzone().getZonename() + ", " + orderBasicData.getTableno().getTablename()));
        }
//        if (orderBasicData.getPaymentmethod()!=null && orderBasicData.getPaymentmethod().equals("1")){
//            tx_pmethod.setText("Payment Method: COD");
//        }else if(orderBasicData.getPaymentmethod()!=null && orderBasicData.getPaymentmethod().equals("2")){
//            tx_pmethod.setText("Payment Method: CARD");
//        }else if(orderBasicData.getPaymentmethod()!=null && orderBasicData.getPaymentmethod().equals("3")){
//            tx_pmethod.setText("Payment Method: bKash");
//        }
        binding.rowMyJobOne.txPmethod.setText(Utils.getDate(orderBasicData.getCreatedAt())+" "+Utils.getTime(orderBasicData.getCreatedAt()));

        //tx_navigate_address.setText(fromHtml(orderBasicData.getShippingaddress()));
        binding.rowMyJobOne.txUserPhone.setText(orderBasicData.getWaiter().getContactno());
        try {
            if (orderBasicData.getWaiter().getContactno() == null || orderBasicData.getWaiter().getContactno().length() == 0) {
                binding.rowMyJobOne.linEmail.setVisibility(View.GONE);
            } else
                binding.rowMyJobOne.txUserEmail.setText(orderBasicData.getWaiter().getContactno());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            binding.rowMyJobTwo.rowInsideJobTwoPricing.txSubtotal.setText(getString(R.string.tk_sign) + " " + getFormatedString(orderBasicData.getAmount()));
            binding.rowMyJobTwo.rowInsideJobTwoPricing.txShippingPrice.setText(getString(R.string.tk_sign) + " " + getFormatedString(orderBasicData.getDeliverycharge()));
            binding.rowMyJobTwo.rowInsideJobTwoPricing.txGrandTotal.setText(getString(R.string.tk_sign) + " " + getFormatedString(orderBasicData.getTotalamount()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        chanageButtonBasedOnStatus();
    }

    public String getFormatedString(String number) {
        try {
            double amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat("#,###.00");

            System.out.println(formatter.format(amount));
            return formatter.format(amount);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return number;
        }
    }

    public void chanageButtonBasedOnStatus() {
        binding.rowAgentAboveJobDetails.txOrderStatus.setText(getString(R.string.order_status_accepted) + " " + getOrderStatus(RiderOrderDetailActivity.this, orderBasicData.getOrderstatus()));
        switch (orderBasicData.getOrderstatus()) {
            case "0":
                binding.linTwoId.setVisibility(View.VISIBLE);
                break;
            case "1":
                binding.linTwoId.setVisibility(View.VISIBLE);
                break;
            case "2":
                binding.linTwoId.setVisibility(View.GONE);
                break;
            case "3":
                switch (orderBasicData.getDeliverystatus()) {
                    case "0":
                        binding.txReady.setText(getString(R.string.rider_accept));
                        binding.txReady.setTag("ACCEPT");
                        break;
                    case "1":
                        binding.txReady.setText(getString(R.string.rider_pickup));
                        binding.txReady.setTag("PICKUP");
                        break;
                    case "3":
                        binding.txReady.setText(getString(R.string.rider_pickup));
                        binding.txReady.setTag("PICKUP");
                        break;
                }


                binding.linTwoId.setVisibility(View.VISIBLE);
                break;
            case "4":
                binding.txReady.setText(getString(R.string.inpantry));
                binding.txReady.setTag("INPANTRY");
                binding.linTwoId.setVisibility(View.VISIBLE);
                break;

            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "10":
            case "11":
                binding.linTwoId.setVisibility(View.GONE);
                break;
            case "12":
                binding.linTwoId.setVisibility(View.GONE);
                break;
            case "13":
                switch (orderBasicData.getDeliverystatus()) {
                    case "0":
                        binding.txReady.setText(getString(R.string.rider_accept));
                        binding.txReady.setTag("ACCEPT");
                        break;
                    case "1":
                        binding.txReady.setText(getString(R.string.rider_pickup));
                        binding.txReady.setTag("PICKUP");
                        break;
                    case "7":
                        binding.txReady.setText(getString(R.string.rider_pickup));
                        binding.txReady.setTag("PICKUP");
                        break;
                }


                binding.linTwoId.setVisibility(View.VISIBLE);                break;
            case "14":
                binding.txReady.setText(getString(R.string.inpantry));
                binding.txReady.setTag("INPANTRY");
                binding.linTwoId.setVisibility(View.VISIBLE);
                break;
        }
    }


    public void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public String getElapsedTime(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt = formatter.parse(dateString);
            CharSequence output = DateUtils.getRelativeTimeSpanString(dt.getTime());
            return output.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public String getOrderStatus(Context context, String status) {

        switch (orderBasicData.getOrderstatus()) {
            case "0": // Pending
                return context.getString(R.string.Pending);

            case "1": // Ordered
                return context.getString(R.string.Ordered);

            case "2": // Processing
                return context.getString(R.string.Processing);

            case "3": // Ready for Deliver
                return context.getString(R.string.Deliver);

            case "4": // On Way
                return context.getString(R.string.way);

            case "5": // Delivered
                return context.getString(R.string.Delivered);

            case "6": // Canceled by Customer
                return context.getString(R.string.returned);

            case "7": // Canceled by Customer
                return context.getString(R.string.damaged);

            case "8": // Canceled by Call Center Agent
                return context.getString(R.string.completed_job);

            case "9": // Canceled by Branch Agent
                return context.getString(R.string.cancel_customer);

            case "10": // Canceled by Branch Agent
                return context.getString(R.string.cancel_call_agent);

            case "11": // Canceled by Branch Agent
                return context.getString(R.string.cancel_by_agent);

            case "12": // Canceled by Branch Agent
                return context.getString(R.string.inpantry);
            case "13": // Canceled by Branch Agent
                return context.getString(R.string.p_ready_to_pickup);
            case "14": // Canceled by Branch Agent
                return context.getString(R.string.p_on_way);


        }
        return status;
    }
/*
    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_details_rider;
    }*/

    @Override
    public void initView() {
        super.initView();
        initializeToolbar();
        riderPresenter = new RiderPresenter();
        riderPresenter.attachView(RiderOrderDetailActivity.this, this);
        setDataFromIntent();
        setupRecycleView();

        setAdapterDataIntoFoodView();
        setOnclickListener();



    }

    private void setOnclickListener() {
        binding.rowMyJobThree.relNavigate.setOnClickListener(this);
        binding.txReady.setOnClickListener(this);
        binding.rowMyJobOne.txUserCallNow.setOnClickListener(this);
        binding.rowMyJobOne.txUserEmail.setOnClickListener(this);
    }

    @Override
    protected ActivityOrderDetailsRiderBinding getViewBinding() {
        return ActivityOrderDetailsRiderBinding.inflate(getLayoutInflater());
    }

    public void setAdapterDataIntoFoodView() {
        if (orderBasicData != null) {
            RiderRowInsideDetailAdapter agentRowInsideDetailAdapter = new RiderRowInsideDetailAdapter(RiderOrderDetailActivity.this, orderBasicData.getOrderitem(), baseItemClickListener);
            setAdapterIntoRecyclView(agentRowInsideDetailAdapter);
        }

    }

    public void setAdapterIntoRecyclView(RiderRowInsideDetailAdapter agentJobListAdapter) {
        binding.rowMyJobTwo.recyclerViewInsideDetaila.setAdapter(agentJobListAdapter);
        binding.rowMyJobTwo.recyclerViewInsideDetaila.setNestedScrollingEnabled(false);
    }

    private void initializeToolbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(RiderOrderDetailActivity.this, R.color.colorAccent));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public void onJobStatusChanged(String currentstatus, String deliveryStatus) {

        RiderGlobal.isComesFromDetails = true;
        orderBasicData.setOrderstatus(currentstatus);
        orderBasicData.setDeliverystatus(deliveryStatus);
        setDataIntoView(orderBasicData);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        RiderGlobal.isComesFromDetails = true;
        RiderGlobal.CurrentStatus = orderBasicData.getOrderstatus();
        finish();
    }

    public void location(String address) {
        String map = "http://maps.google.co.in/maps?q=" + address;
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
        startActivity(i);
    }

    @Override
    public void onClickPositiveButton(DialogInterface pDialog, int pDialogIntefier,String shortNote) {

        switch (pDialogIntefier) {
            case 1:
                if (riderPresenter != null) {
                    riderPresenter.pickUP(orderBasicData.getOrderid(), "pickup");
                }
                break;
            case 2:
                if (riderPresenter != null) {
                    //riderPresenter.delivered(orderBasicData.getOrderid(), "delivered");
                    riderPresenter.delivered(orderBasicData.getOrderid(), "inffpantry");
                }
                break;
            case 3:
                if (riderPresenter != null) {
                    riderPresenter.acceptOrder(orderBasicData.getOrderid(), "accept");
                }
                break;
        }
        if (pDialog != null) {
            pDialog.dismiss();
        }

    }

    @Override
    public void onClickNegativeButton(DialogInterface pDialog, int pDialogIntefier) {

        if (pDialog != null) {
            pDialog.dismiss();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rel_navigate:
                navigateToMap();
                break;

            case R.id.tx_ready:
                onReadyToPickup();
                break;

            case R.id.tx_user_call_now:
                callPhone();
                break;

            case R.id.tx_user_email:
                email();
                break;

        }
    }
}
