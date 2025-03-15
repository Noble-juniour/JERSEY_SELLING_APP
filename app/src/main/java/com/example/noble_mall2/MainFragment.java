package com.example.noble_mall2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noble_mall2.Model.Jerseyitem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private RecyclerView newsitemsRecView,popularitemsRec,suggesteditemsRecView;
    private BottomNavigationView bottomnavigationView;

    private JerseyItemAdapter newsitemAdapter,popularitemsAdapter, suggesteditemsAdapter;
    private Utils utils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initView(view);
        initBottomNavigationView();

        utils = new Utils(getActivity());
        utils.initDatabase(getActivity());

        initRecViews();


        return view;
    }
    private void initRecViews()
    {
        Log.d(TAG, "initRecViews: started");
        newsitemAdapter = new JerseyItemAdapter(getActivity());
        popularitemsAdapter = new JerseyItemAdapter(getActivity());
        suggesteditemsAdapter = new JerseyItemAdapter(getActivity());

        newsitemsRecView.setAdapter(newsitemAdapter);
        popularitemsRec.setAdapter(popularitemsAdapter);
        suggesteditemsRecView.setAdapter(suggesteditemsAdapter);

        newsitemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularitemsRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        suggesteditemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));


    }

    private void updateRecView()
    {
        Log.d(TAG, "updateRecView: started");
        ArrayList<Jerseyitem> newitems = utils.getAllitems(getActivity());

        Comparator<Jerseyitem> newsitemComparator = new Comparator<Jerseyitem>() {
            @Override
            public int compare(Jerseyitem o1, Jerseyitem o2) {
                return o1.getId() - o2.getId();
            }
        };
        Comparator<Jerseyitem>reversenewsitemComparator = Collections.reverseOrder(newsitemComparator);
        Collections.sort(newitems,reversenewsitemComparator);
        if (null != newitems)
        {
            newsitemAdapter.setItems(newitems);
        }

        ArrayList<Jerseyitem>popularitems = utils.getAllitems(getActivity());

        Comparator<Jerseyitem>popularityyComparator = new Comparator<Jerseyitem>() {
            @Override
            public int compare(Jerseyitem o1, Jerseyitem o2) {
                return compareByPopularity(o1,o2);
            }
        };

        Comparator<Jerseyitem> reversepopularityComparator =  Collections.reverseOrder(popularityyComparator);
        Collections.sort(popularitems,reversepopularityComparator);

        popularitemsAdapter.setItems(popularitems);

        ArrayList<Jerseyitem>suggesteditems = utils.getAllitems(getActivity());

        Comparator<Jerseyitem>suggesteditemsComparator = new Comparator<Jerseyitem>() {
            @Override
            public int compare(Jerseyitem o1, Jerseyitem o2) {
                return o1.getUserPoint() - o2.getUserPoint();
            }
        };

        Comparator<Jerseyitem> reversesuggesteditemsComparator = Collections.reverseOrder(suggesteditemsComparator);
        Collections.sort(suggesteditems,reversesuggesteditemsComparator);
        suggesteditemsAdapter.setItems(suggesteditems);
        //updateRecView();
    }
    @Override
    public void onResume() {
        updateRecView();
        super.onResume();
    }

    private int compareByPopularity (Jerseyitem item1, Jerseyitem item2)
    {
        Log.d(TAG, "compareByPopularity: started");
        //return item1.getPopularityPoint() - item2.getPopularityPoint();
        if (item1.getPopularityPoint()>item2.getPopularityPoint())
        {
            return 1;
        } else if (item1.getPopularityPoint()<item2.getPopularityPoint()) {
            return -1;
        }else
        {
            return 0;
        }
    }

    private void initBottomNavigationView()
    {
        Log.d(TAG, "initBottomNavigationView: started");
        bottomnavigationView.setSelectedItemId(R.id.homeActivity);
        bottomnavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search:
                        Intent intent = new Intent(getActivity(),Search_Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.homeActivity:
                        //TODO: fix this
                        break;
                    case R.id.cart:
                        Intent cartIntent = new Intent(getActivity(), CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void initView(View view)
    {
        Log.d(TAG, "initView: started");
        bottomnavigationView = (BottomNavigationView) view.findViewById(R.id.bottomnavigationView);
        newsitemsRecView = (RecyclerView) view.findViewById(R.id.newitemsRecView);
        popularitemsRec = (RecyclerView) view.findViewById(R.id.PopularitemsRecView);
        suggesteditemsRecView = (RecyclerView) view.findViewById(R.id.suggesteditemsRecView);
    }
}
