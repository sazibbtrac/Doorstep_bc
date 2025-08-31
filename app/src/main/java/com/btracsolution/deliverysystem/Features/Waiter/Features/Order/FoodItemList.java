package com.btracsolution.deliverysystem.Features.Waiter.Features.Order;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider.AgentRiderListAdapter;
import com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider.AgentSelectRider;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart.MyCartActivity;
import com.btracsolution.deliverysystem.Model.CartItem;
import com.btracsolution.deliverysystem.Model.MenuFoodListModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.Utility.Validation;
import com.btracsolution.deliverysystem.databinding.ActivityMyCartBinding;
import com.btracsolution.deliverysystem.databinding.ActivityProductlistBinding;
import com.google.gson.Gson;

import java.util.ArrayList;



public class FoodItemList extends BaseActivity<ActivityProductlistBinding> {

  /*  @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.rvHorizontal)
    RecyclerView rvHorizontal;
    @BindView(R.id.ivCross)
    ImageView ivCross;
    @BindView(R.id.etSearch)
    EditText etSearch;
    //Toolbar
    @BindView(R.id.tvCount)
    TextView tvCount;
    @BindView(R.id.llBack)
    LinearLayout llBack;
    @BindView(R.id.llCountBottom)
    LinearLayout llCountBottom;
    @BindView(R.id.rlCart)
    RelativeLayout rlCart;
    @BindView(R.id.ivCart)
    ImageView ivCart;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvCountBottom)
    TextView tvCountBottom;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
*/
    FoodItemListPresenter foodItemListPresenter;
    ArrayList<CartItem> cartItems;
    SharedData sharedData;
    SubCategoryAdapter subCategoryAdapter;

    public static void open(Context context, String food_category, int position) {
        Intent intent = new Intent(context, FoodItemList.class);
        intent.putExtra("food_category", food_category);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }
/*
    @Override
    protected int getLayoutId() {
        return R.layout.activity_productlist;
    }*/

    @Override
    public void initView() {
        super.initView();
        initializeToolbar();
        setupRecycleView();
        initHorizontalRecyclerView();
        initializeView();
        getIntentData();

    }

    public ActivityProductlistBinding getBinding() {
        return binding;
    }

    @Override
    protected ActivityProductlistBinding getViewBinding() {
        return ActivityProductlistBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void initializeView() {

        binding.toolbar.tvTitle.setText("Food Items");
        sharedData=new SharedData(FoodItemList.this);
        if (sharedData.getCartData() != null) {
            cartItems=sharedData.getCartData();
        }else{
            cartItems=new ArrayList<>();
        }
        binding.toolbar.rlCart.setVisibility(View.VISIBLE);
        if (cartItems.size()>0) {
            binding.toolbar.tvCount.setVisibility(View.VISIBLE);
            binding.toolbar.tvCount.setText("" + cartItems.size());

            binding.tvCountBottom.setVisibility(View.VISIBLE);
            binding.tvCountBottom.setText(""+cartItems.size());
            countTotalPrice();
        }
        binding.toolbar.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetSharedDataCart();
                finish();
            }
        });


        binding.toolbar.rlCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartItems.size()>0){
                    String cart = new Gson().toJson(cartItems);
                    MyCartActivity.open(FoodItemList.this,cart);
                    finish();
                }else{
                    showToast("Cart is empty");
                }
            }
        });
        binding.llCountBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartItems.size()>0){
                    String cart = new Gson().toJson(cartItems);
                    MyCartActivity.open(FoodItemList.this,cart);
                    finish();
                }else{
                    showToast("Cart is empty");
                }
            }
        });
        binding.ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etSearch.setText("");
                hideKeyboard();
            }
        });
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.equals("")) {
                    ((FoodItemListAdapter) binding.recyclerView.getAdapter()).getFilter().filter(charSequence.toString());
                } else {
                    ((FoodItemListAdapter) binding.recyclerView.getAdapter()).getFilter().filter("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void initializeToolbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(FoodItemList.this, R.color.colorAccent));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
    private void initHorizontalRecyclerView(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rvHorizontal.setLayoutManager(layoutManager);
    }
    public void setAdapterForHoriZontalRecycleView(SubCategoryAdapter subCategoryAdapter) {
        binding.rvHorizontal.setAdapter(subCategoryAdapter);

    }
    public void setupRecycleView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(llm);
    }

    public void setAdapterForRecycleView(FoodItemListAdapter adapterForRecycleView) {
        binding.recyclerView.setAdapter(adapterForRecycleView);

    }

    public void setTitleOfActivity(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void getIntentData() {
        if (getIntent() != null) {
            MenuFoodListModel menuFoodListModel = new Gson().fromJson(getIntent().getStringExtra("food_category"), MenuFoodListModel.class);
            if (menuFoodListModel != null) {
                foodItemListPresenter = new FoodItemListPresenter(FoodItemList.this, menuFoodListModel, getIntent().getIntExtra("position", 0));
                foodItemListPresenter.prepareDataForAdapter(menuFoodListModel);
                setTitleOfActivity(menuFoodListModel.getMenuData().get(getIntent().getIntExtra("position", 0)).getCategoryName());

            }
        } else
            finish();
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

    double totalPrice=0;
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
            binding.tvTotal.setText("BDT "+totalPrice);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
