package com.dineout.code.hall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dineout.R;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hall_activity_welcome);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                Intent myIntent = new Intent(Welcome.this, SelectUser.class);
                startActivity(myIntent);
            }
        });

        /*new FireBaseHelper().NewInventory("Pepperoni",100, 50,10);
        new FireBaseHelper().NewInventory("Chicken",100, 50,10);
        new FireBaseHelper().NewInventory("Beef",100, 50,10);
        new FireBaseHelper().NewInventory("Olive",100, 50,10);
        new FireBaseHelper().NewInventory("Mushroom",100, 50,10);
        new FireBaseHelper().NewInventory("Tomato",100, 50,10);
        new FireBaseHelper().NewInventory("Cheese",100, 50,10);
        new FireBaseHelper().NewInventory("Bread",100, 50,10);
        new FireBaseHelper().NewInventory("Turkey",100, 50,10);
        new FireBaseHelper().NewInventory("Cabbage",100, 50,10);
        new FireBaseHelper().NewInventory("Egg",100, 50,10);
        new FireBaseHelper().NewInventory("Salad",100, 50,10);

        new FireBaseHelper().NewMenu("Hot Dog",20,"Appetizer",150);
        new FireBaseHelper().NewMenu("Pizza",20,"Main Course",800);
        new FireBaseHelper().NewMenu("Burger",20,"Main Course",500);

        new FireBaseHelper().NewMenuItems("Hot Dog","Pepperoni",1);
        new FireBaseHelper().NewMenuItems("Hot Dog","Bread",2);
        new FireBaseHelper().NewMenuItems("Pizza","Pepperoni",6);
        new FireBaseHelper().NewMenuItems("Pizza","Chicken",3);
        new FireBaseHelper().NewMenuItems("Pizza","Beef",2);
        new FireBaseHelper().NewMenuItems("Pizza","Olive",8);
        new FireBaseHelper().NewMenuItems("Pizza","Mushroom",10);
        new FireBaseHelper().NewMenuItems("Pizza","Tomato",3);
        new FireBaseHelper().NewMenuItems("Pizza","Cheese",7);
        new FireBaseHelper().NewMenuItems("Pizza","Bread",8);
        new FireBaseHelper().NewMenuItems("Burger","Beef",5);
        new FireBaseHelper().NewMenuItems("Burger","Cheese",2);
        new FireBaseHelper().NewMenuItems("Burger","Turkey",3);
        new FireBaseHelper().NewMenuItems("Burger","Cabbage",2);
        new FireBaseHelper().NewMenuItems("Burger","Egg",2);
        new FireBaseHelper().NewMenuItems("Burger","Bread",2);
        new FireBaseHelper().NewMenuItems("Burger","Salad",2);*/

        /*new FireBaseHelper().NewOrder("200",new Timestamp(System.currentTimeMillis() / 1000).toString(),"4",2);
        new FireBaseHelper().NewOrderDetails("200","Pizza",20,0,2,3);

        new FireBaseHelper().NewOrder("203",new Timestamp(System.currentTimeMillis() / 1000).toString(),"4",2);
        new FireBaseHelper().NewOrderDetails("203","Burger",20,0,2,3);

        new FireBaseHelper().NewOrder("201",new Timestamp(System.currentTimeMillis() / 1000).toString(),"4",3);
        new FireBaseHelper().NewOrderDetails("201","Hot Dog",20,0,3,3);

        new FireBaseHelper().NewOrder("204",new Timestamp(System.currentTimeMillis() / 1000).toString(),"4",3);
        new FireBaseHelper().NewOrderDetails("204","Hot Dog",20,0,3,3);

        new FireBaseHelper().NewReceipt("201",0,30);
        new FireBaseHelper().NewReceipt("204",0,30);

        new FireBaseHelper().NewOrder("202",new Timestamp(System.currentTimeMillis() / 1000).toString(),"4",4);
        new FireBaseHelper().NewOrderDetails("202","Burger",20,0,4,3);*/

    }
}
