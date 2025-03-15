package com.example.noble_mall2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noble_mall2.Model.Jerseyitem;
import com.example.noble_mall2.Model.Order;

import java.util.ArrayList;

public class SuccessfulPaymentFragment extends Fragment {
    private static final String TAG = "SuccessfulPaymentFragmet";

    private Button btnBack;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_success_payment, container,false);

        Utils utils = new Utils(getActivity());
        utils.RemoveCartItems();

        Bundle bundle = getArguments();
        try {
            Order order = bundle.getParcelable("order");
            ArrayList<Integer> itemids = order.getItems();
            utils.addPopularityPoint(itemids);

            ArrayList<Jerseyitem> items = utils.getItemsById(itemids);
            for (Jerseyitem item: items)
            {
                ArrayList<Jerseyitem>samecategory = new ArrayList<>();
                samecategory = utils.getitemscategory(item.getCategory());
                for (Jerseyitem j : samecategory)
                {
                    utils.increaseUserPoint(j,4);
                }
            }

        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        btnBack = (Button)view.findViewById(R.id.btnGoback);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return view;
    }
}
