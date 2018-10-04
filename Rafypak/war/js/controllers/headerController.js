app.directive('submenu',function(){
	return{
		templateUrl:'pages/menuTemplate.html'
	};
});

app.controller('headerController',['$scope','$rootScope','$location','$http','empresasService','$cookieStore',function($scope,$rootScope,$location,$http,empresasService,$cookieStore){
	$rootScope.cargarEmpresasHeader=function(){
	
	$http.get("/empresas/findAll").then(function(response){
		$scope.empresas=response.data;
	},function(response){
		if(response.status==403){
			alert("You're not allowed to see this page");
			$location.path("/login");
		}
	});
	}
	$rootScope.empresaActual=  $cookieStore.get('rfcEmpresa');
	$scope.empresaElegida="Elija una empresa";
	$scope.cargarEmpresasHeader();
	$scope.$watch('empresaElegida',function(){
		if($scope.empresaElegida!="Elija una empresa"){
			var empresa= $scope.empresaElegida;
			var url= $location.path();
			if(checkUrl(url)){
				$rootScope.empresaAcutal= $scope.empresaElegida;
				$scope.empresaElegida="Elija una empresa";
				url=url.substring(0,url.lastIndexOf("/")+1);
				$location.path(url+empresa);
			}else{
				$scope.empresaElegida="Elija una empresa";
				$location.path('/empresas/details/'+$rootScope.empresaAcutal);
			}
		}
	});
	
	function checkUrl(url){
		if(url.indexOf('/esquemas/agregar')!=-1){
			return true;
		}
		
		if(url.indexOf('/empresas/edit')!=-1){
			return true;
		}
		
		if(url.indexOf('/empresas/details')!=-1){
			return true;
		}
		
		if(url.indexOf('/empresas/empleados')!=-1){
			return true;
		}
		if(url.indexOf('/empleados/list')!=-1){
			return true;
		}

		return false;
	}
	
	$http.get("/usuario/check").then(function(response){
		$rootScope.variable = true;
		$scope.cargarEmpresasHeader();
	},function(response){
		if(response.status==403){
			$rootScope.variable = false;
			$scope.empresas={};
		}
		$location.path("/login");
		console.log(response);
	});
	
	$rootScope.checaUsuario=function(){
	$http.get("/usuario/check").then(function(response){
		$rootScope.variable = true;
	},function(response){
		if(response.status==403){
			$rootScope.variable = false;
		}
		$location.path("/login");
	});
	}
	$scope.CerrarSesion = function(){
		$http.get("/usuario/cerrarSesion").then(function(response) {
			$rootScope.variable = false;
			$location.path("/login");
		}, function(response) {
			$location.path("/login");
		});
	};
	
	
	$rootScope.nombreEmpresa= $cookieStore.get('nombreEmpresa');
	
}]);