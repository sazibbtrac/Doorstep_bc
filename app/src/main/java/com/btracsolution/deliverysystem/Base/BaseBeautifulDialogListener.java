package com.btracsolution.deliverysystem.Base;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by mahmudul.hasan on 3/4/2018.
 */

public interface BaseBeautifulDialogListener {

    void onClickOfPositiveButton(boolean isClick, int position, String extra, MaterialDialog dialog,int identifier);

    void onClickOfNegativeButton(boolean isClick, int position, String extra, MaterialDialog dialog,int identifier);
}
