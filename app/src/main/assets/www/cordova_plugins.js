cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
  {
    "id": "cordova-plugin-camera.Camera",
    "file": "plugins/cordova-plugin-camera/www/CameraConstants.js",
    "pluginId": "cordova-plugin-camera",
    "clobbers": [
      "Camera"
    ]
  },
  {
    "id": "cordova-plugin-camera.CameraPopoverOptions",
    "file": "plugins/cordova-plugin-camera/www/CameraPopoverOptions.js",
    "pluginId": "cordova-plugin-camera",
    "clobbers": [
      "CameraPopoverOptions"
    ]
  },
  {
    "id": "cordova-plugin-camera.camera",
    "file": "plugins/cordova-plugin-camera/www/Camera.js",
    "pluginId": "cordova-plugin-camera",
    "clobbers": [
      "navigator.camera"
    ]
  },
  {
    "id": "cordova-plugin-camera.CameraPopoverHandle",
    "file": "plugins/cordova-plugin-camera/www/CameraPopoverHandle.js",
    "pluginId": "cordova-plugin-camera",
    "clobbers": [
      "CameraPopoverHandle"
    ]
  },
  {
    "id": "cordova-toast-plugin.ToastPlugin",
    "file": "plugins/cordova-toast-plugin/www/ToastPlugin.js",
    "pluginId": "cordova-toast-plugin",
    "clobbers": [
      "navigator.toast"
    ]
  },
  {
    "id": "cordova-plugin-dialog.DialogPlugin",
    "file": "plugins/cordova-plugin-dialog/www/DialogPlugin.js",
    "pluginId": "cordova-plugin-dialog",
    "clobbers": [
      "navigator.dialog"
    ]
  }
];
module.exports.metadata = 
// TOP OF METADATA
{
  "cordova-plugin-whitelist": "1.3.3",
  "cordova-plugin-camera": "3.0.0",
  "cordova-toast-plugin": "1.0.0",
  "cordova-plugin-dialog": "1.0.0"
};
// BOTTOM OF METADATA
});