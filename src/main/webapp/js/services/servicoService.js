
(function () {
    'use strict';

    angular
        .module('agenda')
        .factory('servicoService', ['$http', 'config', 'authManagerService',
            function($http, config, authManagerService){

                var salaoId = authManagerService.identity().salaoId;
                var url =  config.baseUrl + '/v1/salao/'+salaoId+'/servico';

                function get(limit, offset) {
                    if(limit) {
                        url +=  '?limit='+ limit + '&offset=' + offset;
                    }

                    return $http.get(url);
                }

                function atualizar(servico) {
                    servico = angular.copy(servico);
                    var link = servico.link;
                    delete servico.link;
                    return $http.put(link.href, servico);
                }

                function criar(servico) {
                    servico = angular.copy(servico);
                    return $http.post(url, servico);
                }

                function excluir(servico) {
                    return $http.delete(servico.link.href);
                }


                return {
                    get: get,
                    put: atualizar,
                    post: criar,
                    delete: excluir
                }

            }]
        );
})();