package com.example.noble_mall2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noble_mall2.Model.Jerseyitem;
import com.example.noble_mall2.Model.Review;

import java.util.ArrayList;

public class JerseyitemActivity extends AppCompatActivity implements AddReviewDialog.AddReview {
    private static final String TAG = "GroceryitemActivity";

    private ServiceConnection serviceConnection =  new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TrackUserTime.LocalBinder binder = (TrackUserTime.LocalBinder) service;
            mService = binder.getService();
            isBound = true;
            mService.setItem(incomingitem);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };
    private  TrackUserTime mService;
    private boolean isBound = false;

    private TextView txtName,txtprice,txtDescription,txtAvailability;
    private ImageView itemImage;
    private Button btnAddToCart;

    private ImageView firstfilledStar,secondfilledstar,thirdfilledstar,firstemptystar,secondemptystar,thirdemptystar;
    private RecyclerView ReviewsRecView;
    private ReviewAdapter adapter;

    private RelativeLayout addReviewRelativeLayout;

    private int currentrate = 0;

    private Jerseyitem incomingitem;



    private Utils utils;

    @Override
    public void onAddReviewResult(Review review) {
        Log.d(TAG, "onAddReviewResult: We are Adding" +review.toString());
        utils.AddReview(review);
        utils.increaseUserPoint(incomingitem,3);

        ArrayList<Review> reviews = utils.getReviewforitem(review.getGroceryitemid());
        if (null != reviews)
        {
            adapter.setReviews(reviews);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jerseyitem);

        utils = new Utils(this);

        initViews();

        Intent intent = getIntent();
        try {

            //private Jerseyitem incomingitem;
            incomingitem = intent.getParcelableExtra("item");

            if (incomingitem == null || incomingitem.getImageurl() == null) {
                Log.e(TAG, "Item or image URL is null!");
            } else {
                Log.d(TAG, "Item image URL: " + incomingitem.getImageurl());
            }

            this.currentrate = incomingitem.getRate();
            changevisibility(currentrate);
            setViewsValues();
            utils.increaseUserPoint(incomingitem,1);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }


    }

    /**
     * responsible for setting the initial values for views
     */
    private void setViewsValues()
    {
     txtName.setText(incomingitem.getName());
     txtprice.setText(String.valueOf("KSH: " + incomingitem.getPrice()));
     txtAvailability.setText(incomingitem.getAvailableAmount() +  "  piece(s)  available");
     txtDescription.setText(incomingitem.getDescription());

        Glide.with(this)
                .asBitmap()
                .load(incomingitem.getImageurl())
                .into(itemImage);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utils utils = new Utils(this);
                utils.additemtocart(incomingitem.getId());
                //TODO : Add items to the cart
                Intent cartIntent = new Intent(JerseyitemActivity.this, CartActivity.class);
                cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(cartIntent);
            }
        });

        handlestarssituation();


        adapter = new ReviewAdapter();
        ReviewsRecView.setAdapter(adapter);
        ReviewsRecView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Review> reviews = utils.getReviewforitem(incomingitem.getId());
        if (null != reviews)
        {
            adapter.setReviews(reviews);
        }

        addReviewRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : Show the dialog
                AddReviewDialog addReviewDialog = new AddReviewDialog();
                Bundle bundle = new Bundle();
                bundle.putParcelable("item", incomingitem);
                addReviewDialog.setArguments(bundle);
                addReviewDialog.show(getSupportFragmentManager(), "add review dialog");
            }
        });

    }

    private void handlestarssituation()
    {
        Log.d(TAG, "handlestarssituation: started");

        firstemptystar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (checkifratehaschanged(1))
               {
                   updateDatabase(1);
                   changevisibility(1);
                   changeUserPoint(1);
               }
            }
        });
        secondemptystar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkifratehaschanged(2))
                {
                    updateDatabase(2);
                    changevisibility(2);
                    changeUserPoint(2);
                }
            }
        });
        thirdemptystar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkifratehaschanged(3))
                {
                    updateDatabase(3);
                    changevisibility(3);
                    changeUserPoint(3);
                }
            }
        });

        firstfilledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkifratehaschanged(1))
                {
                    updateDatabase(1);
                    changevisibility(1);
                    changeUserPoint(1);
                }
            }
        });
        secondfilledstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkifratehaschanged(2))
                {
                    updateDatabase(2);
                    changevisibility(2);
                    changeUserPoint(2);
                }
            }
        });
        thirdfilledstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkifratehaschanged(3))
                {
                    updateDatabase(3);
                    changevisibility(3);
                    changeUserPoint(3);
                }
            }
        });

    }
    private void changeUserPoint(int stars)
    {
        Log.d(TAG, "changeUserPoint: started");
        utils.increaseUserPoint(incomingitem,(stars-currentrate)*2);
    }

    private void updateDatabase(int newrate)
    {
        Log.d(TAG, "updateDatabase: started");
        utils.updateTheNewrate(incomingitem, newrate);
        
    }

    private void changevisibility(int newrate)
    {
        Log.d(TAG, "changevisibility: started");
        switch (newrate)
        {
            case 0:
                firstfilledStar.setVisibility(View.GONE);
                secondfilledstar.setVisibility(View.GONE);
                thirdfilledstar.setVisibility(View.GONE);
                firstemptystar.setVisibility(View.VISIBLE);
                secondemptystar.setVisibility(View.VISIBLE);
                thirdemptystar.setVisibility(View.VISIBLE);
                break;
            case 1:
                firstfilledStar.setVisibility(View.VISIBLE);
                secondfilledstar.setVisibility(View.GONE);
                thirdfilledstar.setVisibility(View.GONE);
                firstemptystar.setVisibility(View.GONE);
                secondemptystar.setVisibility(View.VISIBLE);
                thirdemptystar.setVisibility(View.VISIBLE);
                break;
            case 2:
                firstfilledStar.setVisibility(View.VISIBLE);
                secondfilledstar.setVisibility(View.VISIBLE);
                thirdfilledstar.setVisibility(View.GONE);
                firstemptystar.setVisibility(View.GONE);
                secondemptystar.setVisibility(View.GONE);
                thirdemptystar.setVisibility(View.VISIBLE);
                break;
            case 3:
                firstfilledStar.setVisibility(View.VISIBLE);
                secondfilledstar.setVisibility(View.VISIBLE);
                thirdfilledstar.setVisibility(View.VISIBLE);
                firstemptystar.setVisibility(View.GONE);
                secondemptystar.setVisibility(View.GONE);
                thirdemptystar.setVisibility(View.GONE);
                break;
            default:
                firstfilledStar.setVisibility(View.GONE);
                secondfilledstar.setVisibility(View.GONE);
                thirdfilledstar.setVisibility(View.GONE);
                firstemptystar.setVisibility(View.VISIBLE);
                secondemptystar.setVisibility(View.VISIBLE);
                thirdemptystar.setVisibility(View.VISIBLE);
                break;
        }
    }

    private boolean checkifratehaschanged(int newrate)
    {

        if(newrate == currentrate)
        {
            return false;
        }
        else
        {
            return true;
            }
    }

    private void initViews()
    {
        Log.d(TAG, "initViews: started");

        txtName = (TextView) findViewById(R.id.txtName);
        txtprice = (TextView) findViewById(R.id.txtprice);
        txtDescription = (TextView) findViewById(R.id.txtDesc);
        txtAvailability = (TextView) findViewById(R.id.txtAvailability);

        itemImage = (ImageView) findViewById(R.id.itemImage);

        btnAddToCart = (Button) findViewById(R.id.btnAddToCart);

        firstfilledStar = (ImageView) findViewById(R.id.firstfilledstar);
        secondfilledstar = (ImageView) findViewById(R.id.secondfilledstar);
        thirdfilledstar = (ImageView) findViewById(R.id.thirdfilledstar);
        firstemptystar = (ImageView) findViewById(R.id.firstemptystar);
        secondemptystar = (ImageView) findViewById(R.id.secondemptystar);
        thirdemptystar = (ImageView) findViewById(R.id.thirdemptystar);

        ReviewsRecView = (RecyclerView) findViewById(R.id.reviewRecView);

        addReviewRelativeLayout = (RelativeLayout) findViewById(R.id.AddReviewRelLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, TrackUserTime.class);
        bindService(intent, serviceConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (isBound)
        {
            unbindService(serviceConnection);
        }
    }
}