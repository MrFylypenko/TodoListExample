/**
 * Created by vano on 25.03.16.
 */
app.factory('ProjectFactory', function ($http, $rootScope, $resource) {

    /**
     @see app.config in app.js

     'save':   {method:'PUT'},   //PUT
     'update': {method:'POST'},   //POST
     'query':  {method:'GET', isArray:true}, //isArray: false
     */

    return $resource('api/project/rest/:projectId',
        {
            projectId: '@projectId',
        },
        {
            query: {
                url:'api/project/query',
                isArray: false
            },
            getTasksByProjectId:{
                url:'api/project/getTasksByProjectId/:projectId/:from',
                isArray: false
            }
        });

});