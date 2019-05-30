package com.evan.arouter_read.business.readlist;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.evan.baselib.base.BaseActivity;
import com.evan.baselib.callback.EvanCallBack;
import com.evan.baselib.manager.RequestManager;
import com.evan.baselib.manager.ThreadPoolManager;
import com.evan.baselib.throwable.HttpError;
import com.evan.arouter_read.R;
import com.evan.arouter_read.bean.ReadListBean;

/**
 * 图片列表
 */
@Route(path = "/read/readlist")
public class ReadListActivity extends BaseActivity {
    private RecyclerView rv;
    private FrameLayout fl_layout;
    private ReadAdapter mAdapter;
    private ReadFragment mFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readlist);
        rv = findViewById(R.id.rv);
        fl_layout = findViewById(R.id.fl_layout);
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rv.setItemAnimator(null);
        rv.setLayoutManager(manager);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                manager.invalidateSpanAssignments(); //防止第一行到顶部有空白区域
            }
        });
        mAdapter = new ReadAdapter(this);
        mAdapter.setItemClick(new ReadAdapter.IItemClick() {
            @Override
            public void click(int position,ReadListBean.Item been) {
                mAdapter.setSelectItem(position);
                //挂载fragment
                if(mFragment == null){
                    mFragment = ReadFragment.newInstance(been.getImg());
                    FragmentManager supportFragmentManager = getSupportFragmentManager();
                    FragmentTransaction tx=supportFragmentManager.beginTransaction();
                    tx.add(R.id.fl_fragment,mFragment,"ONE");
                    tx.commit();
                    fl_layout.setVisibility(View.VISIBLE);
                }else{
                    mFragment.setData(been.getImg());
                    fl_layout.setVisibility(View.VISIBLE);
                }
            }
        });
        rv.setAdapter(mAdapter);
        findViewById(R.id.iv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/share/share")
                        .withString("title", mAdapter.getSelectBean().getName())
                        .withString("imgurl", mAdapter.getSelectBean().getImg())
                        .navigation();

            }
        });
        initData();
    }
    private void initData(){
        //获取数据
        RequestManager.getInstance().requestAsyn("read_list", RequestManager.RequestMethod.GET, null, new EvanCallBack<ReadListBean>() {
            @Override
            public void onSuccess(ReadListBean readListBean, String json) {
                mAdapter.setData(readListBean.getList());
            }

            @Override
            public void onError(HttpError error) {
                ThreadPoolManager.safeToast(error.msg);

            }
        });
    }

    public void goneFragment(){
        fl_layout.setVisibility(View.GONE);
    }

}
