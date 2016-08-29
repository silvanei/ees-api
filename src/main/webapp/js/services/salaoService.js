
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

                function getAcesso() {
                    var salaoId = authManagerService.identity().salaoId;
                    var url =  config.baseUrl + '/v1/salao/'+salaoId+'/acesso';
                    return $http.get(url);
                }

                function getFuncionario() {
                    var salaoId = authManagerService.identity().salaoId;
                    var url =  config.baseUrl + '/v1/salao/'+salaoId+'/funcionario';
                    return $http.get(url);
                }

                return {
                    get: get,
                    put: atualizar,
                    acesso: getAcesso,
                    funcionario: getFuncionario
                }

            }]
        );
})();