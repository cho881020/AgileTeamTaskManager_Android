package kr.co.kj_studio.agileteamtaskmanager.datas;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by KJ_Studio on 2016-01-03.
 */
public class UserData implements Serializable {

    public int id = -1;
    public String uid = "";
    public String name = "";

    public static UserData getUserDataFromJson(JSONObject jsonObject) {
        UserData userData = new UserData();
        try {
            userData.id = jsonObject.getInt("id");
            userData.uid = jsonObject.getString("uid");
            userData.name = jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userData;
    }

}
