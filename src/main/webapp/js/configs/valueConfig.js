/**
 * Created by silvanei on 21/08/16.
 */


(function () {
    'use strict';

    angular
        .module('agenda')
        .value('config', {
            baseUrl: 'http://localhost:9090/agenda/rest',
            paginacao: {
                itensPorPagina: 10,
                maxSize: 5
            },
            diasDaSemana: [
                {id: 0, label: "Domingo" },
                {id: 1, label: "Segunda feira" },
                {id: 2, label: "Terça feira" },
                {id: 3, label: "Quarta feira" },
                {id: 4, label: "Quinta feira" },
                {id: 5, label: "Sexta feira" },
                {id: 6, label: "Sábado" }
            ],

            statusAgendamento: [
                {id: 1, label: "Reservado" },
                {id: 2, label: "Em Atendimento" },
                {id: 3, label: "Finalizado" },
                {id: 4, label: "Cancelado" },
                {id: 5, label: "Não Compareceu" }
            ],

            calendar: {
                schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source',
                lang: 'pt-BR',
                timezone: 'local',
                defaultView: 'agendaDay',
                slotDuration: '00:30:00', // Intervalo entr as linhas
                slotLabelFormat: 'H:mm',
                header: {
                    left: 'prev,next today title',
                    center: '',
                    right: 'agendaDay,agendaWeek,month'
                    //right: ''
                },
                resources: [],
                events: [],
                groupByResource: true,
                allDaySlot: false,
                editable: true,
                eventResize: function(event, delta, revertFunc) {
                    revertFunc();
                }
            }
        })
    ;

})();