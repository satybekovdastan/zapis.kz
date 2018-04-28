package ds.zapiskz;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ds.zapiskz.Models.Categories;
import ds.zapiskz.Models.DetailShopList;
import ds.zapiskz.api.ApiService;
import ds.zapiskz.api.RetroClient;
import ds.zapiskz.slider.ImageModel;
import ds.zapiskz.slider.MenuSliderAdapter;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private static ViewPager mPager;
    ArrayList<ImageModel> listImage = new ArrayList<>();
    ArrayList<String> arrayListPhone = new ArrayList<>();
    private static int currentPage2 = 0;
    private static int NUM_PAGES = 0;
    CircleIndicator indicator;
    TextView textName, textType, textAddress, textPhone;
    int id;
    ListView listView;
    Button btnCall;
    Timer swipeTime;
    RelativeLayout progressBarRelative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        mPager = findViewById(R.id.pager);
        textName = findViewById(R.id.textName);
        textAddress = findViewById(R.id.textAddress);
        textType = findViewById(R.id.textType);
        indicator = findViewById(R.id.indicator_fragment);
        listView = findViewById(R.id.listview);
        textPhone = findViewById(R.id.textPhone);
        btnCall = findViewById(R.id.btnCall);
        progressBarRelative = findViewById(R.id.progressBarRelative);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPhone();
            }
        });

        id = getIntent().getIntExtra("id",0);

        initMain();
    }

    private void initMain() {
        progressBarRelative.setVisibility(View.VISIBLE);
        ApiService api = RetroClient.getApiService();
        Call<DetailShopList> call = api.profilePicture("salon/page/?id="+id);
        call.enqueue(new Callback<DetailShopList>() {
            @Override
            public void onResponse(Call<DetailShopList> call, Response<DetailShopList> response) {
                if (response.isSuccessful()) {
                    textName.setText(response.body().getContacts().getName());
                    textType.setText(response.body().getContacts().getType());
                    textAddress.setText(response.body().getContacts().getAddress());
                    arrayListPhone = response.body().getContacts().getPhoneNumbers();

                    textPhone.setText(arrayListPhone.get(0));

                    ArrayList<String> list = new ArrayList<>();
                    ArrayList<Categories> categories = response.body().getCategories();
                    for (int j = 0; j < categories.size(); j++) {
                        list.add(categories.get(j).getName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailActivity.this,
                            R.layout.my_list_item,
                            list);
                    listView.setAdapter(adapter);

                    ArrayList<String> images = response.body().getContacts().getPictures();
                    for (int i = 0; i < images.size(); i++) {
                        ImageModel imageModel = new ImageModel();
                        imageModel.setImage_drawable("https://zapis.kz/" + images.get(i));
                        listImage.add(imageModel);
                    }
                    init();
                }else {
                        Log.e("RESPONCE", response.code()+" res = "+response.message() );
                }

                progressBarRelative.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<DetailShopList> call, Throwable t) {
                Log.e("FAIL", t.getMessage());
            }
        });
    }

    private void init() {
        mPager.setAdapter(new MenuSliderAdapter(this, listImage));
        indicator.setViewPager(mPager);
        NUM_PAGES = listImage.size();
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage2 == NUM_PAGES) {
                    currentPage2 = 0;
                }
                mPager.setCurrentItem(currentPage2++, true);
            }
        };

        swipeTime = new Timer();
        swipeTime.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 3000, 3000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        swipeTime.cancel();
    }

    void getPhone(){
        final CharSequence[] Animals = arrayListPhone.toArray(new String[arrayListPhone.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Позвонить");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = Animals[item].toString();
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", selectedText, null)));
            }
        });
        AlertDialog alertDialogObject = dialogBuilder.create();
        alertDialogObject.show();
    }

}
