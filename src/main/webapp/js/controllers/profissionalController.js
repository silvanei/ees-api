/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('profissionalController', ['$scope', 'config', '$log', 'profissionalService', 'Notification',
            function ($scope,config, $log, profissionalService, Notification) {
//                Notification.success('Primary notification');
//                Notification.error('Primary notification');
//                Notification.success({message: 'Success notification<br>Some other <b>content</b><br><a href="https://github.com/alexcrack/angular-ui-notification">This is a link</a><br><img src="https://angularjs.org/img/AngularJS-small.png">', title: 'Html content'});
//
//
//                  // or simply..
////                  Notification('Primary notification');
////
////                  // Other Options
////                  // Success
////                  Notification.success('Success notification');
////
////                  // Message with custom type
////                  Notification({message: 'Warning notification'}, 'warning');
////
////                  // With Title
////                  Notification({message: 'Primary notification', title: 'Primary notification'});
////
////                  // Message with custom delay
////                  Notification.error({message: 'Error notification 1s', delay: 1000});
////
////                  // Embed HTML within your message.....
////                  Notification.success({message: 'Success notification<br>Some other <b>content</b><br><a href="https://github.com/alexcrack/angular-ui-notification">This is a link</a><br><img src="https://angularjs.org/img/AngularJS-small.png">', title: 'Html content'});
////
////                  // Change position notification
////                  Notification.error({message: 'Error Bottom Right', positionY: 'bottom', positionX: 'right'});
////
////                  // Replace message
////                  Notification.error({message: 'Error notification 1s', replaceMessage: true});

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
                    }).error(function(data, status) {
                        $log.error(data);
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
                        console.log($scope.profissional);
                        carregarLista();
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