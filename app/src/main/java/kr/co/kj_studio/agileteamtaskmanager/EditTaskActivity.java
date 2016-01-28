package kr.co.kj_studio.agileteamtaskmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import kr.co.kj_studio.agileteamtaskmanager.util.ContextUtil;
import kr.co.kj_studio.agileteamtaskmanager.util.ServerUtil;

public class EditTaskActivity extends BaseActivity {

    private android.widget.EditText todoContentEdt;

    int teamId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        setCustomActionbar();
        bindViews();
        setupEvents();
        setValues();

        teamId = getIntent().getIntExtra("teamId", -1);

    }

    @Override
    public void bindViews() {
        super.bindViews();

        this.todoContentEdt = (EditText) findViewById(R.id.todoContentEdt);
    }

    @Override
    public void setupEvents() {
        super.setupEvents();

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerUtil.new_cont(EditTaskActivity.this, ContextUtil.getUSER_ID(EditTaskActivity.this)+"", teamId+"", todoContentEdt.getText().toString(), new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {

                        Toast.makeText(EditTaskActivity.this, "할일이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });

    }

    private void setValues() {
        mTitleTextView.setText("새 TODO 만들기");
        rightBtn.setVisibility(View.VISIBLE);
    }
}
