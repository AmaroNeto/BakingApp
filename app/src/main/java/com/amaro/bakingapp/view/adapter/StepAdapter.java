package com.amaro.bakingapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amaro.bakingapp.R;
import com.amaro.bakingapp.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private Context mContext;
    private List<Step> mSteps;
    private ListItemClickListener mOnClickListener;

    public StepAdapter(Context context, List<Step> steps) {
        mContext = context;
        mSteps = steps;
    }

    @NonNull
    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.step_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapter.ViewHolder holder, int position) {
        Step step = mSteps.get(position);
        holder.position = position;
        holder.step = step;holder.title.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public interface ListItemClickListener {
        void onListItemClick(int position);
    }

    public void setOnListItemClick(ListItemClickListener listener) {
        mOnClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        Step step;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title);
        }

        @Override
        public void onClick(View v) {
            if(mOnClickListener != null) {
                mOnClickListener.onListItemClick(position);
            }
        }
    }
}
