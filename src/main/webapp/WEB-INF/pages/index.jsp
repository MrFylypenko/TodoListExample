<%--
  Created by IntelliJ IDEA.
  User: vano
  Date: 18.12.15
  Time: 10:43
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html lang="ru" ng-app="app">
<head>

    <base href="${pageContext.request.contextPath}/">


    <meta charset="UTF-8">
    <title>Task Manager</title>
    <link rel="stylesheet" data-ng-href="resources/bower_components/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" data-ng-href="resources/bower_components/ui-select/dist/select.min.css">
    <link rel="stylesheet" data-ng-href="resources/bower_components/angular-bootstrap/ui-bootstrap-csp.css">


    <base href="/">
    <script src="resources/bower_components/jquery/dist/jquery.js"></script>
    <script src="resources/bower_components/angular/angular.js"></script>
    <script src="resources/bower_components/angular-route/angular-route.js"></script>
    <script src="resources/bower_components/angular-resource/angular-resource.js"></script>

    <script src="resources/bower_components/bootstrap/dist/js/bootstrap.js"></script>

    <script src="resources/bower_components/ui-select/dist/select.min.js"></script>
    <script src="resources/bower_components/angular-bootstrap/ui-bootstrap.js"></script>
    <script src="resources/bower_components/angular-bootstrap/ui-bootstrap-tpls.js"></script>

    <script src="resources/js/app.js"></script>

    <script src="resources/js/controllers/LoginController.js"></script>
    <script src="resources/js/controllers/MainController.js"></script>
    <script src="resources/js/controllers/RegistrationController.js"></script>
    <script src="resources/js/controllers/TaskController.js"></script>
    <script src="resources/js/controllers/MyTasksController.js"></script>
    <script src="resources/js/controllers/ProjectController.js"></script>
    <script src="resources/js/controllers/modal/TaskControllerModal.js"></script>
    <script src="resources/js/controllers/modal/ProjectControllerModal.js"></script>
    <script src="resources/js/controllers/TaskViewerInProjectController.js"></script>


    <script src="resources/js/directives/fileModel.js"></script>


    <script src="resources/js/service/AuthenticationService.js"></script>
    <script src="resources/js/service/RegistrationService.js"></script>
    <script src="resources/js/service/FileUploadService.js"></script>
    <script src="resources/js/service/TaskService.js"></script>


    <script src="resources/js/factory/CommentFactory.js"></script>
    <script src="resources/js/factory/TaskFactory.js"></script>
    <script src="resources/js/factory/ProjectFactory.js"></script>


    <script src="resources/js/filters/byte.js"></script>


</head>
<body class="container" ng-controller="MainController">

<nav class="navbar navbar-default <%--navbar-fixed-top--%> ">

    <div class="container-fluid">
        <div class="navbar-header ">
            <a class="navbar-brand" href="#">Task Manager</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li role="presentation" ng-class="{'active': param == '/mytasks' }"><a href="mytasks">MyTasks</a></li>
                <li role="presentation" ng-class="{'active': param == '/task' }"><a href="task">Tasks</a></li>
                <li role="presentation" ng-class="{'active': param == '/projects' }"><a href="projects">Projects</a>
                </li>
                <li role="presentation" ng-class="{'active': param == '/hello' } "><a href="hello">Hello</a></li>
                <li role="presentation" ng-class="{'active': param == '/about' }"><a href="about">About</a></li>

            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li role="presentation" ng-hide="globals.currentUser.username"
                    ng-class="{'active': param == '/login' }"><a href="login">Login</a></li>
                <li role="presentation" ng-hide="globals.currentUser.username"
                    ng-class="{'active': param == '/register' }"><a href="register">Registration</a>
                </li>
                <p ng-show="globals.currentUser.username" class="navbar-text">You logged as</p>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false" style="position: relative; margin-left: 30px">
                        <img src="{{globals.currentUser.imageUrl}}"  ng-if="globals.currentUser.imageUrl"
                             style="height: 30px; width: 30px; position: absolute; left: -25px; top: 10px;">
                        {{globals.currentUser.firstName}} {{globals.currentUser.lastName}}<span
                            class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Profile (нет)</a></li>
                        <li><a href="#">Settings (нет)</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a ng-click="logout()" href="logout">Logout</a></li>
                    </ul>
                </li>
                <li ng-show="globals.currentUser.username" role="presentation"><a ng-click="logout()" href="logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>


</nav>


<div class="well" data-ng-view></div>


<div class="">

    <div class=" well" align="center">&copy; 2016 Todolist</div>
</div>
</body>
</html>