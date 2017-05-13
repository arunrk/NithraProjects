package nithra.tamil.jokes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by NITHRA-1 on 05/21/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    String DB_PATH = "";
    // Database Name
    public static String DATABASE_NAME = "jokes.db";
    // Current version of database
    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase database;
    Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
    }


    @Override
    public void onCreate(final SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDataBase() throws IOException {
        this.getReadableDatabase();
        try {
            copyDataBase();
        } catch (IOException e) {
            throw new Error("Error copying database");
        }
    }

    private void copyDataBase() throws IOException {
        System.out.println("copyDataBase"+ "---------------==================== copyDataBase");
        // Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }


    public void openDataBase() throws SQLException {
        // Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        database = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }







    public List<Jokes> getJokesCat() {
        List<Jokes> list = new ArrayList<Jokes>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        String query = "select distinct cat_id, cat from jokes_table";

        c = db.rawQuery(query, null);

        Log.d("QUERY : ", query);

        if (c.moveToFirst()) {
            do {
                String id = "";
                String cat = "";
                id = c.getString(c.getColumnIndex("cat_id"));
                cat = c.getString(c.getColumnIndex("cat"));

                Jokes jokes = new Jokes();
                jokes.setCat_id(id);
                jokes.setCat(cat);
                list.add(jokes);
            } while (c.moveToNext());
        }


        return list;
    }


    public List<Jokes> getSubCat(String catID) {
        List<Jokes> list = new ArrayList<Jokes>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        String query = "select distinct cat_id, sub_cat from jokes_table where cat_id = '" + catID + "'";

        c = db.rawQuery(query, null);

        Log.d("QUERY : ", query);

        if (c.moveToFirst()) {
            do {
                String id = "";
                String cat = "";
                id = c.getString(c.getColumnIndex("cat_id"));
                cat = c.getString(c.getColumnIndex("sub_cat"));

                Jokes jokes = new Jokes();
                jokes.setCat_id(id);
                jokes.setCat(cat);
                list.add(jokes);
            } while (c.moveToNext());
        }


        return list;
    }
}
