/**
 * Created by vano on 24.03.16.
 */
app.controller('MyTasksController', function ($scope, $location, $rootScope, TaskFactory, $http, $uibModal) {

    var vm = $scope.vm = this;

    vm.myTasksTodo = [];
    vm.myTasksDoing = [];
    vm.myTasksDone = [];

    vm.updateAll = function(){
        TaskFactory.getMyTasksTodo({from: 0}, function (response) {
            vm.myTasksTodo = response.list;
            vm.myTasksTodoTotal = response.total;
        });
        TaskFactory.getMyTasksDoing({from: 0}, function (response) {
            vm.myTasksDoing = response.list;
            vm.myTasksDoingTotal = response.total;
        });
        TaskFactory.getMyTasksDone({from: 0}, function (response) {
            vm.myTasksDone = response.list;
            vm.myTasksDoneTotal = response.total;
        });
    };

    vm.updateAll();

    vm.modalEditTaskOpen = function(id){

        vm.taskId = id;
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'resources/tpl/modals/editTask.html',
            controller: 'TaskControllerModal',
            size: 'lg',
            scope:  $scope,
            resolve: {
                taskId: id
            }
        });

        modalInstance.result.then(function () {
            vm.updateAll();
        }, function () {
            //close
        });
    };

   /* vm.updateTask = function () {
        TaskFactory.update(vm.editTask, function () {
            vm.updateAll();
            vm.editsite = {};
        });

        jQuery('#modalEditTask.modal').modal('hide');
    };*/


});
