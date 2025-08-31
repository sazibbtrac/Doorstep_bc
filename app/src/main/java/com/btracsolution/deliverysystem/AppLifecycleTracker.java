package com.btracsolution.deliverysystem;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.btracsolution.deliverysystem.Utility.AgentGlobal;
import com.btracsolution.deliverysystem.Utility.RiderGlobal;

public class AppLifecycleTracker  implements Application.ActivityLifecycleCallbacks {

    private int activityCount = 0;

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityCount--;
        if (activityCount == 0) {
            AgentGlobal.isAgentActivityOpen = false;
            RiderGlobal.isAgentActivityOpen = false;
            AgentGlobal.isAgentActivityOpenWaiter = false;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

    @Override
    public void onActivityResumed(Activity activity) {
        activityCount++;
        AgentGlobal.isAgentActivityOpen = activityCount > 0;
        RiderGlobal.isAgentActivityOpen = activityCount >0;
        AgentGlobal.isAgentActivityOpenWaiter = activityCount >0;
    }

    @Override
    public void onActivityPaused(Activity activity) {}

    @Override
    public void onActivityDestroyed(Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
}

