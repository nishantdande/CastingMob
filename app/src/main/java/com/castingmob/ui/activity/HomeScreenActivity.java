package com.castingmob.ui.activity;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 24/01/16
 ==============================================
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.castingmob.CastingMob;
import com.castingmob.Feed.model.Filter;
import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.ui.adapter.MenuOptionAdapter;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.ui.fragment.home.HomeScreenFragment;
import com.castingmob.ui.view.CastingRecycleView;

import org.apache.commons.lang3.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeScreenActivity extends CastingMobActivity  {



    public interface onFilterChangeListener{
        void onFilterChange(Filter filter);
    }

    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;

    @Bind(R.id.menu_option)
    CastingRecycleView mMenuOption;
    @Bind(R.id.account_name)
    TextView mAccountName;
    @Bind(R.id.account_type)
    TextView mAccountType;
    @Bind(R.id.profile_pic)
    ImageView mProfilePic;



    public onFilterChangeListener onFilterChangeListener;

    public enum HOME_ACTIVITY{
        HOME_SCREEN(0);
        int id;
        HOME_ACTIVITY(int id) {
            this.id = id;
        }
    }

    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);

        logger.debug(Profile.getInstance().toString());
        initDrawer();
        if (savedInstanceState == null) {
            mFragmentManager = getSupportFragmentManager();
            setFragment(HOME_ACTIVITY.HOME_SCREEN, null);
        }
    }

    private void initDrawer(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,
                toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        manageLeftDrawer();
    }

    private void manageLeftDrawer(){
        MenuOptionAdapter menuOptionAdapter = new MenuOptionAdapter(this);
        mMenuOption.setAdapter(menuOptionAdapter);
        mMenuOption.setItemClickListener(onItemClickListener);


        mAccountName.setText(String.format("%s %s", Profile.getInstance().getFirstName(),
                Profile.getInstance().getLastName()));

        mAccountType.setText(StringUtils.capitalize(Profile.getInstance().getUserType()));

        Glide.with(CastingMob.getInstance().getContext())
                .load(Profile.getInstance().getProfilePhoto())
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.ic_logo_final_black)
                .into(new BitmapImageViewTarget(mProfilePic) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        mProfilePic.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    CastingRecycleView.OnItemClickListener onItemClickListener = new CastingRecycleView.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            closeLeftDrawer();
            handleMenu(view,position);
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };

    private void handleMenu(View view,int position) {
        switch (position){

            case 0:{
                Router.startMessageActivity(this, null);
            }
            break;

            case 1:{
                view.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Router.startDetailScreenActivity(HomeScreenActivity.this,
                                DetailActivity.DETAIL_FLOW.POLAROID_PHOTO_GALLERY.getId());

                    }
                });

                view.findViewById(R.id.icon).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Router.startDetailScreenActivity(HomeScreenActivity.this,
                                DetailActivity.DETAIL_FLOW.POLAROID_PHOTO.getId());

                    }
                });
            }
            break;

            case 2:{
                view.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Router.startDetailScreenActivity(HomeScreenActivity.this,
                                DetailActivity.DETAIL_FLOW.PHOTO_GALLERY.getId());

                    }
                });

                view.findViewById(R.id.icon).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Router.startDetailScreenActivity(HomeScreenActivity.this,
                                DetailActivity.DETAIL_FLOW.COVER_PHOTO.getId());

                    }
                });
            }
            break;

            case 4:{
                Router.startDetailScreenActivity(HomeScreenActivity.this,
                        DetailActivity.DETAIL_FLOW.UPDATE_STYLE.getId());
            }
                break;

            case 5:{
                view.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Router.startSearchCastingActivity(HomeScreenActivity.this, null);

                    }
                });

                view.findViewById(R.id.icon).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Router.startOwnCastingActivity(HomeScreenActivity.this, null);

                    }
                });

            }
                break;

            case 6:{
                Router.startAppliedCastingActivity(HomeScreenActivity.this, null);
            }
            break;
        }
    }

    public void onApplyFilterClick(){
        closeRightDrawer();

        if (onFilterChangeListener != null){
            onFilterChangeListener.onFilterChange(Filter.getFilter());
        }

        logger.debug(Filter.getInstance().toString());
    }

    @OnClick(R.id.menu_drawer)
    public void onDrawerClick(){
        openLeftDrawer();
    }



    @OnClick(R.id.filter)
    public void onFilterClick(){
//        openRightDrawer();
        Router.startFilterActivity(this, null);
    }


    @OnClick(R.id.check_feeds)
    public void onChangeFeed(){
        final String[] feed_type = getResources().getStringArray(R.array.feed_type);
        DialogFactory.createListDialog(this, getString(R.string.dialog_select_feed_type_title),
                feed_type, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: {
                                dialog.dismiss();
                                dialog.cancel();
                            }
                            break;

                            case 1: {
                                Router.startNewsFeedScreenActivity(HomeScreenActivity.this, null);
                            }
                            break;
                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (onFilterChangeListener != null){
            onFilterChangeListener.onFilterChange(Filter.getFilter());
        }

        logger.debug(Filter.getInstance().toString());
    }

    /**
     * Set account fragment based on the status.
     *
     * @param account_flow the current account process value.
     * @param bundle          the information bundle.
     */
    public void setFragment(HOME_ACTIVITY account_flow, Bundle bundle) {
        switch (account_flow) {
            case HOME_SCREEN: {
                addFragmentWithArgument(new HomeScreenFragment(),
                        HomeScreenFragment.class.getSimpleName(), bundle);
            }
            break;
        }
    }

    private void destroyFragment(String tagName){

        Fragment fragment = mFragmentManager.findFragmentByTag(tagName);
        if (fragment!=null)
            removeFragment(fragment);
    }

    // add the fragment with bundle as argument.
    private void addFragmentWithArgument(Fragment fragment, String tag, Bundle bundle) {
        if (bundle != null)
            fragment.setArguments(bundle);

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        mFragmentTransaction.add(R.id.container, fragment, tag);
        mFragmentTransaction.commitAllowingStateLoss();
    }

    //remove fragment from stack.
    private void removeFragment(Fragment fragment)
    {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_left);
        mFragmentTransaction.remove(fragment);
        mFragmentTransaction.commitAllowingStateLoss();
    }

    public void openRightDrawer(){
        drawerLayout.openDrawer(Gravity.RIGHT);
    }

    public void closeRightDrawer(){
        drawerLayout.closeDrawer(Gravity.RIGHT);
    }

    public void openLeftDrawer(){
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    public void closeLeftDrawer(){
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

}
