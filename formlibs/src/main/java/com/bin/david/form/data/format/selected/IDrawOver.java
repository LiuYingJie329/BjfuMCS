package com.bin.david.form.data.format.selected;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.bin.david.form.core.TableConfig;


public interface IDrawOver {

     void draw(Canvas canvas,Rect scaleRect, Rect showRect, TableConfig config);
}
