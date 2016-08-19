/**
 * Created by silvanei on 18/08/16.
 */


(function () {
    'use strict';

    angular.module('agenda').config(['$stateProvider', '$urlRouterProvider', '$locationProvider', function($stateProvider, $urlRouterProvider, $locationProvider) {

        // default route
        $urlRouterProvider.otherwise("/login");
        // configure html5 to get links working on jsfiddle
        $locationProvider.html5Mode(true);

        $stateProvider
            .state('agenda', {
                url: "/agenda",
                templateUrl: '/view/agenda.html',
                controller: 'agendaController'
            })
            .state('salao', {
                url: "/salao",
                templateUrl: '/view/salao.html',
                controller: 'salaoController'
            })
            .state('profissional', {
                url: "/profissional",
                templateUrl: '/view/profissional.html',
                controller: 'profissionalController'
            })
            .state('cliente', {
                url: "/cliente",
                templateUrl: '/view/cliente.html',
                controller: 'clienteController'
            })
            .state('login', {
                url: "/login",
                templateUrl: '/view/login.html',
                controller: 'loginController'
            })
            .state('registro', {
                url: "/registro",
                templateUrl: '/view/registro.html',
                controller: 'registroController'
            })
            .state('recuperar-senha', {
                url: "/recuperar-senha",
                templateUrl: '/view//recuperar-senha.html',
                controller: 'recuperarSenhaController'
            })
        ;
    }]);

})();