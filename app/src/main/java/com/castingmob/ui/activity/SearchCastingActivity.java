package com.castingmob.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.casting.CastingManager;
import com.castingmob.casting.model.CastingItem;
import com.castingmob.ui.adapter.SearchCastingAdapter;
import com.castingmob.ui.view.CastingRecycleView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nishant on 10/03/16.
 */
public class SearchCastingActivity extends CastingMobActivity {


    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.search_casting)
    CastingRecycleView mSearchCastingList;

    SearchCastingAdapter searchCastingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_casting);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        searchCastingAdapter = SearchCastingAdapter.getInstance();
        mSearchCastingList.setAdapter(searchCastingAdapter);
        mSearchCastingList.setItemClickListener(onItemClickListener);
        getCasting();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getCasting(){
        CastingManager.getInstance().getCasting(Profile.getInstance().getToken(), new Callback<List<CastingItem>>() {
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
            public void onResponse(Call<List<CastingItem>> call, Response<List<CastingItem>> response) {
                if (response.isSuccessful()) {
                    refreshCastings(response.body());
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
            public void onFailure(Call<List<CastingItem>> call, Throwable t) {
                logger.error(t);
            }
        });
    }

    private void refreshCastings(List<CastingItem> castingItem) {
        searchCastingAdapter.setcasting(this, castingItem);
    }


    CastingRecycleView.OnItemClickListener onItemClickListener
            = new CastingRecycleView.OnItemClickListener() {
        @Override
        public void onItemClick(View view, final int position) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("castingItem", searchCastingAdapter.getCastByPosition(position));
            Router.startCastingDetailsActivity(SearchCastingActivity.this, bundle);
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Router.REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                Bundle bundle = data.getExtras();
                if (bundle != null){
                    fetchCasting(bundle);
                }else{
                    // Do nothing
                }
            }
        }
    }

    @OnClick(R.id.addCasting)
    public void onCastingFilterClick(){
        Router.startCastingFilterActivity(this, null);
    }

    private void fetchCasting(Bundle bundle) {
        CastingManager.getInstance().getCastingByParameter(Profile.getInstance().getToken(),
                bundle.getString("gender"),
                bundle.getString("country"),
                bundle.getString("city"),
                bundle.getString("state"), new Callback<List<CastingItem>>() {
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
                    public void onResponse(Call<List<CastingItem>> call, Response<List<CastingItem>> response) {
                        if (response.isSuccessful()) {
                            refreshCastings(response.body());
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
                    public void onFailure(Call<List<CastingItem>> call, Throwable t) {
                        logger.error(t);
                    }
                });
    }
}
