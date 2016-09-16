/**
 * Created by silvanei on 04/09/16.
 */
(function() {
    'use strict';

    angular
        .module('agenda')
        .factory('clienteService', ['$http', 'config', 'authManagerService',
            function($http, config, authManagerService) {

                function url() {
                    var salaoId = authManagerService.identity().salaoId;
                    var resourceUrl = config.baseUrl + '/v1/salao/'+salaoId+'/cliente';
                    return resourceUrl;
                }

                function getAll(limit, offset) {
                    return $http.get(url() + '?limit='+ limit + '&offset=' + offset);
                }

                function post(clienteSalao) {
                    return $http.post(url(), clienteSalao);
                }

                function put(cliente){
                    cliente = angular.copy(cliente);
                    return $http.put(cliente.link.href, cliente)
                }

                function excluir(cliente) {
                    return $http.delete(cliente.link.href);
                }

                return {
                    getAll: getAll,
                    post: post,
                    put: put,
                    delete: excluir
                }
            }
        ])
    ;

})();