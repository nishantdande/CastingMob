package com.castingmob.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.castingmob.R;
import com.castingmob.database.model.Conversation;
import com.castingmob.ui.fragment.ChatFragment;
import com.castingmob.ui.fragment.CommentFragment;
import com.castingmob.ui.fragment.ConversationFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nishant on 12/03/16.
 */
public class MessageActivity extends CastingMobActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.alert)
    ImageView mAlert;

    @Bind(R.id.title)
    TextView mTitle;

    @Bind(R.id.back)
    ImageView back;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        back.setVisibility(View.GONE);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFragmentManager = getSupportFragmentManager();
        Bundle bundle = getIntent().getExtras();
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

    /**
     * @param chat
     * @param bundle  the information bundle.
     */
    public void setFragment(CHAT chat, Bundle bundle) {
        switch (chat) {
            case CONVERSATION: {
                mAlert.setVisibility(View.GONE);
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
                        mTitle.setText(conversation.getParticipants().get(0).getName());
                    }
                }
                addFragmentWithArgument(new ChatFragment(),
                        ChatFragment.class.getSimpleName(), bundle);
            }
            break;

            case COMMENT: {
                mAlert.setVisibility(View.GONE);
                mTitle.setText("Comments");
                addFragmentWithArgument(new CommentFragment(),
                        CommentFragment.class.getSimpleName(), bundle);
            }
            break;
        }
    }

    private void destroyFragment(String tagName){

        Fragment fragment = mFragmentManager.findFragmentByTag(tagName);
        if (fragment!=null)
            removeFragment(fragment);
    }

    // add the fragment with bundle as argument.
    private void addFragmentWithArgument(Fragment fragment, String tag, Bundle bundle) {
        if (bundle != null)
            fragment.setArguments(bundle);

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        mFragmentTransaction.add(R.id.container, fragment, tag);
        mFragmentTransaction.commitAllowingStateLoss();
    }

    //remove fragment from stack.
    private void removeFragment(Fragment fragment)
    {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_left);
        mFragmentTransaction.remove(fragment);
        mFragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = mFragmentManager.findFragmentById(R.id.container);
        if (fragment != null){
            if ((fragment instanceof ConversationFragment)){
                finish();
            }
        }
    }
}
