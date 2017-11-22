app.service('usuarioService', [
		'$http',
		'$q',
		'$window',
		'$location',
		function($http, $q, $window, $location) {
			
			this.crearUsuario = function(usuario) {
				var d = $q.defer();
				$http.post("/usuario/registro", usuario).then(
						function(response) {
							console.log(response);
							d.resolve(response.data);
						},
						function(response) {
							if(response.status==400){
							alert("No se puede crear "
									+ usuario.usuario + " usuario o correo no disponibles");
							}if(response.status==403){
								//alert("No tiene permiso de realizar esta acción");
								$location.path("/login");
							}
							d.reject(response);
							$window.location.reload;
						});
				return d.promise;
			}

			this.consultarUsuariosTodos = function() {
				var d = $q.defer();
				$http.get("/usuario/consultarTodos").then(function(response) {
					d.resolve(response.data);
				}, function(response) {
					if(response.status==403){
						//alert("No tiene permiso de realizar esta acción");
						$location.path("/login");
					}
				});
				return d.promise;
			};
			
			this.actualizarUsuario = function(usuario) {
				var d = $q.defer();
				$http.post("/usuario/actualiza", usuario).then(
						function(response) {
							console.log(response);
							d.resolve(response.data);
						}, function(response) {
						});
				return d.promise;
			};

			this.eliminaUsuario = function(usuario) {
				var d = $q.defer();
				$http.post("/usuario/elimina", usuario).then(
						function(response) {
							console.log(response);
							d.resolve(response.data);
						}, function(response) {
						});
				return d.promise;
			};

			this.resetPass = function(email) {
				var d = $q.defer();
				console.log(email);
				$http.post("/usuario/reset/", email).then(function(response) {
					console.log(response);
					d.resolve(response.data);
				}, function(response) {
					// tratar error
					alert("No existe el email, intente de nuevo");
					d.reject(response);
					$window.location.reload;
				});
				return d.promise;
			}

		} ]);

app
		.controller(
				'usuarioController',
				[
						'$scope',
						'$location',
						'usuarioService',
						'perfilService',
						'$window',
						function($scope, $location, usuarioService,
								perfilService, $window) {

							perfilService
									.consultarPerfilesTodosU()
									.then(
											function(data) {
												$scope.perfiles = data;
												if ($scope.perfiles.length == 0) {
													console
															.log($scope.perfiles);
													alert("Necesita crear un perfil antes de crear un usuario");
													$location
															.path("/altaperfiles");
												}
											}).then(function(data){
												
											});
							$scope.validate = function() {
									  if ($scope.usuario.email != $scope.usuario.emailconfirm) {
									    $scope.IsMatch=true;
									    return false;
									  }
									  $scope.IsMatch=false;
							}
							
							$scope.validatePass = function() {
								if($scope.usuario.pass != $scope.usuario.passconfirm){
									$scope.IsMatchP=true;
									return false;
								}
								$scope.IsMatchP=false;
							}		
							
							$scope.acceptSubmit = function() {
								if($scope.form.email.$valid && $scope.form.emailconfirm.$valid && !$scope.IsMatch && $scope.form.pass.$valid && $scope.form.passconfirm.$valid && !$scope.IsMatchP){
									return true;
								}
								return false;
								
							}
							$scope.EnviarFormulario = function() {
								//console.log($scope.form.pass.$valid);
								$scope.validate();
								if(($scope.usuario.email != $scope.usuario.emailconfirm) || ($scope.usuario.pass != $scope.usuario.passconfirm)){
									alert("Email o contraseña no coinciden")
									//$window.location.reload();
								}else{
									if($scope.form.pass.$valid){
								usuarioService
										.crearUsuario($scope.usuario)
										.then(
												function(data) {
													alert("Usuario creado correctamente");
													$location
															.path("/modificarusuarios");
												})}else{
													alert("Contraseña no valida, intente de nuevo");
												}
							}}
						} ]);



app.controller('controladorListaUsuarios', [ '$scope', 'usuarioService',
		'perfilService', '$location', '$window',
		function($scope, usuarioService, perfilService, $location, $window) {

			usuarioService.consultarUsuariosTodos().then(function(data) {
				$scope.usuariosLista = data;
			})

			perfilService.consultarPerfilesTodos().then(function(data) {
				$scope.perfiles = data;
				if ($scope.perfiles.length == 0) {
					alert("No hay usuarios");
					$location.path("/altausuarios");
				}
			})

			$scope.actualizarUsuario = function($scope) {
				console.log($scope);
				usuarioService.actualizarUsuario($scope).then(function(data) {
					alert("Usuario modificado correctamente");
					$window.location.reload();
				});
			}

			$scope.eliminarUsuario = function($scope) {
				console.log($scope);
				usuarioService.eliminaUsuario($scope).then(function(data) {
					alert("Usuario eliminado correctamente");
					$window.location.reload();
				})
			}
		} ]);

app.controller('controladorReset', [ '$scope', 'usuarioService', '$location',
		function($scope, usuarioService, $location) {
	
				$scope.Restablecer = function($scope){
		//			console.log($scope);
					usuarioService.resetPass($scope).then(function(data){
						alert("Contraseña Enviada");
						$location.path("/login");
					})
				}
			} 
]);