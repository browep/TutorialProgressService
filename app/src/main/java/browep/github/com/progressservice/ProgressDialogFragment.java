package browep.github.com.progressservice;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by pbrower on 7/23/15.
 */
public class ProgressDialogFragment extends DialogFragment {

    private ProgressDialog progressDialog;

    public ProgressDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Working...");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public void updateProgress(int progress) {
        progressDialog.setProgress(progress);
    }
}
