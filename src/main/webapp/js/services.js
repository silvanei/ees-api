'use strict';

var services = angular.module('agenda.services', ['ngResource']);

services.factory('ServicoFactory', function($resource){

    return $resource('/rest/v1/salao/1/servico', {}, {
        query: {
            method: 'GET',
            params: {},
            isArray: false,
            headers: {
                'Authorization': 'Behar eyJhbGciOiJIUzI1NiJ9.eyJjbGkiOm51bGwsInNsYSI6MSwic3ViIjoiMSIsImV4cCI6MTQ3MTM2MjQzOSwiaWF0IjoxNDcxMjc2MDM5fQ.Ux4-cNmsaxOelGC6PSmrEV7ZOd7yvoAM53Yrq5jIF8E',
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }
    });
});