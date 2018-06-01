package com.bjfu.mcs.adapter;

import com.bjfu.mcs.R;
import com.bjfu.mcs.greendao.LocationInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;


public class LocationAdapter extends BaseQuickAdapter<LocationInfo,BaseViewHolder> {

    public LocationAdapter(int layoutResId, ArrayList<LocationInfo> dataList) {
        super(layoutResId, dataList);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, LocationInfo locationInfo) {

        baseViewHolder.setText(R.id.tv_location,locationInfo.getAddressStr());
        baseViewHolder.setText(R.id.tv_location_desc,locationInfo.getLocationDescribe());
        baseViewHolder.setText(R.id.tv_time , locationInfo.getClientTime());

    }
}
