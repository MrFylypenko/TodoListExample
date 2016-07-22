/**
 * Created by vano on 25.03.16.
 */
app.controller('ProjectController', function ($scope, $location, $rootScope, ProjectFactory, $uibModal, projects) {
    $rootScope.param = $location.path();
    var vm = $scope.vm = this;

    vm.toggleTasks = function (project) {
        project.show = !project.show;
        return project.show;
    };

    $scope.$on('updateProjectById', function (event, projectId) {
        ProjectFactory.get({projectId: projectId}, function (resp) {
            for (var i = 0; i < vm.projects.length; i++) {
                if (vm.projects[i].id == resp.id) {
                    resp.show = true;
                    vm.projects[i] = resp;
                }
            }
        })
    });


    vm.modalAddOpen = function () {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'resources/tpl/modals/addProject.html',
            controller: 'ProjectControllerModal',
            size: 'lg',
            scope: $scope,
            resolve: {
                projectId: 0
            }
        });
        modalInstance.result.then(function (addProject) {
            ProjectFactory.save(addProject, function () {
                vm.getAll();
            });
        }, function () {
            console.log('Modal dismissed at: ' + new Date());
        });
    };


    vm.modalEditProject = function (projectId) {
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: 'resources/tpl/modals/editProject.html',
            controller: 'ProjectControllerModal',
            size: 'lg',
            scope: $scope,
            resolve: {
                projectId: projectId
            }
        });
        modalInstance.result.then(function (addProject) {

            ProjectFactory.update(addProject, function () {
                vm.getAll();
            });
        }, function () {
            console.log('Modal dismissed at: ' + new Date());
        });
    };

    vm.deleteProject = function (projectId) {
        ProjectFactory.delete({projectId: projectId}, function () {
            vm.getAll();
        });
    }


    //project list navigation
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

    vm.page = 0;
    vm.count = 10;
    vm.from = 0;
    vm.reverse = true;
    vm.field = 'name';
    vm.total = 0;

    vm.projects = projects.list;
    vm.total = projects.total;
    vm.pageLength = Math.ceil(projects.total / 10) - 1;


    //TODO search
    vm.getAll = function () {
        if (vm.page * vm.count > vm.total) {
            vm.page = 0;
        };

        var params = {
            count: vm.count,
            from: vm.page * vm.count,
            field: vm.field,
            direction: vm.reverse ? "asc" : "desc"
        };

        ProjectFactory.query(params, function (response) {
            vm.projects = response.list;
            vm.pageLength = Math.ceil(response.total / vm.count) - 1;
            vm.total = response.total;
        });
    };

});
