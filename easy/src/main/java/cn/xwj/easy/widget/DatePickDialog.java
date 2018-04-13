package cn.xwj.easy.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.lang.ref.WeakReference;

import cn.xwj.easy.R;
import cn.xwj.easy.util.Utilities;

/**
 * 日期时间选择对话框
 * <p>
 * Created by xw on 2017/10/19.
 */

public class DatePickDialog extends DialogFragment {

    /**
     * 只显示日期对话框（不包括时间）
     */
    public static final int TYPE_SELECT_DATE = 0;
    /**
     * 只显示时间对话框（不包括日期）
     */
    public static final int TYPE_SELECT_TIME = 1;
    /**
     * 显示日期时间对话框
     */
    public static final int TYPE_SELECT_DATE_TIME = 3;

    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private String mTitle;
    private int type = TYPE_SELECT_DATE;

    private OnClickListener mPositiveButtonOnClick;
    private String mPositiveButtonText;

    private OnClickListener mNavigationButtonOnClick;
    private String mNavigationButtonText;


    public interface OnClickListener {
        void onClick(DatePickDialog pickDialog, @Nullable String date, @Nullable String time);
    }

    /**
     * 设置PositiveButton
     *
     * @param positiveButtonText    文本
     * @param positiveButtonOnClick 监听事件
     * @return DatePickDialog
     */
    public DatePickDialog setPositiveClick(String positiveButtonText,
                                           OnClickListener positiveButtonOnClick) {
        mPositiveButtonOnClick = positiveButtonOnClick;
        this.mPositiveButtonText = positiveButtonText;
        return this;
    }

    /**
     * 设置NavigationButton
     *
     * @param navigationButtonText    文本
     * @param navigationButtonOnClick 监听事件
     * @return DatePickDialog
     */
    public DatePickDialog setNavigationClick(String navigationButtonText,
                                             OnClickListener navigationButtonOnClick) {
        this.mNavigationButtonOnClick = navigationButtonOnClick;
        this.mNavigationButtonText = navigationButtonText;
        return this;
    }

    /**
     * 创建一个时间选择对话框
     *
     * @return atePickDialog
     */
    public static DatePickDialog create() {
        return new DatePickDialog();
    }

    /**
     * 设置显示对话框的类型
     *
     * @param type {@link #TYPE_SELECT_DATE},
     *             {@link #TYPE_SELECT_TIME},
     *             {@link #TYPE_SELECT_DATE_TIME }
     * @return datePickDialog
     */
    public DatePickDialog setType(int type) {
        this.type = type;
        return this;
    }

    /**
     * 设置对话框标题
     *
     * @param title 标题
     * @return datePickDialog
     */
    public DatePickDialog setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    /**
     * 显示对话框
     */
    public void show(FragmentManager fragmentManager) {
        if (fragmentManager == null) {
            throw new IllegalArgumentException("fragmentManager is null");
        }
        super.show(fragmentManager, "datePicker");
    }

    /**
     * 该方法在show的时候才会调用
     *
     * @param savedInstanceState
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.date_pick_layout, null);
        initView(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(mTitle)
                .setView(view);
        if (!TextUtils.isEmpty(mPositiveButtonText)) {
            builder.setPositiveButton(mPositiveButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mPositiveButtonOnClick != null) {
                        mPositiveButtonOnClick.onClick(DatePickDialog.this,
                                getDate(), getTime());
                    }
                }
            });
        }

        if (!TextUtils.isEmpty(mNavigationButtonText)) {
            builder.setNegativeButton(mNavigationButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mNavigationButtonOnClick != null) {
                        mNavigationButtonOnClick.onClick(DatePickDialog.this,
                                getDate(), getTime());
                    }
                }
            });
        }
        return builder.create();
    }


    /**
     * 初始化view
     *
     * @param view
     */
    private void initView(View view) {
        mDatePicker = (DatePicker) view.findViewById(R.id.date_picker);
        mTimePicker = (TimePicker) view.findViewById(R.id.time_picker);
        mTimePicker.setIs24HourView(true);
        if (type == TYPE_SELECT_DATE) {
            mTimePicker.setVisibility(View.GONE);
        } else if (type == TYPE_SELECT_TIME) {
            mDatePicker.setVisibility(View.GONE);
        }
    }

    /**
     * 获取选择的日期
     *
     * @return 如果{@code type}不等于 {@link #TYPE_SELECT_DATE}
     * or {@link #TYPE_SELECT_DATE_TIME} 结果为null
     */
    @Nullable
    private String getDate() {
        if (type == TYPE_SELECT_DATE || type == TYPE_SELECT_DATE_TIME) {
            return Utilities.formatString("%4d-%02d-%02d", mDatePicker.getYear(),
                    mDatePicker.getMonth() + 1, mDatePicker.getDayOfMonth());
        }
        return null;
    }

    /**
     * 获取选择的时间
     *
     * @return 如果{@code type}不等于 {@link #TYPE_SELECT_TIME}
     * or {@link #TYPE_SELECT_DATE_TIME} 结果为null
     */
    @Nullable
    private String getTime() {
        if (type == TYPE_SELECT_DATE_TIME || type == TYPE_SELECT_TIME) {
            if (Build.VERSION.SDK_INT >= 23) {
                return Utilities.formatString("%02d:%02d", mTimePicker.getHour(),
                        mTimePicker.getMinute());
            }
            return Utilities.formatString("%02d:%02d", mTimePicker.getCurrentHour(),
                    mTimePicker.getCurrentMinute());

        }
        return null;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mDatePicker = null;
        mTimePicker = null;
        mPositiveButtonOnClick = null;
        mNavigationButtonOnClick = null;
    }
}
