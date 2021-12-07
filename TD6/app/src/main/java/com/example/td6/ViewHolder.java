package com.example.td6;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView repoName;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        repoName = itemView.findViewById(R.id.repoName);
    }
}
