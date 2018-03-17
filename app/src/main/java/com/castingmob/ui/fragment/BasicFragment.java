package com.castingmob.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.castingmob.Feed.model.Filter;
import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.casting.CastingManager;
import com.castingmob.casting.model.location.LocationItem;
import com.castingmob.casting.model.location.Prediction;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.utils.ValueGenerator;

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
public class BasicFragment extends Fragment {

    @Bind(R.id.age_range)
    TextView mAge;
    @Bind(R.id.height_range)
    TextView mHeight;
    @Bind(R.id.weight_range)
    TextView mWeight;
    @Bind(R.id.locality)
    AppCompatAutoCompleteTextView mLocality;

    ArrayList<String> locations = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mLocality.addTextChangedListener(textWatcher);
    }

    @OnClick(R.id.age_range)
    public void selectAge(){
        DialogFactory.createListDialog(getContext(), "Select Age", ValueGenerator.getInstance().getAge() , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Filter.getInstance().setMinAge(String.valueOf(ValueGenerator.getInstance().getAge().get(which)));
                Filter.getInstance().setMaxAge(String.valueOf(ValueGenerator.getInstance().getAge().get(which)));
                mAge.setText("Select Age Range - "+String.valueOf(ValueGenerator.getInstance().getAge().get(which)));
            }
        });
    }

    @OnClick(R.id.height_range)
    public void selectHeight(){
        DialogFactory.createListDialog(getContext(), "Select Height in cm", ValueGenerator.getInstance().getHeight(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Filter.getInstance().setMaxHeight(String.valueOf(ValueGenerator.getInstance().getHeight().get(which)));
                Filter.getInstance().setMinHeight(String.valueOf(ValueGenerator.getInstance().getHeight().get(which)));
                mHeight.setText("Select height Range - "+ValueGenerator.getInstance().getHeight().get(which));
            }
        });
    }

    @OnClick(R.id.weight_range)
    public void selectWeight(){
        DialogFactory.createListDialog(getActivity(), "Select Weight in pounds", ValueGenerator.getInstance().getWeight(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Filter.getInstance().setMinWeight(String.valueOf(ValueGenerator.getInstance().getWeight().get(which)));
                Filter.getInstance().setMaxWeight(String.valueOf(ValueGenerator.getInstance().getWeight().get(which)));
                mWeight.setText("Select weight Range - "+ValueGenerator.getInstance().getWeight().get(which));
            }
        });
    }

    public String getLocality() {
        return mLocality.getText().toString().trim();
    }

    @OnClick(R.id.rbtnMale)
    public void onMaleClick(){
        Filter.getInstance().setGender(String.valueOf(Profile.Gender.male));
    }

    @OnClick(R.id.rbtnFemales)
    public void onFemaleClick(){
        Filter.getInstance().setGender(String.valueOf(Profile.Gender.female));
    }

    @OnClick(R.id.rbtnBoth)
    public void onBothClick(){
        Filter.getInstance().setGender(String.valueOf(Profile.Gender.both));
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
                            (getActivity(),android.R.layout.select_dialog_item,locations);
                    mLocality.setThreshold(1);
                    mLocality.setAdapter(adapter);
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
