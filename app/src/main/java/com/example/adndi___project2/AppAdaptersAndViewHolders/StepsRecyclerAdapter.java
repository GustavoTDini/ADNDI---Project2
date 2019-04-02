package com.example.adndi___project2.AppAdaptersAndViewHolders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.adndi___project2.DataBase.RecipeSteps;
import com.example.adndi___project2.R;

import java.util.List;

public class StepsRecyclerAdapter extends RecyclerView.Adapter<StepsViewHolder> {

    // ClickHandler para definirmos a interface
    private final RecyclerAdapterOnClickHandler mClickHandler;

    private List<RecipeSteps> mSteps;

    // contexto atual
    private Context context;


    /**
     * Construtor da classe
     *
     * @param clickHandler interface para definirmos a função do click
     */
    public StepsRecyclerAdapter(RecyclerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * onCreateViewHolder, cria os varios viewHolders que irão
     *
     * @param viewGroup o ViewGroup que contem esta viewHolder
     * @param i         tipo do view utilizado, neste caso não é utilizado, pois só temos 1 tipo de view
     * @return o ViewHolder criado
     */
    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.steps_list_content, viewGroup, false);
        return new StepsViewHolder(view, mClickHandler);
    }

    /**
     * onBindViewHolder, povoa os MovieViewHolder com as Informações do MovieData
     *
     * @param viewHolder viewHolder a ser povoado, neste caso um MovieViewHOlder
     * @param position   posição do MovieData da list
     */
    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder viewHolder, int position) {
        if (mSteps.get(position) != null) {
            String stepImageUrl = null;
            String stepOrder = String.valueOf(mSteps.get(position).getStepOrder());
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

    /**
     * getItemCount, retorna o tamanho da lista
     *
     * @return int com o tamanho
     */
    @Override
    public int getItemCount() {
        if (null == mSteps) return 0;
        return mSteps.size();
    }

    /**
     * setRecipeData define qual lista queremos usar no MovieGridAdapter
     *
     * @param stepsList lista que utilizaremos
     */
    public void setRecipeData(List<RecipeSteps> stepsList) {
        mSteps = stepsList;
        notifyDataSetChanged();
    }

}
