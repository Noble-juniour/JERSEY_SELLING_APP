package com.example.noble_mall2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noble_mall2.Model.Jerseyitem;

import java.util.ArrayList;

public class ShowItemsByCategoryActivity extends AppCompatActivity {
    private static final String TAG = "ShowItemsByCategoryActi";

    private TextView txtname;
    private RecyclerView recyclerView;

    private  JerseyItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items_by_category);

        initViews();

        adapter = new JerseyItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        try {
            Intent intent = getIntent();
            String category = intent.getStringExtra("category");
            Utils utils = new Utils(this);
            ArrayList<Jerseyitem> items = utils.getitemscategory(category);
            adapter.setItems(items);
            txtname.setText(category);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

    }

    private void initViews()
    {
        Log.d(TAG, "initViews: started");

        txtname = (TextView) findViewById(R.id.txtCategory);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    }

}