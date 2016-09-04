/**
 * Created by silvanei on 04/09/16.
 */
(function() {
    'use strict';

    angular
        .module('agenda')
        .factory('clienteService', ['$http', 'config', 'authManagerService',
            function($http, config, authManagerService) {

                function getAll(limit, offset) {
                    var salaoId = authManagerService.identity().salaoId;
                    return $http.get(config.baseUrl + '/v1/salao/'+salaoId+'/cliente?limit='+ limit + '&offset=' + offset);
                }

                return {
                    getAll: getAll
                }
            }
        ])
    ;

})();