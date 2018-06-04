package com.bjfu.mcs.utils.picker.entity;

import java.util.List;

/**
 * 用于联动选择器展示的第二级条目
 */
public interface LinkageSecond<Trd> extends LinkageItem {

    List<Trd> getThirds();

}