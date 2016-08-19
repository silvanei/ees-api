/**
 * Created by silvanei on 16/08/16.
 */

(function () {
    'use strict';

    angular.module('agenda').factory('AuthenticationService', function Service($http, $localStorage) {
        var service = {};

        service.Login = Login;
        service.Logout = Logout;

        return service;

        function Login(email, password, callback) {
            $http.post('http://localhost:9090/agenda/rest/v1/token', { email: email, password: password })
                .success(function (response) {
                    // login successful if there's a token in the response
                    if (response.token) {
                        // store username and token in local storage to keep user logged in between page refreshes
                        $localStorage.currentUser = { email: email, token: response.token };

                        // add jwt token to auth header for all requests made by the $http service
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.token;

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
        }

        function Logout() {
            // remove user from local storage and clear http auth header
            delete $localStorage.currentUser;
            $http.defaults.headers.common.Authorization = '';
        }
    });
})();