
package localhost;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="usuarioIntegrador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xmlComprobanteBase64" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idComprobante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "usuarioIntegrador",
    "xmlComprobanteBase64",
    "idComprobante"
})
@XmlRootElement(name = "TimbraCFDI")
public class TimbraCFDI {

    protected String usuarioIntegrador;
    protected String xmlComprobanteBase64;
    protected String idComprobante;

    /**
     * Obtiene el valor de la propiedad usuarioIntegrador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuarioIntegrador() {
        return usuarioIntegrador;
    }

    /**
     * Define el valor de la propiedad usuarioIntegrador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuarioIntegrador(String value) {
        this.usuarioIntegrador = value;
    }

    /**
     * Obtiene el valor de la propiedad xmlComprobanteBase64.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlComprobanteBase64() {
        return xmlComprobanteBase64;
    }

    /**
     * Define el valor de la propiedad xmlComprobanteBase64.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlComprobanteBase64(String value) {
        this.xmlComprobanteBase64 = value;
    }

    /**
     * Obtiene el valor de la propiedad idComprobante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdComprobante() {
        return idComprobante;
    }

    /**
     * Define el valor de la propiedad idComprobante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdComprobante(String value) {
        this.idComprobante = value;
    }

}
