(function() {
    'use strict';

    angular
        .module('customeranalyticsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('person-data', {
            parent: 'entity',
            url: '/person-data',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'customeranalyticsApp.personData.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-data/person-data.html',
                    controller: 'PersonDataController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personData');
                    $translatePartialLoader.addPart('aGE');
                    $translatePartialLoader.addPart('gENDER');
                    $translatePartialLoader.addPart('eMOTION');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('person-data-detail', {
            parent: 'person-data',
            url: '/person-data/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'customeranalyticsApp.personData.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/person-data/person-data-detail.html',
                    controller: 'PersonDataDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personData');
                    $translatePartialLoader.addPart('aGE');
                    $translatePartialLoader.addPart('gENDER');
                    $translatePartialLoader.addPart('eMOTION');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonData', function($stateParams, PersonData) {
                    return PersonData.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'person-data',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('person-data-detail.edit', {
            parent: 'person-data-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-data/person-data-dialog.html',
                    controller: 'PersonDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonData', function(PersonData) {
                            return PersonData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-data.new', {
            parent: 'person-data',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-data/person-data-dialog.html',
                    controller: 'PersonDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                insertDate: null,
                                age: null,
                                gender: null,
                                emotion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('person-data', null, { reload: 'person-data' });
                }, function() {
                    $state.go('person-data');
                });
            }]
        })
        .state('person-data.edit', {
            parent: 'person-data',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-data/person-data-dialog.html',
                    controller: 'PersonDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonData', function(PersonData) {
                            return PersonData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-data', null, { reload: 'person-data' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('person-data.delete', {
            parent: 'person-data',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/person-data/person-data-delete-dialog.html',
                    controller: 'PersonDataDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonData', function(PersonData) {
                            return PersonData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('person-data', null, { reload: 'person-data' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
