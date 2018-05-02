package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.NewOrderTypeModel;
import com.project.mgjandroid.utils.MLog;

import java.util.List;
import java.util.Map;

import static com.project.mgjandroid.utils.DialogUtil.context;

/**
 * Created by SunXueLiang on 2018-01-04.
 */

public class OrderTypeGridAdapter extends BaseAdapter {

    private Context context;
    private List<Map<Integer, String>> list;
    private int selectTypeId;
    private int currentType;
    private String name;

    public OrderTypeGridAdapter(Context context, List<Map<Integer, String>> list, int typeId) {
        this.context = context;
        this.list = list;
        this.selectTypeId = typeId;
    }

    public void setSelectTypeId(int typeId) {
        if (typeId == selectTypeId) {
            return;
        }
        this.selectTypeId = typeId;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Map<Integer, String> getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_order_type_gride_view, null);
            holder.tvName = (TextView) view.findViewById(R.id.order_type_text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        currentType = 0;
        name = "";
        for (Integer s : getItem(i).keySet()) {
            currentType = s;
            name = getItem(i).get(s);
        }
        holder.tvName.setText(name);
        if (selectTypeId == currentType) {
            holder.tvName.setBackgroundResource(R.drawable.shap_orange_range_bg);
            holder.tvName.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.tvName.setBackgroundResource(R.drawable.shap_gray_range_bg);
            holder.tvName.setTextColor(context.getResources().getColor(R.color.color_3));
        }
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MLog.e("点击了---"+ currentType +"---"+ name);
//            }
//        });

        return view;
    }

    static class ViewHolder {
        TextView tvName;
    }
}
