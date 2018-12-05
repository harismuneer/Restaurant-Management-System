package com.dineout.code.order;
import com.dineout.R;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;

/*This class is responsible for showing pop up when Confirm Order is clicked by user in cart page*/

public class confirmOrder extends AppCompatDialogFragment {
    public static int order_id = 1;
    String dishName;
    //HARDCODED TABLEID
    int tableid = 4, servingSize;

    //data available locally on app when offline
    //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    FirebaseDatabase appeteaserDb = FirebaseDatabase.getInstance();
    final DatabaseReference appDb = FirebaseDatabase.getInstance().getReference("");

    //TABLEID ACCESSED THROUGH SHARED PREFERENCES
    //SINCE NOTHING RECEIVED YET, TABLEID IS HARDCODED AS 4
    //SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    //SharedPreferences.Editor editor = mPreferences.edit();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.order_orderconfirmpopup,null);//specifies xml file of confirmation popup
        builder.setView(view);
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(confirmOrder.this.getActivity(), cartListView.class));
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //get Cart Object from Db to get quantity and dishname
                appDb.child("cart").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        for(DataSnapshot dsCart: dataSnapshot.getChildren())
                        {
                            //snapshot object's data saved in variables
                            cart c = dsCart.getValue(cart.class);
                            servingSize = c.getQuantity();
                            dishName = c.getAddedname();
                        }

                        //ONLY EXECUTE IF IT HAS READ FROM EXISTING CART
                        if(dishName != null)
                        {
                            //create Order and OrderDetails objects and pass to Db
                            String ts = new Timestamp(System.currentTimeMillis()).toString();
                            Order ord = new Order(Integer.toString(order_id), Integer.toString(tableid), ts, 0);
                            OrderDetails od = new OrderDetails(dishName, 0, Integer.toString(order_id), 0, servingSize, 0);

                            DatabaseReference addRow;

                            addRow = appDb.child("Order").push();
                            addRow.setValue(ord);
                            addRow = appDb.child("OrderDetails").push();
                            addRow.setValue(od);
                            order_id++;
                            appDb.child("cart").removeValue();
                        }
                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });

                startActivity(new Intent(confirmOrder.this.getActivity(), Time.class));
            }
        });

        return builder.create();
    }
}
