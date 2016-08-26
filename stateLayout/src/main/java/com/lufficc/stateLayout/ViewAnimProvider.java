package com.lufficc.stateLayout;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by lufficc on 2016/8/26.
 */

public interface ViewAnimProvider {
    void onHideAndShow(@Nullable View willHide, @NonNull View willShow);
}
