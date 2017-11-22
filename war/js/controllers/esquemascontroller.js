app.service('esquemasService', [ '$http', '$q', function($http, $q) {
	this.getEmpresa = function(rfc) {
		var d = $q.defer();
		$http.get("/empresas/find/" + rfc).then(function(response) {
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	}

	this.get = function(id) {
		var d = $q.defer();
		$http.get("/esquemas/find/" + id).then(function(response) {
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	}

	this.saveEmpresa = function(empr) {
		var d = $q.defer();
		$http.post("/esquemas/add", empr).then(function(response) {
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	}

	this.save = function(empr) {
		var d = $q.defer();
		$http.post("/esquemas/update", empr).then(function(response) {
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	}
	this.update = function(esquema) {
		var d = $q.defer();
		$http.post("/esquemas/update/" ,esquema).then(function(response) {
			console.log(response);
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	};	
	
	this.elimina = function(id) {
		var d = $q.defer();
		$http.post("/regimen/delete/" ,+id).then(function(response) {
			console.log(response);
			d.resolve(response.data);
		}, function(response) {
		});
		return d.promise;
	};	
} ]);
app.controller("esquemasDetailsController", [ '$scope', '$routeParams',
		'$location', 'esquemasService', "$rootScope", '$cookies',
		'$cookieStore',
		function($scope, $routeParams, $location, esquemasService, $rootScope, $cookies, $cookieStore) {
			esquemasService.get($routeParams.id).then(function(data) {
				$scope.regimen = data;
				console.log(data);
				$rootScope.nombreEmpresa;
				$rootScope.id=$scope.regimen.id;
				
				$scope.regresaEmpresa = function() {
					$location.path("/empresas/details/" + $cookieStore.get('rfcEmpresa'));
				}
				$scope.irEmpleados = function() {
					$location.path("/empleados/esquemas/" + $routeParams.id);

				}
			})
		} ]);

app.controller(
				"esquemasEditController",
				[
						'$scope',
						'$routeParams',
						'$location',
						'esquemasService',
						'empleadosService',
						'$rootScope',
						'$cookies',
						'$cookieStore',
						function($scope, $routeParams, $location,
								esquemasService,empleadosService,$rootScope, $cookies, $cookieStore) {
							$rootScope.nombreEmpresa;
							
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
							
							$scope.Horas = true;
							$scope.Semanal = true;
							$scope.Quincenal = true;
							$scope.Decenal = true;
							$scope.Mensual = true;
							
							$scope.Credito= true;
							$scope.Descuento = true;
							$scope.Incapacidad = true;
							$scope.Ausencia = true;
							$scope.Mensaje = true;
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
//borre hard
							$scope.listtipos = [
									{
										"dias" : "",
										"descripcion" : "",
									},
									{
										"clave" : "2",
										"descripcion" : "Sueldos",
									},
									{
										"clave" : "3",
										"descripcion" : "Jubilados",
									},
									{
										"clave" : "4",
										"descripcion" : "Pensionados",
									},
									{
										"clave" : "5",
										"descripcion" : "Asimilados a Miembros Sociedades Cooperativas Producción",
									},
									{
										"clave" : "6",
										"descripcion" : "Asimilados Integreantes Sociedades Asociaciones Civiles",
									},
									{
										"clave" : "7",
										"descripcion" : "Asimilados Miembros consejos ",
									},
									{
										"clave" : "8",
										"descripcion" : "Asimilados comisionistas",
									},
									{
										"clave" : "9",
										"descripcion" : "Asimilados Honorarios",
									},
									{
										"clave" : "10",
										"descripcion" : "Asimilados acciones",
									},
									{
										"clave" : "11",
										"descripcion" : "Asimilados otros",
									},
									{
										"clave" : "99",
										"descripcion" : "Otro Regimen",
									},]
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
							},{
								"clave" : "4",
								"descripcion" : "Veces UMA",
							}, ]
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
									
//							$scope.catalPer= function(catalogos){
//								var cat = $scope.tipocat;
//								if(cat=="Sat"){
//									$scope.ClaveSat=false;
//									$scope.DescripcionSat=false;
//									$scope.ClaveEmpresa=true;
//									$scope.DescripcionEmpresa=true;
//									$scope.IsShow=true;
//								}
//								if(cat=="Propias de la empresa"){
//									$scope.ClaveSat=true;
//									$scope.DescripcionSat=true;
//									$scope.ClaveEmpresa=false;
//									$scope.DescripcionEmpresa=false;
//									$scope.IsShow=true;
//								}
//							}
							
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
							$scope.regimen = {
								nombre : "",
								percepciones : [],
								deducciones : [],
								diasDePago:[]
							};

							// esquemasService.getEmpresa($routeParams.rfc).then(
							// function(data) {
							// $scope.empresa = data[0];
							// });
							$scope.newEsq = {};
							$scope.newDed = {};
							$scope.listaEsq = [];
							
							$scope.addPer = function(inputPerc) {
								var dSat = $("#descripcionSat option:selected").text();
								var cSat = $("#claveSat option:selected").text();							
								var dEmp = $("#descripcionEmpresa option:selected").text();
								var cEmp = $("#claveEmpresa option:selected").text();			
								
								
								var esq = {
										clave : cEmp,
										descripcion : dEmp.trim(),
										cantidad : inputPerc.cantidad,
										tipo : cSat
								}
								console.log(esq);
								console.log(inputPerc);
								// ToDoService.add($scope.newEsq);
//								var esq = $scope.inputPerc;
//								for (var i = 0; i < $scope.listpercecpciones.length; i++) {
//									if ($scope.listpercecpciones[i].clave == esq.clave) {
//										esq.descripcion = $scope.listpercecpciones[i].descripcion
//									}
//								}								
								$scope.regimen.percepciones.push(esq);
								$scope.inputPerc = {};
							}
							$scope.valor = function(inputPerc){
								var val= $scope.inputPerc;
								console.log(val);
								if(val.clave=="002"||val.clave=="003" || val.clave=="005" || val.clave=="006" || val.clave=="009" || val.clave=="013" || val.clave=="014" || val.clave=="020" || val.clave=="021" || val.clave=="022" || val.clave=="023"|| val.clave=="024"|| val.clave=="025" || val.clave=="027" || val.clave=="037"  ){
									$scope.IsShow = true;
									$scope.Horas = true;
									$scope.Semanal = true;
									$scope.Quincenal = true;
									$scope.Decenal = true;
									$scope.Mensual = true;
								}else if(val.clave=="019"){
									$scope.IsShow = true;
									$scope.Horas = false;
									$scope.Semanal = false;
									$scope.Quincenal = false;
									$scope.Decenal = false;
									$scope.Mensual = false;
								}else{
									$scope.IsShow= false;
									$scope.Horas = true;
									$scope.Semanal = true;
									$scope.Quincenal = true;
									$scope.Decenal = true;
									$scope.Mensual = true;
								}
								
							}
												
							$scope.addDed = function(inputDeduc) {
								var dSat = $("#descripcionSatDed option:selected").text();
								var cSat = $("#claveSatDed option:selected").text();							
								var dEmp = $("#descripcionEmpresaDed option:selected").text();
								var cEmp = $("#claveEmpresaDed option:selected").text();											
								
								var esq = {
										clave : cEmp,
										descripcion : dEmp.trim(),
										descuento : $scope.inputDeduc.descuento,
										tipo : cSat
										
								}
								console.log(esq);
								
								$scope.regimen.deducciones.push(esq);
								$scope.inputDeduc = {};
							}
							
							$scope.valordos = function(inputDeduc) {								
								var ded = $scope.inputDeduc;								
										
										if (ded.clave=="001" || ded.clave=="002" || ded.clave=="003"){											
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

							esquemasService.get($routeParams.id).then(
									function(data) {
										$scope.regimen = data;
										console.log(data);
										if (!$scope.regimen.percepciones) {
											$scope.regimen.percepciones = [];
										}

										if (!$scope.regimen.deducciones) {
											$scope.regimen.deducciones = [];
										}
									});
							$scope.showFormPer = false;
							$scope.showPer = function() {
								$scope.showFormPer = true;
							}
							$scope.showDed = function() {
								$scope.showFormPer = false;
							}
							
							$scope.disableDay=[true,true,true,true];
							$scope.ejecutar = function(j) {
								$scope.regimen.diasDePago=[];
								
								for (var i = 0; i < 4; i++) {
									$scope.disableDay[i] = true;
								}
								for (var i = 0; i < j; i++) {
									$scope.disableDay[i] = false;
									$scope.regimen.diasDePago[i]="";
								}
								
							};

							$scope.save = function(reg) {
								// if (!empr.regimenes) {
								// empr.regimenes = []
								// }
								// var send = {
								// empresa : empr.RFC,
								// regimen : $scope.regimen,
								// tipo : $scope.regimen.tipoRegimen
								// }

								esquemasService.save(reg).then(function(data) {
									console.log(data);
									alert("Guardado con éxito");
									$location.path("/empresas/list")
								})

							};
							$scope.eliminarPercepcion = function(per) {
								var index= $scope.regimen.percepciones.indexOf(per);
								$scope.regimen.percepciones.splice(index,1);
							};
							$scope.eliminarDeduccion = function(ded) {
								var index= $scope.regimen.deducciones.indexOf(ded);
								$scope.regimen.deducciones.splice(index,1);
							};
							
							$scope.update = function() {		
								console.log($scope.regimen);
								console.log($scope.regimen.diasDePago);
								esquemasService.update($scope.regimen).then(function(data) {
									$scope.regimen= data;
									
									alert("Esquema Modificado correctamentes");
									$location.path("/empresas/details/"+$rootScope.rfc);
								});
							}
							
							$scope.regresaEmpresa = function() {
								$location.path("/empresas/details/" +$cookieStore.get('rfcEmpresa'));
							}
							$scope.clean = function() {
								document.getElementById("forma").reset();
							}
							$scope.diaspagos = [ {
								"dia" : "",
							},{								
								"dia" : "1",
							}, {
								"dia" : "2",
							}, {
								"dia" : "3",
							}, {
								"dia" : "4",
							}, {
								"dia" : "5",
							}, {
								"dia" : "6",
							}, {
								"dia" : "7",
							}, {
								"dia" : "8",
							}, {
								"dia" : "9",
							}, {
								"dia" : "10",
							}, {
								"dia" : "11",
							}, {
								"dia" : "12",
							}, {
								"dia" : "13",
							}, {
								"dia" : "14",
							}, {
								"dia" : "15",
							}, {
								"dia" : "16",
							}, {
								"dia" : "17",
							}, {
								"dia" : "18",
							}, {
								"dia" : "19",
							}, {
								"dia" : "20",
							}, {
								"dia" : "21",
							}, {
								"dia" : "22",
							}, {
								"dia" : "23",
							}, {
								"dia" : "24",
							}, {
								"dia" : "25",
							}, {
								"dia" : "26",
							}, {
								"dia" : "27",
							}, {
								"dia" : "28",
							}, {
								"dia" : "29",
							}, {
								"dia" : "30",
							}, {
								"dia" : "31",
							},]
						} ]);

app.controller(
				"esquemasController",
				[
						'$scope',
						'$routeParams',
						'$location',
						'$rootScope',
						'esquemasService',
						'empleadosService',
						'$cookies',
						'$cookieStore',
						function($scope, $routeParams, $location,$rootScope,
								esquemasService, empleadosService,$cookies,
								$cookieStore) {
							$rootScope.nombreEmpresa;
							
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
							
							$scope.Horas = true;
							$scope.Semanal = true;
							$scope.Quincenal = true;
							$scope.Decenal = true;
							$scope.Mensual = true;
							
							$scope.Credito= true;
							$scope.Descuento = true;
							$scope.Incapacidad = true;
							$scope.Ausencia = true;
							$scope.Mensaje = true;
							
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
//borre hard				
							$scope.riesgoPuestos = [
								{									
									"descripcion" : "",
								},
								{
									"descripcion" : "Clase I",
								},
								{
									"descripcion" : "Clase II",
								},
								{
									"descripcion" : "Clase III",
								},
								{
									"descripcion" : "Clase IV",
								},
								{
									"descripcion" : "Clase V",
								},]
							$scope.listtipos = [
									{
										"dias" : "",
										"descripcion" : "",
									},
									{
										"clave" : "2",
										"descripcion" : "Sueldos",
									},
									{
										"clave" : "3",
										"descripcion" : "Jubilados",
									},
									{
										"clave" : "4",
										"descripcion" : "Pensionados",
									},
									{
										"clave" : "5",
										"descripcion" : "Asimilados Miembros Sociedades Cooperativas de Producción",
									},
									{
										"clave" : "6",
										"descripcion" : "Asimilados Integreantes Sociedades Asociaciones Civiles",
									},
									{
										"clave" : "7",
										"descripcion" : "Asimilados Miembros consejos",
									},
									{
										"clave" : "8",
										"descripcion" : "Asimilados comisionistas",
									},
									{
										"clave" : "9",
										"descripcion" : "Asimilados Honorarios",
									},
									{
										"clave" : "10",
										"descripcion" : "Asimilados acciones",
									},
									{
										"clave" : "11",
										"descripcion" : "Asimilados otros",
									},
									{
										"clave" : "99",
										"descripcion" : "Otro Regimen",
									},]
							$scope.listpagos = [ {
								"dias" : "",
								"descripcion" : "",
							}, {
								"dias" : "7",
								"descripcion" : "Semanal",
							}, {
								"dias" : "10",
								"descripcion" : "Decenal",
							}, {
								"dias" : "15",
								"descripcion" : "Quincenal",
							}, {
								"dias" : "30",
								"descripcion" : "Mensual",
							}, ]
							$scope.diaspagos = [ {
								"dia" : "",
							},{								
								"dia" : "1",
							}, {
								"dia" : "2",
							}, {
								"dia" : "3",
							}, {
								"dia" : "4",
							}, {
								"dia" : "5",
							}, {
								"dia" : "6",
							}, {
								"dia" : "7",
							}, {
								"dia" : "8",
							}, {
								"dia" : "9",
							}, {
								"dia" : "10",
							}, {
								"dia" : "11",
							}, {
								"dia" : "12",
							}, {
								"dia" : "13",
							}, {
								"dia" : "14",
							}, {
								"dia" : "15",
							}, {
								"dia" : "16",
							}, {
								"dia" : "17",
							}, {
								"dia" : "18",
							}, {
								"dia" : "19",
							}, {
								"dia" : "20",
							}, {
								"dia" : "21",
							}, {
								"dia" : "22",
							}, {
								"dia" : "23",
							}, {
								"dia" : "24",
							}, {
								"dia" : "25",
							}, {
								"dia" : "26",
							}, {
								"dia" : "27",
							}, {
								"dia" : "28",
							}, {
								"dia" : "29",
							}, {
								"dia" : "30",
							}, {
								"dia" : "31",
							},]
							$scope.listTipoDescuento = [ {
								"clave" : "",
								"descripcion" : "",
							}, {
								"clave" : "1",
								"descripcion" : "Porcentaje",
							}, {
								"clave" : "2",
								"descripcion" : "Cuenta Fija",
							}, {
								"clave" : "3",
								"descripcion" : "Veces SMGV",
							},{
								"clave" : "4",
								"descripcion" : "Veces UMA",
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
									
//							$scope.catalPer= function(catalogos){
//								var cat = $scope.tipocat;
//								if(cat=="Sat"){
//									$scope.ClaveSat=false;
//									$scope.DescripcionSat=false;
//									$scope.ClaveEmpresa=true;
//									$scope.DescripcionEmpresa=true;
//									$scope.IsShow=true;
//								}
//								if(cat=="Propias de la empresa"){
//									$scope.ClaveSat=true;
//									$scope.DescripcionSat=true;
//									$scope.ClaveEmpresa=false;
//									$scope.DescripcionEmpresa=false;
//									$scope.IsShow=true;
//								}
//							}
							
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
							$scope.regimen = {
								nombre : "",
								percepciones : [],
								deducciones : [],
								diasDePago:[]
							};
							$scope.regresaEmpresa = function() {
								$location.path("/empresas/details/"
										+ $routeParams.rfc);
							}
							esquemasService.getEmpresa($routeParams.rfc).then(
									function(data) {
										$scope.empresa = data[0];
									});
							$scope.newEsq = {};
							$scope.newDed = {};
							$scope.listaEsq = [];
							
							$scope.addPer = function(inputPerc) {
								var cSat = $("#claveSat option:selected").text();							
								var dEmp = $("#descripcionEmpresa option:selected").text();
								var cEmp = $("#claveEmpresa option:selected").text();			
								
								if(cSat==""||dEmp==""||cEmp==""){
									alert("Faltan datos de la Percepción");
								}else{
									var esq = {
											clave : cEmp,
											descripcion : dEmp.trim(),
											cantidad : inputPerc.cantidad,
											tipo : cSat
									}
									console.log(esq);
									
								// 	ToDoService.add($scope.newEsq);
//									var esq = $scope.inputPerc;
//								for (var i = 0; i < $scope.listpercecpciones.length; i++) {
//									if ($scope.listpercecpciones[i].clave == esq.clave) {
//										esq.descripcion = $scope.listpercecpciones[i].descripcion
//									}
//								}								
									$scope.regimen.percepciones.push(esq);
									$scope.inputPerc = {};
								}
							}
							
//							$scope.valor = function(inputPerc){
//								console.log(inputPerc)
//								var esq = $scope.inputPerc;
//								for (var i = 0; i < $scope.percep.length; i++) {
//									if ($scope.percep[i].clave == esq.clave) {
//										esq.descripcion = $scope.listpercecpciones[i].descripcion
//										if(esq.clave=="002"||esq.clave=="003" || esq.clave=="017" || esq.clave=="021" || esq.clave=="027" || esq.clave=="039" || esq.clave=="042"){
//											$scope.IsShow = true;
//										}else{
//											$scope.IsShow= false;
//										}
//									}
//								}
//							}
							$scope.valor = function(inputPerc){
								var val= $scope.inputPerc;
								console.log(val);
								if(val.clave=="002"||val.clave=="003" || val.clave=="005" || val.clave=="006" || val.clave=="009" || val.clave=="014" || val.clave=="020" || val.clave=="021" || val.clave=="022" || val.clave=="023"|| val.clave=="024"|| val.clave=="025" || val.clave=="027" || val.clave=="037"  ){
									$scope.IsShow = true;
									$scope.Horas = true;
									$scope.Semanal = true;
									$scope.Quincenal = true;
									$scope.Decenal = true;
									$scope.Mensual = true;
								}else if(val.clave=="019"){
									$scope.IsShow = true;
									$scope.Horas = false;
									$scope.Semanal = false;
									$scope.Quincenal = false;
									$scope.Decenal = false;
									$scope.Mensual = false;
								}else{
									$scope.IsShow= false;
									$scope.Horas = true;
									$scope.Semanal = true;
									$scope.Quincenal = true;
									$scope.Decenal = true;
									$scope.Mensual = true;
								}
								
							}
							
							$scope.addDed = function(inputDeduc) {
								var cSat = $("#claveSatDed option:selected").text();							
								var dEmp = $("#descripcionEmpresaDed option:selected").text();
								var cEmp = $("#claveEmpresaDed option:selected").text();											
								
								if(cSat==""||dEmp==""||cEmp==""){
									alert("Faltan datos de la deducción");
								}else{
									var esq = {
											clave : cEmp,
											descripcion : dEmp.trim(),
											descuento : $scope.inputDeduc.descuento,
											tipo : cSat
											
									}
									console.log(esq);
									
									$scope.regimen.deducciones.push(esq);
									$scope.inputDeduc = {};
								}
							}
//								var ded = $scope.inputDeduc;
//								for (var i = 0; i < $scope.listdeducciones.length; i++) {
//									if ($scope.listdeducciones[i].clave == ded.clave) {
//										ded.descripcion = $scope.listdeducciones[i].descripcion
//										
//									}
//								}
//								$scope.regimen.deducciones.push(ded);
//								$scope.inputDeduc = {};
//							}
							$scope.valordos = function(inputDeduc) {								
								var ded = $scope.inputDeduc;								
										
										if (ded.clave=="001" || ded.clave=="002" || ded.clave=="003"){											
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


							$scope.save = function(empr) {
								if (!empr.regimenes) {
									empr.regimenes = []
								}
								var diasPago=$scope.regimen.diasDePago;
								var maxd= 0;
								if($scope.regimen.formaPago=="Semanal"){
									maxd=4;
								}
								if($scope.regimen.formaPago=="Decenal"){
									maxd=3;
								}
								if($scope.regimen.formaPago=="Quincenal"){
									maxd=2;
								}
								if($scope.regimen.formaPago=="Mensual"){
									maxd=1;
								}
								diasPago=diasPago.splice(maxd,4-maxd);
								console.log(diasPago);
								var send = {
									empresa : empr.RFC,
									regimen : $scope.regimen,
									tipo : $scope.regimen.tipoRegimen									
								/* pago: $scope.regimen.tipoPago */
								}

								empr.regimenes.push($scope.regimen);
								console.log(send);
								esquemasService.saveEmpresa(send).then(
										function(data) {
											console.log(data);
											alert("Guardado con éxito");
											$location.path("/empresas/details/"+$rootScope.rfc)
										}).then(function(response){
											if(response.status==403){
												alert("No tiene permisos para realizar esta acción");
												$location.path("/");
											}		
										})
							};
							$scope.disableDay=[true,true,true,true];
							$scope.ejecutar = function(j) {
								for (var i = 0; i < 4; i++) {
									$scope.disableDay[i] = true;									
								}
								for (var i = 0; i < j; i++) {
									$scope.disableDay[i] = false;
									$scope.regimen.diasDePago[i]="";
								};								
							};							
							
							$scope.dias=function(){

								if($scope.regimen.formaPago=="Semanal"){
									$scope.Mensaje=true;									
									for(var i=1; i<$scope.regimen.diasDePago.length;i++){
										if($scope.regimen.diasDePago[i] <= $scope.regimen.diasDePago[i-1]){
											$scope.Mensaje=false;
										}
									}
								}
								if($scope.regimen.formaPago=="Quincenal"){
									$scope.Mensaje=true;									
									for(var i=1; i<2;i++){
										if($scope.regimen.diasDePago[i] <= $scope.regimen.diasDePago[i-1]){
											$scope.Mensaje=false;
										}
									}
								}
								if($scope.regimen.formaPago=="Decenal"){
									$scope.Mensaje=true;									
									for(var i=1; i<3;i++){
										if($scope.regimen.diasDePago[i] <= $scope.regimen.diasDePago[i-1]){
											$scope.Mensaje=false;
										}
									}
								}
								
							};
							$scope.clean = function(per, ded) {
								document.getElementById("forma").reset();								
								var index= $scope.regimen.percepciones.indexOf(per);								
								$scope.regimen.percepciones.splice(per);
								var index= $scope.regimen.deducciones.indexOf(ded);
								$scope.regimen.deducciones.splice(ded);
							}
						} ]);
