package com.tars.synthesis.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tars.synthesis.R;
import com.tars.synthesis.bean.Category;

import java.util.ArrayList;

/**
 * Created by kyly on 2015/11/17.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<Category> categories;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public CategoryAdapter(@NonNull Context context){
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public void setData(ArrayList<Category> data){
        this.categories = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_category,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category = categories.get(position);
        assert null != category;
        Drawable drawable =mContext.getResources().getDrawable(category.getImageId());
        holder.CategoryImage.setImageDrawable(drawable);
        holder.CategoryName.setText(category.getName());
        holder.item.setTag(position);
        holder.item.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return null == categories ? 0:categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView CategoryImage;
        public TextView CategoryName;
        public View item;

        public ViewHolder(@NonNull View itemView){
        super(itemView);
            this.item = itemView;
            CategoryImage = (ImageView) itemView.findViewById(R.id.categoryimage);
            CategoryName = (TextView) itemView.findViewById(R.id.categoryname);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (null != mListener){
            mListener.OnItemClick(position);
        }
    }
}
