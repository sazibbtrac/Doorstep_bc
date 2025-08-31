package com.btracsolution.deliverysystem.Base;

import com.btracsolution.deliverysystem.Model.RiderListSingleModel;

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public interface BaseItemClickListener {
    void onClickOfListItem(boolean isClick, int position, String extra);

}
