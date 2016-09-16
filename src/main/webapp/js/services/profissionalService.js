/**
 * Created by silvanei on 22/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .factory('profissionalService', ['$http', 'config', 'authManagerService',
            function($http, config, authManagerService) {

                function url() {
                    var salaoId = authManagerService.identity().salaoId;
                    var resourceUrl = config.baseUrl + '/v1/salao/'+salaoId+'/funcionario';
                    return resourceUrl;
                }

                function get(id) {
                    return $http.get(url() + '/' +id);
                }

                function getAll(limit, offset) {
                    if(limit > 0) {
                        return $http.get(url() + '?limit='+ limit + '&offset=' + offset);
                    }

                    return $http.get(url());
                }

                function criar(profissional) {
                    return $http.post(url(), profissional);
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
                    return $http.delete(url() + '/'+ profissional +'/servico/' + idServico);
                }

                function addHorarioTrabalho(funcionarioId, horarioTrabalho) {
                    return $http.post(url() + '/'+ funcionarioId + '/horario-trabalho/' + horarioTrabalho.dia, horarioTrabalho);
                }

                function removerHorarioTrabalho(horario) {
                    return $http.delete(horario.link.href)
                }

                function adicionarAcesso(funcionarioId, acesso) {
                    acesso = angular.copy(acesso);
                    return $http.post(url() + '/'+funcionarioId+'/acesso', acesso);
                }

                function removeAcesso(acesso) {
                    acesso = angular.copy(acesso);
                    return $http.delete(url() + '/'+acesso.funcionarioId+'/acesso');
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
                    removeHorarioTrabalho: removerHorarioTrabalho,
                    addAcesso: adicionarAcesso,
                    removeAcesso: removeAcesso
                }
            }
        ]
    );

})();