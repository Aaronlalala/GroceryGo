package edu.illinois.cs465.grocerygo.layout.activity;

import static edu.illinois.cs465.grocerygo.constant.Constant.BOTTOM_HOMEPAGE_BUTTON;
import static edu.illinois.cs465.grocerygo.constant.Constant.BOTTOM_MAIL_BUTTON;
import static edu.illinois.cs465.grocerygo.constant.Constant.BOTTOM_ONGOING_BUTTON;
import static edu.illinois.cs465.grocerygo.constant.Constant.MAIL_FRAGMENT_TAG;
import static edu.illinois.cs465.grocerygo.constant.Constant.ONGOING_FRAGMENT_TAG;
import static edu.illinois.cs465.grocerygo.constant.Constant.POST_FRAGMENT_TAG;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import edu.illinois.cs465.grocerygo.R;
import edu.illinois.cs465.grocerygo.layout.fragment.mail.MailFragment;
import edu.illinois.cs465.grocerygo.layout.fragment.OngoingFragment;
import edu.illinois.cs465.grocerygo.layout.fragment.post.PostFragment;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawer;

    // bottom tabLayout object
    private TabLayout mBottomTabLayout;
    // current selected tab position
    private int curPosition = 0;
    // current page
    private Fragment curFragment;
    // tool bar
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_layout);
        initView();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void initView() {
        mBottomTabLayout = findViewById(R.id.bottom_tab);
        setBottomTabStyle();
        attachFragment(POST_FRAGMENT_TAG);
        ImageView postView = findViewById(R.id.post);
        NavigationView menu = findViewById(R.id.nav_view);
        // Create side menu
        toolbar = findViewById(R.id.toolbar);
        // Set toolbar as the action bar
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        menu.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_history: {
                    Intent intent = new Intent(this, HistoryActivity.class);
                    this.startActivity(intent);
                    drawer.closeDrawers();
                    return true;
                }
                default: {
                    return false;
                }
            }
        });
        postView.setOnClickListener(view -> {
            Intent intent = new Intent(this, PostActivity.class);
            this.startActivity(intent);
        });
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

}