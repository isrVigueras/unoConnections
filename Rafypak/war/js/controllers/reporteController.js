app.controller("reporteController", ['$http','$scope','$cookieStore',function($http,$scope,$cookieStore){
	$('.datepicker').datepicker({format: 'mm-dd-yyyy'});
	
	$('.input-daterange').datepicker({
	    format: "mm-dd-yyyy"
	});
	
	$('.input-daterange input').each(function() {
	    $(this).datepicker("format","mm-dd-yyyy");
	});

	$scope.rfcEmpresa=$cookieStore.get("rfcEmpresa");
}]);