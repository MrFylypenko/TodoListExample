/**
 * Created by Vano on 23.02.2016.
 */
app.controller('TaskController', function ($location, $rootScope, $scope, $http, TaskFactory, FileUploadService, tasks, $route, $uibModal, $log) {
    console.log($route);

    var vm = $scope.vm = this;

    vm.tasks = [];

    vm.countValues = [2, 5, 10, 20, 50, 100];
    vm.count = 10;
    vm.page = 0;
    vm.ready = false;
    vm.field = 'name';

    vm.pageLength = 0;

    vm.total = tasks.total;
    vm.tasks = tasks.list;

    vm.modalEditTaskOpen = function(id){
        console.log("open Task");

        vm.taskId = id;
        //vm.taskId = 500;

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

            vm.getAll();
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });

        //return modalInstance.result;


    };


    vm.addTask = function () {
        var id = 0;
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'resources/tpl/modals/addTask.html',
            controller: 'TaskControllerModal',
            size: 'lg',
            scope: $scope,
            resolve: {
                taskId: id
            }
        });
        modalInstance.result.then(function () {
            vm.getAll();
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });

    };


    vm.deleteTask = function (id) {
        TaskFactory.delete({taskId: id}, function () {
            vm.getAll();
        });
    };


    //task list navigation
    vm.previous = function (bolt) {
        if (vm.page == 0) return false;
        if (bolt) vm.page = 0;
        else vm.page--;
        vm.getAll();
    };

    vm.next = function (bolt) {
        if (bolt && vm.pageLength >= vm.page + 1) {
            vm.page = vm.pageLength;
            vm.getAll();
        }

        if (vm.pageLength >= vm.page + 1) {
            vm.page++;
            vm.getAll();
        }
    };

    //TODO search by project name
    /*vm.search = function (event) {
     if (!event || event.keyCode == 13) vm.getAll();
     };*/

    vm.getAll = function () {
        vm.ready = false;

        if (vm.page * vm.count > vm.total) {
            vm.page = 0;
        }

        var params = {
            count: vm.count,
            from: vm.page * vm.count,
            field: vm.field,
            direction: vm.reverse ? "asc" : "desc"
        };

        TaskFactory.query(params, function (response) {
            vm.tasks = response.list;
            vm.pageLength = Math.ceil(response.total / vm.count) - 1;
            vm.total = response.total;
        });
    };

});
