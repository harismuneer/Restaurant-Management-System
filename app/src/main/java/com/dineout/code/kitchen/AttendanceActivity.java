package com.dineout.code.kitchen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.dineout.R;

import com.dineout.code.kitchen.models.AttendanceDb;
import com.dineout.code.kitchen.models.Chef;
import com.dineout.code.kitchen.models.OrderDetailsDb;

import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity
{
    ActionBar actionBar;
    public static ArrayList<Chef> mChefs = new ArrayList<>();
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kitchen_activity_attendance2);

        //setContentView(R.layout.kitchen_activity_main);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffff8800")));
        actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>AROS </font>"));

        //getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.cardview_dark_background)));
        //ExtractChefsFromDb();
        mChefs = (ArrayList<Chef>) getIntent().getExtras().get("chefs");
        initRecyclerView();
    }

    private void ExtractChefsFromDb()
    {
        mChefs.clear();
        mChefs.add(new Chef("Chef 1"));
        mChefs.add(new Chef("Chef 2"));
        mChefs.add(new Chef("Chef 3"));
        mChefs.add(new Chef("Chef 4"));
        mChefs.add(new Chef("Chef 5"));
        mChefs.add(new Chef("Chef 6"));
        mChefs.add(new Chef("Chef 7"));
        mChefs.add(new Chef("Chef 8"));
        mChefs.add(new Chef("Chef 9"));
        mChefs.add(new Chef("Chef 10"));
        mChefs.add(new Chef("Chef 11"));
        mChefs.add(new Chef("Chef 12"));

        initRecyclerView();
    }

    private void initRecyclerView()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.attendance_rv);
        recyclerView.setLayoutManager(layoutManager);

        com.dineout.code.kitchen.RecyclerViewAdapterAttendanceList adapter = new com.dineout.code.kitchen.RecyclerViewAdapterAttendanceList(this, mChefs);
        recyclerView.setAdapter(adapter);


    }

    public void doneClick(View view){

        ArrayList<Chef> chefs = ((RecyclerViewAdapterAttendanceList)recyclerView.getAdapter()).getmChefs();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        ProgressDialog progressDialog = new ProgressDialog(AttendanceActivity.this);
        progressDialog.setMessage("Uploading Attendance");
        progressDialog.show();

        for (Chef chef:chefs){
            if(!chef.isPresent()){
                if(chef.isCooking()){
                    Toast.makeText(this, chef.getName()+ " is cooking!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
                for(OrderDetailsDb dish:chef.getChefQueue()){
                    MainActivity.orderDetails.add(dish);
                }
                chef.setChefQueue(new ArrayList<OrderDetailsDb>());
            }

            DatabaseReference myRef = database.getReference("Attendance/" + chef.getId());
            myRef.setValue(new AttendanceDb(chef.getId(), chef.isPresent()));
        }

        progressDialog.dismiss();

        Intent intent = new Intent();
        this.setResult(RESULT_OK, intent);
        finish();
    }
}
