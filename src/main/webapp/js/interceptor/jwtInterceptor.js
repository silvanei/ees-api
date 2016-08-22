/**
 * Created by silvanei on 20/08/16.
 */

(function () {
    'use strict';

    angular
        .module('agenda')
        .factory('jwtInterceptor', ['$location', '$localStorage', '$q',
            function($location, $localStorage, $q) {
                return {
                    request: function(request) {

                        if (request.url.substr(request.url.length - 5) == '.html') {
                            return request;
                        }

                        if (request.url.substr(request.url.length - 5) == 'token') {
                            return request;
                        }


                        request.headers = request.headers || {};

                        if ($localStorage.currentUser) {
                            request.headers['Authorization'] = 'Bearer ' + $localStorage.currentUser.token;
                        }

                        return request;

                    },
                    responseError: function(response) {
                        if (response.status === 401 || response.status === 403) {
                            $location.path('/login');
                        }

                        return $q.reject(response);
                    }
                }
            }
        ]);

})();