package com.estimote.proximity;

import android.app.Application;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.service.BeaconManager;
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;

import java.util.UUID;

public class MyApplication extends Application {

    public EstimoteCloudCredentials cloudCredentials =
            new EstimoteCloudCredentials("moxdbeacons-gmail-com-s-pr-a01", "28c32e885374ea4c4a298c38a5a6b18c");

/*    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new BeaconRegion(
                        "monitored region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        22504, 48827));
            }
        });
    }*/
}
