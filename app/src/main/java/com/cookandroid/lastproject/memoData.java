package com.cookandroid.lastproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class memoData {

    private static final String dataName = "MemoTest";
    private static final String table2 = "MemoTable";
    private static final int dbVersion = 1;

    private OpenHelper opener;
    private SQLiteDatabase datab;

    private Context context;

    public memoData(Context context) {
        this.context = context;
        this.opener = new OpenHelper(context, dataName, null, dbVersion);
        datab = opener.getWritableDatabase();
    }

    private class OpenHelper extends SQLiteOpenHelper{

        public OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String create = "CREATE TABLE "+ table2 +" (" + "seq integer PRIMARY KEY AUTOINCREMENT, "+
                    "title text," + "maintext text," + "subtext text," + "isdone integer)";
            sqLiteDatabase.execSQL(create);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+table2);
            onCreate(sqLiteDatabase);
        }
    }

    public void insertMemo(Memo memo){
        String sql = "INSERT INTO " + table2 + " VALUES(NULL, '"+ memo.title+"','"+ memo.maintext+"','"+ memo.subtext+"',"+ memo.getIsdone()+");";
        datab.execSQL(sql);
    }

    public void deleteMemo(int position){
        String sql = "DELETE FROM " + table2 + " WHERE seq = " +position+";";
        datab.execSQL(sql);
    }

    public void updateMemo(Memo memo) {
        String sql = "UPDATE " + table2 + " SET title = '" + memo.getTitle() + "', " +
                "maintext = '" + memo.getMaintext() + "', " +
                "subtext = '" + memo.getSubtext() + "', " +
                "isdone = " + memo.getIsdone() + " WHERE seq = " + memo.getSeq() + ";";
        datab.execSQL(sql);
    }

    public ArrayList<Memo> selectALL(){
        String sql = "SELECT * FROM "+table2;

        ArrayList<Memo> list = new ArrayList<>();

        Cursor results = datab.rawQuery(sql, null);
        results.moveToFirst();

        while(!results.isAfterLast()) {
            Memo memo = new Memo(results.getInt(0),results.getString(1),results.getString(2),results.getString(3),results.getInt(4));

            list.add(memo);
            results.moveToNext();
        }
        results.close();
        return list;
    }
}
