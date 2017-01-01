package com.mostafa_anter.capstoneproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mostafa_anter.capstoneproject.R;
import com.mostafa_anter.capstoneproject.R2;
import com.mostafa_anter.capstoneproject.model.Article;
import com.mostafa_anter.capstoneproject.ui.views.DynamicHeightNetworkImageView;
import com.mostafa_anter.capstoneproject.util.Util;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mostafa_anter on 12/31/16.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private static final String TAG = "ArticleAdapter";

    private Context mContext;
    private List<Article> mDataSet;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.article_title)TextView title;
        @BindView(R2.id.article_subtitle)TextView subtitle;
        @BindView(R2.id.thumbnail)DynamicHeightNetworkImageView thumbnail;

        public TextView getTitle() {
            return title;
        }

        public TextView getSubtitle() {
            return subtitle;
        }

        public DynamicHeightNetworkImageView getThumbnail() {
            return thumbnail;
        }

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public ArticleAdapter(Context mContext, List<Article> dataSet) {
        this.mContext = mContext;
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_article, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        String title = mDataSet.get(position).getTitle().equalsIgnoreCase("null")? "":
                mDataSet.get(position).getTitle();

        viewHolder.getTitle().setText(title);
        //viewHolder.getSubtitle().setText(Util.manipulateDateFormat(mDataSet.get(position).getPublishedAt()));

        // load thumbnail image :)
        Glide.with(mContext)
                .load(mDataSet.get(position).getUrlToImage())
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(viewHolder.getThumbnail());
        viewHolder.getThumbnail().setAspectRatio(1f + (new Random().nextFloat()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}