app.service('complementoService',['$http','$q', function($http, $q){
	this.timbrar=function(send,uuid){
		var d = $q.defer();
		$http.post("/factura33/timbrar/"+uuid,send).then(
			function(response) {
				console.log(response);
				d.resolve(response.data);
			}, function(response) {
				if(response.status==403){
					alert("No está autorizado para realizar esta acción");
					$location.path("/");
				}
			});
		return d.promise;
	}
	
	this.cancelarFactura=function(uuid,rfc){
		var d = $q.defer();
		var send=uuid+","+rfc
		$http.post("/complementos/cancelarAck",send).then(
			function(response) {
				console.log(response);
				d.resolve(response.data);
			}, function(response) {
				if(response.status==403){
					alert("No está autorizado para realizar esta acción");
					$location.path("/");
				}
			});
		return d.promise;
	}
	
	
	
	this.getComplementos=function(url, page){
		var d = $q.defer();
		$http.get(url+"/"+page).then(
			function(response) {
				console.log(response);
				d.resolve(response.data);
			}, function(response) {
				if(response.status==403){
					alert("No está autorizado para realizar esta acción");
					$location.path("/");
				}
			});
		return d.promise;
	}
	
	this.getPaginas=function(rfc){
		var d = $q.defer();
		$http.get("/complementos/paginas/"+rfc).then(
			function(response) {
				console.log(response);
				d.resolve(response.data);
			}, function(response) {
				if(response.status==403){
					alert("No está autorizado para realizar esta acción");
					$location.path("/");
				}
			});
		return d.promise;
	}
	
	
	
}]);


