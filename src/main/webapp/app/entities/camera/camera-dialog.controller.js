(function() {
    'use strict';

    angular
        .module('customeranalyticsApp')
        .controller('CameraDialogController', CameraDialogController);

    CameraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Camera'];

    function CameraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Camera) {
        var vm = this;

        vm.camera = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.camera.id !== null) {
                Camera.update(vm.camera, onSaveSuccess, onSaveError);
            } else {
                Camera.save(vm.camera, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('customeranalyticsApp:cameraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
