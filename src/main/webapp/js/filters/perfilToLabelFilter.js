/**
 * Created by silvanei on 25/08/16.
 */

(function () {
    'use strict';

    angular
        .module('agenda')
        .filter('perfilToLabel', function(){

            return function(input) {
                switch (input) {
                    case "SALAO_ADMIN" : return "Administrador";
                    case "SALAO_PROFISSIONAL" : return "Profissional";
                    default: return "";
                }
            }
        })
    ;
})();
