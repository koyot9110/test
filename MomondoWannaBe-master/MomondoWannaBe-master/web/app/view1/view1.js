angular.module('myApp.view1', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view1', {
                    templateUrl: 'app/view1/view1.html',
                    controller: 'View1Ctrl',
                    controllerAs: 'ctrl'
                });
            }])

.service('getNumOfSeats', [function(){
        var numOfSeats = {};
        
       return  numOfSeats;
          
          
          
  }])
  .service('getFlightID', [function(){
        var flightID = {};
        
       return  flightID;
          
          
          
  }])
   .service('getAirline', [function(){
        var airlineName = {};
        
       return  airlineName;
          
          
          
  }])
  
        .controller('View1Ctrl', ["getNumOfSeats","getAirline","getFlightID","$scope", "$http", function (getNumOfSeats,getAirline,getFlightID,$scope, $http) {
                    
                $scope.airport = [{
                        id: 1,
                        label: 'Copenhagen',
                        name: 'CPH'
                    }
                    , {
                        id: 2,
                        label: 'Berlin',
                        name: 'SXF'
                    }
                    , {
                        id: 3,
                        label: 'Barcelona',
                        name: 'BCN'
                    }
                    , {
                        id: 4,
                        label: 'Paris',
                        name: 'CDG'
                    }
                    , {
                        id: 5,
                        label: 'London',
                        name: 'STN'
                    }

                ];
                


                $scope.getFlight = function (flightNumber,airline) {
                    if ($scope.chosenAirportDestination === undefined) {
                        return $http({method: 'GET', url: 'api/flightinfo/' + $scope.chosenAirportOrigin.name + '/' + $scope.date.toISOString() + '/' + $scope.tickets,
                            contentType: "application/json"}).success(function (data) {



                            $scope.output = data;
                            getNumOfSeats.numOfSeats = 0;
                           
                        getNumOfSeats.numOfSeats = $scope.tickets;
                            




                        }).
                                error(function (data, status, headers, config) {
//                                    if (data.msg === undefined) {
//                                        alert("No flights found.");
//                                    }
//                                    alert(data.msg);
                                    alert("No flights found.");
                                    $scope.output = [];
                                });
                    }
                    else {
                        return $http({method: 'GET', url: 'api/flightinfo/' + $scope.chosenAirportOrigin.name + "/" + $scope.chosenAirportDestination.name + '/' + $scope.date.toISOString() + '/' + $scope.tickets,
                            contentType: "application/json"}).success(function (data) {


                            
                            $scope.output = data;
                            getNumOfSeats.numOfSeats = 0;
                           getFlightID.flightID = flightNumber;
                        getNumOfSeats.numOfSeats = $scope.tickets;
                        getAirline.airlineName = airline;


                        }).
                                error(function (data, status, headers, config) {
//                                    if (data.msg === undefined) {
//                                        alert("No flights found.");
//                                    }
//                                    alert(data.msg);
                                    alert("No flights found.");
                                    $scope.output = [];
                                });
                    }
                    ;
                };





            }]
                );

