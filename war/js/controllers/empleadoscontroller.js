app.service('empleadosService', [
	'$http',
	'$q',
	'$cookieStore',
	'$location',
	'$rootScope',
	function($http, $q, $cookieStore,$location,$rootScope) {
		if(!$rootScope.variable){
			$location.path("/login");
		}else{
		this.add = function(newEmp) {
		};
		this.updaLocalStorage = function() {/* Actualiza Storage */
		};
		this.clean = function() {/* Limpia o Elimina todos los elementos */
		};
		this.getAll = function(id) {/* Muestra todos los Elementos */
		};
		this.removeItem = function(item) {/* Elimina elemento por elemeto */
		}

		this.calcularPagos = function(send) {
			var d = $q.defer();
			$http.get("/pagos/calcularPagos/" + send.idEsquema + "/"+ send.idRfcEmpresa).then(
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

		this.catalogosPercepciones = function(percepciones) {
			var d = $q.defer();
			$http.get("/percepciones/findAll/").then(function(response) {
				console.log(response);
				d.resolve(response.data);
			}, function(response) {
				if(response.status==403){
					alert("No está autorizado para realizar esta acción");
					$location.path("/");
				}
				d.reject(response);
			});
			return d.promise;
		}

		this.catalogosPercepcionesAgregar = function(per) {
			var d = $q.defer();
			$http.post("/percepciones/add/", per).then(function(response) {
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

		this.updatePer = function(variable) {
			var d = $q.defer();
			$http.post("/percepciones/update/", variable).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				}, function(response) {
					if(response.status==403){
						alert("No está autorizado para realizar esta acción");
						$location.path("/");
					}
					d.reject(response);
				});
			return d.promise;
		}

		this.catalogosDeducciones = function(percepciones) {
			var d = $q.defer();
			$http.get("/deducciones/findAll/").then(function(response) {
				console.log(response);
				d.resolve(response.data);
			}, function(response) {
				if(response.status==403){
					alert("No está autorizado para realizar esta acción");
					$location.path("/");
				}
				d.reject(response);
			});
			return d.promise;
		}
		
		this.catalogosDeduccionesAgregar = function(ded) {
			var d = $q.defer();
			$http.post("/deducciones/add/", ded).then(function(response) {
				console.log(response);
				d.resolve(response.data);
			}, function(response) {
				if(response.status==403){
					alert("No está autorizado para realizar esta acción");
					$location.path("/");
				}
				d.reject(response);
			});
			return d.promise;
		}
		
		this.updateDed = function(variable) {
			var d = $q.defer();
			$http.post("/deducciones/update/", variable).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				
				}, function(response) {
					if(response.status==403){
						alert("No está autorizado para realizar esta acción");
						$location.path("/");
					}
					d.reject(response);
				});
			return d.promise;
		}
		
		this.catalogosContabilidadPercepciones = function(
				rfc) { //percepcionesContabilidad en lugar de rfc
				var d = $q.defer();
				$http.get("/cPropiaPercepciones/findAll/"+rfc).then(
					function(response) {
						console.log(response);
						d.resolve(response.data);
					}, function(response) {
						if(response.status==403){
							alert("No está autorizado para realizar esta acción");
							$location.path("/");
						}
						d.reject(response);
					});
				return d.promise;
			}
		
		this.catalogosPercepcionesAgregarCont = function(send) {
			var d = $q.defer();
			$http.post("/cPropiaPercepciones/add/", send).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				}, function(response) {
					if(response.status==403){
						alert("No está autorizado para realizar esta acción");
						$location.path("/");
					}
					d.reject(response);
				});
			return d.promise;
		}
		
		this.updatePerCont = function(send) {
			var d = $q.defer();
			$http.post("/cPropiaPercepciones/update/", send).then(
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
		
		this.catalogosContabilidadDeducciones = function(
				rfc) {//deduccionesContabilidad
				var d = $q.defer();
				$http.get("/cPropiaDeducciones/findAll/"+rfc).then(
					function(response) {
						console.log(response);
						d.resolve(response.data);
					}, function(response) {
						if(response.status==403){
							alert("No está autorizado para realizar esta acción");
							$location.path("/");
						}
						d.reject(response);
					});
				return d.promise;
			}
			
		this.catalogosDeduccionesAgregarCont = function(send) {
			var d = $q.defer();
			$http.post("/cPropiaDeducciones/add/", send).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				}, function(response) {
					if(response.status==403){
						alert("No está autorizado para realizar esta acción");
						$location.path("/");
					}
					d.reject(response);
				});
			return d.promise;
		}

		this.updateDedCont = function(send) {
			var d = $q.defer();
			$http.post("/cPropiaDeducciones/update/", send).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				}, function(response) {
					if(response.status==403){
						alert("No está autorizado para realizar esta acción");
						$location.path("/");
					}
					d.reject(response);
				});
			return d.promise;
		}
		this.calcularPagoRegimen = function(send) {
			var d = $q.defer();
			$http.get("/pagos/calcularPagosPorRegimen/"+ send).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				}, function(response) {
					if(response.status==403){
						alert("No está autorizado para realizar esta acción");
						$location.path("/");
					}
					d.reject(response);
				});
			return d.promise;
		}
		
		this.calcularInfonavit = function(send) {
			var d = $q.defer();
			$http.post("/pagos/calcularAportacionesInfonavit/"+ send.rfcEmpresa,send).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				}, function(response) {
					if(response.status==403){
						alert("No está autorizado para realizar esta acción");
						$location.path("/");
					}
					d.reject(response);
				});
			return d.promise;
		}
		

		this.recalcularPago = function(pagos) {
			var d = $q.defer();
			var enviar = {
				lista : pagos
			}
			$http.post("/pagos/recalcularPago/", enviar).then(
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

		this.guardarPago = function(pagos) {
			var d = $q.defer();
			var enviar = {
				lista : pagos
			}
			$http.post("/pagos/guardarPago/", enviar).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				}, function(response) {
					if(response.status==403){
						alert("No está autorizado para realizar esta acción");
						$location.path("/");
					}
					d.reject(response);
				});
			return d.promise;
		}

		this.findByEmpresa = function(id) {
			var d = $q.defer();
			$http.get("/employee/getByEmpresa/" + id).then(
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

		this.findByRegimen = function(send) {
			var d = $q.defer();
			$http.get("/employee/getByRegimen/" + send).then(
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

		this.getById = function(id) {
			var d = $q.defer();
			$http.get("/employee/find/" + id).then(function(response) {
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

		this.update = function(empleado) {
			var d = $q.defer();
			$http.post("/employee/update/", empleado).then(
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

		this.elimina = function(id) {
			var d = $q.defer();
			$http.post("/employee/delete/" + id).then(function(response) {
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

	}}]);
app.controller("empleadosController", [
	'$scope',
	'$http',
	'empleadosService',
	'$location',
	'$routeParams',
	'$rootScope',
	function($scope, $http, empleadosService, $location, $routeParams,
		$rootScope) {
		if(!$rootScope.variable){
			$location.path("/login");
		}else{
		$rootScope.nombreEmpresa;
		$scope.byEmpresa = false;
		$scope.addEmp = function() {
			var send = {
				empleado : $scope.newEmp,
				idEmpresa : $routeParams.id
			}
			$http.post("/employee/add", send).then(function(response) {
				alert("Empleado Guardado Satisfactoriamente");
					$location.path("/empleados/esquemas/" + $rootScope.id);// Flow
					console.log(response.data);
				}, function(response) {
					console.log(response);
				});
		}
		$scope.regresaEmpleados = function() {
			$location.path("/empleados/esquemas/" + $routeParams.id);
		}
		$scope.clean = function() {
				// alert("no implementado aun");
				// document.getElementById("forma").reset();
			}
		
		$scope.catalogoBancos=[
			{
				"clave": "002",
				"nombreCorto": "BANAMEX",
				"razonSocial": "Banco Nacional de México, S.A., Institución de Banca Múltiple, Grupo Financiero Banamex" 
				
			},
			{
				"clave": "006",
				"nombreCorto": "BANCOMEXT",
				"razonSocial": "Banco Nacional de Comercio Exterior, Sociedad Nacional de Crédito, Institución de Banca de Desarrollo" 
				
			},{
				"clave": "009",
				"nombreCorto": "BANOBRAS",
				"razonSocial": "Banco Nacional de Obras y Servicios Públicos, Sociedad Nacional de Credito, Institución de Banca de Desarrollo" 
				
			},{
				"clave": "012",
				"nombreCorto": "BBVA BANCOMER",
				"razonSocial": "BBVA Bancomer, S.A., Institución de Banca Múltiple, Grupo Financiero BBVA Bancomer" 
				
			},{
				"clave": "014",
				"nombreCorto": "SANTANDER",
				"razonSocial": "Banco Santander (México), S.A., Institución de Banca Múltiple, Grupo Financiero Santander" 
				
			},{
				"clave": "019",
				"nombreCorto": "BANJERCITO",
				"razonSocial": "Banco Nacional del Ejército, Fuerza Aérea y Armada, Sociedad Nacional de Crédito, Institución de Banca de Desarrollo" 
				
			},{
				"clave": "021",
				"nombreCorto": "HSBC",
				"razonSocial": "HSBC México, S.A., institución de Banca Múltiple, Grupo Financiero HSBC" 
				
			},{
				"clave": "030",
				"nombreCorto": "BAJIO",
				"razonSocial": "Banco del Bajío, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "032",
				"nombreCorto": "IXE",
				"razonSocial": "IXE Banco, S.A., Institución de Banca Múltiple, IXE Grupo Financiero" 
				
			},{
				"clave": "036",
				"nombreCorto": "INBURSA",
				"razonSocial": "Banco Inbursa S.A., Institución de Banca Múltiple, Grupo Financiero Inbursa" 
				
			},{
				"clave": "037",
				"nombreCorto": "INTERACCIONES",
				"razonSocial": "Banco Interacciones, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "042",
				"nombreCorto": "MIFEL",
				"razonSocial": "Banca Mifel, S.A., Institución de Banca Múltiple, Grupo Financiero Mifel" 
				
			},{
				"clave": "044",
				"nombreCorto": "SCOTIABANK",
				"razonSocial": "Scotiabank Inverlat, S.A." 
				
			},{
				"clave": "058",
				"nombreCorto": "BANREGIO",
				"razonSocial": "Banco Regional de Monterrey, S.A., Institución de Banca Múltiple, Banregio Grupo Financiero" 
				
			},{
				"clave": "059",
				"nombreCorto": "INVEX",
				"razonSocial": "Banco Invex, S.A., Institución de Banca Múltiple, Invex Grupo Financiero" 
				
			},{
				"clave": "060",
				"nombreCorto": "BANSI",
				"razonSocial": "Bansi, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "062",
				"nombreCorto": "AFIRME",
				"razonSocial": "Banca Afirme, S.A., Institución de Banca Multiple" 
				
			},{
				"clave": "072",
				"nombreCorto": "BANORTE",
				"razonSocial": "Banco Mercantil del Norte, S.A., Institución de Banca Múltiple, Grupo Financiero" 
				
			},{
				"clave": "102",
				"nombreCorto": "THE ROYAL BANK",
				"razonSocial": "The Royal Bank of Scotland México, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "103",
				"nombreCorto": "AMERICAN EXPRESS",
				"razonSocial": "American Express Bank (México), S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "106",
				"nombreCorto": "BAMSA",
				"razonSocial": "Bank of America México, S.A., Institución de Banca Múltiple, Grupo Financiero Bank of America" 
				
			},{
				"clave": "108",
				"nombreCorto": "TOKYO",
				"razonSocial": "Bank of Tokyo-Mitsubishi UFJ (México), S.A." 
				
			},{
				"clave": "110",
				"nombreCorto": "JP MORGAN",
				"razonSocial": "Banco J.P Morgan, S.A., Institución de Banca Multiple, J.P Morgan Grupo Financiero" 
				
			},{
				"clave": "112",
				"nombreCorto": "BMONEX",
				"razonSocial": "Banco Monex, S.A., Institución de Banca Multiple" 
				
			},{
				"clave": "113",
				"nombreCorto": "VE POR MAS",
				"razonSocial": "Banco Ve Por Mas, S.A., Institución de Banca Multiple" 
				
			},{
				"clave": "116",
				"nombreCorto": "ING",
				"razonSocial": "ING Bank (México, S.A., Institución de Banca Multiple ING Grupo Financiero)" 
				
			},{
				"clave": "124",
				"nombreCorto": "DEUTSCHE",
				"razonSocial": "Deutsche Bank México, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "126",
				"nombreCorto": "CREDIT SUISSE",
				"razonSocial": "Banco Credit Suisse (México), S.A., Institución de Banca Múltiple, Grupo Financiero Credit Suidde (México)" 
				
			},{
				"clave": "127",
				"nombreCorto": "AZTECA",
				"razonSocial": "Banco Azteca, S.A. Institución de Banca Múltiple." 
				
			},{
				"clave": "128",
				"nombreCorto": "AUTOFIN",
				"razonSocial": "Banco Autofin México, S.A. Institución de Banca Múltiple" 
				
			},{
				"clave": "129",
				"nombreCorto": "BARCLAYS",
				"razonSocial": "Barclays Bank México, S.A., Institución de Banca Multiple, Grupo Financiero Barclays México" 
				
			},{
				"clave": "130",
				"nombreCorto": "COMPARTAMOS",
				"razonSocial": "Banco Compartamos S.A., Insitución de Banca Multiple" 
				
			},{
				"clave": "131",
				"nombreCorto": "BANCO FAMSA",
				"razonSocial": "Banco Ahorro Famsa, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "132",
				"nombreCorto": "BMULTIVA",
				"razonSocial": "Banco Multiva, S.A., Institución de Banca Múltiple, Multivalores Grupo Financiero" 
				
			},{
				"clave": "133",
				"nombreCorto": "ACTINVER",
				"razonSocial": "Banco Actinver, S.A., Institución de Banca Múltiple, Grupo Financiero Actinver" 
				
			},{
				"clave": "134",
				"nombreCorto": "WAL-MART",
				"razonSocial": "Banco Wal-Mart de México Adelante, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "135",
				"nombreCorto": "NAFIN",
				"razonSocial": "Nacional Financiera, Sociedad Nacional de Crédito, Institución de Banca de Desarrollo" 
				
			},{
				"clave": "136",
				"nombreCorto": "INTERBANCO",
				"razonSocial": "Inter Banco, S.A. Institución de Banca Múltiple" 
				
			},{
				"clave": "137",
				"nombreCorto": "BANCOPPEL",
				"razonSocial": "BanCoppel, S,A. Institución de Banca Múltiple" 
				
			},{
				"clave": "138",
				"nombreCorto": "ABC CAPITAL",
				"razonSocial": "ABC Capital, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "139",
				"nombreCorto": "UBS BANK",
				"razonSocial": "UBS Bank México, S.A., Institución de Banca Múltiple, UBS Grupo Financiero" 
				
			},{
				"clave": "140",
				"nombreCorto": "CONSUBANCO",
				"razonSocial": "Consubanco, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "141",
				"nombreCorto": "VOLKSWAGEN",
				"razonSocial": "Volkswagen Bank, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "143",
				"nombreCorto": "CIBANCO",
				"razonSocial": "CIBanco, S.A." 
				
			},{
				"clave": "145",
				"nombreCorto": "BBASE",
				"razonSocial": "Banco Base, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "166",
				"nombreCorto": "BANSEFI",
				"razonSocial": "Banco del Ahorro Nacional y Servicios Financieros, Sociedad Nacional de Crédito, Institución de Banca de Desarrollo" 
				
			},{
				"clave": "168",
				"nombreCorto": "HIPOTECARIA FEDERAL",
				"razonSocial": "Sociedad Hipotecaria Federal, Sociedad Nacional de Crédito, Institución de Banca de Desarrollo" 
				
			},{
				"clave": "600",
				"nombreCorto": "MONEXCB",
				"razonSocial": "Monex Casa de Bolsa, S.A. de C.V. Monex Grupo Financiero" 
				
			},{
				"clave": "601",
				"nombreCorto": "GBM",
				"razonSocial": "GBM Grupo Bursátil Mexicano, S.A. de C.V. Casa de Bolsa" 
				
			},{
				"clave": "602",
				"nombreCorto": "MASARI",
				"razonSocial": "Masari Casa de Bolsa, S.A." 
				
			},{
				"clave": "605",
				"nombreCorto": "VALUE",
				"razonSocial": "Value, S.A. de C.V. Casa de Bolsa" 
				
			},{
				"clave": "606",
				"nombreCorto": "ESTRUCTURADORES",
				"razonSocial": "Estructuradores del Mercado de Valores Casa dew Bolsa, S.A. de C.V." 
				
			},{
				"clave": "607",
				"nombreCorto": "TIBER",
				"razonSocial": "Casa de Cambio Tiber, S.A. de C.V." 
				
			},{
				"clave": "608",
				"nombreCorto": "VECTOR",
				"razonSocial": "Vector Casa de Bolsa S.A. de C.V." 
				
			},{
				"clave": "610",
				"nombreCorto": "B&B",
				"razonSocial": "B y B, Casa de Cambio, S.A. de C.V." 
				
			},{
				"clave": "614",
				"nombreCorto": "ACCIVAL",
				"razonSocial": "Acciones y Valores Banamex, S.A. de C.V., Casa de Bolsa" 
				
			},{
				"clave": "615",
				"nombreCorto": "MERRILL LYNCH",
				"razonSocial": "Merrill Lynch Casa de Bolsa, S.A. de C.V., Casa de Bolsa" 
				
			},{
				"clave": "616",
				"nombreCorto": "FINAMEX",
				"razonSocial": "Casa de Bolsa Finamex S.A. de C.V" 
				
			},{
				"clave": "617",
				"nombreCorto": "VALMEX",
				"razonSocial": "Valores Mexicanos Casa de Bolsa, S.A. de C.V." 
				
			},{
				"clave": "618",
				"nombreCorto": "UNICA",
				"razonSocial": "Unica Casa de Cambio, S.A. de C.V." 
				
			},{
				"clave": "619",
				"nombreCorto": "MAPFRE",
				"razonSocial": "MAPFRE Tepeyac, S.A." 
				
			},{
				"clave": "620",
				"nombreCorto": "PROFUTURO",
				"razonSocial": "Profuturo G.N.P., S.A de C.V., Afore" 
				
			},{
				"clave": "621",
				"nombreCorto": "CB ACTINVER",
				"razonSocial": "Actinver Casa de Bolsa, S.A. de C.V." 
				
			},{
				"clave": "622",
				"nombreCorto": "OACTIN",
				"razonSocial": "OPERADORA ACTINVER, S.A. de C.V." 
				
			},{
				"clave": "623",
				"nombreCorto": "SKANDIA",
				"razonSocial": "Skandia Vida, S.A. de C.V." 
				
			},{
				"clave": "626",
				"nombreCorto": "CBDEUTSCHE",
				"razonSocial": "Deustche Securities, S.A. de C.V. CASA DE BOLSA" 
				
			},{
				"clave": "627",
				"nombreCorto": "ZURICH",
				"razonSocial": "Zurich Compañia de Seguros, S.A." 
				
			},{
				"clave": "628",
				"nombreCorto": "ZURICHVI",
				"razonSocial": "Zurich Vida, Compañía de Seguros, S.A." 
				
			},{
				"clave": "629",
				"nombreCorto": "SU CASITA",
				"razonSocial": "Hipotecaria Su Casita, S.A. de C.V. SOFOM ENR" 
				
			},{
				"clave": "630",
				"nombreCorto": "CB INTERCAM",
				"razonSocial": "Intercam Casa de Bolsa, S.A. de C.V." 
				
			},{
				"clave": "631",
				"nombreCorto": "CI BOLSA",
				"razonSocial": "Casa de Bolsa, S.A de C.V." 
				
			},{
				"clave": "632",
				"nombreCorto": "BULLTICK CB",
				"razonSocial": "Bulltick Casa de Bolsa, S.A., de C.V." 
				
			},{
				"clave": "633",
				"nombreCorto": "STERLING",
				"razonSocial": "Sterling Casa de Cambio, S.A. de C.V." 
				
			},{
				"clave": "634",
				"nombreCorto": "FINCOMUN",
				"razonSocial": "Fincomún, Servicios Financieros Comunitarios, S.A. de C.V." 
				
			},{
				"clave": "636",
				"nombreCorto": "HDI SEGUROS",
				"razonSocial": "HDI Seguros, S.A. de C.V." 
				
			},{
				"clave": "637",
				"nombreCorto": "ORDER",
				"razonSocial": "Order Express Casa de Cambio, S.A. de C.V." 
				
			},{
				"clave": "638",
				"nombreCorto": "AKALA",
				"razonSocial": "Akala, S.A. de C.V, Sociedad Financiera Popular" 
				
			},{
				"clave": "640",
				"nombreCorto": "CB JPMORGAN",
				"razonSocial": "J.P. Morgan Casa de Bolsa, S.A. de C.V. J.P. Morgan Grupo Financiero" 
				
			},{
				"clave": "642",
				"nombreCorto": "REFORMA",
				"razonSocial": "Operadora de Recursos Reforma, S.A. de C.V., S.F.P." 
				
			},{
				"clave": "646",
				"nombreCorto": "STP",
				"razonSocial": "Sistema de Transferancias y Pagos STP, S.A. de C.V.SOFOM ENR" 
				
			},{
				"clave": "647",
				"nombreCorto": "TELECOMM",
				"razonSocial": "Telecomunicaciones de México" 
				
			},{
				"clave": "648",
				"nombreCorto": "EVERCORE",
				"razonSocial": "Evercore Casa de Bolsa, S.A. de C.V." 
				
			},{
				"clave": "649",
				"nombreCorto": "SKANDIA",
				"razonSocial": "Skandia Operadora de Fondos, S.A. de C.V." 
				
			},{
				"clave": "651",
				"nombreCorto": "SEGMTY",
				"razonSocial": "Seguros Monterrey New York Life, S.A. de C.V." 
				
			},{
				"clave": "652",
				"nombreCorto": "ASEA",
				"razonSocial": "Solución Asea, S.A. de C.V., Sociedad Financiera Popular" 
				
			},{
				"clave": "653",
				"nombreCorto": "KUSPIT",
				"razonSocial": "Kuspit Casa de Bolsa, S.A. de C.V." 
				
			},{
				"clave": "655",
				"nombreCorto": "SOFIEXPRESS",
				"razonSocial": "J.P. SOFIEXPRESS, S.A. de C.V., S.F.P." 
				
			},{
				"clave": "656",
				"nombreCorto": "UNAGRA",
				"razonSocial": "UNAGRA, S.A. de C.V., S.F.P" 
				
			},{
				"clave": "659",
				"nombreCorto": "OPCIONES EMPRESARIALES DEL NORESTE",
				"razonSocial": "OPCIONES EMPRESARIALES DEL NORESTE, S.A. DE C.V., S.F.P." 
				
			},{
				"clave": "901",
				"nombreCorto": "CLS",
				"razonSocial": "CLs Bank International" 
				
			},{
				"clave": "902",
				"nombreCorto": "INDEVAL",
				"razonSocial": "SD. Indeval, S.A. de C.V." 
				
			},{
				"clave": "670",
				"nombreCorto": "LIBERTAD",
				"razonSocial": "Libertad Servicios Financieros, S.A. De C.V." 
				
			},
		]
		$scope.estadoCivil=[
			{
				"estado": "Soltero/a", 											
			},{
				"estado": "Comprometido/a", 											
			},{
				"estado": "Casado/a", 											
			},{
				"estado": "Divorciado/a", 											
			},{
				"estado": "Viudo/a", 											
			},
			]
		}}]);
app.controller("empleadosDetailsController", [
	'$scope',
	'$http',
	'$location',
	'$routeParams',
	'empleadosService',
	'$rootScope',
	'$cookies',
	'$cookieStore',
	function($scope, $http, $location, $routeParams, empleadosService,
		$rootScope, $cookies, $cookieStore) {
		if(!$rootScope.variable){
			$location.path("/login");
		}else{
		$rootScope.nombreEmpresa;
		$scope.Mostrar = true;
		empleadosService.getById($routeParams.id).then(function(data) {
			$scope.empleados = data;
			console.log(data);
			var d = new Date($scope.empleados.fechaDeContratacion);
			var year = (d.getUTCFullYear());
			var month = ("0" + (d.getUTCMonth() + 1)).slice(-2);
			var day = (d.getUTCDate());
			var newdate = day + "/" + month + "/" + year;
			var fecha = $scope.empleados.fechaDeContratacion = newdate;
			$scope.empleados.fechaDeContratacion = d;
			console.log(fecha);
			console.log(data);
		});

		$scope.update = function() {
			console.log($scope.empleados);
			var upd = {
				empleado : $scope.empleados
			}
			empleadosService.update(upd).then(
				function(data) {
					$scope.empleados = data;
					alert("Empleado modificado correctamente");
					$location.path("/empleados/details/"
						+ $scope.empleados.numEmpleado);
				});
		}

		$scope.regresaListaEmpId = function() {
			$location.path("/empleados/esquemas/"
				+ $cookieStore.get('idEmpresa'));
		}
		$scope.clean = function() {
			document.getElementById("forma").reset();
		}

	}}]);
app.controller("empleadosDetailsRfcController", [
	'$scope',
	'$http',
	'$location',
	'$routeParams',
	'empleadosService',
	'$rootScope',
	'$cookies',
	'$cookieStore',
	function($scope, $http, $location, $routeParams, empleadosService,
		$rootScope, $cookies, $cookieStore) {
		if(!$rootScope.variable){
			$location.path("/login");
		}else{
		$rootScope.nombreEmpresa;
		$scope.Mostrar = false;
		empleadosService.getById($routeParams.id).then(function(data) {
			$scope.empleados = data;
			console.log(data);
			var d = new Date($scope.empleados.fechaDeContratacion);
			var year = (d.getUTCFullYear());
			var month = ("0" + (d.getUTCMonth() + 1)).slice(-2);
			var day = (d.getUTCDate());
			var newdate = day + "/" + month + "/" + year;
			var fecha = $scope.empleados.fechaDeContratacion = newdate;
			$scope.empleados.fechaDeContratacion = d;
			console.log(fecha);
			console.log(data);
		});
		$scope.catalogoBancos=[
			{
				"clave": "002",
				"nombreCorto": "BANAMEX",
				"razonSocial": "Banco Nacional de México, S.A., Institución de Banca Múltiple, Grupo Financiero Banamex" 
				
			},
			{
				"clave": "006",
				"nombreCorto": "BANCOMEXT",
				"razonSocial": "Banco Nacional de Comercio Exterior, Sociedad Nacional de Crédito, Institución de Banca de Desarrollo" 
				
			},{
				"clave": "009",
				"nombreCorto": "BANOBRAS",
				"razonSocial": "Banco Nacional de Obras y Servicios Públicos, Sociedad Nacional de Credito, Institución de Banca de Desarrollo" 
				
			},{
				"clave": "012",
				"nombreCorto": "BBVA BANCOMER",
				"razonSocial": "BBVA Bancomer, S.A., Institución de Banca Múltiple, Grupo Financiero BBVA Bancomer" 
				
			},{
				"clave": "014",
				"nombreCorto": "SANTANDER",
				"razonSocial": "Banco Santander (México), S.A., Institución de Banca Múltiple, Grupo Financiero Santander" 
				
			},{
				"clave": "019",
				"nombreCorto": "BANJERCITO",
				"razonSocial": "Banco Nacional del Ejército, Fuerza Aérea y Armada, Sociedad Nacional de Crédito, Institución de Banca de Desarrollo" 
				
			},{
				"clave": "021",
				"nombreCorto": "HSBC",
				"razonSocial": "HSBC México, S.A., institución de Banca Múltiple, Grupo Financiero HSBC" 
				
			},{
				"clave": "030",
				"nombreCorto": "BAJIO",
				"razonSocial": "Banco del Bajío, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "032",
				"nombreCorto": "IXE",
				"razonSocial": "IXE Banco, S.A., Institución de Banca Múltiple, IXE Grupo Financiero" 
				
			},{
				"clave": "036",
				"nombreCorto": "INBURSA",
				"razonSocial": "Banco Inbursa S.A., Institución de Banca Múltiple, Grupo Financiero Inbursa" 
				
			},{
				"clave": "037",
				"nombreCorto": "INTERACCIONES",
				"razonSocial": "Banco Interacciones, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "042",
				"nombreCorto": "MIFEL",
				"razonSocial": "Banca Mifel, S.A., Institución de Banca Múltiple, Grupo Financiero Mifel" 
				
			},{
				"clave": "044",
				"nombreCorto": "SCOTIABANK",
				"razonSocial": "Scotiabank Inverlat, S.A." 
				
			},{
				"clave": "058",
				"nombreCorto": "BANREGIO",
				"razonSocial": "Banco Regional de Monterrey, S.A., Institución de Banca Múltiple, Banregio Grupo Financiero" 
				
			},{
				"clave": "059",
				"nombreCorto": "INVEX",
				"razonSocial": "Banco Invex, S.A., Institución de Banca Múltiple, Invex Grupo Financiero" 
				
			},{
				"clave": "060",
				"nombreCorto": "BANSI",
				"razonSocial": "Bansi, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "062",
				"nombreCorto": "AFIRME",
				"razonSocial": "Banca Afirme, S.A., Institución de Banca Multiple" 
				
			},{
				"clave": "072",
				"nombreCorto": "BANORTE",
				"razonSocial": "Banco Mercantil del Norte, S.A., Institución de Banca Múltiple, Grupo Financiero" 
				
			},{
				"clave": "102",
				"nombreCorto": "THE ROYAL BANK",
				"razonSocial": "The Royal Bank of Scotland México, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "103",
				"nombreCorto": "AMERICAN EXPRESS",
				"razonSocial": "American Express Bank (México), S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "106",
				"nombreCorto": "BAMSA",
				"razonSocial": "Bank of America México, S.A., Institución de Banca Múltiple, Grupo Financiero Bank of America" 
				
			},{
				"clave": "108",
				"nombreCorto": "TOKYO",
				"razonSocial": "Bank of Tokyo-Mitsubishi UFJ (México), S.A." 
				
			},{
				"clave": "110",
				"nombreCorto": "JP MORGAN",
				"razonSocial": "Banco J.P Morgan, S.A., Institución de Banca Multiple, J.P Morgan Grupo Financiero" 
				
			},{
				"clave": "112",
				"nombreCorto": "BMONEX",
				"razonSocial": "Banco Monex, S.A., Institución de Banca Multiple" 
				
			},{
				"clave": "113",
				"nombreCorto": "VE POR MAS",
				"razonSocial": "Banco Ve Por Mas, S.A., Institución de Banca Multiple" 
				
			},{
				"clave": "116",
				"nombreCorto": "ING",
				"razonSocial": "ING Bank (México, S.A., Institución de Banca Multiple ING Grupo Financiero)" 
				
			},{
				"clave": "124",
				"nombreCorto": "DEUTSCHE",
				"razonSocial": "Deutsche Bank México, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "126",
				"nombreCorto": "CREDIT SUISSE",
				"razonSocial": "Banco Credit Suisse (México), S.A., Institución de Banca Múltiple, Grupo Financiero Credit Suidde (México)" 
				
			},{
				"clave": "127",
				"nombreCorto": "AZTECA",
				"razonSocial": "Banco Azteca, S.A. Institución de Banca Múltiple." 
				
			},{
				"clave": "128",
				"nombreCorto": "AUTOFIN",
				"razonSocial": "Banco Autofin México, S.A. Institución de Banca Múltiple" 
				
			},{
				"clave": "129",
				"nombreCorto": "BARCLAYS",
				"razonSocial": "Barclays Bank México, S.A., Institución de Banca Multiple, Grupo Financiero Barclays México" 
				
			},{
				"clave": "130",
				"nombreCorto": "COMPARTAMOS",
				"razonSocial": "Banco Compartamos S.A., Insitución de Banca Multiple" 
				
			},{
				"clave": "131",
				"nombreCorto": "BANCO FAMSA",
				"razonSocial": "Banco Ahorro Famsa, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "132",
				"nombreCorto": "BMULTIVA",
				"razonSocial": "Banco Multiva, S.A., Institución de Banca Múltiple, Multivalores Grupo Financiero" 
				
			},{
				"clave": "133",
				"nombreCorto": "ACTINVER",
				"razonSocial": "Banco Actinver, S.A., Institución de Banca Múltiple, Grupo Financiero Actinver" 
				
			},{
				"clave": "134",
				"nombreCorto": "WAL-MART",
				"razonSocial": "Banco Wal-Mart de México Adelante, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "135",
				"nombreCorto": "NAFIN",
				"razonSocial": "Nacional Financiera, Sociedad Nacional de Crédito, Institución de Banca de Desarrollo" 
				
			},{
				"clave": "136",
				"nombreCorto": "INTERBANCO",
				"razonSocial": "Inter Banco, S.A. Institución de Banca Múltiple" 
				
			},{
				"clave": "137",
				"nombreCorto": "BANCOPPEL",
				"razonSocial": "BanCoppel, S,A. Institución de Banca Múltiple" 
				
			},{
				"clave": "138",
				"nombreCorto": "ABC CAPITAL",
				"razonSocial": "ABC Capital, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "139",
				"nombreCorto": "UBS BANK",
				"razonSocial": "UBS Bank México, S.A., Institución de Banca Múltiple, UBS Grupo Financiero" 
				
			},{
				"clave": "140",
				"nombreCorto": "CONSUBANCO",
				"razonSocial": "Consubanco, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "141",
				"nombreCorto": "VOLKSWAGEN",
				"razonSocial": "Volkswagen Bank, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "143",
				"nombreCorto": "CIBANCO",
				"razonSocial": "CIBanco, S.A." 
				
			},{
				"clave": "145",
				"nombreCorto": "BBASE",
				"razonSocial": "Banco Base, S.A., Institución de Banca Múltiple" 
				
			},{
				"clave": "166",
				"nombreCorto": "BANSEFI",
				"razonSocial": "Banco del Ahorro Nacional y Servicios Financieros, Sociedad Nacional de Crédito, Institución de Banca de Desarrollo" 
				
			},{
				"clave": "168",
				"nombreCorto": "HIPOTECARIA FEDERAL",
				"razonSocial": "Sociedad Hipotecaria Federal, Sociedad Nacional de Crédito, Institución de Banca de Desarrollo" 
				
			},{
				"clave": "600",
				"nombreCorto": "MONEXCB",
				"razonSocial": "Monex Casa de Bolsa, S.A. de C.V. Monex Grupo Financiero" 
				
			},{
				"clave": "601",
				"nombreCorto": "GBM",
				"razonSocial": "GBM Grupo Bursátil Mexicano, S.A. de C.V. Casa de Bolsa" 
				
			},{
				"clave": "602",
				"nombreCorto": "MASARI",
				"razonSocial": "Masari Casa de Bolsa, S.A." 
				
			},{
				"clave": "605",
				"nombreCorto": "VALUE",
				"razonSocial": "Value, S.A. de C.V. Casa de Bolsa" 
				
			},{
				"clave": "606",
				"nombreCorto": "ESTRUCTURADORES",
				"razonSocial": "Estructuradores del Mercado de Valores Casa dew Bolsa, S.A. de C.V." 
				
			},{
				"clave": "607",
				"nombreCorto": "TIBER",
				"razonSocial": "Casa de Cambio Tiber, S.A. de C.V." 
				
			},{
				"clave": "608",
				"nombreCorto": "VECTOR",
				"razonSocial": "Vector Casa de Bolsa S.A. de C.V." 
				
			},{
				"clave": "610",
				"nombreCorto": "B&B",
				"razonSocial": "B y B, Casa de Cambio, S.A. de C.V." 
				
			},{
				"clave": "614",
				"nombreCorto": "ACCIVAL",
				"razonSocial": "Acciones y Valores Banamex, S.A. de C.V., Casa de Bolsa" 
				
			},{
				"clave": "615",
				"nombreCorto": "MERRILL LYNCH",
				"razonSocial": "Merrill Lynch Casa de Bolsa, S.A. de C.V., Casa de Bolsa" 
				
			},{
				"clave": "616",
				"nombreCorto": "FINAMEX",
				"razonSocial": "Casa de Bolsa Finamex S.A. de C.V" 
				
			},{
				"clave": "617",
				"nombreCorto": "VALMEX",
				"razonSocial": "Valores Mexicanos Casa de Bolsa, S.A. de C.V." 
				
			},{
				"clave": "618",
				"nombreCorto": "UNICA",
				"razonSocial": "Unica Casa de Cambio, S.A. de C.V." 
				
			},{
				"clave": "619",
				"nombreCorto": "MAPFRE",
				"razonSocial": "MAPFRE Tepeyac, S.A." 
				
			},{
				"clave": "620",
				"nombreCorto": "PROFUTURO",
				"razonSocial": "Profuturo G.N.P., S.A de C.V., Afore" 
				
			},{
				"clave": "621",
				"nombreCorto": "CB ACTINVER",
				"razonSocial": "Actinver Casa de Bolsa, S.A. de C.V." 
				
			},{
				"clave": "622",
				"nombreCorto": "OACTIN",
				"razonSocial": "OPERADORA ACTINVER, S.A. de C.V." 
				
			},{
				"clave": "623",
				"nombreCorto": "SKANDIA",
				"razonSocial": "Skandia Vida, S.A. de C.V." 
				
			},{
				"clave": "626",
				"nombreCorto": "CBDEUTSCHE",
				"razonSocial": "Deustche Securities, S.A. de C.V. CASA DE BOLSA" 
				
			},{
				"clave": "627",
				"nombreCorto": "ZURICH",
				"razonSocial": "Zurich Compañia de Seguros, S.A." 
				
			},{
				"clave": "628",
				"nombreCorto": "ZURICHVI",
				"razonSocial": "Zurich Vida, Compañía de Seguros, S.A." 
				
			},{
				"clave": "629",
				"nombreCorto": "SU CASITA",
				"razonSocial": "Hipotecaria Su Casita, S.A. de C.V. SOFOM ENR" 
				
			},{
				"clave": "630",
				"nombreCorto": "CB INTERCAM",
				"razonSocial": "Intercam Casa de Bolsa, S.A. de C.V." 
				
			},{
				"clave": "631",
				"nombreCorto": "CI BOLSA",
				"razonSocial": "Casa de Bolsa, S.A de C.V." 
				
			},{
				"clave": "632",
				"nombreCorto": "BULLTICK CB",
				"razonSocial": "Bulltick Casa de Bolsa, S.A., de C.V." 
				
			},{
				"clave": "633",
				"nombreCorto": "STERLING",
				"razonSocial": "Sterling Casa de Cambio, S.A. de C.V." 
				
			},{
				"clave": "634",
				"nombreCorto": "FINCOMUN",
				"razonSocial": "Fincomún, Servicios Financieros Comunitarios, S.A. de C.V." 
				
			},{
				"clave": "636",
				"nombreCorto": "HDI SEGUROS",
				"razonSocial": "HDI Seguros, S.A. de C.V." 
				
			},{
				"clave": "637",
				"nombreCorto": "ORDER",
				"razonSocial": "Order Express Casa de Cambio, S.A. de C.V." 
				
			},{
				"clave": "638",
				"nombreCorto": "AKALA",
				"razonSocial": "Akala, S.A. de C.V, Sociedad Financiera Popular" 
				
			},{
				"clave": "640",
				"nombreCorto": "CB JPMORGAN",
				"razonSocial": "J.P. Morgan Casa de Bolsa, S.A. de C.V. J.P. Morgan Grupo Financiero" 
				
			},{
				"clave": "642",
				"nombreCorto": "REFORMA",
				"razonSocial": "Operadora de Recursos Reforma, S.A. de C.V., S.F.P." 
				
			},{
				"clave": "646",
				"nombreCorto": "STP",
				"razonSocial": "Sistema de Transferancias y Pagos STP, S.A. de C.V.SOFOM ENR" 
				
			},{
				"clave": "647",
				"nombreCorto": "TELECOMM",
				"razonSocial": "Telecomunicaciones de México" 
				
			},{
				"clave": "648",
				"nombreCorto": "EVERCORE",
				"razonSocial": "Evercore Casa de Bolsa, S.A. de C.V." 
				
			},{
				"clave": "649",
				"nombreCorto": "SKANDIA",
				"razonSocial": "Skandia Operadora de Fondos, S.A. de C.V." 
				
			},{
				"clave": "651",
				"nombreCorto": "SEGMTY",
				"razonSocial": "Seguros Monterrey New York Life, S.A. de C.V." 
				
			},{
				"clave": "652",
				"nombreCorto": "ASEA",
				"razonSocial": "Solución Asea, S.A. de C.V., Sociedad Financiera Popular" 
				
			},{
				"clave": "653",
				"nombreCorto": "KUSPIT",
				"razonSocial": "Kuspit Casa de Bolsa, S.A. de C.V." 
				
			},{
				"clave": "655",
				"nombreCorto": "SOFIEXPRESS",
				"razonSocial": "J.P. SOFIEXPRESS, S.A. de C.V., S.F.P." 
				
			},{
				"clave": "656",
				"nombreCorto": "UNAGRA",
				"razonSocial": "UNAGRA, S.A. de C.V., S.F.P" 
				
			},{
				"clave": "659",
				"nombreCorto": "OPCIONES EMPRESARIALES DEL NORESTE",
				"razonSocial": "OPCIONES EMPRESARIALES DEL NORESTE, S.A. DE C.V., S.F.P." 
				
			},{
				"clave": "901",
				"nombreCorto": "CLS",
				"razonSocial": "CLs Bank International" 
				
			},{
				"clave": "902",
				"nombreCorto": "INDEVAL",
				"razonSocial": "SD. Indeval, S.A. de C.V." 
				
			},{
				"clave": "670",
				"nombreCorto": "LIBERTAD",
				"razonSocial": "Libertad Servicios Financieros, S.A. De C.V." 
				
			},
		]
		$scope.estadoCivil=[
			{
				"estado": "Soltero/a", 											
			},{
				"estado": "Comprometido/a", 											
			},{
				"estado": "Casado/a", 											
			},{
				"estado": "Divorciado/a", 											
			},{
				"estado": "Viudo/a", 											
			},
			]
		$scope.update = function() {
			console.log($scope.empleados);
			var cBanco = $("#claveBanco option:selected").text();
			var nBanco = $("#nombreBanco option:selected").text();
			var upd = {
				empleado : $scope.empleados,
				claveBanco: cBanco,
				nombreBanco: nBanco
			}
			console.log(upd);
			empleadosService.update(upd).then(
				function(data) {
					$scope.empleados = data;

					alert("Empleado modificado correctamente");
					$location.path("/empleados/details/rfc/"
						+ $scope.empleados.numEmpleado);
				});
		}

		$scope.regresaListaEmpRfc = function() {
			$location.path("/empleados/list/"
				+ $cookieStore.get('rfcEmpresa'));
		}
		$scope.clean = function() {
			document.getElementById("forma").reset();
		}
	}}]);

app
.controller(
	"empleadosListController",
	[
	'$scope',
	'$http',
	'$location',
	'$routeParams',
	'empleadosService',
	'$rootScope',
	function($scope, $http, $location, $routeParams,
		empleadosService, $rootScope) {
		if(!$rootScope.variable){
			$location.path("/login");
		}else{
		$scope.IsShow = false;
		$rootScope.rfc = $routeParams.rfc;
		empleadosService
		.findByEmpresa($routeParams.rfc)
		.then(
			function(datos) {
				$scope.empleados = datos;
				for (var i = 0; i < $scope.empleados.length; i++) {
					$scope.empleados[i].busquedaAttr = $scope.empleados[i].empleado.nombre.nombresDePila
					+ " "
					+ $scope.empleados[i].empleado.nombre.apellidoPaterno
					+ " "
					+ $scope.empleados[i].empleado.nombre.apellidoMaterno
					+ " "
					+ $scope.empleados[i].empleado.numEmpleado
					+ " "
					+ $scope.empleados[i].empleado.RFC
				}
				console.log(datos);
			});

		$scope.add = function() {
			$location.path("/empresas/empleados/"
				+ $rootScope.rfc);
		}
		$scope.regresaEmpresa = function() {
			$location.path("/empresas/details/"
				+ $rootScope.rfc);
		}
		$scope.verEmpleados = function(id) {
			$location.path("/empleados/details/rfc/" + id);
		}
		$scope.editarEmpleados = function(id) {
			$location.path("/empleados/edit/rfc/" + id);
		}
	}}]);

app
.controller(
	"empleadosEsquemasController",
	[
	'$scope',
	'$http',
	'$location',
	'$routeParams',
	'empleadosService',
	'$rootScope',
	'$cookies',
	'$cookieStore',

	function($scope, $http, $location, $routeParams,
		empleadosService, $rootScope, $cookies,
		$cookieStore) {
		if(!$rootScope.variable){
			$location.path("/login");
		}else{
		$rootScope.nombreEmpresa;
		if ($routeParams.id == "undefined") {
			$location.path("/empleados/esquemas/"
				+ $cookieStore.get('idEsquema'));
			console.log($cookieStore.get('idEsquema'));
		}else{
			$cookieStore.put('idEsquema',$routeParams.id);
		}
		var send={
				idEsquema : $cookieStore.get('idEsquema')
			}
		$scope.IsShow = true;
		empleadosService.findByRegimen(send.idEsquema)
		.then(
			function(datos) {
				$scope.id = $routeParams.id;
												// console.log("este es el
												// primero"+$routeParams.id);
												$cookieStore.put('idEsquema',
													$routeParams.id);
												// console.log("este es el
												// segundo"+$cookieStore.get('idEsquema'));
												$scope.empleados = datos;
												for (var i = 0; i < $scope.empleados.length; i++) {
													$scope.empleados[i].busquedaAttr = $scope.empleados[i].empleado.nombre.nombresDePila
													+ " "
													+ $scope.empleados[i].empleado.nombre.apellidoPaterno
													+ " "
													+ $scope.empleados[i].empleado.nombre.apellidoMaterno
													+ " "
													+ $scope.empleados[i].empleado.numEmpleado
													+ " "
													+ $scope.empleados[i].empleado.RFC
												}
												console.log(datos);

											});
		$scope.regresaEmpresa = function() {
			$location.path("/empresas/details/"
				+ $rootScope.rfc);
		}
		$scope.regresaEsquemas = function() {
			$location.path("/esquemas/details/"
				+ $routeParams.id);
		}
		$scope.add = function() {
			$location.path("/empleados/add/"
				+ $routeParams.id);
		}
		$scope.verEmpleados = function(id) {
			$location.path("/empleados/details/" + id);
		}
		$scope.editarEmpleados = function(id) {
			$location.path("/empleados/edit/" + id);
		}
		$scope.eliminarEmpleados = function(id) {
			empleadosService.elimina($routeParams.id).then(
				function(data) {
					console.log($scope.empleados);
					;
										});// to do
		}
	}} ]);

app
.controller(
	'pagosController',
	[
	'$scope',
	'$http',
	'$location',
	'$routeParams',
	'empleadosService',
	'$rootScope',
	'$cookies',
	'$cookieStore',
	function($scope, $http, $location, $routeParams,
		empleadosService, $rootScope, $cookies,
		$cookieStore) {
		$rootScope.checaUsuario();
		if(!$rootScope.variable){
			$location.path("/login");
		}else{
		$rootScope.nombreEmpresa;
		$scope.pagos = {
			lista : []
		}
		send={
			idRfcEmpresa: $cookieStore.get('rfcEmpresa'),
			idEsquema:$routeParams.id
		}
		console.log(send);
		empleadosService.calcularPagos(send)
		.then(function(data) {
			$scope.pagos.lista = data;
			if(data.length==0){
				alert("No hay pagos pendientes por guardar");
				$scope.Pagos=true;
			}
			console.log(data);
			
			$scope.ClaveSat=true;
			$scope.DescripcionSat=true;
			$scope.ClaveEmpresa=false;
			$scope.DescripcionEmpresa=false;
			$scope.IsShow=true;
			
			$scope.ClaveSatDed=true;
			$scope.DescripcionSatDed=true;
			$scope.ClaveEmpresaDed=false;
			$scope.DescripcionEmpresaDed=false;
			$scope.Porcentaje=true;
			
			$scope.Cantidad = true;
			$scope.Domingos = true;
			$scope.Porcentaje = true;
			$scope.Incapacidad = true;
			$scope.Credito = true;
			$scope.Descuento = true;
			$scope.Ausencia = true;
			$scope.Beneficiario = true;
			$scope.Contratante = true;
			$scope.Riesgos = true;
			
			$scope.Horas = true;
			$scope.Semanal = true;
			$scope.Quincenal = true;
			$scope.Decenal = true;
			$scope.Mensual = true;
		}).then(function(response){
			if(response.status==403){
				alert("No está autorizado para realizar esta acción");
				$location.path("/");
			}
		})
		$scope.catalogos=[
								{
									"tipo": "Sat"
									
								},
								{
									"tipo": "Propias de la empresa"
								}
							]
							$scope.catalogosDed=[
								{
									"tipo": "Sat"
									
								},
								{
									"tipo": "Propias de la empresa"
								}
							]
		$scope.listpercecpciones = [
		{
			"clave" : "",
			"descripcion" : "",
		},
		{
			"clave" : "002",
			"descripcion" : "Gratificación Anual (Aguinaldo)",
		},
		{
			"clave" : "003",
			"descripcion" : "Participación de los Trabajadores en las Utilidades PTU",
		},
		{
			"clave" : "004",
			"descripcion" : "Reembolso de Gastos Médicos Dentales y Hospitalarios",
		},
		{
			"clave" : "005",
			"descripcion" : "Fondo de Ahorro",
		},
		{
			"clave" : "006",
			"descripcion" : "Caja de Ahorro",
		},
		{
			"clave" : "009",
			"descripcion" : "Contribuciones a Cargo del Trabajador Pagadas por el Patrón",
		},
		{
			"clave" : "010",
			"descripcion" : "Premios por puntualidad",
		},
		{
			"clave" : "011",
			"descripcion" : "Prima de Seguro de vida",
		},
		{
			"clave" : "012",
			"descripcion" : "Seguro de Gastos Médicos Mayores",
		},
		{
			"clave" : "013",
			"descripcion" : "Cuotas Sindicales Pagadas por el Patrón",
		},
		{
			"clave" : "014",
			"descripcion" : "Subsidios por incapacidad",
		},
		{
			"clave" : "015",
			"descripcion" : "Becas para Trabajadores y/o hijos",
		},
		{
			"clave" : "019",
			"descripcion" : "Horas extra",
		},
		{
			"clave" : "020",
			"descripcion" : "Prima dominical",
		},
		{
			"clave" : "021",
			"descripcion" : "Prima vacacional",
		},
		{
			"clave" : "022",
			"descripcion" : "Prima por antigüedad",
		},
		{
			"clave" : "023",
			"descripcion" : "Pagos por separación",
		},
		{
			"clave" : "024",
			"descripcion" : "Seguro de retiro",
		},
		{
			"clave" : "025",
			"descripcion" : "Indemnizaciones",
		},
		{
			"clave" : "026",
			"descripcion" : "Reembolso por funeral",
		},
		{
			"clave" : "027",
			"descripcion" : "Cuotas de seguridad social pagadas por el patrón",
		},
		{
			"clave" : "028",
			"descripcion" : "Comisiones",
		},
		{
			"clave" : "029",
			"descripcion" : "Vales de despensa",
		},
		{
			"clave" : "030",
			"descripcion" : "Vales de restaurante",
		},
		{
			"clave" : "031",
			"descripcion" : "Vales de gasolina",
		},
		{
			"clave" : "032",
			"descripcion" : "Vales de ropa",
		},
		{
			"clave" : "033",
			"descripcion" : "Ayuda para renta",
		},
		{
			"clave" : "034",
			"descripcion" : "Ayuda para artículos escolares",
		},
		{
			"clave" : "035",
			"descripcion" : "Ayuda para anteojos",
		},
		{
			"clave" : "036",
			"descripcion" : "Ayuda para transporte",
		},
		{
			"clave" : "037",
			"descripcion" : "Ayuda para gastos de funeral",
		},
		{
			"clave" : "038",
			"descripcion" : "Otros ingresos por salarios",
		},
		{
			"clave" : "039",
			"descripcion" : "Jubilaciones, pensiones o haberes de retiro",
		},
		{
			"clave" : "044",
			"descripcion" : "Jubilaciones, pensiones o haberes de retiro en parcialidades",
		},
		{
			"clave" : "045",
			"descripcion" : "Ingresos en acciones o títulos valor que representan bienes",
		},
		{
			"clave" : "046",
			"descripcion" : "Ingresos asimilados a salarios",
		},
		{
			"clave" : "047",
			"descripcion" : "Alimentación",
		},
		{
			"clave" : "048",
			"descripcion" : "Habitación",
		},
		{
			"clave" : "049",
			"descripcion" : "Premios por asistencia",
		}, ]
		$scope.listdeducciones = [
		{
			"clave" : "",
			"descripcion" : "",
		},
		{
			"clave" : "001",
			"descripcion" : "Seguridad Social",
		},
		{
			"clave" : "002",
			"descripcion" : "ISR",
		},
		{
			"clave" : "003",
			"descripcion" : "Aportaciones a retiro, cesantía en edad avanzada y vejez",
		},
		{
			"clave" : "004",
			"descripcion" : "Otros",
		},
		{
			"clave" : "005",
			"descripcion" : "Aportaciones a Fondo de vivienda",
		},
		{
			"clave" : "006",
			"descripcion" : "Descuento por incapacidad",
		},
		{
			"clave" : "007",
			"descripcion" : "Pension alimenticia",
		},
		{
			"clave" : "008",
			"descripcion" : "Renta",
		},
		{
			"clave" : "009",
			"descripcion" : "Préstamos a provenientes del Fondo Nacional de la Vivienda para losm Trabajadores",
		},
		{
			"clave" : "010",
			"descripcion" : "Pago por crédito vivienda",
		},
		{
			"clave" : "011",
			"descripcion" : "Pago de abonos INFONACOT",
		},
		{
			"clave" : "012",
			"descripcion" : "Anticipo de salarios",
		},
		{
			"clave" : "013",
			"descripcion" : "Pagos hechos con exceso al trabajador",
		},
		{
			"clave" : "014",
			"descripcion" : "Errores",
		},
		{
			"clave" : "015",
			"descripcion" : "Pérdidas",
		},
		{
			"clave" : "016",
			"descripcion" : "Averías",
		},
		{
			"clave" : "017",
			"descripcion" : "Aquisición de artículos producidos por la empresa o establecimiento",
		},
		{
			"clave" : "018",
			"descripcion" : "Cuotas para la constitución y fomento de sociedades cooperativas y de cajas de ahorro",
		},
		{
			"clave" : "019",
			"descripcion" : "Cuotas sindicales",
		},
		{
			"clave" : "020",
			"descripcion" : "Ausencia (Ausentismo)",
		},
		{
			"clave" : "021",
			"descripcion" : "Cuotas obrero patronales",
		}, ]
		$scope.listTipoIncapacidad = [ {
			"clave" : "",
			"descripcion" : "",
		}, {
			"clave" : "1",
			"descripcion" : "Riesgo de Trabajo",
		}, {
			"clave" : "2",
			"descripcion" : "Enfermedad en general",
		}, {
			"clave" : "3",
			"descripcion" : "Maternidad",
		}, ]
		$scope.horasExtra = [ {
			"horas" : "1"
		}, {
			"horas" : "2"
		}, {
			"horas" : "3"
		}, {
			"horas" : "4"
		}, {
			"horas" : "5"
		}, {
			"horas" : "6"
		}, {
			"horas" : "7"
		}, {
			"horas" : "8"
		}, {
			"horas" : "9"
		}, {
			"horas" : "10"
		}, {
			"horas" : "11"
		}, {
			"horas" : "12"
		}, {
			"horas" : "13"
		}, {
			"horas" : "14"
		}, {
			"horas" : "15"
		}, ]
		$scope.listTipoDescuento = [ {
			"clave" : "",
			"descripcion" : "",
		}, {
			"clave" : "1",
			"descripcion" : "Porcentaje",
		}, {
			"clave" : "2",
			"descripcion" : "Cuota Fija",
		}, {
			"clave" : "3",
			"descripcion" : "Veces SMGV",
		}, {
			"clave" : "4",
			"descripcion" : "Veces UMA",
		}, ]
		$scope.beneficiarios = [ {
			"descripcion" : "",
		}, {
			"descripcion" : "Cónyuge",
		}, {
			"descripcion" : "Concubina o Concubino",
		}, {
			"descripcion" : "Padres",
		}, {
			"descripcion" : "Hijos",
		},{
			"descripcion" : "Abuelos",
		},{
			"descripcion" : "Nietos",
		},{
			"descripcion" : "Otros",
		}, ]
		$scope.contratante = [ {
			"descripcion" : "",
		}, {
			"descripcion" : "Empleador",
		}, {
			"descripcion" : "Trabajador",
		}, {
			"descripcion" : "Otro",
		}, ]
		$scope.riesgosAmparados = [ {
			"descripcion" : "",
		}, {
			"descripcion" : "Fallecimiento",
		}, {
			"descripcion" : "Invalidez",
		}, {
			"descripcion" : "Pérdidas orgánicas",
		}, {
			"descripcion" : "Incapacidad del asegurado para realizar un trabajo personal",
		},]
		
		$scope.flags = [ false, false, false, false ];
		empleadosService.catalogosPercepciones().then(
				function(data) {
					$scope.percepciones = data;
					$scope.flags[0] = true;
				})
			empleadosService.catalogosDeducciones().then(
				function(data) {
					$scope.deducciones = data;
					$scope.flags[1] = true;
				})

			empleadosService
			.catalogosContabilidadPercepciones($cookieStore.get('rfcEmpresa'))
			.then(function(data) {
				$scope.percepcionesContabilidad = data;
				$scope.flags[2] = true;			
			})

			empleadosService.catalogosContabilidadDeducciones($cookieStore.get('rfcEmpresa'))
			.then(function(data) {
				$scope.deduccionesContabilidad = data;
				$scope.flags[3] = true;
			})	
			$scope
.$watch(
'flags',
function() {
if ($scope.flags[0]
&& $scope.flags[1]
&& $scope.flags[2]
&& $scope.flags[3]) {
$scope.tablacompleta = [];
$scope.tablacompletaDed = [];

for (var i = 0; i < $scope.percepcionesContabilidad.length; i++) {
var renglon = {};
renglon.percepcionCont = $scope.percepcionesContabilidad[i];
for (var j = 0; j < $scope.percepciones.length; j++) {
	if (renglon.percepcionCont.llaveAgrupadora.raw.id == $scope.percepciones[j].id) {
		var found = $scope.percepciones[j];
		renglon.percepcionSat = found;
		break;
	}
}
$scope.tablacompleta
.push(renglon);
}
for (var i = 0; i < $scope.deduccionesContabilidad.length; i++) {
var renglonDed = {};
renglonDed.deduccionCont = $scope.deduccionesContabilidad[i];
for (var j = 0; j < $scope.deducciones.length; j++) {
	if (renglonDed.deduccionCont.llaveAgrupadora.raw.id == $scope.deducciones[j].id) {
		var found = $scope.deducciones[j];
		renglonDed.deduccionSat = found;
		break;
	}
}
$scope.tablacompletaDed
.push(renglonDed);
}
}
}, true);
				
		$scope.catalPer= function(catalogos){
			var cat = $scope.tipocat;
			if(cat=="Sat"){
				$scope.ClaveSat=false;
				$scope.DescripcionSat=false;
				$scope.ClaveEmpresa=true;
				$scope.DescripcionEmpresa=true;
				$scope.IsShow=true;
			}
			if(cat=="Propias de la empresa"){
				$scope.ClaveSat=true;
				$scope.DescripcionSat=true;
				$scope.ClaveEmpresa=false;
				$scope.DescripcionEmpresa=false;
				$scope.IsShow=true;
			}
		}
		
		$scope.catalDed= function(catalogosDed){
			var cat = $scope.tipocatded;
			if(cat=="Sat"){
				$scope.ClaveSatDed=false;
				$scope.DescripcionSatDed=false;
				$scope.ClaveEmpresaDed=true;
				$scope.DescripcionEmpresaDed=true;
				$scope.Porcentaje=true;
			}
			if(cat=="Propias de la empresa"){
				$scope.ClaveSatDed=true;
				$scope.DescripcionSatDed=true;
				$scope.ClaveEmpresaDed=false;
				$scope.DescripcionEmpresaDed=false;
				$scope.Porcentaje=true;
			}
		}
		$scope.editar = function(pago) {
								// $scope.inputPerc = {
								// horasExtra : []
								// };
								$scope.pagoEdita = pago;
							}
		$scope.addPer = function(inputPerc) {
			var s = $scope.pagoEdita;
			var dSat = $("#descripcionSat option:selected").text();
			var cSat = $("#claveSat option:selected").text();							
			var dEmp = $("#descripcionEmpresa option:selected").text();
			var cEmp = $("#claveEmpresa option:selected").text();
//			$scope.inputPerc.horasExtra=[];
			if(inputPerc.horasExtra){
				$scope.inputPerc.horasExtra=[inputPerc.horasExtra[0],inputPerc.horasExtra[1],inputPerc.horasExtra[2],inputPerc.horasExtra[3]];
			}
			var esq = {
					clave : cEmp,
					descripcion : dEmp.trim(),
					cantidad : inputPerc.cantidad,
					tipo : cSat,
					horasExtra : inputPerc.horasExtra,
					beneficiarios : inputPerc.beneficiario,
					contratante : inputPerc.contratante,
					riesgosAmparados : inputPerc.riesgosAmparados
			}
			console.log(esq);
			console.log(inputPerc);
			// ToDoService.add($scope.newEsq);
//			var esq = $scope.inputPerc;
//			for (var i = 0; i < $scope.listpercecpciones.length; i++) {
//				if ($scope.listpercecpciones[i].clave == esq.clave) {
//					esq.descripcion = $scope.listpercecpciones[i].descripcion
//				}
//			}								
			s.percepciones.push(esq);
			$scope.inputPerc = {};
		}
		
//		$scope.valor = function(inputPerc){
//			console.log(inputPerc)
//			var esq = $scope.inputPerc;
//			for (var i = 0; i < $scope.percep.length; i++) {
//				if ($scope.percep[i].clave == esq.clave) {
//					esq.descripcion = $scope.listpercecpciones[i].descripcion
//					if(esq.clave=="002"||esq.clave=="003" || esq.clave=="017" || esq.clave=="021" || esq.clave=="027" || esq.clave=="039" || esq.clave=="042"){
//						$scope.IsShow = true;
//					}else{
//						$scope.IsShow= false;
//					}
//				}
//			}
//		}
		
		
		$scope.valor = function(inputPerc){
			var val= $scope.inputPerc;
			//console.log(val);
			if(val.clave=="002"||val.clave=="003" || val.clave=="005" || val.clave=="006" || val.clave=="009" || val.clave=="013" || val.clave=="014" || val.clave=="020" || val.clave=="021" || val.clave=="022" || val.clave=="023"|| val.clave=="024"|| val.clave=="025" || val.clave=="027" || val.clave=="037"  ){
				$scope.IsShow = true;
				$scope.Horas = true;
				$scope.Semanal = true;
				$scope.Quincenal = true;
				$scope.Decenal = true;
				$scope.Mensual = true;
				$scope.Beneficiario = true;
				$scope.Contratante = true;
				$scope.Riesgos = true;
			}else if(val.clave=="011"){
				$scope.IsShow = false;
				$scope.Horas = true;
				$scope.Semanal = true;
				$scope.Quincenal = true;
				$scope.Decenal = true;
				$scope.Mensual = true;
				$scope.Beneficiario = false;
				$scope.Contratante = false;
				$scope.Riesgos = false;
			}else if($scope.pagoEdita.formaPago=="MENSUAL" && val.clave=="019"){
				$scope.IsShow = true;
				$scope.Horas = false;
				$scope.Semanal = false;
				$scope.Quincenal = false;
				$scope.Decenal = false;
				$scope.Mensual = false;
				$scope.Beneficiario = true;
				$scope.Contratante = true;
				$scope.Riesgos = true;
			}else if($scope.pagoEdita.formaPago=="DECENAL" && val.clave=="019"){
				$scope.IsShow = true;
				$scope.Horas = false;
				$scope.Semanal = false;
				$scope.Quincenal = false;
				$scope.Decenal = false;
				$scope.Mensual = true;
				$scope.Beneficiario = true;
				$scope.Contratante = true;
				$scope.Riesgos = true;
			}else if($scope.pagoEdita.formaPago=="QUINCENAL" && val.clave=="019"){
				$scope.IsShow = true;
				$scope.Horas = false;
				$scope.Semanal = false;
				$scope.Quincenal = false;
				$scope.Decenal = true;
				$scope.Mensual = true;
				$scope.Beneficiario = true;
				$scope.Contratante = true;
				$scope.Riesgos = true;
			}else if($scope.pagoEdita.formaPago=="SEMANAL" && val.clave=="019"){
				$scope.IsShow = true;
				$scope.Horas = false;
				$scope.Semanal = false;
				$scope.Quincenal = true;
				$scope.Decenal = true;
				$scope.Mensual = true;
				$scope.Beneficiario = true;
				$scope.Contratante = true;
				$scope.Riesgos = true;
			}else{
				$scope.IsShow= false;
				$scope.Horas = true;
				$scope.Semanal = true;
				$scope.Quincenal = true;
				$scope.Decenal = true;
				$scope.Mensual = true;
				$scope.Beneficiario = true;
				$scope.Contratante = true;
				$scope.Riesgos = true;
			}
			
		}
		
		$scope.addDed = function(inputDeduc) {
			var s = $scope.pagoEdita;
			var dSat = $("#descripcionSatDed option:selected").text();
			var cSat = $("#claveSatDed option:selected").text();							
			var dEmp = $("#descripcionEmpresaDed option:selected").text();
			var cEmp = $("#claveEmpresaDed option:selected").text();											
			
			var esq = {
					clave : cEmp,
					descripcion : dEmp.trim(),
					descuento : $scope.inputDeduc.descuento,
					tipo : cSat,
					incapacidad  : $scope.inputDeduc.incapacidad,
					diasIncapacidad : $scope.inputDeduc.diasIncapacidad,
					modalidad : $scope.inputDeduc.modalidad,
					valor : $scope.inputDeduc.valorDeCredito,
					diasAusente : $scope.inputDeduc.diasAusente
					
			}
			console.log(esq);
			
			s.deducciones.push(esq);
			$scope.inputDeduc = {};
		}
//			var ded = $scope.inputDeduc;
//			for (var i = 0; i < $scope.listdeducciones.length; i++) {
//				if ($scope.listdeducciones[i].clave == ded.clave) {
//					ded.descripcion = $scope.listdeducciones[i].descripcion
//					
//				}
//			}
//			$scope.regimen.deducciones.push(ded);
//			$scope.inputDeduc = {};
//		}
		$scope.valordos = function(inputDeduc) {								
			var ded = $scope.inputDeduc;								
					
					if (ded.clave=="001" || ded.clave=="002" || ded.clave=="003"|| ded.clave=="005"|| ded.clave=="021"|| ded.clave=="022"){											
						$scope.Descuento = true;
						$scope.Incapacidad = true;
						$scope.Credito= true;
						$scope.Porcentaje = true;
						$scope.Ausencia = true;
					} else if(ded.clave == "006") {
						$scope.Porcentaje = true;
						$scope.Incapacidad = false;
						$scope.Credito = true;	
						$scope.Descuento = true;
						$scope.Ausencia = true;
					} else if(ded.clave == "007" || ded.clave=="019"){
						$scope.Porcentaje = false;
						$scope.Incapacidad = true;
						$scope.Credito = true;	
						$scope.Descuento = true;
						$scope.Ausencia = true;
					} else if (ded.clave == "010") {
						$scope.Descuento = true;
						$scope.Incapacidad = true;
						$scope.Credito= false;
						$scope.Porcentaje = true;
						$scope.Ausencia = true;
					} else if (ded.clave == "020") {
						$scope.Descuento = true;
						$scope.Incapacidad = true;
						$scope.Credito= true;
						$scope.Porcentaje = true;
						$scope.Ausencia = false;
					} else {
						$scope.Descuento = false;
						$scope.Incapacidad = true;
						$scope.Credito= true;
						$scope.Porcentaje = true;
						$scope.Ausencia = true;
					}
					
		}

//							$scope.addPer = function(percepcion) {
//								var s = $scope.pagoEdita;
//								var per = $scope.inputPerc;
//								for (var i = 0; i < $scope.listpercecpciones.length; i++) {
//									if ($scope.listpercecpciones[i].clave == per.clave) {
//										per.descripcion = $scope.listpercecpciones[i].descripcion
//									}
//								}
//								console.log(per);
//								if (per.clave == null) {
//									alert("Debes agregar una percepcion");
//								} else if (per.clave == "002"
//									|| per.clave == "003"
//									|| per.clave == "021") {
//									s.percepciones.push(per);
//									alert("Percepcion Agregada");
//									$scope.inputPerc = {};
//									document.getElementById("forma").reset();
//									$scope.Cantidad = true;
//									$scope.Domingos = true;
//									$scope.Horas = true;
//									$scope.Porcentaje = true;
//									$scope.Incapacidad = true;
//									$scope.Credito = true;
//								} else if (per.clave == "004"
//									&& per.cantidad != null
//									|| per.clave == "005"
//									&& per.cantidad != null
//									|| per.clave == "006"
//									&& per.cantidad != null
//									|| per.clave == "009"
//									&& per.cantidad != null
//									|| per.clave == "010"
//									&& per.cantidad != null
//									|| per.clave == "011"
//									&& per.cantidad != null
//									|| per.clave == "012"
//									&& per.cantidad != null
//									|| per.clave == "013"
//									&& per.cantidad != null
//									|| per.clave == "014"
//									&& per.cantidad != null
//									|| per.clave == "015"
//									&& per.cantidad != null
//									|| per.clave == "022"
//									&& per.cantidad != null
//									|| per.clave == "023"
//									&& per.cantidad != null
//									|| per.clave == "024"
//									&& per.cantidad != null) {
//									s.percepciones.push(per);
//									alert("Percepcion Agregada");
//									$scope.inputPerc = {};
//									document.getElementById("forma").reset();
//									$scope.Cantidad = true;
//									$scope.Domingos = true;
//									$scope.Horas = true;
//									$scope.Porcentaje = true;
//									$scope.Incapacidad = true;
//									$scope.Credito = true;
//								} else if ((per.cantidad >= 1 && per.cantidad <= 2)
//									&& $scope.pagoEdita.formaPago == "SEMANAL"
//									&& per.clave == "020") {
//									s.percepciones.push(per);
//									alert("Percepcion Agregada");
//									$scope.inputPerc = {};
//									document.getElementById("forma").reset();
//									$scope.Cantidad = true;
//									$scope.Domingos = true;
//									$scope.Horas = true;
//									$scope.Porcentaje = true;
//									$scope.Incapacidad = true;
//									$scope.Credito = true;
//								} else if ((per.cantidad >= 1 && per.cantidad <= 3)
//									&& $scope.pagoEdita.formaPago == "QUINCENAL"
//									&& per.clave == "020") {
//									s.percepciones.push(per);
//									alert("Percepcion Agregada");
//									$scope.inputPerc = {};
//									document.getElementById("forma").reset();
//									$scope.Cantidad = true;
//									$scope.Domingos = true;
//									$scope.Horas = true;
//									$scope.Porcentaje = true;
//									$scope.Incapacidad = true;
//									$scope.Credito = true;
//								} else if ((per.cantidad >= 1 && per.cantidad <= 4)
//									&& $scope.pagoEdita.formaPago == "DECENAL"
//									&& per.clave == "020") {
//									s.percepciones.push(per);
//									alert("Percepcion Agregada");
//									$scope.inputPerc = {};
//									document.getElementById("forma").reset();
//									$scope.Cantidad = true;
//									$scope.Domingos = true;
//									$scope.Horas = true;
//									$scope.Porcentaje = true;
//									$scope.Incapacidad = true;
//									$scope.Credito = true;
//								} else if ((per.cantidad >= 1 && per.cantidad <= 5)
//									&& $scope.pagoEdita.formaPago == "MENSUAL"
//									&& per.clave == "020") {
//									s.percepciones.push(per);
//									alert("Percepcion Agregada");
//									$scope.inputPerc = {};
//									document.getElementById("forma").reset();
//									$scope.Cantidad = true;
//									$scope.Domingos = true;
//									$scope.Horas = true;
//									$scope.Porcentaje = true;
//									$scope.Incapacidad = true;
//									$scope.Credito = true;
//								} else if ((per.horasExtra[0] != null
//									|| per.horasExtra[1] != null
//									|| per.horasExtra[2] != null || per.horasExtra[3] != null)
//								&& per.clave == "019") {
//									$scope.inputPerc.horasExtra = [
//									per.horasExtra[0],
//									per.horasExtra[1],
//									per.horasExtra[2],
//									per.horasExtra[3] ];
//									s.percepciones.push(per);
//									alert("Percepcion Agregada");
//									$scope.inputPerc = {};
//									document.getElementById("forma").reset();
//									$scope.Cantidad = true;
//									$scope.Domingos = true;
//									$scope.Horas = true;
//									$scope.Porcentaje = true;
//									$scope.Incapacidad = true;
//									$scope.Credito = true;
//								} else {
//									alert("Percepcion No Agregada")
//									$scope.inputPerc = {};
//									document.getElementById("forma").reset();
//									$scope.Cantidad = true;
//									$scope.Domingos = true;
//									$scope.Horas = true;
//									$scope.Porcentaje = true;
//									$scope.Incapacidad = true;
//									$scope.Credito = true;
//								}
//							}
//							$scope.valor = function(listpercecpciones) {
//								var esq = $scope.inputPerc;
//								for (var i = 0; i < $scope.listpercecpciones.length; i++) {
//									if ($scope.listpercecpciones[i].clave == esq.clave) {
//										esq.descripcion = $scope.listpercecpciones[i].descripcion
//										if (esq.clave == "002"
//											|| esq.clave == "003"
//											|| esq.clave == "017"
//											|| esq.clave == "021"
//											|| esq.clave == "027"
//											|| esq.clave == "039"
//											|| esq.clave == "042") {
//											$scope.Cantidad = true;
//										$scope.Domingos = true;
//										$scope.Horas = true;
//
//									} else if (esq.clave == "020") {
//										$scope.Domingos = false;
//										$scope.Cantidad = true;
//										$scope.Horas = true;
//
//									} else if (esq.clave == "019"
//										&& $scope.pagoEdita.formaPago == "DECENAL") {
//										$scope.Horas = false;
//										$scope.Domingos = true;
//										$scope.Cantidad = true;
//										$scope.Semanal = false;
//										$scope.Quincenal = false;
//										$scope.Decenal = false;
//										$scope.Mensual = true;
//
//									} else if (esq.clave == "019"
//										&& $scope.pagoEdita.formaPago == "SEMANAL") {
//										$scope.Horas = false;
//										$scope.Domingos = true;
//										$scope.Cantidad = true;
//										$scope.Semanal = false;
//										$scope.Quincenal = true;
//										$scope.Decenal = true;
//										$scope.Mensual = true;
//
//									} else if (esq.clave == "019"
//										&& $scope.pagoEdita.formaPago == "QUINCENAL") {
//										$scope.Horas = false;
//										$scope.Domingos = true;
//										$scope.Cantidad = true;
//										$scope.Semanal = false;
//										$scope.Quincenal = false;
//										$scope.Decenal = true;
//										$scope.Mensual = true;
//
//									} else if (esq.clave == "019"
//										&& $scope.pagoEdita.formaPago == "MENSUAL") {
//										$scope.Horas = false;
//										$scope.Domingos = true;
//										$scope.Cantidad = true;
//										$scope.Semanal = false;
//										$scope.Quincenal = false;
//										$scope.Decenal = false;
//										$scope.Mensual = false;
//
//									} else if (esq.clave == "") {
//										$scope.Cantidad = true;
//										$scope.Domingos = true;
//										$scope.Horas = true;
//										$scope.Porcentaje = true;
//										$scope.Incapacidad = true;
//										$scope.Credito = true;
//
//										$scope.Sem = true;
//										$scope.Quin = true;
//										$scope.Dec = true;
//										$scope.Men = true;
//
//									} else {
//										$scope.Domingos = true;
//										$scope.Cantidad = false;
//										$scope.Horas = true;
//									}
//								}
//							}
//						}
//
//						$scope.addDed = function(deduccion) {
//							var s = $scope.pagoEdita;
//							var ded = $scope.inputDeduc;
//							for (var i = 0; i < $scope.listdeducciones.length; i++) {
//								if ($scope.listdeducciones[i].clave == ded.clave) {
//									ded.descripcion = $scope.listdeducciones[i].descripcion
//								}
//							}
//							console.log(ded);
//							if (ded.clave == null) {
//								alert("Debes agregar una deduccion");
//							} else if (ded.clave == "002"
//								|| ded.clave == "003"
//								|| ded.clave == "004"
//								|| ded.clave == "005"
//								|| ded.clave == "007"
//								|| ded.clave == "008"
//								|| ded.clave == "009"
//								|| ded.clave == "011"
//								|| ded.clave == "012"
//								|| ded.clave == "013"
//								|| ded.clave == "014"
//								|| ded.clave == "015"
//								|| ded.clave == "016"
//								|| ded.clave == "017"
//								|| ded.clave == "018"
//								|| ded.clave == "019"
//								|| ded.clave == "020"
//								|| ded.clave == "021"
//								|| ded.clave == "022"
//								|| ded.clave == "023") {
//								s.deducciones.push(ded);
//								alert("Deduccion Agregada");
//								$scope.inputDeduc = {};
//								document.getElementById("forma").reset();
//								$scope.Cantidad = true;
//								$scope.Domingos = true;
//								$scope.Horas = true;
//								$scope.Porcentaje = true;
//								$scope.Incapacidad = true;
//								$scope.Credito = true;
//							} else if ((ded.diasIncapacidad >= 1 && ded.diasIncapacidad <= 7)
//								&& $scope.pagoEdita.formaPago == "SEMANAL"
//								&& ded.clave == "006"
//								&& ded.incapacidad != null) {
//								s.deducciones.push(ded);
//								alert("Deduccion Agregada");
//								$scope.inputDeduc = {};
//								document.getElementById("forma").reset();
//								$scope.Cantidad = true;
//								$scope.Domingos = true;
//								$scope.Horas = true;
//								$scope.Porcentaje = true;
//								$scope.Incapacidad = true;
//							} else if ((ded.diasIncapacidad >= 1 && ded.diasIncapacidad <= 15)
//								&& $scope.pagoEdita.formaPago == "QUINCENAL"
//								&& ded.clave == "006"
//								&& ded.incapacidad != null) {
//								s.deducciones.push(ded);
//								alert("Deduccion Agregada");
//								$scope.inputDeduc = {};
//								document.getElementById("forma").reset();
//								$scope.Cantidad = true;
//								$scope.Domingos = true;
//								$scope.Horas = true;
//								$scope.Porcentaje = true;
//								$scope.Incapacidad = true;
//							} else if ((ded.diasIncapacidad >= 1 && ded.diasIncapacidad <= 10)
//								&& $scope.pagoEdita.formaPago == "DECENAL"
//								&& ded.clave == "006"
//								&& ded.incapacidad != null) {
//								s.deducciones.push(ded);
//								alert("Deduccion Agregada");
//								$scope.inputDeduc = {};
//								document.getElementById("forma").reset();
//								$scope.Cantidad = true;
//								$scope.Domingos = true;
//								$scope.Horas = true;
//								$scope.Porcentaje = true;
//								$scope.Incapacidad = true;
//							} else if ((ded.diasIncapacidad >= 1 && ded.diasIncapacidad <= 31)
//								&& $scope.pagoEdita.formaPago == "MENSUAL"
//								&& ded.clave == "006"
//								&& ded.incapacidad != null) {
//								s.deducciones.push(ded);
//								alert("Deduccion Agregada");
//								$scope.inputDeduc = {};
//								document.getElementById("forma").reset();
//								$scope.Cantidad = true;
//								$scope.Domingos = true;
//								$scope.Horas = true;
//								$scope.Porcentaje = true;
//								$scope.Incapacidad = true;
//							} else if (ded.clave == "010"
//								&& ded.modalidad != null
//								&& ded.valorDeCredito != null) {
//								s.deducciones.push(ded);
//								alert("Deduccion Agregada");
//								$scope.inputDeduc = {};
//								document.getElementById("forma").reset();
//								$scope.Cantidad = true;
//								$scope.Domingos = true;
//								$scope.Horas = true;
//								$scope.Porcentaje = true;
//								$scope.Incapacidad = true;
//							} else {
//								alert("Deduccion No Agregada");
//								$scope.inputDeduc = {};
//								document.getElementById("forma").reset();
//								$scope.Cantidad = true;
//								$scope.Domingos = true;
//								$scope.Horas = true;
//								$scope.Porcentaje = true;
//								$scope.Incapacidad = true;
//								$scope.Credito = true;
//							}
//
//						}
//						$scope.valordos = function(listdeducciones) {
//							var s = $scope.pagoEdita;
//							var ded = $scope.inputDeduc;
//							for (var i = 0; i < $scope.listdeducciones.length; i++) {
//								if ($scope.listdeducciones[i].clave == ded.clave) {
//									ded.descripcion = $scope.listdeducciones[i].descripcion
//
//									if (ded.clave == "001"
//										|| ded.clave == "002"
//										|| ded.clave == "003") {
//										$scope.Descuento = true;
//									$scope.Incapacidad = true;
//									$scope.Credito = true;
//									$scope.Porcentaje = true;
//									$scope.Ausencia = true;
//								} else if (ded.clave == "006") {
//									$scope.Porcentaje = true;
//									$scope.Incapacidad = false;
//									$scope.Credito = true;
//									$scope.Descuento = true;
//									$scope.Ausencia = true;
//								} else if (ded.clave == "007"
//									|| ded.clave == "019") {
//									$scope.Porcentaje = false;
//									$scope.Incapacidad = true;
//									$scope.Credito = true;
//									$scope.Descuento = true;
//									$scope.Ausencia = true;
//								} else if (ded.clave == "010") {
//									$scope.Descuento = true;
//									$scope.Incapacidad = true;
//									$scope.Credito = false;
//									$scope.Porcentaje = true;
//									$scope.Ausencia = true;
//								} else if (ded.clave == "020") {
//									$scope.Descuento = true;
//									$scope.Incapacidad = true;
//									$scope.Credito = true;
//									$scope.Porcentaje = true;
//									$scope.Ausencia = false;
//								} else {
//									$scope.Descuento = false;
//									$scope.Incapacidad = true;
//									$scope.Credito = true;
//									$scope.Porcentaje = true;
//									$scope.Ausencia = true;
//								}
//							}
//						}
//					}
					$scope.regresaEmpresa = function() {
						$location.path("/empresas/details/"
							+ $cookieStore.get('rfcEmpresa'));
					}
					$scope.irPagosRealizados = function() {
						$location.path("/empleados/pagosRealizados/"
							+ $routeParams.id);
					}
					$scope.eliminarPercepcion = function(per) {
						var index = $scope.pagoEdita.percepciones
						.indexOf(per);
						$scope.pagoEdita.percepciones.splice(index, 1);
					};
					$scope.eliminarDeduccion = function(ded) {
						var index = $scope.pagoEdita.deducciones
						.indexOf(ded);
						$scope.pagoEdita.deducciones.splice(index, 1);
					};
					$scope.recalcular = function() {
						empleadosService.recalcularPago(
							$scope.pagos.lista).then(
							function(data) {
								$scope.pagos.lista = data;
							})
						}
						$scope.guardarPagos = function() {
							$scope.procesando=true;
							empleadosService
							.guardarPago($scope.pagos.lista).then(
								function(data) {
									$scope.pagos.lista = data;
									$scope.procesando=false; 
									window.location.href = window.location.href
									$scope.terminado=true;
								},function(data){
									$scope.mensajeEjec="Error al timbrar";
//									alert("Error al timbrar nómina");
									$scope.procesando=false; 
									console.log(data);
									window.location.href = window.location.href
									$scope.terminado=true;
								});
						}
					$scope.terminado=false;
					$scope.mensajeEjec="¿Está seguro que desea ejecutar la nómina?;"
		}} ]);
app
.controller(
	'catalogosController',
	[
	'$scope',
	'$http',
	'$location',
	'$routeParams',
	'empleadosService',
	'$rootScope',
	'$cookies',
	'$cookieStore',
	'$window',
	function($scope, $http, $location, $routeParams,
		empleadosService, $rootScope, $cookies,
		$cookieStore, $window) {
		if(!$rootScope.variable){
			$location.path("/login");
		}else{
		console.log($cookieStore.get('rfcEmpresa'))
		$rootScope.nombreEmpresa;
		$scope.flags = [ false, false, false, false ];

		empleadosService.catalogosPercepciones().then(
			function(data) {
				$scope.percepciones = data;
				$scope.flags[0] = true;
			})
		empleadosService.catalogosDeducciones().then(
			function(data) {
				$scope.deducciones = data;
				$scope.flags[1] = true;
			})

		empleadosService
		.catalogosContabilidadPercepciones($cookieStore.get('rfcEmpresa'))
		.then(function(data) {
			$scope.percepcionesContabilidad = data;
			$scope.flags[2] = true;			
		})

		empleadosService.catalogosContabilidadDeducciones($cookieStore.get('rfcEmpresa'))
		.then(function(data) {
			$scope.deduccionesContabilidad = data;
			$scope.flags[3] = true;
		})				
		
		$scope
		.$watch(
			'flags',
			function() {
				if ($scope.flags[0]
					&& $scope.flags[1]
					&& $scope.flags[2]
					&& $scope.flags[3]) {
					$scope.tablacompleta = [];
				$scope.tablacompletaDed = [];

				for (var i = 0; i < $scope.percepcionesContabilidad.length; i++) {
					var renglon = {};
					renglon.percepcionCont = $scope.percepcionesContabilidad[i];
					for (var j = 0; j < $scope.percepciones.length; j++) {
						if (renglon.percepcionCont.llaveAgrupadora.raw.id == $scope.percepciones[j].id) {
							var found = $scope.percepciones[j];
							renglon.percepcionSat = found;
							break;
						}
					}
					$scope.tablacompleta
					.push(renglon);
				}
				for (var i = 0; i < $scope.deduccionesContabilidad.length; i++) {
					var renglonDed = {};
					renglonDed.deduccionCont = $scope.deduccionesContabilidad[i];
					for (var j = 0; j < $scope.deducciones.length; j++) {
						if (renglonDed.deduccionCont.llaveAgrupadora.raw.id == $scope.deducciones[j].id) {
							var found = $scope.deducciones[j];
							renglonDed.deduccionSat = found;
							break;
						}
					}
					$scope.tablacompletaDed
					.push(renglonDed);
				}
			}
		}, true);

		$scope.addPer = function(percepcion) {
			console.log(percepcion);
			empleadosService.catalogosPercepcionesAgregar(
				percepcion).then(function(percepcion) {
					alert("Percepcion Agregada");
					$window.location.reload();
				})
			}

			$scope.addPerCont = function(percepcion) {
				percepcion.claveAsociada = $scope.variable.clave;
				console.log(percepcion);
				var send = {
					cpTipoPercepcion : {
						clave : percepcion.clave,
						descripcion : percepcion.descripcion,
						rfcEmpresa : $cookieStore.get('rfcEmpresa')
					},
					idTipoSAT : percepcion.claveAsociada
				}
				console.log(send);
				empleadosService
				.catalogosPercepcionesAgregarCont(send)
				.then(function(send) {
					alert("Percepcion Agregada");
					$window.location.reload();
				})

			}
			$scope.addDed = function(deduccion) {
				console.log(deduccion);
				empleadosService.catalogosDeduccionesAgregar(
					deduccion).then(function(deduccion) {
						alert("Deduccion Agregada");
						$window.location.reload();
					})
				}
				$scope.addDedCont = function(deduccion) {
					deduccion.claveAsociada = $scope.variable.clave;
					console.log(deduccion);
					var send = {
						cpTipoDeduccion : {
							clave : deduccion.clave,
							descripcion : deduccion.descripcion,
							rfcEmpresa : $cookieStore.get('rfcEmpresa')
						},
						idTipoSAT : deduccion.claveAsociada
					}
					console.log(send);
					empleadosService
					.catalogosDeduccionesAgregarCont(send)
					.then(function(send) {
						alert("Deduccion Agregada");
						$window.location.reload();
					})

				}

				$scope.btnMod = function(percep) {
					$scope.variable = percep;
					$scope.variable.idAsociado = percep.llaveAgrupadora.raw.id
					+ "";
				}
				$scope.modPer = function(variable) {
					variable.llaveAgrupadora.raw.id = variable.idAsociado;
					console.log(variable);

					empleadosService.updatePer(variable).then(
						function(data) {
							$scope.percepciones = data;

							alert("Percepcion Modificada");
							$window.location.reload();
						});

				}
				$scope.btnModDed = function(dedu) {
					$scope.variable = dedu;
					$scope.variable.idAsociado = dedu.llaveAgrupadora.raw.id
					+ "";
				}
				$scope.modDed = function(variable) {
					variable.llaveAgrupadora.raw.id = variable.idAsociado;
					console.log(variable);

					empleadosService.updateDed(variable).then(
						function(data) {
							$scope.deducciones = data;

							alert("Deduccion Modificada");
							$window.location.reload();
						});

				}
				$scope.modPerCont = function(variable) {
					console.log(variable);
					var send = {
						cpTipoPercepcion : {
							id : variable.id,
							clave : variable.clave,
							descripcion : variable.descripcion,
							rfcEmpresa : $cookieStore.get('rfcEmpresa')
						},
						idTipoSAT : variable.idAsociado
					}
					console.log(send);
					empleadosService
					.updatePerCont(send)
					.then(
						function(data) {
							$scope.percepcionesContabilidad = data;

							alert("Percepcion Modificada");
							$window.location.reload();
						});

				}

				$scope.modDedCont = function(variable) {
					console.log(variable);
					var send = {
						cpTipoDeduccion : {
							id : variable.id,
							clave : variable.clave,
							descripcion : variable.descripcion,
							rfcEmpresa : $cookieStore.get('rfcEmpresa')
						},
						idTipoSAT : variable.idAsociado
					}
					console.log(send);
					empleadosService
					.updateDedCont(send)
					.then(
						function(data) {
							$scope.deduccionesContabilidad = data;

							alert("Deduccion Modificada");
							$window.location.reload();
						});

				}

				$scope.btnModPerSat = function(percep) {
					$scope.variable = percep;
				}
				$scope.modPerSat = function(variable) {
					console.log(variable);
					var send = {
							id : variable.id,
							clave : variable.clave,
							descripcion : variable.descripcion						
					}
					console.log(send);
					empleadosService
					.updatePer(send)
					.then(
						function(data) {
							$scope.percepciones = data;

							alert("Percepcion Modificada");
							$window.location.reload();
						});

				}
				
				$scope.btnModDedSat = function(dedu) {
					$scope.variable = dedu;
				}
				$scope.modDedSat = function(variable) {
					console.log(variable);
					var send = {
							id : variable.id,
							clave : variable.clave,
							descripcion : variable.descripcion						
					}
					console.log(send);
					empleadosService
					.updateDed(send)
					.then(
						function(data) {
							$scope.deducciones = data;

							alert("Deduccion Modificada");
							$window.location.reload();
						});

				}
				
							/////////////////////////////////////////////////////////////////////////////////////
							$scope.regresaEmpresa = function() {
								$location.path("/empresas/details/"
									+ $cookieStore.get('rfcEmpresa'));
							}
						}}]);
app
.controller(
	"pagosControllerGuardar",
	[
	'$scope',
	'$http',
	'$location',
	'$routeParams',
	'empleadosService',
	'$rootScope',
	'$cookies',
	'$cookieStore',
	function($scope, $http, $location, $routeParams,
			empleadosService, $rootScope, $cookies,
			$cookieStore) {
		if(!$rootScope.variable){
			$location.path("/login");
		}else{
			$rootScope.nombreEmpresa;
			$scope.pagos = {
				lista : []
			}
		var send={
				idEsquema : $cookieStore.get('idEsquema')
		}
//		var send={
//				idEsquema : $routeParams.id
//		}
			
		$scope.calcularPagoRegimen = function() {
			empleadosService
			.calcularPagoRegimen($routeParams.id).then(
				function(data) {
					$scope.pagos.lista = data;
					if(data.length==0){
						alert("No hay pagos Guardados");
					}
					console.log(data);
				})
		}
			$scope.calcularPagoRegimen();
	}}]);

app
.controller(
	"infonavitController",
	[
	'$scope',
	'$http',
	'$location',
	'$routeParams',
	'empleadosService',
	'$rootScope',
	'$cookies',
	'$cookieStore',
	function($scope, $http, $location, $routeParams,
			empleadosService, $rootScope, $cookies,
			$cookieStore) {
		if(!$rootScope.variable){
			$location.path("/login");
		}else{
			$rootScope.nombreEmpresa;
			
			$scope.periodosInfonavit = [ {
				
				"descripcion" : "",
			}, {
				"descripcion" : "Bimestral",
			}, {
				"descripcion" : "Mensual",
			}, {
				"descripcion" : "Quincenal",
			}, {
				"descripcion" : "Decenal",
			}, {
				"descripcion" : "Semanal",
			}, ]

			$scope.periodos = function() {	
				console.log($scope.infonavit.periodos);
				var send={
						rfcEmpresa: $routeParams.rfc,
						periodo : $scope.infonavit.periodos
				}
				
				console.log(send.rfcEmpresa);
				empleadosService
				.calcularInfonavit(send).then(
					function(data) {
						$scope.infonavit.monto = data;
						console.log(data);
					})
			}
			$scope.regresaEmpresa = function() {
				$location.path("/empresas/details/"
					+ $rootScope.rfc);
			}
	}}]);


