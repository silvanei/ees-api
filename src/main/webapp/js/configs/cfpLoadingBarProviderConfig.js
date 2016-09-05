/**
 * Created by silvanei on 16/08/16.
 */


(function () {
    'use strict';

    angular
        .module('agenda')
        .config(['cfpLoadingBarProvider',
            function(cfpLoadingBarProvider){
                cfpLoadingBarProvider.includeSpinner = false;
            }
        ]);
})();