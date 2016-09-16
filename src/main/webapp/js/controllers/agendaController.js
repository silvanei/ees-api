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
                    $scope.statusAgendamento = config.statusAgendamento;

                    salaoService.get().success(function(data) {

                        if(data.horarioDeFuncionamento.horarioInicio) {
                            config.calendar.minTime = moment(data.horarioDeFuncionamento.horarioInicio).format('HH:mm:ss');

                        }
                        if(data.horarioDeFuncionamento.horarioFinal) {
                            config.calendar.maxTime = moment(data.horarioDeFuncionamento.horarioFinal).format('HH:mm:ss');
                        }

                        $scope.minTime = moment(data.horarioDeFuncionamento.horarioInicio).format('HH:mm');
                        $scope.maxTime = moment(data.horarioDeFuncionamento.horarioFinal).format('HH:mm');

                        $scope.uiConfig = {
                            calendar: angular.extend({
                                eventClick: $scope.eventClick,
                                eventDrop: $scope.eventDrop
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
                            item.resourceId = item.funcionarioId;
                            item.durationEditable = false;
                            //item.className = ['btn', 'btn-danger']
                            //item.color = 'yellow'; // an option!
                            //item.textColor = 'black'; // an option!
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

                    delete $scope.agendamento;
                    $scope.agendamento = {
                        id: event.id,
                        data: event.start.toDate(),
                        clienteId: event.clienteId,
                        servicoId: event.servicoId,
                        hora: event.start.valueOf(),
                        observacao: event.observacao,
                        status: event.status,
                        link: event.link
                    };

                    delete $scope.funcionarios;
                    servicoService.funcionario(event.servicoId)
                        .success(function(data) {
                            $scope.funcionarios = data;
                            $scope.agendamento.funcionarioId = event.resourceId;

                        })
                        .error(function(data, status) {
                            Notification.error(data.errorMessage);
                            delete $scope.funcionarios;
                            delete $scope.agendamento.funcionarioId;
                        })
                    ;

                    $scope.agendamentoForm.$setPristine();

                    var modal = modalAgendamento.modal();
                    modal.find('.modal-title').text('Horário - ' + event.title);
                    modal.show();
                };
                $scope.eventDrop = function(event, delta, revertFunc) {
                    var agendamento = {
                        id: event.id,
                        data: event.start.toDate(),
                        hora: event.start.valueOf(),
                        clienteId: event.clienteId,
                        servicoId: event.servicoId,
                        funcionarioId: event.resourceId,
                        observacao: event.observacao,
                        status: event.status,
                        link: event.link
                    };


                    agendaService.put(agendamento)
                        .success(function(data) {
                            $('#calendar').fullCalendar( 'refetchEvents' );

                            modalAgendamento.modal('hide');
                            delete $scope.agendamento;
                            delete $scope.funcionarios;

                            Notification.success('Horario atualizado com sucesso');
                        })
                        .error(function(data, status) {
                            revertFunc();
                            Notification.error(data.errorMessage);
                        })
                    ;
                };

                $scope.onSelectServico = function (item){

                    delete $scope.funcionarios;
                    delete $scope.agendamento.funcionarioId;

                    if(item) {
                        servicoService.funcionario(item.id)
                            .success(function(data) {
                                $scope.funcionarios = data;
                            })
                            .error(function(data, status) {
                                Notification.error(data.errorMessage);
                                delete $scope.funcionarios;
                                delete $scope.agendamento.funcionarioId;
                            })
                        ;
                    }
                };

                $scope.novoAgendamento = function() {
                    modalAgendamento.modal('show');
                    delete $scope.agendamento;
                    delete $scope.funcionarios;

                    var moment = $('#calendar').fullCalendar('getDate');
                    $scope.agendamento = {
                        data:  moment.toDate()
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

                            Notification.success('Horario agendado com sucesso');
                        })
                        .error(function(data, status) {
                            var moment = $('#calendar').fullCalendar('getDate');
                            $scope.agendamento.data = moment.toDate();
                            Notification.error(data.errorMessage);
                        })
                    ;
                };

                $scope.atualizar = function(agendamento) {

                    agendaService.put(agendamento)
                        .success(function(data) {
                            $('#calendar').fullCalendar( 'refetchEvents' );

                            modalAgendamento.modal('hide');
                            delete $scope.agendamento;
                            delete $scope.funcionarios;

                            Notification.success('Horario atualizado com sucesso');
                        })
                        .error(function(data, status) {
                            var moment = $('#calendar').fullCalendar('getDate');
                            $scope.agendamento.data = moment.toDate();
                            Notification.error(data.errorMessage);
                        })
                    ;
                };

                init();
            }
        ])
    ;
})();