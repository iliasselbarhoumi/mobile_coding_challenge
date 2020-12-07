package com.example.mobile_coding_challenge;

import android.content.Context;
import android.view.View;

import butterknife.ButterKnife;

public class ProgressHolder extends BaseViewHolder {

    private Context context;

    ProgressHolder(Context context , View itemView) {
        super(context,itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {
    }
}
