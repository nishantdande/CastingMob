package com.castingmob.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.castingmob.Feed.model.Filter;
import com.castingmob.R;
import com.cocosw.bottomsheet.BottomSheet;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nishant on 09/03/16.
 */
public class NameFragment extends Fragment {

    @Bind(R.id.select_filter_type)
    TextView mSelectAccountType;

    @Bind(R.id.name)
    TextView mName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_name, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Filter.getInstance().setName(getName());
                }
                return false;
            }
        });
    }

    @OnClick(R.id.select_filter_type)
    public void onFilterTypeSelect(){
        new BottomSheet.Builder(getActivity()).title("Search for name of").sheet(R.menu.menu_search_type)
                .listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case R.id.any:
                        mSelectAccountType.setText(getString(R.string.account_selection_type_any));
                        Filter.getInstance().setUserType(getString(R.string.account_selection_type_any));
                        break;

                    case R.id.model:
                        mSelectAccountType.setText(getString(R.string.user_selection_type_model));
                        Filter.getInstance().setUserType(getString(R.string.user_selection_type_model));
                        break;

                    case R.id.agent:
                        mSelectAccountType.setText(getString(R.string.account_selection_type_agent));
                        Filter.getInstance().setUserType(getString(R.string.account_selection_type_agent));
                        break;

                    case R.id.client:
                        mSelectAccountType.setText(getString(R.string.account_selection_type_client));
                        Filter.getInstance().setUserType(getString(R.string.account_selection_type_client));
                        break;
                }
            }
        }).show();
    }

    public String getName() {
        return mName.getText().toString().trim();
    }
}
