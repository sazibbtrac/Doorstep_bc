package com.btracsolution.deliverysystem.Features.Rider.Features.MyCompletedJobPack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.btracsolution.deliverysystem.R;
import com.btracsolution.deliverysystem.databinding.ActivityCompletedDeliveryBinding;
import com.btracsolution.deliverysystem.databinding.ActivityDaydetailsBinding;

/*import butterknife.ButterKnife;*/

/**
 * Created by mahmudul.hasan on 1/4/2018.
 */

public class RiderCompletedJobFragment extends Fragment {

    private ActivityCompletedDeliveryBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = ActivityCompletedDeliveryBinding.inflate(inflater, container, false);
        //  topBinding = RowProfileTopBinding.bind(binding.rowProfileTop.getRoot());
        //  bottomBinding = RowProfileBottomBinding.bind(binding.rowProfileBottom.getRoot());
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

}
