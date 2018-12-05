package com.dineout.code.kitchen;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dineout.R;
import com.dineout.code.kitchen.models.OrderComparator;
import com.dineout.code.kitchen.models.OrderDb;
import com.dineout.code.kitchen.models.OrderDetailsDb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static com.dineout.code.kitchen.MainActivity.READY;

public class RecyclerViewAdapterOrdersOfCook extends RecyclerView.Adapter<OrderViewHolder>
{
    Context mContext;
    ArrayList<OrderDetailsDb> mData;
    ArrayList<TextView> mTextViews;

    Dialog myDialog;
    int mCookNo;
    static int itemNo;

    public RecyclerViewAdapterOrdersOfCook(Context context, ArrayList<OrderDetailsDb> data, int cookNo)
    {
        mContext = context;
        mData = data;
        mCookNo = cookNo;
        //Toast.makeText(context, "sssss" + cookNo, Toast.LENGTH_SHORT).show();
        mTextViews = new ArrayList<>();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.kitchen_layout_orders_of_cook,parent,false);
        final OrderViewHolder vHolder = new OrderViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, final int position)
    {
        holder.order_name.setText(mData.get(position).getDishname());
        holder.order_serving.setText(mData.get(position).getServings() + "");

        // Different colors based on status
        if(mData.get(position).getStatus() == MainActivity.WAITING)
        {
            holder.button.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.parseColor("#ff6961"));
        }
        else if(mData.get(position).getStatus() == MainActivity.COOKING)
        {
            holder.button.setVisibility(View.INVISIBLE);
            holder.itemView.setBackgroundColor(Color.parseColor("#ffeee0"));

            mTextViews.add(holder.eta);
            // run3();

            holder.eta.setTag(mCookNo + " " + mData.get(position));

            /*new Thread(new Runnable()
            {
                int time = 20;
                @Override
                public void run()
                {
                    time--;
                    holder.eta.setText(time);
                }
            }).start();*/

            // Timer(holder.eta);

            /*final Handler mClockHandler = new Handler();
            Timer clockTask = new Timer(holder.eta, holder.eta.getTag().toString());
            mClockHandler.post(clockTask);*/
        }

        holder.order_row.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                //removeItemFromList(position);
                LayoutInflater li = LayoutInflater.from(mContext);
                View promptsView = li.inflate(R.layout.kitchen_prompts, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        mContext);

                // set kitchen_prompts.xmlmpts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);



                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String orderId = mData.get(position).getOrderid();
                                        int servings = mData.get(position).getServings();
                                        int enteredQuantity= Integer.parseInt(userInput.getText().toString());
                                        String dishname = mData.get(position).getDishname();

                                        if(enteredQuantity>servings)
                                        {
                                            Toast.makeText(mContext, "Invalid Number of servings wasted", Toast.LENGTH_LONG ).show();       // get user input and set it to result
                                        }
                                        else
                                        {

                                            //Ramsha's Code here
                                            //Three variables required are enteredQuantity, servings, orderId, dishname

                                            if(enteredQuantity == servings){
                                                //dummyActivity.updateDishStatus(mData.get(position), dummyActivity.READY);
                                                MainActivity.removeOrderDetailsFromDb(mData.get(position));
                                                mData.remove(position);
                                            }
                                            else{
                                                MainActivity.updateDishServings(mData.get(position), mData.get(position).getServings() - enteredQuantity);
                                            }
                                            MainActivity.adapter.notifyDataSetChanged();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
               // alertDialogBuilder.setPositiveButtonIcon(R.drawable.check);
                //alertDialogBuilder.setNegativeButtonIcon(R.drawable.cancel)
                //alertDialogBuilder.setPositiveButtonIcon()
                //Button positiveButton = alertDialogBuilder.getButton(AlertDialog.BUTTON_POSITIVE);



                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                //AlertDialog dialog = Builder.create();


                return true;
            }
        });

        holder.order_row.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ChangeStateOfOrder(position, holder);
            }
        });

        // Head Chef presses ">" button (waiting order)
        holder.button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                itemNo = position;

                chooseChef();
            }
        });
    }

    private void run3()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                int temp = Integer.parseInt(mTextViews.get(0).getText().toString());
                temp = temp - 1;
                mTextViews.get(0).setText(temp);
                // decreaseTime();
            }
        }, 2000);
    }

    @Override
    public int getItemCount()
    {
        if(mData!=null)
        {
            return mData.size();
        }
        else
        {
            return 0;
        }
    }

    public void chooseChef()        // Which item to move from one chef to the other
    {
        Intent intent = new Intent(mContext, CooksListActivity.class);
        //mContext.startActivity(intent);
        intent.putExtra("cookNo", mCookNo);
        //Toast.makeText(mContext, mCookNo + "", Toast.LENGTH_SHORT).show();
        ((Activity)mContext).startActivityForResult(intent, 345);
       // ((dummyActivity)mContext).startActivityForResult(intent, 70);
        // Toast.makeText(mContext, mCookNo + "", Toast.LENGTH_SHORT).show();
    }

    private void ChangeStateOfOrder(int position, OrderViewHolder holder)
    {
        if(mData.get(position).getStatus() == MainActivity.WAITING)
        {
            Toast.makeText(mContext, "Order moved to Cooking state", Toast.LENGTH_SHORT).show();

            holder.button.setVisibility(View.INVISIBLE);
            holder.itemView.setBackgroundColor(Color.parseColor("#ffeee0"));

           // mData.get(position).setStatus(dummyActivity.READY);
            MainActivity.updateDishStatus(mData.get(position), MainActivity.COOKING);
            MainActivity.updateOrderStatusCooking(mData.get(position).getOrderid());
            Collections.sort(mData, new OrderComparator());
            notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(mContext, "Order Cooked", Toast.LENGTH_SHORT).show();
            MainActivity.updateDishStatus(mData.get(position), MainActivity.READY);

            if(MainActivity.isOrderCompleted(mData.get(position).getOrderid())){
                OrderDb thisorder = MainActivity.getOrderFromId(mData.get(position).getOrderid());

                if (thisorder != null)
                    MainActivity.updateOrderStatus(thisorder, READY);
            }


            mData.remove(position);
            MainActivity.adapter.notifyDataSetChanged();
        }

        // RecyclerViewAdapterCook.adapters.get(mCookNo).notifyDataSetChanged();
    }

    private void removeItemFromList(int position)
    {
        if(mData.get(position).getStatus() == MainActivity.WAITING)
        {
            Toast.makeText(mContext, "Order has been Cancelled", Toast.LENGTH_SHORT).show();
            mData.remove(position);
            MainActivity.adapter.notifyDataSetChanged();
        }
        else if(mData.get(position).getStatus() == MainActivity.COOKING)
        {
            Toast.makeText(mContext, "Order Wasted", Toast.LENGTH_SHORT).show();

            // Open dialogue box here /* Code by Mini Specialized Order */

            /*if (mData.get(position).getStatus() > 0 && mData.get(position).getStatus() < 4 && mData.get(position).getPriority()==0)
            {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE:
                                //CHECK INVENTORY!
                                Toast.makeText(mContext,"PROCESSING RE-ORDER",Toast.LENGTH_SHORT).show();

                                FirebaseDatabase.getInstance().getReference("MenuItems").orderByChild("dishname").equalTo(dish.getDishname()).addValueEventListener(new ValueEventListener()
                                {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                    {
                                        for (DataSnapshot orderSnapshot: dataSnapshot.getChildren())
                                        {
                                            final MenuItem item = orderSnapshot.getValue(MenuItem.class);

                                            // Toast.makeText(mContext,"ITEM: "+item.getItemname(),Toast.LENGTH_SHORT).show();

                                            FirebaseDatabase.getInstance().getReference("Inventory/"+item.getItemname()).runTransaction(new Transaction.Handler()
                                            {
                                                @NonNull
                                                @Override
                                                public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                                                    Inventory i = mutableData.getValue(Inventory.class);
                                                    FireBaseHelper helper = new FireBaseHelper();
                                                    if(i==null)
                                                        return Transaction.success(mutableData);
                                                    if(i.getQuantity() >= i.getMinthreshold() && i.getQuantity()-(count*item.getQuantity())<=i.getMinthreshold()) {
                                                        Calendar calendar = Calendar.getInstance();
                                                        @SuppressLint("SimpleDateFormat")
                                                        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
                                                        String strDate = mdformat.format(calendar.getTime());
                                                        helper.NewNotification(item.getItemname(),false,strDate);
                                                        Toast.makeText(getContext(), "Admin Notified for Low Inventory Count!", Toast.LENGTH_LONG).show();
                                                    }
                                                    if(i.getQuantity()-(count*item.getQuantity())<0) {
                                                        Toast.makeText(getContext(),"Error: Inventory Out of Stock\nRe-Order Cannot be placed!",Toast.LENGTH_LONG).show();
                                                        possible = false;
                                                        return Transaction.success(mutableData);
                                                    }
                                                    i.setQuantity(i.getQuantity()-(count*item.getQuantity()));
                                                    mutableData.setValue(i);
                                                    return Transaction.success(mutableData);
                                                }

                                                @Override
                                                public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                                                    FireBaseHelper helper = new FireBaseHelper();
                                                    //LOOK HERE!!!
                                                    //LOAD BALANCING FUNCTION TO BE CALLED HERE - BY DEFAULT, IT ALWAYS GOES TO THE WAITING QUEUE!!

                                                    // assignDishToChef() is load balancing function in Main Activity
                                                    if(possible) {
                                                        if (dish.getServings() - count == 0)
                                                            dishes.remove(position);
                                                        else
                                                            dishes.get(position).setServings(dishes.get(position).getServings() - count);
                                                        helper.UpdateOrderDetails(dish.getOrderid(), dish.getDishname(), dish.getEstimatedtime(), dish.servings, count);
                                                        Toast.makeText(getContext(), "Re-Order Confirmed!", Toast.LENGTH_LONG).show();
                                                        count = 0;
                                                        DishAdapter.this.notifyDataSetChanged();
                                                        parentAdapter.RemoveItem(dish.getOrderid());
                                                    }
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(mContext,"Something went wrong! Try again!",Toast.LENGTH_LONG).show();
                                    }
                                });
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                RelativeLayout linearLayout = new RelativeLayout(mContext);
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
                Toast.makeText(mContext, "Dish Cannot be Re-Ordered!", Toast.LENGTH_SHORT).show();
            }*/
        }
    }

    public class Timer implements Runnable
    {
        /*if (_tv.getTag().toString().equals(tag))
        {
            // do what ever you want to happen every second
            mClockHandler.postDelayed(this, 1000);
            new CountDownTimer(300000, 1000)
            {
                public void onTick(long millisUntilFinished)
                {
                    _tv.setText("" + String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }

                public void onFinish()
                {
                    _tv.setText("0");
                }
            }.start();
        }*/

        TextView tv;
        final Handler mClockHandler = new Handler();
        String tag;

        public Timer(TextView tv, String tag)
        {
            this.tv = tv;
            this.tag = tag;
        }

        public void run()
        {
            if (tv.getTag().toString().equals(tag))
            {
                mClockHandler.postDelayed(this, 1000);
                new CountDownTimer(300000, 1000)
                {
                    public void onTick(long millisUntilFinished)
                    {
                        tv.setText("" + String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    }

                    public void onFinish()
                    {
                        tv.setText("0");
                    }

                }.start();
            }
        }
    }
}
