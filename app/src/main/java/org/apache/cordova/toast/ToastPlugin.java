package org.apache.cordova.toast;

import android.util.Log;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class ToastPlugin extends CordovaPlugin {


      private static final String TAG = "ToastPlugin";

      @Override
      public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
          if (action.equals("showToast")) {
              String mage = args.getString(0);
              this.showToast(mage, callbackContext);
              return true;
          }
          return false;
      }

      /**
       * 显示toast
       */
      private void showToast(String message, CallbackContext callbackContext) {
          if (message != null && message.length() > 0) {
              callbackContext.success(message);
              Log.d(TAG, "js 中调用本例子代码");
              Toast.makeText(cordova.getActivity(), message, Toast.LENGTH_LONG).show();
          } else {
              Toast.makeText(cordova.getActivity(), "未成功返回", Toast.LENGTH_LONG).show();
              callbackContext.error("Expected one non-empty string argument.");
          }
      }
}
