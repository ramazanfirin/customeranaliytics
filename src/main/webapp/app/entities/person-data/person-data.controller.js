(function() {
    'use strict';

    angular
        .module('customeranalyticsApp')
        .controller('PersonDataController', PersonDataController);

    PersonDataController.$inject = ['PersonData'];

    function PersonDataController(PersonData) {

        var vm = this;

        vm.personData = [];

        loadAll();

        function loadAll() {
            PersonData.query(function(result) {
                vm.personData = result;
                vm.searchQuery = null;
            });
        }
    }
})();
