package com.tosmart.xiexuming.myapplication.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tosmart.xiexuming.myapplication.model.Program;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public class DataBaseManager {
    private final TransportStreamDataBase mTSDataBase;

    public DataBaseManager(Context context) {
        this.mTSDataBase = new TransportStreamDataBase
                (context.getApplicationContext(),
                        "tsProgram.db", null, 1);
    }

    public void setHistoryData(String searchHistory) {
        SQLiteDatabase writableDatabase = mTSDataBase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("history", searchHistory);
        writableDatabase.replace("Search", null, contentValues);
        writableDatabase.close();
    }

    public ArrayList<String> getHistoryData() {
        ArrayList<String> historyList = new ArrayList<>();
        SQLiteDatabase readableDatabase = mTSDataBase.getReadableDatabase();
        Cursor cursor = readableDatabase.query("Search", new String[]{"history"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String historyColumn
                    = cursor.getString(cursor.getColumnIndex("history"));
            historyList.add(historyColumn);
        }
        cursor.close();
        return historyList;
    }

    public void deleteHistory(String history) {
        SQLiteDatabase writableDatabase = mTSDataBase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("history", history);
        writableDatabase.delete("Search",
                "history = ?", new String[]{history});
        writableDatabase.close();
    }

    public void deleteHistory() {
        SQLiteDatabase writableDatabase = mTSDataBase.getWritableDatabase();
        writableDatabase.delete("Search", null, null);
        writableDatabase.close();
    }

    public void setFavoriteData(Integer programNumber, String filePath) {
        SQLiteDatabase writableDatabase = mTSDataBase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("programNumber", programNumber);
        contentValues.put("fileName", filePath);
        writableDatabase.replace("Favorite", null, contentValues);
        writableDatabase.close();
    }

    @SuppressLint("Range")
    public ArrayList<Integer> getFavoriteData(String filePath) {
        ArrayList<Integer> favoriteList = null;
        SQLiteDatabase readableDatabase = mTSDataBase.getReadableDatabase();
        Cursor cursor = readableDatabase.query("Favorite",
                null, "fileName = ?", new String[]{filePath},
                null, null, null, null);
        if (cursor != null) {
            favoriteList = new ArrayList<>();
            while (cursor.moveToNext()) {
                favoriteList.add(cursor.getInt(cursor.getColumnIndex("programNumber")));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        readableDatabase.close();
        return favoriteList;
    }

    @SuppressLint("Range")
    public ArrayList<Program> getProgramDescriptor(String path) {
        ArrayList<Program> programs = null;
        SQLiteDatabase readableDatabase = mTSDataBase.getReadableDatabase();
        Cursor cursor = readableDatabase.query("TransportStreamProgramDescriptor",
                null, "fileName=? and programNumber!=?", new String[]{path, "0"},
                null, null, "programNumber asc");
        if (cursor.getCount() > 0) {
            programs = new ArrayList<>();
            while (cursor.moveToNext()) {
                Program program = new Program();
                program.setId(cursor.getInt(cursor.getColumnIndex("id")));
                program.setProgramName(cursor.getString(cursor.getColumnIndex("programName")));
                program.setProgramNumber(cursor.getInt(cursor.getColumnIndex("programNumber")));
                program.setChannelStart(cursor.getString(cursor.getColumnIndex("channelStart")));
                program.setChannelDescriptor
                        (cursor.getString(cursor.getColumnIndex("channelDescriptor")));
                program.setFileName(cursor.getString(cursor.getColumnIndex("fileName")));

                programs.add(program);
            }
        }
        cursor.close();
        readableDatabase.close();
        return programs;
    }

    public void setProgramDescriptor(ArrayList<Program> programs) {
        if (programs == null) {
            return;
        }
        SQLiteDatabase writableDatabase = mTSDataBase.getWritableDatabase();
        for (Program program : programs) {
            ContentValues contentValues = new ContentValues();

            contentValues.put("programNumber", program.getProgramNumber());
            contentValues.put("programName", program.getProgramName());
            contentValues.put("fileName", program.getFileName());
            contentValues.put("channelStart", program.getChannelStart());
            contentValues.put("channelDescriptor", program.getChannelDescriptor());

            writableDatabase.replace("TransportStreamProgramDescriptor",
                    null, contentValues);
        }
        writableDatabase.close();
    }

    public void removeFavoriteData(int programNumber, String fileName) {
        SQLiteDatabase writableDatabase = mTSDataBase.getWritableDatabase();
        writableDatabase.delete("Favorite",
                "fileName = ? and programNumber = ?",
                new String[]{fileName, "" + programNumber});
        writableDatabase.close();
    }
}
