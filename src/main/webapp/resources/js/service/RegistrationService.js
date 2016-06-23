/**
 * Created by vano on 18.02.16.
 */
app.service('RegistrationService', function ($http, $rootScope) {

    var service = {};

    service.Registrate = function (userData, callback){
        $http.post('api/user/registration', userData).then(function (response){
            callback = response;
        }, function (response){
            callback = response;
        })
    };

    service.Login = function (username, password, callback) {

        var postData = 'j_username=' + username + '&j_password=' + password;

        $http({
            url: 'j_spring_security_check',
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

    service.SetCredentials = function (username, password) {
        if (username != '') {
            $rootScope.globals.currentUser = {
                username: username
            }
        }
    };

    service.ClearCredentials = function () {
        $http.get('logout').then(function (data) {
            console.log('я выщел');
        });
        $rootScope.globals.currentUser = '';
    };

    return service;
});