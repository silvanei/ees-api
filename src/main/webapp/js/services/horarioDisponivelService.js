/**
 * Created by silvanei on 12/09/16.
 */


(function () {
    'use strict';

    angular
        .module('agenda')
        .factory('horarioDisponivelService', ['$http', 'config', 'authManagerService',
            function($http, config, authManagerService) {

                var salaoId = authManagerService.identity().salaoId;
                var url = config.baseUrl + '/v1/salao/' + salaoId + '/horario-disponivel';

                function get(servicoId, funcionarioId, dia) {
                    return $http.get(url + '/servico/'+ servicoId +'/funcionario/' + funcionarioId + '/dia/' + dia);
                }

                return {
                    get: get
                }

            }]
        )
    ;

})();