/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('novoClienteController', ['$scope', 'Notification', 'clienteService', 'salaoService',
            function ($scope, Notification, clienteService, salaoService) {
                var modalNovoCliente = $('#modal-novo-cliente');

                function init() {
                    modalNovoCliente.on('show.bs.modal', modalShow);
                    modalNovoCliente.on('hide.bs.modal', modalHide);
                }

                $scope.salvar = function (cliente) {

                    clienteService.post(cliente).success(function(data) {

                        modalNovoCliente.modal('hide');

                        Notification.success('Cliente salvo com sucesso');

                        salaoService.cliente().success(function(data) {
                            $scope.$parent.clientes = data.items;
                        });

                    }).error(function(data, status) {
                        Notification.error(data.errorMessage);
                    });
                };

                function modalShow() {
                    delete $scope.cliente;
                    $scope.novoClienteForm.$setPristine();
                }

                function modalHide() {
                    delete $scope.cliente;
                    $scope.novoClienteForm.$setPristine();
                }

                init();
            }
        ])
    ;

})();