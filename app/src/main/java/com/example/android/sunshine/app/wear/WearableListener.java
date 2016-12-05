package com.example.android.sunshine.app.wear;

import android.util.Log;

import com.example.android.sunshine.app.sync.SunshineSyncAdapter;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by isse on 16/11/2016.
 * Because the data layer synchronizes and sends data across the handheld and wearable,
 * it is usually necessary to listen for important events. Examples of such events include
 * creation of data items and receipt of messages.
 *
 *
 * You typically create instances of this service in both your wearable and handheld apps.
 * If you are not interested in data events in one of these apps, then you don't need to
 * implement this service in that particular app.
 * For example, you can have a handheld app that sets and gets data item objects and a wearable app
 * that listens for these updates to update its UI. The wearable never updates any of the data items,
 * so the handheld app doesn't listen for any data events from the wearable app.
 *
 * More info: https://developer.android.com/training/wearables/data-layer/events.html
 */

public class WearableListener extends WearableListenerService {
    private static final String LOG_TAG = WearableListener.class.getSimpleName();

    private static final String WEATHER_PATH = "/weather";

    private static final String START_ACTIVITY_PATH = "/start-activity";



    /*
    * Whenever a data item object is created, deleted, or changed,
    * the system triggers this callback on all connected nodes.
    * */
    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        if (Log.isLoggable(LOG_TAG, Log.DEBUG)) {
            Log.d(LOG_TAG, "onDataChanged: " + dataEventBuffer);
        }


        // Loop through the events and send data item
        // to the node that created the data item.
        for (DataEvent dataEvent : dataEventBuffer) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                String path = dataEvent.getDataItem().getUri().getPath();
                Log.d(LOG_TAG, path);
                if (path.equals(WEATHER_PATH)) {
                    SunshineSyncAdapter.syncImmediately(this);
                }
            }
        }
    }




}
