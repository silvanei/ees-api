var controllers = angular.module('agenda.controllers', []);

controllers.controller('ServicoController', ['$scope', 'ServicoFactory', function($scope, ServicoFactory) {
    ServicoFactory.query({limit: 2},
        function(data){
            console.log(data);
            $scope.teste = data;
        },
        function(data) {
            console.log(data);
            $scope.teste = data.data;
        }
    );
}]);