package com.tars.synthesis.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tars.synthesis.R;
import com.tars.synthesis.bean.Entity;

import java.util.ArrayList;

/**
 * Created by kyly on 2015/11/17.
 */
public class EntityAdapter extends RecyclerView.Adapter<EntityAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<Entity> entities;
    private OnItemClickListener mListener;
    private ImageLoader mLoader;

    public interface OnItemClickListener {
        void OnItemClick(int entityId);
    }

    public EntityAdapter(@NonNull Context context) {
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
        mLoader = ImageLoader.getInstance();
    }

    public void setData(ArrayList<Entity> data) {
        this.entities = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_entity, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Entity entity = entities.get(position);
        assert null != entity;
        if (null != entity.getImgPath() && 10 < entity.getImgPath().length()) {
            mLoader.displayImage(entity.getImgPath(), holder.entityImage);
        }
        holder.entityName.setText(entity.getViewName());
        holder.entityDesc.setText(null != entity.getShortDesc() ? entity.getShortDesc() : entity.getDetailDesc());
        holder.item.setTag(entity.getId());
        holder.item.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return null == entities ? 0:entities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView entityImage;
        public TextView entityName;
        public TextView entityDesc;
        public View item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.item = itemView;
            entityImage = (ImageView) itemView.findViewById(R.id.entityimage);
            entityName = (TextView) itemView.findViewById(R.id.entityname);
            entityDesc = (TextView) itemView.findViewById(R.id.entitydesc);
        }
    }

    @Override
    public void onClick(View v) {
        int id = (int) v.getTag();
        if (0 < id && null != mListener) {
            mListener.OnItemClick(id);
        }
    }
}
