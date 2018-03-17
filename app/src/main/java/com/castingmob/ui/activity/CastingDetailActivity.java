package com.castingmob.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.castingmob.CastingMob;
import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.casting.CastingManager;
import com.castingmob.casting.model.CastingItem;
import com.castingmob.database.model.Conversation;
import com.castingmob.ui.dialog.DialogFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nishant on 10/03/16.
 */
public class CastingDetailActivity extends CastingMobActivity {


    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.post_image)
    ImageView mPostImage;
    @Bind(R.id.post_account_type)
    TextView mPostAccountType;
    @Bind(R.id.post_location)
    TextView mPostLocation;
    @Bind(R.id.post_description)
    TextView mPostDescription;

    @Bind(R.id.looking_for)
    TextView mLookingFor;

    @Bind(R.id.paid_casting)
    TextView mPaidCasting;

    @Bind(R.id.cost)
    TextView mCost;

    @Bind(R.id.start_date)
    TextView mStartDate;

    @Bind(R.id.end_date)
    TextView mEndDate;

    @Bind(R.id.post_age)
    TextView mPostAge;
    @Bind(R.id.post_sex)
    TextView mPostSex;

    CastingItem castingItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casting_detail);
        ButterKnife.bind(this);

        castingItem = getIntent().getExtras().getParcelable("castingItem");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPostAccountType.setText(castingItem.getTitle());
        mPostDescription.setText(castingItem.getDescription());
        mLookingFor.setText(castingItem.getUserType());
        mPaidCasting.setText((castingItem.getPaid() != null && castingItem.getPaid() ==  true) ? "YES" : "NO");
        mCost.setText(castingItem.getPay() == null || TextUtils.isEmpty(castingItem.getPay()) ? "N/A" : castingItem.getPay());
        mStartDate.setText(castingItem.getStartDate()== null || TextUtils.isEmpty(castingItem.getStartDate()) ? "N/A" : castingItem.getStartDate());
        mEndDate.setText(castingItem.getEndDate() == null || TextUtils.isEmpty(castingItem.getEndDate())? "N/A" : castingItem.getEndDate());
        mPostAge.setText(getString(R.string.casting_detail_age, castingItem.getMaxAge()));
        mPostSex.setText(getString(R.string.casting_detail_sex, castingItem.getGender()));
        mPostLocation.setText(String.format("%s,%s,%s",castingItem.getCity(),
                castingItem.getState(),
                castingItem.getCountry()));

        Glide.with(CastingMob.getInstance().getContext())
                .load(castingItem.getPhotoUrl())
                .centerCrop()
                .placeholder(R.drawable.background)
                .crossFade()
                .into(mPostImage);
    }

    @OnClick(R.id.applyCasting)
    public void applyCasting(){
        CastingManager.getInstance().applyCasting(Profile.getInstance().getToken(),
                String.valueOf(castingItem.getId()), new Callback<Conversation>() {
                    @Override
                    public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                        if (response.isSuccessful()){
                            DialogFactory.createAlertDialogWithNeutralButton(CastingDetailActivity.this,
                                    "Message Sent", "The Casting is added to your applied section.",
                                    "Done", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                                dialogInterface.cancel();
                                        }
                                    });
                        }else{
                            logger.error(new Exception(response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Conversation> call, Throwable t) {
                        logger.error(t);
                    }
                });
    }
}
