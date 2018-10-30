package com.estimote.proximity;

import android.app.Application;

import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class MyApplication extends Application {

    public EstimoteCloudCredentials cloudCredentials =
            new EstimoteCloudCredentials("moxdbeacons-gmail-com-s-pr-a01", "28c32e885374ea4c4a298c38a5a6b18c");
}
