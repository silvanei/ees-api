/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('loginController', ['$scope', 'authenticationService', '$location',
            function ($scope, authenticationService, $location) {

                authenticationService.logout();

                $scope.login = function() {
                    authenticationService.login($scope.user.email, $scope.user.password, function (result) {
                        $scope.loading = true;
                        if (result === true) {
                            $location.path('/');
                        } else {
                            $scope.error = 'E-mail ou senha incoreto.';
                            $scope.loading = false;
                        }
                    });
                }

            }]
        );
})();