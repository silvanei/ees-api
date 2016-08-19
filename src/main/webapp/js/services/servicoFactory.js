'use strict';

angular.module('agenda.services', ['ngResource']);

angular.module('agenda.services').factory('ServicoFactory', function($resource){

    return $resource('/rest/v1/salao/1/servico', {}, {
        query: {
            method: 'GET',
            params: {},
            isArray: false,
            headers: {
                'Authorization': 'Behar eyJhbGciOiJIUzI1NiJ9.eyJjbGkiOm51bGwsInNsYSI6MSwic3ViIjoiMSIsImV4cCI6MTQ3MTQ1Mzg3NSwiaWF0IjoxNDcxMzY3NDc1fQ.pxflwkyxoiOhD3K4hM90JfvASfyzDZSzRoeWegyPMfc',
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }
    });
});