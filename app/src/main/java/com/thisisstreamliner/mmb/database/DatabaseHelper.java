package com.thisisstreamliner.mmb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static final String DB_NAME = "manage_money_balance";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate : started");
        String createUserTable = "CREATE TABLE users (_id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "first_name TEXT, last_name TEXT, address TEXT, image_url TEXT, remained_amount DOUBLE)";

        String createShoppingTable = "CREATE TABLE shopping (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "item_id INTEGER, user_id INTEGER, transaction_id INTEGER, price DOUBLE, date DATE, " +
                "description TEXT)";

        String createInvestmentTable = "CREATE TABLE investments (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "amount DOUBLE, monthly_roi DOUBLE, name TEXT, init_date DATE, finish_date DATE, " +
                "user_id INTEGER, transaction_id INTEGER)";

        // roi - rate of interest
        String createLoansTable = "CREATE TABLE loans (_id INTEGER PRIMARY KEY AUTOINCREMENT, init_date DATE, " +
                "finish_date DATE, init_amount DOUBLE, remained_amount DOUBLE, monthly_payment DOUBLE, " +
                "monthly_roi DOUBLE, name TEXT, user_id INTEGER)";

        db.execSQL(createUserTable);
        db.execSQL(createShoppingTable);
        db.execSQL(createInvestmentTable);
        db.execSQL(createLoansTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
