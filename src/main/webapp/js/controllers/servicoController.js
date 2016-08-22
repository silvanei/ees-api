/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('servicoController', ['$scope', 'servicoService', 'config', '$log',
            function ($scope, servicoService, config, $log) {

                function init() {
                    $scope.currentPage = 1;
                    $scope.itemsPerPage = config.paginacao.itensPorPagina;
                    $scope.maxSize = config.paginacao.maxSize;

                    carregarLista();
                }

                function carregarLista() {
                    var limit = config.paginacao.itensPorPagina;
                    var offset = (config.paginacao.itensPorPagina * $scope.currentPage) - config.paginacao.itensPorPagina;

                    servicoService.get(limit, offset).success(function(data) {
                        $log.log(data);
                        $scope.totalItems = data.count;

                        $scope.servicos = data.items.map(function(servico) {
                            servico.duracao = new Date(servico.duracao);
                            return servico;
                        });
                    }).error(function(data, status) {
                        console.log(data);
                        console.log(status);
                        $scope.error = data.errorMessage;
                    });
                }

                $scope.editar = function (servico) {
                    $scope.servicoForm.$setPristine();
                    $scope.servico = angular.copy(servico);
                    $('#modal-servico').modal('show');
                };

                $scope.adicionar = function () {
                    delete $scope.servico;
                    $scope.servicoForm.$setPristine();
                    $('#modal-servico').modal('show');
                };

                $scope.salvar = function (servico) {
                    $log.log(servico);

                    servicoService.post(servico)
                        .success(function() {
                            $('#modal-servico').modal('hide');
                            delete $scope.servico;
                            $scope.servicoForm.$setPristine();
                            carregarLista();
                        }
                    );
                };

                $scope.atualizar = function(servico) {
                    servicoService.put(servico)
                        .success(function() {
                            $('#modal-servico').modal('hide');
                            delete $scope.servico;
                            $scope.servicoForm.$setPristine();
                            carregarLista();
                        }
                    );
                };

                $scope.confirmExcluir = function(servico) {
                    $scope.servico = angular.copy(servico);
                    $('#excluir-servico').modal('show');

                };

                $scope.excluir = function(servico) {
                    servicoService.delete(servico)
                        .success(function() {
                            $('#excluir-servico').modal('hide');
                            delete $scope.servico;
                            $scope.servicoForm.$setPristine();
                            carregarLista();
                        }
                    );

                };

                $scope.pageChanged = function() {
                    $log.log('Page changed to: ' + $scope.currentPage);
                    carregarLista();
                };


                init();
            }
        ])
    ;

})();