package com.estimote.proximity.estimote;


import android.content.Context;
import android.util.Log;

import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;

import com.estimote.coresdk.service.BeaconManager;
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

    private Context context;
    private ProximityContentAdapter proximityContentAdapter;
    private EstimoteCloudCredentials cloudCredentials;
    private ProximityObserver.Handler proximityObserverHandler;

    private BeaconManager beaconManager;

    private String title, subtitle;

    public ProximityContentManager(Context context, ProximityContentAdapter proximityContentAdapter, EstimoteCloudCredentials cloudCredentials) {
        this.context = context;
        this.proximityContentAdapter = proximityContentAdapter;
        this.cloudCredentials = cloudCredentials;
    }

    public void start() {

        beaconManager = new BeaconManager(context);

        ProximityObserver proximityObserver = new ProximityObserverBuilder(context, cloudCredentials)
                .onError(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Log.e("app", "proximity observer error: " + throwable);
                        return null;
                    }
                })
                .withBalancedPowerMode()
                .build();

        ProximityZone zone3 = new ProximityZoneBuilder()
                .forTag("moxdbeacons-gmail-com-s-pr-a01")
                .inCustomRange(3)
                .onContextChange(new Function1<Set<? extends ProximityZoneContext>, Unit>() {
                    @Override
                    public Unit invoke(Set<? extends ProximityZoneContext> contexts) {

                        List<ProximityContent> nearbyContent = new ArrayList<>();

                        for (ProximityZoneContext proximityContext : contexts) {

                            beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
                                @Override
                                public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<com.estimote.coresdk.recognition.packets.Beacon> beacons) {
                                    if (!beacons.isEmpty()) {
                                        subtitle = String.format("%d", beacons.get(0).getRssi()) + String.format("%s", RegionUtils.computeAccuracy(beacons.get(0))) ;
                                    }
                                }
                            });

                            title = proximityContext.getAttachments().get("title");
                            if (title == null) {
                                title = "unknown";
                            }
                            //String subtitle = Utils.getShortIdentifier(proximityContext.getDeviceId());
                            //subtitle = "~1m entfernt";

                            nearbyContent.add(new ProximityContent(title, subtitle));
                        }

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .build();

/*        ProximityZone mintClose = new ProximityZoneBuilder()
                .forTag("mint")
                .inNearRange()
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {
                        String title = context.getAttachments().get("title");
                        if (title == null) {
                            title = "unknown";
                        }
                        String subtitle = "~ 1 meter (sehr Nah)";

                        ProximityContent mintDistance = new ProximityContent(title, subtitle);
                        nearbyContent.add(0, mintDistance);

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {

                        nearbyContent.remove(0);

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .build();

        ProximityZone mintFar = new ProximityZoneBuilder()
                .forTag("mint")
                .inFarRange()
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {
                        String title = context.getAttachments().get("title");
                        if (title == null) {
                            title = "unknown";
                        }
                        String subtitle = "~ 5 meter (in der Umgebung)";

                        ProximityContent mintDistance = new ProximityContent(title, subtitle);
                        nearbyContent.add(0, mintDistance);

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {

                        nearbyContent.remove(0);

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .build();

        ProximityZone iceClose = new ProximityZoneBuilder()
                .forTag("ice")
                .inNearRange()
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {
                        String title = context.getAttachments().get("title");
                        if (title == null) {
                            title = "unknown";
                        }
                        String subtitle = "~ 1 meter (sehr Nah)";

                        ProximityContent mintDistance = new ProximityContent(title, subtitle);
                        nearbyContent.add(1, mintDistance);

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {

                        nearbyContent.remove(1);

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .build();

        ProximityZone iceFar = new ProximityZoneBuilder()
                .forTag("ice")
                .inFarRange()
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {
                        String title = context.getAttachments().get("title");
                        if (title == null) {
                            title = "unknown";
                        }
                        String subtitle = "~ 5 meter (in der Umgebung)";

                        ProximityContent mintDistance = new ProximityContent(title, subtitle);
                        nearbyContent.add(1, mintDistance);

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {

                        nearbyContent.remove(1);

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .build();

        ProximityZone blueClose = new ProximityZoneBuilder()
                .forTag("blueberry")
                .inNearRange()
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {
                        String title = context.getAttachments().get("title");
                        if (title == null) {
                            title = "unknown";
                        }
                        String subtitle = "~ 1 meter (sehr Nah)";

                        ProximityContent mintDistance = new ProximityContent(title, subtitle);
                        nearbyContent.add(2, mintDistance);

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {

                        nearbyContent.remove(2);

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .build();

        ProximityZone blueFar = new ProximityZoneBuilder()
                .forTag("blueberry")
                .inFarRange()
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {
                        String title = context.getAttachments().get("title");
                        if (title == null) {
                            title = "unknown";
                        }
                        String subtitle = "~ 5 meter (in der Umgebung)";

                        ProximityContent mintDistance = new ProximityContent(title, subtitle);
                        nearbyContent.add(2, mintDistance);

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {

                        nearbyContent.remove(2);

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .build();*/

        proximityObserverHandler = proximityObserver.startObserving(zone3);
    }

    public void stop() {
        proximityObserverHandler.stop();
    }
}
