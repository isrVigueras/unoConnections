app.service('itemServicio', [
		'$http',
		'$q',
		function($http, $q) {

			this.insertar = function(nuevoItem) {
				var d = $q.defer();
				$http.post("/items/insertar/", nuevoItem).then(
						function(response) {
							console.log(response);
							d.resolve(response.data);
						}, function(response) {
						});
				return d.promise
			};

			this.actualizar = function(actualizarItem) {
				var d = $q.defer();
				$http.post("/items/actualizar/", actualizarItem).then(
						function(response) {
							console.log(response);
							d.resolve(response.data);
						}, function(response) {
						});
				return d.promise
			};

			this.consultar = function(id) {
				var d = $q.defer();
				$http.get("/items/consultar/" + id).then(function(response) {
					console.log(response);
					d.resolve(response.data);
				}, function(response) {
				});
				return d.promise
			};

			this.consultarTodos = function() {
				var d = $q.defer();
				$http.get("/items/consultarTodos").then(function(response) {
					d.resolve(response.data);
				}, function(response) {
				});
				return d.promise;
			};
			
			this.consultarPorEstatus = function(estatus){
				var d = $q.defer();
				$http.get("/items/consultarPorEstatus/" + estatus).then(function(response){
					d.resolve(response.data);
				}, function(response) {
				});
				return d.promise;
			};
			
		} ]);

app.controller('controladorInsertarItem', [ '$scope', 'itemServicio',
		function($scope, itemServicio) {
			$scope.EnviarFormulario = function() {
				$scope.item.estatus="Recepcion";
				itemServicio.insertar($scope.item).then(function(data) {
					$scope.item = data;
					alert("Item ingresado correctamente");
				});
			}
		} ]);

app.controller('controladorActualizarItem', [ '$scope', 'itemServicio',
		'$routeParams', '$location',function($scope, itemServicio, $routeParams, $location) {
	
			itemServicio.consultar($routeParams.id).then(function(data) {
				$scope.item = data;
			});
			
			$scope.ActualizarItem= function() { // cambiar el nombre del
				// método
				itemServicio.actualizar($scope.item).then(function(data) {
					$scope.item = data;
					alert("Item modificado correctamente");
					$location.path("/vistaListaItems/");
				});
			}
			$scope.Regresar=function(){
				$location.path("/vistaListaItems/");	
				};
			
		} ]);

app.controller('controladorListaItems', [ '$scope', 'itemServicio','$location',
		function($scope, itemServicio,$location) {
			itemServicio.consultarTodos().then(function(data) {
				$scope.itemsLista = data;
			});
			
			$scope.actualizarItem=function(id){
				$location.path("/vistaActualizar/"+id);
			}
		} ]);

app.controller('controladorListaEstatus', ['$scope','itemServicio', 
		function($scope, itemServicio){
	$scope.estatus="Recepcion";
			itemServicio.consultarPorEstatus($scope.estatus).then(function(data){
				$scope.itemsLista=data;
			})
			
			$scope.$watch('estatus', function(estatus){
				console.log(estatus);
				itemServicio.consultarPorEstatus(estatus).then(function(data){
					$scope.itemsLista=data;
					console.log(data);
				})	
			});
}
	]);