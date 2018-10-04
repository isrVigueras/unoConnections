
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
 *         &lt;element name="CancelaCFDIAckResult" type="{http://tempuri.org/}ArrayOfAnyType" minOccurs="0"/&gt;
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
    "cancelaCFDIAckResult"
})
@XmlRootElement(name = "CancelaCFDIAckResponse")
public class CancelaCFDIAckResponse {

    @XmlElement(name = "CancelaCFDIAckResult")
    protected ArrayOfAnyType cancelaCFDIAckResult;

    /**
     * Obtiene el valor de la propiedad cancelaCFDIAckResult.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAnyType }
     *     
     */
    public ArrayOfAnyType getCancelaCFDIAckResult() {
        return cancelaCFDIAckResult;
    }

    /**
     * Define el valor de la propiedad cancelaCFDIAckResult.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAnyType }
     *     
     */
    public void setCancelaCFDIAckResult(ArrayOfAnyType value) {
        this.cancelaCFDIAckResult = value;
    }

}
