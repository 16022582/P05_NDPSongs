package rp.p05_song;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "simplenotes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "song";
    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_SINGERS = "singers";
    private static final String COL_YEAR = "years";
    private static final String COL_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_TITLE + " TEXT, "
                + COL_SINGERS + " TEXT, "
                + COL_YEAR + " INTEGER, "
                + COL_STARS + "INTEGER) ";
        db.execSQL(createNoteTableSql);
        Log.i("info","created tables");

        ContentValues values = new ContentValues();
        values.put(COL_TITLE, "Our Singapore");
        values.put(COL_SINGERS, "JJ Lin & Dick Lee");
        values.put(COL_YEAR, 2015);
        values.put(COL_STARS, 5);
        db.insert(TABLE_NAME, null, values);

        Log.i("info", "dummy records inserted");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertSong(String songTitle, String singer, int year, int stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE , songTitle);
        values.put(COL_SINGERS, singer);
        values.put(COL_YEAR, year);
        values.put(COL_STARS, stars);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        if (result == -1) {
            Log.d("DBHelper", "Insert failed");
        }

        Log.d("SQL Insert ",""+ result);
        return result;
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songs = new ArrayList<Song>();

        String selectQuery = "SELECT " + COL_ID + ", "
                + COL_TITLE + ", "
                + COL_SINGERS + ", "
                + COL_YEAR + ", "
                + COL_STARS + " FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String songTitle = cursor.getString(1);
                String singer = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Song song = new Song(id, songTitle, singer, year, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }


    public int updateSong(Song data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, data.getTitle());
        values.put(COL_SINGERS, data.getSingers());
        values.put(COL_YEAR, data.getYear());
        values.put(COL_STARS, data.getStars());
        String condition = COL_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_NAME, values, condition, args);
        db.close();

        if (result < 1){
            Log.d("DBHelper", "Update Failed");
        }
        return result;
    }

    public int deleteSong(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COL_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_NAME, condition, args);
        db.close();

        if (result < 1) {
            Log.d("DBHelper", "Delete failed");
        }
        return result;
    }
}
