package com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.Dialog.CustomListViewDialog;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.FoodItemList;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.FoodItemListAdapter;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.FoodItemListPresenter;
import com.btracsolution.deliverysystem.Model.CartItem;
import com.btracsolution.deliverysystem.Model.DeliveryZoneResponse;
import com.btracsolution.deliverysystem.Model.MemberListResponse;
import com.btracsolution.deliverysystem.Model.MenuFoodListModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.Utility.Utils;
import com.btracsolution.deliverysystem.databinding.ActivityBillHistoryBinding;
import com.btracsolution.deliverysystem.databinding.ActivityMyCartBinding;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;

public class MyCartActivity extends BaseActivity<ActivityMyCartBinding> implements DeliveryZoneAdapter.DeliveryZoneListener,TableNoAdapter.TableNoListener{


/*    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.rvCart)
    RecyclerView rvCart;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.tvTotalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.tvMemName)
    TextView tvMemName;
    @BindView(R.id.tvTableNo)
    TextView tvTableNo;
    @BindView(R.id.etMemId)
    EditText etMemId;
    @BindView(R.id.civMem)
    CircleImageView civMem;
    @BindView(R.id.ivSearchMem)
    ImageView ivSearchMem;
    @BindView(R.id.llLocation)
    LinearLayout llLocation;
    @BindView(R.id.llTableNo)
    LinearLayout llTableNo;
    @BindView(R.id.llPaymentCash)
    LinearLayout llPaymentCash;
    @BindView(R.id.llPaymentCard)
    LinearLayout llPaymentCard;
    @BindView(R.id.llPaymentMobile)
    LinearLayout llPaymentMobile;
    @BindView(R.id.ivCardTick)
    ImageView ivCardTick;
    @BindView(R.id.ivCashTick)
    ImageView ivCashTick;
    @BindView(R.id.ivMobileTick)
    ImageView ivMobileTick;
    @BindView(R.id.tvDeliveryZone)
    TextView tvDeliveryZone;
    @BindView(R.id.actvMemid)
    AutoCompleteTextView actvMemid;*/
    MyCartPresenter myCartPresenter;
    ArrayList<CartItem> cartItems;
    ArrayList<MemberListResponse.MemberData> memberListResponses;
    SharedData sharedData;
    CustomListViewDialog locationDialog;
    CustomListViewDialog tableNoDialog;
    List<DeliveryZoneResponse.Zone> deliveryZoneResponseList;
    List<DeliveryZoneResponse.TableNo> deliveryTableNoResponseList;
    MemberSearchAdapter memberSearchAdapter;
    //Toolbar
/*    @BindView(R.id.tvCount)
    TextView tvCount;
    @BindView(R.id.llBack)
    LinearLayout llBack;
    @BindView(R.id.rlCart)
    RelativeLayout rlCart;
    @BindView(R.id.ivCart)
    ImageView ivCart;
    @BindView(R.id.tvTitle)
    TextView tvTitle;*/
    String memId,deliZone,tableNo;
    int SELECT_TYPE_ZONE = -1,deliveryZoneId,SELECT_TYPE_TABLE=-1,tableNoId;
    double totalPrice=0;
    public static void open(Context context,String cart) {
        Intent intent = new Intent(context, MyCartActivity.class);
        intent.putExtra("cart", cart);
//        intent.putExtra("position", position);
        context.startActivity(intent);
    }

/*
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_cart;
    }
*/

    public ActivityMyCartBinding getBinding() {
        return binding;
    }

    @Override
    public void initView() {
        super.initView();
        initializeToolbar();
        setupRecycleView();
        initializeView();
        getIntentData();

    }

    @Override
    protected ActivityMyCartBinding getViewBinding() {
        return ActivityMyCartBinding.inflate(getLayoutInflater());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    private void initializeView() {
        binding.llBar.tvTitle.setText("My Cart");
        sharedData=new SharedData(MyCartActivity.this);
        deliveryZoneResponseList= new ArrayList<>();
        deliveryTableNoResponseList= new ArrayList<>();
        memberListResponses=new ArrayList<>();
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    myCartPresenter.sendDataToServer(true,memId,String.valueOf(deliveryZoneId),"0", "0",String.valueOf(tableNoId));
                }

            }
        });
        binding.llBar.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetSharedDataCart();
                finish();
            }
        });
        binding.ivSearchMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String memid = binding.actvMemid.getText().toString();
                    if (memid != null && memid.length() >= 4) {
                        hideKeyboard();
                        myCartPresenter.getSingleMemberData(true, binding.actvMemid.getText().toString());

                    }else{
                        showToast("Please enter valid member id");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                }

        });
        binding.llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (deliveryZoneResponseList!=null && deliveryZoneResponseList.size()>0){
                    DeliveryZoneAdapter dataAdapter = new DeliveryZoneAdapter(MyCartActivity.this, deliveryZoneResponseList, SELECT_TYPE_ZONE);
                    locationDialog = new CustomListViewDialog(MyCartActivity.this, dataAdapter);
                    locationDialog.show();
                    locationDialog.setCanceledOnTouchOutside(true);
                }
            }
        });

        binding.llTableNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deliveryTableNoResponseList!=null && deliveryTableNoResponseList.size()>0){
                    TableNoAdapter dataAdapter = new TableNoAdapter(MyCartActivity.this, deliveryTableNoResponseList, SELECT_TYPE_TABLE);
                    tableNoDialog = new CustomListViewDialog(MyCartActivity.this, dataAdapter);
                    tableNoDialog.show();
                    tableNoDialog.setCanceledOnTouchOutside(true);
                }
            }
        });

        binding.llPaymentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llPaymentCard.setBackground(MyCartActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg_with_stroke));
                binding.llPaymentCash.setBackground(MyCartActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg));
                binding.llPaymentMobile.setBackground(MyCartActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg));
                binding.ivCardTick.setVisibility(View.VISIBLE);
                binding.ivCashTick.setVisibility(View.GONE);
                binding.ivMobileTick.setVisibility(View.GONE);
            }
        });
        binding.llPaymentCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llPaymentCash.setBackground(MyCartActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg_with_stroke));
                binding.llPaymentCard.setBackground(MyCartActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg));
                binding.llPaymentMobile.setBackground(MyCartActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg));
                binding.ivCardTick.setVisibility(View.GONE);
                binding.ivCashTick.setVisibility(View.VISIBLE);
                binding.ivMobileTick.setVisibility(View.GONE);
            }
        });
        binding.llPaymentMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llPaymentMobile.setBackground(MyCartActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg_with_stroke));
                binding.llPaymentCash.setBackground(MyCartActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg));
                binding.llPaymentCard.setBackground(MyCartActivity.this.getResources().getDrawable(R.drawable.silver_rounded_bg));
                binding.ivCardTick.setVisibility(View.GONE);
                binding.ivCashTick.setVisibility(View.GONE);
                binding.ivMobileTick.setVisibility(View.VISIBLE);
            }
        });
        binding.actvMemid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.actvMemid.getText().clear();
                binding.actvMemid.showDropDown();
            }
        });
        binding.actvMemid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               if (binding.actvMemid.getText().length() < 4)
                    binding.tvMemName.setText("");
                    binding.civMem.setImageDrawable(MyCartActivity.this.getResources().getDrawable(R.drawable.ic_profile));
            }
        });
