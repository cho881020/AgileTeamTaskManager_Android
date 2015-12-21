package kr.co.kj_studio.agileteamtaskmanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KJ_Studio on 2015-12-07.
 */
public class MyAdapter extends ArrayAdapter<String> {
    Context mContext;
    public MyAdapter(Context context, ArrayList<String> list) {
        super(context, R.layout.my_list_item, R.id.contentTxt, list);

        mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = new MyListItem(mContext);
        return row;
    }

    @Override
    public int getCount() {
        return 10;
    }
}
