/**
 * Created by vano on 15.02.16.
 */

app.service('AuthenticationService', function ($http, $rootScope, $location) {

    var service = {};

    service.Login = function (username, password, callback) {

        var postData = 'username=' + username + '&password=' + password;

        $http({
            url: 'login',
            method: "POST",
            data: postData,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function successCallback(response) {
            callback(response);

        }, function errorCallback(response) {
            response.message = 'Username or password is incorrect';
            callback(response);
            console.log('AuthenticationService.Login error');
        });
    };

    service.SetCredentials = function (user, password) {
        if (user != '') {
            $rootScope.globals.currentUser = user;
        }
    };

    service.ClearCredentials = function () {
        $http.get('logout').then(function (data) {
            $rootScope.globals.currentUser = '';
            $location.path('login');
        });
    };

    return service;
});