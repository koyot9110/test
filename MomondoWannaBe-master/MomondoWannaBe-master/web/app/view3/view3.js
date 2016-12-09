'use strict';

angular.module('myApp.view3', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view3', {
                    templateUrl: 'app/view3/view3.html',
                    controller: 'View3Ctrl',
                    controllerAs: 'ctrl'
                });
            }])

        .controller('View3Ctrl', ["$scope", "$http", "InfoFactory", "InfoService", function ($scope, $http, InfoFactory, InfoService) {
                this.msgFromFactory = InfoFactory.getInfo();
                this.msgFromService = InfoService.getInfo();



                $scope.post = function () {


                    $http({method: 'POST', url: 'api/register',
                        contentType: "application/json", data: JSON.stringify($scope.user)}).
                            success(function (data, status, headers, config) {

                                 alert($scope.user.userName + " has sucessefully registered!");
                                $scope.user = null;
                              
                                


                            }).
                            error(function (data, status, headers, config) {
//                                if (data.msg === undefined) {
//                                    alert("Something weng wrong try again.");
//                                }
//                                alert(data.msg);
                                alert("Something weng wrong try again.");
                                $scope.user = {};
                            });

                };

            }]);
