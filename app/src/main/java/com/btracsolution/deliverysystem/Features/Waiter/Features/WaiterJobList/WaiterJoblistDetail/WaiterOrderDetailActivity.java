package com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJoblistDetail;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Features.Rider.Features.RiderOrderDetails.RiderOrderDetailActivity;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.AgentGlobal;
import com.btracsolution.deliverysystem.Utility.CustomAlertDialog;
import com.btracsolution.deliverysystem.Utility.DialogClickInterface;
import com.btracsolution.deliverysystem.Utility.MemberUpdateDialog;
import com.btracsolution.deliverysystem.Utility.Utils;
import com.btracsolution.deliverysystem.databinding.ActivityOrderDetailsAgentBinding;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
import butterknife.BindView;
import butterknife.OnClick;*/
import de.hdodenhof.circleimageview.CircleImageView;

public class WaiterOrderDetailActivity extends BaseActivity<ActivityOrderDetailsAgentBinding> implements View.OnClickListener, JobStatusChangeStatusWaiter, DialogClickInterface {

    /*@BindView(R.id.tx_order_number)
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
    @BindView(R.id.tx_order_from)
    TextView tx_order_from;
    @BindView(R.id.tx_pmethod)
    TextView tx_pmethod;
    @BindView(R.id.tx_subtotal)
    TextView tx_subtotal;
    @BindView(R.id.tx_shipping_price)
    TextView tx_shipping_price;
    @BindView(R.id.tx_grand_total)
    TextView tx_grand_total;
    @BindView(R.id.tx_special_instruction)
    TextView tx_special_instruction;
    @BindView(R.id.tx_accept)
    TextView tx_accept;
    @BindView(R.id.tx_decline)
    TextView tx_decline;

    @BindView(R.id.recycler_view_inside_detaila)
    RecyclerView recycler_view_inside_detaila;


    @BindView(R.id.tx_ready)
    TextView tx_ready;


    @BindView(R.id.card_rider_assigned)
    CardView card_rider_assigned;


    @BindView(R.id.lin_agent_job_ready)
    LinearLayout lin_agent_job_ready;


    @BindView(R.id.lin_agent_job_three)
    LinearLayout lin_agent_job_three;


    @BindView(R.id.profile_image)
    CircleImageView profile_image;


    @BindView(R.id.rel_call_button)
    RelativeLayout rel_call_button;

    @BindView(R.id.tx_rider_name)
    TextView tx_rider_name;

    @BindView(R.id.tx_branch_name)
    TextView tx_branch_name;

    @BindView(R.id.lin_inside_top)
    LinearLayout lin_inside_top;

    @BindView(R.id.tvEditMember)
    TextView tvEditMember;
*/
    OrderDetailsModel.orderBasicData orderBasicData;
    BaseItemClickListener baseItemClickListener = new BaseItemClickListener() {
        @Override
        public void onClickOfListItem(boolean isClick, int position, String extra) {

        }
    };
    private BroadcastReceiver receiver;
    private WaiterJobDetailPresenter waiterJobDetailPresenter;
    private boolean isFirstTimeInThisActivity = true;
    String acceptType;

    public void updateUserName(String name, String memberId) {
       // bindin.setText(name + "(" + memberId + ")");
        binding.rowAgentMyJobOne.txUserName.setText(name + "(" + memberId + ")");
    }

