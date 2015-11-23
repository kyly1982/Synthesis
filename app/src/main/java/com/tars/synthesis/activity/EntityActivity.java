package com.tars.synthesis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.tars.synthesis.R;
import com.tars.synthesis.adapter.EntityAdapter;
import com.tars.synthesis.bean.Entity;
import com.tars.synthesis.bean.EntityList;
import com.tars.synthesis.bean.Page;
import com.tars.synthesis.utils.JsonCache;

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
    private int mType;
    private String mCategoryName;
    private String mEntityName;
    private String mUrl;
    private Gson mGson;
    private JsonCache mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getParameters();
        initView();
        mClient = new AsyncHttpClient();
        mGson = new Gson();
        mCache = new JsonCache(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCache.saveCacheFile();
    }

    private void getParameters(){
        Intent intent = getIntent();
        mType = intent.getIntExtra("TYPE", 2);
        switch (mType){
            case 0:
                mCategoryName = getIntent().getStringExtra("CATEGORY");
                mEntityName = null;
                break;
            case 1:
                mCategoryName = null;
                mEntityName = getIntent().getStringExtra("ENTIVITY");
                break;
            default:
                this.finish();
                break;
        }
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.apptitle);
        mList = (UltimateRecyclerView) findViewById(R.id.list);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntityActivity.this.finish();
            }
        });
        mTitle.setText(mCategoryName);

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



        final RequestParams params = new RequestParams();

        switch (mType){
            case 0:
                if (null == mPage) {
                    mPage = new Page();
                }
                if (null == mUrl) {
                    mUrl = getString(R.string.interface_domain) + getString(R.string.interface_getentitylist);
                }
                params.put("kinds", mCategoryName);
                break;
            case 1:
                if (null == mUrl){
                    mUrl = getString(R.string.interface_domain) + getString(R.string.interface_search);
                }
                params.put("cName", mEntityName);
                break;
            default:
                break;
        }



        mClient.get(mUrl, params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                parseData(null,null,getCacheData(mUrl,params));
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
                if (parseData(mUrl,params,data)){
                    showData();
                    return;
                }
                //处理返回数据不正确
                Snackbar.make(mList,"返回数据不正确，请稍候再试！",Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Snackbar.make(mList,"获取数据时失败，原因："+throwable.getLocalizedMessage(),Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private boolean parseData(String url,RequestParams params,String data){
        if (1 == mType){
            return parseSearchData(data);
        }
        EntityList list = mGson.fromJson(data, EntityList.class);
        if (null != list) {
            mPage.setPage(list.getPage());
            mPage.setAllCount(list.getAllCount());
            mPage.setPageSize(list.getPageSize());
            if (1 == mPage.getPage()) {
                if (null == mEntities){
                    mEntities = new ArrayList<>(20);
                } else {
                    mEntities.clear();
                }
                if (null != url && null != params){
                    cacheData(url,params,data);
                }
            }
            mEntities.addAll(list.getData());
            return true;
        }
        return false;
    }

    private boolean parseSearchData(String data){
        ArrayList<Entity> entities = mGson.fromJson(data,new TypeToken<ArrayList<Entity>>(){}.getType());
        if (null != entities && !entities.isEmpty()){
            this.mEntities = entities;
            return true;
        }
        return  false;
    }

    private void cacheData(String url,RequestParams params,String data){
        String key = url + "&" + params.toString();
        mCache.put(key, data);
    }

    private String getCacheData(String url,RequestParams params){
        String key = url + "&" + params.toString();
        return mCache.get(key);
    }



    @Override
    public void OnItemClick(Entity entity) {
        Intent intent = new Intent(this, SynthesisActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ENTITY",entity);
        intent.putExtras(bundle);
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
        if (!mPage.EOP()) {
            loadData();
        } else {
            Snackbar.make(mList,"已到最后一项！",Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        mPage.setPage(0);
        loadData();
    }
}
