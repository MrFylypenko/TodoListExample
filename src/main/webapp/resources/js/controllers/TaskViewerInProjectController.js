/**
 * Created by vano on 25.03.16.
 */
app.controller('TaskViewerInProjectController', function ($scope, $location, $rootScope, ProjectFactory, TaskFactory, $uibModal, $log) {

    var vm = $scope.vm = this;

    //$scope.vm.projectId = $scope.$parent.vm.project.id;

    vm.project = $scope.$parent.vm.project;

    /* console.log($scope.vm.project);*/

    /*vm.getTasksByProjectId = function (id) {
     return ProjectFactory.getTasksByProjectId({projectId: id, from : 0}, function (response) {

     console.log('test');

     vm.project.tasks = response.list;

     return response.list;
     });
     }*/


    //vm.project.tasks = vm.getTasksByProjectId(vm.project.id);


    //console.log(vm.project.tasks);


    vm.addTask = function () {
        console.log("TaskViewerInProjectController#addTask");
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

            $scope.$emit('updateProjectById', $scope.$parent.vm.project.id);

            /*ProjectFactory.get({projectId: $scope.$parent.vm.project.id}, function (resp){
                for (var i = 0; i < $scope.$parent.vm.projects.length; i++){
                    if ($scope.$parent.vm.projects[i].id == $scope.$parent.vm.project.id){
                        resp.show = true;
                        $scope.$parent.vm.projects[i] = resp;
                    }
                }
            })*/

        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    vm.modalEditTaskOpen = function (id) {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'resources/tpl/modals/editTask.html',
            controller: 'TaskControllerModal',
            size: 'lg',
            scope: $scope,
            resolve: {
                taskId: id
            }
        });
        modalInstance.result.then(function (selectedItem) {

            vm.getAll();

            $scope.$emit('updateProjectById', $scope.$parent.vm.project.id);


        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };


    vm.deleteTask = function (id) {
        TaskFactory.delete({taskId: id}, function () {
            vm.getAll();
            $scope.$emit('updateProjectById', $scope.$parent.vm.project.id);
        });
    };


    //navigation
    vm.page = 0;
    vm.total = 1;
    vm.count = 10;

    //vm.pageLength


    vm.getAll = function () {

        if (vm.page * vm.count > vm.total) {
            vm.page = 0;
        }

        ProjectFactory.getTasksByProjectId({projectId: vm.project.id, from: vm.page * vm.count}, function (response) {
            vm.project.tasks = response.list;
            vm.total = response.total;
            vm.pageLength = Math.ceil(response.total / vm.count) - 1;

        });
    }


    vm.prev = function () {
        if (vm.page == 0) return false;
        vm.page--;
        vm.getAll();
    }


    vm.next = function () {
        if (vm.pageLength >= vm.page + 1) {
            vm.page++;
            vm.getAll();
        }
    }

    vm.getAll();

});
