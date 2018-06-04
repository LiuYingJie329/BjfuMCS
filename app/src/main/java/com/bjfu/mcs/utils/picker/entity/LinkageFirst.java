package com.bjfu.mcs.utils.picker.entity;

import java.util.List;

/**
 * 用于联动选择器展示的第一级条目
 *
 */
public interface LinkageFirst<Snd> extends LinkageItem {

    List<Snd> getSeconds();

}