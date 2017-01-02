package com.mostafa_anter.capstoneproject.ui.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.mostafa_anter.capstoneproject.R;
import com.mostafa_anter.capstoneproject.data.ArticlesContract;
import com.mostafa_anter.capstoneproject.data.MArticlesPrefStore;
import com.mostafa_anter.capstoneproject.model.Article;
import com.mostafa_anter.capstoneproject.util.Util;

import java.util.List;

/**
 * Created by mostafa_anter on 12/16/16.
 */

public class WidgetCollectionService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor cursor;
            private List<Article> mDataSet;


            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if (cursor != null) {
                    cursor.close();
                }
                final long identityToken = Binder.clearCallingIdentity();
                // get data from content provider
                String selection = ArticlesContract.ArticleEntry.COLUMN_SOURCE + "=?";
                String[] selectionArgs = {new MArticlesPrefStore(getApplicationContext()).getSourceValue()};
                cursor = getContentResolver().query(
                        ArticlesContract.ArticleEntry.CONTENT_URI,
                        ArticlesContract.ArticleEntry.ARTICLE_COLUMNS,
                        selection, selectionArgs, ArticlesContract.ArticleEntry.DEFAULT_SORT);
                mDataSet = Util.returnListFromCursor(cursor);

                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                    mDataSet = null;
                }
            }

            @Override
            public int getCount() {
                return mDataSet == null ? 0 : mDataSet.size();
            }

            @Override
            public RemoteViews getViewAt(int i) {
                if (i == AdapterView.INVALID_POSITION ||
                        cursor == null || mDataSet.size() < 1) {
                    return null;
                }
                RemoteViews remoteViews = new RemoteViews(WidgetCollectionService.this.getPackageName(),
                        R.layout.widget_item_list);

                String title = mDataSet.get(i).getTitle().equalsIgnoreCase("null")? "":
                        mDataSet.get(i).getTitle();

                String author = "";
                if (mDataSet.get(i) != null && mDataSet.get(i).getAuthor() != null) {
                    author = mDataSet.get(i).getAuthor().equalsIgnoreCase("null")?"":
                            mDataSet.get(i).getAuthor();
                    author = author.isEmpty()? "" : " by " + author;
                }
                String publishAt = "";
                if (mDataSet.get(i) != null && mDataSet.get(i).getPublishedAt() != null &&
                        !mDataSet.get(i).getPublishedAt().equalsIgnoreCase("null"))
                    publishAt = mDataSet.get(i).getPublishedAt();


                remoteViews.setTextViewText(R.id.article_title, title);
                remoteViews.setTextViewText(R.id.article_subtitle, Util.manipulateDateFormat(publishAt) + author);

                return remoteViews;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_item_list);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }
        };
    }
}
