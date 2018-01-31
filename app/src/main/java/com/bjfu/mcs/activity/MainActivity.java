package com.bjfu.mcs.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.bjfu.mcs.R;
import com.bjfu.mcs.base.BaseActivity;
import com.bjfu.mcs.utils.Constants;
import com.bjfu.mcs.utils.Rx.RxActivityTool;
import com.bjfu.mcs.utils.Rx.RxToast;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;


public class MainActivity extends BaseActivity  {

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;

    public static Handler mHandler = null;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constants.upLoad:
                        RxToast.success("定位成功");
                        break;
                    default:
                        break;
                }

            }
        };

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.drawer_item_crossfade_drawer_layout_drawer);

        // Create a few sample profile
        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460");
        final IProfile profile2 = new ProfileDrawerItem().withName("Bernat Borras").withEmail("alorma@github.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        profile, profile2
                )
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withDrawerLayout(R.layout.crossfade_drawer)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_compact_header).withIcon(GoogleMaterial.Icon.gmd_brightness_5).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_action_bar_drawer).withIcon(FontAwesome.Icon.faw_home).withBadge("22").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName("地图展示").withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3),
                        new PrimaryDrawerItem().withName("图形展示").withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4),
                        new PrimaryDrawerItem().withDescription("经度\n纬度").withName("定位").withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName("设置").withIcon(FontAwesome.Icon.faw_info).withIdentifier(6),
                        new SecondaryDrawerItem().withName("关于").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withTag("Bullhorn").withIdentifier(7)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(drawerItem != null){
                            Intent intent = null;
                            if(drawerItem.getIdentifier() == 1){
                                RxToast.normal(drawerItem.getIdentifier()+"");
                            }else if(drawerItem.getIdentifier() == 2){
                                RxToast.normal(drawerItem.getIdentifier()+"");
                            }else if(drawerItem.getIdentifier() == 3){
                                RxToast.normal(drawerItem.getIdentifier()+"");
                            }else if(drawerItem.getIdentifier() == 4){
                                RxToast.normal(drawerItem.getIdentifier()+"");
                            }else if(drawerItem.getIdentifier() == 5){
                                RxToast.normal(drawerItem.getIdentifier()+"");
                            }else if(drawerItem.getIdentifier() == 6){
                                RxToast.normal(drawerItem.getIdentifier()+"");
                                RxActivityTool.skipActivity(MainActivity.this, SettingActivity.class);
                            }else if(drawerItem.getIdentifier() == 7){
                                RxToast.normal(drawerItem.getIdentifier()+"");
                                RxActivityTool.skipActivity(MainActivity.this,AboutActivity.class);
                            }
                            if(intent != null){
                                MainActivity.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        final MiniDrawer miniResult = result.getMiniDrawer();
        View view = miniResult.build(this);
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = result.saveInstanceState(outState);
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

}
