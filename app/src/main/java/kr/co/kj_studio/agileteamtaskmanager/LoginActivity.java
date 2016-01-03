package kr.co.kj_studio.agileteamtaskmanager;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Arrays;

import kr.co.kj_studio.agileteamtaskmanager.datas.UserData;
import kr.co.kj_studio.agileteamtaskmanager.util.ContextUtil;
import kr.co.kj_studio.agileteamtaskmanager.util.ServerUtil;

public class LoginActivity extends AppCompatActivity {

    Button facebookLoginBtn;
    CallbackManager callbackManager;


    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.d("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(LoginActivity.this);

        getAppKeyHash();


        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d("CHO", "fbLogin Success");

                Profile loginProfile = Profile.getCurrentProfile();
//                String pictureURI = "https://graph.facebook.com/" + loginProfile.getId() + "/picture?type=large";

                Log.d("login get token", loginResult.getAccessToken().getToken());

                ServerUtil.ios_login(LoginActivity.this, loginProfile.getId(), new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {

                        try {
                            JSONObject userProfile = json.getJSONObject("userProfile");
                            UserData userData = UserData.getUserDataFromJson(userProfile);
                            ContextUtil.setUserData(LoginActivity.this, userData);
                            Log.d("CHO", "userProfile = " + userData.uid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent mIntent = new Intent(LoginActivity.this, SelectTeamActivity.class);
                        startActivity(mIntent);
                        finish();
                    }
                });

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });

//        if (ContextUtil.isUserLoggedin(LoginActivity.this)) {

//        }


        bindViews();
        setupEvents();
    }

    private void bindViews() {

        facebookLoginBtn = (Button) findViewById(R.id.facebookLoginBtn);
    }

    void setupEvents() {
        facebookLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions = {"public_profile", "user_friends", "email"};
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList(permissions));
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

}
