package com.dineout.code.order;
import com.dineout.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class setQuantity extends AppCompatActivity {
    int quantity = 1;
    String dishname="";

    ArrayList<MenuItem> inc =new ArrayList<MenuItem>();  // array of items
    ArrayList<Integer> menuQunatity = new ArrayList<Integer>(); // array of ingredients of that

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_set_quantity);
        ListView listview=(ListView)findViewById(R.id.ingridientListView);
        final CustomAdaptor cad=new CustomAdaptor();
        listview.setAdapter(cad);
        ImageButton closeQuantity = (ImageButton) findViewById(R.id.closeQuantity);

        Button add=(Button)findViewById(R.id.add);


        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences sharedPreferences=getSharedPreferences("shared preferences",MODE_PRIVATE);
        dishname = sharedPreferences.getString(("dishName"), "");  // taking dishname from menu when clicked add
        //dishname="Soup";
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("MenuItem").child(dishname);  // accessing the ingredients corresponding to the dishname
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                        for(DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            MenuItem MI = ds.getValue(MenuItem.class);
                            String displayname = MI.getDishName();
                            inc.add(MI);   // storing the relevant list of ingredient in inc  and listview adapter updated for display
                            cad.notifyDataSetChanged(); // notify adapter on inc
                        }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        closeQuantity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {    // cross button redirection
                // finish();
                startActivity(new Intent(setQuantity.this, MainActivity.class));
            }
        });

        final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Inventory");   //access ingredients from inventory
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                boolean invalid=false;
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        Item i = ds.getValue(Item.class);
                                        for (final MenuItem ms : inc) {    //iterate through dish ingredients and compute req quanitity of ingredients by multiplying servings
                                            if (ms.getIngredientName().equals(i.getName())) {
                                                int requiredamount = (Integer.parseInt(ms.getQuantity())) * (quantity);
                                                if (requiredamount >= Integer.parseInt(i.getThreshold())) {   // if exceeeds threshold
                                                    quantityInvalidPrompt qp = new quantityInvalidPrompt();
                                                    qp.show(getSupportFragmentManager(), "Quantity is Invalid");
                                                    invalid = true;

                                                    //generate notification!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                                                    final long CURRENT_TIME_MILLIS = System.currentTimeMillis();
                                                    Date instant = new Date(CURRENT_TIME_MILLIS);
                                                    SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm" );
                                                    String time = sdf.format( instant );
                                                    NotificationClass notif = new NotificationClass(i.getName(), time,false);
                                                    FirebaseDatabase.getInstance().getReference().child("notification").push().setValue(notif);  // added on firebase
                                                }
                                                else // if threshold not exceeded update inventory quantities
                                                {
                                                    int newQ=Integer.parseInt(i.quantity)- requiredamount;
                                                    Item item = new Item(i.getName(),i.getPrice(),String.valueOf(newQ),i.getThreshold());
                                                    ref2.child(i.getName()).setValue(item);
                                                }
                                            }
                                        }
                                    }
                                    if(invalid==false)   // if less then threshold add to cart
                                    {
                                           writeNewItemEntry(dishname, quantity);
                                            startActivity(new Intent(setQuantity.this, cartListView.class));  // redirect to cart
                                        }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



            }
        });




    }
    public void increaseInteger(View view) {  // + button inc
        quantity = quantity + 1;
        display(quantity);

    }public void decreaseInteger(View view) { //- button inc
        if(quantity !=0)
            quantity = quantity - 1;
        display(quantity);
    }

    private void display(int number) {      // display quanitity in interface
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number);
        displayInteger.setText("" + number);
    }

    private void writeNewItemEntry( String itemname, int q){    // func adding dish in  cart on firebase
        cart entry = new cart(itemname, q);
        FirebaseDatabase.getInstance().getReference().child("cart").push().setValue(entry);
    }


    class CustomAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return inc.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView =getLayoutInflater().inflate(R.layout.order_ingridient,null);
            TextView name=(TextView)findViewById(R.id.textView2_description);
            TextView dName=(TextView)convertView.findViewById(R.id.dishIngridient);
           // dName.setText(ingridientName[position]);

            dName.setText(inc.get(position).getIngredientName());
            name.setText(dishname);
            return convertView;
        }
    }
}
