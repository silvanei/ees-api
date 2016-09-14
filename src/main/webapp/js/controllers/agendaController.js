/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('agendaController', ['$scope', 'config','Notification', 'salaoService', 'agendaService', 'servicoService',
            function ($scope, config, Notification, salaoService, agendaService, servicoService) {

                var modalAgendamento = $("#modal-agendamento");

                function init() {

                    $scope.currentDate = moment().format('YYYY-MM-DD');

                    salaoService.get().success(function(data) {

                        $scope.minTime = moment(data.horarioDeFuncionamento.horarioInicio).format('HH:mm');
                        $scope.maxTime = moment(data.horarioDeFuncionamento.horarioFinal).format('HH:mm');

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

                    $('li', '.teste-menu').on('click', function() {
                        $(this).toggleClass(function(){
                            return 'active';
                        });
                    });
                }

                $scope.eventSources = [function (start, end, timezone, callback) {

                    agendaService.get(start, end.subtract(1, 'days')).success(function(data) {
                        // Converte timestamp para Date
                        data.events.forEach(function(item) {
                            item.start = new Date(item.start);
                            item.end = new Date(item.end);
                            item.resourceId = item.funcionarioId;
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

                    console.log(event);
                    //event.resourceId = 3;

                    //$('#calendar').fullCalendar('updateEvent', event);
                };

                $scope.onSelectServico = function (item){

                    delete $scope.funcionarios;
                    delete $scope.agendamento.funcionario;

                    if(item) {
                        servicoService.funcionario(item.id)
                            .success(function(data) {
                                $scope.funcionarios = data;
                            })
                            .error(function(data, status) {
                                Notification.error(data.errorMessage);
                                delete $scope.funcionarios;
                                delete $scope.agendamento.funcionario;
                            })
                        ;
                    }
                };

                $scope.novoAgendamento = function() {
                    modalAgendamento.modal('show');
                    delete $scope.agendamento;
                    delete $scope.funcionarios;

                    $scope.agendamento = {
                        data: new Date()
                    };

                    $scope.agendamentoForm.$setPristine();
                };

                $scope.salvar = function(agendamento) {
                    agendaService.post(agendamento)
                        .success(function(data) {
                            $('#calendar').fullCalendar( 'refetchEvents' );

                            modalAgendamento.modal('hide');
                            delete $scope.agendamento;
                            delete $scope.funcionarios;

                            Notification.success('Horaio agendado com sucesso');
                        })
                        .error(function(data, status) {
                            $scope.agendamento.data = new Date();
                            Notification.error(data.errorMessage);
                        })
                    ;
                };

                init();
            }
        ])
    ;
})();