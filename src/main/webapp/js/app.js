(function () {
    'use strict';

    angular
        .module('agenda', ['ui.router', 'ui.calendar', 'ngMessages', 'ngStorage', 'ui.bootstrap', 'ng-currency', 'ngMask', 'ui-notification'])
        .run(function (authManagerService) {

            authManagerService.checkAuthOnRefresh();
        })
    ;
})();

//$(document).ready(function () {
//    $('body').bind('cut copy paste', function (e) {
//        e.preventDefault();
//    });
//});