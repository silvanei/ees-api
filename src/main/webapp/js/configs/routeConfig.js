/**
 * Created by silvanei on 18/08/16.
 */


(function () {
    'use strict';

    angular.module('agenda').config(['$stateProvider', '$urlRouterProvider', '$locationProvider', function($stateProvider, $urlRouterProvider, $locationProvider) {

        // default route
        $urlRouterProvider.otherwise("/");
        // configure html5 to get links working on jsfiddle
        $locationProvider.html5Mode(true);

        $stateProvider
            .state('agenda', {
                url: "/",
                views: {
                    'header': { templateUrl:"/view/header.html"},
                    'content': { controller: 'agendaController', templateUrl:"/view/agenda.html"},
                    'footer': { templateUrl:"/view/footer.html"}
                },
                authorize: true
            })
            .state('salao', {
                url: "/salao",
                views: {
                    'header': { templateUrl:"/view/header.html"},
                    'content': { controller: 'salaoController', templateUrl:"/view/salao.html"},
                    'footer': { templateUrl:"/view/footer.html"}
                }
            })
            .state('profissional', {
                url: "/profissional",
                views: {
                    'header': { templateUrl:"/view/header.html"},
                    'content': { controller: 'profissionalController', templateUrl:"/view/profissional.html"},
                    'footer': { templateUrl:"/view/footer.html"}
                }
            })
            .state('cliente', {
                url: "/cliente",
                views: {
                    'header': { templateUrl:"/view/header.html"},
                    'content': { controller: 'clienteController', templateUrl:"/view/cliente.html"},
                    'footer': { templateUrl:"/view/footer.html"}
                }
            })
            .state('login', {
                url: "/login",
                views: {
                    'content': { controller: 'loginController', templateUrl:"/view/login.html"}
                }
            })
            .state('logout', {
                url: "/logout",
                views: {
                    'content': { controller: 'loginController', templateUrl:"/view/login.html"}
                }
            })
            .state('registro', {
                url: "/registro",
                views: {
                    'content': { controller: 'registroController', templateUrl:"/view/registro.html"}
                }
            })
            .state('recuperar-senha', {
                url: "/recuperar-senha",
                views: {
                    'content': { controller: 'recuperarSenhaController', templateUrl:"/view/recuperar-senha.html"}
                }
            })
        ;
    }]);

})();