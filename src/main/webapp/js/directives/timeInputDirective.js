/**
 * Created by silvanei on 21/08/16.
 */
// timeInputDirective

(function () {
    'use strict';

    angular
        .module('agenda')
        .directive('timeInput', function() {
            return {
                restrict : 'A',
                require: 'ngModel',
                link: function (scope, element, attrs, ctrl) {

                    element.bind('keyup', function(){
                        if(ctrl.$viewValue) {
                            var newDate = new Date(ctrl.$viewValue);
                            ctrl.$setViewValue(ctrl.$viewValue);
                            ctrl.$render();
                        }
                    });

                    ctrl.$parsers.push(function(value) {
                        return value;
                    });
                }
            }
        })
    ;

})();