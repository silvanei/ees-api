/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('salaoController', ['$scope', 'salaoService', 'Notification', '$log', 'imageFileService', 'estadoService', '$rootScope',
            function ($scope, salaoService, Notification, $log, imageFileService, estadoService, $rootScope) {

                function init() {
                    $('.nav-tabs a').click(function (e) {
                        e.preventDefault();
                        $(this).tab('show');
                    });

                    salaoService.get().success(function(data) {
                        $scope.dados = data;

                        if(typeof data.endereco.estado !== 'undefined') {
                            estadoService.get(data.endereco.estado.id).success(function(data) {
                                $scope.cidades = data.cidades;
                            })
                        }

                    }).error(function(data, status) {
                        $log.error(data);
                        Notification.error(data.errorMessage);
                    });

                    imageFileService.get().success(function(data, status, headers){
                        $scope.img = 'data:image/jpeg;base64,' + data.encodedImage;

                    }).error(function(data, status, headers){
                        Notification.error(data.errorMessage);
                    });

                    estadoService.get().success(function(data) {
                        $scope.estados = data;
                    }).error(function(data) {
                        Notification.error(data.errorMessage);
                    });

                }

                $scope.changeEstado = function(estadoId) {
                    estadoService.get(estadoId).success(function(data) {
                        $scope.cidades = data.cidades;
                    }).error(function(data) {
                        Notification.error(data.errorMessage);
                    });
                };

                $scope.uploadFile = function(files) {
                    imageFileService.post(files[0]).success(function(data, status, headers){
                        delete $scope.img;
                        $scope.img = 'data:image/jpeg;base64,' + data.encodedImage;
                        Notification.success('Imagen enviada com sucesso');
                    }).error(function(data, status, headers){
                        Notification.error(data.errorMessage);
                    });
                };

                $scope.excluirImg = function() {
                    imageFileService.delete().success(function(data, status, headers){

                        imageFileService.get().success(function(data, status, headers){
                            $scope.img = 'data:image/jpeg;base64,' + data.encodedImage;
                        });

                        Notification.success('Imagen excluida com sucesso');
                    }).error(function(data, status, headers){
                        Notification.error(data.errorMessage);
                    });
                };

                $scope.atualizarDados = function(dados) {
                    salaoService.put(dados).success(function(data) {
                        $scope.dados = data;
                        $rootScope.nomeSalao = data.nome;
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