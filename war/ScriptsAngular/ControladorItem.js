app.service('itemServicio',['$http','$q', function($http,$q) {
	
	this.insertar = function(nuevoItem) {
		var d= $q.defer();
		$http.post("/items/insertar/",nuevoItem).then(function(response){
			console.log(response);
				d.resolve(response.data);
		},function(response){});
		return d.promise
	};	
}]);

app.controller('controladorItem',['$scope','itemServicio',function($scope, itemServicio){
	$scope.EnviarFormulario = function(){
		itemServicio.insertar($scope.item).then(function(data){
			alert(data);
		});
	}
}]);