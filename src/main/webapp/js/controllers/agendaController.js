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

                    delete $scope.agendamento;
                    $scope.agendamento = {
                        id: 1,
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

                            Notification.success('Horario agendado com sucesso');
                        })
                        .error(function(data, status) {
                            $scope.agendamento.data = new Date();
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