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
            vm.options={
            		title: {
                        display: true,
                        text: 'Cinsiyet Analizi'
                    },
                    legend: {
                        display: true,
                        labels: {
                            fontColor: 'rgb(255, 99, 132)'
                        }
                    }
            }
            
            vm.ageOptions={
            		title: {
                        display: true,
                        text: 'Yaş Analizi'
                    },
                    legend: {
                        display: true,
                        labels: {
                            fontColor: 'rgb(255, 99, 132)'
                        }
                    }
            }
        
            vm.ageGenderOptions={
            		title: {
                        display: true,
                        text: 'Yaş - Cinsiyet Analizi'
                    },
                    legend: {
                        display: true,
                        labels: {
                            fontColor: 'rgb(255, 99, 132)'
                        }
                    }
            }
            
            vm.colors= [
            	"#97BBCD",
            	"#F7464A"

            ]
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
        	PersonData.getAgeReport(genderQueryVM,onAgeSuccess, onError);
        	PersonData.getAgeGenderReport(genderQueryVM,onAgeGenderSuccess, onError);
        }
        
        function onSuccess(result){
        	vm.labels=[]
        	vm.data=[]
        	
        	for(var i=0;i<result.length;i++){
        		vm.labels.push(result[i].gender)
        		vm.data.push(result[i].count)
        	}
        	
        	vm.isSaving = false;
        }
        
        function onAgeSuccess(result){
        	vm.ageLabels=[]
        	vm.ageData=[]
        	
        	for(var i=0;i<result.length;i++){
        		vm.ageLabels.push(result[i].age)
        		vm.ageData.push(result[i].count)
        	}
        	
        	vm.isSaving = false;
        }
        
        function onAgeGenderSuccess(result){
        	vm.ageGenderLabels=[]
        	vm.ageGenderData=[]
        	
        	for(var i=0;i<result.length;i++){
        		vm.ageGenderLabels.push(result[i].age+" "+result[i].gender)
        		vm.ageGenderData.push(result[i].count)
        	}
        	
        	vm.isSaving = false;
        }
        
        function onError(){
        	vm.isSaving = false;
        }
    }
})();
