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

                function get(id) {
                    return $http.get(config.baseUrl + '/v1/salao/'+salaoId+'/funcionario/' + id);
                }

                function getAll(limit, offset) {
                    return $http.get(config.baseUrl + '/v1/salao/'+salaoId+'/funcionario?limit='+ limit + '&offset=' + offset);
                }

                function criar(profissional) {
                    return $http.post(config.baseUrl + '/v1/salao/'+salaoId+'/funcionario', profissional);
                }

                function atualizar(profissional) {
                    profissional = angular.copy(profissional);
                    return $http.put(profissional.link.href, profissional);
                }

                function excluir(profissional) {
                    return $http.delete(profissional.link.href);
                }

                function adicionarServico(profissional, idServico) {
                    return $http.post(profissional.link.href + '/servico/' + idServico);
                }

                function removerServico(profissional, idServico) {
                    return $http.delete(config.baseUrl + '/v1/salao/'+salaoId+'/funcionario/'+ profissional +'/servico/' + idServico);
                }

                function addHorarioTrabalho(funcionarioId, horarioTrabalho) {
                    return $http.post(config.baseUrl + '/v1/salao/'+salaoId+'/funcionario/'+ funcionarioId + '/horario-trabalho/' + horarioTrabalho.dia, horarioTrabalho);
                }

                function removerHorarioTrabalho(horario) {
                    return $http.delete(horario.link.href)
                }

                return {
                    get: get,
                    getAll: getAll,
                    post: criar,
                    put: atualizar,
                    delete: excluir,
                    addService: adicionarServico,
                    removeService: removerServico,
                    addHorarioTrabalho: addHorarioTrabalho,
                    removeHorarioTrabalho: removerHorarioTrabalho
                }
            }
        ]
    );

})();