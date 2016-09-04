/**
 * Created by silvanei on 04/09/16.
 */

(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('menuController', ['$scope', '$location',
            function($scope, $location) {
                $scope.isActive = function (viewLocation) {
                    return viewLocation === $location.path();
                };
            }
        ]);

})();