/**
 * Created by silvanei on 16/08/16.
 */


(function () {
    'use strict';

    angular
        .module('agenda')
        .config(['jwtOptionsProvider',
            function(jwtOptionsProvider){
                // Please note we're annotating the function so that the $injector works when the file is minified
                jwtOptionsProvider.config({
                    tokenGetter: ['authenticationService', 'options', function(authenticationService, options) {
                        if (options.url.substr(options.url.length - 5) == '.html') {
                            return null;
                        }

                        return authenticationService.getToken();
                    }],
                    whiteListedDomains: ['http://localhost:8080']
                });
            }
        ]);
})();