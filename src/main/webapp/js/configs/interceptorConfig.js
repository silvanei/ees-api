/**
 * Created by silvanei on 16/08/16.
 */


(function () {
    'use strict';

    angular
        .module('agenda')
        .config(['$httpProvider', 'jwtOptionsProvider',
            function($httpProvider, jwtOptionsProvider){
                $httpProvider.interceptors.push('jwtInterceptor');
            }
        ]);
})();