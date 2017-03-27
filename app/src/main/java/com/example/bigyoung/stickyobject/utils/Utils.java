package com.example.bigyoung.stickyobject.utils;

import android.graphics.Rect;
import android.view.View;

/**
 * Created by BigYoung on 2017/3/26.
 */

public class Utils {
    //获取状态栏和标题栏的高度
    public static int getViewTopLocation(View view) {
        if (view != null) {
            Rect rect = new Rect();
            view.getWindowVisibleDisplayFrame(rect);
            return rect.top;
        } else {
            return 0;
        }

    }
}
