package com.dineout.billing;

import com.dineout.R;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    private ArrayList<OrderBill> mDataset;
    private Context c;
    private int itemLayout;
    OrdersViewHolder holder;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class OrdersViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView orderid;
        public TextView tableid;
        public TextView totalbill;
        Context c;

        public OrdersViewHolder(View v) {
            super(v);
            orderid = (TextView) v.findViewById(R.id.orderid_text);
            tableid = (TextView) v.findViewById(R.id.tableid_text);
            totalbill = (TextView) v.findViewById(R.id.totalbill_text);
        }

        public void setValues(OrderBill o) {
            orderid.setText("OrderBill ID: " + o.id);
            tableid.setText("Table: " + o.table);
            totalbill.setText("Total Bill: " + o.getTotal());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrdersAdapter(ArrayList<OrderBill> data, int layout, Context c) {
        mDataset = new ArrayList<OrderBill>(data);
        itemLayout = layout;
        this.c = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrdersAdapter.OrdersViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(itemLayout, parent, false);
        holder = new OrdersViewHolder(v);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(OrdersViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (mDataset != null && holder != null) {
            holder.setValues(mDataset.get(position));
            final int x = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(ComplexRecyclerViewAdapter.this, "Item no: "+ position, Toast.LENGTH_LONG).show;
                    Toast.makeText(v.getContext(), "Opening OrderBill Details", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(c, ConfirmPayment.class);
                    i.putExtra("order", mDataset.get(x));
                    //i.putExtra("orderid", "1");
                    v.getContext().startActivity(i);
                }
            });
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}