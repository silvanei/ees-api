/**
 * Created by silvanei on 16/08/16.
 */

(function () {
    'use strict';

    angular
        .module('agenda')
        .factory('authenticationService', ['$http', '$localStorage',
            function($http, $localStorage) {

                var login = function(email, password, callback) {
                    $http.post('http://localhost:9090/agenda/rest/v1/token', { email: email, password: password })
                        .success(function (response) {
                            // login successful if there's a token in the response
                            if (response.token) {
                                // store username and token in local storage to keep user logged in between page refreshes
                                $localStorage.currentUser = { email: email, token: response.token };
                                // execute callback with true to indicate successful login
                                callback(true);
                            } else {
                                // execute callback with false to indicate failed login
                                callback(false);
                            }
                        })
                        .error(function(data) {
                            callback(false);
                        });
                };

                var logout = function() {
                    // remove user from local storage and clear http auth header
                    delete $localStorage.currentUser;
                };

                var getToken = function() {
                    if ($localStorage.currentUser) {
                        return $localStorage.currentUser.token;
                    }
                    return null;
                };

                return {
                    login: login,
                    logout: logout,
                    getToken: getToken
                }

            }
        ])
    ;
})();