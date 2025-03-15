package com.example.noble_mall2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noble_mall2.Model.Jerseyitem;

import java.util.ArrayList;

public class CartRecViewAdapter extends RecyclerView.Adapter<CartRecViewAdapter.ViewHolder> {
    private static final String TAG = "CartRecViewAdapter";

    public interface GetTotalPrice{
        void onGettingTotalPriceResult(double price);
    }
    public interface DeleteCartItem{
        void onDeletingResult(Jerseyitem item);
    }

    private DeleteCartItem deleteCartItem;

    private GetTotalPrice getTotalPrice;
    private Fragment fragment;

    public CartRecViewAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    private ArrayList<Jerseyitem> items = new ArrayList<>();

    public CartRecViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_rec_view_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Jerseyitem item = items.get(position);


        holder.txtName.setText(items.get(position).getName());
        holder.txtPrice.setText(String.valueOf(items.get(position).getPrice()));

        deleteCartItem = (DeleteCartItem) fragment;


        Log.d("CartRecViewAdapter", "Image URL for item: " + item.getImageurl());

//        if (item.getImageurl() != null && !item.getImageurl().isEmpty()) {
//            Log.d(TAG, "onBindViewHolder: sijui");
//            Glide.with(fragment.getActivity())
//                    .asBitmap()
//                    .load(item.getImageurl())
//                    .into(holder.image);
//            Log.d(TAG, "onBindViewHolder: sijui sasa");
//        } else {
//
//            Glide.with(fragment.getActivity())
//                    .load(R.drawable.default_image)
//                    .into(holder.image);
//        }



        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity())
                        .setTitle("Delete item")
                        .setMessage("Are You sure you want to Delete " + items.get(position).getName() + "?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteCartItem.onDeletingResult(items.get(position));
                            }
                        });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items ==  null ? 0 :  items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public ImageView image;
        private TextView txtName,txtPrice,txtDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtPrice = (TextView) itemView.findViewById(R.id.txtprice);
            txtDelete = (TextView) itemView.findViewById(R.id.btnDelete);
        }
    }

    private void CalculatePrice () {
        Log.d(TAG, "CalculatePrice: started");

        try{
            getTotalPrice = (GetTotalPrice) fragment;
        }
        catch (ClassCastException e)
        {
            e.printStackTrace();
        }
        double totalprice = 0;
        for (Jerseyitem item : items){
            totalprice+=item.getPrice();
        }

        getTotalPrice.onGettingTotalPriceResult(totalprice);
    }

    public void setItems(ArrayList<Jerseyitem> items) {
        this .items = items;
        CalculatePrice();
        notifyDataSetChanged();
    }

    

}
