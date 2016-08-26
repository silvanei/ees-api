/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('profissionalController', ['$scope', 'config', '$log', 'profissionalService', 'Notification',
            function ($scope,config, $log, profissionalService, Notification) {

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

                    profissionalService.getAll(limit, offset).success(function(data) {
                        $scope.totalItems = data.count;
                        $scope.profissionais = data.items;
                    }).error(function(data, status) {
                        console.log(data);
                        console.log(status);
                        $scope.error = data.errorMessage;
                    });
                }

                $scope.adicionar = function () {
                    $('#modal-profissional').modal('show');
                    delete $scope.profissional;
                    $scope.dadosProfissionalForm.$setPristine();
                    $('#modal-profissional a[href="#dados"]').tab('show');
                };

                $scope.salvar = function (profissional) {
                    $log.log(profissional);

                    profissionalService.post(profissional).success(function(data) {
                        //$('#modal-profissional').modal('hide');
                        delete $scope.profissional;
                        $scope.profissional = data;
                        $scope.dadosProfissionalForm.$setPristine();
                        carregarLista();

                        Notification.success('Profissional salvo com sucesso');

                    }).error(function(data, status) {
                        $log.error(data);

                        Notification.error(data.errorMessage);
                    });
                };

                $scope.editar = function (profissional) {
                    $scope.dadosProfissionalForm.$setPristine();
                    $scope.profissional = angular.copy(profissional);
                    console.log($scope.profissional);
                    $('#modal-profissional').modal('show');
                };

                $scope.atualizar = function(profissional) {
                    profissionalService.put(profissional).success(function() {
                        $('#modal-profissional').modal('hide');
                        delete $scope.profissional;
                        $scope.dadosProfissionalForm.$setPristine();
                        carregarLista();

                        Notification.success('Profissional atualizado com sucesso');

                    }).error(function(data, status) {
                        $log.error(data);

                        Notification.error(data.errorMessage);
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

                        Notification.success('Profissional excluido com sucesso');
                    }).error(function(data, status) {
                        $log.error(data);

                        Notification.error(data.errorMessage);
                    });

                };

                $scope.adicionarServico = function () {
                    profissionalService.addService($scope.profissional, $scope.servico.selecionado).success(function(data) {
                        $scope.profissional = data;
                        carregarLista();

                        Notification.success('Serviço atribuído com sucesso');
                    }).error(function(data, status) {
                        $log.error(data);

                        Notification.error(data.errorMessage);
                    });
                };

                $scope.removeServico = function(idProfissional, idServico) {
                    profissionalService.removeService(idProfissional, idServico).success(function(data) {

                        profissionalService.get(idProfissional).success(function (data) {
                            $scope.profissional = data;

                        }).error(function(data, status) {
                            $log.error(data);

                            Notification.error(data.errorMessage);
                        });


                        Notification.success('Serviço removido com sucesso');

                    }).error(function(data, status) {
                        $log.error(data);

                        Notification.error(data.errorMessage);
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