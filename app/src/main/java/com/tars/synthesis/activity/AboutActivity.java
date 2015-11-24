package com.tars.synthesis.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tars.synthesis.BuildConfig;
import com.tars.synthesis.R;
import com.tars.synthesis.utils.AutoUpgrade.AppUpdate;
import com.tars.synthesis.utils.AutoUpgrade.AppUpdateService;
import com.tars.synthesis.utils.AutoUpgrade.ResponseParser;
import com.tars.synthesis.utils.AutoUpgrade.Version;
import com.tars.synthesis.utils.AutoUpgrade.internal.SimpleJSONParser;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.URLEncoder;

public class AboutActivity extends BaseActivity {
    private Toolbar mToolbar;
    private TextView appver;
//    private ActionBar mActionBar;
//    private Button mButton;
    private AppUpdate mUpdateService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mUpdateService = AppUpdateService.getAppUpdate(this);
        initView();
        PushAgent.getInstance(this).onAppStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUpdateService.callOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUpdateService.callOnPause();
    }

    private void initView(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        appver = (TextView) findViewById(R.id.appversion);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.this.finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.checkUpgrade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUpgrade();
            }
        });
        appver.setText(BuildConfig.VERSION_NAME);
    }

    private void checkUpgrade() {
        MobclickAgent.onEvent(this,"update_click");
        String url = getString(R.string.interface_domain) + getString(R.string.interface_checkupgread);
        url = url + "&pushMan=" + URLEncoder.encode(getString(R.string.channel));
        mUpdateService.checkLatestVersion(url, new MyJsonParser());
    }

    static class MyJsonParser extends SimpleJSONParser implements ResponseParser {
        @Override
        public Version parser(String response) {
            try {
                JSONTokener jsonParser = new JSONTokener(response);
                JSONObject json = (JSONObject) jsonParser.nextValue();
                Version version = null;
                if (json.has("state") && json.has("dataObject")) {
                    JSONObject dataField = json.getJSONObject("dataObject");
                    int code = dataField.getInt("code");
                    String name = dataField.getString("name");
                    String feature = dataField.getString("feature");
                    String targetUrl = dataField.getString("targetUrl");
                    version = new Version(code, name, feature, targetUrl);
                }
                return version;
            } catch (JSONException exp) {
                exp.printStackTrace();
                return null;
            }
        }
    }
}
