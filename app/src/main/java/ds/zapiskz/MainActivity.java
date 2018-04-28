package ds.zapiskz;

import android.os.Build;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import ds.zapiskz.Adapters.RVNewsAdapter;
import ds.zapiskz.Models.News;
import ds.zapiskz.api.ApiService;
import ds.zapiskz.api.RetroClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigation;
    private int mSelectedItem;
    private static final String SELECTED_ITEM = "arg_selected_item";
    private Fragment fragment;
    RecyclerView rv_recommend, rv_popular, rv_new;
    RVNewsAdapter rvMainAdapter;
    int itemSelect = 0;
    RelativeLayout progressBarRelative;
    int p = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        navigation = findViewById(R.id.navigation);
        progressBarRelative = findViewById(R.id.progressBarRelative);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }


        MenuItem selectedItem;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            selectedItem = navigation.getMenu().findItem(mSelectedItem);
        } else {
            selectedItem = navigation.getMenu().getItem(0);
        }
        try {
            selectFragment(selectedItem);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rv_recommend = findViewById(R.id.rv_recommend);
        rv_popular = findViewById(R.id.rv_popular);
        rv_new = findViewById(R.id.rv_new);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rv_recommend.setHasFixedSize(true);
        rv_recommend.setLayoutManager(layoutManager);

        rv_popular.setHasFixedSize(true);
        rv_popular.setLayoutManager(layoutManager2);

        rv_new.setHasFixedSize(true);
        rv_new.setLayoutManager(layoutManager3);
        progressBarRelative.setVisibility(View.VISIBLE);
        getPopular();
        getRecommended();
        getRecentlyAdded();
    }

    private void selectFragment(MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.navigation_home:
                if (itemSelect != 0) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    itemSelect = 0;
                }
                return;
            case R.id.navigation_brand:
                if (itemSelect != 1) {
                    getSupportFragmentManager().popBackStack();
                    fragment = new FragmenUser();
                    ft.replace(R.id.content, fragment);
                    ft.commit();
                    itemSelect = 1;
                }
                return;
            case R.id.navigation_trc:
                if (itemSelect != 2) {
                    getSupportFragmentManager().popBackStack();
                    fragment = new FragmenUser();
                    ft.replace(R.id.content, fragment);
                    ft.commit();
                    itemSelect = 2;
                }
                return;
        }

        mSelectedItem = item.getItemId();
        for (int i = 0; i < navigation.getMenu().size(); i++) {
            MenuItem menuItem = navigation.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }

    }

    private void getRecommended() {

        ApiService api = RetroClient.getApiService();
        Call<News> call = api.getRecommended();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                Log.e("JSON", response.code() + " + " + response.isSuccessful());
                if (response.isSuccessful()) {

                    ArrayList<News> result = response.body().getArrayList();
                    rvMainAdapter = new RVNewsAdapter(MainActivity.this, result);
                    rv_recommend.setAdapter(rvMainAdapter);
                    initProgress(1);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }

    private void getPopular() {
        ApiService api = RetroClient.getApiService();
        Call<News> call = api.getPopular();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                Log.e("JSON", response.code() + " + " + response.isSuccessful());
                if (response.isSuccessful()) {

                    ArrayList<News> result = response.body().getArrayList();
                    rvMainAdapter = new RVNewsAdapter(MainActivity.this, result);
                    rv_popular.setAdapter(rvMainAdapter);
                    initProgress(1);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }

    private void getRecentlyAdded() {
        ApiService api = RetroClient.getApiService();
        Call<News> call = api.getRecentlyAdded();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                Log.e("JSON", response.code() + " + " + response.isSuccessful());
                if (response.isSuccessful()) {
                    ArrayList<News> result = response.body().getArrayList();
                    rvMainAdapter = new RVNewsAdapter(MainActivity.this, result);
                    rv_new.setAdapter(rvMainAdapter);
                    initProgress(1);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }

    public void initProgress(int page) {
        int progress = p + page;
        p = progress;
        Log.e("start", p+"");
        if (p == 3) {
            progressBarRelative.setVisibility(View.GONE);
        }
    }


    @Override
    public void onBackPressed() {
        if (itemSelect != 0) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            navigation.setSelectedItemId(R.id.navigation_home);
            itemSelect = 0;
        } else if (itemSelect == 0) {
            super.onBackPressed();
        }
    }


}
