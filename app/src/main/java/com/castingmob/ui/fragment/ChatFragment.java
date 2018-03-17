package com.castingmob.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.database.manager.ConversationManager;
import com.castingmob.database.manager.MessageManager;
import com.castingmob.database.model.Conversation;
import com.castingmob.database.model.Message;
import com.castingmob.message.MessageApiManager;
import com.castingmob.ui.activity.MessageActivity;
import com.castingmob.ui.adapter.ChatAdapter;
import com.castingmob.ui.view.CastingRecycleView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nishant on 12/03/16.
 */
public class ChatFragment extends BaseFragment{


    @Bind(R.id.chatList)
    CastingRecycleView mChatList;
    @Bind(R.id.chatField)
    EditText mTypeText;

    private ChatAdapter chatAdapter;
    private Conversation conversation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle!= null){
            conversation = bundle.getParcelable("chat");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_screen,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        chatAdapter = ChatAdapter.getInstance();
        mChatList.setAdapter(chatAdapter);
        mChatList.setItemClickListener(onItemClickListener);
        fetchMessage();
    }

    @OnClick(R.id.sendMessage)
    public void onSendClick(){

        if (TextUtils.isEmpty(getTypeText()))
            return;

        MessageApiManager.getInstance().sendMessage(Profile.getInstance().getToken(),
                conversation.getParticipants().get(0).getToken(), getTypeText(), new Callback<Message>() {
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
                        mTypeText.setText("");
                        if (response.isSuccessful()) {
                            MessageManager.getInstance().
                                    syncMessage(response.body(), new MessageManager.onComplete() {
                                        @Override
                                        public void status(boolean s) {

                                        }

                                        @Override
                                        public void onCompleteSync() {
                                            setChatList(String.valueOf(conversation.getConversationId()));
                                        }
                                    });
                        } else {
                            ((MessageActivity) getActivity()).logger.error(new Exception(response.raw() + ""));
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

    private void fetchMessage() {
        if (conversation !=  null){
            long lastMessageTime = ((MessageManager.getInstance()
                    .isMessageExistsByConversationId(String.valueOf(conversation.getConversationId())) == true) ?
                    conversation.getLastMessageTime() : 0);
            MessageApiManager.getInstance().getChatMessages(Profile.getInstance().getToken(),
                    conversation.getParticipants().get(0).getToken(), lastMessageTime, new Callback<List<Message>>() {
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
                        public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                            if (response.isSuccessful()) {
                                MessageManager.getInstance().
                                        syncMessage(response.body(), new MessageManager.onComplete() {
                                            @Override
                                            public void status(boolean s) {

                                            }

                                            @Override
                                            public void onCompleteSync() {
                                                setChatList(String.valueOf(conversation.getConversationId()));
                                            }
                                        });
                            } else {
                                ((MessageActivity) getActivity()).logger.error(new Exception(response.raw() + ""));
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
                        public void onFailure(Call<List<Message>> call, Throwable t) {
                            ((MessageActivity) getActivity()).logger.error(t);
                        }
                    });

        }
    }

    CastingRecycleView.OnItemClickListener onItemClickListener
            = new CastingRecycleView.OnItemClickListener() {
        @Override
        public void onItemClick(View view, final int position) {

        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };

    private void setChatList(String conversationId){
        chatAdapter.setMessage(getActivity(), MessageManager.getInstance().getMessagesByConversationId(conversationId));
    }

    public String getTypeText() {
        return mTypeText.getText().toString().trim();
    }
}
