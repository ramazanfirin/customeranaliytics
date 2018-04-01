(function() {
    'use strict';

    angular
        .module('customeranalyticsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('genderReport', {
            parent: 'app',
            url: '/genderReport',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/reports/gender/genderReport.html',
                    controller: 'GenderReportController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
