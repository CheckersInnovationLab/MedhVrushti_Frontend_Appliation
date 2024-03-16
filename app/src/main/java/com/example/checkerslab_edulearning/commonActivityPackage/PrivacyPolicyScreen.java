package com.example.checkerslab_edulearning.commonActivityPackage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.checkerslab_edulearning.R;

import java.util.ArrayList;
import java.util.List;

public class PrivacyPolicyScreen extends Fragment {

    String url="https://checkerslab.com/#privacy_policy";
    private RecyclerView recyclerView;

    List<String> privacyPolicyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_privacy_policy_screen, container, false);

        Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(urlIntent);
        privacyPolicyList=new ArrayList<>();

       recyclerView=view.findViewById(R.id.PrivacyPolicy_recyclerView_id);




        return view;
    }
}