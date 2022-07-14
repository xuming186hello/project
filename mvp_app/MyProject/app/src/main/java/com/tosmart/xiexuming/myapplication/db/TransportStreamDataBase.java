package com.tosmart.xiexuming.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @author xiexuming
 */
public class TransportStreamDataBase extends SQLiteOpenHelper {
    public static final String CREATE_PROGRAM_DESCRIPTOR_TABLE =
            "create table TransportStreamProgramDescriptor ("
                    + "id integer  primary key,"
                    + "programNumber integer,"
                    + "programName text,"
                    + "channelStart text,"
                    + "channelDescriptor text,"
                    + "fileName text"
                    + ")";

    private static final String CREATE_SEARCH_TABLE =
            "create table Search ("
                    + "history text primary key"
                    + ")";

    public static final String CREATE_FAVORITE_TABLE =
            "create table Favorite ("
                    + "programNumber Integer ,"
                    + "fileName text,"
                    + "primary key(programNumber,fileName)"
                    + ")";

    private final Context mContext;

    public TransportStreamDataBase(@Nullable Context context,
                                   @Nullable String name,
                                   @Nullable SQLiteDatabase.CursorFactory factory,
                                   int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FAVORITE_TABLE);
        db.execSQL(CREATE_SEARCH_TABLE);
        db.execSQL(CREATE_PROGRAM_DESCRIPTOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
