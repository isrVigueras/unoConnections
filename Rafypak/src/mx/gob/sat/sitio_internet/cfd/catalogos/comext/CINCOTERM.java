//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.11.28 at 03:25:18 PM CST 
//


package mx.gob.sat.sitio_internet.cfd.catalogos.comext;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for c_INCOTERM.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="c_INCOTERM">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;whiteSpace value="collapse"/>
 *     &lt;enumeration value="CFR"/>
 *     &lt;enumeration value="CIF"/>
 *     &lt;enumeration value="CPT"/>
 *     &lt;enumeration value="CIP"/>
 *     &lt;enumeration value="DAF"/>
 *     &lt;enumeration value="DAP"/>
 *     &lt;enumeration value="DAT"/>
 *     &lt;enumeration value="DES"/>
 *     &lt;enumeration value="DEQ"/>
 *     &lt;enumeration value="DDU"/>
 *     &lt;enumeration value="DDP"/>
 *     &lt;enumeration value="EXW"/>
 *     &lt;enumeration value="FCA"/>
 *     &lt;enumeration value="FAS"/>
 *     &lt;enumeration value="FOB"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "c_INCOTERM", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos/ComExt")
@XmlEnum
public enum CINCOTERM {

    CFR,
    CIF,
    CPT,
    CIP,
    DAF,
    DAP,
    DAT,
    DES,
    DEQ,
    DDU,
    DDP,
    EXW,
    FCA,
    FAS,
    FOB;

    public String value() {
        return name();
    }

    public static CINCOTERM fromValue(String v) {
        return valueOf(v);
    }

}