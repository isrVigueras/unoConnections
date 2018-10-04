app.controller("facturaListController",['comprobanteService','comprobanteService33','$rootScope','$scope','$location','$routeParams','$cookieStore','$http','$routeParams','serialService',function(comprobanteService,comprobanteService33,$rootScope,$scope, $location,$routeParams,$cookieStore,$http,$routeParams,serialService){
	serialService.findSeries($cookieStore.get("rfcEmpresa")).then(function(data){
		$scope.seriales=data;
	})
	$scope.ids="";
	$scope.url="/facturacion/consultar/"+$cookieStore.get('rfcEmpresa');
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

	comprobanteService33.getPaginas($cookieStore.get("rfcEmpresa")).then(function(data){
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
		comprobanteService.getComprobantes($scope.url,page).then(function(data){
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
		comprobanteService.buscar($scope.fechaInicio,$scope.fechaFin,$cookieStore.get('rfcEmpresa')).then(function(data){
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
		comprobanteService.enviarCorreo(send).then(function(data){
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
		var uuid=$scope.uuidParaCancelar;
		var rfc= $cookieStore.get("rfcEmpresa");
		comprobanteService.cancelarFactura(uuid,rfc).then(function (data){
			if(data[1]!='0'){
				alert("No se pudo cancelar, intente más tarde");
			}else{
				alert("Factura Cancelada");
			}
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
		comprobanteService.numPaginasSerie(rfc,serie.serie).then(function(data){
			$scope.maxPage= data;
			$scope.url="/facturacion/buscarSerie/"+$cookieStore.get('rfcEmpresa')+"/"+serie.serie;
			$scope.cargarFacturas(1);
		});
	}
	$scope.buscarRfc = function(receptor){
		var rfc= $cookieStore.get("rfcEmpresa");
		comprobanteService.numPaginasRfc(rfc,receptor).then(function(data){
			$scope.maxPage=data;
			$scope.url="/facturacion/buscarRec/"+$cookieStore.get('rfcEmpresa')+"/"+receptor;
			$scope.cargarFacturas(1);
		});
	}
	
	$scope.descargaXmls=function(ids){
		comprobanteService.getXMLs(ids).then(function(data){
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
	
	$scope.$watch('complementoPagos', function(){
		if($scope.complementoPagos.pago[0].doctoRelacionado[0].impSaldoAnt && $scope.complementoPagos.pago[0].monto){
			$scope.complementoPagos.pago[0].doctoRelacionado[0].impSaldoInsoluto= $scope.complementoPagos.pago[0].doctoRelacionado[0].impSaldoAnt*1-$scope.complementoPagos.pago[0].monto*1;
			$scope.complementoPagos.pago[0].doctoRelacionado[0].impPagado=$scope.complementoPagos.pago[0].monto;
		}
	}, true)
	
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
		waitingDialog.show('Enviando Complemento', {dialogSize: 'md', progressType: 'warning'});
		comprobanteService33.timbrarComplemento(send).then(function(data){
			waitingDialog.hide();
			alert(data);
			$location.path("/listComplementos");
		});
	}
	
	
}]);

app.controller("facturaEditController33",['serialService','conceptosService','comprobanteService','comprobanteService33','$rootScope','$scope','$location','$routeParams',function(serialService,conceptosService,comprobanteService,comprobanteService33,$rootScope,$scope, $location,$routeParams){
	$scope.ids = [ "comprobante", "emisorCont", "receptorCont" ];
	var myEl = angular.element("#comprobante");
	myEl.addClass('active in');
	$scope.showTab = function(id) {
		$scope.ids.forEach(function(i) {
			// console.log(i);
			var myEl = angular.element("#" + i);
			// console.log(myEl.hasClass());
			myEl.removeClass('active');
			myEl.removeClass('in');
			myEl.removeClass('show');
		});
		var myEl = angular.element("#" + id);
		myEl.addClass('active in show');
	};
	
	
	//cargar comprobante
	$scope.comprobante= comprobanteService.getCFDI32();
	
	$scope.usoFDIhide=function(){
		var str = $scope.comprobante.receptor.rfc;
	    var res = str.substring(0, 4);
		if(!res.search("[A-Z]{4}"))	{
			$(".nomoral").show();
			
		}else {
			
			$(".nomoral").hide();
		}
	}
	
	//$scope.receptores = comprobanteService.getDummyReceptores();
	
	$scope.importeCambio=function(){
		$scope.concepto.importe= $scope.conceptoAux.cantidad * $scope.concepto.valorUnitario;	
	}
	
	$scope.addConcepto=function(){
		var concepto= $scope.concepto;
		var cantidad=$scope.conceptoAux.cantidad;
		var importe=$scope.conceptoAux.cantidad * concepto.valorUnitario;
		var descripcion = $scope.concepto.descripcion;
		var valorUnitario= $scope.concepto.valorUnitario;
		var unidad= $scope.concepto.unidad
		var noIdentificacion=$scope.concepto.noIdentificacion;
		concepto={
				cantidad:cantidad,
				importe:importe,
				unidad:unidad,
				descripcion:descripcion,
				valorUnitario:valorUnitario,
				noIdentificacion:noIdentificacion,
				claveProdServ:{valor:$scope.concepto.claveProdServ},
				claveUnidad:{valor:$scope.concepto.claveUnidad},
				impuestos : {
					traslados : {
						traslado : [] 
					}

				}
		}
//		concepto.cantidad=$scope.conceptoAux.cantidad;
//		concepto.importe = $scope.conceptoAux.cantidad * concepto.valorUnitario;
		var trasladoIVA=comprobanteService33.getTraslado();
		
		if($scope.ivaIncluido){
			var aux=concepto.valorUnitario;
			var valor=concepto.valorUnitario/1.16;
			valor=valor.toFixed(2)*1;
			aux= aux-valor;
			aux= aux*concepto.cantidad;
			concepto.valorUnitario= valor;
			concepto.impuesto= aux;
			concepto.importe= concepto.valorUnitario*concepto.cantidad;
		}else{
			concepto.impuesto= concepto.valorUnitario*0.16* concepto.cantidad;
			trasladoIVA.base=concepto.importe;
			trasladoIVA.importe=trasladoIVA.base * trasladoIVA.tasaOCuota;
			trasladoIVA.importe=trasladoIVA.importe.toFixed(2)*1;
			concepto.impuestos.traslados.traslado.push(trasladoIVA);
			
		}
//		concepto.cantidad=$scope.conceptoAux.cantidad;
//		concepto.importe = $scope.conceptoAux.cantidad * concepto.valorUnitario;
		$scope.conceptoAux.cantidad="";
		$scope.comprobante.conceptos.concepto.push(concepto);
		$scope.concepto= comprobanteService33.nuevoConcepto();
		$scope.comprobante.subTotal = $scope.getSubtotal($scope.comprobante.conceptos.concepto);
		
		//modificar los valores de los parámetros por valores reales
		$scope.comprobante.total = $scope.getTotal($scope.comprobante.subTotal, 0, 0, 0);
		
		$scope.calcularIva();
		$scope.getSubtotal($scope.comprobante.conceptos.concepto);
		$scope.actualizaTotal();
	};
	
	$scope.calcularIva=function(){
		
		if ($scope.comprobante.conceptos.concepto.length > 0) {
			for(var i =0; i< $scope.comprobante.conceptos.concepto.length;i++){
				if($scope.ivaIncluido){
					var valor=$scope.comprobante.conceptos.concepto[i].valorUnitario/1.16;
					valor=valor.toFixed(2)*1;
					$scope.comprobante.conceptos.concepto[i].valorUnitario= valor;
											
				}else{
					if($scope.ivaIncluido==false){
						var valor=$scope.comprobante.conceptos.concepto[i].valorUnitario *1.16;
						valor=valor.toFixed(2)*1;
						$scope.comprobante.conceptos.concepto[i].valorUnitario= valor;
					}
				}
				$scope.comprobante.conceptos.concepto[i].importe=$scope.comprobante.conceptos.concepto[i].valorUnitario*$scope.comprobante.conceptos.concepto[i].cantidad;	
			}
		}
		$scope.getSubtotal($scope.comprobante.conceptos.concepto);
		$scope.actualizaTotal();
		

	}
	
	$scope.$watch('comprobante.subTotal',function(){
			var total=$scope.comprobante.subTotal;
//			var ntotal= total/1.16;
			var iva=0;
			if ($scope.comprobante.descuento) {
				iva = (total - $scope.comprobante.descuento)*0.16; 
			} else {
				iva=total*0.16;
			}
			iva = iva.toFixed(2)*1;
			$scope.comprobante.impuestos.totalImpuestosTrasladados=iva;
//			$scope.comprobante.subTotal=ntotal;
			$scope.comprobante.total=$scope.comprobante.subTotal+iva;
			$scope.comprobante.impuestos.traslados.traslado[0].importe=iva;
		
	},true);
	
	$scope.$watch('comprobante.descuento',function(){
		if ($scope.comprobante.descuento == 0) {
			$scope.actualizaTotal();
			$scope.comprobante.descuento = null;
			$scope.comprobante.motivoDescuento = null;
		} else if ($scope.comprobante.descuento > 0) {
			$scope.actualizaTotal();
		}
	},true);
	
	$scope.quitarConcepto=function(index){
		var importe = $scope.comprobante.conceptos.concepto[index].importe;
		$scope.comprobante.conceptos.concepto.splice(index,1)
		$scope.comprobante.subTotal -= importe;
		$scope.calcularIva();
//		var arrTraslado = $scope.comprobante.impuestos.traslados.traslado;
//		if (arrTraslado) {
//			// obtener el impuesto trasladado
//		}
//		if ($scope.comprobante.conceptos.concepto.length > 0) {
//			$scope.comprobante.subTotal = $scope.getSubtotal($scope.comprobante.conceptos.concepto);
//		}
	};

	$scope.$watch('comprobante.conceptos.concepto',function(){
		if ($scope.comprobante.conceptos.concepto.length > 0) {
			$scope.comprobante.subTotal = $scope.getSubtotal($scope.comprobante.conceptos.concepto);
		}else{
			$scope.comprobante.subTotal=0.0;
		}
		$scope.actualizaTotal();
	},true);
	
	$scope.$watch('concepto',function(){
		$scope.concepto.importe=0.0;
		if ($scope.concepto.cantidad) {
			if($scope.concepto.valorUnitario){
				$scope.concepto.importe=$scope.concepto.cantidad*$scope.concepto.valorUnitario;
			}
		}
	},true);
	
	//Inicializar el iva
	if ($scope.comprobante.impuestos.traslados == null) {
		$scope.comprobante.impuestos.traslados = comprobanteService.initTraslados();
	}
	if(!$scope.comprobante.impuestos.traslados.traslado){
		$scope.comprobante.impuestos.traslados.traslado=[];
	}
	$scope.comprobante.impuestos.traslados.traslado.push({impuesto:"IVA",tasa:0.16,importe:0});
	
	
	$scope.$watch('comprobante.descuento',function(){
		var total=$scope.comprobante.subTotal;

		var iva=0;
		if(!$scope.ivaIncluido){
			if ($scope.comprobante.descuento) {
				iva = (total - $scope.comprobante.descuento)*0.16; 
			} else {
				iva=total*0.16;
			}
		}else{
			for(var i=0; i<$scope.comprobante.conceptos.concepto.length; i++){
				
				iva+=$scope.comprobante.conceptos.concepto[i].impuesto;
			}
		}
		iva=iva.toFixed(2)*1;
		$scope.comprobante.impuestos.totalImpuestosTrasladados=iva;
//		$scope.comprobante.subTotal=ntotal;
		$scope.comprobante.total=$scope.comprobante.subTotal+iva;
		$scope.comprobante.total= $scope.comprobante.total.toFixed(2)*1;
		$scope.comprobante.impuestos.traslados.traslado[0].importe=iva;
	},true);
	
	$scope.addTraslado=function(){
		var traslado= $scope.traslado;
		if ($scope.comprobante.impuestos.traslados == null) {
			$scope.comprobante.impuestos.traslados = comprobanteService.initTraslados();
		}
		traslado.importe = $scope.comprobante.subTotal * traslado.tasa;
		if(!$scope.comprobante.impuestos.traslados.traslado){
			$scope.comprobante.impuestos.traslados.traslado=[];
		}
		$scope.comprobante.impuestos.traslados.traslado.push(traslado);
		
		var totalImpTras = $scope.comprobante.impuestos.totalImpuestosTrasladados;
		if (totalImpTras === null)
			totalImpTras = 0.0;
		var i;
		for (i = 0; i < $scope.comprobante.impuestos.traslados.traslado.length; i++) {
			totalImpTras += $scope.comprobante.impuestos.traslados.traslado[i].importe;
		}
		$scope.comprobante.impuestos.totalImpuestosTrasladados = totalImpTras;
		
		$scope.comprobante.total += totalImpTras;
		$scope.traslado={};
	}
	
	$scope.quitarTraslado=function(index){
		var importeTraslado = $scope.comprobante.impuestos.traslados.traslado[index].importe;
		$scope.comprobante.impuestos.traslados.traslado.splice(index,1);
		$scope.comprobante.impuestos.totalImpuestosTrasladados -= importeTraslado;
		$scope.comprobante.total -= importeTraslado;
	}
	
	$scope.addRetencion=function(){
		var retencion= $scope.retencion;
		retencion.importe=Number(retencion.importe);
		if ($scope.comprobante.impuestos.retenciones == null) {
			$scope.comprobante.impuestos.retenciones = comprobanteService.initRetenciones();
		}
		if(!$scope.comprobante.impuestos.retenciones.retencion){
			$scope.comprobante.impuestos.retenciones.retencion=[];
		}
		$scope.comprobante.impuestos.retenciones.retencion.push(retencion);
		$scope.retencion={};
		if (!totalImpRet) {
 			var totalImpRet=0.0;
 		}
 		var i;	
		for (i = 0; i < $scope.comprobante.impuestos.retenciones.retencion.length; i++) {
 			totalImpRet += $scope.comprobante.impuestos.retenciones.retencion[i].importe;
 		}
 		$scope.comprobante.impuestos.totalImpuestosRetenidos = totalImpRet;
 		
 		$scope.actualizaTotal();;
 		//$scope.retencion={};
		}
	
	$scope.quitarRetencion=function(index){
		$scope.comprobante.impuestos.retenciones.retencion.splice(index,1);
		if (!totalImpRet) {
			var totalImpRet=0.0;
		}
		var i;
		for (i = 0; i < $scope.comprobante.impuestos.retenciones.retencion.length; i++) {
			totalImpRet += $scope.comprobante.impuestos.retenciones.retencion[i].importe;
			$scope.comprobante.impuestos.totalImpuestosRetenidos = totalImpRet;
		}
		if( $scope.comprobante.impuestos.retenciones.retencion.length == 0){
			$scope.comprobante.impuestos.totalImpuestosRetenidos = 0;
			$scope.comprobante.impuestos.retenciones==null;
		}
		$scope.actualizaTotal();
	}
	
	$scope.guardarPrefactura = function() {
		$scope.comprobante.fecha = new Date();
		if ($scope.comprobante.emisor.domicilioFiscal == null) {
			$scope.comprobante.emisor.domicilioFiscal = comprobanteService.getDomicilio();
		}
		if ($scope.comprobante.receptor.domicilio == null) {
			$scope.comprobante.receptor.domicilio = comprobanteService.getDomicilio();
		}
		$scope.comprobante.serie = $scope.serialElegido.serie;
		$scope.comprobante.folio = $scope.serialElegido.folio;
		if($scope.comentarios==null){
			$scope.comentarios=="";
		}
		var send ={
				comentarios: $scope.comentarios,
				comprobante : $scope.comprobante
				
			} 
		comprobanteService.actualizarPrefactura(send,$routeParams.uuid).then(function(data){
			console.log(data)
			alert(data);
			$location.path("/listFacturas");
		})
		
	}
	
	
	$scope.enviarComprobante=function(){
		if($scope.serialElegido.serie) {
			$scope.comprobante.serie = $scope.serialElegido.serie;
			$scope.comprobante.folio = $scope.serialElegido.folio;
			$scope.mensajeFac="Procesando...";
			$scope.comprobante.fecha = new Date();
			if ($scope.comprobante.emisor.domicilioFiscal == null) {
				$scope.comprobante.emisor.domicilioFiscal = comprobanteService.getDomicilio();
			}
			if ($scope.comprobante.receptor.domicilio == null) {
				$scope.comprobante.receptor.domicilio = comprobanteService.getDomicilio();
			}
			$scope.comprobante.serie = $scope.serialElegido.serie;
			$scope.comprobante.folio = $scope.serialElegido.folio;
			var send ={
					comprobante : $scope.comprobante,
					email : $scope.comprobante.email,
					comentarios:$scope.comentarios
				}
			$scope.procesando=true;
			$scope.finalizado=false;
			$scope.comprobante.total=   $scope.comprobante.total.toFixed(2)*1;
			$scope.comprobante.subTotal=   $scope.comprobante.subTotal.toFixed(2)*1;
			$scope.comprobante.impuestos.totalImpuestosTrasladados= $scope.comprobante.impuestos.totalImpuestosTrasladados.toFixed(2)*1;
			
			for(var i =0; i < $scope.comprobante.conceptos.concepto.length;i++){
				$scope.comprobante.conceptos.concepto[i].importe=$scope.comprobante.conceptos.concepto[i].importe.toFixed(2)*1;
			}
			comprobanteService33.timbrarGuardado(send,$routeParams.uuid).then(function(data){
				$scope.mensajeFac=data;
				$scope.finalizado=true;
				console.log(data)
				alert(data);
				$location.path("/listFacturas");
			})
		} else {
			alert("No se eligió una serie para el comprobante");
			$scope.terminado=true;
			$scope.mensajeFac="De clic fuera de esta ventana para elegir la serie";
		}
	}
	$scope.procesando=false;
	$scope.getSubtotal = function(arregloConcepto) {
		if (arregloConcepto != undefined && arregloConcepto != null) {
			var tamArreglo = arregloConcepto.length;
			var i, subtotal = 0.0;
			for (i = 0; i < tamArreglo; i++) {
				subtotal += arregloConcepto[i].importe;
			}
		}
		return subtotal;
	}
	
	$scope.actualizaTotal=function(){
			$scope.comprobante.total=$scope.comprobante.subTotal;
		if($scope.comprobante.descuento){
			$scope.comprobante.total-=$scope.comprobante.descuento;
		}
		if($scope.comprobante.impuestos.totalImpuestosRetenidos){
			$scope.comprobante.total-=$scope.comprobante.impuestos.totalImpuestosRetenidos;
		}
		$scope.comprobante.total+= $scope.comprobante.impuestos.totalImpuestosTrasladados;
	}
	
	$scope.getTotal = function(subtotal, descuentos, impTras, impRet) {
		return subtotal - descuentos + impTras - impRet;
	}
	$scope.rfc= $routeParams.rfc;	
	$scope.cargarReceptores=function(){
		
		comprobanteService33.getReceptores($scope.rfc).then(function(data){
			if (data.emisor != undefined) {
				if (data.emisor.receptores != undefined) {
					$scope.receptores=data.emisor.receptores;
				}
				$scope.comprobante.emisor.rfc= data.emisor.rfc;
				$scope.comprobante.emisor.nombre = data.nombreEmisor;
				$scope.comprobante.emisor.domicilioFiscal=data.domicilioFiscal;
				$scope.emails = data.emails;
			}
			$scope.comprobante.lugarExpedicion = data.lugarDeExpedicion;
		})
	}
	$scope.cargarReceptores(); 
	
	conceptosService.cargaConceptos($routeParams.rfc).then(function(data){
		$scope.conceptosEmp=data.conceptos;
	});
	serialService.findSeries($routeParams.rfc).then(function(data){
		$scope.seriales=data;
		comprobanteService33.getComprobanteGuardado($routeParams.uuid).then(function(data){
			$scope.comprobante= data.comprobante;
			$scope.comentarios=data.comentarios;
			$scope.impuestosLocales=data.impuestosLocales;
			if($scope.impuestosLocales != null) {
				$scope.mostrarImpLocales = true;
			}
			$scope.serialElegido={serie:data.serie};
			$scope.actualizaTotal();
			$scope.comprobante.fecha=new Date();
		});
	});
	/*$scope.rfcReceptorChange = function(index) {
		console.log(index);
		console.log($scope.receptores);
		$scope.comprobante.receptor.nombre = $scope.receptores[index].nombre;
		console.log($scope.comprobante.receptor.nombre);
	}
	
	$scope.nombreReceptorChange = function(index) {
		$scope.comprobante.receptor.rfc = $scope.receptores[index].rfc;
		console.log($scope.comprobante.receptor.rfc);
	}*/

	$scope.OtroCorreo=true;
	$scope.otro = function(){
		$scope.Correo = true;
		$scope.OtroCorreo=false;
	}
	
	$scope.impuestosLocales = comprobanteService.getComplementoImpuestosLocales();
	
	$scope.$watch('impuestosLocales.retencionesLocalesAndTrasladosLocales',function(){
		$scope.actualizaTotal();
	},true);
	
	$scope.addRetencionLocal = function() {
		var retenLocal = $scope.retencionLocal;
		var importe = (retenLocal.tasadeRetencion / 100) * $scope.comprobante.subTotal;
		importe = importe.toFixed(2) * 1;
		
		retenLocal = {
			impLocRetenido : $scope.retencionLocal.impLocRetenido,
			tasadeRetencion : $scope.retencionLocal.tasadeRetencion,
			importe : importe
		}
		
		$scope.impuestosLocales.retencionesLocalesAndTrasladosLocales.push(retenLocal);
		$scope.impuestosLocales.totaldeRetenciones += retenLocal.importe;
		$scope.retencionLocal.impLocRetenido = "";
		$scope.retencionLocal.tasadeRetencion = 0;
	}
	
	$scope.quitarRetencionLocal = function(index) {
		var retenLocalRemove = $scope.impuestosLocales.retencionesLocalesAndTrasladosLocales.splice(index, 1);
		$scope.impuestosLocales.totaldeRetenciones -= retenLocalRemove[0].importe;
	}
	
}])