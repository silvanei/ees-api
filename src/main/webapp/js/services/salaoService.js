
(function () {
    'use strict';

    angular
        .module('agenda')
        .factory('salaoService', ['$http', 'config', 'authManagerService',
            function($http, config, authManagerService){
                var salaoId = authManagerService.identity().salaoId;
                var url =  config.baseUrl  + '/v1/salao/'+salaoId;

                function get() {
                    return $http.get(url+'/dados');
                }

                function atualizar(dados) {
                    var dados = angular.copy(dados);
                    return $http.put(dados.link.href, dados);
                }

                function getAcesso() {
                    return $http.get(url + '/acesso');
                }

                function getFuncionario() {
                    return $http.get(url + '/funcionario');
                }

                function getClientes() {
                    return $http.get(url + '/cliente');
                }

                return {
                    get: get,
                    put: atualizar,
                    acesso: getAcesso,
                    funcionario: getFuncionario,
                    cliente: getClientes
                }

            }]
        );
})();