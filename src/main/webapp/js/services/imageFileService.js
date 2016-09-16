/**
 * Created by silvanei on 15/09/16.
 */

(function() {
    'use strict';

    angular
        .module('agenda')
        .factory('imageFileService', ['$http', 'config', 'authManagerService', 'Notification',
            function($http, config, authManagerService, Notification) {
                var salaoId = authManagerService.identity().salaoId;
                var resourceUrl = config.baseUrl + '/v1/salao/'+salaoId+'/image';

                function post(fileModel) {
                    var fd = new FormData();
                    fd.append('file', fileModel);

                    return $http.post(resourceUrl, fd, {
                        transformRequest: angular.identity,
                        headers: {'Content-Type': undefined}
                    });
                }

                function get() {
                    return $http.get(resourceUrl);
                }

                function excluir() {
                    return $http.delete(resourceUrl);
                }

                return {
                    post: post,
                    get: get,
                    resourceUrl: resourceUrl,
                    delete: excluir
                }

            }
        ])
    ;

})();