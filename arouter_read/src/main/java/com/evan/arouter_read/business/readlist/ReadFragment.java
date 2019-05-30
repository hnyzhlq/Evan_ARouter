package com.evan.arouter_read.business.readlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.evan.baselib.base.BaseFragment;
import com.evan.baselib.utils.imageutil.ImageUtils;
import com.evan.arouter_read.R;

/**
 * 展示图片详情的fragment
 */
@Route(path = "/read/readfragment")
public class ReadFragment extends BaseFragment {
    @Autowired(name = "imgurl")
    public String mImgUrl;
    private ImageView mIv;

    public static ReadFragment newInstance(String imgurl) {
        Bundle args = new Bundle();
        args.putString("imgurl", imgurl);
        ReadFragment fragment = new ReadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        initArguments();
        initView(view);
        return view;
    }

    private void initArguments() {
        if (getArguments() == null) return;
        mImgUrl = getArguments().getString("imgurl");
    }

    private void initView(View view){
        mIv = view.findViewById(R.id.iv);
        showImg();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();
                if(activity instanceof ReadListActivity)
                ((ReadListActivity)activity).goneFragment();
            }
        });

    }

    public void setData(String imgurl){
        mImgUrl = imgurl;
        showImg();
    }
    private void showImg(){
        if(!TextUtils.isEmpty(mImgUrl)){
            ImageUtils.loadRoundImage(mImgUrl,mIv,12);
        }
    }
}
