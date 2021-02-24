package com.example.contact_amandeep_790018_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.contact_amandeep_790018_android.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.contact_amandeep_790018_android.model.Contact;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Database name
    public static final String DATABASE_NAME = "my_database";
   // SQLiteDatabase sqLiteDatabase;

    // sqLite openHelper instance
    DatabaseHelper sqLiteDatabase;

    EditText etFname, etLname, etEmail, etPhno, etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFname = findViewById(R.id.editTextfname);
        etLname = findViewById(R.id.editTextlname);
        etEmail = findViewById(R.id.editTextEmailAddress);
        etPhno = findViewById(R.id.editTextPhone);
        etAddress = findViewById(R.id.editTextAddress);

        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);

        // initialise our database
        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createTable();

        // initializing the instance of sqLLite openHelper class
    //     sqLiteDatabase = new DatabaseHelper(this);

    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS contact (" +
                "id INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT, " +
                "fname VARCHAR(20) NOT NULL, " +
                "lname VARCHAR(20) NOT NULL, " +
                "email VARCHAR(20) NOT NULL, " +
                "ph_no DOUBLE NOT NULL," +
                "address VARCHAR(50) NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                addContact();
                break;
            case R.id.button3:
                startActivity(new Intent(this, ContactActivity.class));
                break;
        }

    }

    private void addContact() {
        String fname = etFname.getText().toString().trim();
        String lname = etLname.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phno = etPhno.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (fname.isEmpty()) {
            etFname.setError("fname field cannot be empty");
            etFname.requestFocus();
            return;
        }

        if (lname.isEmpty()) {
            etLname.setError("lname field cannot be empty");
            etLname.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("email field cannot be empty");
            etEmail.requestFocus();
            return;
        }

        if (phno.isEmpty()) {
            etPhno.setError("phno field cannot be empty");
            etPhno.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            etAddress.setError("address field cannot be empty");
            etAddress.requestFocus();
            return;
        }

        String sql = "INSERT INTO contacts (fname, lname, email, ph_no, address)" +
                "VALUES (?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(sql, new String[]{fname, lname, email, phno, address});
        Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show();

        // insert employee into database table with the help of database openHelper class
      /*  if (sqLiteDatabase.addContact(fname, lname, email, phno, address))
            Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Employee NOT Added", Toast.LENGTH_SHORT).show();*/

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        clearFields();
    }

    private void clearFields() {
        etFname.setText("");
        etLname.setText("");
        etEmail.setText("");
        etPhno.setText("");
        etAddress.setText("");
        // etName.clearFocus();
        //    etSalary.clearFocus();
    }
}



