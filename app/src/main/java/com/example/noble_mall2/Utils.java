package com.example.noble_mall2;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.noble_mall2.Model.Jerseyitem;
import com.example.noble_mall2.Model.Review;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String TAG = "Utils";
    public static final String DATABASE_NAME = "fake_database";
    private static int ID = 0;
    private static int ORDER_ID = 0;
    private Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public static int getOrderId()
    {
        ORDER_ID++;
        return ORDER_ID;
    }

    public static int getID()
    {
        ID++;
        return ID;
    }

    public void additemtocart(int id)
    {
        Log.d(TAG, "additemtocart: started with ID" + id);
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
        ArrayList<Integer> cartitems = gson.fromJson(sharedPreferences.getString("cartitems", null),type);
        if (null == cartitems)
        {
            cartitems = new ArrayList<>();
        }
        cartitems.add(id);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cartitems", gson.toJson(cartitems));
        editor.apply();
        Log.d(TAG, "additemtocart: Cart now contains " + cartitems.size() + " items.");
    }

    public void updateTheNewrate(Jerseyitem item, int Newrate)
    {
        Log.d(TAG, "updateTheNewrate: started"  + item.getName());
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Jerseyitem>>(){}.getType();
        ArrayList<Jerseyitem> items = gson.fromJson(sharedPreferences.getString("allitems", null),type);

        if (null != items)
        {
            ArrayList<Jerseyitem>  newitems = new ArrayList<>();
            for (Jerseyitem i : items)
            {
                if (i.getId() == item.getId()) {
                    i.setRate(Newrate);
                }
                newitems.add(i);
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("allitems", gson.toJson(newitems));
            editor.commit();
            //editor.apply();
        }
        }


    public ArrayList<Review> getReviewforitem(int id)
    {
        Log.d(TAG, "getReviewforitem: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Jerseyitem>>(){}.getType();
        ArrayList<Jerseyitem> items = gson.fromJson(sharedPreferences.getString("allitems", null),type);

        if (null != items)
        {
            for (Jerseyitem item: items)
            {
                if (item.getId() == id)
                {
                    return item.getReviews();
                }
            }
        }
        return null;
    }

    public Boolean AddReview(Review review)
    {
        Log.d(TAG, "AddReview: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Jerseyitem>>(){}.getType();
        ArrayList<Jerseyitem> items = gson.fromJson(sharedPreferences.getString("allitems",null) , type);
        if (null != items)
        {
            ArrayList<Jerseyitem> newitems = new ArrayList<>();
            for (Jerseyitem item : items)
            {
                if (item.getId() == review.getGroceryitemid())
                {
                    ArrayList<Review> reviews = item.getReviews();
                    reviews.add(review);
                    item.setReviews(reviews);
                }
                newitems.add(item);
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("allitems", gson.toJson(newitems));
            editor.commit();
            return true;
        }
        return false;
    }
    public void initDatabase(Context context)
    {
        Log.d(TAG, "initDatabase: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<Jerseyitem>>(){}.getType();
        ArrayList<Jerseyitem>possibleitems = gson.fromJson(sharedPreferences.getString("allitems",""),type);

        if(null == possibleitems)
            {
               initAllitems(context);
            }
        //initAllitems(context);

    }
    public ArrayList<Jerseyitem>getAllitems(Context context)
    {
        Log.d(TAG, "getAllitems: started");
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Type type = new TypeToken<ArrayList<Jerseyitem>>(){}.getType();
        ArrayList<Jerseyitem> allitems = gson.fromJson(sharedPreferences.getString("allitems",null),type);
        return allitems;
    }
    private void initAllitems(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        ArrayList<Jerseyitem> allitems = new ArrayList<>();




        allitems.add(new Jerseyitem("Arsenal Jersey","arsenal Football jersey 24/25","https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjIRsOfnomBsHA9gr" +
                "-NCTgpYD3RJf4mmTLb5L5XyGYZpetWOZE_GuMKoRf9Q89wpXvW41hEWHEiZEtWRmViApIoMOMtNiTK9xIhhNdDJcODR8gVRwHuHp_QbzgySSFf1DSgSx31_qCooVDzKkvKlmqkpNIhJ_W4Qm5KLVeLEede4xdfkN6-Fz2HCVPT9l9Y/s1600/arsenal-24-25-home-away-third-kits%20%284%29.jpg"
                ,"Clothes",1000,1500.00));
        allitems.add(new Jerseyitem("Manchester United Jersey","Manchester United Football jersey 24/25",
                "https://assets.goal.com/images/v3/blt0b58e96934be6892/GOAL%20-%20Blank%20WEB%20-%20Facebook%20-%202024-05-15T092153.151.jpg?auto=webp&format=pjpg&width=1080&quality=60"
                ,"Clothes",1000,1500.00));
        allitems.add(new Jerseyitem("Manchester City Jersey","Manchester City Football jersey 24/25",
                "https://assets.goal.com/images/v3/bltf3b625ccdc4041c7/GOAL%20-%20Multiple%20Images%20-%202%20Split%20-%20Facebook%20(47).jpg?auto=webp&format=pjpg&width=1080&quality=60"
                ,"Clothes",1000,1500.00));
        allitems.add(new Jerseyitem("Liverpool Jersey","Liverpool Football jersey 24/25",
                "https://assets.goal.com/images/v3/bltdaed679108df80bd/GOAL_-_Multiple_Images_-_2_Split_-_Facebook_(42).jpg?auto=webp&format=pjpg&width=1080&quality=60"
                ,"Clothes",1000,1500.00));
        allitems.add(new Jerseyitem("Real Madrid Jersey","Real Madrid Football jersey 24/25",
                "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjPJ7p2xT9wTpzh_eohMII1m7zsUTVORUNbPlbVMEa1IFiO9WN8f1U2lDgW0sbKWtnDu1zyUaWPPdee0sLTn4WewPmw_HmJ211pUD7HaGPb8LT3dg9QymFcqdBpjLgjmE0Jiyam87z_6bzqFooUn2Sj8KXo5Tor41dmco3McEe4A7TDOMNWheXpqb9ScMoe/s1600/madrid%2024-25%20kit%20%282%29.jpg"
                ,"Clothes",1000,1500.00));
        allitems.add(new Jerseyitem("Manchester United Jersey","Manchester united Football jersey 24/25",
                "https://cdn.footballkitarchive.com/2024/07/25/b4nF3YsZcetpGhe.jpg"
                ,"Clothes",1000,1500.00));
        allitems.add(new Jerseyitem("Manchester United Jersey","Manchester united Football jersey 24/25",
                "https://cdn.footballkitarchive.com/2024/07/01/wUOw1MLFGu7rjaJ.jpg"
                ,"Clothes",1000,1500.00));
        allitems.add(new Jerseyitem("Manchester United Jersey","Manchester united Football jersey 24/25",
                "https://cdn.footballkitarchive.com/2024/06/11/o6sfAZeGIHaoDYS.jpg"
                ,"Clothes",1000,1500.00));
        allitems.add(new Jerseyitem(" Arsenal  Jersey","Arsenal Football jersey 24/25",
                "https://cdn.footballkitarchive.com/2024/06/14/HxMnHy20yueSUI6.jpg"
                ,"Clothes",1000,1500.00));
        allitems.add(new Jerseyitem(" Arsenal  Jersey","Arsenal Football jersey 24/25",
                "https://cdn.footballkitarchive.com/2024/07/18/uTDtKHzXMWzCOao.jpg"
                ,"Clothes",1000,1500.00));
        allitems.add(new Jerseyitem(" Chelsea  Jersey","Chelsea Football jersey 24/25",
                "https://images.footballfanatics.com/chelsea/chelsea-nike-home-stadium-shirt-2024-25_ss5_p-200851164+pv-1+u-canixtzkdetrqr6ldmyh+v-b5489kqvtmzx51jgr5hh.jpg?_hv=2&w=900"
                ,"Clothes",1000,500.00));





        String finalString = gson.toJson(allitems);
        editor.putString("allitems", finalString);
        editor.apply();
    }

    public ArrayList<Jerseyitem> searchforitem (String text)
    {
        Log.d(TAG, "searchforitem: started");

        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Jerseyitem>>(){}.getType();
        ArrayList<Jerseyitem> allitems = gson.fromJson(sharedPreferences.getString("allitems", null) ,type);

        ArrayList<Jerseyitem> searchitems = new ArrayList<>();
        if (null != allitems)
        {
            for (Jerseyitem item: allitems){
                if (item.getName().equalsIgnoreCase(text))
                {
                    searchitems.add(item);
                }

                String [] splittedString = item.getName().split(" ");
                for (int i = 0 ; i < splittedString.length; i++)
                {
                    if (splittedString[i].equalsIgnoreCase(text))
                    {
                        boolean doesexist = false;
                        for (Jerseyitem seachitem : searchitems)
                        {
                            if (seachitem.equals(item))
                            {
                                doesexist = true;
                            }
                        }
                        if (!doesexist)
                        {
                            searchitems.add(item);
                        }
                    }
                }
            }
        }
        return searchitems;
    }

    public ArrayList<String> getthethreecategories()
    {
        Log.d(TAG, "getthethreecategories: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Jerseyitem>>(){}.getType();
        ArrayList<Jerseyitem> allitems = gson.fromJson(sharedPreferences.getString("allitems", null) ,type);

        ArrayList<String> categories = new ArrayList<>();

        if(null != allitems) {
            for (int i = 0; i < allitems.size(); i++) {
                if (categories.size()<3)
                {
                    boolean doesexist = false;
                    for (String s: categories){
                        if (allitems.get(i).getCategory().equals(s))
                        {
                            doesexist = true;
                        }
                    }
                    if (!doesexist){
                        categories.add(allitems.get(i).getCategory());
                    }
                }
            }
        }
        return categories;
    }

    public ArrayList<String> getAllCategories()
    {
        Log.d(TAG, "getAllCategories: started");
        Log.d(TAG, "getthethreecategories: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Jerseyitem>>(){}.getType();
        ArrayList<Jerseyitem> allitems = gson.fromJson(sharedPreferences.getString("allitems", null) ,type);

        ArrayList<String> categories = new ArrayList<>();

        if(null != allitems) {
            for (int i = 0; i < allitems.size(); i++) {
                {
                    boolean doesexist = false;
                    for (String s: categories){
                        if (allitems.get(i).getCategory().equals(s))
                        {
                            doesexist = true;
                        }
                    }
                    if (!doesexist){
                        categories.add(allitems.get(i).getCategory());
                    }
                }
            }
        }
        return categories;
    }
    public ArrayList<Jerseyitem> getitemscategory(String category){
        Log.d(TAG, "getitemscategory: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Jerseyitem>>(){}.getType();
        ArrayList<Jerseyitem> allitems = gson.fromJson(sharedPreferences.getString("allitems", null) ,type);


        ArrayList<Jerseyitem>  newitems = new ArrayList<>();
        if (null != allitems) {
            for (Jerseyitem item : allitems){
                if (item.getCategory().equals(category))
                {
                    newitems.add(item);
                }
            }
        }
        return newitems;
    }

    public ArrayList<Integer> getCartItems() {
        Log.d(TAG, "getCartItems: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
        ArrayList<Integer> cartitems = gson.fromJson(sharedPreferences.getString("cartitems", null) ,type);

        Log.d(TAG, "getCartItems: Cart contains " + (cartitems != null ? cartitems.size() : 0) + " items.");
        return cartitems;
    }

    public ArrayList<Jerseyitem> getItemsById (ArrayList<Integer> ids){
        Log.d(TAG, "getItemsById: started");

        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Jerseyitem>>(){}.getType();
        ArrayList<Jerseyitem> allitems = gson.fromJson(sharedPreferences.getString("allitems", null) ,type);

        ArrayList<Jerseyitem> resultitems = new ArrayList<>();
        //if ( null != allitems){

           // for (Jerseyitem id: allitems)
        //{
                for (int id: ids){
                    if (null != allitems){
                        for (Jerseyitem item : allitems)
                        {
                            if (item.getId() == id) {
                                resultitems.add(item);
                            }
                        }

                    }

                }
            //}
        //}

        Log.d(TAG, "getItemsById: Fetched " + resultitems.size() + " items.");

        return resultitems;
    }

    public ArrayList<Integer> deleteCartItem(Jerseyitem item) {
        Log.d(TAG, "deleteCartItem: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>(){}.getType();

        //This line retrieves the list of Jerseyitem objects saved in SharedPreferences under the key "cartitems".
        ArrayList<Integer> cartitems = gson.fromJson(sharedPreferences.getString("cartitems", null) ,type);

        ArrayList<Integer> newitems = new ArrayList<>();

        if(null != cartitems)
        {
            for (int i : cartitems)
            {
                if (item.getId() != i){
                    newitems.add(i);
                }
            }

            SharedPreferences.Editor editor  = sharedPreferences.edit();
            editor.putString("cartitems", gson.toJson(newitems));
            editor.commit();
        }

        return newitems;
    }

    public void RemoveCartItems()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("cartitems");
        editor.apply();

    }
    public void addPopularityPoint(ArrayList<Integer> items)
    {
        Log.d(TAG, "addPopularityPoint: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Jerseyitem>>(){}.getType();
        ArrayList<Jerseyitem> allitems = gson.fromJson(sharedPreferences.getString("allitems", null) ,type);

        ArrayList<Jerseyitem> newitems = new ArrayList<>();
        for (Jerseyitem i : allitems)
        {
            boolean doesExist = false;
            for (int j : items)
            {
                if (i.getId() == j)
                {
                    doesExist = true;
                }
            }
            if(doesExist = true)
            {
                i.setPopularityPoint(i.getPopularityPoint()+1);
            }
            newitems.add(i);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("allitems", gson.toJson(newitems));
        editor.apply();
    }
    public void increaseUserPoint(Jerseyitem item, int points)
    {
        Log.d(TAG, "increaseUserPoint: started to increase user point for: " + item.getName() + "for" + points);
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Jerseyitem>>(){}.getType();
        ArrayList<Jerseyitem> allitems = gson.fromJson(sharedPreferences.getString("allitems", null),type);
        if (null != allitems)
        {
            ArrayList<Jerseyitem> newitems = new ArrayList<>();
            for (Jerseyitem i : allitems)
            {
                if (i.getId() == item.getId())
                {
                    i.setUserPoint(i.getUserPoint() + points);
                }
                newitems.add(i);
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("allitems", gson.toJson(newitems));
            editor.commit();
        }
    }
}
