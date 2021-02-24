package com.example.contact_amandeep_790018_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contact_amandeep_790018_android.model.Contact;
import com.example.contact_amandeep_790018_android.R;
import com.example.contact_amandeep_790018_android.util.DatabaseHelper;

import java.util.List;

public class ContactAdapter extends ArrayAdapter {

    Context context;
    int layoutRes;
    List<Contact> contactList;
    SQLiteDatabase sqLiteDatabase;
   DatabaseHelper sqLiteDatabase;

    public ContactAdapter(@NonNull Context context, int resource, List<Contact> contactList ) {
        super(context, resource, contactList);
        this.contactList = contactList;
        this.sqLiteDatabase = sqLiteDatabase;
        this.context = context;
        this.layoutRes = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = convertView;
        if (v == null) v = inflater.inflate(layoutRes, null);
        TextView fnameTV = v.findViewById(R.id.tv_fname);
        TextView lnameTV = v.findViewById(R.id.tv_lname);
        TextView phnoTV = v.findViewById(R.id.tv_ph_no);

        final Contact contact = contactList.get(position);
        fnameTV.setText(contact.getFname());
        lnameTV.setText(contact.getLname());
        phnoTV.setText(String.valueOf(contact.getPhno()));

        v.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact(contact);
            }

            private void updateContact(final Contact contact) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.dialog_update_contact, null);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final EditText etFname = view.findViewById(R.id.editTextfname);
                final EditText etLname = view.findViewById(R.id.editTextlname);
                final EditText etEmail = view.findViewById(R.id.editTextEmailAddress);
                final EditText etPhno = view.findViewById(R.id.Phone);
                final EditText etAddress = view.findViewById(R.id.editTextAddress);


                //    String[] deptArray = context.getResources().getStringArray(R.array.departments);
                //     int position = Arrays.asList(deptArray).indexOf(employee.getDepartment());

                etFname.setText(contact.getFname());
                etLname.setText(contact.getLname());
                etEmail.setText(contact.getEmail());
                etPhno.setText(String.valueOf(contact.getPhno()));
                etAddress.setText(contact.getAddress());


                view.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                        String sql = "UPDATE contact SET fname = ?, lname = ?, email = ?, ph_no = ?, address = ?, WHERE id = ?";
                        sqLiteDatabase.execSQL(sql, new String[]{fname, lname, email, phno, address, String.valueOf(contact.getId())});
                        if (sqLiteDatabase.updateContact(contact.getId(), fname, lname, email, address, Double.parseDouble(phno)))
                            loadContacts();


                    }

                });
            }
        });


        v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact(contact);
            }

            private void deleteContact(final Contact contact) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sql = "DELETE FROM contact WHERE id = ?";
                        sqLiteDatabase.execSQL(sql, new Integer[]{contact.getId()});
                        if (sqLiteDatabase.deleteContact(contact.getId()))
                            loadContacts();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "The contact (" + contact.getFname() + ") is not deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        Log.d(TAG, "getView: " + getCount());
        return v;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    private void loadContacts() {
        String sql = "SELECT * FROM contact";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

      //  Cursor cursor = sqLiteDatabase.getAllContacts();

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
    }
}

