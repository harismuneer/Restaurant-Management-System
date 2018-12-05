package com.dineout.code.admin;

import com.dineout.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/*spinen to select end of week day*/
public class EndOfWeekActivitiy extends AppCompatActivity {

    Spinner days;
    String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_set_end_of_week);
        days = (Spinner) findViewById(R.id.SelectDayDropDown301);

    }

    public void onClickReg13(View v) {
        selectedItem = days.getSelectedItem().toString();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("EndOfWeek").setValue(selectedItem);
        Toast.makeText(EndOfWeekActivitiy.this, "Set End of Child Successful.", Toast.LENGTH_SHORT).show();

    }
}
