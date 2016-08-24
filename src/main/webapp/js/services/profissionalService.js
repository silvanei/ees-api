/**
 * Created by silvanei on 22/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .factory('profissionalService', ['$http', 'config', 'authManagerService',
            function($http, config, authManagerService) {

                var salaoId = authManagerService.identity().salaoId;

                function get(limit, offset) {
                    return $http.get(config.baseUrl + '/v1/salao/'+salaoId+'/funcionario?limit='+ limit + '&offset=' + offset);
                }

                function criar(profissional) {
                    return $http.post(config.baseUrl + '/v1/salao/'+salaoId+'/funcionario', profissional);
                }

                function atualizar(profissional) {
                    profissional = angular.copy(profissional);
                    var link = profissional.link;
                    delete profissional.link;
                    delete profissional.servicosPrestados;

                    return $http.put(link.href, profissional);
                }

                function excluir(profissional) {
                    return $http.delete(profissional.link.href);
                }

                function adicionarServico(profissional, idServico) {
                    return $http.post(profissional.link.href + '/servico/' + idServico);
                }

                return {
                    get: get,
                    post: criar,
                    put: atualizar,
                    delete: excluir,
                    addService: adicionarServico
                }
            }
        ]
    );

})();