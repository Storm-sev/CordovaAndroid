cordova.define("cordova-toast-plugin.ToastPlugin", function(require, exports, module) {
var exec = require('cordova/exec');

/**
* ToastPlugin : 是在plugin.xml中配置的feature的name
* showToast : 是js 中调用的方法名字 
*/
exports.showToast = function (arg0, success, error) {
    exec(success, error, 'ToastPlugin', 'showToast', [arg0]);
};

});
