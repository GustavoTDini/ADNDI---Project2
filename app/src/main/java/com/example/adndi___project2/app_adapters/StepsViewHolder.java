package com.example.adndi___project2.app_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adndi___project2.R;

/**
 * Classe de ViewHolder que irá definir as view da RecyclerView da lista de passos
 * - implementa o onClickListener para definir a ação de clique
 */
public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    final TextView mStepOrderTextView;
    final ImageView mStepImageView;
    final TextView mStepShortDescriptionTextView;

    private RecyclerAdapterOnClickHandler mListener;

    StepsViewHolder(View view, RecyclerAdapterOnClickHandler clickListener) {
        super(view);
        mStepOrderTextView = view.findViewById(R.id.tv_step_list_order);
        mStepImageView = view.findViewById(R.id.iv_step_list_image);
        mStepShortDescriptionTextView = view.findViewById(R.id.tv_step_list_short_description);
        mListener = clickListener;
        view.setOnClickListener(this);
    }

    /**
     * Método a ser chamado em um click em uma view
     *
     * @param view view que foi clicada
     */
    @Override
    public void onClick(View view) {
        mListener.onClick(getAdapterPosition());
    }

}
