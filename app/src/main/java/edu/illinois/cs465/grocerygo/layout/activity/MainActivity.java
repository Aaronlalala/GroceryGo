package edu.illinois.cs465.grocerygo.layout.activity;

import static edu.illinois.cs465.grocerygo.constant.Constant.BOTTOM_HOMEPAGE_BUTTON;
import static edu.illinois.cs465.grocerygo.constant.Constant.BOTTOM_MAIL_BUTTON;
import static edu.illinois.cs465.grocerygo.constant.Constant.BOTTOM_ONGOING_BUTTON;
import static edu.illinois.cs465.grocerygo.constant.Constant.MAIL_FRAGMENT_TAG;
import static edu.illinois.cs465.grocerygo.constant.Constant.ONGOING_FRAGMENT_TAG;
import static edu.illinois.cs465.grocerygo.constant.Constant.POST_FRAGMENT_TAG;

import androidx.appcompat.app.AppCompatActivity;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import edu.illinois.cs465.grocerygo.R;
//import edu.illinois.cs465.grocerygo.chatRoomAdapter;
import edu.illinois.cs465.grocerygo.layout.fragment.MailFragment;
import edu.illinois.cs465.grocerygo.layout.fragment.OngoingFragment;
import edu.illinois.cs465.grocerygo.layout.fragment.PostFragment;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawer;

    // bottom tabLayout object
    private TabLayout mBottomTabLayout;
    // current selected tab position
    private int curPosition = 0;
    // current page
    private Fragment curFragment;

//    private RecyclerView recycler_view;
//    private chatRoomAdapter adapter;
//    private ArrayList<String> mData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_layout);
        mBottomTabLayout = findViewById(R.id.bottom_tab);
        setBottomTabStyle();
        attachFragment(POST_FRAGMENT_TAG);

//        setContentView(R.layout.mail_fragment);
//        // 準備資料，塞50個項目到ArrayList裡
//        for(int i = 0; i < 100; i++) {
//            mData.add("項目"+i);
//        }
//        // 將資料交給adapter
//        adapter = new chatRoomAdapter(mData);
//        // 設置adapter給recycler_view
//        recycler_view.setAdapter(adapter);
//        // 連結元件
//        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
//        // 設置RecyclerView為列表型態
//        recycler_view.setLayoutManager(new LinearLayoutManager(this));
//        // 設置格線
//        //recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    private  void setBottomTabStyle() {
        mBottomTabLayout.addTab(mBottomTabLayout.newTab().setCustomView(generateTabStyle(BOTTOM_HOMEPAGE_BUTTON)));
        mBottomTabLayout.addTab(mBottomTabLayout.newTab().setCustomView(generateTabStyle(BOTTOM_ONGOING_BUTTON)));
        mBottomTabLayout.addTab(mBottomTabLayout.newTab().setCustomView(generateTabStyle(BOTTOM_MAIL_BUTTON)));
        mBottomTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab != null && tab.getCustomView() != null && onBottomTabSelected(tab.getPosition())) {
                    int position = tab.getPosition();
                    ImageView imageView = tab.getCustomView().findViewById(R.id.tab_icon);
                    switch (position) {
                        case BOTTOM_HOMEPAGE_BUTTON: {
                            imageView.setImageResource(R.drawable.home_black);
                            break;
                        }
                        case BOTTOM_ONGOING_BUTTON: {
                            imageView.setImageResource(R.drawable.car_black);
                            break;
                        }
                        case BOTTOM_MAIL_BUTTON: {
                            imageView.setImageResource(R.drawable.envelope_black);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab != null && tab.getCustomView() != null && onBottomTabSelected(tab.getPosition())) {
                    int position = tab.getPosition();
                    ImageView imageView = tab.getCustomView().findViewById(R.id.tab_icon);
                    switch (position) {
                        case BOTTOM_HOMEPAGE_BUTTON: {
                            imageView.setImageResource(R.drawable.home_white);
                            break;
                        }
                        case BOTTOM_ONGOING_BUTTON: {
                            imageView.setImageResource(R.drawable.car_white);
                            break;
                        }
                        case BOTTOM_MAIL_BUTTON: {
                            imageView.setImageResource(R.drawable.envelope_white);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });
//        mBottomTabLayout.getTabAt(BOTTOM_HOMEPAGE_BUTTON).select();
    }

    private View generateTabStyle(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabitem_layout, null);
        ImageView img = view.findViewById(R.id.tab_icon);
        switch (position) {
            case BOTTOM_HOMEPAGE_BUTTON: {
                img.setImageResource(R.drawable.home_black);
                break;
            }
            case BOTTOM_ONGOING_BUTTON: {
                img.setImageResource(R.drawable.car_white);
                break;
            }
            case BOTTOM_MAIL_BUTTON: {
                img.setImageResource(R.drawable.envelope_white);
                break;
            }
            default: {
                Log.e("TAB P", "generateTabStyle: error" );
            }
        }
        return view;
    }

    private boolean onBottomTabSelected(int position) {
        if (position == curPosition) {
            return true;
        }
        switch (position) {
            case BOTTOM_HOMEPAGE_BUTTON: {
                attachFragment(POST_FRAGMENT_TAG);
                break;
            }

            case BOTTOM_ONGOING_BUTTON: {
                attachFragment(ONGOING_FRAGMENT_TAG);
                break;
            }

            case BOTTOM_MAIL_BUTTON: {
                attachFragment(MAIL_FRAGMENT_TAG);
                break;
            }
        }
        curPosition = position;
        return true;
    }

    private void attachFragment(String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (curFragment != null) {
            ft.hide(curFragment);
        }
        if (fragment == null) {
            switch (tag) {
                case ONGOING_FRAGMENT_TAG: {
                    fragment = new OngoingFragment();
                    break;
                }
                case MAIL_FRAGMENT_TAG: {
                    fragment = new MailFragment();
                    break;
                }
                default: {
//                  This is for POST_FRAGMENT_TAG
                    fragment = new PostFragment();
                    break;
                }
            }
            fragment.setUserVisibleHint(true);
            ft.add(R.id.container,fragment, tag);
        } else {
            ft.show(fragment);
        }
        curFragment = fragment;
        ft.commit();
    }
//    @Override
//    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
}