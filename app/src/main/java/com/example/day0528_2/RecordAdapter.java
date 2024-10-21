package com.example.day0528_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {

    private ArrayList<String> recordList;

    // 생성자에서 ArrayList를 받도록 수정
    public RecordAdapter(ArrayList<String> recordList) {
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 레이아웃을 인플레이트하여 ViewHolder에 전달
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        // 기록 데이터를 텍스트 뷰에 설정
        String record = recordList.get(position);
        holder.recordTextView.setText(record);
    }

    @Override
    public int getItemCount() {
        // 기록 리스트의 아이템 개수를 반환
        return recordList.size();
    }

    // ViewHolder 클래스
    public static class RecordViewHolder extends RecyclerView.ViewHolder {
        public TextView recordTextView;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            // 레이아웃에서 텍스트뷰를 찾음
            recordTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}
