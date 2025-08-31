package com.btracsolution.deliverysystem.Base;

import com.btracsolution.deliverysystem.Model.RiderListSingleModel;

public interface BaseItemClickListenerRiderSelect {
    void onClickOfListItem(boolean isClick, RiderListSingleModel.riderInfo riderInfo, String extra);
}
