package com.tars.synthesis.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.tars.synthesis.R;
import com.tars.synthesis.utils.AutoUpgrade.AppUpdate;
import com.tars.synthesis.utils.AutoUpgrade.AppUpdateService;

public class AboutActivity extends BaseActivity {
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private Button mButton;
    private AppUpdateService mUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }

    private void initView(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
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
                checkUpdate();
            }
        });
    }

    private void checkUpdate(){

    }
}
