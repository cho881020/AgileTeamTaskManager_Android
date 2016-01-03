package kr.co.kj_studio.agileteamtaskmanager.datas;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import bolts.Task;

/**
 * Created by KJ_Studio on 2016-01-03.
 */
public class TaskData implements Serializable {

    public int id;
    public String uid;
    public String username;
    public String status;
    public String user_content;
    public int user_id;
    public int team_id;

    public static TaskData getTaskDataFromJson(JSONObject jsonObject) {
        TaskData taskData = new TaskData();
        try {
            taskData.id = jsonObject.getInt("id");
            taskData.uid = jsonObject.getString("uid");
            taskData.username = jsonObject.getString("username");
            taskData.status = jsonObject.getString("status");
            taskData.user_content = jsonObject.getString("user_content");
            taskData.user_id = jsonObject.getInt("user_id");
            taskData.team_id = jsonObject.getInt("team_id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return taskData;
    }

}
