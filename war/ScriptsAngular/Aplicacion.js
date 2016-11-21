var app = angular.module("app", ['ngRoute']);
app.config(['$routeProvider',function($routeProvider) {
	
	$routeProvider.when('/vistaInsertar', {
	    templateUrl: "Vistas/FormularioRecepcion.html",
	    controller: "controladorItem"
	  });
	
}]);

