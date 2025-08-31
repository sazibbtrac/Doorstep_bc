package com.btracsolution.deliverysystem.Features.Waiter.Features.BillHistory.Billitem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseActivity;
import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Features.Waiter.Features.BillHistory.BillHistoryActivity;
import com.btracsolution.deliverysystem.Features.Waiter.Features.BillHistory.BillHistoryAdapter;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJoblistDetail.WaiterJobDetailPresenter;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJoblistDetail.WaiterOrderDetailActivity;
import com.btracsolution.deliverysystem.Model.BillHistoryResponse;
import com.btracsolution.deliverysystem.Model.OrderDetailsModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.ActivityBillHistoryBinding;
import com.btracsolution.deliverysystem.databinding.ActivityBillItemBinding;
import com.google.gson.Gson;



public class BillItemActivity extends BaseActivity<ActivityBillItemBinding> {

/*

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

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

    BillHistoryResponse.Datum billdata;

    public static void open(Context context, BillHistoryResponse.Datum datum) {
        Intent intent = new Intent(context, BillItemActivity.class);
        intent.putExtra("billdata", new Gson().toJson(datum));
//        intent.putExtra("position", position);
        context.startActivity(intent);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bill_item);
//    }



    @Override
    public void initView() {
        super.initView();
        initializeToolbar();
        setupRecycleView();
        setDataFromIntent();

//
//        setAdapterDataIntoFoodView();
//
//        waiterJobDetailPresenter = new WaiterJobDetailPresenter();
//        waiterJobDetailPresenter.attachView(WaiterOrderDetailActivity.this, this);

    }

    @Override
    protected ActivityBillItemBinding getViewBinding() {
        return ActivityBillItemBinding.inflate(getLayoutInflater());
    }

    public void setupRecycleView() {
        LinearLayoutManager llm = new LinearLayoutManager(BillItemActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(llm);
    }
    public void setDataFromIntent() {
        if (getIntent() != null) {
            billdata = new Gson().fromJson(getIntent().getStringExtra("billdata"), BillHistoryResponse.Datum.class);
           // setDataIntoView(orderBasicData);
            BillItemAdapter billItemAdapter = new BillItemAdapter(this, billdata, baseItemClickListener);
            binding.recyclerView.setAdapter(billItemAdapter);
        } else
            finish();
    }
    private void initializeToolbar() {
        binding.bar.tvTitle.setText("Bill Items");
        binding.bar.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(BillItemActivity.this, R.color.colorAccent));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    BaseItemClickListener baseItemClickListener = new BaseItemClickListener() {
        @Override
        public void onClickOfListItem(boolean isClick, int position, String extra) {
            if (billdata != null) {
              //  BillItemActivity.open(activity, billHistoryResponse.getData().get(position));
            }
        }
    };
}