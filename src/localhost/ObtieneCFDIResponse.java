
package localhost;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="ObtieneCFDIResult" type="{http://localhost/}ArrayOfAnyType" minOccurs="0"/>
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
    "obtieneCFDIResult"
})
@XmlRootElement(name = "ObtieneCFDIResponse")
public class ObtieneCFDIResponse {

    @XmlElement(name = "ObtieneCFDIResult")
    protected ArrayOfAnyType obtieneCFDIResult;

    /**
     * Obtiene el valor de la propiedad obtieneCFDIResult.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAnyType }
     *     
     */
    public ArrayOfAnyType getObtieneCFDIResult() {
        return obtieneCFDIResult;
    }

    /**
     * Define el valor de la propiedad obtieneCFDIResult.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAnyType }
     *     
     */
    public void setObtieneCFDIResult(ArrayOfAnyType value) {
        this.obtieneCFDIResult = value;
    }

}
