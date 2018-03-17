package com.castingmob.ui.adapter;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 26/01/16
 ==============================================
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.castingmob.CastingMob;
import com.castingmob.Feed.model.Comment;
import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.utils.DateTimeUtility;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private static final CommentAdapter feedAdapter = new CommentAdapter();

    CommentAdapter(){}

    public static CommentAdapter getInstance() {
        return feedAdapter;
    }

    private List<Comment> commentList;
    private Context context;

    public void setComments(Context context,List<Comment> messageList){
        this.context = context;
        if (this.commentList != null && this.commentList.size()>0){
            this.commentList.clear();
        }
        this.commentList = messageList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (commentList != null && commentList.size()>0) {

            Comment comment = commentList.get(position);

            AccountManager.getInstance().getChatConversationProfilePic(comment.getCommentorToken(), new Callback<ResponseBody>() {
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
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.body() != null){
                            String profilePic = response.body().string();
                            Log.d("castingMob", profilePic);
                            if (response.isSuccessful()) {
                                Glide.with(CastingMob.getInstance().getContext())
                                        .load(profilePic)
                                        .asBitmap()
                                        .centerCrop()
                                        .placeholder(R.drawable.ic_logo_final_black)
                                        .into(new BitmapImageViewTarget(holder.imageView) {
                                            @Override
                                            protected void setResource(Bitmap resource) {
                                                super.setResource(resource);
                                                RoundedBitmapDrawable circularBitmapDrawable =
                                                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                                circularBitmapDrawable.setCircular(true);
                                                holder.imageView.setImageDrawable(circularBitmapDrawable);
                                            }
                                        });
                            }
                        }
                    } catch (IOException e) {
                        Log.e("castingMob", e.getMessage(), e);
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
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("castingMob", t.getMessage(), t);
                }
            });


            String title = String.format("%s @ %s", comment.getCommentorName(),
                    DateTimeUtility.messageTimeStamp(comment.getCommentTime()));
            holder.mTitle.setText(title);
            holder.mMessageText.setText(comment.getText());
        }
    }

    @Override
    public int getItemCount() {
        return commentList == null ? 0 : commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView mTitle;
        protected TextView mMessageText;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.profile_pic);
            this.mTitle = (TextView) view.findViewById(R.id.title);
            this.mMessageText = (TextView) view.findViewById(R.id.messageText);
        }
    }

    /**
     * Get the Comment By Position
     * @param position
     * @return
     */
    public Comment getCommentByPosition(int position){
        return commentList.get(position);
    }

    public void updateCommentList(Comment comment){
        commentList.add(comment);
        notifyDataSetChanged();
    }
}
