
package org.tempuri;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ObtieneTimbresDisponiblesResult" type="{http://tempuri.org/}ArrayOfAnyType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "obtieneTimbresDisponiblesResult"
})
@XmlRootElement(name = "ObtieneTimbresDisponiblesResponse")
public class ObtieneTimbresDisponiblesResponse {

    @XmlElement(name = "ObtieneTimbresDisponiblesResult")
    protected ArrayOfAnyType obtieneTimbresDisponiblesResult;

    /**
     * Obtiene el valor de la propiedad obtieneTimbresDisponiblesResult.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAnyType }
     *     
     */
    public ArrayOfAnyType getObtieneTimbresDisponiblesResult() {
        return obtieneTimbresDisponiblesResult;
    }

    /**
     * Define el valor de la propiedad obtieneTimbresDisponiblesResult.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAnyType }
     *     
     */
    public void setObtieneTimbresDisponiblesResult(ArrayOfAnyType value) {
        this.obtieneTimbresDisponiblesResult = value;
    }

}
