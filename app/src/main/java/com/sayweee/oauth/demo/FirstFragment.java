package com.sayweee.oauth.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sayweee.logger.Logger;
import com.sayweee.oauth.OAuth;
import com.sayweee.oauth.iml.OAuthBean;
import com.sayweee.oauth.iml.OAuthCallback;
import com.sayweee.oauth.iml.WeeeOAuth;

public class FirstFragment extends Fragment {

    TextView textview_first;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textview_first = view.findViewById(R.id.textview_first);
        String client_id = "567937503453";
        String client_secret = "Xm7Kn7Wr2LoAM2c7O9Tn1XLtuTs87i33";
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OAuth.shared().setClientId(client_id).isTest(true)
                        .setOAuthCallback(new OAuthCallback() {
                            @Override
                            public void onResult(boolean success, OAuthBean data) {
                                Logger.json(data);
                            }
                        })
                        .doOAuthWeee(getActivity(), false, client_secret);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WeeeOAuth.shared().removeOAuthCallback();
    }
}