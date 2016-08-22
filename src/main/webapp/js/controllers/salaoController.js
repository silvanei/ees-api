/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('salaoController', ['$scope', 'servicoService',
            function ($scope, servicoService) {

                function init() {
                    $('.nav-tabs a').click(function (e) {
                        e.preventDefault();
                        $(this).tab('show');
                    });

                    carregarLista(5, 2);
                }

                function carregarLista(limit, offset) {

                    servicoService.get(limit, offset).success(function(data) {
                        console.log(data);
                        $scope.totalItems = data.limit;
                        $scope.currentPage = data.offset / data.limit ;
                        $scope.maxSize = 5;
                        $scope.bigTotalItems = data.count;

                        $scope.servicos = data.items.map(function(servico) {
                            servico.duracao = new Date(servico.duracao);
                            return servico;
                        });
                    });
                }

                $scope.editar = function (servico) {
                    $('#novo-servico').modal('show');
                    $scope.servico = servico;
                };

                $scope.adicionar = function (servico) {
                    $('#novo-servico').modal('show');
                    delete $scope.servico;
                };

                $scope.salvar = function (servico) {
                    console.log(servico);
                    servicoService.post(servico).success(function() {
                        $('#novo-servico').modal('hide');
                        delete $scope.servico;
                        carregarLista(2, 0);
                    });
                };

                $scope.atualizar = function(servico) {
                    servicoService.put(servico).success(function() {
                        $('#novo-servico').modal('hide');
                        delete $scope.servico;
                        carregarLista(2, 0);
                    });
                };

                $scope.confirmExcluir = function(servico) {
                    $scope.servico = servico;
                    $('#excluir-servico').modal('show');

                };

                $scope.excluir = function(servico) {
                    servicoService.delete(servico).success(function() {
                        $('#excluir-servico').modal('hide');
                        delete $scope.servico;
                        carregarLista(2, 0);
                    });

                };

                $scope.setPage = function (pageNo) {
                    $scope.currentPage = pageNo;
                };

                $scope.pageChanged = function() {
                    console.log('Page changed to: ' + $scope.currentPage);
                };


                init();
            }
        ])
    ;

})();