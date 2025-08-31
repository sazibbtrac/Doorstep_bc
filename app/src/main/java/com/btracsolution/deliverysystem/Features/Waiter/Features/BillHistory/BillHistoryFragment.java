package com.btracsolution.deliverysystem.Features.Waiter.Features.BillHistory;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btracsolution.deliverysystem.Features.Waiter.Features.Bill.BillActivity;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart.MemberSearchAdapter;
import com.btracsolution.deliverysystem.Model.MemberListResponse;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.databinding.ActivityBillHistoryBinding;
import com.btracsolution.deliverysystem.databinding.ActivityDaydetailsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;




public class BillHistoryFragment extends Fragment implements Runnable{

    //activity_bill_history
    private ActivityBillHistoryBinding binding;
    private static final String TAG = "BillHistoryActivity";
    BillHistoryAdapter billHistoryAdapter;
    // BillHistoryAdapterWithHeader billAdapterWithHeader;
    public BillHistoryPresenter billHistoryPresenter;
    SharedData sharedData;
    ArrayList<MemberListResponse.MemberData> memberListResponses;

    /*@BindView(R.id.ivCross)
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

*/    MemberSearchAdapter memberSearchAdapter;
    String memberid;


    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    public BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;

    View v;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
/*        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.activity_bill_history, container, false);
        ButterKnife.bind(this, v);
        return  v;*/

        binding = ActivityBillHistoryBinding.inflate(inflater, container, false);
        //  topBinding = RowProfileTopBinding.bind(binding.rowProfileTop.getRoot());
        //  bottomBinding = RowProfileBottomBinding.bind(binding.rowProfileBottom.getRoot());
        setHasOptionsMenu(true);
        setOnclickListener();
        return binding.getRoot();
    }

    private void setOnclickListener() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initializeToolbar();
//        setupRecycleView();
//        ListPairedDevices();
//        initializeView();
    }

    @Override
    public void run() {

    }
//    private void initializeView() {
//        sharedData = new SharedData(this.getContext());
//        memberListResponses = new ArrayList<>();
//
//        billHistoryPresenter = new BillHistoryPresenter(this);
//        billHistoryPresenter.getAllMemberListFromServer(false);
//
//        etSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                etSearch.getText().clear();
//                etSearch.showDropDown();
//            }
//        });
//        etSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        ivCross.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //billPresenter.getServerData(true,etSearch.getText().toString());
//            }
//        });
//
//    }
//    private void initializeToolbar() {
//        tvTitle.setText("Bill History");
//        llBack.setVisibility(View.GONE);
//        llBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
//
//    }
//
//    public void yesOrderFound() {
//        rel_no_order.setVisibility(View.GONE);
//        //pull_to_refresh.setRefreshing(false);
//    }
//
//    public void noOrderFound() {
//        rel_no_order.setVisibility(View.VISIBLE);
//        // pull_to_refresh.setRefreshing(false);
//
//
//    }
//
//    public void setupRecycleView() {
//        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        recycler_view.setLayoutManager(llm);
//    }
//    public void setAdapterIntoRecyclView(BillHistoryAdapter billHistoryAdapter) {
//        this.billHistoryAdapter = billHistoryAdapter;
//        recycler_view.setAdapter(billHistoryAdapter);
//    }
//    public void setupMemberAdapter(){
//
//        memberSearchAdapter = new MemberSearchAdapter(this.getContext(), R.layout.autocompletetextview, memberListResponses);
//        etSearch.setAdapter(memberSearchAdapter);
//        //etMobile.showDropDown();
//        memberSearchAdapter.setListener(new MemberSearchAdapter.ItemClickListener() {
//            @Override
//            public void onItemClcik(MemberListResponse.MemberData memberData) {
//                View view = getActivity().getCurrentFocus();
//                if (view != null) {
//                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                }
//                //tvMemName.setText(memberData.getMemberName());
//                etSearch.setText(memberData.getMemberId());
//                etSearch.dismissDropDown();
//                etSearch.clearListSelection();
//                etSearch.clearFocus();
//                //   Utils.hideKeyboard(MyCartActivity.this);
//
//                // billHistoryPresenter.getServerData(true,etSearch.getText().toString());
//                memberid=memberData.getMemberId();
//                billHistoryPresenter.getBillHistoryFromServer(true,memberid);
//
//
//            }
//        });
//    }
//
//    //BluetoothPrinter Connection
//    @SuppressLint("MissingPermission")
//    private void ListPairedDevices() {
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
//                .getBondedDevices();
//        if (mPairedDevices.size() > 0) {
//            for (BluetoothDevice mDevice : mPairedDevices) {
//                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
//                        + mDevice.getAddress());
//                if (mDevice.getName().contentEquals("InnerPrinter") || mDevice.getName().contentEquals("V2")) {
//                    String mDeviceAddress = mDevice.getAddress();
//                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
//                    mBluetoothDevice = mBluetoothAdapter
//                            .getRemoteDevice(mDeviceAddress);
//
//                    Thread mBlutoothConnectThread = new Thread(BillHistoryFragment.this);
//                    mBlutoothConnectThread.start();
//                }
//            }
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    @Override
//    public void run() {
//        try {
//            mBluetoothSocket = mBluetoothDevice
//                    .createRfcommSocketToServiceRecord(applicationUUID);
//            mBluetoothAdapter.cancelDiscovery();
//            mBluetoothSocket.connect();
//        } catch (IOException eConnectException) {
//            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
//            closeSocket(mBluetoothSocket);
//            return;
//        }finally {
//            Looper.prepare();// to be able to make toast
//            if (mBluetoothSocket.isConnected()) {
//                showToast("Printer connected");
//            }else{
//                showToast("Not connected");
//            }
//            Looper.loop();
//        }
//    }
//    public void showToast(String message) {
//        if (getActivity() != null && !getActivity().isFinishing())
//            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//    }
//    private void closeSocket(BluetoothSocket nOpenSocket) {
//        try {
//            if (nOpenSocket!=null) {
//                nOpenSocket.close();
//                Log.d(TAG, "SocketClosed");
//            }
//        } catch (IOException ex) {
//            Log.d(TAG, "CouldNotCloseSocket");
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        try {
//            if (mBluetoothSocket != null)
//                mBluetoothSocket.close();
//        } catch (Exception e) {
//            Log.e("Tag", "Exe ", e);
//        }
//
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        closeSocket(mBluetoothSocket);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        ListPairedDevices();
//    }
}