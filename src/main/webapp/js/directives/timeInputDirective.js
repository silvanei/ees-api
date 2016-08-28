/**
 * Created by silvanei on 21/08/16.
 */
// timeInputDirective

(function () {
    'use strict';

    angular
        .module('agenda')
        .directive('timeInput', ['$filter', function($filter) {
            return {
                restrict : 'A',
                require: 'ngModel',
                link: function (scope, element, attrs, ctrl) {

                    element.css("text-align", "center");
                    element.css("width", "70px");

                    function formatHour(time) {
                        time = time.replace(/[^0-9]+/g, "");
                        if(time.length === 1) {
                            if(time.substring(0) > 2) {
                                return '';
                            }
                        }

                        if(time.length === 2) {
                            if(time.substring(0) > 23) {
                                return time.substring(0, 1);
                            }
                        }

                        if(time.length === 3) {
                            if (time.substring(2, 3) > 5) {
                                return time.substring(0, 2) + ":";
                            }
                        }

                        if(time.length === 4) {
                            if (time.substring(2, 4) > 59) {
                                return time.substring(0, 2) + ":" + time.substring(2, 3);
                            }
                        }

                        if(time.length > 2) {
                            time = time.substring(0,2) + ":" + time.substring(2);
                        }

                        if(time.length > 5) {
                            time = time.substring(0, 5);
                        }
                        return time;
                    }

                    element.bind('keyup', function(){
                        if(ctrl.$viewValue) {
                            ctrl.$setViewValue(formatHour(ctrl.$viewValue));
                            ctrl.$render();
                        }
                    });

                    // Não permite copiar nen=m colar
                    element.bind('cut copy paste', function(e){
                        e.preventDefault();
                    });

                    ctrl.$parsers.push(function(value) {

                        if(value.length === 5) {
                            var time = value.split(":");
                            // new Date(ano, mês, dia, hora, minuto, segundo, milissegundo);
                            var date = new Date(70, 0, 1, parseInt(time[0]), parseInt(time[1]), 0, 0);
                            return date.getTime();
                        }
                    });

                    ctrl.$formatters.push(function(value) {
                        return $filter('date')(value, 'HH:mm');
                    });
                }
            }
        }])
    ;

})();