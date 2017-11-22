app.service('cfdiService', function() {
	
})
app.controller("cfdiController", function($scope){
	$scope.nombre= "Memo";

	$scope.genPDF=function(){
		html2canvas(document.body,{
			onrendered: function (canvas){
				var img=canvas.toDataURL("imagen/jpg");
				var doc = new jsPDF();
				doc.addImage(img, 'JPEG', .1, .1);
				doc.save('cfdi.pdf');
			}
		})
	}	
});