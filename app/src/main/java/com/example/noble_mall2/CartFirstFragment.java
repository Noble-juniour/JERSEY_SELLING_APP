package com.example.noble_mall2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noble_mall2.Model.Jerseyitem;
import com.example.noble_mall2.Model.Order;

import java.util.ArrayList;

public class CartFirstFragment extends Fragment implements CartRecViewAdapter.GetTotalPrice,
        CartRecViewAdapter.DeleteCartItem {
    private static final String TAG = "CartFirstFragment";

    @Override
    public void onDeletingResult(Jerseyitem item) {
        Log.d(TAG, "onDeletingResult: attempting to delete" + item.toString());

        ArrayList<Integer> itemids = new ArrayList<>();
        itemids.add(item.getId());
        ArrayList<Jerseyitem>items = utils.getItemsById(itemids);
        if (items.size()>0)
        {
            ArrayList<Integer> newitemsids = utils.deleteCartItem(items.get(0));

            if (newitemsids.size() == 0) {
                btnNext.setVisibility(View.GONE);
                btnNext.setEnabled(false);
                txtNoItem.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                btnNext.setVisibility(View.VISIBLE);
                btnNext.setEnabled(true);
                txtNoItem.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            ArrayList<Jerseyitem> newitems = utils.getItemsById(newitemsids);
            this.items = newitemsids;
            adapter.setItems(newitems);
        }

    }

    @Override
    public void onGettingTotalPriceResult(double price) {
        Log.d(TAG, "onGettingTotalPriceResult: totalprice " + price);
        txtPrice.setText(String.valueOf(price));
        this.totalPrice = price;

    }

    private TextView txtPrice,txtNoItem;
    private RecyclerView recyclerView;
    private Button btnNext;


    private CartRecViewAdapter adapter;

    private double totalPrice = 0;
    private ArrayList<Integer> items;


    private Utils utils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_cart, container,false);

        initViews(view);
        items = new ArrayList<>();
        initRecView();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = new Order();
                order.setTotalPrice(totalPrice);
                order.setItems(items);
                Bundle bundle = new Bundle();
                bundle.putParcelable("order", order);
                CartSecondFragment cartSecondFragment = new CartSecondFragment();
                cartSecondFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in,R.anim.out)
                        .replace(R.id.fragment_container,cartSecondFragment).commit();
            }
        });

        utils = new Utils(getActivity());
        return view;
    }

    private void initRecView()
    {
        Log.d(TAG, "initRecView: started");
        adapter = new CartRecViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Utils utils = new Utils(getActivity());

        ArrayList<Integer> itemsids = utils.getCartItems();
        if (null != itemsids) {

            ArrayList<Jerseyitem> items = utils.getItemsById(itemsids);

            Log.d(TAG, "Fetched items: " +items.size());
            for (Jerseyitem item : items)
            {
                Log.d(TAG, "Item : " +item.toString());
            }

            if (items.size() == 0) {
                btnNext.setVisibility(View.GONE);
                btnNext.setEnabled(false);
                txtNoItem.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                btnNext.setVisibility(View.VISIBLE);
                btnNext.setEnabled(true);
                txtNoItem.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            this.items = itemsids;
            adapter.setItems(items);
        }
    }

    private void initViews(View view)
    {
        Log.d(TAG, "initViews: started");

        txtPrice = (TextView) view.findViewById(R.id.txtSum);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        btnNext = (Button) view.findViewById(R.id.btnNext);
        txtNoItem = (TextView) view.findViewById(R.id.txtNoItem);
    }

    @java.lang.Override
    public void onCreateContextMenu(android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {

    }
}
