var app = angular.module("app", ['ngRoute']);
 
 
app.config(['$routeProvider',function($routeProvider) {
   
 
  $routeProvider.when('/pagina1', {
    templateUrl: "pagina1.html",
    controller: "Pagina1Controller"
  });
   
  $routeProvider.when('/pagina2', {
    templateUrl: "pagina2.html",
    controller: "Pagina2Controller"
  });
   
  $routeProvider.when('/pagina3', {
    templateUrl: "pagina3.html",
    controller: "Pagina3Controller"
  }); 
  
  $routeProvider.when('/pagina4', {
	    templateUrl: "pagina4.html",
	    controller: "Pagina4Controller"
  }); 
   
  $routeProvider.otherwise({
        redirectTo: '/pagina1'
  });   
 
}]);
 
 
app.controller("Pagina1Controller", ["$scope",function($scope) {
   $scope.mensaje="Texto cargado desde el controlador Pagina1Controller";
}]);
 
app.controller("Pagina2Controller", ["$scope",function($scope) {
   $scope.mensaje="Texto cargado desde el controlador Pagina2Controller";
}]);
 
 
app.controller("Pagina3Controller", ["$scope",function($scope) {
   $scope.mensaje="Texto cargado desde el controlador Pagina3Controller";
}]);

app.controller("Pagina4Controller", ["$scope",function($scope) {
	   $scope.mensaje="Texto cargado desde el controlador Pagina4Controller";
}]);