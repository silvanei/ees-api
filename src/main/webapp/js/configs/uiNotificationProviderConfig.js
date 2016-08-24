/**
 * Created by silvanei on 16/08/16.
 */


(function () {
    'use strict';

    angular
        .module('agenda')
        .config(['NotificationProvider',
            function(NotificationProvider){
                console.log('NotificationProvider');
                NotificationProvider.setOptions({
                    delay: 10000,
                    startTop: 50,
                    startRight: 30,
//                    verticalSpacing: 20,
//                    horizontalSpacing: 20,
//                    positionX: 'top',
//                    positionY: 'rigth'
                });
            }
        ]);
})();