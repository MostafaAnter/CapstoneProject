package com.mostafa_anter.capstoneproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mostafa_anter.capstoneproject.R;
import com.mostafa_anter.capstoneproject.model.Article;
import com.mostafa_anter.capstoneproject.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.article_title)TextView title;
    @BindView(R.id.article_byline)TextView byline;
    @BindView(R.id.article_body)TextView body;
    @BindView(R.id.adView)AdView mAdView;

    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        article = (Article) getIntent().getExtras().get("item_data");
        if (article == null)
            finish();


        Util.manipulateToolbar(this, toolbar, 0, null, true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!article.getUrl().equalsIgnoreCase("null")){
                    shareLink();
                }else {
                    Snackbar.make(view, "Sorry you cant share this article", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bindData();

        // manipulate ads
        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void bindData(){

        ImageView imageView = (ImageView) collapsingToolbarLayout.findViewById(R.id.mainImage);
        // load thumbnail image :)
        Glide.with(this)
                .load(article.getUrlToImage())
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(imageView);

        // set title text
        String titleText = article.getTitle().equalsIgnoreCase("null")? "":
                article.getTitle();
        title.setText(titleText);

        // set byline text
        String author = "";
        if (article.getAuthor() != null) {
            author = article.getAuthor().equalsIgnoreCase("null")?"":
                    article.getAuthor();
            author = author.isEmpty()? "" : " by " + author;
        }
        String publishAt = "";
        if (article.getPublishedAt() != null &&
                !article.getPublishedAt().equalsIgnoreCase("null"))
            publishAt = article.getPublishedAt();
        byline.setText(Util.manipulateDateFormat(publishAt) + author);

        body.setText(article.getDescription());


    }

    private void shareLink(){
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "M-Articles");
            String sAux = "\n"+ article.getTitle() + "\n\n";
            sAux = sAux + article.getUrl() + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }
}
