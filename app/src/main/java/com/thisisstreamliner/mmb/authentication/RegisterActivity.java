package com.thisisstreamliner.mmb.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thisisstreamliner.mmb.MainActivity;
import com.thisisstreamliner.mmb.R;
import com.thisisstreamliner.mmb.Utils;
import com.thisisstreamliner.mmb.database.DatabaseHelper;
import com.thisisstreamliner.mmb.models.User;


public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private EditText mEmailEditText, mPasswordEditText, mAddressEditText, mNameEditText;
    private TextView mWarningTextView, mLoginTextView, mLicenseTextView;
    private ImageView mFirstImage, mSecondImage, mThirdImage, mForthImage, mFifthImage;
    private Button mRegisterButton;

    private DatabaseHelper mDataBaseHelper;

    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        mDataBaseHelper = new DatabaseHelper(this);

        imageUrl = "first";
        handleImageUrl();

        mRegisterButton.setOnClickListener(v -> initRegister());
    }

    private void initRegister() {
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        if (email.equals("") || password.equals("")) {
            mWarningTextView.setVisibility(View.VISIBLE);
            String warning = "Please enter with password and email";
            mWarningTextView.setText(warning);
        } else {
            mWarningTextView.setVisibility(View.GONE);

        }
    }

    private class DoesUserExist extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                SQLiteDatabase database = mDataBaseHelper.getReadableDatabase();
                Cursor cursor = database.query("users", new String[] {"_id", "email"},
                        "email=?", new String[]{strings[0]}, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        if (cursor.getString(cursor.getColumnIndex("email")).equals(strings[0])) {
                            cursor.close();
                            database.close();
                            return true;
                        } else {
                            cursor.close();
                            database.close();
                            return false;
                        }
                    } else {
                        cursor.close();
                        database.close();
                        return false;
                    }
                } else {
                    database.close();
                    return true;
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                mWarningTextView.setVisibility(View.VISIBLE);
                String warning = "There is user with this email. Please try another email";
                mWarningTextView.setText(warning);
            } else {
                mWarningTextView.setVisibility(View.GONE);

            }
        }
    }

    private class RegisterUser extends AsyncTask<Void, Void, User> {

        private String email;
        private String password;
        private String address;
        private String firstName;
        private String lastName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            email = mEmailEditText.getText().toString();
            password = mPasswordEditText.getText().toString();
            address = mAddressEditText.getText().toString();
            String name = mNameEditText.getText().toString();

            String names[] = name.split(" ");
            if (names.length >= 1) {
                firstName = names[0];
                for (int i = 1; i < names.length; i++) {
                    if (i > 1) {
                        lastName += " " + names[i];
                    } else {
                        lastName = names[i];
                    }
                }
            } else {
                firstName = names[0];
            }
        }

        @Override
        protected User doInBackground(Void... voids) {
            try {
                SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("email", email);
                values.put("password", password);
                values.put("address", address);
                values.put("first_name", firstName);
                values.put("last_name", lastName);
                values.put("remained_amount", 0.0);
                values.put("image_url", imageUrl);

                long userId = db.insert("users", null, values);
                Log.e(TAG, "doInBackground: userId " + userId);

                Cursor cursor = db.query("users", null, "_id=?",
                        new String[]{String.valueOf(userId)}, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        User user = new User();
                        user.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                        user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                        user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                        user.setFirstName(cursor.getString(cursor.getColumnIndex("first_name")));
                        user.setLastName(cursor.getString(cursor.getColumnIndex("last_name")));
                        user.setImageUrl(cursor.getString(cursor.getColumnIndex("image_url")));
                        user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                        user.setRemainedAmount(cursor.getDouble(cursor.getColumnIndex("remained_amount")));

                        cursor.close();
                        db.close();
                        return user;
                    } else {
                        cursor.close();
                        db.close();
                        return null;
                    }
                } else {
                    db.close();
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if (null != user) {
                Toast.makeText(RegisterActivity.this, "User " + user.getEmail() + " registered successfully.", Toast.LENGTH_SHORT).show();
                Utils utils = new Utils(RegisterActivity.this);
                utils.addUserToSharedPreferences(user);
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(RegisterActivity.this, "Wasn't able to register, please try again later", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleImageUrl() {
        Log.d(TAG, "handleImageUrl: started.");
        mFirstImage.setOnClickListener(v -> imageUrl = "first");
        mSecondImage.setOnClickListener(v -> imageUrl = "second");
        mThirdImage.setOnClickListener(v -> imageUrl = "third");
        mForthImage.setOnClickListener(v -> imageUrl = "forth");
        mFifthImage.setOnClickListener(v -> imageUrl = "fifth");
    }

    private void initViews() {
        mEmailEditText = findViewById(R.id.emailEditText);
        mPasswordEditText = findViewById(R.id.passwordEditText);
        mAddressEditText = findViewById(R.id.addressEditText);
        mNameEditText = findViewById(R.id.nameEditText);

        mWarningTextView = findViewById(R.id.warningTextView);
        mLoginTextView = findViewById(R.id.loginTextView);
        mLicenseTextView = findViewById(R.id.licinseTextView);

        mFirstImage = findViewById(R.id.firstImage);
        mSecondImage = findViewById(R.id.secondImage);
        mThirdImage = findViewById(R.id.thirdImage);
        mForthImage = findViewById(R.id.forthImage);
        mFifthImage = findViewById(R.id.fifthImage);

        mRegisterButton = findViewById(R.id.registerButton);

    }
}