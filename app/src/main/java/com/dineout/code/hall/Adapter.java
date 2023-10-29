package com.dineout.code.hall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dineout.code.hall.DB.Assignment;
import com.dineout.code.hall.DB.BillStatus;
import com.dineout.code.hall.DB.Order;
import com.dineout.code.hall.DB.OrderDetails;
import com.dineout.code.hall.DB.Table;
import com.dineout.code.hall.DB.Tablet;
import com.dineout.code.hall.DB.Receipt;

import com.dineout.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khalid on 11/25/2018.
 */

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Table> tables = new ArrayList<>();
    ArrayList<Tablet> tablets = new ArrayList<>();
    ArrayList<BillStatus> billstatus = new ArrayList<>();
    ArrayList<Order> serveorder = new ArrayList<>();
    ArrayList<Assignment> track = new ArrayList<>();
    int Flag;


    private OnItemClickListener mlistener;
    public interface OnItemClickListener
    {
        void OnItemClick(int position);
        void OnFreeClick(int position);
        void OnBookClick(int position);
        void OnOccupyClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mlistener = listener;
    }

    public Adapter(Context context, int Flag, ArrayList<Table> tables, ArrayList<Tablet> tablets, ArrayList<BillStatus> bill, ArrayList<Order> serveorder, ArrayList<Assignment> track) {
        this.Flag=Flag;
        this.context = context;
        this.tables = tables;
        this.tablets = tablets;
        this.billstatus = bill;
        this.serveorder = serveorder;
        this.track = track;
    }

    @Override
    public int getItemCount() {

        if(Flag == 0)
            return this.tables.size();
        else if(Flag == 1)
            return this.billstatus.size();
        else if(Flag == 2 || Flag == 5)
            return this.serveorder.size();
        else if(Flag == 3)
            return this.track.size();
        else if(Flag == 4)
            return this.tablets.size();
        else
            return this.tables.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(Flag == 0)
            return 0;
        else if(Flag == 1)
            return 1;
        else if(Flag == 2)
            return 2;
        else if(Flag == 3)
            return 3;
        else if(Flag == 4)
            return 4;
        else if(Flag == 5)
            return Flag;
        else
            return 0;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {
            case 0:
                View v1 = inflater.inflate(R.layout.hall_table_layout, viewGroup, false);
                viewHolder = new MyViewHolder(v1, mlistener);
                break;
            case 1:
                View v2 = inflater.inflate(R.layout.hall_bill_layout, viewGroup, false);
                viewHolder = new MyViewHolder2(v2,mlistener);
                break;
            case 2:
                View v3 = inflater.inflate(R.layout.hall_readyorder_layout, viewGroup, false);
                viewHolder = new MyViewHolder3(v3,mlistener);
                break;
            case 3:
                View v4 = inflater.inflate(R.layout.hall_track_layout, viewGroup, false);
                viewHolder = new MyViewHolder4(v4,mlistener);
                break;
            case 4:
                View v5 = inflater.inflate(R.layout.hall_tablet_layout, viewGroup, false);
                viewHolder = new MyViewHolder5(v5,mlistener);
                break;
            case 5:
                v3 = inflater.inflate(R.layout.hall_completedorder_layout, viewGroup, false);
                viewHolder = new MyViewHolder3(v3,mlistener);
                break;
            default:
                View v = inflater.inflate(R.layout.hall_table_layout, viewGroup, false);
                viewHolder = new MyViewHolder(v,mlistener);
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case 0:
                ((MyViewHolder) viewHolder).bindView(position);
                break;
            case 1:
                ((MyViewHolder2) viewHolder).bindView(position);
                break;
            case 2:
                ((MyViewHolder3) viewHolder).bindView(position);
                break;
            case 3:
                ((MyViewHolder4) viewHolder).bindView(position);
                break;
            case 4:
                ((MyViewHolder5) viewHolder).bindView(position);
                break;
            case 5:
                ((MyViewHolder3) viewHolder).bindView(position);
                break;
            default:
                ((MyViewHolder) viewHolder).bindView(position);
                break;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {  ///  tables
        TextView ID;
        TextView Capacity;
        TextView TableID;
        TextView TableCapacity;
        ImageView status;
        Button free;
        Button occupy;
        Button booked;

        public MyViewHolder(View itemview, final OnItemClickListener listener) {
            super(itemview);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
            ID = (TextView) itemView.findViewById(R.id.id);
            TableID = (TextView) itemView.findViewById(R.id.Tableid);
            Capacity = (TextView) itemView.findViewById(R.id.Capacity);
            TableCapacity = (TextView) itemView.findViewById(R.id.TableCapacity);
            status = (ImageView) itemView.findViewById(R.id.imageView2);
            free = (Button) itemView.findViewById(R.id.free);
            free.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.OnFreeClick(position);
                        }
                    }
                }
            });
            occupy = (Button) itemView.findViewById(R.id.occupied);
            occupy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.OnOccupyClick(position);
                        }
                    }
                }
            });
            booked = (Button) itemView.findViewById(R.id.booking);
            booked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.OnBookClick(position);
                        }
                    }
                }
            });
        }

        public void bindView(final int position) {
            ID.setText(tables.get(position).getTableID());
            TableID.setText("Id: ");
            Capacity.setText(tables.get(position).getCapacity());
            TableCapacity.setText("Capacity: ");
            if ("Booked".equals(tables.get(position).getStatus())) {
                status.setImageResource(R.drawable.booked);
                free.setEnabled(true);
                occupy.setEnabled(false);
                booked.setEnabled(false);
            } else if ("Occupied".equals(tables.get(position).getStatus())) {
                status.setImageResource(R.drawable.occupy);
                free.setEnabled(true);
                occupy.setEnabled(false);
                booked.setEnabled(false);
            } else if ("Free".equals(tables.get(position).getStatus())) {
                status.setImageResource(R.drawable.free);
                free.setEnabled(false);
                occupy.setEnabled(true);
                booked.setEnabled(true);
            }
        }

        public void onClick(View view) {

        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {  ///  Receive payment for served orders

        TextView ID;
        TextView OID;
        TextView TableID;
        TextView orderid;
        TextView status;
        TextView s;
        TextView bill;
        TextView amount;
        Button paid;
        ListView dishes;

        public MyViewHolder2(View itemview, final OnItemClickListener listener) {
            super(itemview);
            ID = (TextView) itemView.findViewById(R.id.TID);
            TableID = (TextView) itemView.findViewById(R.id.Tableid3);
            OID = (TextView) itemView.findViewById(R.id.OID);
            orderid = (TextView) itemView.findViewById(R.id.orderid);
            status = (TextView) itemView.findViewById(R.id.status);
            s = (TextView) itemView.findViewById(R.id.s);
            bill = (TextView) itemView.findViewById(R.id.bill);
            amount = (TextView) itemView.findViewById(R.id.amount);
            paid = (Button) itemView.findViewById(R.id.Paid);
            dishes = (ListView) itemview.findViewById(R.id.payorderdishes);
            paid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.OnBookClick(position);
                        }
                    }
                }
            });
            itemview.setOnClickListener(new View.OnClickListener() {

                List<OrderDetails> orderDetails = new ArrayList<>();
                List<String> ids = new ArrayList<>();
                boolean updated = false;

                @Override
                public void onClick(View view) {
                    if(listener != null){
                        final int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.OnItemClick(position);
                            FirebaseDatabase.getInstance().getReference("OrderDetails").orderByChild("orderid").equalTo(billstatus.get(position).getOrderID()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                                        OrderDetails details = orderSnapshot.getValue(OrderDetails.class);
                                        if (details.getStatus() == 3 && !ids.contains(details.getOrderid()))
                                        {
                                            updated = true;
                                            orderDetails.add(details);
                                            ids.add(details.getOrderid());
                                        }
                                    }
                                    if(updated) {
                                        DishAdapter dishAdapter = new DishAdapter(context, orderDetails);
                                        dishes.setAdapter(dishAdapter);

                                        int desiredWidth = View.MeasureSpec.makeMeasureSpec(dishes.getWidth(), View.MeasureSpec.UNSPECIFIED);
                                        int totalHeight = 0;
                                        for (int i = 0; i < dishAdapter.getCount(); i++) {
                                            View dishView = dishAdapter.getView(i, itemView, dishes);
                                            if (i == 0)
                                                dishView.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            dishView.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                                            totalHeight += dishView.getMeasuredHeight();
                                        }
                                        ViewGroup.LayoutParams params = dishes.getLayoutParams();
                                        params.height = totalHeight + (dishes.getDividerHeight() * (dishAdapter.getCount() - 1));
                                        dishes.setLayoutParams(params);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(context, "ERROR AT FETCHING ORDER DETAILS", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            });
        }

        public void bindView(final int position) {
            ID.setText(billstatus.get(position).getTableID());
            TableID.setText("Table ID: ");
            OID.setText(billstatus.get(position).getOrderID());
            orderid.setText("Order ID: ");
            amount.setText(Integer.toString(billstatus.get(position).getAmount()));
            bill.setText("Bill Amount: ");
            s.setText(billstatus.get(position).getStatus());
            status.setText("TimeStamp: ");
        }

        public void onClick(View view) {

        }
    }

    public class MyViewHolder3 extends RecyclerView.ViewHolder implements View.OnClickListener {   ///  Ready to serve  /////  view completed orders

        TextView TID;
        TextView OID;
        TextView TableID;
        TextView OrderID;
        ListView dishes;
        Button Served;

        public MyViewHolder3(final View itemview, final OnItemClickListener listener) {
            super(itemview);
            TID = (TextView) itemView.findViewById(R.id.TID);
            TableID = (TextView) itemView.findViewById(R.id.Tableid2);
            OID = (TextView) itemView.findViewById(R.id.OID);
            OrderID = (TextView) itemView.findViewById(R.id.orderid);
            if(Flag==4)
                dishes = (ListView) itemview.findViewById(R.id.completeddishesqueue);
            else if(Flag==2)
                dishes = (ListView) itemview.findViewById(R.id.readydishesqueue);
            itemview.setOnClickListener(new View.OnClickListener() {

                List<OrderDetails> orderDetails = new ArrayList<>();
                List<String> ids = new ArrayList<>();
                boolean updated = false;

                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                            FirebaseDatabase.getInstance().getReference("OrderDetails").orderByChild("orderid").equalTo(serveorder.get(position).getId()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                                        OrderDetails details = orderSnapshot.getValue(OrderDetails.class);
                                        if (Flag == 2)
                                            if (details.getStatus() == 2 && !ids.contains(details.getOrderid()))
                                            {
                                                updated = true;
                                                orderDetails.add(details);
                                                ids.add(details.getOrderid());
                                            }
                                            else if (Flag == 5)
                                                if (details.getStatus() == 4 && !ids.contains(details.getOrderid()))
                                                {
                                                    updated = true;
                                                    orderDetails.add(details);
                                                    ids.add(details.getOrderid());
                                                }
                                    }
                                    if(updated) {
                                        DishAdapter dishAdapter = new DishAdapter(context, orderDetails);
                                        dishes.setAdapter(dishAdapter);

                                        int desiredWidth = View.MeasureSpec.makeMeasureSpec(dishes.getWidth(), View.MeasureSpec.UNSPECIFIED);
                                        int totalHeight = 0;
                                        for (int i = 0; i < dishAdapter.getCount(); i++) {
                                            View dishView = dishAdapter.getView(i, itemView, dishes);
                                            if (i == 0)
                                                dishView.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            dishView.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                                            totalHeight += dishView.getMeasuredHeight();
                                        }
                                        ViewGroup.LayoutParams params = dishes.getLayoutParams();
                                        params.height = totalHeight + (dishes.getDividerHeight() * (dishAdapter.getCount() - 1));
                                        dishes.setLayoutParams(params);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(context, "ERROR AT FETCHING ORDER DETAILS", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            });
            if(Flag != 5) {
                Served = (Button) itemView.findViewById(R.id.Served);
                Served.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.OnBookClick(position);
                            }
                        }
                    }
                });
            }
        }

        public void bindView(final int position) {
            TID.setText(serveorder.get(position).getTableID());
            TableID.setText("Table ID: ");
            OID.setText(serveorder.get(position).getId());
            OrderID.setText("Order ID: ");
        }

        public void onClick(View view) {

        }
    }

    public class MyViewHolder4 extends RecyclerView.ViewHolder implements View.OnClickListener {  //  Tracking

        TextView TID;
        TextView OID;
        TextView SID;
        TextView TableID;
        TextView OrderID;
        TextView ServerID;

        public MyViewHolder4(View itemview, final OnItemClickListener listener) {
            super(itemview);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
            TID = (TextView) itemView.findViewById(R.id.TID);
            TableID = (TextView) itemView.findViewById(R.id.Tableid4);
            OID = (TextView) itemView.findViewById(R.id.OID);
            OrderID = (TextView) itemView.findViewById(R.id.orderid);
            SID = (TextView) itemView.findViewById(R.id.SID);
            ServerID = (TextView) itemView.findViewById(R.id.serverid);
        }

        public void bindView(final int position) {
            TID.setText(track.get(position).getTableID());
            TableID.setText("Table ID: ");
            OID.setText(track.get(position).getTabletID());
            OrderID.setText("Tablet ID: ");
            SID.setText(track.get(position).getEmployeeid());
            ServerID.setText("Server ID: ");
        }

        public void onClick(View view) {

        }
    }

    public class MyViewHolder5 extends RecyclerView.ViewHolder implements View.OnClickListener {  // Tablets

        TextView TID;
        TextView s;
        TextView TabletID;
        TextView Status;
        Button Assign;

        public MyViewHolder5(View itemview, final OnItemClickListener listener) {
            super(itemview);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
            TID = (TextView) itemView.findViewById(R.id.TID);
            TabletID = (TextView) itemView.findViewById(R.id.Tabletid);
            s = (TextView) itemView.findViewById(R.id.s);
            Status = (TextView) itemView.findViewById(R.id.status);
            Assign = (Button) itemView.findViewById(R.id.assign);
            Assign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.OnBookClick(position);
                        }
                    }
                }
            });
        }

        public void bindView(final int position) {
            TID.setText(tablets.get(position).getTabletID());
            TabletID.setText("Tablet ID: ");
            Status.setText("Status: ");
            s.setText(tablets.get(position).getStatus());
            if("In Use".equals(tablets.get(position).getStatus()))
            {
                Assign.setEnabled(false);
            }
            else if("Broken".equals(tablets.get(position).getStatus()))
            {
                Assign.setEnabled(false);
            }
            else if("Issue".equals(tablets.get(position).getStatus()))
            {
                Assign.setEnabled(false);
            }
        }

        public void onClick(View view) {

        }
    }
}

