package com.evan.arouter_share.business.share;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.evan.baselib.base.BaseActivity;
import com.evan.arouter_share.R;
import com.evan.arouter_share.sdk.ShareSdk;
import com.evan.baselib.base.BaseFragment;

@Route(path = "/share/share")
public class ShareActivity extends BaseActivity {
    @Autowired(name = "title")
    public String mTitle;
    @Autowired(name = "imgurl")
    public String mImgUrl;
    private ConstraintLayout clTitle;
    private TextView tvBack;
    private TextView tvTitle;
    private TextView tvShare;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        initView();
        initData();
    }
    private void initView(){
        clTitle = (ConstraintLayout) findViewById(R.id.cl_title);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvShare = (TextView) findViewById(R.id.tv_share);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSdk.getInstans().share();
            }
        });
    }
    private void initData(){
        String title = getIntent().getStringExtra("title");
        String imgurl = getIntent().getStringExtra("imgurl");
        if(TextUtils.isEmpty(imgurl)) return;
        tvTitle.setText(title);
        BaseFragment fragment = (BaseFragment) ARouter.getInstance().build("/read/readfragment")
                .withString("imgurl", imgurl)
                .navigation();
//        BaseFragment fragment = ReadFragment.newInstance(imgurl);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction tx=supportFragmentManager.beginTransaction();
        tx.add(R.id.fl_img,fragment,"ONE");
        tx.commit();
    }

}
