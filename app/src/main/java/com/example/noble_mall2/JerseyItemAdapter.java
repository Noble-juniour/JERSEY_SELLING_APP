package com.example.noble_mall2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noble_mall2.Model.Jerseyitem;

import java.util.ArrayList;

public class JerseyItemAdapter extends RecyclerView.Adapter<JerseyItemAdapter.ViewHolder> {
    private static final String TAG = "GroceryItemAdapter";
    private Context context;
    private ArrayList<Jerseyitem> items = new ArrayList<>();

    public JerseyItemAdapter(Context context) {
        this.context = context;
    }

    public JerseyItemAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jersey_rec_view_list_item,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        Log.d(TAG, "Image URL: " + items.get(position).getImageurl());

        Log.d(TAG, "onBindViewHolder: called");
        holder.name.setText(items.get(position).getName());
        holder.price.setText(String.valueOf(items.get(position).getPrice()));

        Log.d(TAG, "Image URL: " + items.get(position).getImageurl());

        Glide.with(context)
                .asBitmap()
                .load(items.get(position).getImageurl())
                .into(holder.image);


        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : navigate to another activity
                Intent intent = new Intent(context, JerseyitemActivity.class);
                intent.putExtra("item", items.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setAdapter(JerseyItemAdapter newsitemAdapter) {
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView name;
        private TextView price;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.itemImage);
            name = (TextView) itemView.findViewById(R.id.txtitemName);
            price=(TextView) itemView.findViewById(R.id.txtprice);
            parent = (CardView) itemView.findViewById(R.id.parent);
        }
    }

    public void setItems(ArrayList<Jerseyitem> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
