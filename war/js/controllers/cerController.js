app.service('cerService',function($q, $http, $location,$window,$rootScope){
	this.enviarCosas = function(cad) {
		var d = $q.defer();
		$http.post("/factura33/registrarEmisor/", cad).then(function(response) {
			d.resolve(response.data);
		}, function(response) {
			if(response.status==403){
				alert("No está autorizado para realizar esta acción");
				$location.path("/");
			}
		});
		return d.promise;
	}

});

app.controller('cerController',['$scope','$rootScope','$routeParams','$cookieStore','archivoService','fileService','cerService','$location',function($scope,$rootScope,$routeParams,$cookieStore,archivoService,fileService,cerService,$location){
	$scope.enviar=function(){
		$scope.files1= fileService.getFile("b_pics1");
		$scope.files2= fileService.getFile("b_pics2");
		$scope.files1.push($scope.files2[0]);
	
		archivoService.enviarFiles($scope.files1).then(function(data){
			var archivos=data;
			var send=archivos[1]+","+archivos[2]+","+$scope.pass+","+$cookieStore.get('rfcEmpresa');
			cerService.enviarCosas(send).then(function(data){
				alert(data);
				$location.path("/empresas/details/"+$cookieStore.get('rfcEmpresa'));
			})
			
		});
	}
}]);
