package com.javivaquero.earthquakeapp.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

public class EarthQuakesProvider extends ContentProvider {

    public static final Uri CONTENT_URI = Uri.parse("content://com.javivaquero.provider.earthquakes/earthquakes");

    private	static final int ALL_ROWS    = 1;
    private	static final int SINGLE_ROW = 2;



    public static class Columns implements BaseColumns{
        public static final String PLACE_KEY = "place";
        public static final String MAGNITUDE_KEY = "magnitude";
        public static final String LATITUDE_KEY = "latitude";
        public static final String LONGITUDE_KEY = "longitude";
        public static final String DEPTH_KEY = "depth";
        public static final String URL_KEY = "url";
        public static final String TIME_KEY = "time";
    }

    private static final UriMatcher uriMatcher;
    private EarthQuakeOpenHelper earthQuakeOpenHelper;

    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("content://com.javivaquero.provider.earthquakes/earthquakes", "earthquakes", ALL_ROWS);
        uriMatcher.addURI("content://com.javivaquero.provider.earthquakes/earthquakes", "earthquakes/#", SINGLE_ROW);
    }

    @Override
    public boolean onCreate() {
        earthQuakeOpenHelper = new EarthQuakeOpenHelper(getContext(),EarthQuakeOpenHelper.DATABASE_NAME, null, EarthQuakeOpenHelper.DATABASE_VERSION);

        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case ALL_ROWS:
                return "vnd.android.cursor.dir/vnd.javivaquero.provider.earthquakes";
            case SINGLE_ROW:
                return "vnd.android.cursor.item/vnd.javivaquero.provider.earthquakes";
            default:
                throw new IllegalArgumentException("Unsupported	URI: " + uri);

        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = earthQuakeOpenHelper.getWritableDatabase();

        long id	= db.insert(EarthQuakeOpenHelper.DATABASE_TABLE, null, values);

        if (id > -1){
            //Construct	and	return the URI of the newly inserted row.
            Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
            //Notify any observers of the change in the data set.
            getContext().getContentResolver().notifyChange(insertedId, null);

            return insertedId;
        }
        else {
            return null;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db;
        try	{
            db = earthQuakeOpenHelper.getWritableDatabase();
        }catch(SQLiteException ex){
            db = earthQuakeOpenHelper.getReadableDatabase();
        }

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)){
            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(Columns._ID + "= ?");
                selectionArgs = new String[] {rowID};

            default: break;
        }

        queryBuilder.setTables(EarthQuakeOpenHelper.DATABASE_TABLE);

        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null,	sortOrder);

        return	cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper {

        private	static final String	DATABASE_NAME = "earthquakes.db";
        private	static final String	DATABASE_TABLE = "EARTHQUAKES";
        private	static final int DATABASE_VERSION = 1;

        //	SQL	Statement	to	create	a	new	database.
        private	static final String	DATABASE_CREATE	= "CREATE TABLE " + DATABASE_TABLE +
                "(" + Columns._ID + " TEXT PRIMARY KEY, " +
                Columns.PLACE_KEY + " TEXT, " +
                Columns.MAGNITUDE_KEY + " REAL, " +
                Columns.LATITUDE_KEY + " REAL, " +
                Columns.LONGITUDE_KEY + " REAL, " +
                Columns.DEPTH_KEY + " REAL, " +
                Columns.URL_KEY + " TEXT, " +
                Columns.TIME_KEY + " INTEGER);";

        private EarthQuakeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);

        }
    }
}
