/**
 * Created by eastflag on 2016-06-30.
 */
angular.module('clientApp')
  .controller('ObserverCtrl', function ($scope) {
    var self = this;

    self.q = {};
    self.editorOptions = {
      lineWrapping : true,
      lineNumbers: true,
      mode: 'xml'
    };

  });
