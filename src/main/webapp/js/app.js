(function () {
    'use strict';

    angular
        .module('agenda', ['ui.router', 'ui.calendar', 'ngMessages', 'ngStorage', 'ui.bootstrap'])
        .run(function (authManagerService) {

            authManagerService.checkAuthOnRefresh();
        })
    ;
})();