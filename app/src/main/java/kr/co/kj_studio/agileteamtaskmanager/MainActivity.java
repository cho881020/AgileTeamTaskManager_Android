package kr.co.kj_studio.agileteamtaskmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.kj_studio.agileteamtaskmanager.adapter.DoingAdapter;
import kr.co.kj_studio.agileteamtaskmanager.adapter.DoneAdapter;
import kr.co.kj_studio.agileteamtaskmanager.adapter.MyAdapter;
import kr.co.kj_studio.agileteamtaskmanager.datas.ProjectData;
import kr.co.kj_studio.agileteamtaskmanager.datas.TaskData;
import kr.co.kj_studio.agileteamtaskmanager.util.ContextUtil;
import kr.co.kj_studio.agileteamtaskmanager.util.ServerUtil;
import kr.co.kj_studio.agileteamtaskmanager.view.TodoListItemView;

public class MainActivity extends BaseActivity {


    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).build();

    private ListView todoListView;
    private ListView doingListView;
    private ListView doneListView;
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


    TodoAdapter todoAdapter;
    DoingAdapter doingAdapter;
    DoneAdapter doneAdapter;



    ProjectData mProjectData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(MainActivity.this).build();
        ImageLoader.getInstance().init(config);

        bindViews();
        setupEvents();
        setValues();

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
    }

    private void displayListViews() {

        todoCount.setText(todoArrayList.size()+"");

        todoListView.setAdapter(new TodoAdapter(MainActivity.this, todoArrayList));
        doingListView.setAdapter(new MyAdapter(MainActivity.this, new ArrayList<String>()));
        doneListView.setAdapter(new MyAdapter(MainActivity.this, new ArrayList<String>()));
    }

    void setIndicators(int index) {
        if (index == 0 ) {
            todoIndicator.setVisibility(View.VISIBLE);
            doingIndicator.setVisibility(View.GONE);
            doneIndicator.setVisibility(View.GONE);
        }
        else if (index == 1) {

            todoIndicator.setVisibility(View.GONE);
            doingIndicator.setVisibility(View.VISIBLE);
            doneIndicator.setVisibility(View.GONE);
        }
        else if (index == 2) {

            todoIndicator.setVisibility(View.GONE);
            doingIndicator.setVisibility(View.GONE);
            doneIndicator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setupEvents() {
        super.setupEvents();
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

    @Override
    public void bindViews() {
        super.bindViews();

        this.doneLayout = (FrameLayout) findViewById(R.id.doneLayout);
        this.doneIndicator = (TextView) findViewById(R.id.doneIndicator);
        this.doneCount = (TextView) findViewById(R.id.doneCount);
        this.doingLayout = (FrameLayout) findViewById(R.id.doingLayout);
        this.doingIndicator = (TextView) findViewById(R.id.doingIndicator);
        this.doingCount = (TextView) findViewById(R.id.doingCount);
        this.todoLayout = (FrameLayout) findViewById(R.id.todoLayout);
        this.todoIndicator = (TextView) findViewById(R.id.todoIndicator);
        this.todoCount = (TextView) findViewById(R.id.todoCount);
        this.myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        this.doneListView = (ListView) findViewById(R.id.doneListView);
        this.doingListView = (ListView) findViewById(R.id.doingListView);
        this.todoListView = (ListView) findViewById(R.id.todoListView);
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

            if (row== null) {
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
            }
            else {
                mItem.myViewPager.setPagingEnabled(true);
            }

            mItem.myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0) {
                        ServerUtil.to_left(MainActivity.this, mProjectData.id + "", taskData.user_content, taskData.id+"", new ServerUtil.JsonResponseHandler() {
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
