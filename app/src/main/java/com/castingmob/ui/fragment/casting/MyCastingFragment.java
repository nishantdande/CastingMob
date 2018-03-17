package com.castingmob.ui.fragment.casting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.casting.CastingManager;
import com.castingmob.casting.model.CastingItem;
import com.castingmob.event.EventManager;
import com.castingmob.ui.activity.Router;
import com.castingmob.ui.adapter.SearchCastingAdapter;
import com.castingmob.ui.fragment.BaseFragment;
import com.castingmob.ui.fragment.home.CastingContainerFragment;
import com.castingmob.ui.view.CastingRecycleView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nishant on 23/09/16.
 */

public class MyCastingFragment extends BaseFragment{

    private static MyCastingFragment addCastingFragment;

    public static MyCastingFragment createFragment() {
        if (addCastingFragment == null)
            addCastingFragment = new MyCastingFragment();
        return addCastingFragment;
    }

    @Bind(R.id.search_casting)
    CastingRecycleView mSearchCastingList;

    SearchCastingAdapter searchCastingAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_search_casting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


        searchCastingAdapter = SearchCastingAdapter.getInstance();
        mSearchCastingList.setAdapter(searchCastingAdapter);
        mSearchCastingList.setItemClickListener(onItemClickListener);
        getCasting();
    }

    @OnClick(R.id.back)
    public void back(){
        EventBus.getDefault().post(new EventManager(CastingContainerFragment.CASTING_ACTIVITY.CASTING_HOME));
    }

    @OnClick(R.id.addCasting)
    public void addCasting(){
        EventBus.getDefault().post(new EventManager(CastingContainerFragment.CASTING_ACTIVITY.ADD_CASTING));
    }

    @OnClick(R.id.appliedCasting)
    public void appliedCasting(){
        EventBus.getDefault().post(new EventManager(CastingContainerFragment.CASTING_ACTIVITY.APPLIED_CASTING));
    }

    public void getCasting(){
        CastingManager.getInstance().getOwnCasting(Profile.getInstance().getToken(), new Callback<List<CastingItem>>() {
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
        searchCastingAdapter.setcasting(getActivity(), castingItem);
    }


    CastingRecycleView.OnItemClickListener onItemClickListener
            = new CastingRecycleView.OnItemClickListener() {
        @Override
        public void onItemClick(View view, final int position) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("castingItem", searchCastingAdapter.getCastByPosition(position));
            Router.startCastingDetailsActivity(getActivity(), bundle);
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };
}
