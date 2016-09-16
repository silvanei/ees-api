/**
 * Created by silvanei on 15/09/16.
 */

(function() {
    'use strict';

    angular
        .module('agenda')
        .factory('imageFileService', ['$http', 'config', 'authManagerService', 'Notification',
            function($http, config, authManagerService, Notification) {
                function url() {
                    var salaoId = authManagerService.identity().salaoId;
                    var resourceUrl = config.baseUrl + '/v1/salao/'+salaoId+'/image';
                    return resourceUrl;
                }

                function post(fileModel) {
                    var fd = new FormData();
                    fd.append('file', fileModel);

                    return $http.post(url(), fd, {
                        transformRequest: angular.identity,
                        headers: {'Content-Type': undefined}
                    });
                }

                function get() {
                    return $http.get(url());
                }

                function excluir() {
                    return $http.delete(url());
                }

                return {
                    post: post,
                    get: get,
                    resourceUrl: url,
                    delete: excluir
                }

            }
        ])
    ;

})();