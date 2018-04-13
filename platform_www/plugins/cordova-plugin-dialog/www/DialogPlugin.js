cordova.define("cordova-plugin-dialog.DialogPlugin", function(require, exports, module) {
var exec = require('cordova/exec');

/**
*  DialogPlugin: 是在plugin.xml 中配置的frature 的name
*  showDialog : 是js 中调用的方法
*/
exports.showDialog = function (arg0, success, error) {
    exec(success, error, 'DialogPlugin', 'showDialog', [arg0]);
};

exports.showNoClickListener = function (arg0.success,error) {
    exec(success,error,"DialogPlugin",'showNoClickListener',[arg0]);
};

});
