package com.dineout.code.kitchen;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dineout.R;

import com.dineout.code.kitchen.models.AttendanceDb;
import com.dineout.code.kitchen.models.Chef;
import com.dineout.code.kitchen.models.OrderDetailsDb;

import java.util.ArrayList;

public class CooksListActivity extends AppCompatActivity
{
    private ArrayList<Chef> mChefs = new ArrayList<>();
    public static int chefNo = 0;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.kitchen_activity_cooks_list);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffff8800")));

        mChefs = com.dineout.code.kitchen.MainActivity.mChefs;

        //mChefs = (ArrayList<Chef>) getIntent().getSerializableExtra("chefs_list");
        initRecyclerView(RecyclerViewAdapterOrdersOfCook.itemNo, getIntent().getExtras().getInt("cookNo"));
        Log.i("error12", getIntent().getIntExtra("cookNo",0) + " ");

        // initRecyclerView(RecyclerViewAdapterOrdersOfCook.itemNo, /*RecyclerViewAdapterOrdersOfCook.mCookNo*/0);
    }

    private void initRecyclerView(int itemNo, int cookNo)
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapterCookList adapter = new RecyclerViewAdapterCookList(this, mChefs, itemNo, cookNo, this);
        recyclerView.setAdapter(adapter);
    }
}
