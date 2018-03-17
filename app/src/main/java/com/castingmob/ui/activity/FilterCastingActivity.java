package com.castingmob.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;

import com.castingmob.R;
import com.castingmob.casting.CastingManager;
import com.castingmob.casting.model.location.LocationItem;
import com.castingmob.casting.model.location.Prediction;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nishant on 09/03/16.
 */
public class FilterCastingActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.location)
    AppCompatAutoCompleteTextView location;

    String gender;
    String city;
    String state;
    String country;

    ArrayAdapter<String> adapter;
    ArrayList<String> locations = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_casting);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        location.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    @OnClick(R.id.rbtnMale)
    public void onMaleClick(){
        gender = "male";
    }

    @OnClick(R.id.rbtnFemales)
    public void onFemaleClick(){
        gender = "female";
    }

    @OnClick(R.id.rbtnBoth)
    public void onBothClick(){
        gender = "both";
    }

    @OnClick(R.id.clear)
    public void onClear(){
        Intent returnIntent = new Intent(this,SearchCastingActivity.class);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @OnClick(R.id.apply)
    public void onApplyFilter(){
        getLocation();
        Intent returnIntent = new Intent(this,SearchCastingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("gender",gender);
        bundle.putString("city",city);
        bundle.putString("state",state);
        bundle.putString("country",country);
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void getLocation() {
        String[] place = location.getText().toString().trim().split(",");
        if (place.length == 1){
            city = place[0];
            state = "";
            country = "";
        }

        if (place.length == 2){
            city = place[0];
            state = place[1];
            country = "";
        }

        if (place.length == 3){
            city = place[0];
            state = place[1];
            country = place[2];
        }

    }



    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            CastingManager.getInstance().getLocationByText(s.toString(), new Callback<LocationItem>() {
                /**
                 * Invoked for a received HTTP response.
                 * <p/>
                 * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
                 * Call {@link Response#isSuccessful()} to determine if the response indicates success.
                 *
                 * @param call
                 * @param response
                 */
                @Override
                public void onResponse(Call<LocationItem> call, Response<LocationItem> response) {
                    locations.clear();
                    for (Prediction prediction : response.body().getPredictions()){
                        locations.add(prediction.getDescription());
                    }
                    adapter = new ArrayAdapter<String>
                            (FilterCastingActivity.this,android.R.layout.select_dialog_item,locations);
                    location.setThreshold(1);
                    location.setAdapter(adapter);
                }

                /**
                 * Invoked when a network exception occurred talking to the server or when an unexpected
                 * exception occurred creating the request or processing the response.
                 *
                 * @param call
                 * @param t
                 */
                @Override
                public void onFailure(Call<LocationItem> call, Throwable t) {

                }
            });
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
