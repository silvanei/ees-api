(function () {
    'use strict';

    angular
        .module('agenda', ['ui.router', 'ui.calendar', 'ngMessages', 'ngStorage'])
        .run(function ($rootScope, $location, authenticationService) {

            $rootScope.$on('$locationChangeStart', function (event, next, current) {

                if (next.authorize) {
                    if (!authenticationService.getToken()) {
                        $rootScope.$evalAsync(function () {
                            $location.path('/login');
                        })
                    }
                }
            });
        })
    ;
})();