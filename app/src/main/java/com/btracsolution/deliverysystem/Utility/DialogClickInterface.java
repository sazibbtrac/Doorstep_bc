package com.btracsolution.deliverysystem.Utility;

import android.content.DialogInterface;

/**
 * Created by mahmudul.hasan on 3/5/2018.
 */

public interface DialogClickInterface {

    public void onClickPositiveButton(DialogInterface pDialog, int pDialogIntefier,String shortNote);

    public void onClickNegativeButton(DialogInterface pDialog, int pDialogIntefier);
}
