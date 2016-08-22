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
                itensPorPagina: 2,
                maxSize: 5
            }
        })
    ;

})();