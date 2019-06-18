package com.amaro.bakingapp.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amaro.bakingapp.R;
import com.amaro.bakingapp.model.Recipe;
import com.amaro.bakingapp.util.DesignUtils;
import com.amaro.bakingapp.util.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> mRecipes;
    private Context mContext;
    private ListItemClickListener mOnClickListener;

    public RecipeAdapter(Context context, @NonNull List<Recipe> recipes) {
        mRecipes = recipes;
        mContext = context;
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        holder.name.setText(recipe.getName());
        holder.recipe = recipe;

        if(!recipe.getImage().isEmpty()) {
            URL imageURL = NetworkUtils.parseToURL(recipe.getImage());
            Picasso.get()
                    .load(imageURL.toString())
                    .into(holder.image);
            holder.name.setTextColor(Color.WHITE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.image.setForeground(mContext.getDrawable(DesignUtils.getGradient(position)));
            holder.name.setTextColor(Color.WHITE);
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(Recipe recipe);
    }

    public void setOnListItemClick(ListItemClickListener listener) {
        mOnClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Recipe recipe;
        TextView name;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View v) {
            if(mOnClickListener != null) {
                mOnClickListener.onListItemClick(recipe);
            }
        }
    }
}
