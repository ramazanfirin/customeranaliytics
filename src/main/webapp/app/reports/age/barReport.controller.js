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
        	vm.series2=[];
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
        	vm.data2=[];
        	
        	vm.camera = 'ALL';
        	
        	//vm.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];
        	vm.colors= [
            	"#97BBCD",
            	"#F7464A",
            	"#66ffcc"

            ];
        	
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
            
         
            
        }
        
        function prepareReport(){
        	vm.data=[];
        	vm.data2=[];
        	vm.series=[];
        	vm.series2=[];
//        	
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
        	PersonData.timeSeriesGenderReportAll(genderQueryVM,timeSeriesGenderReportAll, onError);
        	
        	//******************************************************************
        	
        	var g1= angular.copy(genderQueryVM);
        	g1.gender='MALE';
        	g1.age="CHILD";
        	PersonData.timeSeriesAgeAndGenderReportAll(g1,timeSeriesGenderAndAgeReportMaleChild, onError);
        	
        	var g2= angular.copy(genderQueryVM);
        	g2.gender='FEMALE';
        	g2.age="CHILD";
        	PersonData.timeSeriesAgeAndGenderReportAll(g2,timeSeriesGenderAndAgeReportFemaleChild, onError);
        	
        	var g3= angular.copy(genderQueryVM);
        	g3.gender='MALE';
        	g3.age="YOUNG";
        	PersonData.timeSeriesAgeAndGenderReportAll(g3,timeSeriesGenderAndAgeReportMaleYoung, onError);
        	
        	var g4= angular.copy(genderQueryVM);
        	g4.gender='FEMALE';
        	g4.age="YOUNG";
        	PersonData.timeSeriesAgeAndGenderReportAll(g4,timeSeriesGenderAndAgeReportFemaleYoung, onError);
        	
        	var g5= angular.copy(genderQueryVM);
        	g5.gender='MALE';
        	g5.age="MIDDLE";
        	PersonData.timeSeriesAgeAndGenderReportAll(g5,timeSeriesGenderAndAgeReportMaleMiddle, onError);
        	
        	var g6= angular.copy(genderQueryVM);
        	g6.gender='FEMALE';
        	g6.age="MIDDLE";
        	PersonData.timeSeriesAgeAndGenderReportAll(g6,timeSeriesGenderAndAgeReportFemaleMiddle, onError);
        	
        	var g7= angular.copy(genderQueryVM);
        	g7.gender='MALE';
        	g7.age="OLDER";
        	PersonData.timeSeriesAgeAndGenderReportAll(g7,timeSeriesGenderAndAgeReportMaleOlder, onError);
        	
        	var g8= angular.copy(genderQueryVM);
        	g8.gender='FEMALE';
        	g8.age="OLDER";
        	PersonData.timeSeriesAgeAndGenderReportAll(g8,timeSeriesGenderAndAgeReportFemaleOlder, onError);
            
        	var g8= angular.copy(genderQueryVM);
        	PersonData.timeSeriesGenderReportAll(genderQueryVM,genderReportAll, onError);
        	
        }
        //************************
        function genderReportAll(result){
        	timeSeriesGenderReportAll=[]
        	
        	for(var i=0;i<result.length;i++){
        		var temp = new Object();
        		temp.x = result[i].date;
        		temp.y = result[i].count;
        		timeSeriesGenderReportAll.push(temp)
        	}
        	vm.data2.push(timeSeriesGenderReportAll);
        	vm.series2.push('ALL');
        	vm.isSaving = false;
        }
        
        //************************************************************
        function timeSeriesGenderAndAgeReportMaleChild(result){
           	var timeSeriesGenderAndAgeReportMaleChild=[]
     	
        	for(var i=0;i<result.length;i++){
        		var temp = new Object();
        		temp.x = result[i].date;
        		temp.y = result[i].count;
        		timeSeriesGenderAndAgeReportMaleChild.push(temp)
        	}
        	vm.data2.push(timeSeriesGenderAndAgeReportMaleChild);
        	vm.series2.push('MALE_CHILD');
        	vm.isSaving = false;
        }
        
        function timeSeriesGenderAndAgeReportFemaleChild(result){
           	var timeSeriesGenderAndAgeReportFemaleChild=[]
     	
        	for(var i=0;i<result.length;i++){
        		var temp = new Object();
        		temp.x = result[i].date;
        		temp.y = result[i].count;
        		timeSeriesGenderAndAgeReportFemaleChild.push(temp)
        	}
        	vm.data2.push(timeSeriesGenderAndAgeReportFemaleChild);
        	vm.series2.push('FEMALE_CHILD');
        	vm.isSaving = false;
        }
        //****************************************************************************
        function timeSeriesGenderAndAgeReportMaleYoung(result){
           	var timeSeriesGenderAndAgeReportMaleYoung=[]
     	
        	for(var i=0;i<result.length;i++){
        		var temp = new Object();
        		temp.x = result[i].date;
        		temp.y = result[i].count;
        		timeSeriesGenderAndAgeReportMaleYoung.push(temp)
        	}
        	vm.data2.push(timeSeriesGenderAndAgeReportMaleYoung);
        	vm.series2.push('MALE_YOUNG');
        	vm.isSaving = false;
        }
        
        function timeSeriesGenderAndAgeReportFemaleYoung(result){
           	var timeSeriesGenderAndAgeReportFemaleYoung=[]
     	
        	for(var i=0;i<result.length;i++){
        		var temp = new Object();
        		temp.x = result[i].date;
        		temp.y = result[i].count;
        		timeSeriesGenderAndAgeReportFemaleYoung.push(temp)
        	}
        	vm.data2.push(timeSeriesGenderAndAgeReportFemaleYoung);
        	vm.series2.push('FEMALE_YOUNG');
        	vm.isSaving = false;
        }
        
        
        //*****************************************************************************
        
        function timeSeriesGenderAndAgeReportMaleMiddle(result){
           	var timeSeriesGenderAndAgeReportMaleMiddle=[]
     	
        	for(var i=0;i<result.length;i++){
        		var temp = new Object();
        		temp.x = result[i].date;
        		temp.y = result[i].count;
        		timeSeriesGenderAndAgeReportMaleMiddle.push(temp)
        	}
        	vm.data2.push(timeSeriesGenderAndAgeReportMaleMiddle);
        	vm.series2.push('MALE_MIDDLE');
        	vm.isSaving = false;
        }
        
        function timeSeriesGenderAndAgeReportFemaleMiddle(result){
           	var timeSeriesGenderAndAgeReportFemaleMiddle=[]
     	
        	for(var i=0;i<result.length;i++){
        		var temp = new Object();
        		temp.x = result[i].date;
        		temp.y = result[i].count;
        		timeSeriesGenderAndAgeReportFemaleMiddle.push(temp)
        	}
        	vm.data2.push(timeSeriesGenderAndAgeReportFemaleMiddle);
        	vm.series2.push('FEMALE_MIDDLE');
        	vm.isSaving = false;
        }
        
        
        //*******************************************************************************
        
        function timeSeriesGenderAndAgeReportMaleOlder(result){
           	var timeSeriesGenderAndAgeReportMaleOlder=[]
     	
        	for(var i=0;i<result.length;i++){
        		var temp = new Object();
        		temp.x = result[i].date;
        		temp.y = result[i].count;
        		timeSeriesGenderAndAgeReportMaleOlder.push(temp)
        	}
        	vm.data2.push(timeSeriesGenderAndAgeReportMaleOlder);
        	vm.series2.push('MALE_OLDER');
        	vm.isSaving = false;
        }
        
        function timeSeriesGenderAndAgeReportFemaleOlder(result){
           	var timeSeriesGenderAndAgeReportFemaleOlder=[]
     	
        	for(var i=0;i<result.length;i++){
        		var temp = new Object();
        		temp.x = result[i].date;
        		temp.y = result[i].count;
        		timeSeriesGenderAndAgeReportFemaleOlder.push(temp)
        	}
        	vm.data2.push(timeSeriesGenderAndAgeReportFemaleOlder);
        	vm.series2.push('FEMALE_OLDER');
        	vm.isSaving = false;
        }
        
        //********************************************************************************
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
        	
        	timeSeriesGenderReportAll=[]
        	
        	for(var i=0;i<result.length;i++){
        		var temp = new Object();
        		temp.x = result[i].date;
        		temp.y = result[i].count;
        		timeSeriesGenderReportAll.push(temp)
        	}
        	vm.data.push(timeSeriesGenderReportAll);
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
