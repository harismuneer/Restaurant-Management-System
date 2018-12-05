package com.dineout.code.admin;

import com.dineout.R;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/*Making a dish using ingredients
Setting items from drop down
making object 
saving to firebase

*/

public class AddMenuItemActivity extends AppCompatActivity {

    DatabaseReference mDatabase;

    EditText name, time, type, price, quantity;
    List<String> items = new ArrayList<>();
    Spinner ingredientSpn;
    Button addMenu, addIngredient;
    ArrayList<MenuItem> menuItems = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_add_menu_item);

        name = (EditText) findViewById(R.id.AddDishName300);
        type = (EditText) findViewById(R.id.AddDishType300);
        price = (EditText) findViewById(R.id.AddDishPrice300);
        time = (EditText) findViewById(R.id.AddDishEstimatedTime300);
        addIngredient = (Button) findViewById(R.id.AddIngredientQuantityButton300);
        addMenu = (Button) findViewById(R.id.AddMenuItemButton300);
        quantity = (EditText) findViewById(R.id.AddQuantity300);
        ingredientSpn = (Spinner) findViewById(R.id.IngredientsDropDown300);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingredientSpn.setAdapter(adapter);


        mDatabase.child("Inventory").addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                if (dataSnapshot != null) {
                    //String s=dataSnapshot.getKey();
                    Item item = dataSnapshot.getValue(Item.class);
                    adapter.add(item.name);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity.getText().toString().length() <= 0) {
                    quantity.setError("Quantity is Required");
                } else {
                    MenuItem menuItem = new MenuItem(name.getText().toString(), ingredientSpn.getSelectedItem().toString(), quantity.getText().toString());
                    menuItems.add(menuItem);
                    quantity.setText("");
                    Toast.makeText(AddMenuItemActivity.this, "Ingredient Added Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean go = true;
                if (name.getText().toString().length() <= 0) {
                    name.setError("Dish Name is Required");
                    go = false;
                } else if (time.getText().toString().length() <= 0) {
                    time.setError("Est. Time is Required");
                    go = false;
                } else if (type.getText().toString().length() <= 0) {
                    type.setError("Dish Type is Required");
                    go = false;
                } else if (price.getText().toString().length() <= 0) {
                    price.setError("Price is Required");
                    go = false;
                }
                if (price.getText().toString().length() > 0 && Integer.parseInt(price.getText().toString()) < 1) {
                    price.setError("Price cannot be 0");
                    go = false;

                }
                if (menuItems.size() == 0) {
                    quantity.setError("No Ingredients Added");
                    go = false;
                }
                if (go) {
                    //Add intent here
                    Menu m = new Menu(name.getText().toString(), time.getText().toString(), type.getText().toString(), price.getText().toString());
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("Menu").child(m.getDishName()).setValue(m);
                    for (int i = 0; i < menuItems.size(); i++) {
                        ref.child("MenuItem").child(menuItems.get(i).dishName).child(menuItems.get(i).ingredientName).setValue(menuItems.get(i));
                    }
                    Toast.makeText(AddMenuItemActivity.this, "Menu Item Added Successfully", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
    }
}
