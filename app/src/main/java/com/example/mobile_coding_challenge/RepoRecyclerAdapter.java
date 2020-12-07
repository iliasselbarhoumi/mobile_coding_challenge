package com.example.mobile_coding_challenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    private List<Repository> repos;
    private Context context;
    private ConstraintLayout constraintLayout;

    public RepoRecyclerAdapter(Context context , ArrayList<Repository> repositories) {
        this.repos = repositories;
        this.context = context;

    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(this.context, LayoutInflater.from(parent.getContext()).inflate(R.layout.repo, parent, false));
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(this.context, LayoutInflater.from(parent.getContext()).inflate(R.layout.loading, parent, false));
            default:
                return null;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == repos.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }


    @Override
    public int getItemCount() {
        return repos == null ? 0 : repos.size();
    }

    public void addItems(List<Repository> repos) {
        this.repos.addAll(repos);
        notifyDataSetChanged();
    }


    public void addLoading() {
        isLoaderVisible = true;
        this.repos.add(new Repository());
        notifyItemInserted(this.repos.size() - 1);
    }


    public void removeLoading() {
        isLoaderVisible = false;
        int position = this.repos.size() - 1;
        Repository item = getItem(position);
        if (item != null) {
            this.repos.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        this.repos.clear();
        notifyDataSetChanged();
    }


    Repository getItem(int position) {
        return this.repos.get(position);
    }


    /************************************************************
     *
     *
     * *****************  ViewHolder Class  *******************
     *
     * **********************************************************/


    public class ViewHolder extends BaseViewHolder {

        private Context context;
        private ConstraintLayout constraintLayout;

        @BindView(R.id.repo_name)
        TextView repo_name;

        @BindView(R.id.repo_description)
        TextView repo_description;

        @BindView(R.id.repo_owner_name)
        TextView repo_owner_name;

        @BindView(R.id.numbers_of_stars)
        TextView repo_number_of_stars;

        @BindView(R.id.repo_avatar)
        ImageView repo_avatar;


        ViewHolder(Context context , View itemView) {
            super(context,itemView);
            this.context = context;
            this.constraintLayout = itemView.findViewById(R.id.constraintLayout);
            ButterKnife.bind(this, itemView);

        }

        protected void clear() {
        }

        public void onBind(int position) {
            super.onBind(position);
            Repository item = repos.get(position);

            repo_name.setText(item.getRepository_name());
            repo_description.setText(item.getRepository_description());
            repo_owner_name.setText(item.getRepository_owner_name());
            repo_number_of_stars.setText(item.getRepository_number_of_stars());
            Picasso.with(this.context).load(item.getRepository_avatar()).into(repo_avatar);
        }
    }

}
