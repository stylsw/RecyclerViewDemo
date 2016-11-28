package com.fingertip.recyclerviewdemo.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by sweet on 2016/11/27.
 */

public class RecyclerDividerItemDecoration extends RecyclerView.ItemDecoration {
    public static final int DIVIDER_NONE = 0;

    public static final int DIVIDER_HORIZONTAL = 1;

    public static final int DIVIDER_VERTICAL = 2;

    public static final int DIVIDER_ALL = 3;

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;

    private int mOrientation;

    private final Rect mBounds = new Rect();

    private Context mContext;

    private int mMarginLeft = 0;
    private int mMarginRight = 0;
    private int mMarginTop = 0;
    private int mMarginBottom = 0;
    private float density;

    public RecyclerDividerItemDecoration(Context context, int orientation) {
        mContext = context;
        density = context.getResources().getDisplayMetrics().density;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        mOrientation = orientation;
    }

    public void setDrawableResource(int drawableId) {
        setDrawable(ContextCompat.getDrawable(mContext, drawableId));
    }

    public void setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = drawable;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null)
            return;
        checkOrientationExpetion(parent);
        if (isLinearLayoutManager(parent)) {
            switch (mOrientation) {
                case DIVIDER_HORIZONTAL:
                    onDrawHorizontal(c, parent);
                    break;
                case DIVIDER_VERTICAL:
                    onDrawVertical(c, parent);
                    break;
            }
        } else {
            switch (mOrientation) {
                case DIVIDER_NONE:
                    break;
                case DIVIDER_HORIZONTAL:
                    onDrawHorizontal(c, parent);
                    break;
                case DIVIDER_VERTICAL:
                    onDrawVertical(c, parent);
                    break;
                case DIVIDER_ALL:
                    onDrawVertical(c, parent);
                    drawVertical(c,parent);
                    onDrawHorizontal(c, parent);
                    drawHorizontal(c,parent);
                    break;
            }
        }
    }
    private void drawVertical(Canvas canvas, RecyclerView parent){
        canvas.save();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (isLastRow(parent.getChildAdapterPosition(child), parent)) {
                continue;
            }

            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int left = mBounds.left  + mMarginLeft;
            final int right = mBounds.right - mMarginRight;
            final int top = mBounds.top + mMarginTop;
            final int bottom =  mBounds.bottom - mMarginBottom;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    public void onDrawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();

        final int left = parent.getPaddingLeft() + mMarginLeft;
        final int right = parent.getWidth() - parent.getPaddingRight() - mMarginRight;

        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (isLastRow(parent.getChildAdapterPosition(child), parent)) {
                continue;
            }

            parent.getDecoratedBoundsWithMargins(child, mBounds);

            final int bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child)) - mMarginBottom;
            final int top = bottom - mDivider.getIntrinsicHeight() + mMarginTop;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent){
        canvas.save();


        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (isLastColum(parent.getChildAdapterPosition(child), parent)) {
                continue;
            }
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int left = mBounds.right  - mDivider.getIntrinsicWidth() + mMarginLeft;
            final int right = mBounds.right - mMarginRight;
            final int top = mBounds.top + mMarginTop;
            final int bottom =  mBounds.bottom - mMarginBottom;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }
    public void onDrawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();

        final int top = parent.getPaddingTop() + mMarginTop;
        final int bottom = parent.getHeight() - parent.getPaddingBottom() - mMarginBottom;

        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (isLastColum(parent.getChildAdapterPosition(child), parent)) {
                continue;
            }
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(ViewCompat.getTranslationX(child)) - mMarginRight;
            final int left = right - mDivider.getIntrinsicWidth() + mMarginLeft;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    public RecyclerDividerItemDecoration setMarginLeft(int mMarginLeft) {
        checkMarginForHorizontalExpetion();
        this.mMarginLeft = dipTopx(mMarginLeft);
        return this;
    }

    public RecyclerDividerItemDecoration setMarginTop(int mMarginTop) {
        checkMarginForVerticalExpetion();
        this.mMarginTop = dipTopx(mMarginTop);
        return this;
    }

    public RecyclerDividerItemDecoration setMarginRight(int mMarginRight) {
        checkMarginForHorizontalExpetion();
        this.mMarginRight = dipTopx(mMarginRight);
        return this;
    }

    public RecyclerDividerItemDecoration setMarginBottom(int mMarginBottom) {
        checkMarginForVerticalExpetion();
        this.mMarginBottom = dipTopx(mMarginBottom);
        return this;
    }

    private void checkMarginForVerticalExpetion() {
        if (mOrientation == DIVIDER_VERTICAL) {
            throw new IllegalStateException("Orientation is DIVIDER_VERTICAL ,it cannot setMarginTop or setMarginBottom.");
        }
    }

    private void checkMarginForHorizontalExpetion() {
        if (mOrientation == DIVIDER_HORIZONTAL) {
            throw new IllegalStateException("Orientation is DIVIDER_HORIZONTAL ,it cannot setMarginLeft or setMarginRight.");
        }
    }

    private void checkOrientationExpetion(RecyclerView parent) {
        if (mOrientation == DIVIDER_VERTICAL) {
            if (isLinearLayoutManager(parent)) {
                if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    throw new IllegalStateException("LinearLayoutManager Orientation is LinearLayoutManager.HORIZONTAL,cannot set Orientation DIVIDER_VERTICAL");
                }
            }
        } else if (mOrientation == DIVIDER_HORIZONTAL) {
            if (isLinearLayoutManager(parent)) {
                if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayoutManager.VERTICAL) {
                    throw new IllegalStateException("LinearLayoutManager Orientation is LinearLayoutManager.VERTICAL,cannot set Orientation DIVIDER_HORIZONTAL");
                }
            }
        }
    }

    private boolean isLinearLayoutManager(RecyclerView parent) {
        return parent.getLayoutManager().getClass() == LinearLayoutManager.class;
    }

    public void setMargin(int mMarginLeft, int mMarginTop, int mMarginRight, int mMarginBottom) {
        setMarginLeft(mMarginLeft);
        setMarginTop(mMarginTop);
        setMarginRight(mMarginRight);
        setMarginBottom(mMarginBottom);
    }

    private int dipTopx(int dip) {
        return (int) (density * dip + 0.5f);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null)
            return;
        int itemPosition = parent.getChildAdapterPosition(view);

        switch (mOrientation) {
            case DIVIDER_NONE:
                break;
            case DIVIDER_HORIZONTAL:
                if (!isLastColum(itemPosition, parent))
                    outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
                break;
            case DIVIDER_VERTICAL:
                if (!isLastRow(itemPosition, parent))
                    outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
                break;
            case DIVIDER_ALL:
                if (!isLastColum(itemPosition, parent))
                    outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
                else if (!isLastRow(itemPosition, parent)) {
                    outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
                } else {
                    outRect.set(0, 0, 0, 0);
                }
                break;
        }
    }

    private boolean isLastColum(int itemPosition, RecyclerView parent) {
        int columCount = getColumnCount(parent);

        return (itemPosition + 1) % columCount == 0;
    }

    private boolean isLastRow(int itemPosition, RecyclerView parent) {
        int columCount = getColumnCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int temp = 0;

        if (childCount % columCount == 0) {
            temp = columCount;
        } else {
            temp = childCount % columCount;
        }

        return itemPosition >= childCount - temp;
    }

    private int getColumnCount(RecyclerView parent) {
        // 列数
        int columnCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
//        if (layoutManager instanceof GridLayoutManager) {
//            columnCount = ((GridLayoutManager) layoutManager).getSpanCount();
//        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
//            columnCount = ((StaggeredGridLayoutManager) layoutManager)
//                    .getSpanCount();
//        } else if (layoutManager instanceof LinearLayoutManager) {
//            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL) {
//                columnCount = layoutManager.getItemCount();
//            } else {
//                columnCount = 1;
//            }
//        }
        if (isLinearLayoutManager(parent)) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                columnCount = layoutManager.getItemCount();
            } else {
                columnCount = 1;
            }
        } else {
            if (layoutManager instanceof GridLayoutManager) {
                columnCount = ((GridLayoutManager) layoutManager).getSpanCount();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                columnCount = ((StaggeredGridLayoutManager) layoutManager)
                        .getSpanCount();
            }
        }
        return columnCount;
    }

}
