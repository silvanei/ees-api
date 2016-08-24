/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('profissionalController', ['$scope', 'config', '$log', 'profissionalService', 'servicoService',
            function ($scope,config, $log, profissionalService, servicoService) {
                function init() {
                    $('.nav-tabs a').click(function (e) {
                        e.preventDefault();
                        $(this).tab('show');
                    });

                    $scope.currentPage = 1;
                    $scope.itemsPerPage = config.paginacao.itensPorPagina;
                    $scope.maxSize = config.paginacao.maxSize;

                    carregarLista();
                }

                function carregarLista(callback) {
                    var limit = config.paginacao.itensPorPagina;
                    var offset = (config.paginacao.itensPorPagina * $scope.currentPage) - config.paginacao.itensPorPagina;

                    profissionalService.get(limit, offset).success(function(data) {
                        $log.log(data);
                        $scope.totalItems = data.count;
                        $scope.profissionais = data.items;

                    }).error(function(data, status) {
                        console.log(data);
                        console.log(status);
                        $scope.error = data.errorMessage;
                    });
                }

                function carregarServico(callback) {
                    servicoService.get().success(function(data) {
                        $log.log(data);
                        $scope.servicos = data.items;

                        if( callback instanceof Function) {
                            callback(data.items);
                        }

                    }).error(function(data, status) {
                        console.log(data);
                        console.log(status);
                        $scope.error = data.errorMessage;
                    });

                }

                $scope.adicionar = function () {
                    delete $scope.profissional;
                    $scope.dadosProfissionalForm.$setPristine();
                    $('#modal-profissional').modal('show');
                };

                $scope.salvar = function (profissional) {
                    $log.log(profissional);

                    profissionalService.post(profissional).success(function() {
                        $('#modal-profissional').modal('hide');
                        delete $scope.profissional;
                        $scope.dadosProfissionalForm.$setPristine();
                        carregarLista();
                    }).error(function(data, status) {
                        $log.error(data);
                    });
                };

                $scope.editar = function (profissional) {
                    $scope.dadosProfissionalForm.$setPristine();
                    $scope.profissional = angular.copy(profissional);
                    $('#modal-profissional').modal('show');

                    carregarServico(function(servicos) {
                        var result = servicos.filter(function(item1) {
                            for (var i in $scope.profissional.servicosPrestados) {
                                if (item1.id === $scope.profissional.servicosPrestados[i].id) { return false; }
                            };
                            return true;
                        });
                        $scope.servicos = result;
                    });

                };

                $scope.atualizar = function(profissional) {
                    profissionalService.put(profissional).success(function() {
                        $('#modal-profissional').modal('hide');
                        delete $scope.profissional;
                        $scope.dadosProfissionalForm.$setPristine();
                        carregarLista();
                    }).error(function(data, status) {
                        $log.error(data);
                    });
                };

                $scope.confirmExcluir = function(profissional) {
                    $scope.profissional = angular.copy(profissional);
                    $('#excluir-profissional').modal('show');

                };

                $scope.excluir = function(profissional) {
                    profissionalService.delete(profissional).success(function() {
                        $('#excluir-profissional').modal('hide');
                        delete $scope.profissional;
                        $scope.dadosProfissionalForm.$setPristine();
                        carregarLista();
                    }).error(function(data, status) {
                        $log.error(data);
                    });

                };

                $scope.adicionarServico = function () {
                    $log.log($scope.profissional);

                    profissionalService.addService($scope.profissional, $scope.servico.selecionado).success(function(data) {
                        $scope.profissional = data;
                        carregarLista();
                        carregarServico(function(servicos) {
                            var result = servicos.filter(function(item1) {
                                for (var i in $scope.profissional.servicosPrestados) {
                                    if (item1.id === $scope.profissional.servicosPrestados[i].id) { return false; }
                                };
                                return true;
                            });
                            $scope.servicos = result;
                        });

                    }).error(function(data, status) {
                        $log.error(data);
                    });
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