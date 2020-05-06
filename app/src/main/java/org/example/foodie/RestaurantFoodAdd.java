package org.example.foodie;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class RestaurantFoodAdd extends AppCompatActivity {
    private TextView restaurantName, restaurantInfo;
    private ImageView restaurantImage;


    //DrawerLayout drawer;
    //NavigationView navigationView;
    FrameLayout frameLayout;
    ActionBarDrawerToggle toggle;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_food_add);
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.rest_drawer_layout);

        nvDrawer = (NavigationView) findViewById(R.id.navView);


        View headerView = nvDrawer.getHeaderView(0);


        // Setup drawer view
        setupDrawerContent(nvDrawer);
        //FragmentManager fragmentManager=new F;

        frameLayout = (FrameLayout) findViewById(R.id.restaurantLayout);
        //View headerLayout = nvDrawer.inflateHeaderView(R.layout.nav_header);
        toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fragmentManager = getSupportFragmentManager();


        try {
            fragmentManager.beginTransaction().replace(R.id.restaurantLayout, ResturantHome.class.newInstance(), "Home");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


        //set default fragment
        loadFragment(new ResturantHome());

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;

        }


        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        boolean id = false;
        Class fragmentClass = null;
        switch (menuItem.getItemId()) {

            /*case R.id.childDetails:
                fragmentClass = ChildDetails.class;
                break;*/

            case R.id.rest_home:
                id = true;
                fragmentClass = ResturantHome.class;
                break;
            default:
                fragmentClass = ResturantHome.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();


//else {
        fragmentManager.beginTransaction().replace(R.id.restaurantLayout, fragment).addToBackStack("fragBack").commit();

        if (!id) {
        }
        getFragmentManager().popBackStack();
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.restaurantLayout, fragment);
        transaction.commit();
    }

    /*  @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          MenuInflater inflater = getMenuInflater();

          inflater.inflate(R.menu.main_activity_bar, menu);
          return super.onCreateOptionsMenu(menu);
      }
  */
    @Override
    public void onBackPressed() {

        finish();
        super.onBackPressed();
    }


}