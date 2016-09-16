/**
 * Created by silvanei on 20/08/16.
 */

//(function () {
//    'use strict';
//
//    angular
//        .module('agenda')
//        .factory('jwtInterceptor', ['$location', '$localStorage', '$q',
//            function($location, $localStorage, $q) {
//                return {
//                    request: function(request) {
//                        return request;
//
//                        if (request.url.substr(request.url.length - 5) == '.html') {
//                            return request;
//                        }
//
//                        if (request.url.substr(request.url.length - 5) == 'token') {
//                            return request;
//                        }
//
//
//                        request.headers = request.headers || {};
//
//                        if ($localStorage.currentUser) {
//                            request.headers['Authorization'] = 'Bearer ' + $localStorage.currentUser.token;
//                        }
//
//                        return request;
//
//                    },
//                    responseError: function(response) {
//                        if (response.status === 401) {
//                            $location.path('/login');
//                        }
//
//                        if (response.status === 403) {
//                            $('.modal-backdrop').remove();
//                            $location.path('/');
//                        }
//
//                        return $q.reject(response);
//                    }
//                }
//            }
//        ]);
//
//})();