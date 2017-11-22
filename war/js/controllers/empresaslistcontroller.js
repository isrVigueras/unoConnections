app.controller('empresasListController',['$scope','$location','$http','empresasService',function($scope,$location,$http,empresasService){
	$http.get("/empresas/findAll").then(function(response){
		$scope.empresas=response.data;
		console.log($scope.empresas);
	},function(response){
		if(response.status==403){
			alert("You're not allowed to see this page");
			$location.path("/login");
		}
		$location.path("/login");
	});
	
	$scope.add=function(){
		$location.path("/empresas");
	}
	
	$scope.ver=function(rfc){
		console.log(rfc);
		$location.path("/empresas/details/"+rfc.RFC);
	};
	$scope.editar=function(rfc){
		$location.path("/empresas/edit/"+rfc.RFC);
	};
	$scope.eliminar=function(rfc){
		alert(rfc);
	};
}]);