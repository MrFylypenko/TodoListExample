/**
 * Created by vano on 01.04.16.
 */
app.controller('ProjectControllerModal', function ($scope, $location, $rootScope, ProjectFactory, $log, $uibModalInstance, projectId) {
    $rootScope.param = $location.path();
    var vm = $scope.vm = this;


    if (projectId > 0){
        ProjectFactory.get({projectId:projectId}, function (resp) {
            vm.editProject = resp;
        })
    }else{}


    vm.update = function (){
        $uibModalInstance.close(vm.editProject);
    };

    vm.add = function () {
        $uibModalInstance.close(vm.addProject);
    };

    vm.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };


});
