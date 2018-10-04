app.service("comprobanteService33", ['$http','$q',function($http,$q) {
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
			parte : null, // usar init y get
			claveProdServ: null,
			claveUnidad:null
			
		}
	}
	this.initTraslado= function(){
		return [];
		
	}
	this.getTraslado= function(){
		return{
			base:null,
			//Clave 002 - IVA
			impuesto:{valor:"002"},
			tipoFactor:{valor:"Tasa"},
			tasaOCuota:0.16,
			importe:null
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
			version : "3.3",
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
			version : "3.3",
			serie : null,
			folio : null,
			fecha : new Date(),
			sello : null,
			formaPago : {valor:"03"},
			noCertificado : null,
			certificado : null,
			condicionesDePago : null,
			subTotal : null,
			descuento : null,
			motivoDescuento : null,
			moneda : {
				valor:"MXN"
				},
			tipoCambio : null,
			total : null,
			tipoDeComprobante : {valor:"I"},
			metodoPago : {valor:"PUE"},
			lugarExpedicion : {valor:null},
			numCtaPago : null,
			folioFiscalOrig : null,
			serieFolioFiscalOrig : null,
			fechaFolioFiscalOrig : null,
			montoFolioFiscalOrig : null,
			cfdiRelacionados : null,

			emisor : {
				rfc : null, //hardcode para Web Service de pruebas
				nombre : null,
				regimenFiscal :  {
				    valor: "PERSONAS MORALES"
				} ,
				domicilioFiscal: null, // usar getDomicilio
				expedidoEn: null // usar getDomicilio
				
			},
			receptor : {
				rfc : null,
				nombre : null,
			    domicilio: null, // usar getDomicilio
			    usoCFDI:{valor:null}
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
	
	this.getCFDIParaPago = function() {
		return {
			version : "3.3",
			serie : null,
			folio : null,
			fecha : new Date(),
			sello : null,
			noCertificado : null,
			certificado : null,
			subTotal : 0,
			moneda : {
				valor:"XXX"
				},
			total : 0,
			tipoDeComprobante : {valor:"P"},
			lugarExpedicion : {valor:null},

			emisor : {
				rfc : null, //hardcode para Web Service de pruebas
				nombre : null,
				regimenFiscal :  {
				    valor: "601"
				} 
			},
			receptor : {
				rfc : null,
				nombre : null,
			    usoCFDI:{valor:"P01"}
			},
			conceptos : {
				concepto: [ 
					{
						claveProdServ : "84111506",
						cantidad : 1,
						claveUnidad : "ACT",
						descripcion : "Pago",
						valorUnitario : 0,
						importe : 0
					}
				]
			},
			complemento : null,
			addenda : null
		}
	}
	
	this.getComplementoPago = function(uuidRelacionado) {
		return  {
			pago : [
			   {
				   doctoRelacionado : [
                       {
                    	   idDocumento : uuidRelacionado,
                    	   serie : null,
                    	   folio : null,
                    	   monedaDR : {valor:"MXN"},
                    	   tipoCambioDR : null,
                    	   metodoDePagoDR : {valor:"PPD"},
                    	   numParcialidad : null,
                    	   impSaldoAnt : null,
                    	   impPagado : null,
                    	   impSaldoInsoluto : null
                       }
				   ],
				   fechaPago : new Date(),
				   formaDePagoP : null,
				   monedaP : null,
				   tipoCambioP : null,
				   monto : null,
				   numOperacion : null,
				   rfcEmisorCtaOrd : null,
				   nomBancoOrdExt : null,
				   ctaOrdenante : null,
				   rfcEmisorCtaBen : null,
				   ctaBeneficiario : null,
				   tipoCadPago : null,
				   certPago : null,
				   cadPago : null,
				   selloPago : null
			   }             
			],
			version : "1.0"
		}
	}
	
	this.actualizarPrefactura=function(comprobante,uuid){
		var d = $q.defer();
		$http.post("/factura33/actualizar/"+uuid,comprobante).then(
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
		$http.post("/factura33/generar/",comprobante).then(
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
		$http.post("/factura33/timbrar/",send).then(
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
		$http.post("/factura33/emailTo/",send).then(
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
	
	this.timbrar = function(uuid,email) {
		var d = $q.defer();
		$http.post("/factura33/timbrarPrefactura/",""+uuid+","+email).then(
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
		$http.get("/emisor/consultar33/"+rfc).then(
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
		$http.get("/factura33/editar/"+uuid).then(
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
	this.cancelarFactura33=function(uuid,rfc){
		var d = $q.defer();
		$http.post("/factura33/cancelarAck/",""+uuid+","+rfc).then(
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
		$http.get("/factura33/numPaginas/"+rfc).then(
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
	
	this.timbrarComplemento=function(send){
		var d = $q.defer();
		$http.post("/complementos/timbrar",send).then(
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
	
	this.timbrarComplementoM=function(send){
		var d = $q.defer();
		$http.post("/complementos/timbrarManual",send).then(
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

app.controller("comprobante33", [ '$scope', '$location', 'comprobanteService33','$routeParams','conceptosService','serialService','$window',
	function($scope, $location, comprobanteService33,$routeParams,conceptosService,serialService,$window) {
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
		$scope.comprobante= comprobanteService33.getCFDI32();
		//$scope.receptores = comprobanteService33.getDummyReceptores();
		
		$scope.addConcepto=function(){
			var concepto= $scope.concepto;
			var cantidad=$scope.conceptoAux.cantidad;
			var importe=$scope.conceptoAux.cantidad * concepto.valorUnitario;
			importe=importe.toFixed(2)*1;
			var descripcion = $scope.concepto.descripcion;
			var valorUnitario= $scope.concepto.valorUnitario;
			var unidad= $scope.concepto.unidad
			var noIdentificacion=$scope.concepto.noIdentificacion;
			var descuento= concepto.descuento;
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
			if(descuento){
				concepto.descuento=descuento*1;
			}
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
//			concepto.cantidad=$scope.conceptoAux.cantidad;
//			concepto.importe = $scope.conceptoAux.cantidad * concepto.valorUnitario;
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
					iva=total*0.16;
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
			$scope.concepto.cantidad=$scope.conceptoAux.cantidad;
			$scope.concepto.importe=0.0;
			if ($scope.concepto.cantidad) {
				if($scope.concepto.valorUnitario){
					$scope.concepto.importe=$scope.concepto.cantidad*$scope.concepto.valorUnitario;
				}
			}
		},true);
		
		//Inicializar el iva
		if ($scope.comprobante.impuestos.traslados == null) {
			$scope.comprobante.impuestos.traslados = comprobanteService33.initTraslados();
		}
		if(!$scope.comprobante.impuestos.traslados.traslado){
			$scope.comprobante.impuestos.traslados.traslado=[];
		}
		$scope.comprobante.impuestos.traslados.traslado.push({impuesto:{valor:"002"},tipoFactor:{valor:"Tasa"},tasaOCuota:0.16,importe:0});
		
		
		
		
		
		$scope.addTraslado=function(){
			var traslado= $scope.traslado;
			if ($scope.comprobante.impuestos.traslados == null) {
				$scope.comprobante.impuestos.traslados = comprobanteService33.initTraslados();
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
			if ($scope.comprobante.impuestos.retenciones == null) {
				$scope.comprobante.impuestos.retenciones = comprobanteService33.initRetenciones();
			}
			if(!$scope.comprobante.impuestos.retenciones.retencion){
				$scope.comprobante.impuestos.retenciones.retencion=[];
			}
			$scope.comprobante.impuestos.retenciones.retencion.push(retencion);
			$scope.retencion={};
		}
		
		$scope.quitarRetencion=function(index){
			$scope.comprobante.impuestos.retenciones.retencion.splice(index,1);
		}
		
		$scope.guardarPrefactura = function() {
			if($scope.serialElegido.serie){
				$scope.comprobante.serie = $scope.serialElegido.serie;
				$scope.comprobante.folio = $scope.serialElegido.folio;
				if($scope.comprobante.receptor){
					$scope.comprobante.fecha = new Date();
					if($scope.comentarios==null){
						$scope.comentarios=="";
					}
			
					$scope.comprobante.total=   $scope.comprobante.total.toFixed(2)*1;
					$scope.comprobante.subTotal=   $scope.comprobante.subTotal.toFixed(2)*1;
					$scope.comprobante.impuestos.totalImpuestosTrasladados= $scope.comprobante.impuestos.totalImpuestosTrasladados.toFixed(2)*1;
					
					for(var i =0; i < $scope.comprobante.conceptos.concepto.length;i++){
						$scope.comprobante.conceptos.concepto[i].importe=$scope.comprobante.conceptos.concepto[i].importe.toFixed(2)*1;
					}
					
					var send ={
							comentarios: $scope.comentarios,
							comprobante : $scope.comprobante
							
					}
					comprobanteService33.guardarPrefactura(send).then(function(data){
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
			if($scope.serialElegido){
			if($scope.serialElegido.serie){
				$scope.comprobante.serie = $scope.serialElegido.serie;
				$scope.comprobante.folio = $scope.serialElegido.folio;
				$scope.mensajeFac="Procesando";
				$scope.comprobante.fecha = new Date();
				if ($scope.comprobante.emisor.domicilioFiscal == null) {
					$scope.comprobante.emisor.domicilioFiscal = comprobanteService33.getDomicilio();
				}
				if ($scope.comprobante.receptor.domicilio == null) {
					$scope.comprobante.receptor.domicilio = comprobanteService33.getDomicilio();
				}
				$scope.comprobante.serie = $scope.serialElegido.serie;
				$scope.comprobante.folio = $scope.serialElegido.folio;
				if($scope.comentarios==null){
					$scope.comentarios=="";
				}
				if ($scope.aplicaCFDIRelacionado) {
					var cfdiRelacionadoAux = {
							uuid : $scope.uuidRelacionado
					}
					$scope.comprobante.cfdiRelacionados = {
							cfdiRelacionado : [],
							tipoRelacion : $scope.claveTipoRelacion
					}
					$scope.comprobante.cfdiRelacionados.cfdiRelacionado.push(cfdiRelacionadoAux);
				}
				var send ={
						comentarios: $scope.comentarios,
						comprobante : $scope.comprobante,
						email : $scope.comprobante.email
						
				} 
				$scope.procesando=true;
				$scope.finalizado=false;
				$scope.comprobante.total=   $scope.comprobante.total.toFixed(2)*1;
				$scope.comprobante.subTotal=   $scope.comprobante.subTotal.toFixed(2)*1;
				$scope.comprobante.impuestos.totalImpuestosTrasladados= $scope.comprobante.impuestos.totalImpuestosTrasladados.toFixed(2)*1;
				
				for(var i =0; i < $scope.comprobante.conceptos.concepto.length;i++){
					$scope.comprobante.conceptos.concepto[i].importe=$scope.comprobante.conceptos.concepto[i].importe.toFixed(2)*1;
				}
//				console.log($scope.comprobante);
				comprobanteService33.enviarComprobante(send).then(function(data){
					$scope.mensajeFac=data;
					$scope.finalizado=true;
					console.log(data)
//					$scope.listFacturas();
				})
				}
	}
			else{
				var modal=$('#myModal');
				modal.modal('hide');
				$('body').removeClass('modal-open');
				$('.modal-backdrop').remove();
				alert("No se eligió una serie para el comprobante");
				
			}
			}
			$scope.procesando=false;
			
		
		$scope.getSubtotal = function(arregloConcepto) {
			if (arregloConcepto != undefined && arregloConcepto != null) {
				$scope.totalDescuento=0;
				var tamArreglo = arregloConcepto.length;
				var i, subtotal = 0.0;
				for (i = 0; i < tamArreglo; i++) {
					subtotal += arregloConcepto[i].importe;
					$scope.totalDescuento+= arregloConcepto[i].descuento;
				}
				if($scope.ivaIncluido) {
//					subtotal = subtotal +0.01;
					subtotal = subtotal.toFixed(2)*1;
				}
			}
			return subtotal;
		}
		
		$scope.actualizaTotal=function(){
			$scope.comprobante.total= $scope.comprobante.subTotal-$scope.comprobante.descuento+$scope.comprobante.impuestos.totalImpuestosTrasladados-$scope.comprobante.impuestos.totalImpuestosRetenidos;
			
			$scope.comprobante.total = $scope.comprobante.total.toFixed(2)*1;
			if($scope.totalDescuento){
				$scope.comprobante.descuento= $scope.totalDescuento;
//				$scope.comprobante.total-= $scope.totalDescuento;
				$scope.comprobante.total = $scope.comprobante.total.toFixed(2)*1;
			}
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
				$scope.comprobante.lugarExpedicion.valor = data.lugarDeExpedicion;
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
		$scope.importeCambio=function(){
			$scope.concepto.importe= $scope.conceptoAux.cantidad * $scope.concepto.valorUnitario;
			
		}
		
		$scope.usoFDIhide=function(){
			var str = $scope.comprobante.receptor.rfc;
		    var res = str.substring(0, 4);
			if(!res.search("[A-Z]{4}"))	{
				$(".nomoral").show();
				
			}else {
				
				$(".nomoral").hide();
			}
		}
		
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
//		$scope.otro = function(){
//			$scope.Correo = true;
//			$scope.OtroCorreo=false;
//		}

//		   
		 $('#otroCorreo').click(function () {
		        if (!this.checked) {
		        	$scope.Correo = false;
					$scope.OtroCorreo=true;
					
		        }else{ 
		        	
					$scope.Correo = true;
					$scope.OtroCorreo=false;
		        }
		    });
	
		$scope.comprobante.emisor.regimenFiscal.valor="601";
//		$scope.comprobante.receptor.usoCFDI.valor="P01";
		$scope.comprobante.condicionesDePago="Contado";
		
		
		
		
}]);

