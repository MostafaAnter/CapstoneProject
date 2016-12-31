package com.mostafa_anter.capstoneproject.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class ArticlesContract{

	public static final String CONTENT_AUTHORITY = "com.mostafa_anter.capstoneproject.app";

	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


	public static final class ArticleEntry implements BaseColumns{
		// table name
		public static final String TABLE_ARTICLES = "article";
		// columns
		public static final String _ID = "_id";
		public static final String COLUMN_AUTHOR = "author";
		public static final String COLUMN_TITLE = "title";
		public static final String COLUMN_DESCRIPTION = "description";
		public static final String COLUMN_URL = "url";
		public static final String COLUMN_URLTOIMAGE = "urlToImage";
		public static final String COLUMN_PUBLISHEDAT = "publishedAt";
		public static final String COLUMN_SOURCE = "source";
		public static final String COLUMN_VERSION_NAME = "version_name";


		// create content uri
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
			.appendPath(TABLE_ARTICLES).build();
		// create cursor of base type directory for multiple entries
		public static final String CONTENT_DIR_TYPE =
		ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_ARTICLES;
		// create cursor of base type item for single entry
		public static final String CONTENT_ITEM_TYPE =
			ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + TABLE_ARTICLES;

		// for building URIs on insertion
		public static Uri buildArticlesUri(long id){
        		return ContentUris.withAppendedId(CONTENT_URI, id);
		}
	}
}
