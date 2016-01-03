package kr.co.kj_studio.agileteamtaskmanager.datas;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by KJ_Studio on 2016-01-03.
 */
public class ProjectData implements Serializable {

    public String team_title="";
    public String teampassword="";
    public int id=-1;

    public static ProjectData getProjectDataFromJson(JSONObject jsonObject) {
        ProjectData projectData = new ProjectData();

        try {
            projectData.id = jsonObject.getInt("id");
            projectData.team_title = jsonObject.getString("team_title");
            projectData.teampassword = jsonObject.getString("teampassword");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return projectData;
    }

}
