package com.tars.synthesis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tars.synthesis.R;

public class SynthesisActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener{

    private Toolbar mToolbar;
    private WebView mWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synthesis);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showData();
    }

    private void initView(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mWeb = (WebView) findViewById(R.id.web);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setOnMenuItemClickListener(this);

        WebSettings webSettings = mWeb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        mWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWeb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                // TODO Auto-generated method stub
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (R.id.action_about == item.getItemId()){
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
        }
        return false;
    }

    private void showData(){
        int id = getIntent().getIntExtra("ENTITY_ID",0);
        if (0 < id){
            String url = getString(R.string.interface_entity) + id;
            mWeb.loadUrl(url);
        }
    }
}
