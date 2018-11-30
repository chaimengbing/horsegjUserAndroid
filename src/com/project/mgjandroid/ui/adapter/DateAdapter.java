package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.groupbuying.GroupPurchaseCoupon;
import com.project.mgjandroid.utils.CalendarUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class DateAdapter extends BaseAdapter {
    private final static String TAG = DateAdapter.class.getSimpleName();
    private int[] days = new int[42];
    private Context context;
    private int year;
    private int month;
    private int bespeakDays;
    private GroupPurchaseCoupon groupPurchaseCoupon;

    private int nextCount = -1;
    private int clickTemp = -1;
    private int clickMonth = -1;

    public DateAdapter(Context context, int[][] days, int year, int month, int bespeakDays, GroupPurchaseCoupon groupPurchaseCoupon) {
        this.context = context;
        int dayNum = 0;
        //将二维数组转化为一维数组，方便使用
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                this.days[dayNum] = days[i][j];
                dayNum++;
            }
        }
        this.year = year;
        this.month = month;
        this.bespeakDays = bespeakDays;
        this.groupPurchaseCoupon = groupPurchaseCoupon;
    }

    public void setSeclection(int position, int clickMonth) {
        clickTemp = position;
        this.clickMonth = clickMonth;
        notifyDataSetChanged();
    }

    public void setData(int[][] days, int year, int month, int bespeakDays) {
        int dayNum = 0;
        //将二维数组转化为一维数组，方便使用
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                this.days[dayNum] = days[i][j];
                dayNum++;
            }
        }
        this.year = year;
        this.month = month;
        this.bespeakDays = bespeakDays;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return days.length;
    }

    @Override
    public Object getItem(int i) {
        return days[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private long getDate(int year, int month, int day) {
        Date date = DateUtils.str2Date(year + "-" + month + "-" + day, "yyyy-MM-dd");

        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        long today = (System.currentTimeMillis() + offSet) / 86400000;
        long start = (date.getTime() + offSet) / 86400000;
        long intervalTime = start - today;

//        Calendar today = Calendar.getInstance();
//        today.set(Calendar.HOUR, 0);
//        today.set(Calendar.MINUTE, 0);
//        today.set(Calendar.SECOND, 0);
//
//
//        Calendar old = Calendar.getInstance();
//        old.set(Calendar.YEAR, year);
//        old.set(Calendar.MONTH, month - 1);
//        old.set(Calendar.DAY_OF_MONTH, day);
//
//        old.set(Calendar.HOUR, 0);
//        old.set(Calendar.MINUTE, 0);
//        old.set(Calendar.SECOND, 0);
        //老的时间减去今天的时间
//        long intervalMilli = old.getTimeInMillis() - today.getTimeInMillis();
//        int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));
        // -2:前天 -1：昨天 0：今天 1：明天 2：后天， out：显示日期
//        if (intervalTime >= -2 && intervalTime <= 2) {
//            Log.e(TAG, "xcts:" + String.valueOf(xcts));
//            return String.valueOf(xcts);
//        } else {
//            return "out";
//        }
        return intervalTime;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.date_item, null);
            viewHolder = new ViewHolder();
            viewHolder.date_item = (TextView) view.findViewById(R.id.date_item);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (days[i] < 0) {
            viewHolder.date_item.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.date_item.setVisibility(View.VISIBLE);
        }

        viewHolder.date_item.setTextColor(context.getResources().getColor(R.color.color_c));
        viewHolder.date_item.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        viewHolder.date_item.setText(days[i] + "");

        int day = CalendarUtils.getCurrentDayOfMonth();
        int nowadayMonth = CalendarUtils.getMonth();


        int count = day + bespeakDays;
        if (days[i] >= day && days[i] < count && nowadayMonth == month) {
            viewHolder.date_item.setBackgroundColor(context.getResources().getColor(R.color.color_f5));
            viewHolder.date_item.setTextColor(context.getResources().getColor(R.color.color_3));

            String lastDay = DateUtils.getDateLastDay(year, month);
            int visibleCount = 0;
            if (CheckUtils.isNoEmptyStr(lastDay)) {
                visibleCount = Integer.parseInt(lastDay.toString().trim()) - day + 1;
            }
            nextCount = bespeakDays - visibleCount;
        }
        int mMonth = nowadayMonth + 1;
        if (mMonth == 13) {
            mMonth = 1;
        }
        if (nextCount > 0 && month == mMonth) {
            if (days[i] <= nextCount) {
                viewHolder.date_item.setBackgroundColor(context.getResources().getColor(R.color.color_f5));
                viewHolder.date_item.setTextColor(context.getResources().getColor(R.color.color_3));
            }
        }
        if (getDate(year, month, days[i]) == 0) {
            viewHolder.date_item.setText("今天");
            viewHolder.date_item.setTextColor(context.getResources().getColor(R.color.bg_festival));
            viewHolder.date_item.getPaint().setFakeBoldText(true);
        } else if (getDate(year, month, days[i]) == 1) {
            viewHolder.date_item.setText("明天");
            viewHolder.date_item.getPaint().setFakeBoldText(true);
        } else if (getDate(year, month, days[i]) == 2) {
            viewHolder.date_item.setText("后天");
            viewHolder.date_item.getPaint().setFakeBoldText(true);
        }

        if (clickTemp == i && clickMonth == month) {
            viewHolder.date_item.setBackgroundColor(context.getResources().getColor(R.color.bg_festival));
            viewHolder.date_item.setTextColor(context.getResources().getColor(R.color.white));
        }

        return view;
    }

    /**
     * 优化Adapter
     */
    class ViewHolder {
        TextView date_item;
    }
}
