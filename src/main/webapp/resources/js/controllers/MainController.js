/**
 * Created by vano on 15.02.16.
 */

app.controller('MainController', function ($scope, $location, $rootScope, AuthenticationService) {

    $scope.logout = function () {
        AuthenticationService.ClearCredentials();
    };

   /* $rootScope.param = $location.path();*/

});