app.controller("listComplementosController",['complementoService','$rootScope','$scope','$location','$routeParams','$cookieStore','$http','$routeParams',function(complementoService,$rootScope,$scope, $location,$routeParams,$cookieStore,$http,$routeParams){
//	serialService.findSeries($cookieStore.get("rfcEmpresa")).then(function(data){
//		$scope.seriales=data;
//	})
	$scope.ids="";
	$scope.url="/complementos/consultar/"+$cookieStore.get('rfcEmpresa');
//	$('.modal-backdrop').remove();
//	$location.path("/listFacturas");	
	$scope.filtroSerie="Serie...";
	$scope.paginaActual=1;
	$scope.llenarPags=function(){
		var inicio=0;
		if($scope.paginaActual>5){
			inicio=$scope.paginaActual-5;
		}
		var fin = inicio+9;
		if(fin>$scope.maxPage){
			fin=$scope.maxPage;
		}
		$scope.paginas=[];
		for(var i = inicio; i< fin; i++){
			$scope.paginas.push(i+1);
		}
		for(var i = inicio; i<= fin; i++){
			$('#pagA'+i).removeClass("active");
			$('#pagB'+i).removeClass("active");
		}
		$('#pagA'+$scope.paginaActual).addClass("active");
		$('#pagB'+$scope.paginaActual).addClass("active");
	}

	complementoService.getPaginas($cookieStore.get("rfcEmpresa")).then(function(data){
		$scope.maxPage=data;
		$scope.llenarPags();
	});
	
	$scope.cargarPagina=function(pag){
		if($scope.paginaActual!=pag){
			$scope.paginaActual=pag;
			$scope.cargarFacturas(pag);
		}
	}
	
	$scope.cargarFacturas=function(page){
		var modal=$('#myModal3');
		modal.modal('show');
		$('body').addClass('modal-open');
		complementoService.getComplementos($scope.url,page).then(function(data){
			modal.modal('hide');
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();
			$scope.facturas=[];
			$scope.facturas=data;
			$scope.todos=true;
			console.log($scope.facturas);
			$scope.ids="";
			for (var i = 0; i < $scope.facturas.length; i++) {
				$scope.ids=$scope.ids+$scope.facturas[i].uuid+",";
			}
			$scope.llenarPags();
			$scope.buscar.rfc="";
		});
	}
	
	$scope.paraTimbrar=function(uuid){
		$scope.uuidParaTimbrar=uuid;
	}
	$scope.paraCancelar=function(uuid){
		$scope.uuidParaCancelar=uuid;
	}
	$scope.paraCancelar33=function(uuid){
		$scope.uuidParaCancelar33=uuid;
	}
	
	$scope.paraCorreo=function(uuid){
		$scope.paraEnviar=uuid;
		$scope.OtroCorreo=true;
	}
	$scope.paraCorreo33=function(uuid){
		$scope.paraCorreoUuid=uuid;
		$scope.OtroCorreo=true;
	}
	
	$scope.timbrar= function(uuid) {
		
		comprobanteService33.timbrar($scope.uuidParaTimbrar,$scope.comprobante.email).then(function(data){
			console.log(data)
			$('#myModal3').modal('hide');
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();
			alert(data);
			$location.path("/listFacturas");
			$window.location.reload();
		})
	}
	
	$scope.editarFactura=function(uuid){
		$location.path("/editaFactura/"+$cookieStore.get("rfcEmpresa")+"/"+uuid);
	}
	$scope.editarFactura33=function(uuid){
		$location.path("/editaFactura33/"+$cookieStore.get("rfcEmpresa")+"/"+uuid);
	}
	$scope.OtroCorreo=true;
	$scope.otro = function(){
		$scope.Correo = true;
		$scope.OtroCorreo=false;
	}
	$http.get("/empresas/getEmails/"+$cookieStore.get("rfcEmpresa")).then(
			function(response) {
				console.log(response);				
				$scope.emails = response.data;
			}, function(response) {
				
	});
	
	$scope.buscar=function(){
		complementoService.buscar($scope.fechaInicio,$scope.fechaFin,$cookieStore.get('rfcEmpresa')).then(function(data){
			$scope.facturas= data;
			$scope.todos=false;
			for (var i = 0; i < $scope.facturas.length; i++) {
				$scope.facturas[i].busquedaAttr = $scope.facturas[i].rfcReceptor;
			}
		});
	}

	$scope.enviarMail=function(){
		var modal=$('#myModal3');
		modal.modal('show');
		$('body').addClass('modal-open');
		var send= $scope.comprobante.email+","+$scope.paraEnviar;
		complementoService.enviarCorreo(send).then(function(data){
			console.log(data)
			$('#myModal3').modal('hide');
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();
			alert("Correo enviado");
			$location.path("/listFacturas");
		})
	}
	$scope.enviarMail33=function(){
		var modal=$('#myModal3');
		modal.modal('show');
		$('body').addClass('modal-open');
		var send= $scope.comprobante.email+","+$scope.paraCorreoUuid;
		comprobanteService33.enviarCorreo(send).then(function(data){
			console.log(data)
			$('#myModal3').modal('hide');
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();
			alert("Correo enviado");
			$location.path("/listFacturas");
		})
	}
	
	$('.datepicker').datepicker({format: 'mm-dd-yyyy'});
	
	$('.input-daterange').datepicker({
	    format: "mm-dd-yyyy"
	});
	
	
	$('.input-daterange input').each(function() {
	    $(this).datepicker("format","mm-dd-yyyy");
	});

	$scope.cancelarFactura=function(){
		var uuid=$scope.uuidParaCancelar33;
		var rfc= $cookieStore.get("rfcEmpresa");
		waitingDialog.show('Cancelando', {dialogSize: 'sm', progressType: 'warning'});
		complementoService.cancelarFactura(uuid,rfc).then(function (data){
			
			waitingDialog.hide();
			alert(data);
			$window.location.reload();
			
		})
	}
	$scope.cancelarFactura33=function(){
		var uuid=$scope.uuidParaCancelar33;
		var rfc= $cookieStore.get("rfcEmpresa");
		comprobanteService33.cancelarFactura33(uuid,rfc).then(function (data){
			if(data!='Comprobante cancelado'){
//				alert("No se pudo cancelar, intente más tarde");
				alert(data);
			}else{
				alert("Factura Cancelada");
			}
		})
	}
	$scope.cargarFacturas(1);
	
	$scope.buscarSerie = function(serie){
		var rfc= $cookieStore.get("rfcEmpresa");
		complementoService.numPaginasSerie(rfc,serie.serie).then(function(data){
			$scope.maxPage= data;
			$scope.url="/facturacion/buscarSerie/"+$cookieStore.get('rfcEmpresa')+"/"+serie.serie;
			$scope.cargarFacturas(1);
		});
	}
	$scope.buscarRfc = function(receptor){
		var rfc= $cookieStore.get("rfcEmpresa");
		complementoService.numPaginasRfc(rfc,receptor).then(function(data){
			$scope.maxPage=data;
			$scope.url="/facturacion/buscarRec/"+$cookieStore.get('rfcEmpresa')+"/"+receptor;
			$scope.cargarFacturas(1);
		});
	}
	
	$scope.descargaXmls=function(ids){
		complementoService.getXMLs(ids).then(function(data){
			var zip = new JSZip();
			for(var i=0; i<data.folios.length; i++){
				zip.file(data.folios[i]+".xml",data.xmls[i]);
			}

			zip.generateAsync({type:"blob"})
			.then(function(content) {
			    saveAs(content, "Comprobantes.zip");
			});
		})
		
	}
	
	$scope.addComplemento = function (uuidRelacionado, index) {
		$scope.uuidParaComplementoPago = uuidRelacionado;
		$scope.cfdiParaPago = comprobanteService33.getCFDIParaPago();
		$scope.complementoPagos = comprobanteService33.getComplementoPago();
		$scope.complementoPagos.pago[0].doctoRelacionado[0].idDocumento=uuidRelacionado;
		$scope.resta= $scope.complementoPagos.pago[0].doctoRelacionado[0].impSaldoAnt*1-$scope.complementoPagos.pago[0].monto*1;
	}
	
//	$scope.$watch('complementoPagos', function(){
//		if($scope.complementoPagos.pago[0].doctoRelacionado[0].impSaldoAnt && $scope.complementoPagos.pago[0].monto){
//			$scope.complementoPagos.pago[0].doctoRelacionado[0].impSaldoInsoluto= $scope.complementoPagos.pago[0].doctoRelacionado[0].impSaldoAnt*1-$scope.complementoPagos.pago[0].monto*1;
//			$scope.complementoPagos.pago[0].doctoRelacionado[0].impPagado=$scope.complementoPagos.pago[0].monto;
//		}
//	}, true)
//	
	$scope.tabIds = [ "comprobante", "complementoDePago" ];
	var myEl = angular.element("#comprobante");
	myEl.addClass('active in');
	$scope.showTab = function(id) {
		$scope.tabIds.forEach(function(i) {
			// console.log(i);
			var myEl = angular.element("#" + i);
			// console.log(myEl.hasClass());
			myEl.removeClass('active');
			myEl.removeClass('in');
			myEl.removeClass('show');
		});
		var myEl = angular.element("#" + id);
		myEl.addClass('active in show');
	}
	
	$scope.enviarComplemento=function(){
		$scope.cfdiParaPago.conceptos={}
		var send={uuid:$scope.uuidParaComplementoPago,
				complementoPagos:$scope.complementoPagos,
				serie:$scope.serialElegido
		}
		console.log(send)
		comprobanteService33.timbrarComplemento(send);
	}
	
	
}]);