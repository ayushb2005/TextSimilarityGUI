package com.example.c1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    private List<String> fileNames;

    public FileAdapter(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        holder.fileNameTextView.setText(fileNames.get(position));
    }

    @Override
    public int getItemCount() {
        return fileNames.size();
    }

    public static class FileViewHolder extends RecyclerView.ViewHolder {
        TextView fileNameTextView;

        public FileViewHolder(View itemView) {
            super(itemView);
            fileNameTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}

