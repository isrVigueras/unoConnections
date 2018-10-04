var app = angular.module("app", [ 'ngRoute', 'ngCookies' ]);
app.config([ '$routeProvider', function($routeProvider) {

	$routeProvider.when('/empresas', {
		templateUrl : "pages/empresas.html",
		controller : "empresasController"
	});

	$routeProvider.when('/esquemas/agregar/:rfc', {
		templateUrl : "pages/esquemas.html",
		controller : "esquemasController"
	});

	$routeProvider.when('/empleados', {
		templateUrl : "pages/empleadosList.html",
		controller : "empleadosController"
	});

	$routeProvider.when('/altausuarios', {
		templateUrl : "pages/altausuarios.html",
	/* controller: "usuariosController" */
	});

	$routeProvider.when('/empresas/list', {
		templateUrl : "pages/empresasList.html",
		controller : "empresasListController"
	});

	$routeProvider.when('/cfdi', {
		templateUrl : "pages/cfdi.html",
		controller : "cfdiController"
	});

	$routeProvider.when('/cfdisinqr', {
		templateUrl : "pages/cfdisinqr.html",
		controller : "cfdiController"
	});

	$routeProvider.when('/empresas/edit/:rfc', {
		templateUrl : "pages/empresasEdit.html",
		controller : "empresasEditController"
	});

	$routeProvider.when('/empresas/details/:rfc', {
		templateUrl : "pages/empresasDetails.html",
		controller : "empresasDetailsController"
	});

	$routeProvider.when('/empresas/empleados/:rfc', {
		templateUrl : "pages/empleados.html",
		controller : "empresasEmpleadosController"
	});

	$routeProvider.when('/esquemas/details/:id', {
		templateUrl : "pages/esquemasDetails.html",
		controller : "esquemasDetailsController"
	});

	$routeProvider.when('/esquemas/edit/:id', {
		templateUrl : "pages/esquemasEdita.html",
		controller : "esquemasEditController"
	});

	$routeProvider.when('/empleados/list/:rfc', {
		templateUrl : "pages/empleadosList.html",
		controller : "empleadosListController"
	});

	$routeProvider.when('/empleados/add/:id', {
		templateUrl : "pages/empleados.html",
		controller : "empleadosController"
	});

	$routeProvider.when('/empleados/esquemas/:id', {
		templateUrl : "pages/empleadosList.html",
		controller : "empleadosEsquemasController"
	});

	$routeProvider.when('/empleados/details/:id', {
		templateUrl : "pages/empleadosDetails.html",
		controller : "empleadosDetailsController"
	});

	$routeProvider.when('/empleados/details/rfc/:id', {
		templateUrl : "pages/empleadosDetails.html",
		controller : "empleadosDetailsRfcController"
	});

	$routeProvider.when('/empleados/edit/:id', {
		templateUrl : "pages/empleadosEdit.html",
		controller : "empleadosDetailsController"
	});

	$routeProvider.when('/empleados/edit/rfc/:id', {
		templateUrl : "pages/empleadosEdit.html",
		controller : "empleadosDetailsRfcController"
	});

	$routeProvider.when('/empleados/pago/:id', {
		templateUrl : "pages/pagos.html",
		controller : "pagosController"
	});
	
	$routeProvider.when('/empleados/pagosRealizados/:id', {
		templateUrl : "pages/pagosRealizados.html",
		controller : "pagosControllerGuardar"
	});

	$routeProvider.when('/catalogos/:rfc', {
		templateUrl : "pages/catalogos.html",
		controller : "catalogosController"
	});

	$routeProvider.when('/catalogosSat/:rfc', {
		templateUrl : "pages/catalogosSat.html",
		controller : "catalogosController"
	});	
	
	$routeProvider.when('/empleados/infonavit/:rfc', {
		templateUrl : "pages/aportacionInfonavit.html",
		controller : "infonavitController"
	});

	$routeProvider.when('/login', {
		templateUrl : "pages/login.html",
		controller : "navigation"
	});

	$routeProvider.when('/altaperfiles', {
		templateUrl : "pages/altaperfil.html",
		controller : "perfilController"
	})

	$routeProvider.when('/modificarperfiles', {
		templateUrl : "pages/modificaperfiles.html",
		controller : "controladorListaPerfiles"
	})

	$routeProvider.when('/altausuarios', {
		templateUrl : "pages/altausuario.html",
		controller : "usuarioController"
	})

	$routeProvider.when('/modificarusuarios', {
		templateUrl : "pages/modificausuarios.html",
		controller : "controladorListaUsuarios"
	})

	$routeProvider.when('/resetPass', {
		templateUrl : "pages/resetpass.html",
		controller : "controladorReset"
	})

	$routeProvider.when('/bitacora', {
		templateUrl : "pages/bitacora.html",
		controller : "bitacoraController"
	});

	$routeProvider.when('/archivo', {
		templateUrl : "pages/archivo.html",
		controller : "archivoController"
	});
	
	$routeProvider.when('/clientesList/:rfc', {
		templateUrl : "pages/clientesList.html",
		controller : "clientesListController"
	});
	
	$routeProvider.when('/certificado', {
		templateUrl : "pages/certificado.html",
		controller : "cerController"
	});
	
	$routeProvider.when('/clientesEdit/:rfc/:ind', {
		templateUrl : "pages/clientesEdita.html",
		controller : "editaClienteController"
	});
	
	$routeProvider.when('/conceptosEdit/:rfc/:ind', {
		templateUrl : "pages/editaConceptos.html",
		controller : "conceptosEditController"
	});
	
	$routeProvider.when('/altaClientes/:rfc', {
		templateUrl : "pages/altaClientes.html",
		controller : "clientesController"
	});
	
	$routeProvider.when('/conceptos/:rfc', {
		templateUrl : "pages/conceptos.html",
		controller : "conceptosController"
	});
	
	$routeProvider.when('/conceptosList/:rfc', {
		templateUrl : "pages/conceptosList.html",
		controller : "conceptosListController"
	});
	
	$routeProvider.when('/facturacion/:rfc', {
		templateUrl : "facturacion/pages/comprobanteF32.html",
		controller : "comprobante"
	});
	
	$routeProvider.when('/facturacion33/:rfc', {
		templateUrl : "facturacion/pages/comprobanteF33.html",
		controller : "comprobante33"
	});
	
	$routeProvider.when('/listFacturas', {
		templateUrl : "facturacion/pages/listFacturas.html",
		controller : "facturaListController"
	});
	
	$routeProvider.when('/listComplementos', {
		templateUrl : "facturacion/pages/listComplementos.html",
		controller : "complementoListController"
	});
	
	
	$routeProvider.when('/editaFactura/:rfc/:uuid', {
		templateUrl : "facturacion/pages/comprobanteF32.html",
		controller : "facturaEditController"
	});
	
	$routeProvider.when('/editaFactura33/:rfc/:uuid', {
		templateUrl : "facturacion/pages/comprobanteF33.html",
		controller : "facturaEditController33"
	});
	
	$routeProvider.when('/facturacionMultiple', {
		templateUrl : "facturacion/pages/multiple.html",
		controller : "archivoController"
	});
	
	$routeProvider.when('/reporte/', {
		templateUrl : "pages/reporte.html",
		controller : "reporteController"
	});

	$routeProvider.when('/altaSerie/:rfc', {
		templateUrl : "pages/altaSerial.html",
		controller : "altaSerieController"
	});
	
	//SerialList/
	$routeProvider.when('/SerialList/:rfc', {
		templateUrl : "pages/serialList.html",
		controller : "serialListController"
	});
	
	$routeProvider.when('/serialEdit/:rfc/:ind', {
		templateUrl : "pages/editaSerial.html",
		controller : "serieEditController"
	});
	
	$routeProvider.when('/listComplementos', {
		templateUrl : "facturacion/pages/listComplementos.html",
		controller : "listComplementosController"
	});
	
	$routeProvider.when('/complementoManual', {
		templateUrl : "facturacion/pages/complemento33.html",
		controller : "complementoController"
	});
	
	//altaImagen#/listComplementos
	
	$routeProvider.when('/altaImagen', {
		templateUrl : "pages/altaImagen.html",
		controller : "imagenController"
	});
	
	$routeProvider.otherwise({
		redirectTo : '/empresas/list'
	});

	
} ]);

