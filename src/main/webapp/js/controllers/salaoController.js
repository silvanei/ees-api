/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('salaoController', ['$scope', 'salaoService', 'Notification', '$log',
            function ($scope, salaoService, Notification, $log) {

                function init() {
                    $('.nav-tabs a').click(function (e) {
                        e.preventDefault();
                        $(this).tab('show');
                    });

                    salaoService.get().success(function(data) {
                        $log.log(data);
                        $scope.dados = data;

                    }).error(function(data, status) {
                        $log.error(data);
                        Notification.error(data.errorMessage);
                    });
                }

                $scope.atualizarDados = function(dados) {
                    dados.endereco.estado = 1;
                    dados.endereco.cidade = 1;
                    dados.endereco.bairro = 1;
                    dados.endereco.cep = 83406310;

                    salaoService.put(dados).success(function(data) {
                        $scope.dados = data;
                        Notification.success('Dados atualizado com sucesso');

                    }).error(function(data, status) {
                        $log.error(data);
                        Notification.error(data.errorMessage);
                    });
                };

                init();
            }
        ])
    ;

})();