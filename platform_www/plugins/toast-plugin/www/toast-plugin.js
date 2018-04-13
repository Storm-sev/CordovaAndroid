cordova.define("toast-plugin.toast-plugin", function(require, exports, module) {
var exec = require('cordova/exec');

exports.showToast = function (arg0, success, error) {
    exec(success, error, 'toast-plugin', 'showToast', [arg0]);
};

});
