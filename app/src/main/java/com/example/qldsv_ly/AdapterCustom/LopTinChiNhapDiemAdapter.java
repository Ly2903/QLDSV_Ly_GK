package com.example.qldsv_ly.AdapterCustom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qldsv_ly.Objects.ObjectLopTinChiNhapDiem;
import com.example.qldsv_ly.R;

import java.util.ArrayList;
import java.util.List;


public class LopTinChiNhapDiemAdapter extends RecyclerView.Adapter<LopTinChiNhapDiemAdapter.LTCNhapDiemViewHolder> {


    private final List<ObjectLopTinChiNhapDiem> mlistLTC;

    private final ItemClickListener mItemClick;


    public LopTinChiNhapDiemAdapter(List<ObjectLopTinChiNhapDiem> mListLTC, ItemClickListener mItemClick) {
        this.mlistLTC = mListLTC;
        this.mItemClick = mItemClick;
    }

    @NonNull
    @Override
    public LTCNhapDiemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loptinchi_nhapdiem, parent, false);

        return new LTCNhapDiemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LTCNhapDiemViewHolder holder, int position) {
        ObjectLopTinChiNhapDiem ltc = mlistLTC.get(position);
        if (ltc == null) {
            return;
        }
        holder.maLTC.setText(ltc.getMaLTC());
        holder.tenMH.setText(ltc.getTenMH());

        holder.itemView.setOnClickListener(view -> {
            mItemClick.OnItemClick(view, ltc, position);

        });
    }



    @Override
    public int getItemCount() {
        return mlistLTC.size();
    }

    public interface ItemClickListener {
        void OnItemClick(View view, ObjectLopTinChiNhapDiem ltc, int i);

    }

    public static class LTCNhapDiemViewHolder extends RecyclerView.ViewHolder {

        private final TextView maLTC, tenMH;


        public LTCNhapDiemViewHolder(@NonNull View itemView) {
            super(itemView);
            maLTC = itemView.findViewById(R.id.maLTC);
            tenMH = itemView.findViewById(R.id.tenMH);
        }
    }
}
