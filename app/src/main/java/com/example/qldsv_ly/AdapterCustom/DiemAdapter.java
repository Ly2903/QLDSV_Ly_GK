package com.example.qldsv_ly.AdapterCustom;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qldsv_ly.Objects.ObjectDiem;
import com.example.qldsv_ly.R;

import java.util.List;
public class DiemAdapter extends RecyclerView.Adapter<DiemAdapter.DiemViewHolder> {


    private final List<ObjectDiem> mlistDiem;
    private final ItemClickListener mItemClick;



    public DiemAdapter(List<ObjectDiem> mListDiem, ItemClickListener mItemClick) {
        this.mlistDiem = mListDiem;
        this.mItemClick = mItemClick;
    }

    @NonNull
    @Override
    public DiemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ctltc_nhapdiem, parent, false);

        return new DiemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DiemViewHolder holder, int position) {
        ObjectDiem diem = mlistDiem.get(position);
        if (diem == null) {
            return;
        }
        holder.MaSV.setText(diem.getMaSV());
        holder.DiemCC.setText(String.valueOf(diem.getDiemCC()));
        holder.DiemGK.setText(String.valueOf(diem.getDiemGK()));
        holder.DiemCK.setText(String.valueOf(diem.getDiemCK()));
        holder.DiemTK.setText(String.valueOf(diem.getDiemTK()));

        holder.itemView.setOnClickListener(view -> {
            mItemClick.OnItemClick(view, diem, position);

        });
    }



    @Override
    public int getItemCount() {
        return mlistDiem.size();
    }

    public interface ItemClickListener {
        void OnItemClick(View view, ObjectDiem diem, int i);

    }

    public static class DiemViewHolder extends RecyclerView.ViewHolder {

        private final TextView MaSV, DiemCC, DiemGK, DiemCK, DiemTK;


        public DiemViewHolder(@NonNull View itemView) {
            super(itemView);
            MaSV = itemView.findViewById(R.id.maSV);
            DiemCC = itemView.findViewById(R.id.diemCC);
            DiemGK = itemView.findViewById(R.id.diemGK);
            DiemCK = itemView.findViewById(R.id.diemCK);
            DiemTK = itemView.findViewById(R.id.diemTK);

        }
    }
}
