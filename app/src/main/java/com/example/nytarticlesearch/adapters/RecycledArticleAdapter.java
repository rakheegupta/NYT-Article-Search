package com.example.nytarticlesearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nytarticlesearch.GlideApp;
import com.example.nytarticlesearch.R;
import com.example.nytarticlesearch.models.Article;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class RecycledArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
    private List<Article> mArticleList;
    Context mContext;
    public RecycledArticleAdapter(Context context,List<Article> articles){
        mArticleList=articles;
        mContext=context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View articleView = inflater.inflate(R.layout.list_item_article,parent,false);
        //Return a new holder instance
        ArticleViewHolder viewHolder;
        viewHolder=new ArticleViewHolder(articleView);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        //prepare the item to display
        Article article = mArticleList.get(position);
        //clear previous article image
        holder.mIvArticle.setImageResource(0);
        String imageUri=article.getmImageUrl();
        holder.mTvTitle.setText(article.getmHeadline());
        holder.mTvOverview.setText(article.getmAbstract());
        GlideApp.with(holder.mIvArticle.getContext())
                .load(imageUri)
                .override((int) mContext.getResources().getDimension(R.dimen.image_width), (int) mContext.getResources().getDimension(R.dimen.image_height))
                .placeholder(R.drawable.ic_launcher_foreground)
                .transform(new RoundedCornersTransformation(30, 10))
                .into(holder.mIvArticle);
    }
}
