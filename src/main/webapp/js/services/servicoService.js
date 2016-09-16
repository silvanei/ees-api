
(function () {
    'use strict';

    angular
        .module('agenda')
        .factory('servicoService', ['$http', 'config', 'authManagerService',
            function($http, config, authManagerService){

                function url() {
                    var salaoId = authManagerService.identity().salaoId;
                    var url =  config.baseUrl + '/v1/salao/'+salaoId+'/servico';

                    return url;
                }

                function get(limit, offset) {
                    if(limit) {
                        return $http.get(url() + '?limit='+ limit + '&offset=' + offset);
                    }

                    return $http.get(url());
                }

                function atualizar(servico) {
                    servico = angular.copy(servico);
                    var link = servico.link;
                    delete servico.link;
                    return $http.put(link.href, servico);
                }

                function criar(servico) {
                    servico = angular.copy(servico);
                    return $http.post(url(), servico);
                }

                function excluir(servico) {
                    return $http.delete(servico.link.href);
                }

                function funcionario(servidoId) {
                    return $http.get(url() + '/' + servidoId + '/funcionario');
                }

                return {
                    get: get,
                    put: atualizar,
                    post: criar,
                    delete: excluir,
                    funcionario: funcionario
                }

            }]
        );
})();