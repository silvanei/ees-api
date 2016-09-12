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
            'ui.select'
        ]).run(function (authManagerService) {
            authManagerService.checkAuthOnRefresh();
        })
    ;
})();