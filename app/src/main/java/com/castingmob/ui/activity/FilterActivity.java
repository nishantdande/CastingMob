package com.castingmob.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.castingmob.Feed.model.Filter;
import com.castingmob.R;
import com.castingmob.ui.fragment.AdvanceFragment;
import com.castingmob.ui.fragment.BasicFragment;
import com.castingmob.ui.fragment.NameFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nishant on 09/03/16.
 */
public class FilterActivity extends AppCompatActivity {

    public enum FILTER_ACTIVITY{
        NAME(0),
        BASIC(1),
        ADVANCE(2);
        int id;
        FILTER_ACTIVITY(int id) {
            this.id = id;
        }
    }

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            mFragmentManager = getSupportFragmentManager();
            setFragment(FILTER_ACTIVITY.NAME, null);
        }
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

    @OnClick(R.id.button31)
    public void onNameClick(){
        setFragment(FILTER_ACTIVITY.NAME, null);
    }

    @OnClick(R.id.button32)
    public void onBasicClick(){
        setFragment(FILTER_ACTIVITY.BASIC, null);
    }

    @OnClick(R.id.button33)
    public void onAdvanceClick(){
        setFragment(FILTER_ACTIVITY.ADVANCE, null);
    }

    @OnClick(R.id.clear)
    public void onClear(){
        Filter.getInstance().clear();
        Intent returnIntent = new Intent(this,NewHomeScreenActivity.class);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @OnClick(R.id.apply)
    public void onApplyFilter(){
        Intent returnIntent = new Intent(this,NewHomeScreenActivity.class);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void setFragment(FILTER_ACTIVITY filter_activity, Bundle bundle) {

        if (getFragmentName()!= null){
            destroyFragment(getFragmentName());
        }

        switch (filter_activity) {
            case NAME: {
                addFragmentWithArgument(new NameFragment(),
                        NameFragment.class.getSimpleName(), bundle);
            }
            break;

            case BASIC: {
                addFragmentWithArgument(new BasicFragment(),
                        BasicFragment.class.getSimpleName(), bundle);
            }
            break;

            case ADVANCE: {
                addFragmentWithArgument(new AdvanceFragment(),
                        AdvanceFragment.class.getSimpleName(), bundle);
            }
            break;
        }
    }

    // add the fragment with bundle as argument.
    private void addFragmentWithArgument(Fragment fragment, String tag, Bundle bundle) {
        if (bundle != null)
            fragment.setArguments(bundle);

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.container, fragment, tag);
        mFragmentTransaction.commit();
    }
    private void destroyFragment(String tagName){

        Fragment fragment = mFragmentManager.findFragmentByTag(tagName);
        if (fragment!=null)
            removeFragment(fragment);
    }

    //remove fragment from stack.
    private void removeFragment(Fragment fragment)
    {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.remove(fragment);
        mFragmentTransaction.commitAllowingStateLoss();
    }

    private String getFragmentName(){
        if (mFragmentManager!= null){
            Fragment fragment = mFragmentManager.findFragmentById(R.id.container);
            if (fragment != null){
                String s= fragment.getClass().getSimpleName();
                if (s!= null)
                   return s;
            }
        }

        return null;
    }

}
