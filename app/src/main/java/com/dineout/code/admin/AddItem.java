package com.dineout.code.admin;

import com.dineout.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/*Making a new inventory item validations and pushing to firebase*/
public class AddItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_add_item);
    }

    public void btnBlick(View v) {

        EditText name = (EditText) findViewById(R.id.AddItemName300);
        EditText price = (EditText) findViewById(R.id.AddItemPrice300);
        EditText quantity = (EditText) findViewById(R.id.AddItemQuantity300);
        EditText threshold = (EditText) findViewById((R.id.AddItemThreshold300));
        boolean go = true;
        if (name.getText().toString().length() <= 0) {
            name.setError("Name is empty");
            go = false;
        }
        if (price.getText().toString().length() <= 0) {
            price.setError("Price is empty");
            go = false;
        }
        if (quantity.getText().toString().length() <= 0) {
            quantity.setError("Quantity is empty");
            go = false;
        }
        if (threshold.getText().toString().length() <= 0) {
            threshold.setError("Threshold is empty");
            go = false;
        }
        if (price.getText().toString().length() > 0 && Integer.parseInt(price.getText().toString()) < 1) {
            price.setError("Price must be greater than 0");
            go = false;
        }
        if (quantity.getText().toString().length() > 0 && Integer.parseInt(quantity.getText().toString()) < 1) {
            quantity.setError("Quantity must be greater than 0");
            go = false;
        }
        if (threshold.getText().toString().length() > 0 && Integer.parseInt(threshold.getText().toString()) < 1) {
            quantity.setError("Threshold must be greater than 0");
            go = false;
        }
        if (go) {
            Item i = new Item(name.getText().toString(), price.getText().toString(), quantity.getText().toString(), threshold.getText().toString());
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            ref.child("Inventory").child(name.getText().toString()).setValue(i);
            Toast.makeText(this, "Item has been added to the Inventory", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(this, AdminPanelActivity.class);
            startActivity(it);
            //finish this activity
        }
    }
}
