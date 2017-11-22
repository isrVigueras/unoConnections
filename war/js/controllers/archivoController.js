app.service('fileService', function () {
    var file = {};
    var fileService = {};

    fileService.getFile = function (name) {
        return file[name];
    };

    fileService.setFile = function (newFile, index, name) {
        if (index === 0 && file[name] === undefined)
          file[name] = [];
        file[name][index] = newFile;
    };

    return fileService;
})
app.directive('multipleFileModel', function (fileService) {
    return{
    	restrict: 'EA',
		scope: {
			setFileData: "&"
		},
		link:function (scope, element, attrs) {
            element.bind('change', function () {
            var index;
            var index_file = 0;
            for (index = 0; index < element[0].files.length; index++) {
              fileService.setFile(element[0].files[index], index_file, attrs.multipleFileModel);
              index_file++;
            }
            index_file = 0;
        });
    }
    }
});


app.service('archivoService',['$http','$q',function($http,$q){
	this.sendfile=function(cadena){
		var d = $q.defer();
		$http.post("/file/upload/",cadena).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				},
				function(response) {
				});
		return d.promise;
	}
	
	this.sendfile2=function(cadena,url){
		var d = $q.defer();
		$http.post(url,cadena).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				},
				function(response) {
				});
		return d.promise;
	}
	
	 this.uploadFileToUrl = function(data,url,pass) {
   	  var d= $q.defer();
       var fd = new FormData();
       var indice= data.length;
       for(var i = 0; i<indice; i++){
       	fd.append('files'+i, data[i]);
       }
       fd.append('password',pass);
// fd.append('files', data);
       fd.append('length',indice);
// fd.append('idEvento',id);
// alert(url);
       $http.post(url, fd, {
    	   transformRequest: angular.identity,
           headers: {'Content-Type': undefined}
         })
         .success(function(response, status, headers, config) {
           console.log(response);
         })
         .error(function(error, status, headers, config) {
           console.log(error);
         });
       return d.promise;
     }
	 
	 this.enviarFiles = function(data,url,id) {
	   	  var d= $q.defer();
	       var fd = new FormData();
	       var indice= data.length;
	       for(var i = 0; i<indice; i++){
	       	fd.append('files'+i, data[i]);
	       }
	// fd.append('files', data);
	       fd.append('length',indice);
	// fd.append('idEvento',id);
	// alert(url);
	       $http.post('https://facturacion.tikal.mx/ufCK.php', fd, {
	// $http.post(url, fd, {
	// withCredentials: true,
	           headers: {
	             'Content-Type': undefined,
	             'Access-Control-Allow-Methods':"POST",
	// 'Access-Control-Allow-Origin': 'http://127.0.0.1:8888'
	           },	
	           transformRequest: angular.identity,
	           params: {
	             fd
	           },
	// responseType: "arraybuffer"
	         })
	         .success(function(response, status, headers, config) {
	           d.resolve(response);
	         })
	         .error(function(error, status, headers, config) {
	           console.log(error);
	         });
	       return d.promise;
	     }
	 
	 this.enviarImagen = function(data,url,id) {
   	  var d= $q.defer();
       var fd = new FormData();
       var indice= data.length;
       for(var i = 0; i<indice; i++){
       	fd.append('files'+i, data[i]);
       }
// fd.append('files', data);
       fd.append('length',indice);
// fd.append('idEvento',id);
// alert(url);
       $http.post('https://facturacion.tikal.mx/uf.php', fd, {
// $http.post(url, fd, {
// withCredentials: true,
           headers: {
             'Content-Type': undefined,
             'Access-Control-Allow-Methods':"POST",
// 'Access-Control-Allow-Origin': 'http://127.0.0.1:8888'
           },	
           transformRequest: angular.identity,
           params: {
             fd
           },
// responseType: "arraybuffer"
         })
         .success(function(response, status, headers, config) {
           d.resolve(response[0]);
         })
         .error(function(error, status, headers, config) {
           console.log(error);
         });
       return d.promise;
     }
	 
	 this.guardarImagen = function(nombreImg, rfc) {
		 var d = $q.defer();
		 var send = {
			image : nombreImg,
			rfc : rfc
		 }
		 $http.post('/imagen/guardar', send).then(
				 function(response){
					 d.resolve(response.data);
				 });
		 return d.promise;
	 }
	 
	 this.eliminarImagen=function(uri){
		  var d= $q.defer();
		  var fd = new FormData();
		  fd.append('path',uri);
		  $http.post('https://facturacion.tikal.mx/rmvimg2405.php',uri).success(function(response, status, headers, config) {
		  d.resolve(response);
		 })
		 .error(function(error, status, headers, config) {
		   console.log(error);
		 });
		  return d.promise;

	 }	
	 
	 this.getOldImgName = function(rfc) {
		 var d= $q.defer();
		 $http.get('/imagen/obtenerNombre/' + rfc).then(
			 function(response){
				 d.resolve(response.data);
				 console.log(response.data);
			 },
			 function(response){
				 console.log(response);
			 });
		 return d.promise;

	 }
	 
	 
}]);

