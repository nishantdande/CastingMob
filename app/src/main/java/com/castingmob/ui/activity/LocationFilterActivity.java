package com.castingmob.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.castingmob.Feed.model.Filter;
import com.castingmob.R;
import com.castingmob.casting.CastingManager;
import com.castingmob.casting.model.location.LocationItem;
import com.castingmob.casting.model.location.Prediction;
import com.castingmob.prefrences.ProfilePreferences;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nishant on 19/09/16.
 */
public class LocationFilterActivity extends CastingMobActivity {

    @Bind(R.id.location)
    AppCompatAutoCompleteTextView mLocation;

    @Bind(R.id.title)
    TextView mTitle;

    ArrayAdapter<String> adapter;
    ArrayList<String> locations = new ArrayList<String>();
    String city;
    String state;
    String country;

    int RESULT = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);
        ButterKnife.bind(this);

        String s = getIntent().getExtras().getString("tab");
        if (s!= null){
            if (s.contains("casting"))
            {
                RESULT = 200;
                if (getTitleLocation() != null){
                    mLocation.setText(getCastingTitleLocation());
                }
            }else if((s.contains("info")))
            {
                RESULT = 202;
                mLocation.setText(getProfileLocation(getIntent().getExtras()));
            }else{
                    RESULT = 201;
                    mTitle.setText("Select Location");
                    if (getTitleLocation() != null){
                        mLocation.setText(getTitleLocation());
                    }
                }

        }

        mLocation.addTextChangedListener(textWatcher);


    }

    public String getProfileLocation(Bundle bundle){
        String city = bundle.getString("city");
        String country = bundle.getString("country");

        StringBuilder s  = new StringBuilder();
        if (!TextUtils.isEmpty(city)){
            s.append(city+",");
        }

        if (!TextUtils.isEmpty(country)){
            s.append(country+",");
        }
        return s.toString();
    }

    public String getCastingTitleLocation(){

        StringBuilder s  = new StringBuilder();
        if (!TextUtils.isEmpty(Filter.getInstance().getCityCasting())){
            s.append(Filter.getInstance().getCityCasting()+",");
        }

        if (!TextUtils.isEmpty(Filter.getInstance().getStateCasting())){
            s.append(Filter.getInstance().getStateCasting()+",");
        }

        if (!TextUtils.isEmpty(Filter.getInstance().getCountryCasting())){
            s.append(Filter.getInstance().getCountryCasting());
        }

        return s.toString();
    }

    public String getTitleLocation(){

        StringBuilder s  = new StringBuilder();
        if (!TextUtils.isEmpty(Filter.getInstance().getCity())){
            s.append(Filter.getInstance().getCity()+",");
        }

        if (!TextUtils.isEmpty(Filter.getInstance().getState())){
            s.append(Filter.getInstance().getState()+",");
        }

        if (!TextUtils.isEmpty(Filter.getInstance().getCountry())){
            s.append(Filter.getInstance().getCountry());
        }

        return s.toString();
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

    @OnClick(R.id.clear)
    public void onClear(){
        Bundle bundle = null;
        if (RESULT == 200){
            Filter.getInstance().setCityCasting("");
            Filter.getInstance().setCountryCasting("");
            Filter.getInstance().setStateCasting("");
        }if(RESULT == 202){
            bundle = new Bundle();
            bundle.putString("city","");
            bundle.putString("country","");
        }
        else{
            Filter.getInstance().setCity("");
            Filter.getInstance().setCountry("");
            Filter.getInstance().setState("");
        }

        ProfilePreferences.getInstance().setFilterInfo(Filter.getFilter());
        Intent returnIntent = new Intent(this,NewHomeScreenActivity.class);
        if (bundle != null)
            returnIntent.putExtras(bundle);
        setResult(RESULT,returnIntent);
        finish();
    }

    @OnClick(R.id.apply)
    public void onApplyFilter(){
        getLocation();
        Bundle bundle = null;
        if (RESULT == 200) {
            Filter.getInstance().setCityCasting(city);
            Filter.getInstance().setCountryCasting(country);
            Filter.getInstance().setStateCasting(state);
        }if(RESULT == 202){
            bundle = new Bundle();
            bundle.putString("city",city);
            bundle.putString("country",country);
        }
        else{
            Filter.getInstance().setCity(city.trim().toLowerCase());
            Filter.getInstance().setCountry(country.trim().toLowerCase());
            Filter.getInstance().setState(state.trim().toLowerCase());
        }

        ProfilePreferences.getInstance().setFilterInfo(Filter.getFilter());

        Intent returnIntent = new Intent(this,NewHomeScreenActivity.class);
        if (bundle != null)
            returnIntent.putExtras(bundle);
        setResult(RESULT,returnIntent);
        finish();
    }


    public void getLocation() {
        String[] place = mLocation.getText().toString().trim().split(",");
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
                            (LocationFilterActivity.this,android.R.layout.select_dialog_item,locations);
                    mLocation.setThreshold(1);
                    mLocation.setAdapter(adapter);
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
