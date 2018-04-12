package com.example.android.bakingapp.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private List<Step> mSteps;
    private OnStepClicked mOnStepClicked;
    @NonNull
    @Override
    public StepAdapter.StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item_sample,parent,false);
        return new StepViewHolder(view);
    }

    public StepAdapter(List<Step> steps, OnStepClicked onStepClicked) {
        mSteps = steps;
        mOnStepClicked = onStepClicked;
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapter.StepViewHolder holder, int position) {
        Step step = mSteps.get(position);
        String shortDescription = step.getShortDescription();
        holder.stepDescription.setText(shortDescription);

    }
    public interface OnStepClicked{
        void onClicked(Step step);
    }

    @Override
    public int getItemCount() {
        if (mSteps == null)return 0;
        return mSteps.size();

    }
    public void addSteps(List<Step> steps){
        mSteps = steps;
        notifyDataSetChanged();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stepDescription;
        public StepViewHolder(View itemView) {
            super(itemView);
            stepDescription = itemView.findViewById(R.id.short_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Step step = mSteps.get(position);
            mOnStepClicked.onClicked(step);

        }
    }
}
