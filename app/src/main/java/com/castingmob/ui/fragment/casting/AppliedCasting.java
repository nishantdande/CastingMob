package com.castingmob.ui.fragment.casting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.casting.CastingManager;
import com.castingmob.casting.model.CastingItem;
import com.castingmob.database.model.Conversation;
import com.castingmob.database.model.Message;
import com.castingmob.event.EventManager;
import com.castingmob.message.MessageApiManager;
import com.castingmob.ui.activity.NewHomeScreenActivity;
import com.castingmob.ui.activity.Router;
import com.castingmob.ui.adapter.casting.AppliedCastingAdapter;
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
 * Created by nishant on 26/09/16.
 */

public class AppliedCasting extends BaseFragment {

    private static AppliedCasting appliedCasting;

    public static AppliedCasting createFragment() {
        if (appliedCasting == null) {
            appliedCasting = new AppliedCasting();
        }
        return appliedCasting;
    }

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.search_casting)
    CastingRecycleView mSearchCastingList;

    AppliedCastingAdapter searchCastingAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_applied_casting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        searchCastingAdapter = AppliedCastingAdapter.getInstance();
        mSearchCastingList.setAdapter(searchCastingAdapter);
        mSearchCastingList.setItemClickListener(onItemClickListener);
        getCasting();
    }

    @OnClick(R.id.back)
    public void back(){
        EventBus.getDefault().post(new EventManager(CastingContainerFragment.CASTING_ACTIVITY.MY_CASTING));
    }

    public void getCasting(){
        CastingManager.getInstance().getAppliedCasting(Profile.getInstance().getToken(), new Callback<List<CastingItem>>() {
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
            ImageView sendMessage = (ImageView) view.findViewById(R.id.sendMessage);
            sendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   final Conversation conversation =  searchCastingAdapter.getCastByPosition(position).getConversation();
                    MessageApiManager.getInstance().sendMessage(Profile.getInstance().getToken(),
                            conversation.getParticipants().get(1).getToken() ,conversation.getLastMessage(), new Callback<Message>() {
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
                                public void onResponse(Call<Message> call, Response<Message> response) {
                                    if (response.isSuccessful()) {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("comment", 1);
                                        bundle.putParcelable("chat", conversation);
                                        Router.startMessageActivity(getActivity(), bundle);
                                    } else {
                                        ((NewHomeScreenActivity) getActivity()).logger.error(new Exception(response.raw() + ""));
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
                                public void onFailure(Call<Message> call, Throwable t) {
                                    Log.e("sendMessage", t.getMessage(), t);
                                }
                            });
                }
            });

        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };
}
