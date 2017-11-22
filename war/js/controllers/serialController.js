app.service('serialService',function($q, $http, $location,$window,$rootScope){
	this.registraSerial = function(newSerial) {
		var d = $q.defer();
//		console.log($rootScope)
		newSerial;
		$http.post("/serial/add/", newSerial).then(
			function(response) {
//				console.log(response); 
				d.resolve(response.data);
			});
		return d.promise;
	}
	this.findSeries = function(rfc) {
		var d = $q.defer();
		$http.get("/serial/getAll/"+rfc).then(function(response) {
		//$http.get("/serial/getAll/"+"AAA010101AAA").then(function(response) {
			console.log(response);
			d.resolve(response.data);
		}, function(response) {
			d.reject(response);
		});
		return d.promise;
	};
	this.findCliente = function(id,rfc) {
		var d = $q.defer();
		$http.get("/clientes/find/"+id).then(function(response) {
			console.log(response);
			d.resolve(response.data);
		}, function(response) {
			d.reject(response);
		});
		return d.promise;
	};
	this.editaCliente = function(send) {
		var d = $q.defer();
		$http.post("/receptor/edit/"+send.id,send).then(function(response) {
			console.log(response);
			d.resolve(response.data);
		}, function(response) {
			d.reject(response);
		});
		return d.promise;
	};
});

app.controller('altaSerieController',['$scope','$rootScope','$routeParams','$cookieStore','serialService','$window',function($scope,$rootScope,$routeParams,$cookieStore,serialService,$window){
	$scope.guardar=function(){
		$scope.serial.rfc= $cookieStore.get('rfcEmpresa');
		serialService.registraSerial($scope.serial).then(function(){
			alert("Serial Agregado");
			$window.location.reload();
			$location.path("/serialList/"+$routeParams.rfc);
		})
	}
}]);

app.controller('serialListController',['$scope','$rootScope','$routeParams','$cookieStore','serialService','$location',function($scope,$rootScope,$routeParams,$cookieStore,serialService,$location){
	var rfcEmpresa=$cookieStore.get('rfcEmpresa');
	serialService.findSeries(rfcEmpresa).then(function(data){
		$scope.seriales=data;
	});
	$scope.editar = function(id) {
		$cookieStore.put('indice', id);
		$location.path("/serialEdit/"+$routeParams.rfc+"/"+id);
		console.log($cookieStore.get('indice'));
	}
}]);

app.controller("clientesEditController",['$scope','clientesService','$routeParams','$location','$window','$cookieStore',function($scope, clientesService, $routeParams,$location, $window, $cookieStore){
}]);