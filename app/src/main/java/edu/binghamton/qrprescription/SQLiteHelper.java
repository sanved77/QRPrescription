package edu.binghamton.qrprescription;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/*
 *   Database helper class with all the methods we need.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "meddata";
    private static final String TABLE_NAME = "meds";
    private static final String KEY_NAME = "name";
    private static final String KEY_MORNING = "morning";
    private static final String KEY_AFTERNOON = "afternoon";
    private static final String KEY_NIGHT = "night";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME + " ( id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, morning INTEGER, afternoon INTEGER, night INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    // Used to add new words
    public void addPrescription(MedEntry wd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, wd.getName());
        values.put(KEY_MORNING, wd.getMorning());
        values.put(KEY_AFTERNOON, wd.getAfternoon());
        values.put(KEY_NIGHT, wd.getNight());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }


    // Get aa ArrayList of all words
    public ArrayList<MedEntry> allPrescriptions() {

        ArrayList<MedEntry> prescriptions = new ArrayList<MedEntry>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MedEntry pres = null;

        if (cursor.moveToFirst()) {
            do {
                pres = new MedEntry(cursor.getString(1),cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
                prescriptions.add(pres);
            } while (cursor.moveToNext());
        }

        return prescriptions;
    }


    // Written here is a method use to delete every word/row.
    // Use it for testing purposes. Not for release.
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }


//    // Searching a word by giving a similar term
//    public ArrayList searchWord(String term){
//
//        ArrayList<WordData> wordDataList = new ArrayList<WordData>();
//        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE word LIKE '%"+ term +"%'";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        WordData word = null;
//
//        if (cursor.moveToFirst()) {
//            do {
//                word = new WordData(cursor.getInt(3),cursor.getString(1), cursor.getString(2));
//
//                wordDataList.add(word);
//            } while (cursor.moveToNext());
//        }
//
//        return wordDataList;
//    }

    // Delete a given word
    public void deleteWord(String term){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where name like '"+ term +"'");
    }


    // Get the primary ID of any word
    public int getId(String term) {

        int id = 99999;
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE name LIKE '" + term + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        return id;
    }


//    // Use to update current word entries
//    public void editName(int id2, MedEntry wd){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues vals = new ContentValues();
//        vals.put(KEY_WORD,wd.getWord());
//        vals.put(KEY_SENTENCE, wd.getSentence());
//
//        db.update(TABLE_NAME, vals, "id="+id2, null);
//    }


//    // Method to search a word by giving it's ID
//    public WordData searchWord(int id){
//
//        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE id="+ id +"";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        WordData word = null;
//
//        if (cursor.moveToFirst()) {
//            do {
//                word = new WordData(cursor.getInt(3),cursor.getString(1), cursor.getString(2));
//
//            } while (cursor.moveToNext());
//        }
//
//        return word;
//    }

}
