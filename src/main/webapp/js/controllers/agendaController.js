/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('agendaController', ['$scope', 'config', 'profissionalService', 'Notification', 'agendaService',
            function ($scope, config, profissionalService, Notification, agendaService) {

                $scope.eventSources = [function (start, end, timezone, callback) {

                    agendaService.get(start, end).success(function(data) {
                        // Converte timestamp para Date
                        data.events.forEach(function(item) {
                            item.start = new Date(item.start);
                            item.end = new Date(item.end);
                        });


                        $scope.resources = data.resources;
                        callback(data.events);

                        $('#calendar-aqui').fullCalendar( 'refetchResources' );
                        data.resources.forEach(function(item) {
                            $('#calendar-aqui').fullCalendar('addResource', {
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


                $scope.uiConfig = {
                    calendar: angular.extend({
                        eventClick: $scope.eventClick,
                        viewRender: $scope.viewRender
                    }, config.calendar)
                };
            }
        ])
    ;
})();