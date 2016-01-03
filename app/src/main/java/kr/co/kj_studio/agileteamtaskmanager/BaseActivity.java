package kr.co.kj_studio.agileteamtaskmanager;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by KJ_Studio on 2016-01-03.
 */
public class BaseActivity extends AppCompatActivity {


    public static TextView mTitleTextView;
    public static ImageButton rightBtn;
    public static ImageButton leftBtn;
    public static Button stateBtn;
    public static ImageButton searchBtn;
    public static ImageButton searchDetailBtn;
    public static ImageButton moreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomActionbar();
    }

    public void setupEvents() {

    }


    public void bindViews() {
        mTitleTextView = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.titleTxt);
        rightBtn = (ImageButton) getSupportActionBar().getCustomView().findViewById(R.id.okBtn);
        leftBtn = (ImageButton) getSupportActionBar().getCustomView().findViewById(R.id.toggleBtn);
        stateBtn = (Button) getSupportActionBar().getCustomView().findViewById(R.id.stateBtn);
        moreBtn = (ImageButton) getSupportActionBar().getCustomView().findViewById(R.id.moreBtn);
        searchBtn = (ImageButton) getSupportActionBar().getCustomView().findViewById(R.id.searchBtn);
        searchDetailBtn = (ImageButton) getSupportActionBar().getCustomView().findViewById(R.id.searchDetailBtn);


    }

    public void setCustomActionbar() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
    }

}
