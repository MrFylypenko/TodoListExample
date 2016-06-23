/**
 * Created by vano on 31.03.16.
 */

app.controller('TaskControllerModal', function ($location, $rootScope, $scope, $http, TaskFactory, FileUploadService, taskId, $uibModalInstance, CommentFactory) {

    var vm = $scope.vm = this;

    vm.comment = {};

    if (taskId > 0) {
        TaskFactory.get({taskId: taskId}, function (task) {
            vm.editTask = task;
        });
    } else {
        vm.addTask = {
            priority: "LOW",
            status: "TODO",
            project: $scope.$parent.vm.project  // get parent object
        };
    }


    vm.getUsers = function (username) {
        $http.get("api/user/query/" + username).then(function (response) {
            vm.users = response.data;
        });
    };

    vm.addComment = function () {

        if (vm.comment.value != undefined && vm.comment.value.length > 0) {
            vm.comment.task = {id: vm.editTask.id};
            CommentFactory.save(vm.comment, function (resp) {
                TaskFactory.get({taskId: vm.editTask.id}, function (task) {
                    vm.editTask = task;
                });
            });

            vm.comment = {};

        }
    };

    vm.uploadFile = function () {
        var file = vm.myFile;
        var uploadUrl = 'file/add/' + vm.editTask.id;
        var defer = FileUploadService.uploadFileToUrl(file, uploadUrl, vm);

        defer.then(function () {
            TaskFactory.get({taskId: vm.editTask.id}, function (task) {
                vm.editTask = task;
            });
        });
    };

    vm.cancel = function () {
        $uibModalInstance.dismiss();
    };


    vm.saveTask = function () {
        TaskFactory.save(vm.addTask, function () {
            vm.addTask = {};
            $uibModalInstance.close();
        });
    };


    vm.updateTask = function () {
        TaskFactory.update(vm.editTask, function () {
            vm.editTask = {};
            $uibModalInstance.close();
        });
    };
});
