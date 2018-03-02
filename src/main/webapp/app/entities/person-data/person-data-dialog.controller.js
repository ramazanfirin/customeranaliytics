(function() {
    'use strict';

    angular
        .module('customeranalyticsApp')
        .controller('PersonDataDialogController', PersonDataDialogController);

    PersonDataDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonData'];

    function PersonDataDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonData) {
        var vm = this;

        vm.personData = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personData.id !== null) {
                PersonData.update(vm.personData, onSaveSuccess, onSaveError);
            } else {
                PersonData.save(vm.personData, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('customeranalyticsApp:personDataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.insertDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
