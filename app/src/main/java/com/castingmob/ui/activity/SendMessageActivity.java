package com.castingmob.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.database.manager.MessageManager;
import com.castingmob.database.model.Message;
import com.castingmob.message.MessageApiManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nishant on 10/03/16.
 */
public class SendMessageActivity extends CastingMobActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.message)
    EditText mMessage;

    @Bind(R.id.title)
    TextView mTitle;

    String participantToken;
    String castingItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            participantToken = bundle.getString("participantToken");
            castingItem = bundle.getString("participant");
        }

        if (castingItem!=null)
            mTitle.setText(castingItem);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.send_message)
    public void sendMessage(){
        if (TextUtils.isEmpty(getMessage()))
            return;

        if (participantToken == null)
            return;

        MessageApiManager.getInstance().sendMessage(Profile.getInstance().getToken(),
                participantToken, getMessage(), new Callback<Message>() {
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
                        mMessage.setText("");
                        if (response.isSuccessful()) {
                            MessageManager.getInstance().
                                    syncMessage(response.body(), new MessageManager.onComplete() {
                                        @Override
                                        public void status(boolean s) {

                                        }

                                        @Override
                                        public void onCompleteSync() {

                                        }
                                    });
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
                    public void onFailure(Call<Message> call, Throwable t) {
                        logger.error(t);
                    }
                });

    }

    public String  getMessage() {
        return mMessage.getText().toString().trim();
    }
}
