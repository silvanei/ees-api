
(function () {
    'use strict';

    angular
        .module('agenda')
        .factory('salaoService', ['$http', 'config', 'authManagerService',
            function($http, config, authManagerService){

                function get() {
                    var salaoId = authManagerService.identity().salaoId;
                    var url =  config.baseUrl + '/v1/salao/'+salaoId+'/dados';
                    return $http.get(url);
                }

                function atualizar(dados) {
                    var dados = angular.copy(dados);
                    return $http.put(dados.link.href, dados);
                }

                return {
                    get: get,
                    put: atualizar
                }

            }]
        );
})();