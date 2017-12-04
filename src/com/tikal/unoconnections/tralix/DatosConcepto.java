package com.tikal.unoconnections.tralix;

public class DatosConcepto {
	
	//#05
		//private String idUnicoInterno;
		private String clave;
		private float cantidad;
		private String descripcion;
		private float valorUnit;
		private float importe;
		private String unidadMed;
		private int categoria; //categoria
		private String fraccionArancelaria;
		private String unidadAduana;
		public float getCantidad() {
			return cantidad;
		}
		public void setCantidad(float cantidad) {
			this.cantidad = cantidad;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public float getValorUnit() {
			return valorUnit;
		}
		public void setValorUnit(float valorUnit) {
			this.valorUnit = valorUnit;
		}
		public float getImporte() {
			return importe;
		}
		public void setImporte(float importe) {
			this.importe = importe;
		}
		public String getUnidadMed() {
			return unidadMed;
		}
		public void setUnidadMed(String unidadMed) {
			this.unidadMed = unidadMed;
		}
		public int getCategoria() {
			return categoria;
		}
		public void setCategoria(int categoria) {
			this.categoria = categoria;
		}
		public String getFraccionArancelaria() {
			return fraccionArancelaria;
		}
		public void setFraccionArancelaria(String fraccionArancelaria) {
			this.fraccionArancelaria = fraccionArancelaria;
		}
		public String getClave() {
			return clave;
		}
		public void setClave(String clave) {
			this.clave = clave;
		}
		public String getUnidadAduana() {
			return unidadAduana;
		}
		public void setUnidadAduana(String unidadAduana) {
			this.unidadAduana = unidadAduana;
		} 

		
}
