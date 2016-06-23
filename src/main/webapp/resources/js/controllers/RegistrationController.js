/**
 * Created by vano on 16.02.16.
 */
app.controller('RegistrationController', function ($scope, $location, $rootScope, RegistrationService) {

   /* $rootScope.param = $location.path();*/

    $scope.register = function () {
        var response = null;
        RegistrationService.Registrate($scope.vm.user, response);
        $location.path('/login');
    };

});
