'use strict';

angular.module('iron-test').controller('TestcasesController', ['$scope', 'Testcases', 'Teststeps', 'Testruns',
    '$stateParams', '$state', 'uiGridConstants', '$timeout', 'IronTestUtils',
  function($scope, Testcases, Teststeps, Testruns, $stateParams, $state, uiGridConstants, $timeout, IronTestUtils) {
    $scope.saveSuccessful = null;
    var timer;
    $scope.autoSave = function(isValid) {
      if (timer) $timeout.cancel(timer);
      timer = $timeout(function() {
        $scope.update(isValid);
      }, 2000);
    };

    $scope.testcaseGridColumnDefs = [
      {
        name: 'name', width: 200, minWidth: 100,
        sort: {
          direction: uiGridConstants.ASC,
          priority: 1
        },
        cellTemplate: 'testcaseGridNameCellTemplate.html'
      },
      {name: 'description', width: 600, minWidth: 300},
      {
        name: 'delete', width: 100, minWidth: 80, enableSorting: false, enableFiltering: false,
        cellTemplate: 'testcaseGridDeleteCellTemplate.html'
      }
    ];

    $scope.teststepGridColumnDefs = [
      {
        name: 'id', displayName: 'ID', width: 55, minWidth: 55,
        sort: { direction: uiGridConstants.ASC, priority: 1 }
      },
      {
        name: 'name', width: 200, minWidth: 100,
        cellTemplate: 'teststepGridNameCellTemplate.html'
      },
      {name: 'type', width: 80, minWidth: 80},
      {name: 'description', width: 500, minWidth: 300},
      {
        name: 'delete', width: 100, minWidth: 80, enableSorting: false, enableFiltering: false,
        cellTemplate: 'teststepGridDeleteCellTemplate.html'
      },
      {
        name: 'result', width: 100, minWidth: 80,
        cellTemplate: 'teststepGridResultCellTemplate.html'
      }
    ];

    $scope.update = function(isValid) {
      if (isValid) {
        $scope.testcase.$update(function(response) {
          $scope.saveSuccessful = true;
          $scope.testcase = response;
        }, function(response) {
          IronTestUtils.openErrorMessageModal(response);
        });
      } else {
        $scope.submitted = true;
      }
    };

    $scope.create = function(isValid) {
      if (isValid) {
        var testcase = new Testcases({
          name: this.name,
          description: this.description
        });
        testcase.$save(function(response) {
          $state.go('testcase_edit', {testcaseId: response.id});
        }, function(response) {
          IronTestUtils.openErrorMessageModal(response);
        });
      } else {
        $scope.submitted = true;
      }
    };

    $scope.remove = function(testcase) {
      testcase.$remove(function(response) {
        $state.go($state.current, {}, {reload: true});
      }, function(response) {
        IronTestUtils.openErrorMessageModal(response);
      });
    };

    $scope.run = function() {
      var testrun = new Testruns({
        testcaseId: $scope.testcase.id
      });
      testrun.$save(function(response) {
        $scope.failedTeststepIds = response.failedTeststepIds;
      },function(response) {
        IronTestUtils.openErrorMessageModal(response);
      });
    };

    $scope.removeTeststep = function(teststep) {
      var teststepService = new Teststeps(teststep);
      teststepService.$remove(function(response) {
        $state.go($state.current, {}, {reload: true});
      }, function(response) {
        IronTestUtils.openErrorMessageModal(response);
      });
    };

    $scope.find = function() {
      Testcases.query(function(testcases) {
        $scope.testcases = testcases;
      }, function(response) {
        IronTestUtils.openErrorMessageModal(response);
      });
    };

    $scope.findOne = function() {
      Testcases.get({
        testcaseId: $stateParams.testcaseId
      }, function(testcase) {
        $scope.testcase = testcase;
      }, function(response) {
        IronTestUtils.openErrorMessageModal(response);
      });
    };
  }
]);
