(function() {
    'use strict';

    angular
        .module('customeranalyticsApp')
        .controller('PersonDataDetailController', PersonDataDetailController);

    PersonDataDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonData'];

    function PersonDataDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonData) {
        var vm = this;

        vm.personData = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('customeranalyticsApp:personDataUpdate', function(event, result) {
            vm.personData = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
