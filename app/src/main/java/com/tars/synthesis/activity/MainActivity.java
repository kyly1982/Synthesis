package com.tars.synthesis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.tars.synthesis.R;
import com.tars.synthesis.adapter.CategoryAdapter;
import com.tars.synthesis.bean.Category;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements CategoryAdapter.OnItemClickListener,Toolbar.OnMenuItemClickListener{
    private Toolbar mToolbar;
    private UltimateRecyclerView mList;
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
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        mList = (UltimateRecyclerView) findViewById(R.id.list);
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mList.setLayoutManager(manager);
        mList.disableLoadmore();
        mList.enableDefaultSwipeRefresh(false);
        mToolbar.setOnMenuItemClickListener(this);
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

    @Override
    public void OnItemClick(int position) {
        Category category = mCategories.get(position);
        Intent intent = new Intent(this,EntityActivity.class);
        intent.putExtra("CATEGORY",category.getName());
        startActivity(intent);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (R.id.action_about == item.getItemId()){
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
        }
        return false;
    }
}
