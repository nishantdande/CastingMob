package com.castingmob.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.database.manager.ConversationManager;
import com.castingmob.database.model.Conversation;
import com.castingmob.event.EventManager;
import com.castingmob.message.MessageApiManager;
import com.castingmob.ui.activity.MessageActivity;
import com.castingmob.ui.activity.NewHomeScreenActivity;
import com.castingmob.ui.adapter.ConversationAdapter;
import com.castingmob.ui.fragment.home.MessageFragment;
import com.castingmob.ui.view.CastingRecycleView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nishant on 12/03/16.
 */
public class ConversationFragment extends BaseFragment{

    @Bind(R.id.conversationList)
    CastingRecycleView mConversationList;

    ConversationAdapter conversationAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conversation_screen,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        conversationAdapter = ConversationAdapter.getInstance();
        mConversationList.setAdapter(conversationAdapter);
        mConversationList.setItemClickListener(onItemClickListener);
        fetchConversation();
    }

    private void fetchConversation() {
        MessageApiManager.getInstance().getConversation(Profile.getInstance().getToken(), new Callback<List<Conversation>>() {
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
            public void onResponse(Call<List<Conversation>> call, Response<List<Conversation>> response) {
                if (response.isSuccessful()) {
                    ConversationManager.getInstance().
                            syncConversation(response.body(), new ConversationManager.onComplete() {
                                @Override
                                public void status(boolean s) {

                                }

                                @Override
                                public void onCompleteSync() {
                                    setConversationList();
                                }
                            });
                } else {
                    ((MessageActivity)getActivity()).logger.error(new Exception(response.raw() + ""));
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
            public void onFailure(Call<List<Conversation>> call, Throwable t) {
                ((NewHomeScreenActivity)getActivity()).logger.error(t);
            }
        });
    }

    CastingRecycleView.OnItemClickListener onItemClickListener
            = new CastingRecycleView.OnItemClickListener() {
        @Override
        public void onItemClick(View view, final int position) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("chat",conversationAdapter.getConversationByPosition(position));
            EventBus.getDefault().post(new EventManager(MessageFragment.CHAT.CHAT, bundle));
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };

    private void setConversationList(){
        conversationAdapter.setConversation(getActivity(), ConversationManager.getInstance().getAllConversation());
    }
}
