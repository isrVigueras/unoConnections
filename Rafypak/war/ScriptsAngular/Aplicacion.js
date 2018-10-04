var app = angular.module("app", [ 'ngRoute', 'ngTable' ]);
app.config([ '$routeProvider', function($routeProvider) {

	$routeProvider.when('/vistaInsertar', {
		templateUrl : "Vistas/FormularioRecepcion.html",
		controller : "controladorInsertarItem"
	});

	$routeProvider.when('/vistaActualizar/:id', {
		templateUrl : "Vistas/FormularioActualizar.html",
		controller : "controladorActualizarItem"
	});

	$routeProvider.when('/vistaListaItems', {
		templateUrl : "Vistas/ListaItems.html",
		controller : "controladorListaItems"
	});
	
	$routeProvider.when('/vistaListaEstatus', {
		templateUrl : "Vistas/ListaItemStatus.html",
		controller : "controladorListaEstatus"
	});
	
} ]);
