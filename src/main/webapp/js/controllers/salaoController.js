/**
 * Created by silvanei on 16/08/16.
 */
(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('salaoController', ['$scope',
            function ($scope) {

                function init() {
                    $('.nav-tabs a').click(function (e) {
                        e.preventDefault();
                        $(this).tab('show');
                    });

                    $scope.teste = 1472178069286;
                }

                init();
            }
        ])
    ;

})();