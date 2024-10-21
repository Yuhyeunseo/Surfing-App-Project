package com.example.day0528_2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoppedTimeAdapter extends RecyclerView.Adapter<StoppedTimeAdapter.StoppedTimeViewHolder> {

    private ArrayList<String> stoppedTimeList;

    // 생성자에서 ArrayList를 받음
    public StoppedTimeAdapter(ArrayList<String> stoppedTimeList) {
        this.stoppedTimeList = stoppedTimeList;
    }

    @NonNull
    @Override
    public StoppedTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 레이아웃을 인플레이트하여 ViewHolder에 전달
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new StoppedTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoppedTimeViewHolder holder, int position) {
        // 기록 데이터를 텍스트 뷰에 설정
        String stoppedTime = stoppedTimeList.get(position);
        holder.stoppedTimeTextView.setText(stoppedTime);
    }

    @Override
    public int getItemCount() {
        // 기록 리스트의 아이템 개수를 반환
        return stoppedTimeList.size();
    }

    // ViewHolder 클래스
    public static class StoppedTimeViewHolder extends RecyclerView.ViewHolder {
        public TextView stoppedTimeTextView;

        public StoppedTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            // 레이아웃에서 텍스트뷰를 찾음
            stoppedTimeTextView = itemView.findViewById(android.R.id.text1);
        }
    }

    // addTime 메서드 추가
    public void addTime(String newTime) {
        stoppedTimeList.add(newTime);  // 리스트에 새로운 시간을 추가
        notifyItemInserted(stoppedTimeList.size() - 1);  // 어댑터에 데이터가 변경되었음을 알림
    }
}
