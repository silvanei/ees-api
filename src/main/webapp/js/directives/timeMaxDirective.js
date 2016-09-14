/**
 * Created by silvanei on 13/09/16.
 */


(function () {
    'use strict';

    angular
        .module('agenda')
        .directive('timeMax', function() {
            return {
                require: 'ngModel',
                link: function(scope, elm, attrs, ctrl) {

                    ctrl.$validators.timeMax = function(modelValue, viewValue) {

                        if(moment(attrs.timeMax, 'HH:mm').isSame(moment(viewValue, 'HH:mm'))) {
                            return true;
                        }

                        if(moment(attrs.timeMax, 'HH:mm').isAfter(moment(viewValue, 'HH:mm'))) {
                            return true;
                        }

                        return false;
                    };
                }
            };
        })
    ;
})();