package com.example.appmobile;

import android.os.Handler;

public class Updater {
    private Handler handler;
    private Runnable runnable;
    private boolean isRunning;
    private ApiHelper.ApiCallback callback;

    public Updater() {
        handler = new Handler();
    }

    public void startUpdating(ApiHelper.ApiCallback callback) {
        this.callback = callback;
        if (isRunning) {
            return;
        }
        isRunning = true;
        runnable = new Runnable() {
            @Override
            public void run() {
                ApiHelper.select(new ApiHelper.ApiCallback() {
                    @Override
                    public void onResult(String result) {
                        if (callback != null) {
                            callback.onResult(result);
                        }
                    }
                });
                handler.postDelayed(this, 1000); // 1000ms = 1sec
            }
        };
        handler.post(runnable);
    }

    public void stopUpdating() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
        handler.removeCallbacks(runnable);
    }
}
