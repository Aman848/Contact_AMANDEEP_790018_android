package com.example.contact_amandeep_790018_android;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.contact_amandeep_790018_android.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;

    List<Contact> contactList;
    ListView contactListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact2);

        contactListView = findViewById(R.id.lv_contacts);
        contactList = new ArrayList<>();

        sqLiteDatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);
        loadContacts();
    }

    private void loadContacts() {
        String sql = "SELECT * FROM contact";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

     //   Cursor cursor = sqLiteDatabase.getAllContacts();

        if (cursor.moveToFirst()) {
            do {
                // create an contact instance
                contactList.add(new Contact(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4),
                        cursor.getString(5)
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        // create an adapter to display the contacts
    //    ContactAdapter contactAdapter = new ContactAdapter(this,
    //            R.layout.list_layout_contact,
    //            contactList,
   //             sqLiteDatabase);
    }
}