package com.thisisstreamliner.mmb;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.thisisstreamliner.mmb.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("items", null, null, null, null, null, null);
        if (cursor != null)  {
            if (cursor.moveToFirst()) {
                Log.d(TAG, "onCreate: name: " + cursor.getString(cursor.getColumnIndex("name")));
            }
        }
    }
}