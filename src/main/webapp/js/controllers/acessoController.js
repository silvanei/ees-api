/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('acessoController', ['$scope', 'salaoService', 'profissionalService', 'config', '$log', 'Notification',
            function ($scope, salaoService, profissionalService, config, $log, Notification) {

                function init() {

                    salaoService.acesso().success(function(data) {
                        $log.log(data);
                        $scope.acessos = data;

                    }).error(function(data, status) {
                        $log.error(data);
                        Notification.error(data.errorMessage);
                    });

                    salaoService.funcionario().success(function(data) {
                        $log.log(data);
                        $scope.funcionarios = data.items;

                    }).error(function(data, status) {
                        $log.error(data);
                        Notification.error(data.errorMessage);
                    });
                }

                $scope.novoAcesso = function() {
                    delete $scope.acesso;
                    $('#novo-acesso').modal('show');
                    $scope.acessoForm.$setPristine();
                };

                $scope.adicionar = function(funcionarioId, acesso) {

                    profissionalService.addAcesso(funcionarioId, acesso).success(function(data) {
                        init();

                        delete $scope.acesso;
                        $scope.acessoForm.$setPristine();

                        Notification.success('Profissional salvo com sucesso');

                        $('#novo-acesso').modal('hide');

                    }).error(function(data, status) {
                        $log.error(data);
                        Notification.error(data.errorMessage);
                    });
                };

                $scope.editar = function(acesso) {
                    $log.log(acesso);
                    $('#novo-acesso').modal('show');
                    $scope.acessoForm.$setPristine();
                    $scope.acesso = acesso;
                };
                $scope.atualizar = function(acesso) {
                    console.log(acesso);
                };

                $scope.remover = function(acesso) {
                    $scope.acessoConfirm = angular.copy(acesso);
                    $("#modal-excluir-acesso").modal("show");
                };
                $scope.excluir = function(acesso) {
                    console.log(acesso);
                    $("#modal-excluir-acesso").modal("hide");
                };

                init();
            }
        ])
    ;

})();