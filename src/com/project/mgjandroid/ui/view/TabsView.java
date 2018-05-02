package com.project.mgjandroid.ui.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;

/***************************************
 * 项目名称：mgjandroid
 * 类描述：
 * 创建人：Mr_Lei
 * 创建时间：2018/3/12 11:44
 ***************************************/

public class TabsView extends LinearLayout {

    private int mSelectedColor = getResources().getColor(R.color.title_bar_bg);// 选中的字体颜色
    private int mNotSelectedColor = getResources().getColor(R.color.color_6);// 未选中的字体颜色

    private LinearLayout mTabsContainer;// 放置tab的容器
    private RelativeLayout mIndicator;// 指示器和底部横线

    private OnTabsItemClickListener listener;

    public TabsView(Context context) {
        this(context, null);
    }

    public TabsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        // 初始化容器
        mTabsContainer = new LinearLayout(getContext());
        mTabsContainer.setOrientation(HORIZONTAL);
        mTabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(mTabsContainer);
        // 初始化指示器
        mIndicator = new RelativeLayout(context);
        View view = new View(context);
        view.setBackgroundColor(getResources().getColor(R.color.title_bar_bg));
        view.setLayoutParams(new LayoutParams(80, 4));
        mIndicator.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));// 先任意设置宽度
        mIndicator.setGravity(Gravity.CENTER);
        mIndicator.addView(view);
        addView(mIndicator);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        resetIndicator();
    }

    /**
     * 重新设置指示器
     */
    private void resetIndicator() {
        int childCount = mTabsContainer.getChildCount();
        ViewGroup.LayoutParams layoutParams = mIndicator.getLayoutParams();
        if (childCount <= 0) {
            layoutParams.width = 0;
        } else {
            layoutParams.width = getWidth() / childCount;
        }
        mIndicator.setLayoutParams(layoutParams);
    }

    /**
     * 设置选项卡
     *
     * @param titles
     */
    public void setTabs(String... titles) {
        mTabsContainer.removeAllViews();
        if (titles != null) {
            for (int i = 0; i < titles.length; i++) {
                TextView textView = new TextView(getContext());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textView.setText(titles[i]);
                textView.setClickable(true);
                textView.setPadding(0, 28, 0, 28);
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
                textView.setTag(i);
                textView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int position = (Integer) v.getTag();
                        setCurrentTab(position, true);
                        if (listener != null) {
                            listener.onClick(v, position);
                        }
                    }
                });
                mTabsContainer.addView(textView);
            }
            // 初始化，默认选中第一个
            setCurrentTab(0, false);
            // 设置指示器
            post(new Runnable() {
                @Override
                public void run() {
                    // 设置指示器
                    resetIndicator();
                }
            });
        }
    }

    /**
     * 设置当前的tab
     *
     * @param position
     */
    public void setCurrentTab(int position, boolean anim) {
        int childCount = mTabsContainer.getChildCount();
        if (position < 0 || position >= childCount) {
            return;
        }
        // 设置每个tab的状态
        for (int i = 0; i < childCount; i++) {
            TextView childView = (TextView) mTabsContainer.getChildAt(i);
            if (i == position) {
                childView.setTextColor(mSelectedColor);
            } else {
                childView.setTextColor(mNotSelectedColor);
            }
        }
        // 指示器的移动
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIndicator, "x", position * mIndicator.getWidth());
        if (anim) {
            objectAnimator.setDuration(200).start();
        } else {
            objectAnimator.setDuration(0).start();
        }
    }

    /**
     * Tabs点击的监听事件
     *
     * @param listener
     */
    public void setOnTabsItemClickListener(OnTabsItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnTabsItemClickListener {
        void onClick(View view, int position);
    }
}
