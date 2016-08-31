/**
 * Created by silvanei on 21/08/16.
 */


(function () {
    'use strict';

    angular
        .module('agenda')
        .value('config', {
            baseUrl: 'http://localhost:8080/rest',
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
            ]
        })
    ;

})();