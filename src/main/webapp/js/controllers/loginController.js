/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('loginController', ['$scope', 'authenticationService', '$location', 'Notification',
            function ($scope, authenticationService, $location, Notification) {

                authenticationService.logout();

                $scope.login = function(user) {
                    $scope.loading = true;
                    authenticationService.login(user.email, user.password, function (result) {
                        if (result === true) {
                            $location.path('/');
                        } else {
                            Notification.error('E-mail ou senha incoreto');
                            $scope.loading = false;
                        }
                    });
                }

            }]
        );
})();