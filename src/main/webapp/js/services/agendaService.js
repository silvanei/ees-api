/**
 * Created by silvanei on 10/09/16.
 */

(function() {
    'use strict';

    angular
        .module('agenda')
        .factory('agendaService', ['$http', 'config', 'authManagerService',
            function($http, config, authManagerService) {

                function url() {
                    var salaoId = authManagerService.identity().salaoId;
                    var resourceUrl = config.baseUrl + '/v1/salao/'+salaoId+'/agenda';
                    return resourceUrl;
                }

                function get(start, end) {
                    return $http.get(url() + '?inicio=' + start.format('YYYY-MM-DD')+ '&fim=' + end.format('YYYY-MM-DD'));
                }

                function post(agendamento) {
                    agendamento = angular.copy(agendamento);

                    agendamento.data = _getDate(agendamento);

                    delete agendamento.hora;

                    return $http.post(url(), agendamento);
                }

                function put(agendamento) {
                    agendamento = angular.copy(agendamento);

                    agendamento.data = _getDate(agendamento);

                    delete agendamento.hora;

                    delete agendamento.hora;
                    return $http.put(agendamento.link.href, agendamento);
                }

                function _getDate(agendamento) {
                    var hora = moment(agendamento.hora).format('HH');
                    var minuto = moment(agendamento.hora).format('mm');
                    var dia = moment(agendamento.data)
                            .hour(hora)
                            .minute(minuto)
                            .second(0)
                    ;
                    return dia.format('x');
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