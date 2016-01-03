package kr.co.kj_studio.agileteamtaskmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.kj_studio.agileteamtaskmanager.R;
import kr.co.kj_studio.agileteamtaskmanager.datas.ProjectData;

/**
 * Created by KJ_Studio on 2016-01-03.
 */
public class ProjectAdapter extends ArrayAdapter<ProjectData>{


    Context mContext;
    ArrayList<ProjectData> mList;
    LayoutInflater inf;

    public ProjectAdapter(Context context, ArrayList<ProjectData> list) {
        super(context, R.layout.project_list_item, R.id.projectTitle, list);

        mContext = context;
        mList = list;
        inf = LayoutInflater.from(mContext);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row== null) {
            row = inf.inflate(R.layout.project_list_item, null);
        }

        ProjectData projectData = mList.get(position);
        TextView projectTitle = (TextView) row.findViewById(R.id.projectTitle);

        projectTitle.setText(projectData.team_title);

        return row;
    }



}
