(function() {
    'use strict';

    angular
        .module('customeranalyticsApp')
        .controller('PersonDataDeleteController',PersonDataDeleteController);

    PersonDataDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonData'];

    function PersonDataDeleteController($uibModalInstance, entity, PersonData) {
        var vm = this;

        vm.personData = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonData.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
