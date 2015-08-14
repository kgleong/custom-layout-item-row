package com.codepath.uncooperativelistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MyCustomLayout extends ViewGroup {
    View leftChild;
    View rightChild;
    CustomLayoutParams leftChildLayoutParams;
    CustomLayoutParams rightChildLayoutParams;

    public MyCustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();

        int widthUsed =  getPaddingRight() + getPaddingLeft();
        int heightUsed = getPaddingTop() + getPaddingBottom();

        if(childCount == 2) {
            for(int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                CustomLayoutParams layoutParams = (CustomLayoutParams) child.getLayoutParams();

                switch(layoutParams.position) {
                    case CustomLayoutParams.POSITION_LEFT:
                        leftChild = child;
                        break;
                    case CustomLayoutParams.POSITION_RIGHT:
                        rightChild = child;
                        break;
                }
            }

            rightChildLayoutParams = (CustomLayoutParams) rightChild.getLayoutParams();
            widthUsed += rightChildLayoutParams.leftMargin + rightChildLayoutParams.rightMargin;

            measureChildWithMargins(rightChild, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);

            leftChildLayoutParams = (CustomLayoutParams) leftChild.getLayoutParams();

            widthUsed += rightChild.getMeasuredWidth();
            widthUsed += leftChildLayoutParams.leftMargin + leftChildLayoutParams.rightMargin;

            measureChildWithMargins(leftChild, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);

            widthUsed += leftChild.getMeasuredWidth();
            heightUsed += Math.max(leftChild.getMeasuredHeight(), rightChild.getMeasuredHeight());
        }
        else {
            throw new RuntimeException("MyCustomLayout must have two children");
        }

        int measuredWidth = resolveSize(widthUsed, widthMeasureSpec);
        int measuredHeight = resolveSize(heightUsed, heightMeasureSpec);

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // Place left hand side view
        int left = getPaddingLeft() + leftChildLayoutParams.leftMargin;
        int top = getPaddingTop() + leftChildLayoutParams.topMargin;
        int right = left + leftChild.getMeasuredWidth();
        int bottom = top + leftChild.getMeasuredHeight();

        leftChild.layout(left, top, right, bottom);

        // Place right hand side view
        left = right + rightChildLayoutParams.leftMargin;
        top = getPaddingTop() + rightChildLayoutParams.topMargin;
        right = left + rightChild.getMeasuredWidth();
        bottom = top + rightChild.getMeasuredHeight();

        rightChild.layout(left, top, right, bottom);
    }

    // This method must be overridden when the view group is not scrollable.
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParams(getContext(), attrs);
    }

    public static class CustomLayoutParams extends MarginLayoutParams {
        public static final int POSITION_LEFT = 0;
        public static final int POSITION_RIGHT = 1;

        public int position = POSITION_LEFT;

        public CustomLayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomLayoutParams);
            position = typedArray.getInt(R.styleable.CustomLayoutParams_layout_position, position);
        }
    }
}

