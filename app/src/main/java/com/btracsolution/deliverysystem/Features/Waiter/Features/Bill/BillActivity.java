package com.btracsolution.deliverysystem.Features.Waiter.Features.Bill;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Features.Agent.IncomingCall.IncomingCallActivity;
import com.btracsolution.deliverysystem.Features.Waiter.Features.BillHistory.BillHistoryActivity;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart.MemberSearchAdapter;
import com.btracsolution.deliverysystem.Features.Waiter.WaiterActivity;
import com.btracsolution.deliverysystem.Firebase.MyRingtoneService;
import com.btracsolution.deliverysystem.Model.MemberListResponse;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.databinding.ActivityDaydetailsBinding;
import com.btracsolution.deliverysystem.databinding.ActivityMyOrderListAgentBinding;
import com.btracsolution.deliverysystem.databinding.FragmentBillBinding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillActivity extends Fragment {

    // TODO: Rename parametFer arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "BillActivity";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    BillAdapter billAdapter;
    BillAdapterWithHeader billAdapterWithHeader;
    public BillPresenter billPresenter;
    SharedData sharedData;
    ArrayList<MemberListResponse.MemberData> memberListResponses;
    public ArrayList<OrderDetailsModel.orderBasicData> orderList = null;

    private FragmentBillBinding binding;
    //fragment_bill
   /* @BindView(R.id.rel_no_order)
    RelativeLayout rel_no_order;
    @BindView(R.id.ivCross)
    ImageView ivCross;
    @BindView(R.id.etSearch)
    AutoCompleteTextView etSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.tvGrandTotal)
    TextView tvGrandTotal;
    @BindView(R.id.etPayAmount)
    EditText etPayAmount;
    @BindView(R.id.btnPay)
    Button btnPay;
    @BindView(R.id.btnBillHisotry)
    Button btnBillHisotry;
    @BindView(R.id.llTotal)
    LinearLayout llTotal;
*/
/*    @BindView(R.id.tvPOptionLabel)
    TextView tvPOptionLabel;
    @BindView(R.id.llPaymentOption)
    LinearLayout llPaymentOption;
    @BindView(R.id.llPaymentCash)
    LinearLayout llPaymentCashCard;
    @BindView(R.id.llPaymentCard)
    LinearLayout llPaymentCard;
    @BindView(R.id.llPaymentMobile)
    LinearLayout llPaymentCredit;
    @BindView(R.id.ivCardTick)
    ImageView ivCardTick;
    @BindView(R.id.ivCashTick)
    ImageView ivCashTick;
    @BindView(R.id.ivMobileTick)
    ImageView ivMobileTick;*/

    public String memberid, totalBill = "0", payMethod = "0";
    //Toolbar
    /*@BindView(R.id.tvCount)
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
    @BindView(R.id.textDeviceName)
    TextView textDeviceName;
    @BindView(R.id.cbAll)
    CheckBox cbAll;
*/
    View v;

    MemberSearchAdapter memberSearchAdapter;

    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    public BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;

    public BillActivity() {
        // Required empty public constructor
    }


    public FragmentBillBinding getBinding() {
        return binding;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Bill.
     */
    // TODO: Rename and change types and number of parameters
    public static BillActivity newInstance(String param1, String param2) {
        BillActivity fragment = new BillActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBillBinding.inflate(inflater, container, false);
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

        mBluetoothSocket = DeliveryApp.getInstance().getCurrentBluetoothConnection();
        try {
            if (mBluetoothSocket != null && mBluetoothSocket.isConnected()) {
                showToast("Printer Connected");
            } else {
                showToast("Printer not Connected");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        orderList = new ArrayList<>();
        memberListResponses = new ArrayList<>();
        setupRecycleView();
        binding.llTotal.setVisibility(View.GONE);
        binding.btnPay.setVisibility(View.GONE);
        binding.cbAll.setVisibility(View.GONE);
        setUpToolBar();
        billPresenter = new BillPresenter();
        billPresenter.attachView(getActivity(), getActivity(), this);
        billPresenter.getAllMemberListFromServer(false);
        checkBluetoothPermission();
        //ListPairedDevices();
        binding.btnBillHisotry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BillHistoryActivity.open(getActivity());
            }
        });
        binding.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateItem() && validateData()) {
                   // showToast("Total bill +"+totalBill);
                    billPresenter.payBillToServer(true, memberid, orderList, totalBill, payMethod);
                    //  PrintUtility.getOrderItemsForWaiterV2(orderList);
                }
            }
        });
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
                        billPresenter.getSingleMemberData(true, binding.etSearch.getText().toString());

                    }else{
                        showToast("Please enter valid member id");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        binding.llPaymentCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payMethod = "1";
                binding.llPaymentCash.setBackground(BillActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg_with_stroke));
                binding.llPaymentCard.setBackground(BillActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg));
                binding.llPaymentMobile.setBackground(BillActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg));
                binding.ivCardTick.setVisibility(View.GONE);
                binding.ivCashTick.setVisibility(View.VISIBLE);
                binding.ivMobileTick.setVisibility(View.GONE);
            }
        });
        binding.llPaymentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payMethod = "2";
                binding.llPaymentCard.setBackground(BillActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg_with_stroke));
                binding.llPaymentCash.setBackground(BillActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg));
                binding.llPaymentMobile.setBackground(BillActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg));
                binding.ivCardTick.setVisibility(View.VISIBLE);
                binding.ivCashTick.setVisibility(View.GONE);
                binding.ivMobileTick.setVisibility(View.GONE);
            }
        });

        binding.llPaymentMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payMethod = "3";
                binding.llPaymentMobile.setBackground(BillActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg_with_stroke));
                binding.llPaymentCash.setBackground(BillActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg));
                binding.llPaymentCard.setBackground(BillActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg));
                binding.ivCardTick.setVisibility(View.GONE);
                binding.ivCashTick.setVisibility(View.GONE);
                binding.ivMobileTick.setVisibility(View.VISIBLE);
            }
        });
        binding.cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (binding.cbAll.isChecked()){
                    binding.etPayAmount.setText(billPresenter.orderDetailsModel.getBillDetails().getTotalamount().toString() + " BDT");
                    binding.tvGrandTotal.setText(billPresenter.orderDetailsModel.getBillDetails().getTotalamount().toString() + " BDT");
                    totalBill = billPresenter.orderDetailsModel.getBillDetails().getTotalamount().toString();
                }else{

                }
            }
        });
        binding.cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (orderList != null && orderList.size() > 0) {
                        if (binding.cbAll.isChecked()) {
                            billAdapterWithHeader.selectAll();
                            //  recyclerView.smoothScrollToPosition(vehicleListAdapter.getItemCount()- 1);
//                            etPayAmount.setText(billPresenter.orderDetailsModel.getBillDetails().getTotalamount().toString() + " BDT");
//                            tvGrandTotal.setText(billPresenter.orderDetailsModel.getBillDetails().getTotalamount().toString() + " BDT");
//                            totalBill = billPresenter.orderDetailsModel.getBillDetails().getTotalamount().toString();
                        } else {
                            billAdapterWithHeader.unselectall();
                            totalBill = "0";
                            binding.tvGrandTotal.setText("0");
                            binding.etPayAmount.setText("0");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean validateData() {
        if (memberid == null) {
            showToast("Please enter member id");
            return false;
        }
        if (payMethod == "0") {
            showToast("Select payment method");
            return false;
        }
        if (orderList != null && orderList.size() < 1) {
            showToast("No bill found");
            return false;
        }
        return true;
    }

    private boolean validateItem() {
        boolean res = false;
        try {
            if (orderList != null && orderList.size() > 0) {
                for (int i = 0; i < orderList.size(); i++) {
                    if (orderList.get(i).isOrderChecked()) {
                        res = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!res) {
            showToast("Please select at least one order");
        }
        return res;
    }

    private void setUpToolBar() {
        binding.bar.ivBack.setVisibility(View.GONE);
        binding.bar.textDeviceName.setVisibility(View.VISIBLE);
        binding.bar.textDeviceName.setText("Bill history");
        binding.bar.textDeviceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BillHistoryActivity.open(getActivity());

            }
        });
        sharedData = new SharedData(getContext());
        if (sharedData != null) {
//            Toast.makeText(this, sharedData.getMyData(), Toast.LENGTH_SHORT).show();
            if (sharedData.getMyData() != null) {
                // setTitleOfActivity(sharedData.getMyData().getData().getRestaurantInfo().getName() + " " + "➞" + " " + sharedData.getMyData().getData().getBranchInfo().get(0).getBranchName());
                binding.bar.tvTitle.setText(sharedData.getMyData().getData().getRestaurantInfo().getName() + " " + "\n" + sharedData.getMyData().getData().getFullName());
                //setSubTitleOfActivity(sharedData.getMyData().getData().getFullName());
            }

        } else {

        }
        //   tvTitle.setText("Order List");
        binding.bar.ivBack.setVisibility(View.GONE);
    }

    public void setupRecycleView() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(llm);
    }

    public void setAdapterIntoRecyclView(BillAdapter billAdapter) {
        this.billAdapter = billAdapter;
        binding.recyclerView.setAdapter(billAdapterWithHeader);
    }

    public void setAdapterIntoRecyclView(BillAdapterWithHeader billAdapterWithHeader) {
        this.billAdapterWithHeader = billAdapterWithHeader;
        binding.recyclerView.setAdapter(billAdapterWithHeader);
    }


    public void showToast(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }

    public void yesOrderFound() {
        binding.relNoOrder.setVisibility(View.GONE);
        //pull_to_refresh.setRefreshing(false);
    }

    public void noOrderFound() {
        binding.relNoOrder.setVisibility(View.VISIBLE);
        // pull_to_refresh.setRefreshing(false);


    }

    public void showToast(String message) {
        if (getActivity() != null && !getActivity().isFinishing())
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void setupMemberAdapter() {
        try {
            if (this.getActivity() != null) {
                memberSearchAdapter = new MemberSearchAdapter(this.getActivity(), R.layout.autocompletetextview, memberListResponses);
                binding.etSearch.setAdapter(memberSearchAdapter);
                //etMobile.showDropDown();
                memberSearchAdapter.setListener(new MemberSearchAdapter.ItemClickListener() {
                    @Override
                    public void onItemClcik(MemberListResponse.MemberData memberData) {
                        View view = getActivity().getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        //tvMemName.setText(memberData.getMemberName());
                        binding.etSearch.setText(memberData.getMemberId());
                        binding.etSearch.dismissDropDown();
                        binding.etSearch.clearListSelection();
                        binding.etSearch.clearFocus();
                        //   Utils.hideKeyboard(MyCartActivity.this);

                        billPresenter.getServerData(true, binding.etSearch.getText().toString());
                        memberid = memberData.getMemberId();

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < 31) {
            if (DeliveryApp.getInstance().getCurrentBluetoothConnection() != null && DeliveryApp.getInstance().getCurrentBluetoothConnection().isConnected()) {

            } else {
                DeliveryApp.getInstance().setupBluetoothConnection();
            }
        }
    }

    private void checkBluetoothPermission() {

        try {
            if (Build.VERSION.SDK_INT >= 31) {
          /*      new TedPermission(this.getActivity())
                        .setPermissionListener(permissionlistener)
                        //.setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        // .setPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .setPermissions(Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN)
                        .check();*/

                TedPermission.create()
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN)
                        .check();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            DeliveryApp.getInstance().setupBluetoothConnection();        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            // Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };



    public void hideKeyboard() {
        try {
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}