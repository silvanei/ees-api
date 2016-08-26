/**
 * Created by silvanei on 25/08/16.
 */

(function () {
    'use strict';

    angular
        .module('agenda')
        .filter('minutesToHours', function(){

            return function(input, mask) {
                if(input) {
                    input = moment.duration(input, "minutes").format(mask || "h[h]mm[min]", { forceLength: true });
                }
                return input;
            }
        })
    ;
})();
