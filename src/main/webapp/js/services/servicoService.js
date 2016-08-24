
(function () {
    'use strict';

    angular
        .module('agenda')
        .factory('servicoService', ['$http', 'config', 'authManagerService',
            function($http, config, authManagerService){

                var salaoId = authManagerService.identity().salaoId;

                function get(limit, offset) {

                    var url =  config.baseUrl + '/v1/salao/'+salaoId+'/servico';
                    if(limit) {
                        url +=  '?limit='+ limit + '&offset=' + offset;
                    }

                    return $http.get(url);
                }

                function atualizar(servico) {
                    servico = angular.copy(servico);
                    var link = servico.link;
                    delete servico.link;

                    servico.duracao = servico.duracao.getTime();

                    return $http.put(link.href, servico);
                }

                function criar(servico) {
                    servico = angular.copy(servico);
                    servico.duracao = servico.duracao.getTime();
                    return $http.post(config.baseUrl + '/v1/salao/'+salaoId+'/servico', servico);
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