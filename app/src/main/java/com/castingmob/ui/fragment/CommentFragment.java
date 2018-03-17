package com.castingmob.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.castingmob.Feed.FeedManager;
import com.castingmob.Feed.model.Comment;
import com.castingmob.Feed.model.FeedItem;
import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.ui.activity.MessageActivity;
import com.castingmob.ui.adapter.CommentAdapter;
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
public class CommentFragment extends BaseFragment{


    @Bind(R.id.chatList)
    CastingRecycleView mChatList;
    @Bind(R.id.chatField)
    EditText mTypeText;

    private CommentAdapter commentAdapter;
    private FeedItem feedItem;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle!= null){
            feedItem = bundle.getParcelable("feedItem");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment_screen,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        commentAdapter = CommentAdapter.getInstance();
        mChatList.setAdapter(commentAdapter);
        mChatList.setItemClickListener(onItemClickListener);
        fetchComments();
    }

    @OnClick(R.id.sendMessage)
    public void onSendClick(){

        if (TextUtils.isEmpty(getTypeText()))
            return;

        if (feedItem!= null){
            FeedManager.getInstance().commentOnPhoto(Profile.getInstance().getToken(),
                    feedItem.getPhotoId(), getTypeText(), new Callback<Comment>() {
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
                        public void onResponse(Call<Comment> call, Response<Comment> response) {
                            mTypeText.setText("");
                            if (response.isSuccessful()) {
                                // refresh ui
                                commentAdapter.updateCommentList(response.body());
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
                        public void onFailure(Call<Comment> call, Throwable t) {
                            ((MessageActivity) getActivity()).logger.error(t);
                        }
                    });
        }
    }

    private void fetchComments() {
        if (feedItem !=  null){

            FeedManager.getInstance().getAllComments(Profile.getInstance().getToken(), feedItem.getPhotoId(), new Callback<List<Comment>>() {
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
                public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                    if (response.isSuccessful()) {
                        // refresh Ui
                        setCommentList(response.body());
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
                public void onFailure(Call<List<Comment>> call, Throwable t) {
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

    private void setCommentList(List<Comment> commentList){
        commentAdapter.setComments(getActivity(), commentList);
    }

    public String getTypeText() {
        return mTypeText.getText().toString().trim();
    }
}
