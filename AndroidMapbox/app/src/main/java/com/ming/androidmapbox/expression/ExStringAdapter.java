package com.ming.androidmapbox.expression;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExStringAdapter extends RecyclerView.Adapter<ExStringAdapter.VH> {
    private Context context;
    private List<String> dataList;
    private AdapterView.OnItemClickListener onItemClickListener;

    public ExStringAdapter(Context context, List<String> list) {
        this.context = context;
        this.dataList = list;
    }

    @NonNull
    @Override
    public ExStringAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ExStringAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExStringAdapter.VH holder, int position) {
        holder.tvName.setText(dataList.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(null, null, holder.getLayoutPosition(), holder.getItemId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class VH extends RecyclerView.ViewHolder {
        private TextView tvName;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(android.R.id.text1);
        }
    }
}
