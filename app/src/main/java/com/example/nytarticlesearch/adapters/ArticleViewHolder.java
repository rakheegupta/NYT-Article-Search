package com.example.nytarticlesearch.adapters;

// Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nytarticlesearch.R;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    public ImageView mIvArticle;
    public TextView mTvTitle;
    public TextView mTvOverview;

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ArticleViewHolder(View itemView){
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
        super(itemView);
        mIvArticle=(ImageView)itemView.findViewById(R.id.ivArticle);
        mTvTitle=(TextView)itemView.findViewById(R.id.tvArticleHeader);
        mTvOverview=(TextView)itemView.findViewById(R.id.tvShortDescription);
    }
}
