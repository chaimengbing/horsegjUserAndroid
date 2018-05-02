package com.project.mgjandroid.ui.activity.carhailing;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.carhailing.CarHailing;
import com.project.mgjandroid.bean.carhailing.CarHailingTrip;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.Entity;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by User_Cjh on 2016/12/12.
 */
public class CarHailingListAdapter extends BaseListAdapter<CarHailingTrip> {

    private View.OnClickListener listener;

    public CarHailingListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void getRealView(ViewHolder holder, CarHailingTrip bean, int position, View convertView, ViewGroup parent) {
        ImageView image = holder.getView(R.id.car_hailing_list_header);
        TextView firstOrder = holder.getView(R.id.tv_first_order);
        FrameLayout imageFa = holder.getView(R.id.car_hailing_list_header_father);
        imageFa.setTag(position);
        imageFa.setOnClickListener(listener);
        if (CheckUtils.isNoEmptyStr(bean.getChauffeur().getHeaderImg())) {
            ImageUtils.loadBitmap(mActivity, bean.getChauffeur().getHeaderImg(), image, R.drawable.horsegj_default, Constants.getEndThumbnail(63, 63));
        } else {
            image.setImageResource(R.drawable.horsegj_default);
        }
        if (bean.getCarFirstOrder() != null) {
            firstOrder.setVisibility(View.VISIBLE);
            firstOrder.setText("新用户下单立减" + StringUtils.BigDecimal2Str(bean.getCarFirstOrder().getDiscountAmount()) + "元");
        } else {
            firstOrder.setVisibility(View.GONE);
        }
        Date leaveTime = bean.getLeaveTime();
        Date serviceTime = bean.getServiceTime();
        holder.setText(R.id.car_hailing_trip_start_time, CommonUtils.getTime(leaveTime, serviceTime));
        holder.setText(R.id.car_hailing_trip_car_type, bean.getChauffeur().getCarColor() + "·" + bean.getChauffeur().getCarSeries());
        holder.setText(R.id.car_hailing_trip_get_on_place, bean.getStartCityName() + bean.getStartDistrictName());
//        holder.setText(R.id.car_hailing_trip_get_on_place_default,bean.getStartAddress());
        holder.setText(R.id.car_hailing_trip_get_off_place, bean.getEndCityName() + bean.getEndDistrictName());
//        holder.setText(R.id.car_hailing_trip_get_off_place_default,bean.getEndAddress());
        BigDecimal minPrice = bean.getChauffeurTripDetailList().get(0).getPrice();
        for (CarHailing carHailing : bean.getChauffeurTripDetailList()) {
            if (minPrice.compareTo(carHailing.getPrice()) > 0) {
                minPrice = carHailing.getPrice();
            }
        }
        if (new BigDecimal(minPrice.intValue()).compareTo(minPrice) == 0) {
            holder.setText(R.id.car_hailing_trip_price, minPrice.intValue() + "元");
        } else {
            holder.setText(R.id.car_hailing_trip_price, minPrice + "元");
        }
        if (bean.getType() == 1) {
            holder.setText(R.id.car_hailing_trip_type_show, "剩余");
            holder.setText(R.id.car_hailing_trip_type, "拼车：");
//            holder.setText(R.id.car_hailing_trip_price, minPrice+"元");
            holder.setText(R.id.car_hailing_trip_price_show, "/人起");
        } else if (bean.getType() == 2) {
            holder.setText(R.id.car_hailing_trip_type_show, "共");
            holder.setText(R.id.car_hailing_trip_type, "包车：");
//            holder.setText(R.id.car_hailing_trip_price, minPrice+"元");
            holder.setText(R.id.car_hailing_trip_price_show, "起");
        } else {
            holder.setText(R.id.car_hailing_trip_type_show, "共");
            holder.setText(R.id.car_hailing_trip_type, "");
//            holder.setText(R.id.car_hailing_trip_price, minPrice+"元");
            holder.setText(R.id.car_hailing_trip_price_show, "/人起");
        }
        holder.setText(R.id.car_hailing_trip_seat, bean.getPeopleNum() - bean.getCurrentPeopleNum() + "");
    }
}
