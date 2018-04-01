(function() {
    'use strict';

    angular
        .module('customeranalyticsApp')
        .controller('GenderReportController', GenderReportController);

    GenderReportController.$inject = ['PersonData','Camera','$scope', 'Principal', 'LoginService', '$state'];

    function GenderReportController (PersonData,Camera,$scope, Principal, LoginService, $state) {
        var vm = this;
        vm.prepareReport = prepareReport;
        vm.startDate= new Date();
        vm.endDate=new Date();;
        
        
        
        prepareData();
        
        function prepareData(){
        	Camera.query(function(result) {
                vm.cameras = result;
                vm.searchQuery = null;
            });
        	vm.camera = 'ALL';
        }
        
        function prepareReport(){
        	vm.startDate;
        	vm.endDate;
        	var a;
        
        	var genderQueryVM = new Object();
        	genderQueryVM.startDate = vm.startDate;
        	genderQueryVM.endDate = vm.endDate;
        	genderQueryVM.camera = vm.camera;
        	
        	
        	
        	PersonData.getGenderReport(genderQueryVM,onSuccess, onError);
        }
        
        function onSuccess(result){
        	
        	vm.isSaving = false;
        }
        
        function onError(){
        	vm.isSaving = false;
        }
    }
})();
