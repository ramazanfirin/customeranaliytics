(function() {
    'use strict';

    angular
        .module('customeranalyticsApp')
        .controller('CameraDetailController', CameraDetailController);

    CameraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Camera'];

    function CameraDetailController($scope, $rootScope, $stateParams, previousState, entity, Camera) {
        var vm = this;

        vm.camera = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('customeranalyticsApp:cameraUpdate', function(event, result) {
            vm.camera = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
