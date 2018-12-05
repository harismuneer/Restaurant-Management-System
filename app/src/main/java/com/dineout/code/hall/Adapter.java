package com.dineout.code.hall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.dineout.R;

import com.dineout.code.hall.DB.Table;
import com.dineout.code.hall.DB.Assignment;
import com.dineout.code.hall.DB.BillStatus;
import com.dineout.code.hall.DB.Order;
import com.dineout.code.hall.DB.Tablet;

import java.util.ArrayList;

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

    public interface OnItemClickListener {
        void OnItemClick(int position);

        void OnFreeClick(int position);

        void OnBookClick(int position);

        void OnOccupyClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    public Adapter(Context context, int Flag, ArrayList<Table> tables, ArrayList<Tablet> tablets, ArrayList<BillStatus> bill, ArrayList<Order> serveorder, ArrayList<Assignment> track) {
        this.Flag = Flag;
        this.context = context;
        this.tables = tables;
        this.tablets = tablets;
        this.billstatus = bill;
        this.serveorder = serveorder;
        this.track = track;
    }

    @Override
    public int getItemCount() {

        if (Flag == 0)
            return this.tables.size();
        else if (Flag == 1)
            return this.billstatus.size();
        else if (Flag == 2 || Flag == 5)
            return this.serveorder.size();
        else if (Flag == 3)
            return this.track.size();
        else if (Flag == 4)
            return this.tablets.size();
        else
            return this.tables.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (Flag == 0)
            return 0;
        else if (Flag == 1)
            return 1;
        else if (Flag == 2)
            return 2;
        else if (Flag == 3)
            return 3;
        else if (Flag == 4)
            return 4;
        else if (Flag == 5)
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
                viewHolder = new MyViewHolder2(v2, mlistener);
                break;
            case 2:
                View v3 = inflater.inflate(R.layout.hall_readyorder_layout, viewGroup, false);
                viewHolder = new MyViewHolder3(v3, mlistener);
                break;
            case 3:
                View v4 = inflater.inflate(R.layout.hall_track_layout, viewGroup, false);
                viewHolder = new MyViewHolder4(v4, mlistener);
                break;
            case 4:
                View v5 = inflater.inflate(R.layout.hall_tablet_layout, viewGroup, false);
                viewHolder = new MyViewHolder5(v5, mlistener);
                break;
            case 5:
                v3 = inflater.inflate(R.layout.hall_completedorder_layout, viewGroup, false);
                viewHolder = new MyViewHolder3(v3, mlistener);
                break;
            default:
                View v = inflater.inflate(R.layout.hall_table_layout, viewGroup, false);
                viewHolder = new MyViewHolder(v, mlistener);
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
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
            ID = (TextView) itemView.findViewById(R.id.id200);
            TableID = (TextView) itemView.findViewById(R.id.Tableid200);
            Capacity = (TextView) itemView.findViewById(R.id.Capacity200);
            TableCapacity = (TextView) itemView.findViewById(R.id.TableCapacity200);
            status = (ImageView) itemView.findViewById(R.id.imageView200);
            free = (Button) itemView.findViewById(R.id.free200);
            free.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnFreeClick(position);
                        }
                    }
                }
            });
            occupy = (Button) itemView.findViewById(R.id.occupied200);
            occupy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnOccupyClick(position);
                        }
                    }
                }
            });
            booked = (Button) itemView.findViewById(R.id.booking200);
            booked.setOnClickListener(new View.OnClickListener() {
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

        public void bindView(final int position) {
            ID.setText(tables.get(position).getTableID());
            TableID.setText("Id: ");
            Capacity.setText(tables.get(position).getCapacity());
            TableCapacity.setText("Capacity: ");
            if (tables.get(position).getStatus().equals("Booked")) {
                status.setImageResource(R.drawable.booked);
                free.setEnabled(true);
                occupy.setEnabled(false);
                booked.setEnabled(false);
            } else if (tables.get(position).getStatus().equals("Occupied")) {
                status.setImageResource(R.drawable.occupy);
                free.setEnabled(true);
                occupy.setEnabled(false);
                booked.setEnabled(false);
            } else if (tables.get(position).getStatus().equals("Free")) {
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

        public MyViewHolder2(View itemview, final OnItemClickListener listener) {
            super(itemview);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
            ID = (TextView) itemView.findViewById(R.id.TID200);
            TableID = (TextView) itemView.findViewById(R.id.Tableid203);
            OID = (TextView) itemView.findViewById(R.id.OID200);
            orderid = (TextView) itemView.findViewById(R.id.orderid200);
            status = (TextView) itemView.findViewById(R.id.status200);
            s = (TextView) itemView.findViewById(R.id.s200);
            bill = (TextView) itemView.findViewById(R.id.bill200);
            amount = (TextView) itemView.findViewById(R.id.amount200);
            paid = (Button) itemView.findViewById(R.id.Paid200);
            paid.setOnClickListener(new View.OnClickListener() {
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

        public void bindView(final int position) {
            ID.setText(billstatus.get(position).getTableID());
            TableID.setText("Table Id: ");
            OID.setText(billstatus.get(position).getOrderID());
            orderid.setText("Order ID: ");
            amount.setText(billstatus.get(position).getAmount() + "");
            bill.setText("Bill Amount: ");
            s.setText(billstatus.get(position).getStatus());
            status.setText("Status: ");
        }

        public void onClick(View view) {

        }
    }

    public class MyViewHolder3 extends RecyclerView.ViewHolder implements View.OnClickListener {   ///  Ready to serve  /////  view completed orders

        TextView TID;
        TextView OID;
        TextView TableID;
        TextView OrderID;
        Button Served;

        public MyViewHolder3(View itemview, final OnItemClickListener listener) {
            super(itemview);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
            TID = (TextView) itemView.findViewById(R.id.TID200);
            TableID = (TextView) itemView.findViewById(R.id.Tableid202);
            OID = (TextView) itemView.findViewById(R.id.OID200);
            OrderID = (TextView) itemView.findViewById(R.id.orderid200);
            if (Flag != 5) {
                Served = (Button) itemView.findViewById(R.id.Served200);
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
            TableID.setText("Table Id: ");
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
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
            TID = (TextView) itemView.findViewById(R.id.TID200);
            TableID = (TextView) itemView.findViewById(R.id.Tableid204);
            OID = (TextView) itemView.findViewById(R.id.OID200);
            OrderID = (TextView) itemView.findViewById(R.id.orderid200);
            SID = (TextView) itemView.findViewById(R.id.SID200);
            ServerID = (TextView) itemView.findViewById(R.id.serverid200);
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
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
            TID = (TextView) itemView.findViewById(R.id.TID200);
            TabletID = (TextView) itemView.findViewById(R.id.Tabletid200);
            s = (TextView) itemView.findViewById(R.id.s200);
            Status = (TextView) itemView.findViewById(R.id.status200);
            Assign = (Button) itemView.findViewById(R.id.assign200);
            Assign.setOnClickListener(new View.OnClickListener() {
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

        public void bindView(final int position) {
            TID.setText(tablets.get(position).getTabletID());
            TabletID.setText("Tablet ID: ");
            Status.setText("Status: ");
            s.setText(tablets.get(position).getStatus());
            if (tablets.get(position).getStatus().equals("InUse")) {
                Assign.setEnabled(false);
            } else if (tablets.get(position).getStatus().equals("broken")) {
                Assign.setEnabled(false);
            } else if (tablets.get(position).getStatus().equals("issue")) {
                Assign.setEnabled(false);
            }
        }

        public void onClick(View view) {

        }
    }
}