//        if (sharedData.getCartData() != null) {
//            cartItems=sharedData.getCartData();
//        }else{
//            cartItems=new ArrayList<>();
//        }

        binding.scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 100);

    }

    private boolean validate() {
        if (cartItems.size()<1){
            showAlert("No item found in your cart");
            return false;
        }
        if (memId==null || memId.isEmpty()){
            showToast("Please give member data");
            return false;
        }
        if (SELECT_TYPE_ZONE==-1){
            showToast("Please select delivery zone");
            return false;
        }
        if (SELECT_TYPE_TABLE==-1){
            showToast("Please select table no");
            return false;
        }
        return true;
    }

    private void initializeToolbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(MyCartActivity.this, R.color.colorAccent));
        }

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        }
    }

    public void setupRecycleView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvCart.setLayoutManager(llm);
    }

    public void setAdapterForRecycleView(MyCartAdapter adapterForRecycleView) {
        binding.rvCart.setAdapter(adapterForRecycleView);

    }

    public void setTitleOfActivity(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void getIntentData() {
        if (getIntent() != null) {
            cartItems = (ArrayList<CartItem>) new Gson().fromJson(getIntent().getStringExtra("cart"),
                    new TypeToken<ArrayList<CartItem>>() {
                    }.getType());
            myCartPresenter = new MyCartPresenter(MyCartActivity.this);
            myCartPresenter.prepareDataForAdapter();
            myCartPresenter.getDeliveryZoneData(false);
            myCartPresenter.getAllMemberListFromServer(false);

            countTotalPrice();

        }
        setTitleOfActivity("MY Cart");
    }

    public void countTotalPrice(){
        try {
            totalPrice=0;
            if (cartItems.size()>0){
                for (int i=0;i<cartItems.size();i++){
                    if (!cartItems.get(i).getFoodPrice().isEmpty()){
                        double price= Double.parseDouble(cartItems.get(i).getFoodPrice())* cartItems.get(i).getQuantity();
                        totalPrice=totalPrice+price;
                    }
                }
            }
            binding.tvTotalPrice.setText("BDT "+totalPrice);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        resetSharedDataCart();
        finish();
    }

    public void resetSharedDataCart(){
        if (sharedData.getCartData() != null) {
            sharedData.unsetCartData();
        }
        sharedData.setCartData(new Gson().toJson(cartItems));
    }

    @Override
    public void onClickDeliveryZone(int position) {
        SELECT_TYPE_ZONE=position;
        deliveryZoneId= deliveryZoneResponseList.get(position).getId();
        binding.tvDeliveryZone.setText(deliveryZoneResponseList.get(position).getZonename());
        locationDialog.cancel();
    }

    @Override
    public void onClickTableNo(int position) {
        SELECT_TYPE_TABLE=position;
        tableNoId= deliveryTableNoResponseList.get(position).getTable_id();
        binding.tvTableNo.setText(deliveryTableNoResponseList.get(position).getTablename());
        tableNoDialog.cancel();
    }

    public void setupMemberAdapter(){

        memberSearchAdapter = new MemberSearchAdapter(getApplicationContext(), R.layout.autocompletetextview, memberListResponses);
        binding.actvMemid.setAdapter(memberSearchAdapter);
        //etMobile.showDropDown();
        memberSearchAdapter.setListener(new MemberSearchAdapter.ItemClickListener() {
            @Override
            public void onItemClcik(MemberListResponse.MemberData memberData) {
                View view = MyCartActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                binding.tvMemName.setText(""+memberData.getMemberName());
                binding.actvMemid.setText(memberData.getMemberId());
                binding.actvMemid.dismissDropDown();
                binding.actvMemid.clearListSelection();

                memId=memberData.getMemberId();

                Glide.with(MyCartActivity.this)
                .load(memberData.getMemberImage())
                .centerCrop()
                .into(binding.civMem);
                binding.actvMemid.clearFocus();
         //   Utils.hideKeyboard(MyCartActivity.this);
            }
        });
    }
}