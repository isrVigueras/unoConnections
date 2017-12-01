app.service('conceptosService', [
	'$http',
	'$q',
	'$location',
	'$rootScope',
	'$window',
	function($http, $q, $location,$rootScope, $window) {
		this.registraConcepto = function(newConcepto) {
			var d = $q.defer();
//			console.log($rootScope)
			var send={
					rfc:$rootScope.empresaActual,
					conceptos:[newConcepto]
			}
			$http.post("/conceptos/add/", send).then(
				function(response) {
//					console.log(response); 
					d.resolve(response.data);
				});
			return d.promise;
		}
		this.cargaConceptos = function(rfc) {
			var d = $q.defer();
//			console.log($rootScope)
			$http.get("/conceptos/cargar/"+rfc).then(
				function(response) {
//					console.log(response); 
					d.resolve(response.data);
				});
			return d.promise;
		}
		this.editaConcepto = function(send) {
			var d = $q.defer();
			$http.post("/conceptos/edit/"+send.id,send).then(function(response) {
				console.log(response);
				d.resolve(response.data);
			}, function(response) {
				d.reject(response);
			});
			return d.promise;
		};
		this.eliminaConcepto = function(send) {
			var d = $q.defer();
			$http.post("/conceptos/delete/"+send.id,send).then(function(response) {
				console.log(response);
				d.resolve(response.data);
			}, function(response) {
				d.reject(response);
			});
			return d.promise;
		};
		this.findConceptos = function(rfc) {
			var d = $q.defer();
//			console.log($rootScope)
			$http.get("/conceptos/getProdServ/"+rfc).then(
				function(response) {
//					console.log(response); 
					d.resolve(response.data);
				});
			return d.promise;
		}
		this.findClave = function(pal) {
			var d = $q.defer();
//			console.log($rootScope)
			$http.get("/unidad_de_medida/getLista/"+pal).then(
				function(response) {
//					console.log(response); 
					d.resolve(response.data);
				});
			return d.promise;
		}
		
}])
app.controller("conceptosController",[
	'$scope',
	'conceptosService',
	'$routeParams',
	'$location',
	'$window',
	'$rootScope','archivoService',
	'$cookieStore',
	function($scope, conceptosService, $routeParams,$location,$window,$rootScope,archivoService,$cookieStore){
		conceptosService.findConceptos($routeParams.rfc).then(function(data){
			$scope.productos_servicios=data;
			console.log($scope.productos_servicios);
		})
		$scope.tipoCarga=1;
		$scope.newConcepto={
				unidad:"No Aplica"
		}
		$scope.guardarConcepto=function(){
			$scope.newConcepto.claveProdServ= $scope.newConcepto.productos_servicios[0];
			$scope.newConcepto.descripcionSAT= $scope.newConcepto.productos_servicios[1];
			$scope.newConcepto.claveUnidad= $scope.newConcepto.claveNombreUnidad[0];
			conceptosService.registraConcepto($scope.newConcepto).then(function(data){
				alert("Concepto Guardado");
				$location.path("/conceptosList/"+$routeParams.rfc);
				$window.location.reload();
			});
		}		
		
	
		$scope.ok=function(){
//			var c= $('#cadena').text();
			archivoService.sendfile2($scope.datos,"/conceptos/addMultiple/"+$cookieStore.get("rfcEmpresa")).then(function(data){
				console.log(data);
				alert("Conceptos guardados");
				$location.path("/conceptosList/"+$routeParams.rfc);
				$window.location.reload();
			});
		}
		$scope.buscarClaveUnidad= function(){
			conceptosService.findClave($scope.newConcepto.nombreUnidad).then(function(data){
				$scope.claves_unidades=data;
				console.log($scope.productos_servicios);
			})
		}
}]);



app.controller("conceptosListController",[
	'$scope',
	'conceptosService',
	'$routeParams',
	'$location',
	'$window',
	'$rootScope',
	'$cookieStore',
	function($scope, conceptosService, $routeParams,$location,$window,$rootScope,$cookieStore){
		$scope.cargar=function(){
			conceptosService.cargaConceptos($routeParams.rfc).then(function(data){
				$scope.conceptos=data.conceptos;
				console.log($scope.conceptos);
				for (var i = 0; i < $scope.conceptos.length; i++) {
					$scope.conceptos[i].busquedaAttr = $scope.conceptos[i].noIdentificacion
					+ " ";
					$scope.conceptos[i].indice=i;
//					+ $scope.empleados[i].empleado.nombre.apellidoPaterno
//					+ " "
//					+ $scope.empleados[i].empleado.nombre.apellidoMaterno
//					+ " "
//					+ $scope.empleados[i].empleado.numEmpleado
//					+ " "
//					+ $scope.empleados[i].empleado.RFC
				}
			})
		}
		$scope.cargar();
		$scope.editarConcepto = function(id) {
			$cookieStore.put('indice', id);
			$location.path("/conceptosEdit/"+$routeParams.rfc+"/"+id);
			console.log($cookieStore.get('indice'));
		}
		$scope.eliminarConcepto = function(id){
			var send={
					id: id,
					rfc: $cookieStore.get('rfcEmpresa')
			}
			console.log(send);
			conceptosService.eliminaConcepto(send).then(function(send) {	
				alert("Concepto Eliminado");
				$window.location.reload();
				$location.path("/conceptosList/"+$routeParams.rfc);
			})
		}
		$scope.nuevoConcepto = function(){
			$location.path("/conceptos/"+$routeParams.rfc);
		}	
	}]);

app.controller("conceptosEditController",[
	'$scope',
	'conceptosService',
	'$routeParams',
	'$location',
	'$window',
	'$cookieStore',
	function($scope, conceptosService, $routeParams,$location, $window, $cookieStore){
		conceptosService.findConceptos($routeParams.rfc).then(function(data){
			$scope.productos_servicios=data;
			console.log($scope.productos_servicios);
		})
		conceptosService.cargaConceptos($routeParams.rfc).then(function(data){
			$scope.newConcepto=data.conceptos[$routeParams.ind];
		})
		
		$scope.editaConcepto = function(newConcepto) {			
			$scope.newConcepto.claveUnidad=$scope.newConcepto.claveNombreUnidad[0];
			$scope.newConcepto.descripcionUnidadSAT=$scope.newConcepto.claveNombreUnidad[1];
			var send={
						conceptos: [newConcepto],
						id: $cookieStore.get('indice'),
						rfc: $cookieStore.get('rfcEmpresa')
			}
		console.log(send);		
		conceptosService.editaConcepto(send).then(function(send) {
					alert("Concepto Modificado");
					$window.location.reload();
					$location.path("/conceptosList/"+$routeParams.rfc);
				})
	}
		$scope.buscarClaveUnidad= function(){
			conceptosService.findClave($scope.newConcepto.nombreUnidad).then(function(data){
				$scope.claves_unidades=data;
				console.log($scope.productos_servicios);
			})
		}
		
}]);
		
	