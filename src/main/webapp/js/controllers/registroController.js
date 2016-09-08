/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('registroController', ['$scope', 'registroService', 'Notification', '$rootScope', '$location', '$log',
            function ($scope, registroService, Notification, $rootScope, $location, $log) {

                $scope.registrar = function(registro) {

                    registroService.post(registro).success(function(data) {
                        $scope.registroForm.$setPristine();

                        Notification.success('Registro salvo com sucesso');

                        $rootScope.$evalAsync(function () {
                            $location.path('/login');
                        });

                    }).error(function(data, status) {
                        $log.error(data);
                        Notification.error(data.errorMessage);
                    });
                };

            }
        ])
    ;

})();