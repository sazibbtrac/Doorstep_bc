package com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Menu;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart.MyCartActivity;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.FoodItemList;
import com.btracsolution.deliverysystem.Model.CartItem;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.databinding.ActivityMenuDesignBinding;
import com.btracsolution.deliverysystem.databinding.FragmentWaiterMenuBinding;
import com.google.gson.Gson;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaiterMenuCategory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaiterMenuCategory extends Fragment {

    private FragmentWaiterMenuBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    WaiterMenuPresenter waiterMenuPresenter;
/*    @BindView(R.id.simpleListView)
    ListView simpleListView;*/
    View v;


   /* //Toolbar
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
    SharedData sharedData;
    ArrayList<CartItem> cartItems;
    public WaiterMenuCategory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaiterMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static WaiterMenuCategory newInstance(String param1, String param2) {
        WaiterMenuCategory fragment = new WaiterMenuCategory();
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


        binding = FragmentWaiterMenuBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        setOnclickListener();
        return binding.getRoot();
    }

    private void setOnclickListener() {
    }

    public void showToast(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        waiterMenuPresenter = new WaiterMenuPresenter(getActivity(), WaiterMenuCategory.this);
        waiterMenuPresenter.getServerData(true);
        setHasOptionsMenu(true);
        sharedData= new SharedData(getContext());


        setUpToolBar();

        binding.bar.rlCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartItems!=null && cartItems.size()>0){
                    String cart = new Gson().toJson(cartItems);
                    MyCartActivity.open(view.getContext(),cart);
                    //finish();
                }else{
                    showToast(view.getContext(),"Cart is empty");
                }
            }
        });

    }

    private void setUpToolBar() {
        binding.bar.ivBack.setVisibility(View.GONE);
        binding.bar.rlCart.setVisibility(View.VISIBLE);
        if (sharedData != null) {
//            Toast.makeText(this, sharedData.getMyData(), Toast.LENGTH_SHORT).show();
            if (sharedData.getMyData() != null) {
                // setTitleOfActivity(sharedData.getMyData().getData().getRestaurantInfo().getName() + " " + "➞" + " " + sharedData.getMyData().getData().getBranchInfo().get(0).getBranchName());
                binding.bar.tvTitle.setText(sharedData.getMyData().getData().getRestaurantInfo().getName() + " " + "\n" +sharedData.getMyData().getData().getFullName());
                //setSubTitleOfActivity(sharedData.getMyData().getData().getFullName());
            }

        }else{

        }
        //   tvTitle.setText("Order List");
        binding.bar.ivBack.setVisibility(View.GONE);
    }
    public void setSimpleListView(ArrayList arrayList) {

        if (getActivity() != null && !getActivity().isFinishing()) {
            String[] from = {"name"};//string array
            int[] to = {R.id.tx_menu_category};//int array of views id's
            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), arrayList, R.layout.row_agent_menu, from, to);//Create object and set the parameters for simpleAdapter
            binding.simpleListView.setAdapter(simpleAdapter);//sets the adapter for listView

            //perform listView item click event
            binding.simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    waiterMenuPresenter.goRespectiveCategories(i);
                }
            });
        }
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint() && v != null) {
            return;
        }
        waiterMenuPresenter.getServerData(false);
        if (sharedData.getCartData() != null) {
            cartItems=sharedData.getCartData();
            binding.bar.tvCount.setText(""+cartItems.size());
            binding.bar.tvCount.setVisibility(View.VISIBLE);
        }else{
            binding.bar.tvCount.setText("0");
            binding.bar.tvCount.setVisibility(View.GONE);


        }
        //INSERT CUSTOM CODE HERE

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sharedData.getCartData() != null) {
            sharedData.unsetCartData();
        }
    }
}