/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('profissionalController', ['$scope', 'config', '$log', 'profissionalService', 'Notification',
            function ($scope,config, $log, profissionalService, Notification) {

                function init() {
                    $('.nav-tabs a').click(function (e) {
                        e.preventDefault();
                        $(this).tab('show');
                    });

                    $scope.currentPage = 1;
                    $scope.itemsPerPage = config.paginacao.itensPorPagina;
                    $scope.maxSize = config.paginacao.maxSize;

                    $scope.diasDaSemana = config.diasDaSemana;

                    carregarLista();
                }

                function carregarLista() {
                    var limit = config.paginacao.itensPorPagina;
                    var offset = (config.paginacao.itensPorPagina * $scope.currentPage) - config.paginacao.itensPorPagina;

                    profissionalService.getAll(limit, offset).success(function(data) {
                        $scope.totalItems = data.count;
                        $scope.profissionais = data.items;
                    }).error(function(data, status) {
                        Notification.error(data.errorMessage);
                    });
                }

                function carregarDiasDaSemana() {
                    $scope.diasDaSemana = config.diasDaSemana.filter(function(dia) {
                        var selecionado = true;
                        $scope.profissional.horariosTrabalho.forEach(function(item) {
                            if(dia.id === item.diaDaSemana){
                                selecionado = false;
                            }
                        });

                        return selecionado;
                    });
                }

                $scope.adicionar = function () {
                    $('#modal-profissional').modal('show');
                    delete $scope.profissional;
                    $scope.dadosProfissionalForm.$setPristine();

                    delete $scope.horarioTrabalho;
                    $scope.horarioTrabalhoForm.$setPristine();

                    $scope.diasDaSemana = config.diasDaSemana;

                    $('#modal-profissional a[href="#dados"]').tab('show');
                };

                $scope.salvar = function (profissional) {
                    $log.log(profissional);

                    profissionalService.post(profissional).success(function(data) {
                        //$('#modal-profissional').modal('hide');
                        delete $scope.profissional;
                        $scope.dadosProfissionalForm.$setPristine();

                        delete $scope.horarioTrabalho;
                        $scope.horarioTrabalhoForm.$setPristine();

                        $scope.profissional = data;
                        carregarLista();

                        Notification.success('Profissional salvo com sucesso');

                    }).error(function(data, status) {
                        $log.error(data);

                        Notification.error(data.errorMessage);
                    });
                };

                $scope.editar = function (profissional) {
                    $scope.dadosProfissionalForm.$setPristine();
                    $scope.profissional = angular.copy(profissional);

                    delete $scope.horarioTrabalho;
                    $scope.horarioTrabalhoForm.$setPristine();

                    carregarDiasDaSemana();


                    $('#modal-profissional').modal('show');
                };

                $scope.atualizar = function(profissional) {
                    profissionalService.put(profissional).success(function() {
                        $('#modal-profissional').modal('hide');
                        delete $scope.profissional;
                        $scope.dadosProfissionalForm.$setPristine();
                        carregarLista();

                        Notification.success('Profissional atualizado com sucesso');

                    }).error(function(data, status) {
                        $log.error(data);

                        Notification.error(data.errorMessage);
                    });
                };

                $scope.confirmExcluir = function(profissional) {
                    $scope.profissional = angular.copy(profissional);
                    $('#excluir-profissional').modal('show');

                };

                $scope.excluir = function(profissional) {
                    profissionalService.delete(profissional).success(function() {
                        $('#excluir-profissional').modal('hide');
                        delete $scope.profissional;
                        $scope.dadosProfissionalForm.$setPristine();
                        carregarLista();

                        Notification.success('Profissional excluido com sucesso');
                    }).error(function(data, status) {
                        $log.error(data);

                        Notification.error(data.errorMessage);
                    });

                };

                $scope.adicionarServico = function () {
                    profissionalService.addService($scope.profissional, $scope.servico.selecionado).success(function(data) {
                        $scope.profissional = data;
                        carregarLista();

                        Notification.success('Serviço atribuído com sucesso');
                    }).error(function(data, status) {
                        $log.error(data);

                        Notification.error(data.errorMessage);
                    });
                };

                $scope.removeServico = function(idProfissional, idServico) {
                    profissionalService.removeService(idProfissional, idServico).success(function(data) {

                        profissionalService.get(idProfissional).success(function (data) {
                            $scope.profissional = data;

                        }).error(function(data, status) {
                            $log.error(data);

                            Notification.error(data.errorMessage);
                        });


                        Notification.success('Serviço removido com sucesso');

                    }).error(function(data, status) {
                        $log.error(data);

                        Notification.error(data.errorMessage);
                    });
                };

                $scope.removeHorario = function(horario) {
                    profissionalService.removeHorarioTrabalho(horario).success(function() {

                        profissionalService.get(horario.funcionarioId).success(function(data) {
                            $scope.profissional = data;
                            carregarDiasDaSemana();
                        });

                        carregarLista();

                        Notification.success('Horario removido com sucesso');
                    }).error(function(data, status) {
                        $log.error(data);
                        Notification.error(data.errorMessage);
                    });
                };

                $scope.adicionarHorario = function(funcionarioId, horario) {
                    profissionalService.addHorarioTrabalho(funcionarioId, horario).success(function(data) {

                        delete $scope.horarioTrabalho;
                        $scope.horarioTrabalhoForm.$setPristine();

                        profissionalService.get(data.funcionarioId).success(function(data) {
                            $scope.profissional = data;
                            carregarDiasDaSemana();
                        });

                        carregarLista();

                        Notification.success('Horario adicionado com sucesso');
                    }).error(function(data, status) {
                        $log.error(data);
                        Notification.error(data.errorMessage);
                    });
                };

                $scope.pageChanged = function() {
                    $log.log('Page changed to: ' + $scope.currentPage);
                    carregarLista();
                };

                init();
            }
        ])
    ;

})();