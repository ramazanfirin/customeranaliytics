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
        
        
        
        //prepareData();
        prepareData2();
        
        function prepareData2(){
//        	vm.labels = ["January", "February", "March", "April", "May", "June", "July"];
//        	vm.series = ['Series A', 'Series B'];
        	vm.series=[];
//        	vm.data = [
//        	    [65, 59, 80, 81, 56, 55, 40],
//        	    [28, 48, 40, 19, 86, 27, 90]
//        	  ];
        	
//        	vm.data= [
//        			[{x:'2016-10-25', y:45}, {x:'2016-11-26', y:45},{x:'2016-12-27', y:50}],
//        			[{x:'2016-10-25', y:145}, {x:'2016-11-26', y:145},{x:'2016-12-27', y:150}]
//        			
//        			];
        	vm.data=[];
        	vm.camera = 'ALL';
        	
        	//vm.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];
        	vm.options = {
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
                            distribution: 'linear',
                            time: {
                            	unit: 'day',
                                displayFormats: {
                                    quarter: 'MMM YYYY'
                                }
                            }
                            
                        }],
        		      yAxes: [
//        		        {
//        		          id: 'y-axis-1',
//        		          type: 'linear',
//        		          display: true,
//        		          position: 'left'
//        		        },
//        		        {
//        		          id: 'y-axis-2',
//        		          type: 'linear',
//        		          display: true,
//        		          position: 'right'
//        		        }
//        		        ,	
        		        {
        		        	ticks: {
                        	beginAtZero: true 
                        }
        		        }
        		      ]
        		    }
        		  };
        	
        }
        
        
        
        function prepareData(){
        	prepareData2();
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
        	vm.data=[];
        	vm.startDate;
        	vm.endDate;
        	var a;
        
        	var genderQueryVM = new Object();
        	genderQueryVM.startDate = vm.startDate;
        	genderQueryVM.endDate = vm.endDate;
        	genderQueryVM.camera = vm.camera;
        	genderQueryVM.gender='MALE';
        	
        	var genderQueryVMFemale= angular.copy(genderQueryVM);
        	genderQueryVMFemale.gender='FEMALE'
        	
        	PersonData.timeSeriesGenderReport(genderQueryVM,timeSeriesGenderReportMale, onError);
        	
        	
        	PersonData.timeSeriesGenderReport(genderQueryVMFemale,timeSeriesGenderReportFemale, onError);	
//            
//        	PersonData.timeSeriesGenderReportAll(genderQueryVM,timeSeriesGenderReportAll, onError);
        	
        }
        
        function timeSeriesGenderReportMale(result){
        	
        	var timeSeriesGenderReportMale=[]
     	
        	for(var i=0;i<result.length;i++){
        		var temp = new Object();
        		temp.x = result[i].date;
        		temp.y = result[i].count;
        		timeSeriesGenderReportMale.push(temp)
        	}
        	vm.data.push(timeSeriesGenderReportMale);
        	vm.series.push('MALE');
        	vm.isSaving = false;
        }
        
        function timeSeriesGenderReportFemale(result){
        	
        	var timeSeriesGenderReportFemale=[]
        	
        	for(var i=0;i<result.length;i++){
        		var temp = new Object();
        		temp.x = result[i].date;
        		temp.y = result[i].count;
        		timeSeriesGenderReportFemale.push(temp)
        	}
        	vm.data.push(timeSeriesGenderReportFemale);
        	vm.series.push('FEMALE');
        	vm.isSaving = false;
        }
        
        function timeSeriesGenderReportAll(result){
        	
        	vm.timeSeriesGenderReportAll=[]
        	
        	for(var i=0;i<result.length;i++){
        		var temp = new Object();
        		temp.x = result[i].insertDate;
        		temp.y = result[i].count;
        		vm.timeSeriesGenderReportAll.push(result[i].count)
        	}
        	vm.data.push(vm.timeSeriesGenderReportAll);
        	vm.series.push('ALL');
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
