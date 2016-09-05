/**
 * Created by silvanei on 21/08/16.
 */


(function () {
    'use strict';

    angular
        .module('agenda')
        .factory('authManagerService', ['$rootScope', '$location', 'authenticationService', 'jwtHelperService',
            function($rootScope, $location, authenticationService, jwtHelperService) {

                $rootScope.isAuthenticated = false;

                function authenticate() {
                    $rootScope.isAuthenticated = true;
                }

                function unauthenticate() {
                    $rootScope.isAuthenticated = false;
                }

                function checkAuthOnRefresh() {
                    $rootScope.$on('$locationChangeStart', function (event, next, current) {
                        if (next.substr(next.length - 8) == 'registro') {
                            return;
                        }

                        if (next.substr(next.length - 15) == 'recuperar-senha') {
                            return;
                        }

                        var token = authenticationService.getToken();

                        if (!token || jwtHelperService.isTokenExpired(token)) {
                            unauthenticate();
                            $rootScope.$evalAsync(function () {
                                $location.path('/login');
                            });
                        } else {
                            authenticate();
                        }
                    });
                }

                function identity() {

                    var token = authenticationService.getToken();
                    var decodeToken = jwtHelperService.decodeToken(token);

                    return {
                        salaoId: decodeToken.sla,
                        perfil: decodeToken.per
                    }
                }

                return {
                    authenticate: authenticate,
                    unauthenticate: unauthenticate,
                    checkAuthOnRefresh: checkAuthOnRefresh,
                    identity: identity
                }
            }
        ])
    ;

})();