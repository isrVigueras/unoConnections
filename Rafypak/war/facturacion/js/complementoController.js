app.controller("complementoController",['serialService', 'comprobanteService33', 'complementoService','$rootScope','$scope','$location','$routeParams','$cookieStore','$http','$routeParams',function(serialService, comprobanteService33, complementoService,$rootScope,$scope, $location,$routeParams,$cookieStore,$http,$routeParams){
	$scope.Correo=false;
	$scope.cfdiParaPago = comprobanteService33.getCFDIParaPago();
	$scope.complementoPagos = comprobanteService33.getComplementoPago();
	 $scope.complementoPagos.pago[0].doctoRelacionado.splice(0);
	 $scope.complementoPagos.pago[0].formaDePagoP={valor:"03"};
	 $scope.complementoPagos.pago[0].monedaP={valor:"MXN"};
//	$scope.complementoPagos.pago[0].doctoRelacionado[0].idDocumento=uuidRelacionado;
//	$scope.resta= $scope.complementoPagos.pago[0].doctoRelacionado[0].impSaldoAnt*1-$scope.complementoPagos.pago[0].monto*1;
	
	$scope.addDoc=function(){
		
		 var doc={
      	   idDocumento : "",
      	   serie : null,
      	   folio : null,
      	   monedaDR :{valor:"MXN"},
      	   tipoCambioDR : null,
      	   metodoDePagoDR :{valor:"PPD"},
      	   numParcialidad : null,
      	   impSaldoAnt : null,
      	   impPagado : null,
      	   impSaldoInsoluto : null
         };
		 
		 $scope.complementoPagos.pago[0].doctoRelacionado.push(doc);
		 
	}
	$scope.addDoc();
	comprobanteService33.getReceptores($cookieStore.get("rfcEmpresa")).then(function(data){
		if (data.emisor != undefined) {
			if (data.emisor.receptores != undefined) {
				$scope.receptores=data.emisor.receptores;
			}
			$scope.cfdiParaPago.emisor.rfc= data.emisor.rfc;
			$scope.cfdiParaPago.emisor.nombre = data.nombreEmisor;
			$scope.cfdiParaPago.emisor.domicilioFiscal=data.domicilioFiscal;
			$scope.emails = data.emails;
		}
		$scope.cfdiParaPago.lugarExpedicion.valor = data.lugarDeExpedicion;
	})
	
	serialService.findSeries($cookieStore.get("rfcEmpresa")).then(function(data){
		$scope.seriales=data;
	})
	
	$scope.delDoc= function(index){
		$scope.complementoPagos.pago[0].doctoRelacionado.splice(index);
	}

	$scope.enviarComprobante= function(){
		var validado= $scope.validar();
		if(validado){
			alert("No se ha elegido una serie");
			document.getElementById("serial").focus();
		}else{
			
		
			$scope.cfdiParaPago.conceptos.concepto=[];
			var send={cfdi:$scope.cfdiParaPago,
//					email:$scope.comprobante.email,
					complementoPagos:$scope.complementoPagos,
					serie:$scope.serialElegido
			}
			comprobanteService33.timbrarComplementoM(send).then(function(data){
				console.log(data);
				alert(data);
			})
		}
	}
	
	$scope.validar=function(){
		if(!$scope.serialElegido){
			alert("No se ha elegido una serie");
			document.getElementById("serial").focus();
			return false;
		}
		if(!$scope.complementoPagos.pago[0].monto){
			alert("No está especificado el monto");
			document.getElementById("monto").focus();
			return false;
		}
		for(var i = 0; i< $scope.complementoPagos.pago[0].doctoRelacionado.length; i++){
			if(!$scope.complementoPagos.pago[0].doctoRelacionado[i].idDocumento){
				alert("No está especificado el UUID del documento relacionado");
				document.getElementById($scope.complementoPagos.pago[0].doctoRelacionado[i].ideuuid).focus();
				return false;
			}
			if(!$scope.complementoPagos.pago[0].doctoRelacionado[i].impSaldoAnt){
				alert("No está especificado el saldo anterior del documento relacionado");
				document.getElementById($scope.complementoPagos.pago[0].doctoRelacionado[i].impSaldoAntid).focus();
				return false;
			}
			if(!$scope.complementoPagos.pago[0].doctoRelacionado[i].impPagado){
				alert("No está especificado el importe pagado del documento relacionado");
				document.getElementById($scope.complementoPagos.pago[0].doctoRelacionado[i].impPagadoid).focus();
				return false;
			}
			if(!$scope.complementoPagos.pago[0].doctoRelacionado[i].impSaldoInsoluto){
				alert("No está especificado el saldo insoluto del documento relacionado");
				document.getElementById($scope.complementoPagos.pago[0].doctoRelacionado[i].impSaldoInsolutoid).focus();
				return false;
			}
			if(!$scope.complementoPagos.pago[0].doctoRelacionado[i].numParcialidad){
				alert("No está especificado el número de parcialidad del documento relacionado");
				document.getElementById($scope.complementoPagos.pago[0].doctoRelacionado[i].numParcialidadid).focus();
				return false;
			}
		}
		
	}
	
	$scope.$watch('complementoPagos.pago[0].doctoRelacionado',function(){
		for(var i = 0; i< $scope.complementoPagos.pago[0].doctoRelacionado.length; i++){
			$scope.complementoPagos.pago[0].doctoRelacionado[i].ideuuid="uuid"+i;
			$scope.complementoPagos.pago[0].doctoRelacionado[i].impSaldoAntid="saldoAnt"+i;
			$scope.complementoPagos.pago[0].doctoRelacionado[i].impPagadoid="pagado"+i;
			$scope.complementoPagos.pago[0].doctoRelacionado[i].impSaldoInsolutoid="saldoIns"+i;
			$scope.complementoPagos.pago[0].doctoRelacionado[i].numParcialidadid="numPar"+i;
		}
	},true)
	
}]);
