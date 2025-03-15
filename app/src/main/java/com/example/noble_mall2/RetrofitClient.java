package com.example.noble_mall2;

import com.example.noble_mall2.Model.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitClient {

    @POST("posts")
    Call<Order> goToFakePayment (@Body Order order);

}
