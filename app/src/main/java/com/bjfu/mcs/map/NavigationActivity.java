package com.bjfu.mcs.map;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.bjfu.mcs.R;
import com.bjfu.mcs.adapter.PoiHostoryAdapter;
import com.bjfu.mcs.adapter.PoiSuggestionAdapter;
import com.bjfu.mcs.adapter.RecyclerViewDivider;
import com.bjfu.mcs.bean.PoiObject;
import com.bjfu.mcs.map.util.LocationManager;
import com.bjfu.mcs.map.util.MapUtils;
import com.bjfu.mcs.map.util.NavUtil;
import com.bjfu.mcs.map.util.ProviderUtil;
import com.bjfu.mcs.utils.Rx.RxActivityTool;

import java.util.List;

import butterknife.ButterKnife;

import static com.bjfu.mcs.map.util.NavUtil.activityList;



public class NavigationActivity extends AppCompatActivity implements
        OnGetSuggestionResultListener, PoiSuggestionAdapter.OnItemClickListener
        , PoiHostoryAdapter.OnHistoryItemClickListener {

    LinearLayout place_search_layout;
    //RelativeLayout title_content_layout;
    EditText place_edit;
    TextView start_place_edit, destination_edit;
    RecyclerView recyclerview_poi, recyclerview_poi_history;
    private List<SuggestionResult.SuggestionInfo> suggestionInfoList;
    private SuggestionSearch mSuggestionSearch = null;
    PoiSuggestionAdapter sugAdapter;
    boolean firstSetAdapter = true, isStartPoi = true;
    String currentAddress, start_place, destination;
    LatLng startLL, endLL, tempLL;
    PoiHostoryAdapter poiHostoryAdapter;
    ProviderUtil providerUtil;
    private NavigationActivity mContext;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityList.add(this);
        setContentView(R.layout.activity_navigation);
        mContext = this;
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("导航");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        providerUtil = new ProviderUtil(this);
        //想使用内置导航，必须初始化导航， NavUtil.initNavi(this);
        NavUtil.initNavi(this);
        currentAddress = LocationManager.getInstance().getAddress();
        place_search_layout = (LinearLayout) findViewById(R.id.place_search_layout);
        start_place_edit = (TextView) findViewById(R.id.start_place_edit);
        destination_edit = (TextView) findViewById(R.id.destination_edit);
        place_edit = (EditText) findViewById(R.id.place_edit);
        recyclerview_poi = (RecyclerView) findViewById(R.id.recyclerview_poi);
        recyclerview_poi.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_poi.addItemDecoration(new RecyclerViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 1,
                ContextCompat.getColor(this, R.color.color_c8cacc)));
        recyclerview_poi_history = (RecyclerView) findViewById(R.id.recyclerview_poi_history);
        recyclerview_poi_history.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_poi_history.addItemDecoration(new RecyclerViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 1,
                ContextCompat.getColor(this, R.color.color_c8cacc)));
        // 初始化建议搜索模块，注册建议搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        /**
         * 当输入关键字变化时，动态更新建议列表
         */
        place_edit.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {

                if (cs.length() <= 0) {
                    return;
                }
                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                mSuggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())
                                .keyword(cs.toString()).city("北京"));
            }
        });

    }


    public void switchPoi(View view) {
        tempLL = startLL;
        startLL = endLL;
        endLL = tempLL;
        start_place = start_place_edit.getText().toString();
        destination = destination_edit.getText().toString();
        destination_edit.setText(start_place);
        start_place_edit.setText(destination);
        if (start_place_edit.getText().toString().equals(getString(R.string.input_destination)))
            start_place_edit.setText(getString(R.string.input_start_place));
    }

    public void showInputStart(View view) {
        place_edit.requestFocus();
        new MapUtils(this).showIMM();
        //title_content_layout.setVisibility(View.GONE);
        place_search_layout.setVisibility(View.VISIBLE);
        place_edit.setHint(getString(R.string.input_start_place));
        isStartPoi = true;
        showHistoryPOI();
    }

    public void showInputDestination(View view) {
        place_edit.requestFocus();
        new MapUtils(this).showIMM();

        //title_content_layout.setVisibility(View.GONE);
        place_search_layout.setVisibility(View.VISIBLE);
        place_edit.setHint(getString(R.string.input_destination));
        isStartPoi = false;
        showHistoryPOI();
    }

    public void backFromSearchPlace(View view) {
        new MapUtils(this).hideIMM();
        place_edit.setText("");
        if (sugAdapter != null)
            sugAdapter.changeData(null);
        //title_content_layout.setVisibility(View.VISIBLE);
        place_search_layout.setVisibility(View.GONE);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (place_search_layout.getVisibility() == View.VISIBLE) {
                backFromSearchPlace(place_search_layout);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }
        recyclerview_poi_history.setVisibility(View.GONE);
        suggestionInfoList = res.getAllSuggestions();
        if (firstSetAdapter) {
            String from = isStartPoi == true ? "start" : "detination";
            sugAdapter = new PoiSuggestionAdapter(this, suggestionInfoList, from);
            recyclerview_poi.setAdapter(sugAdapter);
            sugAdapter.setOnClickListener(this);
            firstSetAdapter = false;
        } else {
            sugAdapter.changeData(suggestionInfoList);
        }
    }

    private void showHistoryPOI() {
        try {
            recyclerview_poi_history.setVisibility(View.VISIBLE);
            List<PoiObject> poiItems = providerUtil.getData();
            poiHostoryAdapter = new PoiHostoryAdapter(NavigationActivity.this, poiItems);
            recyclerview_poi_history.setAdapter(poiHostoryAdapter);
            poiHostoryAdapter.setOnClickListener(this);
        } catch (Exception e) {
            Log.d("gaolei", e.getMessage());
        }
    }

    public void startNavigation(View view) {
        if (start_place_edit.getText().toString().equals(getString(R.string.my_position)))
            startLL = LocationManager.getInstance().getCurrentLL();
        if (startLL == null) {
            Toast.makeText(this, getString(R.string.please_input_start_place), Toast.LENGTH_SHORT).show();
            return;
        }
        if (endLL == null) {
            Toast.makeText(this, getString(R.string.please_input_destination), Toast.LENGTH_SHORT).show();
            return;
        }
        if (startLL != null && endLL != null) {
            String startAddr = start_place_edit.getText().toString();
            String start = startAddr.equals(getString(R.string.my_position)) ? currentAddress : startAddr;
            String endAddr = destination_edit.getText().toString();
            String end = endAddr.equals(getString(R.string.my_position)) ? currentAddress : endAddr;
            Log.i("跳转位置","start-->"+start+"--->end-->"+end+"-->startLL-->"+startLL+"-->endLL-->"+endLL);
            NavUtil.showChoiceNaviWayDialog(this, startLL, endLL, start, end);
        }
    }

    public void searchButtonProcess(View view){
        if (start_place_edit.getText().toString().equals(getString(R.string.my_position)))
            startLL = LocationManager.getInstance().getCurrentLL();
        if (startLL == null) {
            Toast.makeText(this, getString(R.string.please_input_start_place), Toast.LENGTH_SHORT).show();
            return;
        }
        if (endLL == null) {
            Toast.makeText(this, getString(R.string.please_input_destination), Toast.LENGTH_SHORT).show();
            return;
        }
        if (startLL != null && endLL != null) {
            String startAddr = start_place_edit.getText().toString();
            String start = startAddr.equals(getString(R.string.my_position)) ? currentAddress : startAddr;
            String endAddr = destination_edit.getText().toString();
            String end = endAddr.equals(getString(R.string.my_position)) ? currentAddress : endAddr;
            Log.i("跳转位置","start-->"+start+"--->end-->"+end);
            Bundle bundle = new Bundle();
            bundle.putSerializable("startdistance", start.replace("中国",""));
            bundle.putSerializable("enddistance",end);
            RxActivityTool.skipActivity(NavigationActivity.this,RoutePlanDemo.class,bundle);

        }
    }

    @Override
    public void onItemClick(View v, int position, String flag, SuggestionResult.SuggestionInfo info) {
        if (isStartPoi) {
            start_place_edit.setText(info.key);
            startLL = info.pt;
        } else {
            destination_edit.setText(info.key);
            endLL = info.pt;
        }
        backFromSearchPlace(place_search_layout);
        providerUtil.addData(info.key,info.district, info.pt.latitude + "", info.pt.longitude + "");
    }

    @Override
    public void onHistoryItemClick(View v, int position, PoiObject poiObject) {

        if (isStartPoi) {
            startLL = new LatLng(Double.parseDouble(poiObject.lattitude), Double.parseDouble(poiObject.longitude));
            start_place_edit.setText(poiObject.address);
        } else {
            endLL = new LatLng(Double.parseDouble(poiObject.lattitude), Double.parseDouble(poiObject.longitude));
            destination_edit.setText(poiObject.address);
        }
        backFromSearchPlace(place_search_layout);

    }
}
