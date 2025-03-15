package com.example.noble_mall2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noble_mall2.Model.Jerseyitem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class Search_Activity extends AppCompatActivity implements ShowallcategoriesDialog.SelectCategory {
    private static final String TAG = "Search_Activity";

    @Override
    public void onSelectedCategoryResult(String category) {
        Log.d(TAG, "onSelectedCategoryResult: category" + category);
        Intent intent = new Intent(this, ShowItemsByCategoryActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    private EditText searchBar;
    private ImageView btnSearch;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;
    private TextView txtFirstCat,txtSecondCat,txtThirdCat,txtSeeAllCategories;

    private JerseyItemAdapter adapter;

    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        utils = new Utils(this);
        adapter = new JerseyItemAdapter(this);

        initViews();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        initBottomNavigationView();
        initthethreetextviews();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatesearch();
            }
        });
        txtSeeAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowallcategoriesDialog showallcategoriesDialog = new ShowallcategoriesDialog();
                showallcategoriesDialog.show(getSupportFragmentManager(), "all dialog");

                //TODO : Show a dialog
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Jerseyitem> items = utils.searchforitem(String.valueOf(s));
                adapter.setItems(items);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initthethreetextviews()
    {
        Log.d(TAG, "initthethreetextviews: started");
        ArrayList<String> categories = utils.getthethreecategories();

        switch (categories.size())
        {
            case 1:
                txtFirstCat.setText(categories.get(0));
                txtSecondCat.setVisibility(View.GONE);
                txtThirdCat.setVisibility(View.GONE);
                break;
            case 2:
                txtFirstCat.setText(categories.get(0));
                txtSecondCat.setText(categories.get(1));
                txtThirdCat.setVisibility(View.GONE);
                break;
            case 3:
                txtFirstCat.setText(categories.get(0));
                txtSecondCat.setText(categories.get(1));
                txtThirdCat.setText(categories.get(2));
                break;
            default:
                break;
        }
        txtFirstCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search_Activity.this, ShowItemsByCategoryActivity.class);
                intent.putExtra("category",txtFirstCat.getText().toString());
                startActivity(intent);
            }
        });
        txtSecondCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search_Activity.this, ShowItemsByCategoryActivity.class);
                intent.putExtra("category",txtSecondCat.getText().toString());
                startActivity(intent);
            }
        });
        txtThirdCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search_Activity.this, ShowItemsByCategoryActivity.class);
                intent.putExtra("category",txtThirdCat.getText().toString());
                startActivity(intent);
            }
        });
    }

     private void initiatesearch()
     {
         Log.d(TAG, "initiatesearch: started");


         String text = searchBar.getText().toString();
         ArrayList<Jerseyitem> items = utils.searchforitem(text);
         for (Jerseyitem item : items)
         {
             utils.increaseUserPoint(item,3);
         }
         adapter.setItems(items);
     }

    private void initViews()
    {
        Log.d(TAG, "initViews: started");

        searchBar = (EditText) findViewById(R.id.edittextsearchbar);
        btnSearch = (ImageView) findViewById(R.id.btnsearch);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationView);
        txtFirstCat = (TextView) findViewById(R.id.firstcategory);
        txtSecondCat = (TextView) findViewById(R.id.secondcategory);
        txtThirdCat = (TextView) findViewById(R.id.thirdcategory);
        txtSeeAllCategories = (TextView) findViewById(R.id.btnAllCategories);
    }

    private void initBottomNavigationView()
    {
        Log.d(TAG, "initBottomNavigationView: started");
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search:
                        Toast.makeText(Search_Activity.this, "Search Button clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.homeActivity:
                        Intent intent = new Intent(Search_Activity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                        break;
                    case R.id.cart:
                        Intent cartintent = new Intent(Search_Activity.this, CartActivity.class);
                        cartintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartintent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

}