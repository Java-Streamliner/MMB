package com.thisisstreamliner.mmb.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.thisisstreamliner.mmb.R;
import com.thisisstreamliner.mmb.database.DatabaseHelper;


public class RegisterActivity extends AppCompatActivity {

    private EditText mEmailEditText, mPasswordEditText, mAddressEditText, mNameEditText;
    private TextView mWarningTextView, mLoginTextView, mLicenseTextView;
    private ImageView mFirstImage, mSecondImage, mThirdImage, mForthImage, mFifthImage;
    private Button mRegisterButton;

    private DatabaseHelper mDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        mDataBaseHelper = new DatabaseHelper(this);

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