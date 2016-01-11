package kr.co.kj_studio.agileteamtaskmanager.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.co.kj_studio.agileteamtaskmanager.R;

/**
 * Created by KJ_Studio on 2015-12-07.
 */
public class DoingListItemView extends LinearLayout {

    public CustomViewPager myViewPager;
    TextView emptyView1;
    TextView emptyView2;
    LinearLayout contentLayout;

    public TextView contentTxt;
    Context mContext;
    public ImageView profileImage;
    public TextView userNameTxt;

    public DoingListItemView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public DoingListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public DoingListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.doing_list_item, this);

        myViewPager = (CustomViewPager) findViewById(R.id.myViewPager);
        emptyView1 = (TextView) findViewById(R.id.emptyView1);
        emptyView2 = (TextView) findViewById(R.id.emptyView2);
        contentLayout = (LinearLayout) findViewById(R.id.contentLayout);

        contentTxt = (TextView) findViewById(R.id.contentTxt);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        userNameTxt = (TextView) findViewById(R.id.userNameTxt);

        WizardPagerAdapter adapter = new WizardPagerAdapter();
        myViewPager.setAdapter(adapter);
        myViewPager.setOffscreenPageLimit(3);
        myViewPager.setCurrentItem(1);

    }

    class WizardPagerAdapter extends PagerAdapter {

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.emptyView1;
                    break;
                case 1:
                    resId = R.id.contentLayout;
                    break;
                case 2:
                    resId = R.id.emptyView2;
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
}
