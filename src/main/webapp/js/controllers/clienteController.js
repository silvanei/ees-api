/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('clienteController', ['$scope', 'config', 'Notification', 'clienteService',
            function ($scope, config, Notification, clienteService) {

                var modalCliente = $('#modal-cliente');

                function init() {
                    $scope.currentPage = 1;
                    $scope.itemsPerPage = config.paginacao.itensPorPagina;
                    $scope.maxSize = config.paginacao.maxSize;

                    carregarLista();
                }

                function carregarLista() {
                    var limit = config.paginacao.itensPorPagina;
                    var offset = (config.paginacao.itensPorPagina * $scope.currentPage) - config.paginacao.itensPorPagina;

                    clienteService.getAll(limit, offset).success(function(data) {
                        $scope.totalItems = data.count;
                        $scope.clientes = data.items;
                    }).error(function(data, status) {
                        Notification.error(data.errorMessage);
                    });
                }

                $scope.pageChanged = function() {
                    carregarLista();
                };

                $scope.adicionar = function () {
                    modalCliente.modal('show');
                    delete $scope.cliente;
                    $scope.dadosClienteForm.$setPristine();
                };

                $scope.salvar = function (cliente) {

                    clienteService.post(cliente).success(function(data) {
                        delete $scope.cliente;
                        $scope.dadosClienteForm.$setPristine();

                        carregarLista();
                        modalCliente.modal('hide');

                        Notification.success('Cliente salvo com sucesso');

                    }).error(function(data, status) {
                        Notification.error(data.errorMessage);
                    });
                };

                $scope.atualizar = function(cliente) {
                    clienteService.put(cliente).success(function(data) {
                        delete $scope.cliente;
                        $scope.dadosClienteForm.$setPristine();

                        carregarLista();
                        modalCliente.modal('hide');

                        Notification.success('Cliente atualizado com sucesso');

                    }).error(function(data, status) {
                        Notification.error(data.errorMessage);
                    });
                };

                $scope.editar = function (cliente) {
                    $scope.dadosClienteForm.$setPristine();
                    $scope.cliente = angular.copy(cliente);

                    modalCliente.modal('show');
                };

                $scope.confirmExcluir = function(cliente) {
                    $scope.cliente = angular.copy(cliente);
                    $('#excluir-cliente').modal('show');

                };

                $scope.excluir = function(cliente) {
                    clienteService.delete(cliente).success(function() {
                        $('#excluir-cliente').modal('hide');
                        delete $scope.cliente;
                        $scope.dadosClienteForm.$setPristine();
                        carregarLista();

                        Notification.success('Cliente excluido com sucesso');
                    }).error(function(data, status) {
                        Notification.error(data.errorMessage);
                    });

                };

                init();
            }
        ])
    ;

})();