package com.project.mgjandroid.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.mgjandroid.model.Entity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by User_Cjh on 2016/5/23.
 */
public abstract class BaseRecyclerAdapter<T extends Entity> extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mDatas = new LinkedList<T>();
    public OnItemClickListener mOnItemClickListener;
    public OnItemLongClickListener mOnItemLongClickListener;
    protected int layoutId;

    public BaseRecyclerAdapter(Context context, int layoutId) {
        mContext = context;
        this.layoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<T> mDatas) {
        if (mDatas != null) {
            this.mDatas = mDatas;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null) {
            mOnItemLongClickListener.onItemLongClick(v, (int) v.getTag());
            return true;
        }
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mDatas.size() > 0) {
            count = mDatas.size();
        }
        return count;
    }

    public void remove(int position) {
        mDatas.remove(position);
    }

    public void removeAll() {
        mDatas.clear();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getData() {
        return mDatas;
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    // 点击事件接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.itemView.setTag(position);
        newBindViewHolder(holder, position, mDatas.get(position));
    }

    protected abstract void newBindViewHolder(BaseViewHolder holder, int position, T bean);

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(layoutId, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new BaseViewHolder(view);
    }

}
