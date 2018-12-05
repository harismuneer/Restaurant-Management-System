package com.dineout.code.kitchen;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dineout.R;

public class OrderViewHolder extends RecyclerView.ViewHolder
{
    public CardView order_row;
    public TextView order_name;
    public TextView order_serving;
    public TextView eta;

    public Button button;

    public OrderViewHolder(View view)
    {
        super(view);

        order_row = itemView.findViewById(R.id.order_row_id);
        order_name = itemView.findViewById(R.id.order_name);
        order_serving = itemView.findViewById(R.id.order_serving);
        button=itemView.findViewById(R.id.button2);
        eta = itemView.findViewById(R.id.eta);
    }
}
