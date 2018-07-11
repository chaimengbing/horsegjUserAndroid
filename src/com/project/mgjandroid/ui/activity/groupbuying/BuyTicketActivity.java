package com.project.mgjandroid.ui.activity.groupbuying;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.adapter.DateAdapter;
import com.project.mgjandroid.utils.CalendarUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

public class BuyTicketActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.img_calendar)
    private ImageView imgCalendar;
    @InjectView(R.id.tv_appointment_date)
    private TextView tvDate;
    @InjectView(R.id.common_back)
    private ImageView tvBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;

    private GridView record_gridView;//定义gridView
    private DateAdapter dateAdapter;//定义adapter
    private ImageView record_left;//左箭头
    private ImageView record_right;//右箭头
    private TextView record_title;//标题
    private int year;
    private int month;
    private String title;
    private int[][] days = new int[6][7];
    private int[] days1 = new int[42];
    private CalendarView calendarView;
    private Context context;
    private PopupWindow mPopWindow;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_buy_ticket);
        Injector.get(this).inject();
        //初始化日期数据
        initData();
        //初始化组件
        initPopWindow();
        //组件点击监听事件
        initEvent();
    }

    private void initData() {
        year = CalendarUtils.getYear();
        month = CalendarUtils.getMonth();
    }

    private void initEvent() {
        record_left.setOnClickListener(this);
        record_right.setOnClickListener(this);
        imgCalendar.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        tvTitle.setText("支付订单");
    }


    private void initPopWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_calendar, null);
        mPopWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopWindow.setBackgroundDrawable(cd);
        mPopWindow.setOutsideTouchable(false);
        mPopWindow.setFocusable(true);
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
        /**
         * 以下是初始化GridView
         */
        record_gridView = (GridView) view.findViewById(R.id.record_gridView);
        days = CalendarUtils.getDayOfMonthFormat(2018, 7);
        dateAdapter = new DateAdapter(this, days, year, month);//传入当前月的年
        record_gridView.setAdapter(dateAdapter);
        record_gridView.setVerticalSpacing(60);
        record_gridView.setEnabled(true);
        record_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mPopWindow!=null){
                    mPopWindow.dismiss();
                }
                title = year + "-" + month + "-";
                int dayNum = 0;
                //将二维数组转化为一维数组，方便使用
                for (int i = 0; i < days.length; i++) {
                    for (int j = 0; j < days[i].length; j++) {
                        days1[dayNum] = days[i][j];
                        dayNum++;
                    }
                }
                tvDate.setText(title+days1[position]);
            }
        });
        /**
         * 以下是初始化其他组件
         */
        record_left = (ImageView) view.findViewById(R.id.record_left);
        record_right = (ImageView) view.findViewById(R.id.record_right);
        record_title = (TextView) view.findViewById(R.id.record_title);
        setTile();
    }


    /**
     * 下一个月
     *
     * @return
     */
    private int[][] nextMonth() {
        if (month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }
        days = CalendarUtils.getDayOfMonthFormat(year, month);
        return days;
    }

    /**
     * 上一个月
     *
     * @return
     */
    private int[][] prevMonth() {
        if (month == 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        days = CalendarUtils.getDayOfMonthFormat(year, month);
        return days;
    }

    /**
     * 设置标题
     */
    private void setTile() {
        title = year + "年" + month + "月";
        record_title.setText(title);
    }

    private void showCalendar(){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mPopWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.img_calendar:
                if(mPopWindow!=null&&!mPopWindow.isShowing()){
                    showCalendar();
                }else {
                    mPopWindow.dismiss();
                }
                break;
            case R.id.record_left:
                days = prevMonth();
                dateAdapter = new DateAdapter(this, days, year, month);
                record_gridView.setAdapter(dateAdapter);
                dateAdapter.notifyDataSetChanged();
                setTile();
                break;
            case R.id.record_right:
                days = nextMonth();
                dateAdapter = new DateAdapter(this, days, year, month);
                record_gridView.setAdapter(dateAdapter);
                dateAdapter.notifyDataSetChanged();
                setTile();
                break;
            case R.id.common_back:
                back();
                break;
        }
    }
}
