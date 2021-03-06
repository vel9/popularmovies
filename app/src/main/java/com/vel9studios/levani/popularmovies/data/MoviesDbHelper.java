/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vel9studios.levani.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vel9studios.levani.popularmovies.data.MoviesContract.MoviesEntry;
import com.vel9studios.levani.popularmovies.data.MoviesContract.ReviewsEntry;
import com.vel9studios.levani.popularmovies.data.MoviesContract.VideosEntry;

//Code Reference: "Developing Android Apps: Fundamentals"
public class MoviesDbHelper extends SQLiteOpenHelper {

    private final String LOG_TAG = MoviesDAO.class.getSimpleName();
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 3;

    static final String DATABASE_NAME = "movies.db";

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold movies.
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE IF NOT EXISTS " + MoviesEntry.TABLE_NAME + " (" +

                MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                //"relational" column
                MoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MoviesEntry.COLUMN_MOVIE_TITLE + " TEXT, " +
                MoviesEntry.COLUMN_IMAGE_PATH + " TEXT, " +
                MoviesEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                MoviesEntry.COLUMN_OVERVIEW + " TEXT, " +
                MoviesEntry.COLUMN_VOTE_AVERAGE + " REAL, " +
                MoviesEntry.COLUMN_POPULARITY + " REAL, " +
                MoviesEntry.COLUMN_FAVORITE_IND + " TEXT " +

                " );";

        final String SQL_CREATE_VIDEOS_TABLE = "CREATE TABLE IF NOT EXISTS " + VideosEntry.TABLE_NAME + " (" +

                VideosEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                VideosEntry.COLUMN_VIDEO_ID + " TEXT, " +
                VideosEntry.COLUMN_VIDEO_KEY + " TEXT, " +
                VideosEntry.COLUMN_VIDEO_NAME + " TEXT, " +
                VideosEntry.COLUMN_VIDEO_SITE + " TEXT, " +
                VideosEntry.COLUMN_VIDEO_SIZE + " REAL, " +
                VideosEntry.COLUMN_TYPE + " TEXT, " +
                //"relational" column
                MoviesEntry.COLUMN_MOVIE_ID + " TEXT " +

                " );";

        final String SQL_CREATE_REVIEWS_TABLE = "CREATE TABLE IF NOT EXISTS " + ReviewsEntry.TABLE_NAME + " (" +

                ReviewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ReviewsEntry.COLUMN_REVIEW_ID + " TEXT, " +
                ReviewsEntry.COLUMN_REVIEW_AUTHOR + " TEXT, " +
                ReviewsEntry.COLUMN_REVIEW_CONTENT + " TEXT, " +
                //"relational" column
                MoviesEntry.COLUMN_MOVIE_ID + " TEXT " +

                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_VIDEOS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_REVIEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Core code from "Developing Android Apps: Fundamentals"
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VideosEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ReviewsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
