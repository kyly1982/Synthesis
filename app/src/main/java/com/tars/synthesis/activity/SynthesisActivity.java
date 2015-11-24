package com.tars.synthesis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.tars.synthesis.R;
import com.tars.synthesis.bean.Entity;
import com.umeng.message.PushAgent;

import java.util.Scanner;

public class SynthesisActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener{

    private Toolbar mToolbar;
    private WebView mWeb;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synthesis);
        initView();
        PushAgent.getInstance(this).onAppStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showData();
    }

    private void initView(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mWeb = (WebView) findViewById(R.id.web);
        mTitle = (TextView) findViewById(R.id.apptitle);
        setSupportActionBar(mToolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SynthesisActivity.this.finish();
            }
        });

            WebSettings webSettings = mWeb.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setSupportMultipleWindows(true);
            mWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            mWeb.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
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
        Entity entity = (Entity)getIntent().getSerializableExtra("ENTITY");
        if (null != entity){
//            String url = "http://192.168.99.247/MineCraftCDN/app-composed.html";
//            Toast.makeText(this,"ID="+entity.getId(),Toast.LENGTH_LONG).show();
            String url = getString(R.string.interface_entity) + entity.getId();
            mWeb.loadUrl(url);
            mTitle.setText(entity.getViewName());
        } else {
            Snackbar.make(mWeb,"参数错误，请重试！",Snackbar.LENGTH_LONG).show();
        }
    }
}
