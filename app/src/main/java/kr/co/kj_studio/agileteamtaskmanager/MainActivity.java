package kr.co.kj_studio.agileteamtaskmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.kj_studio.agileteamtaskmanager.adapter.ProjectAdapter;
import kr.co.kj_studio.agileteamtaskmanager.datas.ProjectData;
import kr.co.kj_studio.agileteamtaskmanager.datas.TaskData;
import kr.co.kj_studio.agileteamtaskmanager.util.ContextUtil;
import kr.co.kj_studio.agileteamtaskmanager.util.ServerUtil;
import kr.co.kj_studio.agileteamtaskmanager.view.DoingListItemView;
import kr.co.kj_studio.agileteamtaskmanager.view.DoneListItemView;
import kr.co.kj_studio.agileteamtaskmanager.view.PinnedSectionListView;
import kr.co.kj_studio.agileteamtaskmanager.view.TodoListItemView;

public class MainActivity extends AppCompatActivity {


    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).build();

    public ImageButton toggleBtn;
    public TextView mTitleTextView;
    private PinnedSectionListView todoListView;
    private PinnedSectionListView doingListView;
    private PinnedSectionListView doneListView;
    private android.support.v4.view.ViewPager myViewPager;
    private android.widget.TextView todoCount;
    private android.widget.TextView todoIndicator;
    private android.widget.FrameLayout todoLayout;
    private android.widget.TextView doingCount;
    private android.widget.TextView doingIndicator;
    private android.widget.FrameLayout doingLayout;
    private android.widget.TextView doneCount;
    private android.widget.TextView doneIndicator;
    private android.widget.FrameLayout doneLayout;

    ArrayList<TaskData> todoArrayList = new ArrayList<>();
    ArrayList<TaskData> doingArrayList = new ArrayList<>();
    ArrayList<TaskData> doneArrayList = new ArrayList<>();

    ProjectAdapter totalAdapter;

    ProjectData mProjectData;
    private Toolbar myawesometoolbar;
    private android.widget.ImageButton addTaskBtn;
    private ListView myTeamListView;
    private android.widget.LinearLayout drawLayout;


    ArrayList<ProjectData> myTeam = new ArrayList<>();
    ArrayList<ProjectData> myBelongTeam = new ArrayList<>();
    ArrayList<ProjectData> myTotalTeam = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(MainActivity.this).build();
        ImageLoader.getInstance().init(config);
        setCustomActionbar();
        bindViews();
        setupEvents();
        setValues();


    }

    @Override
    protected void onResume() {
        super.onResume();

        getContentsFromServer();
    }

    void getContentsFromServer() {
        ServerUtil.content(MainActivity.this, mProjectData.id + "", new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {

                todoArrayList.clear();
                doingArrayList.clear();
                doneArrayList.clear();
                try {
                    JSONArray content = json.getJSONArray("content");
                    for (int i = 0; i < content.length(); i++) {
                        TaskData taskData = TaskData.getTaskDataFromJson(content.getJSONObject(i));
                        if (taskData.status.equals("to_do")) {
                            todoArrayList.add(taskData);
                        } else if (taskData.status.equals("doing")) {
                            doingArrayList.add(taskData);
                        } else {
                            doneArrayList.add(taskData);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("json", json.toString());

                displayListViews();
            }
        });

        ServerUtil.select_team(MainActivity.this, ContextUtil.getUSER_ID(MainActivity.this) + "", new ServerUtil.JsonResponseHandler() {
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


        totalAdapter  = new ProjectAdapter(MainActivity.this, myTotalTeam);
        myTeamListView.setAdapter(totalAdapter);
    }

    public void setCustomActionbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
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
    private void displayListViews() {

        todoCount.setText(todoArrayList.size() + "");
        doingCount.setText(doingArrayList.size() + "");
        doneCount.setText(doneArrayList.size() + "");

        todoListView.setAdapter(new TodoAdapter(MainActivity.this, todoArrayList));
        doingListView.setAdapter(new DoingAdapter(MainActivity.this, doingArrayList));
        doneListView.setAdapter(new DoneAdapter(MainActivity.this, doneArrayList));
    }

    void setIndicators(int index) {
        if (index == 0) {
            todoIndicator.setVisibility(View.VISIBLE);
            doingIndicator.setVisibility(View.GONE);
            doneIndicator.setVisibility(View.GONE);
        } else if (index == 1) {

            todoIndicator.setVisibility(View.GONE);
            doingIndicator.setVisibility(View.VISIBLE);
            doneIndicator.setVisibility(View.GONE);
        } else if (index == 2) {

            todoIndicator.setVisibility(View.GONE);
            doingIndicator.setVisibility(View.GONE);
            doneIndicator.setVisibility(View.VISIBLE);
        }
    }
    public void setupEvents() {

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, EditTaskActivity.class);
                mIntent.putExtra("teamId", mProjectData.id);
                startActivity(mIntent);
            }
        });

        todoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewPager.setCurrentItem(0);
            }
        });

        doingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewPager.setCurrentItem(1);
            }
        });

        doneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewPager.setCurrentItem(2);
            }
        });

        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                setIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setValues() {

        mProjectData = (ProjectData) getIntent().getSerializableExtra("projectData");

        mTitleTextView.setText(mProjectData.team_title);


        WizardPagerAdapter adapter = new WizardPagerAdapter();
        myViewPager.setAdapter(adapter);
        myViewPager.setOffscreenPageLimit(3);
        myViewPager.setCurrentItem(0);
    }


    public void bindViews() {

        toggleBtn = (ImageButton) getSupportActionBar().getCustomView().findViewById(R.id.toggleBtn);
        mTitleTextView = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.titleTxt);
        this.drawLayout = (LinearLayout) findViewById(R.id.drawLayout);
        this.myTeamListView = (ListView) findViewById(R.id.myTeamListView);
        this.addTaskBtn = (ImageButton) findViewById(R.id.addTaskBtn);
        this.myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        this.doneListView = (PinnedSectionListView) findViewById(R.id.doneListView);
        this.doingListView = (PinnedSectionListView) findViewById(R.id.doingListView);
        this.todoListView = (PinnedSectionListView) findViewById(R.id.todoListView);
        this.doneLayout = (FrameLayout) findViewById(R.id.doneLayout);
        this.doneIndicator = (TextView) findViewById(R.id.doneIndicator);
        this.doneCount = (TextView) findViewById(R.id.doneCount);
        this.doingLayout = (FrameLayout) findViewById(R.id.doingLayout);
        this.doingIndicator = (TextView) findViewById(R.id.doingIndicator);
        this.doingCount = (TextView) findViewById(R.id.doingCount);
        this.todoLayout = (FrameLayout) findViewById(R.id.todoLayout);
        this.todoIndicator = (TextView) findViewById(R.id.todoIndicator);
        this.todoCount = (TextView) findViewById(R.id.todoCount);
        this.myawesometoolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
    }

    class WizardPagerAdapter extends PagerAdapter {

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.todoListView;
                    break;
                case 1:
                    resId = R.id.doingListView;
                    break;
                case 2:
                    resId = R.id.doneListView;
                    break;
            }
            return findViewById(resId);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == ((View) arg1);
        }
    }

    public class TodoAdapter extends ArrayAdapter<TaskData> {
        Context mContext;
        ArrayList<TaskData> mList;


        public TodoAdapter(Context context, ArrayList<TaskData> list) {
            super(context, R.layout.doing_list_item, R.id.contentTxt, list);

            mContext = context;
            mList = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                row = new TodoListItemView(mContext);
            }

            final TaskData taskData = mList.get(position);

            TodoListItemView mItem = (TodoListItemView) row;
            mItem.contentTxt.setText(taskData.user_content);


            String imageURL = "https://graph.facebook.com/" + taskData.uid + "/picture";
            ImageLoader.getInstance().displayImage(imageURL, mItem.profileImage, options);

            mItem.userNameTxt.setText(taskData.username);

            if (ContextUtil.getUSER_ID(MainActivity.this) != taskData.user_id) {
                mItem.myViewPager.setPagingEnabled(false);
            } else {
                mItem.myViewPager.setPagingEnabled(true);
            }

            mItem.myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0) {
                        ServerUtil.to_left(MainActivity.this, mProjectData.id + "", taskData.user_content, taskData.id + "", new ServerUtil.JsonResponseHandler() {
                            @Override
                            public void onResponse(JSONObject json) {
                                todoArrayList.clear();
                                doingArrayList.clear();
                                doneArrayList.clear();
                                try {
                                    JSONArray content = json.getJSONArray("content");
                                    for (int i = 0; i < content.length(); i++) {
                                        TaskData taskData = TaskData.getTaskDataFromJson(content.getJSONObject(i));
                                        if (taskData.status.equals("to_do")) {
                                            todoArrayList.add(taskData);
                                        } else if (taskData.status.equals("doing")) {
                                            doingArrayList.add(taskData);
                                        } else {
                                            doneArrayList.add(taskData);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.d("json", json.toString());

                                displayListViews();
                            }
                        });
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            return row;
        }

    }

    public class DoneAdapter extends ArrayAdapter<TaskData> {
        Context mContext;
        ArrayList<TaskData> mList;


        public DoneAdapter(Context context, ArrayList<TaskData> list) {
            super(context, R.layout.doing_list_item, R.id.contentTxt, list);

            mContext = context;
            mList = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                row = new DoneListItemView(mContext);
            }

            final TaskData taskData = mList.get(position);

            DoneListItemView mItem = (DoneListItemView) row;
            mItem.contentTxt.setText(taskData.user_content);


            String imageURL = "https://graph.facebook.com/" + taskData.uid + "/picture";
            ImageLoader.getInstance().displayImage(imageURL, mItem.profileImage, options);

            mItem.userNameTxt.setText(taskData.username);

            if (ContextUtil.getUSER_ID(MainActivity.this) != taskData.user_id) {
                mItem.myViewPager.setPagingEnabled(false);
            } else {
                mItem.myViewPager.setPagingEnabled(true);
            }

            mItem.myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 1) {
                        ServerUtil.to_right(MainActivity.this, mProjectData.id + "", taskData.user_content, taskData.id + "", new ServerUtil.JsonResponseHandler() {
                            @Override
                            public void onResponse(JSONObject json) {
                                todoArrayList.clear();
                                doingArrayList.clear();
                                doneArrayList.clear();
                                try {
                                    JSONArray content = json.getJSONArray("content");
                                    for (int i = 0; i < content.length(); i++) {
                                        TaskData taskData = TaskData.getTaskDataFromJson(content.getJSONObject(i));
                                        if (taskData.status.equals("to_do")) {
                                            todoArrayList.add(taskData);
                                        } else if (taskData.status.equals("doing")) {
                                            doingArrayList.add(taskData);
                                        } else {
                                            doneArrayList.add(taskData);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.d("json", json.toString());

                                displayListViews();
                            }
                        });
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            return row;
        }

    }

    public class DoingAdapter extends ArrayAdapter<TaskData> {
        Context mContext;
        ArrayList<TaskData> mList;


        public DoingAdapter(Context context, ArrayList<TaskData> list) {
            super(context, R.layout.doing_list_item, R.id.contentTxt, list);

            mContext = context;
            mList = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                row = new DoingListItemView(mContext);
            }

            final TaskData taskData = mList.get(position);

            DoingListItemView mItem = (DoingListItemView) row;
            mItem.contentTxt.setText(taskData.user_content);


            String imageURL = "https://graph.facebook.com/" + taskData.uid + "/picture";
            ImageLoader.getInstance().displayImage(imageURL, mItem.profileImage, options);

            mItem.userNameTxt.setText(taskData.username);

            if (ContextUtil.getUSER_ID(MainActivity.this) != taskData.user_id) {
                mItem.myViewPager.setPagingEnabled(false);
            } else {
                mItem.myViewPager.setPagingEnabled(true);
            }

            mItem.myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0) {
                        ServerUtil.to_left(MainActivity.this, mProjectData.id + "", taskData.user_content, taskData.id + "", new ServerUtil.JsonResponseHandler() {
                            @Override
                            public void onResponse(JSONObject json) {
                                todoArrayList.clear();
                                doingArrayList.clear();
                                doneArrayList.clear();
                                try {
                                    JSONArray content = json.getJSONArray("content");
                                    for (int i = 0; i < content.length(); i++) {
                                        TaskData taskData = TaskData.getTaskDataFromJson(content.getJSONObject(i));
                                        if (taskData.status.equals("to_do")) {
                                            todoArrayList.add(taskData);
                                        } else if (taskData.status.equals("doing")) {
                                            doingArrayList.add(taskData);
                                        } else {
                                            doneArrayList.add(taskData);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.d("json", json.toString());

                                displayListViews();
                            }
                        });
                    } else if (position == 2) {
                        ServerUtil.to_right(MainActivity.this, mProjectData.id + "", taskData.user_content, taskData.id + "", new ServerUtil.JsonResponseHandler() {
                            @Override
                            public void onResponse(JSONObject json) {
                                todoArrayList.clear();
                                doingArrayList.clear();
                                doneArrayList.clear();
                                try {
                                    JSONArray content = json.getJSONArray("content");
                                    for (int i = 0; i < content.length(); i++) {
                                        TaskData taskData = TaskData.getTaskDataFromJson(content.getJSONObject(i));
                                        if (taskData.status.equals("to_do")) {
                                            todoArrayList.add(taskData);
                                        } else if (taskData.status.equals("doing")) {
                                            doingArrayList.add(taskData);
                                        } else {
                                            doneArrayList.add(taskData);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.d("json", json.toString());

                                displayListViews();
                            }
                        });
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            return row;
        }

    }

}
