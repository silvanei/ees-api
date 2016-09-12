/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('agendaController', ['$scope', 'config','Notification', 'salaoService', 'agendaService', 'servicoService',
            function ($scope, config, Notification, salaoService, agendaService, servicoService) {

                function init() {
                    salaoService.get().success(function(data) {

                        $scope.uiConfig = {
                            calendar: angular.extend({
                                minTime: moment(data.horarioDeFuncionamento.horarioInicio).format('HH:mm:ss'),
                                maxTime: moment(data.horarioDeFuncionamento.horarioFinal).format('HH:mm:ss'),
                                eventClick: $scope.eventClick,
                                viewRender: $scope.viewRender
                            }, config.calendar)
                        };

                    });

                    salaoService.cliente().success(function(data) {
                        $scope.clientes = data.items;
                    });

                    servicoService.get().success(function(data) {
                        $scope.servicos = data.items;
                    });
                }

                $scope.eventSources = [function (start, end, timezone, callback) {

                    agendaService.get(start, end.subtract(1, 'days')).success(function(data) {
                        // Converte timestamp para Date
                        data.events.forEach(function(item) {
                            item.start = new Date(item.start);
                            item.end = new Date(item.end);
                        });


                        $scope.resources = data.resources;
                        callback(data.events);

                        $('#calendar').fullCalendar( 'refetchResources' );
                        data.resources.forEach(function(item) {
                            $('#calendar').fullCalendar('addResource', {
                                id: item.id,
                                title: item.title
                            });
                        });

                    }).error(function(data, status) {
                        Notification.error(data.errorMessage);
                    });
                }];

                $scope.eventClick = function (event) {
                    //$scope.$apply(function(){
                    //    $scope.alertMessage = (event.title + ' is clicked');
                    //});

                    var modal = $('#reserva').modal();
                    modal.find('.modal-title').text('Horário - ' + event.title);
                    modal.show();

                    //$('#calendar').fullCalendar('updateEvent', event);
                };


                $scope.cliente = {};

                $scope.onSelectCallback = function (item){
                    $scope.eventResult = {item: item};
                    console.log($scope.eventResult);
                };



                init();
            }
        ])
    ;
})();