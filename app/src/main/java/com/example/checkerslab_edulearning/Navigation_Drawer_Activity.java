package com.example.checkerslab_edulearning;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.checkerslab_edulearning.databinding.ActivityNavigationDrawerBinding;
import com.example.checkerslab_edulearning.subscription.User_Subscription_screen;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

public class Navigation_Drawer_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ActivityNavigationDrawerBinding binding;
    private Toolbar toolbar;

    private RelativeLayout editButton;
    private RelativeLayout laterButton;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavigationDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //**************** edit profile Dialog box

        dialog = new Dialog(Navigation_Drawer_Activity.this);
        dialog.setContentView(R.layout.add_profile_dailog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.show();

        editButton=dialog.findViewById(R.id.edit_profile_pop_up_edit_id);
        laterButton=dialog.findViewById(R.id.edit_profile_pop_up_later_id);

        laterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

     //********************************dialog end***********************************************












//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "floating action button", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//
//            }
//        });
//

        if (savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id,new Home_Screen_Fragment()).commit();
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation__drawer_, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:

                Home_Screen_Fragment profileFragment=new Home_Screen_Fragment();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_id,profileFragment);
                transaction.commit();
                break;

            case R.id.nav_subscription_id:

                User_Subscription_screen subscriptionFragment=new User_Subscription_screen();
                FragmentTransaction transaction1=getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.content_id,subscriptionFragment);
                transaction1.commit();
                break;
//
//            case R.id.nav_slideshow:
////                InboxFragment inboxFragment=new InboxFragment();
////                FragmentTransaction transaction2=getSupportFragmentManager().beginTransaction();
////                transaction2.replace(R.id.contentid,inboxFragment);
////                transaction2.commit();
////                break;
        }
        DrawerLayout layout=findViewById(R.id.drawer_layout);
        layout.closeDrawer(GravityCompat.START);
        return true;
    }


}