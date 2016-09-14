/**
 * Created by silvanei on 13/09/16.
 */


(function () {
    'use strict';

    angular
        .module('agenda')
        .directive('timeMin', function() {
            return {
                require: 'ngModel',
                link: function(scope, elm, attrs, ctrl) {

                    ctrl.$validators.timeMin = function(modelValue, viewValue) {

                        if(moment(attrs.timeMin, 'HH:mm').isSame(moment(viewValue, 'HH:mm'))) {
                            return true;
                        }

                        if(moment(attrs.timeMin, 'HH:mm').isBefore(moment(viewValue, 'HH:mm'))) {
                            return true;
                        }

                        return false;
                    };
                }
            };
        })
    ;
})();