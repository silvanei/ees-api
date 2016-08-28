
(function () {
    'use strict';

    angular
        .module('agenda')
        .factory('registroService', ['$http', 'config',
            function($http, config){

                function criar(registro) {
                    registro = angular.copy(registro);
                    return $http.post(config.baseUrl + '/v1/registrar-salao', registro);
                }
                return {
                    post: criar
                }

            }]
        );
})();