(function() {
    'use strict';
    angular
        .module('customeranalyticsApp')
        .factory('PersonData', PersonData);

    PersonData.$inject = ['$resource', 'DateUtils'];

    function PersonData ($resource, DateUtils) {
        var resourceUrl =  'api/person-data/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.insertDate = DateUtils.convertLocalDateFromServer(data.insertDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.insertDate = DateUtils.convertLocalDateToServer(copy.insertDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.insertDate = DateUtils.convertLocalDateToServer(copy.insertDate);
                    return angular.toJson(copy);
                }
            },
            'getGenderReport': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startDate = DateUtils.convertLocalDateToServer(copy.startDate);
                    copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
                    return angular.toJson(copy);
                },
                isArray: true,
                url:'/api/person-data/reports'
            },
            'getAgeReport': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startDate = DateUtils.convertLocalDateToServer(copy.startDate);
                    copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
                    return angular.toJson(copy);
                },
                isArray: true,
                url:'/api/person-data/agereports'
            },
            'getAgeGenderReport': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startDate = DateUtils.convertLocalDateToServer(copy.startDate);
                    copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
                    return angular.toJson(copy);
                },
                isArray: true,
                url:'/api/person-data/agegenderreports'
            },
            
            'timeSeriesGenderReport': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startDate = DateUtils.convertLocalDateToServer(copy.startDate);
                    copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
                    return angular.toJson(copy);
                },
                isArray: true,
                url:'/api/person-data/timeSeriesGenderReport'
            },
            
            'timeSeriesGenderReportAll': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startDate = DateUtils.convertLocalDateToServer(copy.startDate);
                    copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
                    return angular.toJson(copy);
                },
                isArray: true,
                url:'/api/person-data/timeSeriesGenderReportAll'
            },
            
            'timeSeriesAgeAndGenderReportAll': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startDate = DateUtils.convertLocalDateToServer(copy.startDate);
                    copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
                    return angular.toJson(copy);
                },
                isArray: true,
                url:'/api/person-data/timeSeriesAgeAndGenderReportAll'
            },
        });
    }
})();
