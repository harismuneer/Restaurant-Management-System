package com.dineout.code.admin;

import com.dineout.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IngredientsList extends AppCompatActivity {
    private ListView listView;
    DatabaseReference mDatabase;
    IngredientsListAdapter ingredientsListAdapter;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<IngredientRow> ingredients = new ArrayList<>();
    private ArrayList<NotificationClass> notifications1 = new ArrayList<>();
    private ArrayList<NotificationClass> notifications2 = new ArrayList<>();

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(); //firebase database ka object get kia
    DatabaseReference databaseReference = firebaseDatabase.getReference("notification"); //table lia

    private boolean first = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_ingredients_view);
        listView = (ListView) findViewById(R.id.IngredientsList301);

        first = true;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //ArrayList<Item> items = new ArrayList<>();
        ingredientsListAdapter = new IngredientsListAdapter(IngredientsList.this, this.ingredients, notifications1);
        listView.setAdapter(ingredientsListAdapter);


        mDatabase.child("Inventory").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                if (dataSnapshot != null) {

                    //String s=dataSnapshot.getKey();
                    Item item = dataSnapshot.getValue(Item.class);
                   // Toast.makeText(IngredientsList.this, item.name.toString(), Toast.LENGTH_SHORT).show();
                    items.add(item);
                    ingredients.clear();
                    for (int i = 0; i < items.size(); i++) {
                        ingredients.add(new IngredientRow(items.get(i).getName(), items.get(i).getQuantity()));
                        //Toast.makeText(this, "a1", Toast.LENGTH_SHORT).show();
                    }
                    ingredientsListAdapter.notifyDataSetChanged();
                    //listView.refreshDrawableState();
                    //Toast.makeText(IngredientsList.this, Integer.toString(items.size()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot != null) {

                    //String s=dataSnapshot.getKey();
                    Item item = dataSnapshot.getValue(Item.class);
                    //Toast.makeText(IngredientsList.this, item.name.toString(), Toast.LENGTH_SHORT).show();
                    items.add(item);
                    ingredients.clear();
                    for (int i = 0; i < items.size(); i++) {
                        ingredients.add(new IngredientRow(items.get(i).getName(), items.get(i).getQuantity()));
                        //Toast.makeText(this, "a1", Toast.LENGTH_SHORT).show();
                    }
                    ingredientsListAdapter.notifyDataSetChanged();
                    //listView.refreshDrawableState();
                    //Toast.makeText(IngredientsList.this, Integer.toString(items.size()), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            NotificationClass notification;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    notification = dsp.getValue(NotificationClass.class);
                    if (notification != null) {
                        notifications1.add(0, notification);
                    }
                }
                //notificationsAdapter = new NotificationsAdapter(getBaseContext(),notifications);
                //rvNotifications.setAdapter(notificationsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Database Error: ", databaseError.toString());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(IngredientsList.this, Updateitem.class);
                intent.putExtra("name", ingredients.get(i).getName());
                intent.putExtra("quantity", ingredients.get(i).getQuantity());
                startActivity(intent);
            }
        });


    }

    @Override
    public void onRestart() {  // After a pause OR at startup
        super.onRestart();
        //Refresh your stuff here
        finish();
    }
    // ...
}
