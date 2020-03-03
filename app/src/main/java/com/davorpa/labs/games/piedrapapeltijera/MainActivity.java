package com.davorpa.labs.games.piedrapapeltijera;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    static final String EXTRA_USERNAME =  BuildConfig.APPLICATION_ID + ".USERNAME";


    @BindView(R.id.txt_username) EditText txt_username;
    Unbinder unbinder;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        // Capture the layout's components handled by ButterKnife annotations
        //
        unbinder = ButterKnife.bind(this);

        //Init view with values provided by parent intent
        final String username = getIntent().getStringExtra(MainActivity.EXTRA_USERNAME);
        if (username != null) {
            txt_username.setText(username);
            txt_username.selectAll();
            txt_username.requestFocus();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
    }


    /**
     * Event callback executed when Play button is clicked
     * @param view the button instance that fires the event
     */
    @OnClick({R.id.btn_play, R.id.lbl_play})
    public void onPlayClick(final View view) {
        // TODO: Find user in last players to set current user in game
        // validate username
        final String username = txt_username.getText().toString();
        if (username == null || username.trim().isEmpty()) {
            txt_username.setError(getString(R.string.err_required));
            txt_username.requestFocus();
            //Open device keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(txt_username, InputMethodManager.SHOW_IMPLICIT);
            return;
        }

        // Navigate to other activity
        final Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra(MainActivity.EXTRA_USERNAME, username);
        startActivity(intent);
    }
}
