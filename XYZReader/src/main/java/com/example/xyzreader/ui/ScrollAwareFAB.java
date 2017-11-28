package com.example.xyzreader.ui;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by jackb on 11/27/2017.
 *
 * Some code adapted from example from Ian Lake's post
 * https://medium.com/google-developers/intercepting-everything-with-coordinatorlayout-behaviors-8c6adc140c26
 */

public class ScrollAwareFAB extends CoordinatorLayout.Behavior<FloatingActionButton> {

    /**
     * Default constructor for instantiating a ScrollAwareFAB in code.
     */
    public ScrollAwareFAB() {
    }
    /**
     * Default constructor for inflating a ScrollAwareFAB from layout.
     *
     * @param context The {@link Context}.
     * @param attrs The {@link AttributeSet}.
     */
    public ScrollAwareFAB(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Extract any custom attributes out
        // preferably prefixed with behavior_ to denote they
        // belong to a behavior

    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {

        //return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        // Ensure we react to vertical scrolling
        //JB: Why do we call super here?
        //return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
        //        || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);

        return (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        if (dyConsumed > 0) {
            child.setVisibility(View.INVISIBLE);
        } else if (dyConsumed < 0) {
            child.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, final FloatingActionButton child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        Log.e(TAG, "onStopNestedScroll");

        final ObservableScrollView scrollView = (ObservableScrollView) target;
        scrollView.setCallbacks(new ObservableScrollView.Callbacks() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();

                // If bottom reached
                if (scrollY == ((scrollView.getChildAt(0).getMeasuredHeight()) - scrollView.getMeasuredHeight())) {
                    child.setVisibility(View.VISIBLE);
                }

            }
        });
    }
}
