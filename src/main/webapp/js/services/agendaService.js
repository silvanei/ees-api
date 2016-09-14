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
                    return $http.get(resourceUrl + '/' + start.format('YYYY-MM-DD')+ '/' + end.format('YYYY-MM-DD'));
                }

                function post(agendamento) {
                    var dia = moment(agendamento.data).format('YYYY-MM-DD');
                    delete agendamento.data;
                    return $http.post(resourceUrl + '/' + dia + '/event', agendamento);
                }

                function put(agendamento) {
                    agendamento = angular.copy(agendamento);

                    var hora = moment(agendamento.hora).format('HH');
                    var minuto = moment(agendamento.hora).format('mm');
                    var dia = moment(agendamento.data)
                        .hour(hora)
                        .minute(minuto)
                    ;

                    agendamento.data = dia.valueOf();

                    delete agendamento.hora;
                    return $http.put(agendamento.link.href, agendamento);
                }

                return {
                    get: get,
                    post: post,
                    put: put
                }
            }
        ])
    ;

})();