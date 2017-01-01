package com.mostafa_anter.capstoneproject.ui.activity;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mostafa_anter.capstoneproject.R;
import com.mostafa_anter.capstoneproject.R2;
import com.mostafa_anter.capstoneproject.adapter.ArticleAdapter;
import com.mostafa_anter.capstoneproject.data.ArticlesContract;
import com.mostafa_anter.capstoneproject.data.MArticlesPrefStore;
import com.mostafa_anter.capstoneproject.model.Article;
import com.mostafa_anter.capstoneproject.sync.QuoteSyncJob;
import com.mostafa_anter.capstoneproject.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements android.app.LoaderManager.LoaderCallbacks<Cursor>,
        NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R2.id.toolbar)Toolbar toolbar;
    private TextView toolbarSubtitle;

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R2.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;

    @BindView(R2.id.noData)
    LinearLayout noDataView;


    protected ArticleAdapter mAdapter;
    protected StaggeredGridLayoutManager sglm;
    protected List<Article> mDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        manipulateToolbar();
        setRecyclerViewAndSwipe();
        onRefresh();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        QuoteSyncJob.initialize(this);
        getLoaderManager().initLoader(0, null, this);
    }

    private void setRecyclerViewAndSwipe() {
        // manipulateRecyclerView & Swipe to refresh
        mDataSet = new ArrayList<>();
        sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);
        mAdapter = new ArticleAdapter(this, mDataSet);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        //noinspection ResourceAsColor
        mSwipeRefresh.setColorScheme(
                R.color.swipe_color_1, R.color.swipe_color_2,
                R.color.swipe_color_3, R.color.swipe_color_4);
        mSwipeRefresh.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        24,
                        getResources().getDisplayMetrics()));

        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);
    }

    private void manipulateToolbar() {
        // manipulate toolbar
        Util.manipulateToolbar(this, toolbar, 0, null, true);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.title);
        toolbarSubtitle = (TextView) toolbar.findViewById(R.id.subtitle);
        Util.changeViewTypeFace(this, "Righteous-Regular.ttf", toolbarTitle);
        toolbarTitle.setText(getString(R.string.app_name));
        toolbarSubtitle.setText(new MArticlesPrefStore(this)
                .getSourceName(new MArticlesPrefStore(this)
                        .getSourceValue()));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.bbc_news) {
            navigationItemAction(getString(R.string.bbc_news_name),
                    getString(R.string.bbc_news_source));
        } else if (id == R.id.bbc_sport) {
            navigationItemAction(getString(R.string.bbc_sport_name),
                    getString(R.string.bbc_sport_source));
        } else if (id == R.id.cnn) {
            navigationItemAction(getString(R.string.cnn_name),
                    getString(R.string.cnn_source));
        } else if (id == R.id.google) {
            navigationItemAction(getString(R.string.google_name),
                    getString(R.string.google_source));
        } else if (id == R.id.national_geographic) {
            navigationItemAction(getString(R.string.national_geographic_name),
                    getString(R.string.national_geographic_source));
        } else if (id == R.id.sky_news) {
            navigationItemAction(getString(R.string.sky_news_name),
                    getString(R.string.sky_news_source));
        } else if (id == R.id.reddit) {
            navigationItemAction(getString(R.string.reddit_name),
                    getString(R.string.reddit_source));
        } else if (id == R.id.cnbc) {
            navigationItemAction(getString(R.string.cnbc_name),
                    getString(R.string.cnbc_source));
        } else if (id == R.id.entertainment) {
            navigationItemAction(getString(R.string.entertainment_name),
                    getString(R.string.entertainment_source));
        } else if (id == R.id.new_scientist) {
            navigationItemAction(getString(R.string.new_scientist_name),
                    getString(R.string.new_scientist_source));
        } else if (id == R.id.techcrunch) {
            navigationItemAction(getString(R.string.techcrunch_name),
                    getString(R.string.techcrunch_source));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigationItemAction(String sourceName, String sourceValue){
        new MArticlesPrefStore(this).addSourceValue(sourceValue);
        new MArticlesPrefStore(this).addSourceName(sourceValue, sourceName);
        toolbarSubtitle.setText(new MArticlesPrefStore(this).getSourceName(sourceValue));

        // load data :)
        onRefresh();

        getLoaderManager().restartLoader(0, null, this);
    }

    private boolean networkUp() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onRefresh() {
        QuoteSyncJob.syncImmediately(this);
        if (!mSwipeRefresh.isRefreshing()){
            mSwipeRefresh.setRefreshing(true);
        }
        if (!networkUp() && mAdapter.getItemCount() == 0) {
            mSwipeRefresh.setRefreshing(false);
            noDataView.setVisibility(View.VISIBLE);
            Toast.makeText(this, R.string.toast_no_connectivity, Toast.LENGTH_LONG).show();
        } else if (!networkUp()) {
            mSwipeRefresh.setRefreshing(false);
            Toast.makeText(this, R.string.toast_no_connectivity, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String selection = ArticlesContract.ArticleEntry.COLUMN_SOURCE + "=?";
        String[] selectionArgs = {new MArticlesPrefStore(this).getSourceValue()};

        return new CursorLoader(this,
                ArticlesContract.ArticleEntry.CONTENT_URI,
                ArticlesContract.ArticleEntry.ARTICLE_COLUMNS,
                selection, selectionArgs, ArticlesContract.ArticleEntry.DEFAULT_SORT);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        mSwipeRefresh.setRefreshing(false);
        if (data.getCount() != 0) {
            noDataView.setVisibility(View.GONE);
            // clear data set
            clearDataSet();
            mDataSet.addAll(Util.returnListFromCursor(data));
            mAdapter.notifyDataSetChanged();

        }else {
            noDataView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        mSwipeRefresh.setRefreshing(false);
        // clear data set
        clearDataSet();
    }

    // remove all item from RecyclerView
    private void clearDataSet() {
        if (mDataSet != null) {
            mDataSet.clear();
            mAdapter.notifyDataSetChanged();
        }
    }
}
