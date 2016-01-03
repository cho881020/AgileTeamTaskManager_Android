package kr.co.kj_studio.agileteamtaskmanager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import kr.co.kj_studio.agileteamtaskmanager.view.DoingListItem;
import kr.co.kj_studio.agileteamtaskmanager.R;

/**
 * Created by KJ_Studio on 2015-12-28.
 */
public class DoingAdapter extends ArrayAdapter<String> {
    Context mContext;

    public DoingAdapter(Context context, ArrayList<String> list) {
        super(context, R.layout.doing_list_item, R.id.contentTxt, list);

        mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row== null) {
            row = new DoingListItem(mContext);
        }

        return row;
    }

    @Override
    public int getCount() {
        return 10;
    }
}