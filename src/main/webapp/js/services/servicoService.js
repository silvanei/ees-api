
(function () {
    'use strict';

    angular
        .module('agenda')
        .factory('servicoService', ['$http', 'config', 'authManagerService',
            function($http, config, authManagerService){

                var salaoId = authManagerService.identity().salaoId;

                function get(limit, offset) {
                    return $http.get(config.baseUrl + '/rest/v1/salao/'+salaoId+'/servico?limit='+ limit + '&offset=' + offset);
                }

                function atualizar(servico) {
                    var link = servico.link;
                    delete servico.link;

                    servico.duracao = servico.duracao.getTime();

                    return $http.put(link.href, servico);
                }

                function criar(servico) {
                    servico.duracao = servico.duracao.getTime();
                    return $http.post(config.baseUrl + '/rest/v1/salao/'+salaoId+'/servico', servico);
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