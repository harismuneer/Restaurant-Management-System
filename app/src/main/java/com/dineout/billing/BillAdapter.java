package com.dineout.billing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dineout.R;


import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    public ArrayList<DishOrder> mDataset;
    private Context c;
    private int itemLayout;
    boolean tap;
    BillViewHolder holder;
    BillAdapter adapter2;
    TextView bill1;
    TextView bill2;

    public BillAdapter getAdapter2() {
        return adapter2;
    }

    public void setAdapter2(BillAdapter adapter2) {
        this.adapter2 = adapter2;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class BillViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView itemid;
        public TextView dishname;
        public TextView unitprice;
        public TextView quantity;
        public TextView price;
        Context c;

        public BillViewHolder(View v) {
            super(v);
            itemid = (TextView) v.findViewById(R.id.itemid_bill);
            dishname = (TextView) v.findViewById(R.id.dishname_bill);
            unitprice = (TextView) v.findViewById(R.id.unitprice_bill);
            quantity = (TextView) v.findViewById(R.id.quantity_bill);
            price = (TextView) v.findViewById(R.id.price_bill);

        }

        public void setValues(DishOrder o) {
            dishname.setText(o.dishName);
            unitprice.setText("Unit Price: " + String.valueOf(o.price));
            quantity.setText("Quantity: " + String.valueOf(o.quantity));
            price.setText("Price: " + String.valueOf(o.getTotal()));
        }
    }

    public TextView getBill1() {
        return bill1;
    }

    public void setBill1(TextView bill1) {
        this.bill1 = bill1;
    }

    public TextView getBill2() {
        return bill2;
    }

    public void setBill2(TextView bill2) {
        this.bill2 = bill2;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BillAdapter(ArrayList<DishOrder> data, int layout, Context c) {
        mDataset = new ArrayList<DishOrder>(data);
        itemLayout = layout;
        this.c = c;
        tap = false;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public BillAdapter.BillViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(itemLayout, parent, false);
        holder = new BillViewHolder(v);
        return holder;
    }


    public DishOrder deleteItem(int i) {
        DishOrder o = mDataset.get(i);
        mDataset.remove(i);
        OrderBill x = new OrderBill();
        x.setD(mDataset);
        int y = x.getTotal();
        bill1.setText("Bill: " + String.valueOf(y));
        notifyItemRemoved(i);
        notifyItemRangeChanged(0, mDataset.size());
        adapter2.mDataset.add(o);
        OrderBill j = new OrderBill();
        j.setD(adapter2.mDataset);
        int z = j.getTotal();
        bill2.setText("Bill: " + String.valueOf(z));
        adapter2.notifyDataSetChanged();

        return o;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BillViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (mDataset != null && holder != null) {
            holder.setValues(mDataset.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tap == true) {
                        // Toast.makeText(ComplexRecyclerViewAdapter.this, "Item no: "+ position, Toast.LENGTH_LONG).show;
                        DishOrder o = deleteItem(position);

                    }
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
