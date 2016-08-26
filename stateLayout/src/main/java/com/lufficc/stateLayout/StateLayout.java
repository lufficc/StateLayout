package com.lufficc.stateLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcc.stateLayout.R;


/**
 * Created by lcc_luffy on 2016/1/30.
 */
public class StateLayout extends FrameLayout {
    private View contentView;

    private View emptyView;
    private View emptyContentView;

    private View errorView;
    private View errorContentView;

    private View progressView;
    private View progressContentView;

    private TextView emptyTextView;
    private TextView errorTextView;
    private TextView progressTextView;

    private ImageView errorImageView;
    private ImageView emptyImageView;

    private View currentShowingView;


    public StateLayout(Context context) {
        this(context, null);
    }


    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        parseAttrs(context, attrs);

        emptyView.setVisibility(View.GONE);

        errorView.setVisibility(View.GONE);

        progressView.setVisibility(View.GONE);

        currentShowingView = contentView;
    }

    private void parseAttrs(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StateLayout, 0, 0);
        int progressViewId;
        Drawable errorDrawable;
        Drawable emptyDrawable;
        try {
            errorDrawable = a.getDrawable(R.styleable.StateLayout_errorDrawable);
            emptyDrawable = a.getDrawable(R.styleable.StateLayout_emptyDrawable);
            progressViewId = a.getResourceId(R.styleable.StateLayout_progressView, -1);
        } finally {
            a.recycle();
        }

        /******************************************************************************************/

        if (progressViewId != -1) {
            progressView = inflater.inflate(progressViewId, this, false);
        } else {
            progressView = inflater.inflate(R.layout.view_progress, this, false);
            progressTextView = (TextView) progressView.findViewById(R.id.progressTextView);
            progressContentView = progressView.findViewById(R.id.progress_content);
        }

        addView(progressView);
        /******************************************************************************************/

        /******************************************************************************************/

        errorView = inflater.inflate(R.layout.view_error, this, false);
        errorContentView = errorView.findViewById(R.id.error_content);
        errorTextView = (TextView) errorView.findViewById(R.id.errorTextView);
        errorImageView = (ImageView) errorView.findViewById(R.id.errorImageView);
        if (errorDrawable != null) {
            errorImageView.setImageDrawable(errorDrawable);
        } else {
            errorImageView.setImageResource(R.mipmap.ic_error);
        }
        addView(errorView);
        /******************************************************************************************/

        /******************************************************************************************/

        emptyView = inflater.inflate(R.layout.view_empty, this, false);
        emptyContentView = emptyView.findViewById(R.id.empty_content);
        emptyTextView = (TextView) emptyView.findViewById(R.id.emptyTextView);
        emptyImageView = (ImageView) emptyView.findViewById(R.id.emptyImageView);
        if (emptyDrawable != null) {
            emptyImageView.setImageDrawable(emptyDrawable);
        } else {
            emptyImageView.setImageResource(R.mipmap.ic_empty);
        }
        addView(emptyView);
        /******************************************************************************************/

    }

    private void checkIsContentView(View view) {
        if (contentView == null && view != errorView && view != progressView && view != emptyView) {
            contentView = view;
            currentShowingView = contentView;
        }
    }

    public ImageView getErrorImageView() {
        return errorImageView;
    }

    public ImageView getEmptyImageView() {
        return emptyImageView;
    }


    public ViewAnimProvider getViewSwitchAnimProvider() {
        return viewSwitchAnimProvider;
    }

    public void setViewSwitchAnimProvider(ViewAnimProvider viewSwitchAnimProvider) {
        this.viewSwitchAnimProvider = viewSwitchAnimProvider;
    }

    private ViewAnimProvider viewSwitchAnimProvider;

    private void switchWithAnimation(final View toBeShown) {
        final View toBeHided = currentShowingView;
        if (toBeHided == toBeShown)
            return;

        if (viewSwitchAnimProvider != null) {
            viewSwitchAnimProvider.onHideAndShow(toBeHided,toBeShown);
            currentShowingView = toBeShown;
        } else {
            if (toBeHided != null) {
                toBeHided.setVisibility(GONE);
            }
            if (toBeShown != null) {
                currentShowingView = toBeShown;
                toBeShown.setVisibility(VISIBLE);
            }
        }

    }

    public void setEmptyContentViewMargin(int left, int top, int right, int bottom) {
        ((LayoutParams) emptyContentView.getLayoutParams()).setMargins(left, top, right, bottom);
    }

    public void setErrorContentViewMargin(int left, int top, int right, int bottom) {
        ((LayoutParams) errorContentView.getLayoutParams()).setMargins(left, top, right, bottom);
    }

    public void setProgressContentViewMargin(int left, int top, int right, int bottom) {
        ((LayoutParams) progressContentView.getLayoutParams()).setMargins(left, top, right, bottom);
    }

    public void setInfoContentViewMargin(int left, int top, int right, int bottom) {
        ((LayoutParams) emptyContentView.getLayoutParams()).setMargins(left, top, right, bottom);
        ((LayoutParams) errorContentView.getLayoutParams()).setMargins(left, top, right, bottom);
        ((LayoutParams) progressContentView.getLayoutParams()).setMargins(left, top, right, bottom);
    }


    public void showContentView() {
        switchWithAnimation(contentView);
    }

    public void showEmptyView() {
        showEmptyView(null);
    }

    public void showEmptyView(String msg) {
        onHideContentView();
        if (!TextUtils.isEmpty(msg))
            emptyTextView.setText(msg);
        switchWithAnimation(emptyView);
    }

    public void showErrorView() {
        showErrorView(null);
    }

    public void showErrorView(String msg) {
        onHideContentView();
        if (msg != null)
            errorTextView.setText(msg);
        switchWithAnimation(errorView);
    }

    public void showProgressView() {
        showProgressView(null);
    }

    public void showProgressView(String msg) {

        onHideContentView();
        if (msg != null)
            progressTextView.setText(msg);
        switchWithAnimation(progressView);
    }

    public void setErrorAction(final OnClickListener onErrorButtonClickListener) {
        errorView.setOnClickListener(onErrorButtonClickListener);
    }

    public void setEmptyAction(final OnClickListener onEmptyButtonClickListener) {
        emptyView.setOnClickListener(onEmptyButtonClickListener);
    }


    public void setErrorAndEmptyAction(final OnClickListener errorAndEmptyAction) {
        errorView.setOnClickListener(errorAndEmptyAction);
        emptyView.setOnClickListener(errorAndEmptyAction);
    }

    protected void onHideContentView() {
        //Override me
    }


    /**
     * addView
     */

    @Override
    public void addView(View child) {
        checkIsContentView(child);
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        checkIsContentView(child);
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        checkIsContentView(child);
        super.addView(child, width, height);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        return super.addViewInLayout(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        checkIsContentView(child);
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }
}
