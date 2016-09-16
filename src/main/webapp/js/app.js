(function () {
    'use strict';

    angular
        .module('agenda', [
            'ui.router',
            'ui.calendar',
            'ngMessages',
            'ngStorage',
            'ui.bootstrap',
            'ng-currency',
            'ngMask',
            'ui-notification',
            'angular-loading-bar',
            'ngSanitize',
            'ui.select',
            'angular-jwt'
        ]).run(function (authManagerService) {
            authManagerService.checkAuthOnRefresh();
        })
    ;
})();