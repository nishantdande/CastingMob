package com.castingmob.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.castingmob.Feed.model.Filter;
import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.utils.MeasurementConversion;
import com.castingmob.utils.ValueGenerator;
import com.cocosw.bottomsheet.BottomSheet;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nishant on 09/03/16.
 */
public class AdvanceFragment extends Fragment {

    @Bind(R.id.select_filter_type)
    TextView mSelectAccountType;

    @Bind(R.id.age_range)
    TextView mAge;
    @Bind(R.id.height_range)
    TextView mHeight;
    @Bind(R.id.weight_range)
    TextView mWeight;
    @Bind(R.id.locality)
    EditText mLocality;
    @Bind(R.id.nationality)
    TextView mNationality;
    @Bind(R.id.hair_color)
    TextView mHairColor;
    @Bind(R.id.eye_color)
    TextView mEyeColor;
    @Bind(R.id.chest_range)
    TextView mChest;
    @Bind(R.id.hip_range)
    TextView mHip;
    @Bind(R.id.waist_range)
    TextView mWaist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mLocality.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Filter.getInstance().setCity(getLocality());
                }
                return false;
            }
        });
    }

    @OnClick(R.id.select_filter_type)
    public void onFilterTypeSelect(){
        new BottomSheet.Builder(getActivity()).title("Search for name of").sheet(R.menu.menu_search_type)
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case R.id.any:
                                mSelectAccountType.setText(getString(R.string.account_selection_type_any));
                                Filter.getInstance().setUserType(getString(R.string.account_selection_type_any));
                                break;

                            case R.id.model:
                                mSelectAccountType.setText(getString(R.string.user_selection_type_model));
                                Filter.getInstance().setUserType(getString(R.string.user_selection_type_model));
                                break;

                            case R.id.agent:
                                mSelectAccountType.setText(getString(R.string.account_selection_type_agent));
                                Filter.getInstance().setUserType(getString(R.string.account_selection_type_agent));
                                break;

                            case R.id.client:
                                mSelectAccountType.setText(getString(R.string.account_selection_type_client));
                                Filter.getInstance().setUserType(getString(R.string.account_selection_type_client));
                                break;
                        }
                    }
                }).show();
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

    @OnClick(R.id.nationality)
    public void onNationalityClick(){
            final String[] nationality = getResources().getStringArray(R.array.nationality);
            DialogFactory.createListDialog(getActivity(), getString(R.string.dialog_nationality_title), nationality, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mNationality.setText("Select Nationality - "+nationality[which]);
                    Filter.getInstance().setEthnicity(nationality[which]);
                }
            });
    }

    @OnClick(R.id.hair_color)
    public void onHairColorClick(){
            final String[] hair_colors = getResources().getStringArray(R.array.hair_color);
            DialogFactory.createListDialog(getActivity(), getString(R.string.dialog_hair_color_title), hair_colors , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mHairColor.setText("Select Hair Color - "+hair_colors[which]);
                    Filter.getInstance().setHairColor(hair_colors[which]);
                }
            });
    }

    @OnClick(R.id.eye_color)
    public void onEyeColorClick(){
            final String[] eye_colors = getResources().getStringArray(R.array.eye_color);
            DialogFactory.createListDialog(getActivity(), getString(R.string.dialog_eye_color_title), eye_colors, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mEyeColor.setText("Select Eye Color - "+eye_colors[which]);
                    Filter.getInstance().setEyeColor(eye_colors[which]);
                }
            });
    }

    @OnClick(R.id.chest_range)
    public void onChestClick(){
            DialogFactory.createListDialog(getActivity(), "Select Chest", ValueGenerator.getInstance().getOthers() , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Filter.getInstance().setMinChest(String.valueOf(ValueGenerator.getInstance().getOthers().get(which)));
                    Filter.getInstance().setMaxChest(String.valueOf(ValueGenerator.getInstance().getOthers().get(which)));
                    mChest.setText("Select Chest Range - "+ValueGenerator.getInstance().getOthers().get(which));
                }
            });
    }

    @OnClick(R.id.hip_range)
    public void onHipClick(){
            DialogFactory.createListDialog(getActivity(), "Select Hip", ValueGenerator.getInstance().getOthers() , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Filter.getInstance().setMinHip(String.valueOf(ValueGenerator.getInstance().getOthers().get(which)));
                    Filter.getInstance().setMaxHip(String.valueOf(ValueGenerator.getInstance().getOthers().get(which)));
                    mHip.setText("Select Hip Range - "+ValueGenerator.getInstance().getOthers().get(which));
                }
            });
    }

    @OnClick(R.id.waist_range)
    public void onWaistClick(){
            DialogFactory.createListDialog(getActivity(), "Select Waist", ValueGenerator.getInstance().getOthers() , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Filter.getInstance().setMinWaist(String.valueOf(ValueGenerator.getInstance().getOthers().get(which)));
                    Filter.getInstance().setMaxWaist(String.valueOf(ValueGenerator.getInstance().getOthers().get(which)));
                    mWaist.setText("Select Waist Range - "+ValueGenerator.getInstance().getOthers().get(which));
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
}
