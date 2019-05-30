package com.evan.arouter_read.business.readlist;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.evan.baselib.utils.UIUtils;
import com.evan.baselib.utils.imageutil.GlideApp;
import com.evan.arouter_read.R;
import com.evan.arouter_read.bean.ReadListBean;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ReadAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private IItemClick mClick;
    private int mSelectItem = 0;
    private HashMap<Integer, Integer> mHeights = new HashMap<>();
    private List<ReadListBean.Item> mData = new LinkedList<ReadListBean.Item>();

    public ReadAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<ReadListBean.Item> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_readlist, parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (!(viewHolder instanceof ItemHolder))
            return;
        final ItemHolder itemHolder = (ItemHolder) viewHolder;
        final ReadListBean.Item bean = mData.get(i);
        itemHolder.tv.setText(bean.getName());
        //加载图片视图，计算item高度，优化流布局显示体验
        Integer integer = mHeights.get(i);
        if (integer == null || integer==0) {
            GlideApp.with(mContext)
                    .asBitmap()
                    .load(bean.getImg())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            int width = bitmap.getWidth();
                            int height = bitmap.getHeight();
                            if (mHeights.get(i) == null) {
                                height = (int)(height / ((float) width / (UIUtils.getScreenWidth() / 2)));
                                mHeights.put(i,height);
                            }
                            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) itemHolder.view.getLayoutParams();
                            params.height = height;
                            itemHolder.view.setLayoutParams(params);
                            itemHolder.iv.setImageBitmap(bitmap);
                        }
                    });
        }else{
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) itemHolder.view.getLayoutParams();
            params.height = integer;
            itemHolder.view.setLayoutParams(params);
            GlideApp.with(mContext)
                    .load(bean.getImg())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(itemHolder.iv);
        }
        itemHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClick != null){
                    mClick.click(i,bean);
                }
            }
        });
    }
    public void setSelectItem(int selectItem){
        mSelectItem = selectItem;
    }
    public ReadListBean.Item getSelectBean(){
        if(mData == null || mData.size() == 0) return null;
        return mData.get(mSelectItem);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItemClick(IItemClick click) {
        mClick = click;
    }

    public interface IItemClick {
        void click(int position,ReadListBean.Item been);
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView iv;
        public TextView tv;

        public ItemHolder(View view) {
            super(view);
            this.view = view;
            iv = view.findViewById(R.id.iv);
            tv = view.findViewById(R.id.tv);
        }
    }
}
