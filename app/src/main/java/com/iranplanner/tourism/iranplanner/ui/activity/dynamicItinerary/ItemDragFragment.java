package com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary;

import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.standard.StandardFragment;
import com.iranplanner.tourism.iranplanner.ui.activity.OnDynamicListListener;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.List;

import entity.DayPosition;
import entity.ItnDaily;
import tools.Util;

/**
 * Created by yuyidong on 16/4/20.
 */
public class ItemDragFragment extends StandardFragment implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, AbsListView.OnScrollListener,
        SlideAndDragListView.OnDragDropListener, SlideAndDragListView.OnSlideListener,
        SlideAndDragListView.OnMenuItemClickListener, SlideAndDragListView.OnItemDeleteListener {
    private static final String TAG = ItemDragFragment.class.getSimpleName();

    private Menu mMenu;
    //    private List<ApplicationInfo> mAppList;
    private SlideAndDragListView mListView;
    private Toast mToast;
    private DayPosition mDraggedEntity;
    //    private ApplicationInfo mDraggedEntity;
    View view;
    List<DayPosition> dayPositionList;
    OnDynamicListListener onDynamicListListener;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_sdlv, container, false);
        initData();
        initMenu();
        initUiAndListener();
        mToast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);


        // Right to left viewpager
        SlideAndDragListView slideAndDragListView = view.findViewById(R.id.lv_edit);
        slideAndDragListView.setRotationY(180);
        return view;
    }

    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            dayPositionList = (List<DayPosition>) bundle.getSerializable("dayPosition");
            onDynamicListListener = (OnDynamicListListener) bundle.getSerializable("OnDynamicListListener");
        }
    }

    public void initMenu() {
        mMenu = new Menu(true);
//        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width) * 2)
//                .setBackground(Util.getDrawable(getActivity(), R.drawable.btn_left0))
//                .setText("One")
//                .setTextColor(Color.GRAY)
//                .setTextSize(14)
//                .build());
//        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width))
//                .setBackground(Util.getDrawable(getActivity(), R.drawable.btn_left1))
//                .setText("Two")
//                .setTextColor(Color.BLACK)
//                .setTextSize(14)
//                .build());
//        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width) + 30)
//                .setBackground(Util.getDrawable(getActivity(), R.drawable.btn_right0))
//                .setText("Three")
//                .setDirection(MenuItem.DIRECTION_RIGHT)
//                .setTextColor(Color.BLACK)
//                .setTextSize(14)
//                .build());
        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width_img))
                .setBackground(Util.getDrawable(getActivity(), R.drawable.btn_right1))
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setIcon(getResources().getDrawable(R.drawable.ic_draw_map))
                .build());
    }

    public void initUiAndListener() {
        mListView = (SlideAndDragListView) view.findViewById(R.id.lv_edit);
        mListView.setMenu(mMenu);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnDragDropListener(this);
//        mListView.setOnItemLongClickListener(this);
        mListView.setOnSlideListener(this);
        mListView.setOnMenuItemClickListener(this);
        mListView.setOnItemDeleteListener(this);
        mListView.setOnScrollListener(this);
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            if (dayPositionList != null && dayPositionList.size() > 0) {
                return dayPositionList.size();
            } else
                return 0;
        }

        @Override
        public Object getItem(int position) {
            return dayPositionList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return dayPositionList.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CustomViewHolder cvh;
            if (convertView == null) {
                cvh = new CustomViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_custom, null);
                cvh.imgLogo = (ImageView) convertView.findViewById(R.id.img_item_edit);
                cvh.txtName = (TextView) convertView.findViewById(R.id.txt_item_edit);
                cvh.imgLogo2 = (ImageView) convertView.findViewById(R.id.img_item_edit2);
                cvh.imgLogo2.setOnTouchListener(mOnTouchListener);
                convertView.setTag(cvh);
            } else {
                cvh = (CustomViewHolder) convertView.getTag();
            }

            cvh.txtName.setText(dayPositionList.get(position).getTitle());
//            cvh.imgLogo.setImageDrawable();
            cvh.imgLogo2.setImageDrawable(Util.getDrawable(getActivity(), R.mipmap.ic_vertical_hand_scroll_foreground));
            cvh.imgLogo2.setTag(Integer.parseInt(position + ""));
            return convertView;
        }

        class CustomViewHolder {
            public ImageView imgLogo;
            public TextView txtName;
            public ImageView imgLogo2;
        }

        private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Object o = v.getTag();
                if (o != null && o instanceof Integer) {
                    mListView.startDrag(((Integer) o).intValue());
                }
                return false;
            }
        };
    };

    @Override
    public void onDragViewStart(int beginPosition) {
        mDraggedEntity = dayPositionList.get(beginPosition);
        toast("onDragViewStart   beginPosition--->" + beginPosition);
    }

    @Override
    public void onDragDropViewMoved(int fromPosition, int toPosition) {
        DayPosition applicationInfo = dayPositionList.remove(fromPosition);
        dayPositionList.add(toPosition, applicationInfo);
        toast("onDragDropViewMoved   fromPosition--->" + fromPosition + "  toPosition-->" + toPosition);
    }

    @Override
    public void onDragViewDown(int finalPosition) {
        dayPositionList.set(finalPosition, mDraggedEntity);
        toast("onDragViewDown   finalPosition--->" + finalPosition);
    }

    @Override
    public void onSlideOpen(View view, View parentView, int position, int direction) {
        toast("onSlideOpen   position--->" + position + "  direction--->" + direction);
    }

    @Override
    public void onSlideClose(View view, View parentView, int position, int direction) {
        toast("onSlideClose   position--->" + position + "  direction--->" + direction);
    }

    @Override
    public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
        toast("onMenuItemClick   itemPosition--->" + itemPosition + "  buttonPosition-->" + buttonPosition + "  direction-->" + direction);
        switch (direction) {
            case MenuItem.DIRECTION_RIGHT:
                switch (buttonPosition) {
                    case 0:
                        return Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
                }
                break;
        }
        return Menu.ITEM_NOTHING;
    }

    @Override
    public void onItemDeleteAnimationFinished(View view, int position) {
        int deletePosition = position - mListView.getHeaderViewsCount();
        DayPosition dayPosition = dayPositionList.get(deletePosition);
        if (onDynamicListListener != null) {
            onDynamicListListener.onDelete(dayPosition);
        }
        dayPositionList.remove(deletePosition);
        mAdapter.notifyDataSetChanged();
        toast("onItemDeleteAnimationFinished   position--->" + position);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        toast("onItemClick   position--->" + position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        toast("onItemLongClick   position--->" + position);
        return false;
    }

    private void toast(String toast) {
        mToast.setText(toast);
        mToast.show();
    }
}
