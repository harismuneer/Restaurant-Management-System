package com.dineout.code.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dineout.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/*
Admin interface
all junctinoalities corresponding to buttons
*/

public class AdminPanelActivity extends AppCompatActivity {
    Button notificationButton;
    DatabaseReference databaseReference; //TABLE LIA
    FirebaseDatabase firebaseDatabase; //FIREBASE DATABASE KA OBJECT GET KIA

    String date1 = null;
    Date d1;
    Date d2;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    static ArrayList<NotificationClass> notf = new ArrayList<NotificationClass>();
    static ArrayList<Item> itm = new ArrayList<Item>();
    static ArrayList<String> keys = new ArrayList<String>();
    static Boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_admin_menu);

        notificationButton = (Button) findViewById(R.id.ViewNotificationsButton301);
        checkdate();


        notificationButton.setBackgroundResource((R.drawable.mybutton));
        notificationButton.setTextColor(getResources().getColor(R.color.black));

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Notifications.class);
                startActivity(i);
            }
        });

        /*notifications inventry ka item threshold say neechay pe notification ka color change hoga*/
        firebaseDatabase = FirebaseDatabase.getInstance(); //FIREBASE DATABASE KA OBJECT GET KIA

        databaseReference = firebaseDatabase.getReference("notification"); //TABLE LIA


        databaseReference.addValueEventListener(new ValueEventListener() {
            com.dineout.code.admin.NotificationClass notification;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (dsp != null) {
                        notification = dsp.getValue(com.dineout.code.admin.NotificationClass.class);
                        if (notification != null && notification.getTime()!=null && notification.getItemName()!=null){
                            if (notification.isRead() == false) {
                                notificationButton.setText("New Notification");
                                notificationButton.setBackgroundColor(getResources().getColor(R.color.red)); //DON'T CHANGE THE COLORS HERE LOL
                                notificationButton.setTextColor(getResources().getColor(R.color.white)); //DON'T CHANGE THE COLORS HERE LOL
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Database Error: ", databaseError.toString());
            }
        });


    }

    //Add inventory item

    public void onClickReg(View v) {
        Intent i = new Intent(this, AddItem.class);
        startActivity(i);
    }

    //ingredients/raw items threshold say kam row red
    public void onClickReg1(View v) {
        Intent i = new Intent(this, IngredientsList.class);
        startActivity(i);
    }

    //add new emloyee activity
    public void onClickReg2(View v) {
        Intent i = new Intent(this, AddEmployeeActivity.class);
        startActivity(i);
    }

    //add tablet activity
    public void onClickReg3(View v) {
        Intent i = new Intent(this, AddTabletActivity.class);
        startActivity(i);
    }

    //add table activityy
    public void onClickReg4(View v) {
        Intent i = new Intent(this, AddTableActivity.class);
        startActivity(i);
    }


    //making a dish and setting ingedients and theri quantity
    public void onClickReg5(View v) {
        Intent i = new Intent(this, AddMenuItemActivity.class);
        startActivity(i);
    }

    //end of week activty sets
    public void onClickReg6(View v) {
        Intent i = new Intent(this, EndOfWeekActivitiy.class);
        startActivity(i);
    }

    //low threshold notifications of ingredients/inventory items
    public void onClickReg7(View v) {
        Intent i = new Intent(this, Notifications.class);
        startActivity(i);
    }

    /*notification button state restore. also checking new notificatinos*/
    @Override
    protected void onResume() {
        notificationButton.setBackgroundResource((R.drawable.mybutton));
        notificationButton.setTextColor(getResources().getColor(R.color.black));
        notificationButton.setText("Notifications");
        databaseReference.addValueEventListener(new ValueEventListener() {
            com.dineout.code.admin.NotificationClass notification;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (dsp != null) {
                        notification = dsp.getValue(com.dineout.code.admin.NotificationClass.class);
                        if (notification != null && notification.getTime()!=null && notification.getItemName()!=null) {
                            if (notification.isRead() == false) {
                                notificationButton.setText("New Notification");
                                notificationButton.setBackgroundColor(getResources().getColor(R.color.red)); //DON'T CHANGE THE COLORS HERE LOL
                                notificationButton.setTextColor(getResources().getColor(R.color.white)); //DON'T CHANGE THE COLORS HERE LOL
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onResume();
    }

    //options menu for logout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {

            Intent intent;
            intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void checkDate() {
    // Get the current date
    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    // Get the stored date from Firebase Realtime Database
    DatabaseReference dateReference = FirebaseDatabase.getInstance().getReference("Date");
    dateReference.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {
            String storedDate = dataSnapshot.getValue(String.class);

            // Compare the current date with the stored date
            if (isSameDate(currentDate, storedDate)) {
                // Update the stored date in Firebase Realtime Database
                dateReference.child("date").setValue(currentDate);

                // Check the database for notifications
                checkDatabase();
            }
        }

        // Other methods of ChildEventListener
    });
}

private boolean isSameDate(String date1, String date2) {
    try {
        DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        Date d1 = f.parse(date1);
        Date d2 = f.parse(date2);
        return d1.compareTo(d2) == 0;
    } catch (ParseException e) {
        return false;
    }
}

    public void checkdb() {
        firebaseDatabase = FirebaseDatabase.getInstance(); //FIREBASE DATABASE KA OBJECT GET KIA

        databaseReference = firebaseDatabase.getReference("notification"); //TABLE LIA

        databaseReference.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {
                keys.add(dataSnapshot.getKey());
                notf.add(dataSnapshot.getValue(NotificationClass.class));

            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        firebaseDatabase = FirebaseDatabase.getInstance(); //FIREBASE DATABASE KA OBJECT GET KIA

        databaseReference = firebaseDatabase.getReference("Inventory"); //TABLE LIA
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                removenotif();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {

                itm.add(dataSnapshot.getValue(Item.class));

            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void removenotif() {
        for (int i = 0; i < notf.size(); i++) {
            for (int j = 0; j < itm.size(); j++) {
                if (notf.get(i).getItemName().equals(itm.get(j).getName())) {
                    if (Integer.parseInt(itm.get(j).getQuantity()) > Integer.parseInt(itm.get(j).getThreshold()))
                        FirebaseDatabase.getInstance().getReference().child("notification").child(keys.get(i)).removeValue();
                }

            }
        }
    }
}
