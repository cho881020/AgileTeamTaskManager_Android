package kr.co.kj_studio.agileteamtaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.kj_studio.agileteamtaskmanager.adapter.ProjectAdapter;
import kr.co.kj_studio.agileteamtaskmanager.datas.ProjectData;
import kr.co.kj_studio.agileteamtaskmanager.util.ContextUtil;
import kr.co.kj_studio.agileteamtaskmanager.util.ServerUtil;

public class SelectTeamActivity extends BaseActivity {

    ArrayList<ProjectData> myTeam = new ArrayList<>();
    ArrayList<ProjectData> myBelongTeam = new ArrayList<>();
    ArrayList<ProjectData> myTotalTeam = new ArrayList<>();
    private android.widget.ListView myTeamListView;

    ProjectAdapter totalAdapter;

    public final static String ITEM_TITLE = "title";
    public final static String ITEM_CAPTION = "caption";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_team);
        bindViews();

        mTitleTextView.setText("프로젝트 목록");

        this.myTeamListView = (ListView) findViewById(R.id.myTeamListView);
        totalAdapter  = new ProjectAdapter(SelectTeamActivity.this, myTotalTeam);
        myTeamListView.setAdapter(totalAdapter);

        myTeamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(SelectTeamActivity.this, MainActivity.class);
                intent.putExtra("projectData", myTotalTeam.get(position));
                startActivity(intent);
            }
        });

        ServerUtil.select_team(SelectTeamActivity.this, ContextUtil.getUSER_ID(SelectTeamActivity.this) + "", new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {

                myTeam.clear();
                myBelongTeam.clear();
                myTotalTeam.clear();
                try {
                    JSONArray jangteam = json.getJSONArray("jangteam");
                    for (int i = 0; i < jangteam.length(); i++) {
                        myTeam.add(ProjectData.getProjectDataFromJson(jangteam.getJSONObject(i)));
                        myTotalTeam.add(ProjectData.getProjectDataFromJson(jangteam.getJSONObject(i)));
                    }

                    JSONArray sokteam = json.getJSONArray("sokteam");

                    for (int i = 0; i < sokteam.length(); i++) {
                        myBelongTeam.add(ProjectData.getProjectDataFromJson(sokteam.getJSONObject(i)));
                        myTotalTeam.add(ProjectData.getProjectDataFromJson(sokteam.getJSONObject(i)));

                    }

                    displayListView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void displayListView() {

//        totalAdapter.clear();
//        totalAdapter.addAll(myTotalTeam);
//        totalAdapter.notifyDataSetChanged();


        totalAdapter  = new ProjectAdapter(SelectTeamActivity.this, myTotalTeam);
        myTeamListView.setAdapter(totalAdapter);
    }
}
