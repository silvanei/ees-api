/**
 * Created by silvanei on 16/08/16.
 */


(function () {
    'use strict';

    angular
        .module('agenda')
        .config(['$httpProvider','jwtInterceptorProvider', 'jwtOptionsProvider',
            function($httpProvider, jwtInterceptorProvider, jwtOptionsProvider){

                jwtInterceptorProvider.tokenGetter = function(jwtHelper, $http, authenticationService, $localStorage, config) {
                    var jwt = authenticationService.getToken();
                    console.log(jwt);
                    if(jwt){
                        if(jwtHelper.isTokenExpired(jwt)){
                            return $http({
                                url : config.baseUrl + '/v1/token',
                                skipAuthorization : true,
                                method: 'PUT',
                                headers : { Authorization : 'Bearer '+ jwt}
                            }).then(function(response){
                                $localStorage.currentUser.token = response.data.token;
                                return response.data.token;
                            },function(response){
                                authenticationService.logout();
                            });
                        }else{
                            return jwt;
                        }
                    }
                };

                jwtOptionsProvider.config({
                        whiteListedDomains: ['localhost', '192.168.0.13', 'ees-api.herokuapp.com']
                });

                $httpProvider.interceptors.push('jwtInterceptor');
            }
        ]);
})();