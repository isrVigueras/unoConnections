app.service('clientesService', [
	'$http',
	'$q',
	'$location',
	'$rootScope',
	'$window',
	function($http, $q, $location,$rootScope, $window) {

		this.registraCliente = function(newCliente) {
			var d = $q.defer();
//			console.log($rootScope)
			var send={
					rfc:$rootScope.empresaActual,
					receptores:[newCliente]
			}
			$http.post("/receptor/add/", send).then(
				function(response) {
//					console.log(response); 
					d.resolve(response.data);
				});
			return d.promise;
		}
		this.findClientes = function(rfc) {
			var d = $q.defer();
			$http.get("/emisor/consultar2/"+rfc).then(function(response) {
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
		this.eliminaCliente = function(send) {
			var d = $q.defer();
			$http.post("/receptor/delete/"+send.id,send).then(function(response) {
				console.log(response);
				d.resolve(response.data);
			}, function(response) {
				d.reject(response);
			});
			return d.promise;
		};
	
}])
app.controller("clientesController",[
	'$scope',
	'clientesService',
	'$routeParams',
	'$location',
	'$window',
	function($scope, clientesService, $routeParams,$location,$window){
		
		$scope.newCliente={
				domicilio:{
					pais:"MX"
				}
		}
		
		$scope.registraCliente = function(newCliente) {
			console.log(newCliente);		
			clientesService.registraCliente(newCliente).then(function(newCliente) {
						alert("Cliente Agregado");
						$location.path("/clientesList/"+$routeParams.rfc);
						$window.location.reload();
					})
		}

		
		$scope.editar = function(id) {
			$location.path("/clientes/edit/" + id);
		}
		$scope.pais = function(newCliente){
			//console.log(newCliente.pais);
			if(newCliente.pais!="MX"){
				$scope.Estado=true;
			}else{
				$scope.Estado=false;
			}
		}

		$scope.nuevoCliente = function(){
			$location.path("/altaClientes/"+$routeParams.rfc);
		}		
}]);

app.controller("clientesListController",[
	'$scope',
	'clientesService',
	'$routeParams',
	'$location',
	'$window',
	'$cookieStore',
	function($scope, clientesService, $routeParams,$location,$window, $cookieStore){
			
			$scope.clientes = function() {
				clientesService.findClientes($routeParams.rfc).then(
						function(data) {
							$scope.clientes = data;
						})
			}
			$scope.clientes();
			
			$scope.nuevoCliente = function(){
				$location.path("/altaClientes/"+$routeParams.rfc);
			}
			$scope.editar = function(id) {
				$cookieStore.put('indice', id);
				$location.path("/clientesEdit/"+$routeParams.rfc+"/"+id);
			}
			$scope.eliminar = function(id){
				var send={
						id: id,
						rfc: $cookieStore.get('rfcEmpresa')
				}
				console.log(send);
				clientesService.eliminaCliente(send).then(function(send) {	
					alert("Cliente Eliminado");
					$window.location.reload();
					$location.path("/clientesList/"+$routeParams.rfc);
				})
			}
	}]);

//app.controller("editaClienteController",['$scope',function($scope){
//	alert("ora si entra");
//}]);
app.controller("editaClienteController",[
	'$scope',
	'clientesService',
	'$routeParams',
	'$location',
	'$window',
	'$cookieStore',
	function($scope, clientesService, $routeParams,$location, $window, $cookieStore){
		clientesService.findClientes($routeParams.rfc).then(function(data){
			$scope.newCliente=data[$routeParams.ind];
		});
		$scope.editaCliente = function(newCliente) {
			var send={
						receptores: [newCliente],
						id: $cookieStore.get('indice'),
						rfc: $cookieStore.get('rfcEmpresa')
			}
		console.log(send);		
		clientesService.editaCliente(send).then(function(send) {
					alert("Cliente Modificado");
					$window.location.reload();
					$location.path("/clientesList/"+$routeParams.rfc);
				})
	}	
		
}]);