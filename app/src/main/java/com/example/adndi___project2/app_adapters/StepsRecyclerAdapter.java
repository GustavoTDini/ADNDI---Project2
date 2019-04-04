package com.example.adndi___project2.app_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.adndi___project2.R;
import com.example.adndi___project2.database.RecipeSteps;

import java.util.List;

/**
 * Classe do Adapter que irá popular o RecyclerView do Fragment dos passos da receita
 */
public class StepsRecyclerAdapter extends RecyclerView.Adapter<StepsViewHolder> {

    // ClickHandler para definirmos a interface
    private final RecyclerAdapterOnClickHandler mClickHandler;

    private List<RecipeSteps> mSteps;

    // contexto atual
    private Context context;

    public StepsRecyclerAdapter(RecyclerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.steps_list_content, viewGroup, false);
        return new StepsViewHolder(view, mClickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder viewHolder, int position) {
        if (mSteps.get(position) != null) {
            String stepImageUrl = null;
            String stepOrder = String.valueOf(mSteps.get(position).getStepOrder() + 1);
            String stepShortDescription = mSteps.get(position).getStepShortDescription();
            if (!TextUtils.isEmpty(mSteps.get(position).getStepVideoUrl())) {
                stepImageUrl = mSteps.get(position).getStepVideoUrl();
            } else if (!TextUtils.isEmpty(mSteps.get(position).getStepThumbnailUrl())) {
                stepImageUrl = mSteps.get(position).getStepThumbnailUrl();
            }
            viewHolder.mStepOrderTextView.setText(stepOrder);
            viewHolder.mStepShortDescriptionTextView.setText(stepShortDescription);
            if (stepImageUrl == null) {
                viewHolder.mStepImageView.setVisibility(View.GONE);
            } else {
                Glide.with(context).load(stepImageUrl).thumbnail(0.1f).into(viewHolder.mStepImageView);
            }

        }
    }

    @Override
    public int getItemCount() {
        if (null == mSteps) return 0;
        return mSteps.size();
    }

    /**
     * setRecipeData define a lista dos passos da receita atual e avisa a mudança de dados
     *
     * @param stepsList lista que utilizaremos
     */
    public void setRecipeData(List<RecipeSteps> stepsList) {
        mSteps = stepsList;
        notifyDataSetChanged();
    }

}
