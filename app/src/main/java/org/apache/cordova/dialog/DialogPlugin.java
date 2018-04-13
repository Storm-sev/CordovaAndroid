package org.apache.cordova.dialog;

import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class DialogPlugin extends CordovaPlugin {

    private static final String TAG = "DialogPlugin";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("showDialog")) {
            String message = args.getString(0);
            this.showDialog(message, callbackContext);
            return true;
        }
        if (action.equals("showNoClickListener")) {
            String content = args.getString(0);
            this.showNoClickListener(content, callbackContext);
            return true;
        }

        return false;
    }

    private void showNoClickListener(String content, CallbackContext callbackContext) {

        if (content != null && content.length() > 0) {
            callbackContext.success(content);
            Log.d(TAG, "一般特殊dialog");
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void showDialog(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
            Log.d(TAG, "一般调用dialog");
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
