/**
 * Created by vano on 24.03.16.
 */
app.factory('CommentFactory', function ($http, $rootScope, $resource) {

    /**
     @see app.config in app.js

     'save':   {method:'PUT'},   //PUT
     'update': {method:'POST'},   //POST
     'query':  {method:'GET', isArray:true}, //isArray: false

     save need taskId
     delete need commentId

     */

    return $resource('api/comment/rest/:commentId',
        {
            taskId: '@taskId',
            commentId: '@commentId'
        },
        {

        });

});