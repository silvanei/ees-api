/**
 * Created by silvanei on 25/08/16.
 */

(function () {
    'use strict';

    angular
        .module('agenda')
        .filter('diasSemanaToLabel', function(){

            return function(input) {
                switch (input) {
                    case 0 : return "Domingo";
                    case 1 : return "Segunda feira";
                    case 2 : return "Terça feira";
                    case 3 : return "Quarta feira";
                    case 4 : return "Quinta feira";
                    case 5 : return "Sexta feira";
                    case 6 : return "Sábado";
                    default: return "";
                }
            }
        })
    ;
})();
