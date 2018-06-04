package com.bjfu.mcs.utils.picker.entity;

import com.bjfu.mcs.utils.picker.WheelItem;

/**
 * 用于联动选择器展示的条目
 */
interface LinkageItem extends WheelItem {

    /**
     * 唯一标识，用于判断两个条目是否相同
     */
    Object getId();

}
