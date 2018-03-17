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
import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.database.model.Conversation;
import com.castingmob.utils.DateTimeUtility;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {

    private static final ConversationAdapter feedAdapter = new ConversationAdapter();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy/HH/mm");

    ConversationAdapter(){}

    public static ConversationAdapter getInstance() {
        return feedAdapter;
    }

    private List<Conversation> conversationList;
    private Context context;

    public void setConversation(Context context,List<Conversation> conversationList){
        this.context = context;
        this.conversationList = conversationList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_conversation, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (conversationList != null && conversationList.size()>0) {

            Conversation conversation = conversationList.get(position);

            AccountManager.getInstance().getChatConversationProfilePic(conversation.getParticipants().get(0).getToken(), new Callback<ResponseBody>() {
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
                        if (response.body()!=null){
                            String profilePic = response.body().string();
                            Log.d("castingMob",profilePic);
                            if (response.isSuccessful()){
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
                        Log.e("castingMob",e.getMessage(),e);
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
                    Log.e("castingMob",t.getMessage(),t);
                }
            });

            holder.mSenderName.setText(conversation.getParticipants().get(0).getName());

            String messageText = String.format("%s : %s",conversation.getLastMessageSenderName(), conversation.getLastMessage());
            holder.mMessageText.setText(messageText);
            holder.mMessageTime.setText(DateTimeUtility.messageTimeStamp(conversation.getLastMessageTime()));
        }
    }

    @Override
    public int getItemCount() {
        return conversationList == null ? 0 : conversationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView mMessageTime;
        protected TextView mMessageText;
        protected TextView mSenderName;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.profile_pic);
            this.mMessageTime = (TextView) view.findViewById(R.id.messageTime);
            this.mMessageText = (TextView) view.findViewById(R.id.messageText);
            this.mSenderName = (TextView) view.findViewById(R.id.senderName);
        }
    }

    /**
     * Get the Conversation By Position
     * @param position
     * @return
     */
    public Conversation getConversationByPosition(int position){
        return conversationList.get(position);
    }
}
