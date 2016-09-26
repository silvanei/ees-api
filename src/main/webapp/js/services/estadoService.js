/**
 * Created by silvanei on 04/09/16.
 */
(function() {
    'use strict';

    angular
        .module('agenda')
        .factory('estadoService', ['$http', 'config',
            function($http, config) {

                function url() {
                    return config.baseUrl + '/v1/estado';
                }

                function get(estadoId) {

                    if(estadoId) {
                        return $http.get(url() + "/" + estadoId);
                    }

                    return $http.get(url());
                }

                return {
                    get: get
                }
            }
        ])
    ;

})();