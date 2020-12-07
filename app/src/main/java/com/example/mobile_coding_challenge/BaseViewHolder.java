package com.example.mobile_coding_challenge;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder  {


    private int mCurrentPosition;
    private Context context;
    public ConstraintLayout constraintLayout;


    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.constraintLayout = itemView.findViewById(R.id.constraintLayout);
    }

    protected abstract void clear();

    public void onBind(int position) {
        mCurrentPosition = position;
        clear();
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public ConstraintLayout getConstraintLayout() {
        return constraintLayout;
    }
}
