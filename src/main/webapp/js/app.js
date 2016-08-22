(function () {
    'use strict';

    angular
        .module('agenda', ['ui.router', 'ui.calendar', 'ngMessages', 'ngStorage', 'ui.bootstrap', 'ng-currency', 'ngMask'])
        .run(function (authManagerService) {

            authManagerService.checkAuthOnRefresh();
        })
    ;
})();