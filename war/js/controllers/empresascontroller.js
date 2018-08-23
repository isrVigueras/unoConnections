app.service('empresasService', [ '$http', '$q', function($http, $q) {
	this.add = function(newEmp) {/* Agrega elementos a arreglo Empresa */

	};
	this.find = function(rfc) {
		var d= $q.defer();
		$http.get("/empresas/find/"+rfc).then(function(response){
				d.resolve(response.data);
		},function(response){});
		return d.promise;
	};
	
	this.update = function(empresa) {
		var d = $q.defer();
		$http.post("/empresas/update/" ,empresa).then(function(response) {
			console.log(response);
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	};
	this.findTipo = function() {
		var d = $q.defer();
		$http.get("/tipo/getList").then(function(response) {
			console.log(response);
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	};
	this.findDivision = function(tipoId) {
		var d = $q.defer();
		$http.get("/division/getLista/"+tipoId).then(function(response) {
			console.log(response);
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	};
	this.findGrupo = function(grupoClave) {
		var d = $q.defer();
		$http.get("/grupo/getLista/"+grupoClave).then(function(response) {
			console.log(response);
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	};
	this.findClase = function(claseClave) {
		var d = $q.defer();
		$http.get("/clase/getLista/"+claseClave).then(function(response) {
			console.log(response);
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	}
	this.findProductos_Servicios = function(claseClave) {
		var d = $q.defer();
		$http.get("/producto_servicio/getLista/"+claseClave).then(function(response) {
			console.log(response);
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	}
	this.clean = function() {/* Limpia o Elimina todos los elementos */
	};
	this.getAll = function() {/* Muestra todos los Elementos */
	};
	this.removeItem = function(item) {/* Elimina elemento por elemeto */
	}
	
} ]);
app.controller("empresasController", [
		'$scope',
		'$http',
		'$location',
		'empresasService',
		'$rootScope',
		function($scope, $http, $location, empresasService, $rootScope) {
			empresasService.findTipo().then(function(data){
				$scope.tipos=data;
			})
			$scope.Txt=true;
			if(!$rootScope.variable){
				$location.path("/login");
			}else{
			
			$scope.addEmp = function() {
				var send={
						empresa: $scope.newEmp,
						productoServicio: $scope.productos_servicios,
				}
				$http.post("/empresas/add", send).then(
						function(response) {
							alert("Empresa Guardada");
							$location.path("/empresas/list");
						}, function(response) {
							if(response.status==403){
								alert("No tiene permisos para realizar esta acción");
								$location.path("/");
							}							
						});
			}
			$scope.removeEmp = function(item) {
			}

			$scope.clean = function() {
				document.getElementById("forma").reset();
				//$scope.newEmp.regimen="";
				$scope.newEmp.direccion.estado="";
			}
			$scope.valor = function(tipo){
				console.log(tipo);	
				$scope.busqueda.division=[];
				$scope.busqueda.grupo=[];
				$scope.busqueda.clase=[];
				
				empresasService.findDivision(tipo.id).then(function(data){
					$scope.divisiones=data;
				})
			}
			$scope.valordos = function(division){
				console.log(division);	
				$scope.busqueda.grupo=[];
				$scope.busqueda.clase=[];
				
				empresasService.findGrupo(division[0]).then(function(data){
					$scope.grupos=data;
				})
			}
			$scope.valortres = function(grupo){
				console.log(grupo);		
				$scope.busqueda.clase=[];
				
				
				empresasService.findClase(grupo[0]).then(function(data){
					$scope.clases=data;
				})
			}
			$scope.buscar = function(){
				console.log($scope.busqueda.clase);
				if($scope.busqueda.tipo==undefined || $scope.busqueda.division==undefined || $scope.busqueda.grupo==undefined){					
					alert("Para evitar grandes cantidades de resultados favor de seleccionar información de al menos 3 listas");
				}else{	
					empresasService.findProductos_Servicios($scope.busqueda.clase[0]).then(function(data){
						$scope.productos_servicios=data;
					})
					$scope.Txt=false;
				}
				
			}
			$scope.busqueda={};
			$scope.newEmp={emails:[]}
		} }]);

app.controller("empresasEditController", [
		'$scope',
		'$http',
		'$location',
		'$routeParams',
		'empresasService',
		'$rootScope',
		function($scope, $http, $location, $routeParams, empresasService,$rootScope) {	
			empresasService.findTipo().then(function(data){
				$scope.tipos=data;
			})
			$scope.Txt=true;
			if(!$rootScope.variable){
				$location.path("/login");
			}else{
			$http.get("empresas/find/" + $routeParams.rfc).then(function(response) {
						$scope.newEmp = response.data[0];
						if(!$scope.newEmp.emails){
							$scope.newEmp.emails=[];
						}
						console.log(response);
					}, function(response) {
						alert("something went wrong");
						console.log(response);
					});
			  
			/*$scope.addEmp = function() {
				$http.post("/empresas/add", $scope.newEmp).then(
						function(response) {
							alert("Empresa Guardada");
							$location.path("/empresas/list");
						}, function(response) {
							alert("Something went wrong!");
							console.log(response);
						});
			}*/
			$scope.update = function() {
				var send={
						empresa: $scope.newEmp,
						productoServicio: $scope.productos_servicios,
				}
				empresasService.update(send).then(function(data) {
					$scope.newEmp = data;
					
					alert("Empresas Modificada correctamente");
					$location.path("/empresas/list");
				}).then(function(response){
					if(response.status==403){
						alert("No tiene permisos para realizar esta acción");
						$location.path("/");
					}		
				});
			}

			$scope.ver = function(item) {
				$location.path("/empresas/details/" + item.RFC);
			}

			$scope.clean = function() {
				document.getElementById("forma").reset();
				$scope.newEmp.regimen="";
				$scope.newEmp.direccion.estado="";
			}
			$scope.valor = function(tipo){
				console.log(tipo);	
				$scope.busqueda.division=[];
				$scope.busqueda.grupo=[];
				$scope.busqueda.clase=[];
				
				empresasService.findDivision(tipo.id).then(function(data){
					$scope.divisiones=data;
				})
			}
			$scope.valordos = function(division){
				console.log(division);	
				$scope.busqueda.grupo=[];
				$scope.busqueda.clase=[];
				
				empresasService.findGrupo(division[0]).then(function(data){
					$scope.grupos=data;
				})
			}
			$scope.valortres = function(grupo){
				console.log(grupo);		
				$scope.busqueda.clase=[];
				
				
				empresasService.findClase(grupo[0]).then(function(data){
					$scope.clases=data;
				})
			}
			$scope.buscar = function(){
				console.log($scope.busqueda.clase);
				if($scope.busqueda.tipo==undefined || $scope.busqueda.division==undefined || $scope.busqueda.grupo==undefined){					
					alert("Para evitar grandes cantidades de resultados favor de seleccionar información de al menos 3 listas");
				}else{					
					$scope.Txt=false;
					empresasService.findProductos_Servicios($scope.busqueda.clase[0]).then(function(data){
						$scope.productos_servicios=data;
					})
				}
				
			}
			$scope.busqueda={};
		}} ]);

app.controller("empresasDetailsController", [ '$scope', '$http', '$location',
		'$routeParams', 'empresasService','$rootScope','$cookies','$cookieStore',
		function($scope, $http, $location, $routeParams, empresasService, $rootScope, $cookies, $cookieStore) {
			if(!$rootScope.variable){
				$location.path("/login");
			}else{
			if($routeParams.rfc=="undefined"){
				$location.path("/empresas/details/"+$cookieStore.get('rfcEmpresa'));		
			}
	
			empresasService.find($routeParams.rfc).then(function(data){
				$rootScope.empresaActual=$routeParams.rfc;
				$rootScope.regimenesRoot= data[0].regimenesVO;
				$scope.empresa=data[0];
				$rootScope.rfc=$scope.empresa.RFC;
				$rootScope.nombreEmpresa=$scope.empresa.nombre;
				$cookies.rfcEmpresa=$rootScope.rfc;
				$cookieStore.put('rfcEmpresa',$scope.empresa.RFC);
				$cookieStore.put('nombreEmpresa',$scope.empresa.nombre);
				$scope.regimenes=data[1];
//				console.log($scope.regimenes);
			});
			
			$scope.addEsquema=function(){
				$location.path("/esquemas/agregar/"+$scope.empresa.RFC);
			}
			$scope.verRegimen=function(id){
				$location.path("/esquemas/details/"+id);
			}			
			$scope.editarRegimen=function(id){
				$location.path("/esquemas/edit/"+id);
			}				
			$scope.irEmpleados=function(){
				$location.path("/empleados/list/"+$scope.empresa.RFC);	
			}
			$scope.irCatalogos=function(){
				$location.path("/catalogos/"+$scope.empresa.RFC);	
			}
			$scope.irCatalogosSat=function(){
				$location.path("/catalogosSat/"+$scope.empresa.RFC);	
			}
			$scope.calcularPago=function(id){
				$location.path("/empleados/pago/"+id);
			}
			$scope.irInfonavit=function(){
				$location.path("/empleados/infonavit/"+$scope.empresa.RFC);
			}
		}}]);

app.controller("empresasEmpleadosController", [ '$scope', '$http', '$location',
                                      		'$routeParams', 'empresasService','$rootScope',
                                      		function($scope, $http, $location, $routeParams, empresasService, $rootScope) {
									if(!$rootScope.variable){
										$location.path("/login");
									}else{
									$scope.byEmpresa=true;
									empresasService.find($routeParams.rfc).then(function(data){
										$scope.empresa=data[0];
										$rootScope.rfc=$scope.empresa.RFC;
										$scope.regimenes=data[1];
										console.log($scope.regimenes);
										
										$scope.regresaEmpleados = function() {
											$location.path("/empleados/list/" + $rootScope.rfc);	
										}

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
									$scope.addEmp=function(){
										var cBanco = $("#claveBanco option:selected").text();
										var nBanco = $("#nombreBanco option:selected").text();
										var send={
												empleado:$scope.newEmp,
												idEmpresa:$scope.idEmpresa,
												claveBanco:cBanco,
												nombreBanco:nBanco
										}										
										console.log(send);
										$http.post("/employee/add", send).then(function(response) {
											alert("Empleado Guardado");
											$location.path("/empleados/list/"+$rootScope.rfc);//Flow
											console.log(response.data);
										}, function(response) {
											alert("Something went wrong!");
											console.log(response);
										});
									}
									$scope.clean = function() {
										document.getElementById("forma").reset();
										$scope.idEmpresa="";
									}
									$scope.newEmp={fechaDeContratacion:new Date()};
								
									
}}]);
