package com.example.checkerslab_edulearning.mainHome_pkg;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.ErrorStatusDialog;
import com.example.checkerslab_edulearning.LoadingDialog;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;
import com.example.checkerslab_edulearning.myLearningPakage.MyLeaningMainModel;
import com.example.checkerslab_edulearning.myLearningPakage.MyLearningMainAdapter;
import com.example.checkerslab_edulearning.myLearningPakage.MyLearningMainFragment;
import com.example.checkerslab_edulearning.storePackage.StoreAllCoursesScreen;
import com.example.checkerslab_edulearning.storePackage.StoreCoursesAdapter;
import com.example.checkerslab_edulearning.storePackage.StoreCoursesModel;
import com.example.checkerslab_edulearning.storePackage.storeCoursesParentModel;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Home_sub_screen_fragment extends Fragment {

    String url1 = "https://medhvrushti.checkerslab.com/api/v1/cil/advertisements/images/advertisement-images/1003/4579611b-e4c2-40e8-933f-c84c70375fe7.png";
//    String url2 = "https://firebasestorage.googleapis.com/v0/b/iit-foundation.appspot.com/o/All%20Courses%20Image%2Fdemo%2Fsubscription1.jpg?alt=media&token=f52200ed-4c91-4b04-8586-dda6bfb1a517";
//    String url3 = "https://firebasestorage.googleapis.com/v0/b/iit-foundation.appspot.com/o/All%20Courses%20Image%2Fdemo%2Fsubscription3.jpg?alt=media&token=31121357-58b4-45b9-9935-e64e922c57b5";


    private RecyclerView topCatRecyclerView,popularCoursesRecyclerView;
    public  static ArrayList<TopCategoriesModel> catItemsList;
    private ArrayList<popularCoursesModel> popCoursesList;
    private LinearLayoutManager HorizontalLayout,HorizontalLayout2;

    static  public ArrayList<MyLeaningMainModel> activeSubscriptionList;
    private RelativeLayout activeSubViewALL;
    private TextView courseName,topCatSeeAll,courseStartDate,courseEndDate,courseTypeName,popularCoursesViewAll;
    private CardView activeSubCard1,activeSubCard2;
    private RelativeLayout unlockButton;
    private LoadingDialog loadingDialog;
    private ErrorStatusDialog errorStatusDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_sub_screen_fragment, container, false);

        SliderView homeSliderView = view.findViewById(R.id.Home_imageSlider);
        loadingDialog=new LoadingDialog(getActivity());
        errorStatusDialog=new ErrorStatusDialog(getActivity());


        topCatRecyclerView=view.findViewById(R.id.Home_top_categories_recyclerview);
        popularCoursesRecyclerView=view.findViewById(R.id.Home_Popular_Courses_recyclerview);
        topCatSeeAll=view.findViewById(R.id.Top_category_SeeAll_text_View);
        courseStartDate=view.findViewById(R.id.Active_subscription_Enrollment_Date_id);
        courseEndDate=view.findViewById(R.id.Active_subscription_End_Date_id);
        courseTypeName=view.findViewById(R.id.Active_subscription_SubscriptionTUpe_id);
        activeSubCard1=view.findViewById(R.id.Home_active_subscription_details_id);
        activeSubCard2=view.findViewById(R.id.Home_active_subscription_details2_id);
        unlockButton=view.findViewById(R.id.Active_subscription_Unlock_id);

        popularCoursesViewAll=view.findViewById(R.id.Home_Popular_Courses_ViewAll_id);
        unlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getContext(), StoreAllCoursesScreen.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);
            }
        });

        popularCoursesViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),StoreAllCoursesScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        catItemsList=new ArrayList<>();
        popCoursesList=new ArrayList<>();
        HorizontalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        topCatRecyclerView.setLayoutManager(HorizontalLayout);
        HorizontalLayout2
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        topCatRecyclerView.setLayoutManager(HorizontalLayout);
        popularCoursesRecyclerView.setLayoutManager(HorizontalLayout2);
        AddItemsToTopCatRecyclerView();
        addPopularCoursesToRecyclerView();

        ArrayList<HomeSliderModel> sliderDataArrayList = new ArrayList<>();
        sliderDataArrayList.add(new HomeSliderModel(url1));
