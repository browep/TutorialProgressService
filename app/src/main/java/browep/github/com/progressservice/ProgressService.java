package browep.github.com.progressservice;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by pbrower on 7/23/15.
 */
public class ProgressService extends IntentService {

    private static final String TAG = ProgressService.class.getCanonicalName();

    public static final String START = "START";
    public static final String PROGRESS = "PROGRESS";
    public static final String FINISH = "FINISH";
    public static final IntentFilter LISTEN_FILTER = new IntentFilter(ProgressService.class.getCanonicalName());

    static {
        LISTEN_FILTER.addCategory(START);
        LISTEN_FILTER.addCategory(PROGRESS);
        LISTEN_FILTER.addCategory(FINISH);
    }

    public static final String VALUE = "value";

    public ProgressService() {
        super(ProgressService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent startIntent = new Intent(ProgressService.class.getCanonicalName());
        startIntent.addCategory(START);
        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(startIntent);

        // simulate some blocking work with progress
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(100);
                Intent progressIntent = new Intent(ProgressService.class.getCanonicalName());
                progressIntent.addCategory(PROGRESS);
                progressIntent.putExtra(VALUE, i);
                LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(progressIntent);
            } catch (InterruptedException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        Intent finishIntent = new Intent(ProgressService.class.getCanonicalName());
        finishIntent.addCategory(FINISH);
        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(finishIntent);

    }
}
