/**
 * Created by vano on 29.01.16.
 */

var app = angular.module('app', ['ngResource', 'ngRoute', 'ui.bootstrap', 'ui.select']);

app.config(function ($httpProvider, $routeProvider, $locationProvider, $resourceProvider) {

    $resourceProvider.defaults.actions.save = {method: 'PUT'};
    $resourceProvider.defaults.actions.update = {method: 'POST'};


    //for CORS
    /* $httpProvider.defaults.withCredentials = true;*/

    $locationProvider.html5Mode({enabled: true, requireBase: true});

    $routeProvider
        .otherwise({redirectTo: '/task'})
        .when('/login', {
            controller: 'LoginController',
            templateUrl: 'resources/tpl/login.html'
        })
        .when('/about', {
            controller: 'MainController',
            templateUrl: 'resources/tpl/about.html'
        })
        .when('/hello', {
            controller: 'MainController',
            templateUrl: 'hello'
        })
        .when('/projects', {
            controller: 'ProjectController',
            templateUrl: 'resources/tpl/projects.html'
        })
        .when('/task', {
            controller: 'TaskController',
            templateUrl: 'resources/tpl/task.html',
            resolve: {
                tasks: function ($q, TaskFactory) {
                    var params = {
                        count: 10,
                        from: 0,
                        field: 'name',
                        direction: "asc"
                    };

                    var defer = $q.defer();
                    TaskFactory.query(params, function (tasks) {
                        defer.resolve(tasks);
                    });
                    return defer.promise;
                }
            }
        })
        .when('/mytasks', {
            controller: 'MyTasksController',
            templateUrl: 'resources/tpl/mytasks.html'
        })
        .when('/register', {
            controller: 'RegistrationController',
            templateUrl: 'resources/tpl/register.html'
        })
        .when('/file', {
            controller: 'FileController',
            templateUrl: 'resources/tpl/file.html'
        });

}).run(['$rootScope', '$location', '$http', '$q', function ($rootScope, $location, $http, $q) {

    var defer = $q.defer();

    $rootScope.globals = {currentUser: ''};

    $http.get('login/getuser').then(function (data) {
        $rootScope.globals.currentUser = data.data;
        defer.resolve();
    }).catch(function () {
        defer.reject();
    });

    $rootScope.$on('$locationChangeStart', function (event, next, current) {
        defer.promise.then(function () {
            $rootScope.param = $location.path();
            var restrictedPage = $.inArray($location.path(), ['/login', '/register', '/about']) === -1;
            if (restrictedPage && $rootScope.globals.currentUser == '') $location.path('/login');
        }).catch(function () {
            $rootScope.param = $location.path();
            var restrictedPage = $.inArray($location.path(), ['/login', '/register', '/about']) === -1;
            if (restrictedPage && $rootScope.globals.currentUser == '') $location.path('/login');
        });
    });
}]).constant('server', {
    url: '/testHelloWorld'
});