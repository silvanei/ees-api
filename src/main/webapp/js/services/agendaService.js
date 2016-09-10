/**
 * Created by silvanei on 10/09/16.
 */

(function() {
    'use strict';

    angular
        .module('agenda')
        .factory('agendaService', ['$http', 'config', 'authManagerService',
            function($http, config, authManagerService) {

                var salaoId = authManagerService.identity().salaoId;
                var resourceUrl = config.baseUrl + '/v1/salao/'+salaoId+'/agenda';

                function get(start, end) {
                    console.log(start.format('YYYY-MM-DD'));
                    console.log(end.format('YYYY-MM-DD'));
                    return $http.get(resourceUrl + '/' + start.format('YYYY-MM-DD')+ '/' + end.format('YYYY-MM-DD'));
                }

                return {
                    get: get
                }
            }
        ])
    ;

})();