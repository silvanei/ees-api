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

                return {
                    get: get
                }
            }
        ]
    );

})();