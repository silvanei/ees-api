/**
 * Created by silvanei on 16/09/16.
 */

(function () {
    'use strict';

    angular
        .module('agenda')
        .controller('nomeSalaoController', ['salaoService', '$rootScope',
            function(salaoService, $rootScope) {

                function init() {
                    if(!$rootScope.nomeSalao) {
                        salaoService.get().success(function(data) {
                            console.log(data);
                            $rootScope.nomeSalao = data.nome;
                        });
                    }

                }

                init();
            }
        ]);

})();
