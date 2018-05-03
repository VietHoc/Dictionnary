package com.example.dell.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by DELL on 8/2/2016.
 */
public class MyDatabaseLove {

    private static final String DATABASE_NAME="DB_WORK";
    private static final int DATABASE_VERSON=1;
    private static final String TABLE_FAVOURITE="FAVOURITE_LIST";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_CODE="code";
    public static final String COLUMN_CONTENT="content";

    private static Context context;
    static SQLiteDatabase db;
    private OpenHelper openHelper;

    public MyDatabaseLove(Context context){
        MyDatabaseLove.context=context;
    }

    public MyDatabaseLove open(){
        openHelper=new OpenHelper(context);
        db=openHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        openHelper.close();
    }

    public List<Word> getList(){
        List<Word> words = null;
        String[] columns=new String[]{COLUMN_ID,COLUMN_CODE,COLUMN_CONTENT};
        Cursor cursor=db.query(TABLE_FAVOURITE,columns,null,null,null,null,null);
        cursor.moveToFirst();
        for (;!cursor.isAfterLast();cursor.moveToNext()){
            words.add(new Word(cursor.getString(1),cursor.getString(2),cursor.getInt(0)));
        }

        return words;
    }

    public long add(Word word){
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_ID,word.getId());
        contentValues.put(COLUMN_CODE,word.getWord());
        contentValues.put(COLUMN_CONTENT,word.getDetail());
        return db.insert(TABLE_FAVOURITE,null,contentValues);
    }


    public int deleteWord(Word word){
        return db.delete(TABLE_FAVOURITE,COLUMN_CODE+"='"+word.getWord()+"'",null);
    }

    public int deleteAll(){
        return db.delete(TABLE_FAVOURITE,null,null);
    }



    private static class OpenHelper extends SQLiteOpenHelper{

        public OpenHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSON);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_FAVOURITE + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_CODE + " TEXT NOT NULL, "
                    + COLUMN_CONTENT + " TEXT NOT NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITE);
            onCreate(sqLiteDatabase);
        }
    }

}
