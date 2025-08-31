package com.btracsolution.deliverysystem.Base;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.btracsolution.deliverysystem.R;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

/**
 * Created by mahmudul.hasan on 3/4/2018.
 */

public class BaseBeautifulDialog {

    BaseBeautifulDialogListener baseBeautifulDialogListener;
    Activity activity;
    int headerColor = R.color.colorAccent;
    int iconDrawable = R.drawable.dr_logo;
    boolean withAnimationIcon = true;

    public BaseBeautifulDialog(BaseBeautifulDialogListener baseBeautifulDialogListener, Activity activity) {
        this.baseBeautifulDialogListener = baseBeautifulDialogListener;
        this.activity = activity;
    }

    public BaseBeautifulDialog setHeaderColor(int headerColor) {
        this.headerColor = headerColor;
        return this;
    }

    public BaseBeautifulDialog setIconDrawable(int iconDrawable) {
        this.iconDrawable = iconDrawable;
        return this;
    }

    public BaseBeautifulDialog setWithAnimationIcon(boolean withAnimationIcon) {
        this.withAnimationIcon = withAnimationIcon;
        return this;
    }

    public void showMessageWithTitle(String title, String message, final int identifier) {
        new MaterialStyledDialog.Builder(activity)
                .setTitle(title)
                .setDescription(message)
                .setCancelable(false)
//                .setIcon(iconDrawable)
                .withIconAnimation(withAnimationIcon)
                .setIcon(ContextCompat.getDrawable(activity, iconDrawable))
                .setPositiveText(activity.getString(R.string.ofcourse))
                .setNegativeText(activity.getString(R.string.close))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (baseBeautifulDialogListener != null) {
                            baseBeautifulDialogListener.onClickOfPositiveButton(true, 0, "test", dialog, identifier);
                        }

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (baseBeautifulDialogListener != null) {
                            baseBeautifulDialogListener.onClickOfNegativeButton(true, 0, "test", dialog, identifier);
                        }
                    }
                })
                .show();
    }

    public void showMessage(String message, final int identifier) {
        new MaterialStyledDialog.Builder(activity)
                .setDescription(message)
//                .setIcon(iconDrawable)
                .withIconAnimation(withAnimationIcon)
                .setIcon(ContextCompat.getDrawable(activity, iconDrawable))
                .setPositiveText(activity.getString(R.string.ofcourse))
                .setNegativeText(activity.getString(R.string.close))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (baseBeautifulDialogListener != null) {
                            baseBeautifulDialogListener.onClickOfPositiveButton(true, 0, "test", dialog, identifier);
                        }

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (baseBeautifulDialogListener != null) {
                            baseBeautifulDialogListener.onClickOfNegativeButton(true, 0, "test", dialog, identifier);
                        }
                    }
                })
                .show();
    }
}