//        sliderDataArrayList.add(new HomeSliderModel(url2));
//        sliderDataArrayList.add(new HomeSliderModel(url3));
        HomeSliderAdapter adapter = new HomeSliderAdapter(getContext(), sliderDataArrayList);
        homeSliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        homeSliderView.setSliderAdapter(adapter);
        homeSliderView.setScrollTimeInSec(3);
        homeSliderView.setAutoCycle(true);
        homeSliderView.startAutoCycle();

        /////////////////GET ACTIVE Subscription  Details//////////////////////////
        courseName=view.findViewById(R.id.Active_subscription_course_name_id);
        activeSubViewALL=view.findViewById(R.id.Active_subscription_viewAll_id);
        activeSubscriptionList=new ArrayList<>();
        getActiveSubscriptionDetails();

        activeSubViewALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),ActiveSubscriptionActivity.class);
                startActivity(intent);
            }
        });

        topCatSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),TopCategoriesScreen.class);
                startActivity(intent);
            }
        });
        return  view;

    }

    private void getActiveSubscriptionDetails() {
        loadingDialog.startLoadingDialog();
        String Url= StaticFile.Url+"/api/v1/cil/user_subscriptions/get/all/by/user_id?";
        Url=Url+"user_id="+ StaticFile.userId;

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, Url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                       loadingDialog.dismissDialog();
                        // Handle success response from the server
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                MyLeaningMainModel model=new MyLeaningMainModel(object.getString("user_subscription_id"),
                                        object.getString("user_id"),
                                        object.getString("subscription_id"),
                                        object.getString("standard_id"),
                                        object.getString("subscription_name"),
                                        object.getString("subscription_type"),
                                        object.getString("subscription_category"),
                                        object.getString("description"),
                                        object.getString("subscription_image"),
                                        object.getString("subscription_date"),
                                        object.getString("access_end_date"),
                                        object.getString("attribute1"));
                                activeSubscriptionList.add(model);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (activeSubscriptionList.size()>0)
                        {
                            activeSubCard2.setVisibility(View.GONE);
                            activeSubCard1.setVisibility(View.VISIBLE);
                            courseName.setText(activeSubscriptionList.get(0).getSubscription_name());
                            courseStartDate.setText(activeSubscriptionList.get(0).getSubscription_date());
                            courseEndDate.setText(activeSubscriptionList.get(0).getAccess_end_date());
                            courseTypeName.setText(activeSubscriptionList.get(0).getSubscription_type());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.dismissDialog();
                        errorStatusDialog.showErrorMessage();
                        // Handle error response
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

    private void AddItemsToTopCatRecyclerView() {
        String url=StaticFile.Url+"/api/v1/cil/course_category/get/all";

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        TopCategoriesModel model=new TopCategoriesModel(
                                object.getString("course_cat_name"),
                                object.getString("course_cat_img_url"));

                        catItemsList.add(model);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                TopCategoriesAdapter topCategoriesAdapter=new TopCategoriesAdapter(catItemsList,getContext());
                topCatRecyclerView.setAdapter(topCategoriesAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(arrayRequest);

    }

    private void addPopularCoursesToRecyclerView() {
        Log.d("addPopularCoursesToRecyclerView","addPopularCoursesToRecyclerView1");

        String Url = StaticFile.Url+"/api/v1/cil/main_subscriptions/get/all";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, Url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Log.d("addPopularCoursesToRecyclerView","addPopularCoursesToRecyclerView2");

                                popularCoursesModel storeCoursesModel=new popularCoursesModel(
                                        object.getString("subscription_img_url"),
                                        object.getString("subscription_id"));

                                popCoursesList.add(storeCoursesModel);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                        PopularCoursesAdapter storeCoursesAdapter
                                = new PopularCoursesAdapter(popCoursesList,getContext());
                        popularCoursesRecyclerView.setAdapter(storeCoursesAdapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        if (error.networkResponse != null) {
                            Log.d("addPopularCoursesToRecyclerView","addPopularCoursesToRecyclerView4");

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