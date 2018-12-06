package com.dineout.code.hall;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dineout.code.hall.DB.Assignment;
import com.dineout.code.hall.DB.BillStatus;
import com.dineout.code.hall.DB.Order;
import com.dineout.code.hall.DB.OrderDetails;
import com.dineout.code.hall.DB.Table;
import com.dineout.code.hall.DB.Tablet;
import com.dineout.code.hall.DB.Receipt;
import com.dineout.code.hall.DB.Employee;
import com.dineout.code.hall.DB.Item;
import com.dineout.code.hall.DB.MenuItem;
import com.dineout.code.hall.DB.Menu;

import com.dineout.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import com.hrules.horizontalnumberpicker.HorizontalNumberPicker;
import com.hrules.horizontalnumberpicker.HorizontalNumberPickerListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DishAdapter extends ArrayAdapter<OrderDetails> {

    List<OrderDetails> dishes;
    int count;

    public DishAdapter(Context context, List<OrderDetails> dishes) {
        super(context, R.layout.hall_dishes_queue, dishes);
        count = 1;
        this.dishes = dishes;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder")
        final View view = inflater.inflate(R.layout.hall_dishes_queue, parent, false);
        final OrderDetails dish = getItem(position);

        TextView dishName = (TextView) view.findViewById(R.id.MyDishName);
        dishName.setText(dish.getDishname());

        TextView dishtime = (TextView) view.findViewById(R.id.MyDishtime);
        dishtime.setText("Estimated Time: "+dish.getEstimatedtime()+"");

        TextView dishServings = (TextView) view.findViewById(R.id.MyDishServings);
        dishServings.setText("Dish Servings: "+dish.getServings()+"");

        view.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

            boolean possible = true;
            boolean done = false;
            boolean clicked = false;

            @Override
            public void onLongClick() {
                super.onLongClick();
                if (dish.getStatus() > 0 && dish.getStatus() < 4 && dish.getPriority()==0) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                        //CHECK INVENTORY!
                                        Toast.makeText(getContext(),"PROCESSING RE-ORDER",Toast.LENGTH_SHORT).show();
                                        FirebaseDatabase.getInstance().getReference("MenuItem").child(dish.getDishname()).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot orderSnapshot: dataSnapshot.getChildren()) {
                                                    final MenuItem item = orderSnapshot.getValue(MenuItem.class);
                                                    FirebaseDatabase.getInstance().getReference("Inventory/"+item.getIngredientName()).runTransaction(new Transaction.Handler() {
                                                        @NonNull
                                                        @Override
                                                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                                                            Item i = mutableData.getValue(Item.class);
                                                            FireBaseHelper helper = new FireBaseHelper();
                                                            if(i==null)
                                                                return Transaction.success(mutableData);
                                                            if(Integer.valueOf(i.getQuantity()) >= Integer.valueOf(i.getThreshold()) && Integer.valueOf(i.getQuantity())-(count*Integer.valueOf(item.getQuantity()))<=Integer.valueOf(i.getThreshold())) {
                                                                    Calendar calendar = Calendar.getInstance();
                                                                    @SuppressLint("SimpleDateFormat")
                                                                    SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
                                                                    String strDate = mdformat.format(calendar.getTime());
                                                                    helper.NewNotification(item.getIngredientName(),false,strDate);
                                                                Toast.makeText(getContext(), "Admin Notified for Low Inventory Count!", Toast.LENGTH_LONG).show();
                                                            }
                                                            if(Integer.valueOf(i.getQuantity())-(count*Integer.valueOf(item.getQuantity()))<0) {
                                                                Toast.makeText(getContext(),"Error: Inventory Out of Stock\nRe-Order Cannot be placed!",Toast.LENGTH_LONG).show();
                                                                possible = false;
                                                                return Transaction.success(mutableData);
                                                            }
                                                            i.setQuantity(Integer.valueOf(i.getQuantity())-(count*Integer.valueOf(item.getQuantity()))+"");
                                                            mutableData.setValue(i);
                                                            return Transaction.success(mutableData);
                                                        }

                                                        @Override
                                                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                                                            FireBaseHelper helper = new FireBaseHelper();
                                                            //LOOK HERE!!!
                                                            //LOAD BALANCING FUNCTION TO BE CALLED HERE - BY DEFAULT, IT ALWAYS GOES TO THE WAITING QUEUE!!
                                                            if(possible && dishes.size()>0) {
                                                                if (dish.getServings() - count == 0)
                                                                    dishes.remove(position);
                                                                else
                                                                    dishes.get(position).setServings(dishes.get(position).getServings() - count);
                                                                if(!done)
                                                                    helper.done = false;
                                                                if(!clicked) {
                                                                    helper.UpdateOrderDetails(dish.getOrderid(), dish.getDishname(), dish.getEstimatedtime(), dish.getServings(), count);
                                                                    Toast.makeText(getContext(), "Re-Order Confirmed!", Toast.LENGTH_SHORT).show();
                                                                    clicked = true;
                                                                }}
                                                        }
                                                    });
                                               }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Toast.makeText(getContext(),"Something went wrong! Try again!",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    RelativeLayout linearLayout = new RelativeLayout(getContext());
                    HorizontalNumberPicker numberPicker = new HorizontalNumberPicker(getContext());
                    numberPicker.setMaxValue(dish.getServings());
                    numberPicker.setMinValue(1);
                    numberPicker.setListener(new HorizontalNumberPickerListener() {
                        @Override
                        public void onHorizontalNumberPickerChanged(HorizontalNumberPicker horizontalNumberPicker, int value) {
                            count = value;
                        }
                    });

                    RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

                    linearLayout.setLayoutParams(numPicerParams);
                    linearLayout.addView(numberPicker, numPicerParams);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setView(linearLayout);
                    builder.setMessage("Select Dish Quantity").setPositiveButton("Confirm Re-Order", dialogClickListener)
                            .setNegativeButton("Cancel", dialogClickListener).show();
                } else {
                    Toast.makeText(getContext(), "Dish Cannot be Re-Ordered!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}

