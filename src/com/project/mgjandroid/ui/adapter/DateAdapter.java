package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.mgjandroid.R;

import java.util.Calendar;
import java.util.Date;

public class DateAdapter extends BaseAdapter {
    private int[] days = new int[42];
    private Context context;
    private int year;
    private int month;
    private int bespeakDays;

    private int nextCount = -1;
    Date today = new Date();
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

    public void setSeclection(int position,int clickMonth) {
        clickTemp = position;
        this.clickMonth = clickMonth;
        notifyDataSetChanged();
    }

    public void setData(int[][] days, int year, int month, int bespeakDays){
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
//        if (viewHolder.date_item.getVisibility() == View.VISIBLE) {
//            view.setEnabled(false);
//            view.setClickable(false);
//        } else {
//            view.setEnabled(true);
//            view.setClickable(true);
//        }
        viewHolder.date_item.setText(days[i] + "");

        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int nowadayMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int nowadayYear = Calendar.getInstance().get(Calendar.YEAR);

        int count = day + 20;
        if (days[i] >= day && days[i] < count && nowadayMonth == month) {
            viewHolder.date_item.setBackgroundColor(context.getResources().getColor(R.color.color_f5));
            viewHolder.date_item.setTextColor(context.getResources().getColor(R.color.color_3));
            viewHolder.date_item.setEnabled(false);
            viewHolder.date_item.setClickable(false);
            int max = 0;
            if (viewHolder.date_item.getVisibility() == View.VISIBLE){
                if (max < days[i]){
                    max = days[i];
                }
                max = days[i];

            }
            nextCount = count - max + 1;
        }
        int mMonth = nowadayMonth + 1;
        if(mMonth==13){
            mMonth =1;
        }
        if (nextCount > 0 && month == mMonth){
            if (days[i] < nextCount){
                viewHolder.date_item.setBackgroundColor(context.getResources().getColor(R.color.color_f5));
                viewHolder.date_item.setTextColor(context.getResources().getColor(R.color.color_3));
                viewHolder.date_item.setEnabled(false);
                viewHolder.date_item.setClickable(false);
            }
        }
        //是否是本年本月显示的今天、明天、后天
        if (day == days[i] && nowadayMonth == month && nowadayYear == year) {
            viewHolder.date_item.setText("今天");
            viewHolder.date_item.setTextColor(context.getResources().getColor(R.color.bg_festival));
            viewHolder.date_item.getPaint().setFakeBoldText(true);
        } else if (day == days[i] - 1 && nowadayMonth == month && nowadayYear == year) {
            viewHolder.date_item.setText("明天");
            viewHolder.date_item.getPaint().setFakeBoldText(true);
        } else if (day == days[i] - 2 && nowadayMonth == month && nowadayYear == year) {
            viewHolder.date_item.setText("后天");
            viewHolder.date_item.getPaint().setFakeBoldText(true);
        }
        if (clickTemp == i && clickMonth == month ) {
            viewHolder.date_item.setBackgroundColor(context.getResources().getColor(R.color.bg_festival));
            viewHolder.date_item.setTextColor(context.getResources().getColor(R.color.white));
        } else {
//            layout.setBackgroundColor(Color.TRANSPARENT);
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
