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
    Date today = new Date();

    public DateAdapter(Context context, int[][] days, int year, int month) {
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
        if(viewHolder.date_item.getVisibility()== View.VISIBLE){
            view.setEnabled(false);
            view.setClickable(false);
        }else {
            view.setEnabled(true);
            view.setClickable(true);
        }
        viewHolder.date_item.setText(days[i] + "");

        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int nowadayMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int nowadayYear = Calendar.getInstance().get(Calendar.YEAR);
        //是否是本年本月显示的今天、明天、后天
        if (day == days[i] && nowadayMonth == month && nowadayYear == year) {
            viewHolder.date_item.setText("今天");
        } else if (day == days[i] - 1 && nowadayMonth == month && nowadayYear == year) {
            viewHolder.date_item.setText("明天");
        } else if (day == days[i] - 2 && nowadayMonth == month && nowadayYear == year) {
            viewHolder.date_item.setText("后天");
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
