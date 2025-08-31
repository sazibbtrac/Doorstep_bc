package com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Base.BaseItemClickListenerRiderSelect;
import com.btracsolution.deliverysystem.Base.MainViewHolder;
import com.btracsolution.deliverysystem.Model.RiderListSingleModel;
import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.RowAgentRiderListBinding;
import com.btracsolution.deliverysystem.databinding.RowInsideJobTwoGeneric2Binding;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

/*import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;*/
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class AgentRiderViewHolder extends MainViewHolder implements View.OnClickListener{
    private RowAgentRiderListBinding binding;

    public BaseItemClickListenerRiderSelect baseItemClickListener;
/*
    @BindView(R.id.tx_distance)
    TextView tx_distance;

    @BindView(R.id.tx_availability)
    TextView tx_availability;

    @BindView(R.id.tx_name_rider)
    TextView tx_name_rider;

    @BindView(R.id.rel_call_now)
    RelativeLayout rel_call_now;

    @BindView(R.id.lin_assign)
    LinearLayout lin_assign;

    @BindView(R.id.lin_location)
    LinearLayout lin_location;

    @BindView(R.id.profile_image)
    CircleImageView profile_image;*/


    RiderListSingleModel.riderInfo current_position;

    public AgentRiderViewHolder(View itemView) {
        super(itemView);
        binding = RowAgentRiderListBinding.bind(itemView);
        binding.relCallNow.setOnClickListener(this);
        binding.linLocation.setOnClickListener(this);
        binding.linAssign.setOnClickListener(this);
    }

    public void setDataIntoView(Context context, RiderListSingleModel.riderInfo riderInfo, BaseItemClickListenerRiderSelect baseItemClickListener) {
        this.baseItemClickListener = baseItemClickListener;
        this.current_position = riderInfo;

        Glide.with(context)
                .load(riderInfo.getProfileImage())
                .centerCrop()
                .placeholder(R.drawable.dr_logo)
                .error(R.drawable.ic_face_black_36dp)
                .into(binding.profileImage);
        binding.txNameRider.setText(riderInfo.getFullname());
        if (riderInfo.getDistance() != null){
            binding.txDistance.setText(getFormatedString(riderInfo.getDistance()) + context.getString(R.string.km));
        }



            binding.txAvailability.setText(context.getString(R.string.available));
            binding.txAvailability.setBackgroundResource(R.drawable.rectengle_availability);




       /* Glide.with(context)
                .load(riderInfo.getProfileImage())
                .centerCrop()
                .placeholder(R.drawable.dr_logo)
                .error(R.drawable.ic_face_black_36dp)
                .into(binding.profileImage);
        binding.txNameRider.setText(riderInfo.getFullname());
        if (riderInfo.getDistance() != null)
            binding.txDistance.setText(getFormatedString(riderInfo.getDistance()) + context.getString(R.string.km));

        if (riderInfo.getAvailable().equals("1") && riderInfo.getLoginstatus().equals("1")){
            binding.txAvailability.setText(context.getString(R.string.available));
            binding.txAvailability.setBackgroundResource(R.drawable.rectengle_availability);
        }
        else{
            binding.txAvailability.setText(context.getString(R.string.un_available));
            binding.txAvailability.setBackgroundResource(R.drawable.rectengle_un_availability);
        }
        */


//        switch (riderInfo.getAvailable()) {
//            case "1":
//                tx_availability.setText(context.getString(R.string.available));
//                tx_availability.setBackgroundResource(R.drawable.rectengle_availability);
//                break;
//            case "2":
//                tx_availability.setText(context.getString(R.string.un_available));
//                tx_availability.setBackgroundResource(R.drawable.rectengle_un_availability);
//                break;
//        }

    }

    public String getFormatedString(String number) {
        try {
            double amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat("0.00");

            System.out.println(formatter.format(amount));
            return formatter.format(amount);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return number;
        }
    }

/*
    @OnCheckedChanged({R.id.sw_menu_off})
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked) {
        if (checked) {
            switch (button.getId()) {
                case R.id.sw_menu_off:
                    AgentMenuDetailViewHolder.this.baseItemClickListener.onClickOfListItem(true, current_position, "checked");
                    // do stuff
                    break;
            }
        } else {
            switch (button.getId()) {
                case R.id.sw_menu_off:
                    AgentMenuDetailViewHolder.this.baseItemClickListener.onClickOfListItem(true, current_position, "unchecked");
                    // do stuff
                    break;
            }
        }
    }
*/

    //@OnClick(R.id.rel_call_now)
    public void onCallClick() {
        baseItemClickListener.onClickOfListItem(true, current_position, "call");


    }

   // @OnClick(R.id.lin_location)
    public void onLocationgo() {
        baseItemClickListener.onClickOfListItem(true, current_position, "location");


    }

   // @OnClick(R.id.lin_assign)
    public void onAssign() {
        baseItemClickListener.onClickOfListItem(true, current_position, "assign");


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rel_call_now:
                onCallClick();
                break;

            case R.id.lin_location:
                onLocationgo();
                break;

            case R.id.lin_assign:
                onAssign();
                break;
        }
    }
}
