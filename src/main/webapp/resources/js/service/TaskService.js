/**
 * Created by vano on 04.04.16.
 */

app.service('TaskService', function ($http, $rootScope, $location, TaskFactory) {
    var service = {};
    var modalInstance = null;

    service.modalEditTaskOpen = function (id) {

        modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'resources/tpl/modals/editTask.html',
            controller: 'TaskControllerModal',
            size: 'lg',
            scope: $scope,
            resolve: {
                taskId: id
            }
        });
        return modalInstance;
    };

    service.saveTask = function (addTask) {
        return TaskFactory.save(addTask).$promise;
    };


    service.deleteTask = function (id) {
        TaskFactory.delete({taskId: id}, function () {

        });
    };


    return service;
});