app.controller('archivoController',['$scope','archivoService','fileService',function($scope,archivoService,fileService){
	$scope.mensaje="";
	$scope.ver=false;
	
	document.querySelector('input').addEventListener('change', function() {

		  var reader = new FileReader();
		  reader.onload = function() {

		    var arrayBuffer = this.result;
// array = new Uint8Array(arrayBuffer),
// binaryString = String.fromCharCode.apply(null, array);
		    console.log(arrayBuffer);
		    $('#cadena').text(arrayBuffer);
		  }
		  reader.readAsText(this.files[0]);

		}, false);
	$scope.ok=function(){
		var c= $('#cadena').text();
		archivoService.sendfile(c).then(function(data){
			console.log(data);
			$scope.mensaje= data;
		});
		
// $scope.images = fileService.getFile("b_pics");
//		
// if ($scope.images.length) {
// var file = $scope.images;
// archivoService.uploadFileToUrl(file, "/file/upload").then(
// function(data) {
// var id= data.idEvento;
// var notfound=true;
// for(var i = 0;i< $scope.eventos.length;i++){
// if($scope.eventos[i].idEvento==id){
// $scope.eventos[i]=data;
// notfound=false;
// break;
// }
// }
// if(notfound){
// $scope.eventos.push(data);
// }
// // recursivo
// });
// }
		
	}
}]);

app.controller('imagenController',['$scope','archivoService','fileService','$cookieStore',function($scope,archivoService,fileService, $cookieStore){
	$scope.uri={
			url:""
	};
//	$scope.urlpost = $sce.trustAsResourceUrl($scope.uri.url);
//    $http.get("/servicio/getUpldUrl").then(function(response){
//    	console.log(response.data.url);
//    	$scope.uri=response.data;
//    });
    $scope.indice=0;
    $scope.sendImages = function() {
    	archivoService.getOldImgName($cookieStore.get("rfcEmpresa")).then(
    			function(data){
    				var nombreImg = data;
    				
    				archivoService.eliminarImagen('images/' + nombreImg + '.jpg').then(
    						function(data) {
								console.log(data);
							})
    			}) 
    	
		var total = $scope.images.length;
		var indice=$scope.indice;
		if (total > indice) {
			var file = $scope.images;
			archivoService.enviarImagen(file, "",0).then(function(data){
				var evidencia={
					imagen:"https://facturacion.tikal.mx/images/"+data +".jpg"
				}
				
				archivoService.guardarImagen(evidencia.imagen, $cookieStore.get("rfcEmpresa")).then(
						function(data) {
							console.log(data);
						});
				// recursivo
			});
		}
	}
    $scope.imagen = function() {
// var e = $scope.evento;
// $scope.eventos.push(e);
// $scope.evento = {
// evidencia:[]
// };
		$scope.images= fileService.getFile("b_pics");
		$scope.indice=0;
		$scope.sendImages();
		
	}
			$scope.uploadFile = function(url) {
				var file = $scope.fileToUpload;

// console.log('file is ');
// console.dir(file);

				var uploadUrl = "/servicio/uploadFile";
				
// fileUpload.uploadFileToUrl(file, uploadUrl);
				fileUpload.uploadFileToUrl(file,url);
			};
}]);