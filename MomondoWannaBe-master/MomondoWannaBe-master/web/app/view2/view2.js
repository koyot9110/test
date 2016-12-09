'use strict';

angular.module('myApp.view2', ['ngRoute','myApp.view1','myApp.security'])

        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/view2', {
              templateUrl: 'app/view2/view2.html',
              controller: 'View2Ctrl'
            });
          }])

        .controller('View2Ctrl', ["$scope","getuserName","getAirline","getNumOfSeats","getFlightID", "$http",function ($scope,getuserName,getAirline, getNumOfSeats,getFlightID, $http, $location) { 
            
    
//    if(getNumOfSeats.numOfSeats==2){
//                        $scope.passengers.push("1");
//                        $scope.passengers.push("2");
//                    }else if(getNumOfSeats.numOfSeats==3){
//                        $scope.passengers.push("1");
//                        $scope.passengers.push("2");
//                        $scope.passengers.push("3");
//                    }
            $scope.passengers = [];
            
                    for(var i = 0; i < getNumOfSeats.numOfSeats-1; i++){
                        $scope.passengers.push(i);
                    }
                    
                    
          $scope.book = function(booker){
                    var reserve = {};
                    reserve.reserveeName = booker.fName + " " + booker.lName;
                    reserve.username = getuserName.username;
                    reserve.reservePhone = booker.phone;
                    reserve.reserveEmail = booker.email;
                    reserve.numberOfSeats = parseInt(getNumOfSeats.numOfSeats);
                    reserve.flightID = getFlightID.flightID;
                    reserve.airlineName = getAirline.airlineName;
                    reserve.passengers = [];
                    
                    for(var i =0;i<$scope.passengers.length;i++){
                          var fullName = booker.passengers[i].Name;
                          reserve.passengers.push(fullName);
                    }
//                    
                    
                    
                    
                    $http({method: 'POST', url: 'api/booking',
                        contentType: "application/json", data: JSON.stringify(reserve)}).
                            success(function (data, status, headers, config) {
                                 alert(reserve.reserveeName + ": you have booked a flight");
                                 $location.path("#/view4");
                                

                            }).
                            error(function (data, status, headers, config) {
                                    "Something weng wrong try again."
                            });
 
          
      }
      

        }]);