
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
 *         &lt;element name="AsignaTimbresEmisorResult" type="{http://tempuri.org/}ArrayOfAnyType" minOccurs="0"/&gt;
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
    "asignaTimbresEmisorResult"
})
@XmlRootElement(name = "AsignaTimbresEmisorResponse")
public class AsignaTimbresEmisorResponse {

    @XmlElement(name = "AsignaTimbresEmisorResult")
    protected ArrayOfAnyType asignaTimbresEmisorResult;

    /**
     * Obtiene el valor de la propiedad asignaTimbresEmisorResult.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAnyType }
     *     
     */
    public ArrayOfAnyType getAsignaTimbresEmisorResult() {
        return asignaTimbresEmisorResult;
    }

    /**
     * Define el valor de la propiedad asignaTimbresEmisorResult.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAnyType }
     *     
     */
    public void setAsignaTimbresEmisorResult(ArrayOfAnyType value) {
        this.asignaTimbresEmisorResult = value;
    }

}
