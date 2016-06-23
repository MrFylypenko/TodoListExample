/**
 * Created by vano on 23.03.16.
 */
app.factory('TaskFactory', function ($http, $rootScope, $resource) {

    /**
     @see app.config in app.js

      'save':   {method:'PUT'},   //PUT
      'update': {method:'POST'},   //POST
      'query':  {method:'GET', isArray:true}, //isArray: false
     */

    return $resource('api/task/rest/:taskId/:count/:from/:direction/:field',
        {
            taskId: '@taskId',
            count: '@count',
            from: '@from',
            field: '@field',
            direction: '@direction'
        },
        {
            query: {
                isArray: false
            },
            getMyTasksTodo: {
                url: 'api/task/rest/getmytaskstodo/:from',
                isArray: false
            },
            getMyTasksDoing :{
                url: 'api/task/rest/getmytasksdoing/:from',
                isArray: false
            },
            getMyTasksDone:{
                url: 'api/task/rest/getmytasksdone/:from',
                isArray: false
            }
        });
});