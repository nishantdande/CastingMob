package com.castingmob.ui.fragment.casting;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 07/08/16
 ==============================================
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castingmob.Feed.model.Filter;
import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.casting.CastingManager;
import com.castingmob.casting.collection.Criteria;
import com.castingmob.casting.collection.ModelCriteria;
import com.castingmob.casting.collection.PhotographerCriteria;
import com.castingmob.casting.model.CastingItem;
import com.castingmob.event.EventManager;
import com.castingmob.ui.activity.NewHomeScreenActivity;
import com.castingmob.ui.activity.Router;
import com.castingmob.ui.adapter.SearchCastingAdapter;
import com.castingmob.ui.adapter.casting.PhotographerCastingAdapter;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.ui.fragment.BaseFragment;
import com.castingmob.ui.view.CastingRecycleView;
import com.castingmob.ui.view.ProgressView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotographerCastingFragment extends BaseFragment implements NewHomeScreenActivity.onFilterChangeListener {

    private static PhotographerCastingFragment modelsFragment;

    public static PhotographerCastingFragment createFragment() {
        if (modelsFragment == null) {
            modelsFragment = new PhotographerCastingFragment();
        }
        return modelsFragment;
    }

    @Bind(R.id.search_casting)
    CastingRecycleView mSearchCastingList;
    List<CastingItem> castingItems;
    PhotographerCastingAdapter searchCastingAdapter;
    private ProgressView progressView;
    private NewHomeScreenActivity homeScreenActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(PhotographerCastingFragment.this);
    }

    @Subscribe
    // This method will be called when a HelloWorldEvent is posted
    public void onEvent(EventManager event) {
        if (event.getObject() instanceof Filter){
            if (castingItems != null) {
                Criteria photographerCasting = new PhotographerCriteria();
                refreshCastings(photographerCasting.meetCriteriaByLocation(castingItems, Filter.getFilter()));
            }
        }
        else if (event.getCastingItems() != null) {
            castingItems = event.getCastingItems();
            Criteria photographerCasting = new PhotographerCriteria();
            refreshCastings(photographerCasting.meetCriteria(event.getCastingItems()));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_models_casting, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        homeScreenActivity = ((NewHomeScreenActivity) getActivity());
        progressView = DialogFactory.createProgressDialog(getActivity(), "Loading...");

        searchCastingAdapter = PhotographerCastingAdapter.getInstance();
        mSearchCastingList.setAdapter(searchCastingAdapter);
        mSearchCastingList.setItemClickListener(onItemClickListener);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeScreenActivity = (NewHomeScreenActivity) context;
        ((NewHomeScreenActivity)context).onFilterChangeListener = this;
    }

    private void refreshCastings(List<CastingItem> castingItem) {
        searchCastingAdapter.setcasting(homeScreenActivity, castingItem);
    }


    CastingRecycleView.OnItemClickListener onItemClickListener
            = new CastingRecycleView.OnItemClickListener() {
        @Override
        public void onItemClick(View view, final int position) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("castingItem", searchCastingAdapter.getCastByPosition(position));
            Router.startCastingDetailsActivity(homeScreenActivity, bundle);
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(PhotographerCastingFragment.this);
    }

    @Override
    public void onFilterChange(Filter filter) {
        // Never Called
    }

    @Override
    public void onCastingFilterChange(Filter filter) {

    }
}
