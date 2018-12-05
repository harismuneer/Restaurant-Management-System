package com.dineout.code.admin;

import com.dineout.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class IngredientsListAdapter extends BaseAdapter {
    Context context;
    private ArrayList<IngredientRow> ingredients;
    private ArrayList<NotificationClass> notifications1;
    private static LayoutInflater inflater = null;


    public IngredientsListAdapter(Context context, ArrayList<IngredientRow> ingredients, ArrayList<NotificationClass> not1) {
        this.ingredients = ingredients;
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.notifications1 = not1;
    }

    @Override
    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;


        if (vi == null) {
            boolean red = false;
            for (int i = 0; i < notifications1.size(); ++i) {

                if (notifications1.get(i).getItemName().equals(ingredients.get(position).getName())) {
                    vi = inflater.inflate(R.layout.admin_ingredient_row_red, null);
                    red = true;
                    break;
                }
            }

            if (red == false) {
                vi = inflater.inflate(R.layout.admin_ingredient_row, null);
            }
        }

        LinearLayout li = (LinearLayout) vi.findViewById(R.id.linearRow301);
        TextView ingredientName = (TextView) vi.findViewById(R.id.ingredientName301);
        TextView quantity = (TextView) vi.findViewById(R.id.quantity301);


        ingredientName.setText("Ingredient: " + ingredients.get(position).getName());
        quantity.setText("Quantity: " + ingredients.get(position).getQuantity());
        return vi;
    }
}