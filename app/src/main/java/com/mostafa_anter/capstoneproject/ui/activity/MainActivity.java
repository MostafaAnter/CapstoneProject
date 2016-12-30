package com.mostafa_anter.capstoneproject.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.mostafa_anter.capstoneproject.R;
import com.mostafa_anter.capstoneproject.R2;
import com.mostafa_anter.capstoneproject.data.MArticlesPrefStore;
import com.mostafa_anter.capstoneproject.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R2.id.toolbar)Toolbar toolbar;
    private TextView toolbarSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // manipulate toolbar
        Util.manipulateToolbar(this, toolbar, 0, null, true);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.title);
        toolbarSubtitle = (TextView) toolbar.findViewById(R.id.subtitle);
        Util.changeViewTypeFace(this, "Righteous-Regular.ttf", toolbarTitle);
        toolbarTitle.setText(getString(R.string.app_name));
        toolbarSubtitle.setText(new MArticlesPrefStore(this)
                .getSourceName(new MArticlesPrefStore(this)
                        .getSourceValue()));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    }
}
