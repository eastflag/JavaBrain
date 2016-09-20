/**
 * Created by eastflag on 2016-06-30.
 */
angular.module('clientApp')
  .controller('ObserverCtrl', function (HttpSvc) {
    var self = this;

    self.editorOptions = {
      lineWrapping : true,
      lineNumbers: true,
      mode: 'xml'
    };
    //문제배열
    self.feedback = [
      {num: 1, point:  5, content: ''},
      {num: 2, point:  2, content: ''},
      {num: 3, point:  3, content: ''},
      {num: 4, point:  5, content: ''},
      {num: 5, point: 10, content: ''},
      {num: 6, point: 15, content: ''},
      {num: 7, point: 20, content: ''},
      {num: 8, point:  5, content: ''},
      {num: 9, point:  5, content: ''},
      {num:10, point: 15, content: ''},
      {num:11, point: 10, content: ''}
    ];

    self.submit = submit;

    function submit(form) {
      console.log(form);
      var params = {
        boards_id: 202,
        member_id: 1,
        feedback: self.feedback
      };
      HttpSvc.addAnswer(params)
        .success(function (values) {
          console.log(values);
        });
    }

  });
