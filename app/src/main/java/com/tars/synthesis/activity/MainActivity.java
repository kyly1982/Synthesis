package com.tars.synthesis.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.tars.synthesis.R;
import com.tars.synthesis.adapter.CategoryAdapter;
import com.tars.synthesis.bean.Category;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements CategoryAdapter.OnItemClickListener,Toolbar.OnMenuItemClickListener,TextView.OnEditorActionListener,View.OnFocusChangeListener{
    private Toolbar mToolbar;
    private UltimateRecyclerView mList;
    private TextInputLayout mSearchLayout;
    private EditText mSearch;
    private TextView mTitle;

    private ArrayList<Category> mCategories;
    private CategoryAdapter mAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showData();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mList = (UltimateRecyclerView) findViewById(R.id.list);
        mSearchLayout = (TextInputLayout) findViewById(R.id.search);
        mSearch = mSearchLayout.getEditText();
        mTitle = (TextView) findViewById(R.id.title);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mToolbar.setOnMenuItemClickListener(this);

        mList.setLayoutManager(manager);
        mList.disableLoadmore();
        mList.enableDefaultSwipeRefresh(false);
        mSearchLayout.setVisibility(View.VISIBLE);
        mSearch.setOnEditorActionListener(this);
        mSearch.setOnFocusChangeListener(this);
    }

    public void showData(){
        if (null == mCategories){
            getData();
        }
        if (null == mAdapter){
            mAdapter = new CategoryAdapter(this);
            mAdapter.setOnItemClickListener(this);
            mList.setAdapter(mAdapter);
            mAdapter.setData(mCategories);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void getData(){
        mCategories = new ArrayList<>(9);
        String[] name = {"基础材料","食物配方","酿造配方","建筑方块配方","工具配方","武器配方","材料配方","杂项配方","装饰方块配方"};
        int[] image = {R.mipmap.material_base,R.mipmap.formula_foods,R.mipmap.formula_brewing,
                R.mipmap.formula_cons,R.mipmap.formula_tool,R.mipmap.formula_weapon,
                R.mipmap.formula_material,R.mipmap.formula_other,R.mipmap.formula_decorate};
        if (null != name && null != image && image.length == name.length){
            for (int i = 0;i < image.length;i++){
                Category category = new Category(image[i],name[i]);
                mCategories.add(category);
            }
        }
    }

    private void showEntityList(String categoryName,String entityName){
        onFocusChange(mSearch,false);
        Intent intent = new Intent(this,EntityActivity.class);
        if (null != categoryName){
            intent.putExtra("CATEGORY",categoryName);
        } else if (null != entityName){
            intent.putExtra("ENTIVITY", entityName);
        }
        startActivity(intent);
    }

    @Override
    public void OnItemClick(int position) {
        Category category = mCategories.get(position);
        if (null != category){
            showEntityList(category.getName(),null);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (R.id.action_about == item.getItemId()){
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH ){
            if (null != v.getText() && 0 < v.getText().toString().length()){
                showEntityList(null,v.getText().toString().trim());
            } else {
                mSearchLayout.setError("请输入要搜索的物品名称！");
                mSearch.setText("");
            }
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mSearchLayout.setHint("点击键盘上的搜索来搜索");
        if (hasFocus){
            imm.showSoftInput(mSearch,InputMethodManager.SHOW_FORCED);
        } else {
            imm.hideSoftInputFromWindow(mSearch.getWindowToken(),0);
        }
    }
}
