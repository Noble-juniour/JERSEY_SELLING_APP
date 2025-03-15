package com.example.noble_mall2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";


    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        initViews();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.drawer_open, R.string.drawer_closed);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction Transaction = getSupportFragmentManager().beginTransaction();
        Transaction.replace(R.id.fragment_container, new MainFragment());
        Transaction.commit();
        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cart:

                Intent intent = new Intent(MainActivity.this,CartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.categories:
                ShowallcategoriesDialog showallcategoriesDialog = new ShowallcategoriesDialog();
                showallcategoriesDialog.show(getSupportFragmentManager() , "all categories");

                break;
            case R.id.about:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("About")
                        .setMessage("Instructed and Developed by Noble Juniour\n\t call or whatsapp +254743827630 for orders and deliveries")
                        .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO:show url
                            }
                        });
                builder.create().show();
                break;
            case R.id.terms:
                AlertDialog.Builder termsbuilder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Terms of use")
                        .setMessage("No extra Terms\n enjoy :")
                        .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO:show url
                            }
                        });
                termsbuilder.create().show();
                break;
            case R.id.licenses:
                LicencesDialog licencesDialog = new LicencesDialog();
                licencesDialog.show(getSupportFragmentManager(),"licences dialog");
                break;
        }
        return false;
    }

        private void initViews ()
        {
            drawer = (DrawerLayout) findViewById(R.id.drawer);
            navigationView = (NavigationView) findViewById(R.id.navigationDrawer);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
        }

}