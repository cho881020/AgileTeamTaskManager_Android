package kr.co.kj_studio.agileteamtaskmanager.util;

import android.content.Context;
import android.content.SharedPreferences;

import kr.co.kj_studio.agileteamtaskmanager.datas.UserData;


/**
 * Created by KJ_Studio on 2015-11-16.
 */
public class ContextUtil {
    private static final String PREFERENCE_NAME = "RepotPreferences";
    private static final String LOGGED_IN = "isLogin";
    private static final String USER_ID = "User_ID";
    private static final String USER_UID = "USER_UID";
    private static final String USER_NAME = "User_Name";
    private static final String USER_EMAIL = "USER_EMAIL";
    private static final String USER_PHONE_NUM = "USER_PHONE_NUM";
    private static final String USER_COMPANY_NAME = "USER_COMPANY_NAME";
    private static final String USER_DEPART_NAME = "USER_DEPART_NAME";
    private static final String USER_PROFILE_PHOTO = "USER_PROFILE_PHOTO";
    private static final String IS_CAMERA_INITIAL = "IS_CAMERA_INITIAL";



    public static int getUSER_ID(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);

        return prefs.getInt(USER_ID, -1);
    }

    public static void setUSER_ID(Context context, int usr_id) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        //DateTime now = new DateTime(new Date());
        prefs.edit().putInt(USER_ID, usr_id).commit();
    }


    public static void setUserData(Context context, UserData userData) {

        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        //DateTime now = new DateTime(new Date());
        prefs.edit().putString(USER_UID, userData.uid).commit();
        prefs.edit().putString(USER_NAME, userData.name).commit();
        prefs.edit().putInt(USER_ID, userData.id).commit();

    }
//
//
//    public static UserData getUserData(Context context) {
//
//        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME,
//                Context.MODE_PRIVATE);
//        //DateTime now = new DateTime(new Date());
//
//        UserData userData = new UserData();
//        userData.email = prefs.getString(USER_EMAIL, "");
//        userData.name = prefs.getString(USER_NAME, "");
//        userData.phoneNum = prefs.getString(USER_PHONE_NUM, "");
//        userData.companyName = prefs.getString(USER_COMPANY_NAME, "");
//        userData.departmentName = prefs.getString(USER_DEPART_NAME, "");
//
//        return userData;
//    }


    public static boolean isUserLoggedin(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);

        return prefs.getBoolean(LOGGED_IN, false);
    }

    public static void setLoggedIn(Context context, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);

        prefs.edit().putBoolean(LOGGED_IN, value).commit();

    }

}
