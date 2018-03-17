package com.castingmob.ui.fragment.casting;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.castingmob.CastingMob;
import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.account.model.Account;
import com.castingmob.account.model.PhotoItem;
import com.castingmob.account.model.Profile;
import com.castingmob.casting.CastingManager;
import com.castingmob.casting.model.CastingItem;
import com.castingmob.casting.model.location.LocationItem;
import com.castingmob.casting.model.location.Prediction;
import com.castingmob.event.EventManager;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.ui.fragment.BaseFragment;
import com.castingmob.ui.fragment.home.CastingContainerFragment;
import com.castingmob.ui.view.ProgressView;
import com.castingmob.utils.ImageUtility;
import com.castingmob.utils.ValueGenerator;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nishant on 23/09/16.
 */

public class AddCastingFragment extends BaseFragment{

    private static  AddCastingFragment addCastingFragment;
    private Account.subType subType;
    private SimpleDateFormat dateFormatter;

    public static AddCastingFragment createFragment() {
        if (addCastingFragment == null)
            addCastingFragment = new AddCastingFragment();
        return addCastingFragment;
    }

    private int REQUEST_CAMERA = 101;
    private int SELECT_FILE = 102;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.looking_offering)
    EditText mJobPosting;
    @Bind(R.id.requirement)
    EditText mRequirement;
    @Bind(R.id.post_image)
    ImageView mUploadPhoto;
    @Bind(R.id.post_age)
    TextView mAge;

    Profile.Gender gender;
    private ProgressView progressView;
    String photoUrl;

    @Bind(R.id.create_casting)
    Button mCreateCasting;

    @Bind(R.id.rbtnMale)
    RadioButton rbtnMale;
    @Bind(R.id.rbtnFemales)
    RadioButton rbtnFemale;
    @Bind(R.id.rbtnBoth)
    RadioButton rbtnBoth;

    @Bind(R.id.rbtnModel)
    RadioButton rbtnModel;
    @Bind(R.id.rbtnPhotographer)
    RadioButton rbtnPhotographer;
    @Bind(R.id.rbtnMUAs)
    RadioButton rbtnMUAs;
    @Bind(R.id.rbtnStylist)
    RadioButton rbtnStylist;

    @Bind(R.id.cost)
    TextView mCost;

    @Bind(R.id.start_date)
    TextView mStartDate;

    @Bind(R.id.end_date)
    TextView mEndDate;

    @Bind(R.id.paid_casting)
    SwitchCompat mPaid;

    @Bind(R.id.location)
    AppCompatAutoCompleteTextView mLocation;

    boolean isEditable;
    CastingItem castingItem = null;

    String city = "";
    String state = "";
    String country = "";
    String minAge = "";
    String maxAge = "";

    ArrayAdapter<String> adapter;
    ArrayList<String> locations = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_casting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        progressView = DialogFactory.createProgressDialog(getActivity(), "Loading...");
        mLocation.addTextChangedListener(textWatcher);
        rbtnMale.setChecked(true);
        rbtnModel.setChecked(true);
    }

    @OnClick(R.id.back)
    public void back(){
        EventBus.getDefault().post(new EventManager(CastingContainerFragment.CASTING_ACTIVITY.MY_CASTING));
    }

    @OnClick(R.id.rbtnMale)
    public void onMaleClick(){
        gender = Profile.Gender.male;
    }

    @OnClick(R.id.rbtnFemales)
    public void onFemaleClick(){
        gender = Profile.Gender.female;
    }

    @OnClick(R.id.rbtnBoth)
    public void onBothClick(){
        gender = Profile.Gender.both;
    }

    @OnClick(R.id.rbtnModel)
    public void onModelClick(){
        subType = Account.subType.model;
    }

    @OnClick(R.id.rbtnPhotographer)
    public void onPhotographerClick(){
        subType = Account.subType.photographer;
    }

    @OnClick(R.id.rbtnMUAs)
    public void onMUAsClick(){
        subType = Account.subType.makeupartist;
    }

    @OnClick(R.id.rbtnStylist)
    public void onStylistClick(){
        subType = Account.subType.stylist;
    }

    @OnClick(R.id.upload_photos)
    public void uploadPhoto(){
        String[] uploadPhoto = {"Take Photo", "Upload Photo"};

        DialogFactory.createListDialog(getActivity(), "", uploadPhoto, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        onTakePhotoClick();
                        break;

                    case 1:
                        onChoosePhotoClick();
                        break;
                }
            }
        });
    }

    @OnClick(R.id.start_date)
    public void startDate(){
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog dobDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                Date current = newDate.getTime();
                int diff1 = new Date().compareTo(current);

                if (diff1 < 0) {
                    Toast.makeText(getActivity(), "Please select a valid date", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    mStartDate.setText(dateFormatter.format(newDate.getTime()));
                }

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        dobDatePickerDialog.show();
    }

    @OnClick(R.id.end_date)
    public void endDate(){
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog dobDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                Date current = newDate.getTime();
                int diff1 = new Date().compareTo(current);

                if (diff1 < 0) {
                    Toast.makeText(getActivity(), "Please select a valid date", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    mEndDate.setText(dateFormatter.format(newDate.getTime()));
                }

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        dobDatePickerDialog.show();
    }

    @OnClick(R.id.create_casting)
    public void createCasting(){
        progressView.show();
        CastingManager.getInstance().addCasting(Profile.getInstance().getToken(),
                    getJobPosting(), getRequirement(), minAge, maxAge, getGender(),
                    city, state, country, getPhotoUrl(),getSubType(), getPaid(),getCost(),
                getStartDate(), getEndDate(),new Callback<CastingItem>() {
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
                        public void onResponse(Call<CastingItem> call, Response<CastingItem> response) {
                            progressView.closeDialog();
                            if (response.isSuccessful()) {
                                response.body().toString();
                            } else {
                                logger.error(new Exception(response.raw() + ""));
                            }
                        }

                        /**
                         * Invoked when a network exception occurred talking to the server or when an unexpected
                         * exception occurred creating the request or processing the response.
                         *
                         * @param call
                         * @param t
                         */
                        @Override
                        public void onFailure(Call<CastingItem> call, Throwable t) {
                            progressView.closeDialog();
                            logger.error(t);
                        }
                    });
    }

    @OnClick(R.id.post_age)
    public void selectAge(){
        DialogFactory.createListDialog(getActivity(),
                "Select Age", ValueGenerator.getInstance().getAge(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                minAge = String.valueOf(ValueGenerator.getInstance().getAge().get(which)-5);
                maxAge = String.valueOf(ValueGenerator.getInstance().getAge().get(which)+5);
                mAge.setText(String.format("Between %d - %d",(ValueGenerator.getInstance().getAge().get(which)-5),
                        ((ValueGenerator.getInstance().getAge().get(which))+5)));
            }
        });
    }

    public void onTakePhotoClick(){
//        - See more at: http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample#sthash.fD2nNIRu.dpuf
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    public void onChoosePhotoClick(){
//        - See more at: http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample#sthash.fD2nNIRu.dpuf
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                SELECT_FILE);
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            logger.error(new Exception("something went wrong with upload photos"));
            return;
        }

        if (data == null){
            logger.error(new Exception("Intent data is null"));
            return;
        }

        if (requestCode == REQUEST_CAMERA){
            Bitmap bitmap = (Bitmap) (data != null ? data.getExtras().get("data") : null);
            mUploadPhoto.setImageBitmap(bitmap);
            String destination = ImageUtility.getImageDestinationofCapturedPhoto(bitmap);

            if (destination != null){
                logger.debug(destination);
                uploadCastingPhoto(destination);
            }else{
                logger.error(new Exception("Image location not found"));
            }
        }else if(requestCode == SELECT_FILE){
            mUploadPhoto.setImageBitmap(ImageUtility.getBitmapFromGallery(getActivity(), data));
            if (data != null){
                uploadCastingPhoto(ImageUtility.getGalleyImagePath(data, getActivity()));
            }else {
                logger.error(new Exception("Image location not found"));
            }

        }
    }

    private void uploadCastingPhoto(String destination){
        progressView.show();
        AccountManager.getInstance().uploadCastingPhoto(Profile.getInstance().getToken(),
                destination, new Callback<PhotoItem>() {
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
                    public void onResponse(Call<PhotoItem> call, Response<PhotoItem> response) {
                        logger.debug(response.raw() + " " + response.body() + " " + response.isSuccessful());
                        progressView.closeDialog();
                        if (response.isSuccessful()){
                            photoUrl = response.body().getPhotoUrl();
                        }else{
                            logger.error(new Exception(response.raw() + ""));
                        }
                    }

                    /**
                     * Invoked when a network exception occurred talking to the server or when an unexpected
                     * exception occurred creating the request or processing the response.
                     *
                     * @param call
                     * @param t
                     */
                    @Override
                    public void onFailure(Call<PhotoItem> call, Throwable t) {
                        logger.error(t);
                        progressView.closeDialog();
                    }
                });
    }

    public String getJobPosting() {
        return mJobPosting.getText().toString().trim();
    }

    public String getRequirement() {
        return mRequirement.getText().toString().trim();
    }

    public String getAge() {
        return mAge.getText().toString().trim();
    }

    public Profile.Gender getGender() {
        return gender;
    }

    public Account.subType getSubType() {
        return subType;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Double getCost() {
        return  (!TextUtils.isEmpty(mCost.getText().toString().trim()) ?
                Double.valueOf(mCost.getText().toString().trim()) : 0);
    }

    public String getStartDate() {
        return mStartDate.getText().toString().trim();
    }

    public String getEndDate() {
        return mEndDate.getText().toString().trim();
    }

    public String getPaid() {
        return ((mPaid.isChecked()) ? "YES" : "NO");
    }

    public void setJobPosting(String mJobPosting) {
        this.mJobPosting.setText(mJobPosting);
    }

    public void setLocation(String mLocation) {
        this.mLocation.setText(mLocation);
    }

    public void setRequirement(String mRequirement) {
        this.mRequirement.setText(mRequirement);
    }

    public void setAge(String mAge) {
        this.mAge.setText(mAge);
    }

    public void setGender(Profile.Gender gender) {
        if (gender.equals(Profile.Gender.male)) {
            rbtnMale.setChecked(true);
        }

        if (gender.equals(Profile.Gender.female)) {
            rbtnFemale.setChecked(true);
        }

        if (gender.equals(Profile.Gender.both)) {
            rbtnBoth.setChecked(true);
        }
        this.gender = gender;
    }

    public void setSubUserType(Account.subType subType) {
        if (subType.equals(Account.subType.model)) {
            rbtnModel.setChecked(true);
        }

        if (subType.equals(Account.subType.photographer)) {
            rbtnPhotographer.setChecked(true);
        }

        if (subType.equals(Account.subType.makeupartist)) {
            rbtnMUAs.setChecked(true);
        }

        if (subType.equals(Account.subType.stylist)) {
            rbtnStylist.setChecked(true);
        }

        this.subType = subType;
    }



    public void setPhotoUrl(String photoUrl) {
        Glide.with(CastingMob.getInstance().getContext())
                .load(photoUrl)
                .centerCrop()
                .placeholder(R.drawable.background)
                .crossFade()
                .into(mUploadPhoto);
        this.photoUrl = photoUrl;
    }

    public void changeButton(){
        mCreateCasting.setText("Edit");
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
                            (getActivity(),android.R.layout.select_dialog_item,locations);
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
