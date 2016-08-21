
(function () {
    'use strict';

    angular
        .module('agenda')
        .factory('servicoService', ['$http', 'config',
            function($http, config){

                var get = function() {
                    return $http.get(config.baseUrl + '/rest/v1/salao/1/servico');
                };

                return {
                    get: get
                }

            }]
        );
})();