    public static void open(Activity context, OrderDetailsModel.orderBasicData orderBasicData,String from,String orderid,String acceptType) {
        Intent intent = new Intent(context, WaiterOrderDetailActivity.class);
        intent.putExtra("order_date", new Gson().toJson(orderBasicData));
        intent.putExtra("from", from);
        intent.putExtra("orderid", orderid);
        intent.putExtra("acceptType", acceptType);
        context.startActivityForResult(intent, 1);
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

   // @OnClick(R.id.tx_ready)
    public void onReadyToPickup() {

        if (binding.txReady.getTag() != null) {
            switch (binding.txReady.getTag().toString()) {
                case "RIDER":
                    // AgentSelectRider.open(WaiterOrderDetailActivity.this, orderBasicData.getOrderid(), getString(R.string.select_rider), "RIDER");
                    break;
                case "CHANGE":
                    //AgentSelectRider.open(WaiterOrderDetailActivity.this, orderBasicData.getOrderid(), getString(R.string.change_rider), "CHANGE");

                    break;
                case "READY":
                    showToast("Food is processing in kitchen");
                    break;
                case "INPANTRY":
                    if (waiterJobDetailPresenter != null) {
                        waiterJobDetailPresenter.deliveredToMember(orderBasicData.getOrderid(), "Delivered", orderBasicData.getKitchenid());
                    }

                    break;
            }

        } else
            showAlert(getString(R.string.error_into_server));


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
                    finish();

                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 13) {
            if (resultCode == Activity.RESULT_OK) {
                finish();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


   // @OnClick(R.id.tx_accept)
    public void onClickAccept() {
        if (waiterJobDetailPresenter != null) {
            // waiterJobDetailPresenter.acceptOrder(orderBasicData.getOrderid(), "accept");
        }

    }

  //  @OnClick(R.id.tx_decline)
    public void onClickDecline() {
        if (waiterJobDetailPresenter != null) {
            //agentPresenter.declineOrder(orderBasicData.getOrderid(), "decline");
            // CustomAlertDialog.getInstance().showConfirmDialog("CONFIRM REJECT?", "Are you sure want to reject the request?", "Reject", "Close", this, 4, this);

        }
    }
  //  @OnClick(R.id.tvEditMember)
    public void onClickEditMember() {
        try {
            MemberUpdateDialog.getInstance().showMemberEditDialog("MEMBER UPDATE", "Update member for this order",
                    "Update", "Cancel", this, 5, this,waiterJobDetailPresenter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setupRecycleView() {
        LinearLayoutManager llm = new LinearLayoutManager(WaiterOrderDetailActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rowAgentMyJobTwo.recyclerViewInsideDetaila.setLayoutManager(llm);
    }

   // @OnClick(R.id.tx_user_call_now)
    public void callPhone() {
        if (orderBasicData != null)
            callAction(orderBasicData.getMember().getContactno());
    }

 //   @OnClick(R.id.tx_user_email)
    public void email() {
        if (orderBasicData != null)
            emailAction(orderBasicData.getMember().getContactno(), orderBasicData.getOrdernumber());
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
                acceptType=getIntent().getStringExtra("acceptType");
                waiterJobDetailPresenter.getSingleOrder(orderid);
            } else if (getIntent() != null && getIntent().getStringExtra("from").contentEquals("bill") ) {
                orderBasicData = new Gson().fromJson(getIntent().getStringExtra("order_date"), OrderDetailsModel.orderBasicData.class);
                setDataIntoView(orderBasicData);
                binding.rowAgentMyJobOne.tvEditMember.setVisibility(View.GONE);
            }else {
                finish();
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    public void setDataIntoView(OrderDetailsModel.orderBasicData orderBasicData) {
        binding.rowAgentMyJobOne.tvEditMember.setVisibility(View.VISIBLE);
        binding.rowAgentAboveJobDetails.txOrderStatus.setText(getString(R.string.order_status_accepted) + " " + getOrderStatus(this, orderBasicData.getOrderstatus()));
        binding.rowAgentAboveJobDetails.txOrderNumber.setText(getString(R.string.order_number) + " " + orderBasicData.getOrdernumber());
        setTitle(getOrderStatus(this, orderBasicData.getOrdernumber()));
        binding.rowAgentAboveJobDetails.txOrderStatus.setText(getString(R.string.order_status_accepted) + " " + getOrderStatus(this, orderBasicData.getOrderstatus()));
        binding.rowAgentAboveJobDetails.txTimeElaped.setText(getString(R.string.time_elepted_30_min) + " " + getElapsedTime(orderBasicData.getCreatedAt()));
        binding.rowAgentMyJobOne.txUserName.setText(orderBasicData.getMember().getName() + "(" + orderBasicData.getMember().getMemberId() + ")");
        if (orderBasicData.getOrderfrom() != null && orderBasicData.getOrderfrom().equals("1")) {
            binding.rowAgentMyJobOne.txOrderFrom.setText("From Microsite");
        } else if (orderBasicData.getOrderfrom() != null && orderBasicData.getOrderfrom().equals("2")) {
            binding.rowAgentMyJobOne.txOrderFrom.setText("From Doorstep");
        } else if (orderBasicData.getOrderfrom() != null && orderBasicData.getOrderfrom().equals("3")) {
            binding.rowAgentMyJobOne.txOrderFrom.setText("From Website");
        }
        binding.rowAgentMyJobOne.txOrderFrom.setText(Utils.getDate(orderBasicData.getCreatedAt()) + " " + Utils.getTime(orderBasicData.getCreatedAt()));

//        if (orderBasicData.getPaymentmethod() != null && orderBasicData.getPaymentmethod().equals("1")) {
//            tx_pmethod.setText("Payment Method: COD");
//        } else if (orderBasicData.getPaymentmethod() != null && orderBasicData.getPaymentmethod().equals("2")) {
//            tx_pmethod.setText("Payment Method: CARD");
//        } else if (orderBasicData.getPaymentmethod() != null && orderBasicData.getPaymentmethod().equals("3")) {
//            tx_pmethod.setText("Payment Method: bKash");
//        }
        binding.rowAgentMyJobOne.txPmethod.setVisibility(View.GONE);
        if (orderBasicData.getDeliveryzone() != null && orderBasicData.getTableno() != null) {
            binding.rowAgentMyJobOne.txUserAddress.setText(fromHtml(orderBasicData.getDeliveryzone().getZonename() + ", " + orderBasicData.getTableno().getTablename()));
        }
        binding.rowAgentMyJobOne.txUserPhone.setText(orderBasicData.getMember().getContactno());
        try {
            if (orderBasicData.getMember().getContactno() == null || orderBasicData.getMember().getContactno().length() == 0) {
                binding.rowAgentMyJobOne.linEmail.setVisibility(View.GONE);
            } else
                binding.rowAgentMyJobOne.txUserEmail.setText(orderBasicData.getMember().getContactno());
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.rowAgentMyJobTwo.rowInsideJobTwoPricing.txSubtotal.setText(getString(R.string.tk_sign) + " " + getFormatedString(orderBasicData.getAmount()));
        try {
            binding.rowAgentMyJobTwo.rowInsideJobTwoPricing.txShippingPrice.setText(getString(R.string.tk_sign) + " " + getFormatedString(orderBasicData.getDeliverycharge()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.rowAgentMyJobTwo.rowInsideJobTwoPricing.txGrandTotal.setText(getString(R.string.tk_sign) + " " + getFormatedString(orderBasicData.getTotalamount()));
        System.out.println("order " + orderBasicData);
        setRemarks(orderBasicData);
//        try {
//            if (orderBasicData.getOrderitem().get(0).getItemRemarks() == null || orderBasicData.getOrderitem().get(0).getItemRemarks().length() == 0) {
//                tx_special_instruction.setVisibility(View.GONE);
//            } else
//                tx_special_instruction.setText("*Special Instruction : "+orderBasicData.getOrderitem().get(0).getItemRemarks());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        chanageButtonBasedOnStatus();
    }

    private void setRemarks(OrderDetailsModel.orderBasicData orderBasicData) {
        try {
            String remarks = "Remarks: ";
            int count = 0;
            for (int i = 0; i < orderBasicData.getOrderitem().size(); i++) {
                if (orderBasicData.getOrderitem().get(i).getItemRemarks() == null || orderBasicData.getOrderitem().get(i).getItemRemarks().length() == 0) {
                    // tx_special_instruction.setVisibility(View.GONE);
                } else {
                    remarks = remarks + orderBasicData.getOrderitem().get(i).getFoodinfo().getFoodname() + "-" + orderBasicData.getOrderitem().get(i).getItemRemarks() + "\n";
                    count++;
                }
            }

            if (count > 0) {
                binding.rowAgentMyJobTwo.rowInsideJobTwoPricing.txSpecialInstruction.setText(remarks);
            } else {
                binding.rowAgentMyJobTwo.rowInsideJobTwoPricing.txSpecialInstruction.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        binding.rowAgentAboveJobDetails.txOrderStatus.setText(getString(R.string.order_status_accepted) + " " + getOrderStatus(WaiterOrderDetailActivity.this, orderBasicData.getOrderstatus()));
        System.out.println("called executed 1 " + orderBasicData.getOrderstatus());
        switch (orderBasicData.getOrderstatus()) {
            case "0":
//                lin_agent_job_ready.setVisibility(View.GONE);
//                lin_agent_job_three.setVisibility(View.VISIBLE);
                break;
            case "1":
//                lin_agent_job_ready.setVisibility(View.GONE);
//                lin_agent_job_three.setVisibility(View.VISIBLE);
                break;
            case "2":
//                lin_agent_job_ready.setVisibility(View.VISIBLE);
//                lin_agent_job_three.setVisibility(View.GONE);

                binding.txReady.setText(getString(R.string.Deliver));
                binding.txReady.setTag("READY");
                break;
            case "3":
//                if (orderBasicData.getRider() == null) {
//                    tx_ready.setText(getString(R.string.select_rider));
//                    tx_ready.setTag("RIDER");
//                    card_rider_assigned.setVisibility(View.GONE);
//
//                    if (!isFirstTimeInThisActivity) {
//                        tx_ready.performClick();
//                        isFirstTimeInThisActivity = false;
//                    } else {
//                        isFirstTimeInThisActivity = false;
//
//                    }
//
//                } else {
//                    tx_ready.setText(getString(R.string.change_rider));
//                    tx_ready.setTag("CHANGE");
//
//                    setRiderInformation(orderBasicData.getRider());
//                }
//
//                lin_agent_job_ready.setVisibility(View.VISIBLE);
//                lin_agent_job_three.setVisibility(View.GONE);
                break;
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "10":
            case "11":
                System.out.println("called executed " + orderBasicData.getOrderstatus());
                binding.linAgentJobReady.setVisibility(View.GONE);
                binding.linAgentJobThree.setVisibility(View.GONE);

                setRiderInformation(orderBasicData.getRider());


                break;
            case "12":
                System.out.println("called executed " + orderBasicData.getOrderstatus());
                binding.linAgentJobReady.setVisibility(View.VISIBLE);
                binding.linAgentJobThree.setVisibility(View.GONE);
                binding.txReady.setText("DELIVER TO MEMBER");
                binding.txReady.setTag("INPANTRY");
                break;
            case "15":
                System.out.println("called executed " + orderBasicData.getOrderstatus());
                binding.linAgentJobReady.setVisibility(View.VISIBLE);
                binding.linAgentJobThree.setVisibility(View.GONE);
                binding.txReady.setText("DELIVER TO MEMBER");
                binding.txReady.setTag("INPANTRY");
                break;
            case "16":
                break;
        }
        isFirstTimeInThisActivity = false;

    }

    public void setRiderInformation(OrderDetailsModel.Rider riderInformation) {
        try {
            if (riderInformation != null) {
                binding.rowAgentRiderAssigned.cardRiderAssigned.setVisibility(View.VISIBLE);
                binding.rowAgentRiderAssigned.txRiderName.setText(riderInformation.getFullname());
                binding.rowAgentRiderAssigned.txBranchName.setText(riderInformation.getBranchname());

                Glide.with(WaiterOrderDetailActivity.this)
                        .load(riderInformation.getProfileImage())
                        .centerCrop()
                        .placeholder(R.drawable.dr_logo)
                        .error(R.drawable.ic_face_black_36dp)
                        .into(binding.rowAgentRiderAssigned.profileImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  //  @OnClick(R.id.rel_call_button)
    public void clickOnCall() {
        if (orderBasicData.getRider() != null) {
            callAction(orderBasicData.getRider().getContactno());
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
            case "15": // Canceled by Branch Agent
                return context.getString(R.string.p_inpantry);
            case "16": // Canceled by Branch Agent
                return context.getString(R.string.p_delivered);


        }
        return status;
    }

/*    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_details_agent;
    }*/

    @Override
    public void initView() {
        super.initView();
        initializeToolbar();


        waiterJobDetailPresenter = new WaiterJobDetailPresenter();
        waiterJobDetailPresenter.attachView(WaiterOrderDetailActivity.this, this);

        setDataFromIntent();
        setupRecycleView();

        setAdapterDataIntoFoodView();
        setOnClickListener();


    }

    private void setOnClickListener() {

        binding.txReady.setOnClickListener(this);
        binding.txAccept.setOnClickListener(this);
        binding.txDecline.setOnClickListener(this);
        binding.rowAgentRiderAssigned.relCallButton.setOnClickListener(this);
        binding.rowAgentMyJobOne.txUserEmail.setOnClickListener(this);
        binding.rowAgentMyJobOne.txUserCallNow.setOnClickListener(this);
        binding.rowAgentMyJobOne.tvEditMember.setOnClickListener(this);

    }

    @Override
    protected ActivityOrderDetailsAgentBinding getViewBinding() {
        return ActivityOrderDetailsAgentBinding.inflate(getLayoutInflater());
    }

    public void setAdapterDataIntoFoodView() {
        if (orderBasicData != null) {
            WaiterRowInsideDetailAdapter waiterRowInsideDetailAdapter = new WaiterRowInsideDetailAdapter(WaiterOrderDetailActivity.this, orderBasicData.getOrderitem(), baseItemClickListener);
            setAdapterIntoRecyclView(waiterRowInsideDetailAdapter);
        }

    }

    public void setAdapterIntoRecyclView(WaiterRowInsideDetailAdapter waiterRowInsideDetailAdapter) {
        binding.rowAgentMyJobTwo.recyclerViewInsideDetaila.setAdapter(waiterRowInsideDetailAdapter);
        binding.rowAgentMyJobTwo.recyclerViewInsideDetaila.setNestedScrollingEnabled(false);
    }

    private void initializeToolbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(WaiterOrderDetailActivity.this, R.color.colorAccent));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public void onJobStatusChanged(String currentstatus) {

        AgentGlobal.isComesFromDetails = true;
        orderBasicData.setOrderstatus(currentstatus);
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
        AgentGlobal.isComesFromDetails = true;
        AgentGlobal.CurrentStatus = orderBasicData.getOrderstatus();
        finish();
    }


    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    public void registerReceiver() {
        System.out.println("called on received registerReceiver ");
        IntentFilter filter = new IntentFilter();
        filter.addAction("notification.details");


        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //do something based on the intent's action
                System.out.println("called on received");

                if (intent != null) {
                    String type = intent.getStringExtra("type");
                    String order_type = intent.getStringExtra("order_type");
                    if (type.equals("uiUpdate")) {
                        switch (order_type) {
                            case "order_picked_up":
                                orderBasicData.setOrderstatus("4");// Order pickedup status and updated automatically
                                setDataIntoView(orderBasicData);
                                break;
                            case "order_partially_picked_up":
                                orderBasicData.setOrderstatus("14");// Order pickedup status and updated automatically
                                setDataIntoView(orderBasicData);
                                break;
                            case "order_delivered":
                                orderBasicData.setOrderstatus("5"); // Order delivered status and updated automatically
                                setDataIntoView(orderBasicData);
                                break;
                            case "ready_to_pickup":
                                orderBasicData.setOrderstatus("3"); // Order delivered status and updated automatically
                                setDataIntoView(orderBasicData);
                                break;
                            case "cancelJob":
                                orderBasicData.setOrderstatus("10");
                                setDataIntoView(orderBasicData);
                                break;
                        }
                    }
                }


            }
        };
        try {
            registerReceiver(receiver, filter);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("called exceptoin " + e.toString());
        }
    }

    @Override
    public void onClickPositiveButton(DialogInterface pDialog, int pDialogIntefier, String shortNote) {

        if (pDialogIntefier == 5) {
            hideKeyboard();
            waiterJobDetailPresenter.updateMember(orderBasicData.getOrderid(),shortNote);
            pDialog.dismiss();
        } else {
            hideKeyboard();
            waiterJobDetailPresenter.declineOrder(orderBasicData.getOrderid(), shortNote);
            pDialog.dismiss();
        }


    }

    @Override
    public void onClickNegativeButton(DialogInterface pDialog, int pDialogIntefier) {
        hideKeyboard();
        pDialog.dismiss();

    }

    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tx_accept:
                onClickAccept();
                break;

            case R.id.tx_decline:
                onClickDecline();
                break;

            case R.id.tvEditMember:
                onClickEditMember();
                break;

            case R.id.tx_ready:
                onReadyToPickup();
                break;

            case R.id.tx_user_call_now:
                callPhone();
                break;

            case R.id.tx_user_email:
                email();

            case R.id.rel_call_button:
                clickOnCall();
                break;

        }
    }
}