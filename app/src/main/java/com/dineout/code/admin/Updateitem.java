package com.dineout.code.admin;

import com.dineout.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



/*updating inventory items quantity etc*/

public class Updateitem extends AppCompatActivity {

    EditText name, quantity;
    String namee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_updateitem);
        name = (EditText) findViewById(R.id.ItemNameToUpdate301);
        quantity = (EditText) findViewById(R.id.ItemQuantityToUpdate);

        Intent i = getIntent();
        name.setText(i.getStringExtra("name"));
        namee = i.getStringExtra("name");
        quantity.setText(i.getStringExtra("quantity"));

    }

    public void onClick(View v)

    {

        String val = quantity.getText().toString();
        final DatabaseReference db = FirebaseDatabase.getInstance().getReference("Inventory").child(namee);
        db.child("quantity").setValue(val);
        finish();

//        Intent i= new Intent(this,IngredientsList.class);
//        startActivity(i);

    }

}
