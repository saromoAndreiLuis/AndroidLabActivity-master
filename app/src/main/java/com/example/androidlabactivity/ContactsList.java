package com.example.androidlabactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ContactsList extends AppCompatActivity {

    ListView contactListView;
    Button addContactButton, deleteContactButton;
    ArrayList<Contact> contactList;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        contactListView = findViewById(R.id.contactListView);
        addContactButton = findViewById(R.id.addContactButton);
        deleteContactButton = findViewById(R.id.deleteContactButton);

        contactList = new ArrayList<>();
        contactAdapter = new ContactAdapter(this, contactList);
        contactListView.setAdapter(contactAdapter);

        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddContactDialog();
            }
        });

        deleteContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteContactSelectionDialog();
            }
        });

        contactListView.setOnItemLongClickListener((parent, view, position, id) -> {
            showDeleteContactDialog(position);
            return true;
        });
    }

    private void showAddContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_contact, null);
        builder.setView(dialogView);

        final EditText inputName = dialogView.findViewById(R.id.input_name);
        final EditText inputPhone = dialogView.findViewById(R.id.input_phone);
        final EditText inputEmail = dialogView.findViewById(R.id.input_email);
        final EditText inputAge = dialogView.findViewById(R.id.input_age);

        // Set input filter for phone number to only allow digits
        InputFilter numberFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        inputPhone.setFilters(new InputFilter[]{numberFilter});

        builder.setTitle("Add Contact")
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String phone = inputPhone.getText().toString();
                String email = inputEmail.getText().toString();
                String ageStr = inputAge.getText().toString();

                if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || ageStr.isEmpty()) {
                    Toast.makeText(ContactsList.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(ContactsList.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                int age;
                try {
                    age = Integer.parseInt(ageStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(ContactsList.this, "Invalid age", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (age >= 100) {
                    Toast.makeText(ContactsList.this, "Age must be less than 100", Toast.LENGTH_SHORT).show();
                    return;
                }

                Contact newContact = new Contact(name, phone, email, age);
                addContact(newContact);
                dialog.dismiss();
            }
        });
    }

    private void showDeleteContactDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete this contact?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        contactList.remove(position);
                        contactAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    private void showDeleteContactSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Contact to Delete");

        String[] contactNames = new String[contactList.size()];
        for (int i = 0; i < contactList.size(); i++) {
            contactNames[i] = contactList.get(i).getName();
        }

        builder.setItems(contactNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDeleteContactDialog(which);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }

    public void addContact(Contact contact) {
        contactList.add(contact);
        contactAdapter.notifyDataSetChanged();
    }
}
