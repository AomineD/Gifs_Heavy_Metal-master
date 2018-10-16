package com.heavymetal.giphy.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.heavymetal.giphy.adapter.AllCategoryImageAdapter;
import com.heavymetal.giphy.api.apiClient;
import com.heavymetal.giphy.api.apiRest;
import com.heavymetal.giphy.entity.Category;
import com.heavymetal.giphy.manager.PrefManager;
import com.heavymetal.giphy.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllCategoryActivity extends Fragment {

    private int id;
    private String title;
    private String image;

    private Integer page = 0;
    private String language = "0";
    private Boolean loaded=false;

    private View view;
    private SwipeRefreshLayout swipe_refreshl_all_category;
    private LinearLayout linear_layout_load_all_category;
    private RecyclerView recycler_view_all_category;
    private List<Category> categoryList =new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private PrefManager prefManager;
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private RelativeLayout relative_layout_load_more;
    private LinearLayout linear_layout_page_error;
    private Button button_try_again;
    private AllCategoryImageAdapter allCategoryImageAdapter;

    private Context mContext;

    public AllCategoryActivity() {
        // Required empty public constructor


    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        this.view =  inflater.inflate(R.layout.activity_all_category, container, false);

        if(getActivity().getApplicationContext() != null) {
            this.prefManager = new PrefManager(getActivity().getApplicationContext());
        }
        this.language=prefManager.getString("LANGUAGE_DEFAULT");

        //setContentView(R.layout.activity_all_category);
        //if(getActivity().getActionBar() != null)
           // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(getResources().getString(R.string.all_categories_status));
        showAdsBanner(this.view);
        initView(view);
        loadStatusCategory();
        initAction();
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras() ;
        this.id =  bundle.getInt("id");
        this.title =  bundle.getString("title");
        this.image =  bundle.getString("image");
    }
    private void showAdsBanner(View view) {
        if (prefManager.getString("SUBSCRIBED").equals("FALSE")) {
            final AdView mAdView = (AdView) view.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();

            //Log.e("MAIN", "showAdsBanner: "+(mAdView!=null)+" adrequest "+(adRequest!=null));

            // Start loading the ad in the background.
            mAdView.loadAd(adRequest);

            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    mAdView.setVisibility(View.VISIBLE);
                }
            });
        }

    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    public void initView(View view){
        this.relative_layout_load_more= view.findViewById(R.id.relative_layout_load_more);
        this.button_try_again =(Button) view.findViewById(R.id.button_try_again);
        this.swipe_refreshl_all_category=(SwipeRefreshLayout) view.findViewById(R.id.swipe_refreshl_all_category);
        this.linear_layout_page_error=(LinearLayout) view.findViewById(R.id.linear_layout_page_error);
        this.linear_layout_load_all_category=(LinearLayout) view.findViewById(R.id.linear_layout_load_all_category);
        this.recycler_view_all_category=(RecyclerView) view.findViewById(R.id.recycler_view_all_category);
        this.gridLayoutManager=  new GridLayoutManager(getContext(),2);
        allCategoryImageAdapter=  new AllCategoryImageAdapter(categoryList,getActivity());
        recycler_view_all_category.setHasFixedSize(true);
        recycler_view_all_category.setAdapter(allCategoryImageAdapter);
        recycler_view_all_category.setLayoutManager(gridLayoutManager);

    }
    public void initAction(){
        swipe_refreshl_all_category.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                categoryList.clear();
                page = 0;
                loading=true;
                loadStatusCategory();
            }
        });
    }
    public void loadStatusCategory(){
        swipe_refreshl_all_category.setRefreshing(true);
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<Category>> call = service.categoriesImageAll();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    if (response.body().size()!=0){
                        categoryList.clear();
                        for (int i=0;i<response.body().size();i++){
                            categoryList.add(response.body().get(i));
                        }
                        allCategoryImageAdapter.notifyDataSetChanged();
                    }
                    recycler_view_all_category.setVisibility(View.VISIBLE);
                    linear_layout_load_all_category.setVisibility(View.GONE);
                    linear_layout_page_error.setVisibility(View.GONE);
                    swipe_refreshl_all_category.setRefreshing(false);
                }else{
                    recycler_view_all_category.setVisibility(View.GONE);
                    linear_layout_load_all_category.setVisibility(View.GONE);
                    linear_layout_page_error.setVisibility(View.VISIBLE);
                    swipe_refreshl_all_category.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                recycler_view_all_category.setVisibility(View.GONE);
                linear_layout_load_all_category.setVisibility(View.GONE);
                linear_layout_page_error.setVisibility(View.VISIBLE);
                swipe_refreshl_all_category.setRefreshing(false);

            }
        });
    }
}
