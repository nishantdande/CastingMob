package com.castingmob.ui.fragment.home;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 07/08/16
 ==============================================
 */

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.account.model.Profile;
import com.castingmob.database.model.Conversation;
import com.castingmob.event.EventManager;
import com.castingmob.ui.activity.NewHomeScreenActivity;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.ui.fragment.BaseFragment;
import com.castingmob.ui.fragment.ChatFragment;
import com.castingmob.ui.fragment.CommentFragment;
import com.castingmob.ui.fragment.ConversationFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageFragment extends BaseFragment {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.alert)
    ImageView mAlert;

    @Bind(R.id.back)
    ImageView mBack;

    @Bind(R.id.title)
    TextView mTitle;

    public enum CHAT {
        CONVERSATION(0),
        CHAT(1),
        COMMENT(2);

        int id;
        CHAT(int id) {
            this.id = id;
        }

        public static CHAT fromValue(int code){
            for(CHAT e : CHAT.values()){
                if(code == e.id)
                    return e;
            }
            return null;
        }
    }

    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;
    private NewHomeScreenActivity newHomeScreenActivity;
    private Bundle bundle;
    private Bundle savedInstanceState;

    @Subscribe
    public void onEvent(EventManager event){
        if (event != null)
            setFragment(event.getChatActivity(), event.getBundle());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(MessageFragment.this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_message, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        this.savedInstanceState = savedInstanceState;

//        this.newHomeScreenActivity.setSupportActionBar(mToolbar);

        mFragmentManager = getChildFragmentManager();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.newHomeScreenActivity = (NewHomeScreenActivity) context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            if (savedInstanceState == null && bundle == null) {
                setFragment(CHAT.CONVERSATION, null);
            }else{
                if (bundle!=null) {
                    int value = bundle.getInt("comment", 0);
                    CHAT chat = CHAT.fromValue(value);
                    if (chat!=null)
                        setFragment(chat, bundle);
                }

            }
        }
    }

    /**
     * @param chat
     * @param bundle  the information bundle.
     */
    public void setFragment(CHAT chat, Bundle bundle) {
        if (chat != null){
            switch (chat) {
                case CONVERSATION: {
                    mAlert.setVisibility(View.GONE);
                    mBack.setVisibility(View.GONE);
                    mTitle.setText(R.string.message_toolbar_title);
                    addFragmentWithArgument(new ConversationFragment(),
                            ConversationFragment.class.getSimpleName(), bundle);
                }
                break;

                case CHAT: {
                    if (bundle != null) {
                        Conversation conversation = bundle.getParcelable("chat");
                        if (conversation != null){
                            mAlert.setVisibility(View.VISIBLE);
                            mBack.setVisibility(View.VISIBLE);
                            mTitle.setText(conversation.getParticipants().get(0).getName());
                        }
                    }
                    addFragmentWithArgument(new ChatFragment(),
                            ChatFragment.class.getSimpleName(), bundle);
                }
                break;

                case COMMENT: {
                    mAlert.setVisibility(View.GONE);
                    mBack.setVisibility(View.GONE);
                    mTitle.setText("Comments");
                    addFragmentWithArgument(new CommentFragment(),
                            CommentFragment.class.getSimpleName(), bundle);
                }
                break;
            }

        }
    }

    @OnClick(R.id.back)
    public void back(){
        EventBus.getDefault().post(new EventManager(CHAT.CONVERSATION, null));
    }

    @OnClick(R.id.alert)
    public void alert(){
        DialogFactory.createAlertDialogWithButtons(getActivity(), getString(R.string.block_content),
                getString(R.string.block_content_message),
                getString(R.string.block_content_positive_button),
                getString(R.string.block_content_negative_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (bundle != null) {
                    Conversation conversation = bundle.getParcelable("chat");
                    if (conversation != null){
                        AccountManager.getInstance().blockUser(Profile.getInstance().getToken(),
                                conversation.getParticipants().get(0).getToken(), new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (!response.isSuccessful()){
                                            logger.error(new Exception(response.message()));
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        logger.error(t);
                                    }
                                });
                    }
                }
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                dialogInterface.cancel();
            }
        }).show();
    }

    // add the fragment with bundle as argument.
    private void addFragmentWithArgument(Fragment fragment, String tag, Bundle bundle) {
        if (bundle != null)
            fragment.setArguments(bundle);

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container, fragment, tag);
        mFragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(MessageFragment.this);
    }
}
