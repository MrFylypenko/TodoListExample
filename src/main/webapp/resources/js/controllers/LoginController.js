/**
 * Created by vano on 15.02.16.
 */

app.controller('LoginController', function ($scope, $rootScope, $location, AuthenticationService) {
   /* $rootScope.param = $location.path();*/

    $scope.login = function () {
        AuthenticationService.Login($scope.username, $scope.password, function (response) {
            if (response.status == 200) {

                AuthenticationService.SetCredentials(response.data, '');
                $location.path('/task');
            } else {
                $scope.password = '';
                $scope.error = response.message;
                $scope.dataLoading = false;
            }
        });
    };
});