/**
 * Created by silvanei on 16/08/16.
 */


(function () {
    'use strict';

    angular
        .module('agenda')
        .config(['NotificationProvider',
            function(NotificationProvider){
                NotificationProvider.setOptions({
                    delay: 5000,
                    startTop: 55,
                    startRight: 20
                });
            }
        ]);
})();