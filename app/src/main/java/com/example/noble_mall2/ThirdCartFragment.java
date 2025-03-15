package com.example.noble_mall2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noble_mall2.Model.Order;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThirdCartFragment extends Fragment {
    private static final String TAG = "ThirdCartFragment";

    private TextView txtprice,txtshippingdetail;
    private Button btnBack,btnNext;
    private RadioGroup rgpaymentmethods;

    private Order incomingorder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third_cart,container,false);

        initViews(view);
        Bundle bundle = getArguments();

        try {
            incomingorder = bundle.getParcelable("order");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        if (null != incomingorder)
        {
          txtprice.setText(String.valueOf(incomingorder.getTotalPrice()));
          String finalstring = "Items:\n\t Address:"+ incomingorder.getAddress()+ "\n\t" +
                  "Email:" + incomingorder.getEmail() + "\n\t" +
                  "Zip code" + incomingorder.getZipcode() + "\n\t" +
                  "Phone number" + incomingorder.getPhonenumber() + "\n\t";
          txtshippingdetail.setText(finalstring);
          btnNext.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  gotopayment();
              }
          });

          btnBack.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  goback();
              }
          });

        }
        return view;

    }

    private void goback()
    {
        Log.d(TAG, "goback: started");
        Order order = new Order();
        order.setTotalPrice(incomingorder.getTotalPrice());
        order.setItems(incomingorder.getItems());
        Bundle bundle =  new Bundle();
        bundle.putParcelable("order", order);
        CartSecondFragment cartSecondFragment = new CartSecondFragment();
        cartSecondFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.out,R.anim.in)
                .replace(R.id.fragment_container, cartSecondFragment)
                .commit();
    }

    private void gotopayment()
    {
        Log.d(TAG, "gotopayment: started");
        RadioButton radioButton = getActivity().findViewById(rgpaymentmethods.getCheckedRadioButtonId());
        incomingorder.setPaymentmethod(radioButton.getText().toString());
        incomingorder.setSuccess(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitClient retrofitClient = retrofit.create(RetrofitClient.class);
        Call<Order> call = retrofitClient.goToFakePayment(incomingorder);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Log.d(TAG, "onResponse: code" + response.code());
                if (!response.isSuccessful()){
                    return;
                }

                gotopaymentresult(response.body());
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d(TAG, "onFailure: t: " + t.getMessage());
            }
        });
    }

    private void gotopaymentresult(Order order)
    {
        Log.d(TAG, "gotopaymentresult: started");
        if (order.isSuccess())
        {
            SuccessfulPaymentFragment successfulPaymentFragment = new SuccessfulPaymentFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("order",order);
            successfulPaymentFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.in,R.anim.out)
                    .replace(R.id.fragment_container,successfulPaymentFragment)
                    .commit();
        } else {

            FailedPaymentFragment failedPaymentFragment = new FailedPaymentFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("order",order);
            failedPaymentFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.in,R.anim.out)
                    .replace(R.id.fragment_container,failedPaymentFragment)
                    .commit();
        }

    }

    private void initViews(View view)
    {
        Log.d(TAG, "initViews: started");
        txtprice = (TextView) view.findViewById(R.id.txtprice);
        txtshippingdetail = (TextView) view.findViewById(R.id.txtshippingdetail);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnNext = (Button) view.findViewById(R.id.btnfinish);

        rgpaymentmethods = (RadioGroup) view.findViewById(R.id.rgpaymentmethods);
    }
}
