package com.hmkcode.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String TABLE_SONGS = "songs";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_BAND = "band";
    private static final String[] COLUMNS = {KEY_ID, KEY_TITLE, KEY_BAND};
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SongDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_SONG_TABLE = "CREATE TABLE songs ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "band TEXT )";

        db.execSQL(CREATE_SONG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS songs");
        this.onCreate(db);
    }

    public void addSong(Song song) {
        Log.d("addSong", song.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, song.getTitle());
        values.put(KEY_BAND, song.getBand());

        db.insert(TABLE_SONGS,
                null,
                values);
        db.close();
    }

    public Song getSong(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_SONGS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[]{String.valueOf(id)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Song song = new Song();
        song.setId(Integer.parseInt(cursor.getString(0)));
        song.setTitle(cursor.getString(1));
        song.setBand(cursor.getString(2));

        //log
        Log.d("getSong(" + id + ")", song.toString());

        // 5. return book
        return song;
    }

    public List<Song> getAllSongs() {
        List<Song> songs = new LinkedList<Song>();

        String query = "SELECT  * FROM " + TABLE_SONGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Song song = null;
        if (cursor.moveToFirst()) {
            do {
                song = new Song();
                song.setId(Integer.parseInt(cursor.getString(0)));
                song.setTitle(cursor.getString(1));
                song.setBand(cursor.getString(2));

                // Add book to books
                songs.add(song);
            } while (cursor.moveToNext());
        }

        Log.d("getAllSongs()", songs.toString());

        return songs;
    }

    public int updateSong(Song song) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", song.getTitle()); // get title
        values.put("band", song.getBand()); // get author

        int i = db.update(TABLE_SONGS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(song.getId()) }); //selection args
        db.close();

        return i;

    }

    public void deleteSong(Song song) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SONGS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(song.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteSong", song.toString());

    }

}