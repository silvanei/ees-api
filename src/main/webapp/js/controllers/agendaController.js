/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('agendaController', ['$scope', 'servicoService', 'jwtHelperService',
            function ($scope, servicoService, jwtHelperService) {

                //var token = 'eyJhbGciOiJIUzI1NiJ9.eyJjbGkiOm51bGwsInNsYSI6MSwic3ViIjoiMSIsImV4cCI6MTQ3MTQ1Mzg3NSwiaWF0IjoxNDcxMzY3NDc1fQ.pxflwkyxoiOhD3K4hM90JfvASfyzDZSzRoeWegyPMfc';
                //var token = 'eyJhbGciOiJIUzI1NiJ9.eyJjbGkiOm51bGwsInNsYSI6MSwic3ViIjoiMSIsImV4cCI6MTQ3MTc1MzkzMywiaWF0IjoxNDcxNjY3NTMzfQ.BLk490c1Qq6JRMEEc8G_zyu2xBXAQ93RZCHNwHznAik';
                //console.log(jwtHelperService.decodeToken(token));
                //console.log(jwtHelperService.getTokenExpirationDate(token));
                //console.log(jwtHelperService.isTokenExpired(token));

                servicoService.get();

                var date = new Date();
                var d = date.getDate(),
                    m = date.getMonth(),
                    y = date.getFullYear();

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
                    calendar: {
                        schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source',
                        lang: 'pt-BR',
                        defaultView: 'agendaDay',
                        slotDuration: '00:30:00', // Intervalo entr as linhas
                        slotLabelFormat: 'H:mm',
                        minTime: '08:00:00',
                        maxTime: '20:00:00',
                        header: {
                            left: 'prev,next today title',
                            center: '',
                            right: 'agendaDay,agendaWeek,month'
                        },
                        resources: [
                            {id: '1', title: 'Profissional 1'},
                            {id: '2', title: 'Profissional 2'},
                            {id: '3', title: 'Profissional 3'},
                            {id: '4', title: 'Profissional 4'}
                        ],

                        eventClick: $scope.eventClick,

                        events: [
                            {
                                resourceId: '1',
                                title: 'Amarilis',
                                start: new Date(y, m, d, 8, 30),
                                end: new Date(y, m, d, 9),
                                backgroundColor: "#d2d6de", //verde
                                borderColor: "#d2d6de" //verde
                            },
                            {
                                resourceId: '1',
                                title: 'Bruna',
                                start: new Date(y, m, d, 13),
                                end: new Date(y, m, d, 14, 30),
                                backgroundColor: "#f39c12", //red
                                borderColor: "#f39c12" //red
                            },
                            {
                                resourceId: '2',
                                title: 'Heloisa',
                                start: new Date(y, m, d, 9),
                                end: new Date(y, m, d, 10),
                                backgroundColor: "#3c8dbc", //yellow
                                borderColor: "#3c8dbc" //yellow
                            },
                            {
                                resourceId: '2',
                                title: 'Patricia',
                                start: new Date(y, m, d, 10),
                                end: new Date(y, m, d, 11),
                                backgroundColor: "#dd4b39", //red
                                borderColor: "#dd4b39" //red
                            },
                            {
                                resourceId: '3',
                                title: 'Fernanda',
                                start: new Date(y, m, d, 15),
                                end: new Date(y, m, d, 16),
                                allDay: false,
                                backgroundColor: "#00a65a", //Blue
                                borderColor: "#00a65a" //Blue
                            },
                            {
                                resourceId: '4',
                                title: 'Jaqueline',
                                start: new Date(y, m, d, 12, 0),
                                end: new Date(y, m, d, 14, 0),
                                allDay: false,
                                backgroundColor: "#3c8dbc", //Info (aqua)
                                borderColor: "#3c8dbc" //Info (aqua)
                            }
                        ]
                    }
                };

            }
        ])
    ;
})();