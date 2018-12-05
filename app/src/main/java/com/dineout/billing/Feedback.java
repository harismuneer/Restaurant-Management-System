package com.dineout.billing;

import com.dineout.R;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {
    OrderBill o;
    String orderid;

    TextView ordertext;
    RatingBar rbar;
    EditText email;
    EditText para;

    String emailid;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference();

    Context c = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing_activity_feedback);
        Bundle extras = getIntent().getExtras();

        orderid = extras.getString("orderid");
        o = (OrderBill) extras.getSerializable("order");

        TextView ordertext = findViewById(R.id.orderid_feedback);
        ordertext.setText("OrderID: " + orderid);

        rbar = (RatingBar) findViewById(R.id.ratingBar_feedback);
        email = (EditText) findViewById(R.id.email_feedback);
        para = (EditText) findViewById(R.id.editText2);
        Button submit = (Button) findViewById(R.id.submit_feedback);
        submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          //OnClick for send feedback
                                          //Saves feedback in firebase
                                          //Construct an email object

                                          ///IMPORTANT
                                          //you should have made the email field compulsory to be filled else it would crash
                                          //when gmail tries to send email to an empty field

                                          emailid = email.getText().toString();
                                          Toast.makeText(v.getContext(), "Feedback Submitted!", Toast.LENGTH_SHORT).show();

                                          ref.child("Feedback").child(orderid).child("orderid").setValue(orderid);
                                          ref.child("Feedback").child(orderid).child("rating").setValue(rbar.getRating());
                                          ref.child("Feedback").child(orderid).child("email").setValue(emailid);
                                          ref.child("Feedback").child(orderid).child("comments").setValue(para.getText().toString());


                                          //The details for constructed email are given in the message variable

                                          //emailid is the recipient's email
                                          //orderid and o.id are the orderid of the order the feedback is being submitted for
                                          //o.D is the ArrayList of dishes ordered by the customer
                                          //o.D.get().dishname is name of dish
                                          //o.D.get().getTotal() gets total sum of all dishes in the order
                                          //Email is constructed in the code below. Has to be replaced with javax.

                                          Intent i = new Intent(android.content.Intent.ACTION_SEND);
                                          i.setType("plain/text");

                                          i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailid});

                                          i.putExtra(Intent.EXTRA_SUBJECT, "SMARTSERV - Receipt for your Order");
                                          String message = "Greetings Customer,\n" +
                                                  "Hope you enjoyed your meal at our restaurant. \n" +
                                                  "Your receipt for your order is given below: \n\n";

                                          //message = message + "OrderBill ID: " + o.id + "\n";

                                          for (int j = 0; j < o.D.size(); j++) {
                                              message = message + "Dish: " + o.D.get(j).dishName + " \tPrice: " + o.D.get(j).getTotal() + "\n";
                                          }
                                          message = message + "Total Bill: " + o.getTotal() + "\n\nWe hope to see you again!\n\nRegards,\nSMARTSERV";

                                          i.putExtra(Intent.EXTRA_TEXT, message);

                                          try {
                                              startActivity(Intent.createChooser(i, "Select your email client: "));
                                          } catch (android.content.ActivityNotFoundException ex) {
                                              Toast.makeText(c, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                          }
                                      }
                                  }
        );
    }
}
