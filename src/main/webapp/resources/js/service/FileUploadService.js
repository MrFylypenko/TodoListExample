/**
 * Created by vano on 24.03.16.
 */
app.service('FileUploadService', function ($http, $q) {

    var deferred = $q.defer();

    this.uploadFileToUrl = function (file, uploadUrl, callback) {
        var fd = new FormData();
        fd.append('file', file);

        $http.post(uploadUrl, fd, {
            withCredentials: false,
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).success(function () {
            deferred.resolve();
            callback.fileUploadStatus = 'ready upload data';
        }).error(function () {
            callback.fileUploadStatus = 'error upload data';
        });

        return deferred.promise;
    }
});