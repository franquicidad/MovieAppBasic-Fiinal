package com.example.mac.movieappbasic;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mac.movieappbasic.Moviedata.MovieContract;
import com.example.mac.movieappbasic.Moviedata.MovieDbHelper;

/**
 * Created by mac on 21/03/18.
 */

public class MovieContentProvider extends ContentProvider {

    MovieDbHelper mDbHelper;

    Cursor retCursor;

    public static final int MOVIE_DB=100;
    public static final int MOVIE_ID=101;

    private static final UriMatcher sUriMatcher=buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);


        uriMatcher.addURI(MovieContract.AUTHORITY,MovieContract.PATH_MOVIE_DB,MOVIE_DB);
        uriMatcher.addURI(MovieContract.AUTHORITY,MovieContract.PATH_MOVIE_DB +"/#",MOVIE_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {

        Context context=getContext();
        mDbHelper=new MovieDbHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db=mDbHelper.getReadableDatabase();


        int match=sUriMatcher.match(uri);

        switch(match){
            case MOVIE_DB:
                retCursor=db.query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
                default:
                    throw new UnsupportedOperationException("Not yet implemented");
        }

        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase db=mDbHelper.getWritableDatabase();

        int match=sUriMatcher.match(uri);

        Uri returnUri;

        switch (match){
            case MOVIE_DB:
                long id=db.insert(MovieContract.MovieEntry.TABLE_NAME,null,values);

                if(id>0){
                    returnUri= ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI,id);

                }else{
                    throw new android.database.SQLException("Failed to insert row"+uri);
                }
                break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

        //Notify the resolver if the uri has been changed
        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int numberOfRows;

        SQLiteDatabase db=mDbHelper.getWritableDatabase();

        int match=sUriMatcher.match(uri);

        switch (match){
            case MOVIE_ID:
                String id=uri.getPathSegments().get(1);
                numberOfRows= db.delete(MovieContract.MovieEntry.TABLE_NAME,"movieId=?",new String[]{id});
                break;
                default:
                    throw new UnsupportedOperationException("Unknown uri" + uri);
        }
        if(numberOfRows!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return numberOfRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
