package com.example.android.bakingapp.idlingRes;

import android.os.Handler;
import android.support.annotation.Nullable;

public class TestDelayer {
    private static final int DELAY_MILLIS = 3000;

    public interface DelayerCallback {
        void onDone();
    }

    public static void processTest(
            @Nullable final SimpleIdlingResource idlingResource) {
        // The IdlingResource is null in production.
        if (idlingResource != null) {
            idlingResource.setIdlingState(false);//False means phone is not idle(it keeps doing work in background)
        }

        // Delay the execution, return message via callback.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (idlingResource != null) {
                    idlingResource.setIdlingState(true);
                }

            }
        }, DELAY_MILLIS);
    }
}
