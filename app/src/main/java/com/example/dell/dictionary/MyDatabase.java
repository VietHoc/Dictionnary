package com.example.dell.dictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 7/30/2016.
 */
public class MyDatabase extends SQLiteOpenHelper{
        private Context mycontext;
        private static String DB_NAME = "dictionary.sqlite";
        private static String DB_PATH ="/data/data/"+BuildConfig.APPLICATION_ID+"/databases/";
        public SQLiteDatabase myDataBase;


        public MyDatabase(Context context) throws IOException {
            super(context,DB_NAME,null,1);
            this.mycontext=context;
            boolean dbexist = checkdatabase();
            if (dbexist) {
                System.out.println("Database exists");
                opendatabase();
            } else {
                System.out.println("Database doesn't exist");
                createdatabase();
            }
        }

        public void createdatabase() throws IOException {
            boolean dbexist = checkdatabase();
            if(dbexist) {
                System.out.println(" Database exists.");
            } else {
                this.getReadableDatabase();
                try {
                    copydatabase();
                } catch(IOException e) {
                    throw new Error("Error copying database");
                }
            }
        }

        private boolean checkdatabase() {

            boolean checkdb = false;
            try {
                String myPath = DB_PATH + DB_NAME;
                File dbfile = new File(myPath);
                checkdb = dbfile.exists();
            } catch(SQLiteException e) {
                System.out.println("Database doesn't exist");
            }
            return checkdb;
        }

        private void copydatabase() throws IOException {
            //Open your local db as the input stream
            InputStream myinput = mycontext.getAssets().open(DB_NAME);

            // Path to the just created empty db
            String outfilename = DB_PATH + DB_NAME;

            //Open the empty db as the output stream
            OutputStream myoutput = new FileOutputStream(outfilename);

            // transfer byte to inputfile to outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myinput.read(buffer))>0) {
                myoutput.write(buffer,0,length);
            }

            //Close the streams
            myoutput.flush();
            myoutput.close();
            myinput.close();
        }

        public void opendatabase() throws SQLException {
            //Open the database
            String mypath = DB_PATH + DB_NAME;
            myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
        }

        public synchronized void close() {
            if(myDataBase != null) {
                myDataBase.close();
            }
            super.close();
        }
    public List<Word> getList(){
        List<Word> Words = new ArrayList<>();
        opendatabase();
        String word,detail;
        int id;
        Cursor cursor = myDataBase.rawQuery("SELECT id,code,content FROM tblDictionary",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Words.add(new Word(cursor.getString(1),cursor.getString(2),cursor.getInt(0)));
            cursor.moveToNext();
        }
        return Words;
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
