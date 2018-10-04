app.service("comprobanteService", ['$http','$q',function($http,$q) {
	this.nuevoConcepto=function(){
		return {
			noIdentificacion : null,
			cantidad : null,
			unidad : null,
			descripcion : null,
			valorUnitario : null,
			importe : null,
			informacionAduanera: null,
			cuentaPredial : null,
			complementoConcepto : null,
			parte : null // usar init y get

		}
	}
	 
	this.initInformacionAduanera = function() {
		return [];
	}
	
	this.getInformacionAduanera = function() {
		return {
			numero: null,
			fecha: null,
			aduana: null
		};
	}
	
	this.getCuentaPredial = function() {
		return {
			numero : null
		};
	}
	
	this.getComplementoConcepto = function() {
		return {};
	}
	
	this.initParte = function() {
		return [];
	}
	
	this.getParte = function() {
		return {
			noIdentificacion : null,
			cantidad : null,
			unidad : null,
			descripcion : null,
			valorUnitario : null,
			importe : null,
			informacionAduanera : null //usar init y get
		}
	}
	
	// usar esta función para crear los objetos domicilioFiscal y expedidoEn del Emisor
	// y también para crear el objeto domicilio del Receptor
	this.getDomicilio = function() {
		return {
			calle:"Calle",
			noExterior:null,
			noInterior:null,
			colonia:null,
			localidad:null,
			referencia:null,
			municipio:"Municipio",
			estado:"Estado",
			pais:"País",
			codigoPostal:"50000"
		}
	}
	
	this.getImpuestos = function() {
		
	}
	
	this.initRetenciones = function() {
		return {
			retencion: null
		}
	}
	
	this.initTraslados = function() {
		return {
			traslado: null
		}
	}
	
	this.initComplementoOAddenda = function() {
		return {
			any: []
		};
	}
	
	this.getNuevo = function() {
		return {
			version : "3.2",//3.3
			serie : null,
			folio : null,
			fecha : new Date(),
			sello : null,
			formaPago : null,
			noCertificado : null,
			certificado : null,
			condicionesDePago : null,
			subTotal : null,
			descuento : null,
			moneda : "MXN",
			tipoCambio : null,
			total : null,
			tipoDeComprobante : null,
			metodoPago : null,
			lugarExpedicion : null,
			confirmacion : null,

			emisor : {
				rfc : null,
				nombre : null,
				regimenFiscal : null,
			},
			receptor : {
				rfc : null,
				nombre : null,
				residenciaFiscal : null,
				numRegIdTrib : null,
				usoCFDI : null,
			},
			conceptos : {
				concepto: [ {
					claveProdServ : null,
					noIdentificacion : null,
					cantidad : null,
					claveUnidad : null,
					unidad : null,
					descripcion : null,
					valorUnitario : null,
					importe : null,
					descuento : null,
					impuestos : {
						traslados : {
							traslado : [ {
								base : null,
								impuesto : null,
								tipoFactor : null,
								tasaOCuota : null,
								importe : null,
							} ] 
						},

						retenciones : {
							retencion : [ {
								base : null,
								impuesto : null,
								tipoFactor : null,
								tasaOCuota : null,
								importe : null,
							} ] 
						} 

					},
					informacionAduanera : [{
						numeroPedimento : null,
					}] ,
					cuentaPredial : {
						numero : null,
					},
					complementoConcepto : {},
					parte : [{
						claveProdServ : null,
						noIdentificacion : null,
						cantidad : null,
						unidad : null,
						descripcion : null,
						valorUnitario : null,
						importe : null,
						informacionAduanera : [{
							numeroPedimento : null,
						}]
					}]

				}]
			} ,
			impuestos : {
				totalImpuestosRetenidos : null,
				totalImpuestosTraslados : null,
				retenciones : {
					retencion:[ {
						impuesto : null,
						importe : null
					} ]
				},
				traslados : {
					traslado:[ {
						impuesto : null,
						tipoFactor : null,
						tasaOCuota : null,
						importe : null
					} ]
				}
			},
			complemento :[ {
				any: []
			}],
			addenda : {
				any: []
			}

		}
	}
	
	this.getCFDI32 = function() {
		return {
			version : "3.2",//3.3
			serie : null,
			folio : null,
			fecha : new Date(),
			sello : null,
			formaDePago : "Pago en una sola exhibición",
			noCertificado : null,
			certificado : null,
			condicionesDePago : null,
			subTotal : null,
			descuento : null,
			motivoDescuento : null,
			moneda : "MXN",
			tipoCambio : null,
			total : null,
			tipoDeComprobante : "ingreso",
			metodoDePago : "03-Transferencia electrónica de fondos",
			lugarExpedicion : null,
			numCtaPago : null,
			folioFiscalOrig : null,
			serieFolioFiscalOrig : null,
			fechaFolioFiscalOrig : null,
			montoFolioFiscalOrig : null,

			emisor : {
				rfc : null, //hardcode para Web Service de pruebas
				nombre : null,
				regimenFiscal : [ {
				    regimen: "PERSONAS MORALES"
				} ],
				domicilioFiscal: null, // usar getDomicilio
				expedidoEn: null // usar getDomicilio
				
			},
			receptor : {
				rfc : null,
				nombre : null,
			    domicilio: null // usar getDomicilio
			},
			conceptos : {
				concepto: [ 
//					{
//					noIdentificacion : null,
//					cantidad : null,
//					unidad : null,
//					descripcion : null,
//					valorUnitario : null,
//					importe : null,
//					informacionAduanera: [{
//						numero: null,
//						fecha: null,
//						aduana: null
//					}],
//					cuentaPredial : {
//						numero : null
//					},
//					complementoConcepto : {},
//					parte : [{
//						noIdentificacion : null,
//						cantidad : null,
//						unidad : null,
//						descripcion : null,
//						valorUnitario : null,
//						importe : null,
//						informacionAduanera : [{
//							numero: null,
//							fecha: null,
//							aduana: null
//						}]
//					}]
//
//				}
					]
			} ,
			impuestos : {
				totalImpuestosRetenidos : null,
				totalImpuestosTrasladados : null,
				retenciones : null,
				traslados : null
			},
			complemento : null,
			addenda : null

		}
	}
	
	
	this.getComplementoImpuestosLocales = function() {
		return {
			retencionesLocalesAndTrasladosLocales : [],
			version : "1.0",
			totaldeRetenciones : 0.0,
			totaldeTraslados : 0.0
		}
	}
	
	this.actualizarPrefactura=function(comprobante,uuid){
		var d = $q.defer();
		$http.post("/facturacion/actualizar/"+uuid,comprobante).then(
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
	
	this.guardarPrefactura=function(comprobante){
		var d = $q.defer();
		$http.post("/facturacion/generar/",comprobante).then(
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
	
	this.enviarComprobante=function(send){
		var d = $q.defer();
		$http.post("/facturacion/timbrar/",send).then(
			function(response) {
				console.log(response);
				d.resolve(response.data);
			}, function(response) {
				if(response.status==403){
					alert("No está autorizado para realizar esta acción");
					$location.path("/");
				}
				if(response.status==408){
					alert("Se agotó el tiempo de espera, inténtelo de nuevo");
				}
			});
		return d.promise;
	}
	
	this.enviarCorreo=function(send){
		var d = $q.defer();
		$http.post("/facturacion/emailTo/",send).then(
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
	
	this.timbrarGuardado=function(send,uuid){
		var d = $q.defer();
		$http.post("/facturacion/timbrar/"+uuid,send).then(
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
	
	this.timbrar = function(uuid,email) {
		var d = $q.defer();
		$http.post("/facturacion/timbrarGenerado/",""+uuid+","+email).then(
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
	
	this.getComprobantes=function(url,page){
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
	
	this.getDummyReceptores = function() {
		return [
			  {
				  rfc : "ABC010101AB0",
				  nombre : "Empresa Prueba 1 SA de CV"
			  },
			  {
				  rfc : "DEF010101CD1",
				  nombre : "Consultoría Prueba 2 SA de CV"
			  },
			  {
				  rfc : "GHI010101EF2",
				  nombre : "Empresa Prueba 3 "
			  }
		]
	}

	this.getReceptores=function(rfc){
		var d = $q.defer();
		$http.get("/emisor/consultar/"+rfc).then(
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
	
	this.getComprobanteGuardado=function(uuid){
		var d = $q.defer();
		$http.get("/facturacion/editar/"+uuid).then(
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
	
	this.buscar=function(fi,ff,rfc){
		var d = $q.defer();
		send={
				params:{
					fi:fi,
					ff,ff
				}
		}
		$http.get("/facturacion/buscar/"+rfc,send).then(
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
		$http.post("/facturacion/cancelarAck/",""+uuid+","+rfc).then(
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
	
	//numPaginas
	this.numPaginasSerie=function(rfc,serie){
		var d = $q.defer();
		$http.get("/facturacion/numPaginasSerie/"+rfc+"/"+serie).then(
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
	this.numPaginasRfc=function(rfc,receptor){
		var d = $q.defer();
		$http.get("/facturacion/numPaginasRec/"+rfc+"/"+receptor).then(
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
		$http.get("/facturacion/numPaginas/"+rfc).then(
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
	this.getXMLs=function(ids){
		var d = $q.defer();
		$http.get("/facturacion/obtenerXMLmultiple/"+ids).then(
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

app.controller("comprobante", [ '$scope', '$location', 'comprobanteService','$routeParams','conceptosService','serialService','$window',
	function($scope, $location, comprobanteService,$routeParams,conceptosService,serialService,$window) {
		$scope.bloqueado=false;
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
		$scope.comprobante= comprobanteService.getCFDI32();
		//$scope.receptores = comprobanteService.getDummyReceptores();
		
		$scope.addConcepto=function(){
			var concepto= $scope.concepto;
			var cantidad=$scope.conceptoAux.cantidad;
			var importe=$scope.conceptoAux.cantidad * concepto.valorUnitario;
			importe=importe.toFixed(2)*1;
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
					noIdentificacion:noIdentificacion
			}
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
			}
//			concepto.cantidad=$scope.conceptoAux.cantidad;
//			concepto.importe = $scope.conceptoAux.cantidad * concepto.valorUnitario;
			$scope.conceptoAux.cantidad="";
			$scope.comprobante.conceptos.concepto.push(concepto);
			$scope.concepto= comprobanteService.nuevoConcepto();
			$scope.comprobante.subTotal = $scope.getSubtotal($scope.comprobante.conceptos.concepto);
			
			//modificar los valores de los parámetros por valores reales
			$scope.comprobante.total = $scope.getTotal($scope.comprobante.subTotal, 0, 0, 0);
			
			$scope.calcularIva();
			$scope.getSubtotal($scope.comprobante.conceptos.concepto);
			$scope.actualizaTotal();
		};
		
		$scope.calcularIva=function(){
			

		}
		$scope.calcularIvaEvent=function(){
			
			if ($scope.comprobante.conceptos.concepto.length > 0) {
				for(var i =0; i< $scope.comprobante.conceptos.concepto.length;i++){
					if($scope.ivaIncluido){
						var aux=$scope.comprobante.conceptos.concepto[i].valorUnitario;
						var valor=$scope.comprobante.conceptos.concepto[i].valorUnitario/1.16;
//						valor=valor.toFixed(2)*1;
						aux=aux-valor;
//						aux= aux.toFixed(2)*1;
						$scope.comprobante.conceptos.concepto[i].valorUnitario= valor;
						$scope.comprobante.conceptos.concepto[i].impuesto=aux*$scope.comprobante.conceptos.concepto[i].cantidad;
					}else{
						if($scope.ivaIncluido==false){
							var impuesto=$scope.comprobante.conceptos.concepto[i].impuesto/$scope.comprobante.conceptos.concepto[i].cantidad;
							
							var valor=$scope.comprobante.conceptos.concepto[i].valorUnitario+impuesto;
//							valor=valor.toFixed(2)*1;
							$scope.comprobante.conceptos.concepto[i].valorUnitario= valor;
							var aux= valor*0.16;
//							aux= aux.toFixed(2)*1;
							$scope.comprobante.conceptos.concepto[i].impuesto=aux*$scope.comprobante.conceptos.concepto[i].cantidad;
							
						}
					}
					$scope.comprobante.conceptos.concepto[i].importe=$scope.comprobante.conceptos.concepto[i].valorUnitario*$scope.comprobante.conceptos.concepto[i].cantidad;
					$scope.comprobante.conceptos.concepto[i].importe= $scope.comprobante.conceptos.concepto[i].importe.toFixed(2)*1;
				}
			}
			$scope.getSubtotal($scope.comprobante.conceptos.concepto);
			$scope.actualizaTotal();

		}
		
		$scope.$watch('comprobante.subTotal',function(){
				var total=$scope.comprobante.subTotal;
//				var ntotal= total/1.16;
//				var iva= total*0.16;
//				if ($scope.ivaIncluido) {
//					iva = iva.toFixed(2)*1;
//				} else {
//					iva = iva.toFixed(2)*1;
//				}
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
//				$scope.comprobante.subTotal=ntotal;
				$scope.comprobante.total=$scope.comprobante.subTotal+iva;
				$scope.comprobante.total= $scope.comprobante.total.toFixed(2)*1;
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
//			var arrTraslado = $scope.comprobante.impuestos.traslados.traslado;
//			if (arrTraslado) {
//				// obtener el impuesto trasladado
//			}
//			if ($scope.comprobante.conceptos.concepto.length > 0) {
//				$scope.comprobante.subTotal = $scope.getSubtotal($scope.comprobante.conceptos.concepto);
//			}
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
		$scope.comprobante.impuestos.traslados.traslado.push({impuesto:"IVA",tasa:16.00,importe:0});
		
		
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
//			$scope.comprobante.subTotal=ntotal;
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
			if ($scope.ivaIncluido) {
				totalImpTras = totalImpTras + 0.01;
				totalImpTras = totalImpTras.toFixed()*1;
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
			
			$scope.actualizaTotal();
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
			if($scope.serialElegido.serie){
				$scope.comprobante.serie = $scope.serialElegido.serie;
				$scope.comprobante.folio = $scope.serialElegido.folio;
				if($scope.comprobante.receptor){
					$scope.comprobante.fecha = new Date();
					if ($scope.comprobante.emisor.domicilioFiscal == null) {
						$scope.comprobante.emisor.domicilioFiscal = comprobanteService.getDomicilio();
					}
					if ($scope.comprobante.receptor.domicilio == null) {
						$scope.comprobante.receptor.domicilio = comprobanteService.getDomicilio();
					}
					if($scope.comentarios==null){
						$scope.comentarios=="";
					}
			
					$scope.comprobante.total=   $scope.comprobante.total.toFixed(2)*1;
					$scope.comprobante.subTotal=   $scope.comprobante.subTotal.toFixed(2)*1;
					$scope.comprobante.impuestos.totalImpuestosTrasladados= $scope.comprobante.impuestos.totalImpuestosTrasladados.toFixed(2)*1;
					
					for(var i =0; i < $scope.comprobante.conceptos.concepto.length;i++){
						$scope.comprobante.conceptos.concepto[i].importe=$scope.comprobante.conceptos.concepto[i].importe.toFixed(2)*1;
					}
					
					// verificar si se agregaron impuestos locales
					if ($scope.mostrarImpLocales) {
						var send ={
							comentarios: $scope.comentarios,
							comprobante : $scope.comprobante,
							impuestosLocales : $scope.impuestosLocales,
						} 
					} 
					else {
						var send ={
							comentarios: $scope.comentarios,
							comprobante : $scope.comprobante,
						}
					}
					comprobanteService.guardarPrefactura(send).then(function(data){
						console.log(data)
						alert(data);
						$location.path("/listFacturas");
					})
				}else{
					alert("Elija un receptor");
				}
			}else{
				alert("No se eligió una serie para el comprobante")
			}
		}
			
		$scope.enviarComprobante=function(){
			if($scope.serialElegido.serie){
				$scope.comprobante.serie = $scope.serialElegido.serie;
				$scope.comprobante.folio = $scope.serialElegido.folio;
				$scope.mensajeFac="Procesando";
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
				
				// verificar si se agregaron impuestos locales
				if ($scope.mostrarImpLocales) {
					var send ={
						comentarios: $scope.comentarios,
						comprobante : $scope.comprobante,
						impuestosLocales : $scope.impuestosLocales,
						email : $scope.comprobante.email
							
					} 
				} else {
					var send ={
						comentarios: $scope.comentarios,
						comprobante : $scope.comprobante,
						email : $scope.comprobante.email
							
					}
				}
				$scope.procesando=true;
				$scope.finalizado=false;
				$scope.comprobante.total=   $scope.comprobante.total.toFixed(2)*1;
				$scope.comprobante.subTotal=   $scope.comprobante.subTotal.toFixed(2)*1;
				$scope.comprobante.impuestos.totalImpuestosTrasladados= $scope.comprobante.impuestos.totalImpuestosTrasladados.toFixed(2)*1;
				
				for(var i =0; i < $scope.comprobante.conceptos.concepto.length;i++){
					$scope.comprobante.conceptos.concepto[i].importe=$scope.comprobante.conceptos.concepto[i].importe.toFixed(2)*1;
				}
				$scope.bloqueado=true;
				comprobanteService.enviarComprobante(send).then(function(data){
					$scope.mensajeFac=data;
					$scope.finalizado=true;
					console.log(data)
					$scope.bloqueado=false;
//					$scope.listFacturas();
				})
			}else{
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
				if($scope.ivaIncluido) {
//					subtotal = subtotal +0.01;
					subtotal = subtotal.toFixed(2)*1;
				}
			}
			return subtotal;
		}
		
		$scope.actualizaTotal=function(){
			if ($scope.mostrarImpLocales) {
				$scope.comprobante.total= $scope.comprobante.subTotal-$scope.comprobante.descuento+
					$scope.comprobante.impuestos.totalImpuestosTrasladados-$scope.comprobante.impuestos.totalImpuestosRetenidos -
					$scope.impuestosLocales.totaldeRetenciones;
			} else {
				$scope.comprobante.total= $scope.comprobante.subTotal-$scope.comprobante.descuento+
				$scope.comprobante.impuestos.totalImpuestosTrasladados-$scope.comprobante.impuestos.totalImpuestosRetenidos;
			}
			$scope.comprobante.total = $scope.comprobante.total.toFixed(2)*1;
		}
		
		$scope.getTotal = function(subtotal, descuentos, impTras, impRet) {
			return subtotal - descuentos + impTras - impRet;
		}
		$scope.rfc= $routeParams.rfc;	
		$scope.cargarReceptores=function(){
			
			comprobanteService.getReceptores($scope.rfc).then(function(data){
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
		
		$scope.nuevaFactura=function(){
			$window.location.reload();
		}
		$scope.listaFacturas=function(){
			$('#mensaje').modal('hide');
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();
			$location.path("/listFacturas");			
		}
		
		$scope.OtroCorreo=true;
		$scope.otro = function(){
			$scope.Correo = true;
			$scope.OtroCorreo=false;
		}
		$scope.comprobante.emisor.regimenFiscal[0].regimen="General de Ley Personas Morales";
		$scope.comprobante.condicionesDePago="Contado";
		
		$scope.mostrarImpLocales = false;
		$scope.mostrarDivImpLocales = function() {
			
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
}]);