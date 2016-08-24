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
            }
        })
    ;

})();