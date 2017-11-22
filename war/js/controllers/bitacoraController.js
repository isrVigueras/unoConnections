app.service('bitacoraService',['$http','$q',function($http,$q){
	this.cargarTipo=function(tipo){
		var d = $q.defer();
		$http.get("/bitacora/get/"+tipo).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				},
				function(response) {
				});
		return d.promise;
	}
	
	this.intervalo=function(tipo,params){
		var d = $q.defer();
		$http.post("/bitacora/getFechas/"+tipo,params).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				},
				function(response) {
				});
		return d.promise;
	}
	this.usuario=function(tipo,usuario){
		var d = $q.defer();
		$http.get("/bitacora/getUsuario/"+tipo+"/"+usuario).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				},
				function(response) {
				});
		return d.promise;
	}
}]); 

app.controller('bitacoraController',['$scope','bitacoraService','$location',function($scope,bitacoraService,$location){
	$scope.params={}
	$scope.params.fechai=new Date();
	$scope.params.fechaf= new Date();
	$scope.cargar=function(){
		if($scope.filtro=='Reciente'){
			bitacoraService.cargarTipo($scope.tipo).then(function(data){
				console.log(data);
				$scope.bitacora=data;
			});
		}
		if($scope.filtro=="Intervalo de Fechas"){
//			$scope.params.fechai=$scope.params.fechai+"";
//			$scope.params.fechaf=$scope.params.fechaf+"";
			bitacoraService.intervalo($scope.tipo,$scope.params).then(function(data){
				$scope.bitacora=data;
			});
		}
		if($scope.filtro=="Usuario"){
			bitacoraService.usuario($scope.tipo,$scope.params.usuario).then(function(data){
				$scope.bitacora=data;
			});
		}
	}
}]);