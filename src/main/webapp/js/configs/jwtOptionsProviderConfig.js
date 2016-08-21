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
                    tokenGetter: ['authenticationService', function(authenticationService) {
                        //if (options.url.substr(options.url.length - 5) == '.html') {
                        //    return null;
                        //}

                        return authenticationService.getToken();
                        //return "eyJhbGciOiJIUzI1NiJ9.eyJjbGkiOm51bGwsInNsYSI6MSwic3ViIjoiMSIsImV4cCI6MTQ3MTQ1Mzg3NSwiaWF0IjoxNDcxMzY3NDc1fQ.pxflwkyxoiOhD3K4hM90JfvASfyzDZSzRoeWegyPMfc";
                    }],
                    whiteListedDomains: ['localhost'],
                    unauthenticatedRedirector: ['$state', function($state) {
                        $state.go('login');
                    }]
                });
            }
        ]);
})();