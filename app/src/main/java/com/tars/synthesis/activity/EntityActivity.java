package com.tars.synthesis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.tars.synthesis.R;
import com.tars.synthesis.adapter.EntityAdapter;
import com.tars.synthesis.bean.Entity;
import com.tars.synthesis.bean.EntityList;
import com.tars.synthesis.bean.Page;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;

public class EntityActivity extends BaseActivity implements EntityAdapter.OnItemClickListener, Toolbar.OnMenuItemClickListener, UltimateRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private Toolbar mToolbar;
    private UltimateRecyclerView mList;
    private TextView mTitle;

    private ArrayList<Entity> mEntities;
    private EntityAdapter mAdapter;
    private AsyncHttpClient mClient;
    private Page mPage;
    private String mCategory;
    private String mUrl;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mCategory = getIntent().getStringExtra("CATEGORY");
        initView();
        mClient = new AsyncHttpClient();
        mGson = new Gson();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showData();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.title);
        mList = (UltimateRecyclerView) findViewById(R.id.list);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitle.setText(mCategory);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mList.setLayoutManager(manager);
        mToolbar.setOnMenuItemClickListener(this);

        mList.enableLoadmore();
        mList.enableDefaultSwipeRefresh(true);
        mList.setOnLoadMoreListener(this);
        mList.setDefaultOnRefreshListener(this);

    }

    private void showData() {
        if (null == mEntities) {
            loadData();
            mAdapter = new EntityAdapter(this);
            mAdapter.setOnItemClickListener(this);
            mList.setAdapter(mAdapter);
            return;
        } else {
            mAdapter.setData(mEntities);
        }
    }

    private void loadData() {


        if (null == mUrl) {
            mUrl = getString(R.string.interface_domain) + getString(R.string.interface_getentitylist);
        }

        RequestParams params = new RequestParams();
        if (null == mPage) {
            mPage = new Page();
        }
        params.put("page", mPage.getNextPage());
        params.put("kinds", mCategory);

        mClient.get(mUrl, params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                String data = null;
                if (null == mEntities) {
                    mEntities = new ArrayList<Entity>(20);
                }
                if (null != response && 10 < response.toString().length()) {
                    try {
                        if (response.has("dataObject") && response.has("state") && response.getString("state").equals("ok")) {
                            data = response.getString("dataObject");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        data = null;
                    }
                }
                if (null != data && 10 < data.length()) {
                    EntityList list = mGson.fromJson(data, EntityList.class);
                    if (null != list) {
                        mPage.setPage(list.getPage());
                        mPage.setAllCount(list.getAllCount());
                        mPage.setPageSize(list.getPageSize());
                        if (1 == mPage.getPage()) {
                            mEntities.clear();
                        }
                        mEntities.addAll(list.getData());
                        showData();
                        return;
                    }
                }
                //处理返回数据不正确
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }


    @Override
    public void OnItemClick(int entityId) {
        Intent intent = new Intent(this, SynthesisActivity.class);
        intent.putExtra("ENTITY_ID",entityId);
        startActivity(intent);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (R.id.action_about == item.getItemId()) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public void loadMore(int itemsCount, int maxLastVisiblePosition) {
        loadData();
    }

    @Override
    public void onRefresh() {
        mPage.setPage(0);
        loadData();
    }
}
