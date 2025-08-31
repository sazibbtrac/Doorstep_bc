package com.btracsolution.deliverysystem.Features.Waiter.Features.BillHistory;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Bill.BillActivity;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Bill.BillAdapter;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Bill.BillAdapterWithHeader;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Bill.BillPresenter;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart.MemberSearchAdapter;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart.MyCartActivity;
import com.btracsolution.deliverysystem.Model.MemberListResponse;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.databinding.ActivityBillHistoryBinding;
import com.btracsolution.deliverysystem.databinding.FragmentBillBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;



public class BillHistoryActivity extends BaseActivity<ActivityBillHistoryBinding>{


//    private ActivityBillHistoryBinding binding;
    BillHistoryAdapter billHistoryAdapter;
   // BillHistoryAdapterWithHeader billAdapterWithHeader;
    public BillHistoryPresenter billHistoryPresenter;
    SharedData sharedData;
    ArrayList<MemberListResponse.MemberData> memberListResponses;


/*
    @BindView(R.id.ivCross)
    ImageView ivCross;
    @BindView(R.id.etSearch)
    AutoCompleteTextView etSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.rel_no_order)
    RelativeLayout rel_no_order;

    //Toolbar
    @BindView(R.id.tvCount)
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
*/

    MemberSearchAdapter memberSearchAdapter;
    String memberid;


    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    public BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;

    public ActivityBillHistoryBinding getBinding() {
        return binding;
    }

    public static void open(Context context) {
        Intent intent = new Intent(context, BillHistoryActivity.class);
   //     intent.putExtra("cart", cart);
//        intent.putExtra("position", position);
        context.startActivity(intent);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bill_history);
//    }

/*    @Override
    protected int getLayoutId() {
        return R.layout.activity_bill_history;
    }*/

    @Override
    public void initView() {
        super.initView();

        initializeToolbar();
        //ListPairedDevices();
        initializeView();

        setupRecycleView();
        //getIntentData();

    }

    @Override
    protected ActivityBillHistoryBinding getViewBinding() {
        return ActivityBillHistoryBinding.inflate(getLayoutInflater());
    }

    private void initializeView() {
        mBluetoothSocket= DeliveryApp.getInstance().getCurrentBluetoothConnection();
        try {
            if (mBluetoothSocket!=null && mBluetoothSocket.isConnected()){
                showToast("Printer Connected");
            }else{
                showToast("Printer not Connected");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sharedData = new SharedData(BillHistoryActivity.this);
        memberListResponses = new ArrayList<>();

        billHistoryPresenter = new BillHistoryPresenter(this);
        billHistoryPresenter.getAllMemberListFromServer(false);

        binding.etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etSearch.getText().clear();
                binding.etSearch.showDropDown();
            }
        });
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String memid = binding.etSearch.getText().toString();
                    if (memid != null && memid.length() >= 4) {
                        hideKeyboard();
                        billHistoryPresenter.getSingleMemberData(true, binding.etSearch.getText().toString());

                    }else{
                        showToast("Please enter valid member id");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }            }
        });

    }

    private void initializeToolbar() {
        binding.bar.tvTitle.setText("Bill History");
        binding.bar.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(BillHistoryActivity.this, R.color.colorAccent));
        }

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        }
    }

    public void yesOrderFound() {
        binding.relNoOrder.setVisibility(View.GONE);
        //pull_to_refresh.setRefreshing(false);
    }

    public void noOrderFound() {
        binding.relNoOrder.setVisibility(View.VISIBLE);
        // pull_to_refresh.setRefreshing(false);


    }

    public void setupRecycleView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(llm);
    }
    public void setAdapterIntoRecyclView(BillHistoryAdapter billHistoryAdapter) {
        this.billHistoryAdapter = billHistoryAdapter;
        binding.recyclerView.setAdapter(billHistoryAdapter);
    }
    public void setupMemberAdapter(){

        try {

            memberSearchAdapter = new MemberSearchAdapter(this, R.layout.autocompletetextview, memberListResponses);
            binding.etSearch.setAdapter(memberSearchAdapter);
            //etMobile.showDropDown();
            memberSearchAdapter.setListener(new MemberSearchAdapter.ItemClickListener() {
                @Override
                public void onItemClcik(MemberListResponse.MemberData memberData) {
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    //tvMemName.setText(memberData.getMemberName());
                    binding.etSearch.setText(memberData.getMemberId());
                    binding.etSearch.dismissDropDown();
                    binding.etSearch.clearListSelection();
                    binding.etSearch.clearFocus();
                    //   Utils.hideKeyboard(MyCartActivity.this);

                   // billHistoryPresenter.getServerData(true,etSearch.getText().toString());
                    memberid=memberData.getMemberId();
                    billHistoryPresenter.getBillHistoryFromServer(true,memberid);


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}