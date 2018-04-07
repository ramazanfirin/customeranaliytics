(function() {
    'use strict';

    angular
        .module('customeranalyticsApp')
        .controller('BarReportController', BarReportController);

    BarReportController.$inject = ['PersonData','Camera','$scope', 'Principal', 'LoginService', '$state'];

    function BarReportController (PersonData,Camera,$scope, Principal, LoginService, $state) {
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
        	
        	//vm.data= [{x:'2016-10-25', y:45}, {x:'2016-10-26', y:45},{x:'2016-10-27', y:50}];
        	vm.data= [{x:'2016-10-25', y:45}, {x:'2016-11-26', y:45},{x:'2016-12-27', y:50}];
        	
        	//vm.data= [20, 10];
        	
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
                    },
                    scales: {
                        xAxes: [{
                            type: 'time',
                            distribution: 'series',
                            time: {
                            	unit: 'month',
                                displayFormats: {
                                    quarter: 'MMM YYYY'
                                }
                            }
                            
                        }],
                        yAxes: [{
                           	ticks: {
                            	beginAtZero: true 
                             }
                          }]
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
        	
        	
        	
        	PersonData.getGenderReport(genderQueryVM,onGenderSuccess, onError);
        	PersonData.getAgeReport(genderQueryVM,onAgeSuccess, onError);
        	PersonData.getAgeGenderReport(genderQueryVM,onAgeGenderSuccess, onError);
        }
        
        function onGenderSuccess(result){
        	vm.genderlabels=[]
        	vm.genderData=[]
        	
        	for(var i=0;i<result.length;i++){
        		vm.genderLabels.push(result[i].gender)
        		vm.genderData.push(result[i].count)
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
