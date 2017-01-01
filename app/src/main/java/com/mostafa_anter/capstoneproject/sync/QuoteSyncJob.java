package com.mostafa_anter.capstoneproject.sync;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.mostafa_anter.capstoneproject.BuildConfig;
import com.mostafa_anter.capstoneproject.data.MArticlesPrefStore;
import com.mostafa_anter.capstoneproject.model.Article;
import com.mostafa_anter.capstoneproject.model.NewsResponse;
import com.mostafa_anter.capstoneproject.rest.ApiClient;
import com.mostafa_anter.capstoneproject.rest.ApiInterface;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class QuoteSyncJob {

    private static final int ONE_OFF_ID = 2;
    public static final String ACTION_DATA_UPDATED = "com.mostafa_anter.capstoneproject.ACTION_DATA_UPDATED";
    private static final int PERIOD = 300000;
    private static final int INITIAL_BACKOFF = 10000;
    private static final int PERIODIC_ID = 1;

    private static Subscription subscription;

    private QuoteSyncJob() {
    }

    static void getQuotes(Context context) {

        // detach an observer from its observable while the observable is still emitting data
        if (subscription != null) subscription.unsubscribe();
        // get data from api
        // update data inside content provider

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Observable<NewsResponse> mObservable =
                apiService.getArticles(new MArticlesPrefStore(context).getSourceValue(),
                        BuildConfig.API_KEY);

        subscription = mObservable
                .subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .subscribe(new Subscriber<NewsResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d("task complete", "task complete successfully");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("articles error", e.toString());
                    }

                    @Override
                    public void onNext(NewsResponse response) {
                        Log.d("article result", response.getSource());
                        for (Article article: response.getArticles()) {
                            article.setSource(response.getSource());
                        }
                        Log.d("article result", response.toString());
                    }
                });


        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
        context.sendBroadcast(dataUpdatedIntent);
    }

    private static void schedulePeriodic(Context context) {

        JobInfo.Builder builder = new JobInfo.Builder(PERIODIC_ID, new ComponentName(context, QuoteJobService.class));


        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(PERIOD)
                .setBackoffCriteria(INITIAL_BACKOFF, JobInfo.BACKOFF_POLICY_EXPONENTIAL);


        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        scheduler.schedule(builder.build());
    }


    public static synchronized void initialize(final Context context) {

        schedulePeriodic(context);
        syncImmediately(context);

    }

    public static synchronized void syncImmediately(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            Intent nowIntent = new Intent(context, QuoteIntentService.class);
            context.startService(nowIntent);
        } else {

            JobInfo.Builder builder = new JobInfo.Builder(ONE_OFF_ID, new ComponentName(context, QuoteJobService.class));


            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setBackoffCriteria(INITIAL_BACKOFF, JobInfo.BACKOFF_POLICY_EXPONENTIAL);


            JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

            scheduler.schedule(builder.build());


        }
    }


}