app.service('sessionService', [
		'$rootScope',
		'$http',
		'$location',
		'$q',
		function($rootScope, $http, $location, $q) {
			this.authenticate = function(credentials, callback) {

				var headers = credentials ? {
					authorization : "Basic"
							+ btoa(credentials.username + ":"
									+ credentials.password)
				} : {};
				$http.get('user', {
					headers : headers
				}).success(function(data) {
					if (data.usuario) {
						$rootScope.authenticated = true;
						$rootScope.variable = true;
						$rootScope.cargarEmpresasHeader();
						$location.path("/empresas/list");
					} else {
						$rootScope.authenticated = false;
					}
				}).error(function(data) {
					alert("Usuario o Contraseña incorrectos");
					$location.path("/login");
				});
			}
			
			this.isAuthenticated = function() {
				var d = $q.defer();
				$http.get("currentSession").success(function(data) {
					$rootScope.authenticated = true;
					d.resolve(data);
				}).error(function(data) {
					$location.path("/login");
				});
				return d.promise;
			}
		} ]);

app.controller('navigation', [ 'sessionService', '$rootScope', '$scope',
		'$http', '$location',

		function(sessionService, $rootScope, $scope, $http, $location) {

			$scope.credentials = {};
			$scope.login = function() {
				sessionService.authenticate($scope.credentials, function() {
					if ($rootScope.authenticated) {
						$scope.error = false;
						$location.path("/");
					} else {
						$location.path("/login");
						$scope.error = true;
					}
				});
			};
		} ]);