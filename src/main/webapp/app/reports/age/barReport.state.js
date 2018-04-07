(function() {
    'use strict';

    angular
        .module('customeranalyticsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('barReport', {
            parent: 'app',
            url: '/barReport',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/reports/age/barReport.html',
                    controller: 'BarReportController',
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
