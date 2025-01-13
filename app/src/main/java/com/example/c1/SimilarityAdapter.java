package com.example.c1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SimilarityAdapter extends RecyclerView.Adapter<SimilarityAdapter.SimilarityViewHolder> {
    private List<String> similarityData;

    public SimilarityAdapter(List<String> similarityData) {
        this.similarityData = similarityData;
    }

    @NonNull
    @Override
    public SimilarityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.similarity_item, parent, false);
        return new SimilarityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarityViewHolder holder, int position) {
        holder.similarityTextView.setText(similarityData.get(position));
    }

    @Override
    public int getItemCount() {
        return similarityData.size();
    }

    public static class SimilarityViewHolder extends RecyclerView.ViewHolder {
        TextView similarityTextView;

        public SimilarityViewHolder(View itemView) {
            super(itemView);
            similarityTextView = itemView.findViewById(R.id.similarityTextView);
        }
    }
}
