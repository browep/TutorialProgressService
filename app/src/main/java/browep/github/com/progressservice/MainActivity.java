package browep.github.com.progressservice;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    public static final String DIALOG_TAG = "dialog";
    private static final String TAG = MainActivity.class.getCanonicalName();
    private BroadcastReceiver progressReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new MainActivityFragment());
            fragmentTransaction.commit();
        }

        progressReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ProgressDialogFragment progressDialogFragment = null;

                if (getFragmentManager().findFragmentByTag(DIALOG_TAG) == null) {
                    progressDialogFragment = new ProgressDialogFragment();
                    progressDialogFragment.show(getFragmentManager(), DIALOG_TAG);
                } else {
                    progressDialogFragment = (ProgressDialogFragment) getFragmentManager().findFragmentByTag(DIALOG_TAG);
                }

                if (intent.getCategories().contains(ProgressService.PROGRESS)) {
                    progressDialogFragment.updateProgress(intent.getIntExtra(ProgressService.VALUE, 0));
                }

                if (intent.getCategories().contains(ProgressService.FINISH)) {
                    progressDialogFragment.dismiss();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(progressReceiver, ProgressService.LISTEN_FILTER);
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(progressReceiver);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }

}
