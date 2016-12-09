angular.module('myApp.view5', ['ngRoute','myApp.security'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view5', {
                    templateUrl: 'app/view5/view5.html',
                    controller: 'View5Ctrl',
                    controllerAs: 'ctrl'
                });
            }])

        .service('getPrice', [function () {
                var numOfSeats = {};

                return  numOfSeats;



            }])
        .service('getFlightID', [function () {
                var flightID = {};

                return  flightID;



            }])
        .service('getAirline', [function () {
                var airlineName = {};

                return  airlineName;



            }])
        .service('getDestination', [function () {
                var destination = {};

                return  destination;



            }])
        .service('getOrigin', [function () {
                var origin = {};

                return  origin;



            }])
        

        .controller('View5Ctrl', ["getuserName","$scope","$http", function (getuserName,$scope, $http) {



                $scope.getReservations = function () {
                    return $http({method: 'GET', url: 'api/booking/getBookings' ,
                        contentType: "application/json"}).success(function (data) {



                        $scope.output = data;
                        
                    }).
                            error(function (data, status, headers, config) {

                                
                                $scope.output = [];
                            });

                    ;
                };





            }]
                );

