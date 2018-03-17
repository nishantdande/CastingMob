package com.castingmob.ui.fragment.feeds;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 07/08/16
 ==============================================
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.castingmob.Feed.FeedManager;
import com.castingmob.Feed.model.FeedItem;
import com.castingmob.Feed.model.Filter;
import com.castingmob.Feed.model.v2.Feeds;
import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.account.model.UserDetail;
import com.castingmob.event.EventManager;
import com.castingmob.ui.activity.CastingMobActivity;
import com.castingmob.ui.activity.NewHomeScreenActivity;
import com.castingmob.ui.activity.Router;
import com.castingmob.ui.adapter.feeds.ModelAdapter;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.ui.fragment.BaseFragment;
import com.castingmob.ui.fragment.home.HomeScreenContainerFragment;
import com.castingmob.ui.view.CastingRecycleView;
import com.castingmob.ui.view.EndlessRecyclerOnScrollListener;
import com.castingmob.ui.view.ProgressView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelsFragment extends BaseFragment{

    private static ModelsFragment modelsFragment;

    public static ModelsFragment createFragment() {
        if (modelsFragment == null) {
            modelsFragment = new ModelsFragment();
        }
        return modelsFragment;
    }

    private ModelAdapter feedAdapter;

    @Bind(R.id.feeds)
    CastingRecycleView mFeedList;
    private ProgressView progressView;
    private NewHomeScreenActivity homeScreenActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(ModelsFragment.this);
    }

    @Subscribe
    // This method will be called when a HelloWorldEvent is posted
    public void onEvent(LocationBradcast event){
        if (event.getObject() instanceof Filter){
            if (feedAdapter!=null){
                feedAdapter.clear();
            }
            Filter.getFilter().clearLastPhotoTime();
            getFeedsV2(Filter.getFilter());
        }
    }

    @Subscribe
    // This method will be called when a HelloWorldEvent is posted
    public void onEvent(EventManager event){
        if (event.getObject() instanceof Filter){
            if (feedAdapter!=null){
                feedAdapter.clear();
            }
            Filter.getFilter().clearLastPhotoTime();
            getFeedsV2(Filter.getFilter());
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_models, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        homeScreenActivity = ((NewHomeScreenActivity) getActivity());
        progressView = DialogFactory.createProgressDialog(getActivity(), "Loading...");

        feedAdapter = ModelAdapter.getInstance();
        mFeedList.setAdapter(feedAdapter);
        mFeedList.setItemClickListener(onItemClickListener);
        mFeedList.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {

                if (feedAdapter.getItemCount()>0){
                    Filter.getInstance()
                            .setLastPhotoTime(String.valueOf(feedAdapter.getFeedByPosition(feedAdapter.getItemCount()-1)
                                    .getLastUpdatedTime()));
                }
                getFeedsV2(Filter.getFilter());
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
         if (isVisibleToUser){
             if (feedAdapter!= null && feedAdapter.getItemCount()==0){
                 Filter.getInstance()
                         .setLastPhotoTime("0");
             }
             getFeedsV2(Filter.getFilter());
         }
    }

    CastingRecycleView.OnItemClickListener onItemClickListener
            = new CastingRecycleView.OnItemClickListener() {
        @Override
        public void onItemClick(View view, final int position) {


            final FeedItem feedByPosition = feedAdapter.getFeedByPosition(position);
            final ImageView feed = (ImageView) view.findViewById(R.id.feed);
            ImageView like = (ImageView) view.findViewById(R.id.like);
            final ImageView highlight_like = (ImageView) view.findViewById(R.id.highlight_like);
            ImageView comment = (ImageView) view.findViewById(R.id.comment);

            feed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homeScreenActivity.logger.debug("feed click");
                    UserDetail.getInstance().setToken(feedAdapter.getFeedByPosition(position).getUserToken());
                    EventBus.getDefault().post(new EventManager(HomeScreenContainerFragment.FEED_ACTIVITY.FEED_DETAIL));
                }
            });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homeScreenActivity.logger.debug("like click");
                    progressView.show();
                    FeedManager.getInstance().likeSelectedPhoto(Profile.getInstance().getToken(),
                            feedByPosition.getPhotoId() ,new Callback<ArrayList<String>>() {
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
                                public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                                    progressView.closeDialog();
                                    if (response.isSuccessful()){
                                        if (response.body().size() == 0){
                                            DialogFactory.createAlertDialogWithNeutralButton(getActivity(), "You already liked this photo!", "Thanks for the feedback", "Done", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    dialog.cancel();
                                                }
                                            }).show();
                                        }
                                        highlight_like.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.circle_shape));
                                        feedAdapter.updateLike(position, response.body());
                                    }else{
                                        ((CastingMobActivity)getActivity()).logger.error(new Exception(response.message()));
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
                                public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                                    progressView.closeDialog();
                                    ((CastingMobActivity)getActivity()).logger.error(t);
                                }
                            });
                }
            });

            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homeScreenActivity.logger.debug("comment click");
                    Bundle bundle = new Bundle();
                    bundle.putInt("comment", 2);
                    bundle.putParcelable("feedItem", feedByPosition);
                    Router.startMessageActivity(homeScreenActivity, bundle);
                }
            });


        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };

    private void refreshFeeds(List<FeedItem> feedItems){
        feedAdapter.setFeeds(getActivity(), feedItems);
    }

    private void getFeedsV2(Filter filter){
        filter.setUserSubType("model");
        filter.setUserType("model");
        filter.setMaxResult(10);
        FeedManager.getInstance().getFeedsV2(filter, new Callback<Feeds>() {
            @Override
            public void onResponse(Call<Feeds> call, Response<Feeds> response) {
                Log.d("HomeScreen", response.body()+"");
                if (response.isSuccessful()) {
                    refreshFeeds(response.body().getFeed());
                } else {
                    ((CastingMobActivity) getActivity()).logger.error(new Exception(response.raw() + ""));
                }
                call.cancel();
            }

            @Override
            public void onFailure(Call<Feeds> call, Throwable t) {
                ((CastingMobActivity) getActivity()).logger.error(t);
                call.cancel();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(ModelsFragment.this);
    }

    public static class LocationBradcast{

        private Object object;

        public LocationBradcast(Object object) {
            this.object = object;
        }

        public Object getObject() {
            return object;
        }
    }
}
