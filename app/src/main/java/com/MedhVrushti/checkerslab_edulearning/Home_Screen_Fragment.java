package com.MedhVrushti.checkerslab_edulearning;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.MedhVrushti.checkerslab_edulearning.mainHome_pkg.Home_sub_screen_fragment;
import com.MedhVrushti.checkerslab_edulearning.myLearningPakage.MyLearningMainFragment;
import com.MedhVrushti.checkerslab_edulearning.storePackage.StoreMainFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home_Screen_Fragment extends Fragment {


    private BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home__screen_, container, false);
        bottomNavigationView=view.findViewById(R.id.navigation_drawer_id);
        if (savedInstanceState==null)
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_id,new Home_sub_screen_fragment()).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.home_menu_id:
                        selectedFragment=new Home_sub_screen_fragment();
                        break;

                    case R.id.store_menu_id:
                        selectedFragment=new StoreMainFragment();
                        break;

                    case R.id.my_learning_menu_id:
                        selectedFragment=new MyLearningMainFragment();
                        break;
                }

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_id,selectedFragment).commit();
                return true;
            }
        });
        return view;
    }

}