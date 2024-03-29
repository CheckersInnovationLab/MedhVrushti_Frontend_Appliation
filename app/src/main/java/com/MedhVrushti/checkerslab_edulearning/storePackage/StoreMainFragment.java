package com.MedhVrushti.checkerslab_edulearning.storePackage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.MedhVrushti.checkerslab_edulearning.ErrorStatusDialog;
import com.MedhVrushti.checkerslab_edulearning.LoadingDialog;
import com.MedhVrushti.checkerslab_edulearning.Navigation_Drawer_Activity;
import com.MedhVrushti.checkerslab_edulearning.R;
import com.MedhVrushti.checkerslab_edulearning.StaticFile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StoreMainFragment extends Fragment {


    private RecyclerView recyclerView1;
    private ArrayList<storeCoursesParentModel> storeCoursesList;
    private LinearLayoutManager HorizontalLayout;
    private String Url = StaticFile.Url+"/api/v1/cil/main_subscriptions/get/all";
    private LoadingDialog loadingDialog;
    private ErrorStatusDialog errorStatusDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main__store_fragment, container, false);

        loadingDialog=new LoadingDialog(getActivity());
        errorStatusDialog=new ErrorStatusDialog(getActivity());


        recyclerView1 = view.findViewById(R.id.Store_courses_recycler_id);
        storeCoursesList = new ArrayList<>();
        HorizontalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView1.setLayoutManager(HorizontalLayout);
        AddItemsToTopCatRecyclerView();


        return view;
    }

    private void AddItemsToTopCatRecyclerView() {

        loadingDialog.startLoadingDialog();
        if (Navigation_Drawer_Activity.studCourseName.isEmpty() )
        {
            Url = StaticFile.Url+"/api/v1/cil/main_subscriptions/get/all";
        }
        else
        {
            Url=Url+"/by/std_id?standard_id="+Navigation_Drawer_Activity.studCourseName;

        }

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, Url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        loadingDialog.dismissDialog();
                        List<StoreCoursesModel> ChildItemList
                                = new ArrayList<>();
                        List<StoreCoursesModel> StudyMaterialList
                                = new ArrayList<>();

                        List<storeCoursesParentModel> itemList
                               = new ArrayList<>();
                        // Handle success response from the server
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                StoreCoursesModel storeCoursesModel=new StoreCoursesModel(
                                object.getString("subscription_id"),
                                        object.getString("subscription_code"),
                                        object.getString("subscription_name"),
                                        object.getString("subscription_type"),
                                        object.getString("subscription_category"),
                                        object.getString("standard_id"),
                                        object.getString("subject_id"),
                                        object.getString("subscription_price"),
                                        object.getString("description"),
                                        object.getString("default_discount"),
                                        object.getString("subscription_img_url"),
                                        object.getString("total_validity"));

                                if(!(object.getString("subject_id")=="null"))
                                {
                                    ChildItemList.add(storeCoursesModel);
                                }
                                if(object.getString("subject_id")=="null")
                                {
                                    Log.d("subject_id", object.getString("subject_id"));
                                    StudyMaterialList.add(storeCoursesModel);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        if (!StudyMaterialList.isEmpty())
                        {
                            storeCoursesParentModel item
                                    = new storeCoursesParentModel(
                                    "Popular Test Series ",
                                    StudyMaterialList);
                            itemList.add(item);
                        }
                        if (!ChildItemList.isEmpty())
                        {
                            storeCoursesParentModel item2
                                    = new storeCoursesParentModel(
                                    "Subject wise Test Series",
                                    ChildItemList);
                            itemList.add(item2);
                        }

                        storeCoursesParentAdapter myLearningMainAdapter = new storeCoursesParentAdapter(itemList, getContext());
                        recyclerView1.setAdapter(myLearningMainAdapter);

//                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.dismissDialog();
                        errorStatusDialog.showErrorMessage();
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            System.out.println("Error Status Code: " + statusCode);
                            System.out.println("Error Response Data: " + errorMessage);
                        }
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}