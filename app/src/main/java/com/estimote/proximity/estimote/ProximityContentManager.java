package com.estimote.proximity.estimote;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.estimote.proximity.DetailActivity;
import com.estimote.proximity.MainActivity;
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ProximityContentManager {

    private static final String TAG = "ProximityContentManager";

    private Context context;
    private ProximityContentAdapter proximityContentAdapter;
    private EstimoteCloudCredentials cloudCredentials;
    private ProximityObserver.Handler proximityObserverHandler;

    private String title, subtitle;

    public ProximityContentManager(Context context, ProximityContentAdapter proximityContentAdapter, EstimoteCloudCredentials cloudCredentials) {
        this.context = context;
        this.proximityContentAdapter = proximityContentAdapter;
        this.cloudCredentials = cloudCredentials;
    }

    public void start() {

        final ProximityObserver proximityObserver = new ProximityObserverBuilder(context, cloudCredentials)
                .onError(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Log.e(TAG, "proximity observer error: " + throwable);
                        return null;
                    }
                })
                .withBalancedPowerMode()
                .withEstimoteSecureMonitoringDisabled()
                .withTelemetryReportingDisabled()
                .build();

        ProximityZone zonefar = new ProximityZoneBuilder()
                .forTag("moxdbeacons-gmail-com-s-pr-a01")
                .inCustomRange(5)
                .onContextChange(new Function1<Set<? extends ProximityZoneContext>, Unit>() {
                    @Override
                    public Unit invoke(Set<? extends ProximityZoneContext> contexts) {

                        Log.d(TAG, "farzone");

                        List<ProximityContent> nearbyContent = new ArrayList<>();

                        for (ProximityZoneContext proximityContext : contexts) {

                            subtitle = "~5m entfernt";

                            title = proximityContext.getAttachments().get("title");
                            if (title == null) {
                                title = "unknown";
                            }
                            nearbyContent.add(new ProximityContent(title, subtitle));
                        }

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .build();

        ProximityZone ice = new ProximityZoneBuilder()
                .forTag("ice")
                .inNearRange()
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        Log.d(TAG, "ice enter");

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("title", proximityContext.getAttachments().get("title"));
                        intent.putExtra("subtitle", "Hier könnten eine Detailübersicht sein");



                        context.startActivity(intent);
                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        Log.d(TAG, "ice exit");
                        context.startActivity(new Intent(context, MainActivity.class));
                        return null;
                    }
                })
                .build();

        proximityObserverHandler = proximityObserver.startObserving(zonefar, ice);
    }

    public void stop() {
        proximityObserverHandler.stop();
    }
}
