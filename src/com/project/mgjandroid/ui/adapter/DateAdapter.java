package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.utils.CalendarUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class DateAdapter extends BaseAdapter {
    private int[] days = new int[42];
    private Context context;
    private int year;
    private int month;
    private int bespeakDays;

    private int nextCount = -1;
    private int clickTemp = -1;
    private int clickMonth = -1;

    public DateAdapter(Context context, int[][] days, int year, int month, int bespeakDays) {
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

    private String getDate(int year, int month, int day) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);


        Calendar old = Calendar.getInstance();
        old.set(Calendar.YEAR, year);
        old.set(Calendar.MONTH, month - 1);
        old.set(Calendar.DAY_OF_MONTH, day);

        old.set(Calendar.HOUR, 0);
        old.set(Calendar.MINUTE, 0);
        old.set(Calendar.SECOND, 0);
        //老的时间减去今天的时间
        long intervalMilli = old.getTimeInMillis() - today.getTimeInMillis();
        int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));
        // -2:前天 -1：昨天 0：今天 1：明天 2：后天， out：显示日期
        if (xcts >= -2 && xcts <= 2) {
            return String.valueOf(xcts);
        } else {
            return "out";
        }
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
        if (i < 7 && days[i] > 20) {
            viewHolder.date_item.setTextColor(Color.rgb(204, 204, 204));//将上个月的和下个月的设置为灰色
            viewHolder.date_item.setVisibility(View.INVISIBLE);
        } else if (i > 20 && days[i] < 15) {
            viewHolder.date_item.setTextColor(Color.rgb(204, 204, 204));
            viewHolder.date_item.setVisibility(View.INVISIBLE);
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
//            viewHolder.date_item.setEnabled(false);
//            viewHolder.date_item.setClickable(false);

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
//                viewHolder.date_item.setEnabled(false);
//                viewHolder.date_item.setClickable(false);
            }
        }
        //是否是本年本月显示的今天、明天、后天 days[i] == day && month == nowadayMonth
        if (getDate(year, month, days[i]).equals("0")) {
            viewHolder.date_item.setText("今天");
            viewHolder.date_item.setTextColor(context.getResources().getColor(R.color.bg_festival));
            viewHolder.date_item.getPaint().setFakeBoldText(true);
        } else if (getDate(year, month, days[i]).equals("1")) {
            viewHolder.date_item.setText("明天");
            viewHolder.date_item.getPaint().setFakeBoldText(true);
        } else if (getDate(year, month, days[i]).equals("2")) {
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
