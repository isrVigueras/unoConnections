//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.03.01 at 05:42:21 PM CST 
//


package com.tikal.cacao.sat.cfd.nomina;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for c_TipoRegimen.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="c_TipoRegimen">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;whiteSpace value="collapse"/>
 *     &lt;enumeration value="03"/>
 *     &lt;enumeration value="02"/>
 *     &lt;enumeration value="04"/>
 *     &lt;enumeration value="05"/>
 *     &lt;enumeration value="06"/>
 *     &lt;enumeration value="07"/>
 *     &lt;enumeration value="08"/>
 *     &lt;enumeration value="09"/>
 *     &lt;enumeration value="10"/>
 *     &lt;enumeration value="11"/>
 *     &lt;enumeration value="99"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "c_TipoRegimen", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos/Nomina")
@XmlEnum
public enum C_TipoRegimen {

	/** 03 - Jubilados */
    @XmlEnumValue("03")
    VALUE_1("03"),
    
    /** 02 - Sueldos */
    @XmlEnumValue("02")
    VALUE_2("02"),
    
    /** 04 - Pensionados */
    @XmlEnumValue("04")
    VALUE_3("04"),
    
    /** 05 - Asimilados Miembros Sociedades Cooperativas Produccion */
    @XmlEnumValue("05")
    VALUE_4("05"),
    
    /** 06 - Asimilados Integrantes Sociedades Asociaciones Civiles */
    @XmlEnumValue("06")
    VALUE_5("06"),
    
    /** 07 - Asimilados Miembros consejos */
    @XmlEnumValue("07")
    VALUE_6("07"),
    
    /** 08 - Asimilados comisionistas */
    @XmlEnumValue("08")
    VALUE_7("08"),
    
    /** 09 - Asimilados Honorarios */
    @XmlEnumValue("09")
    VALUE_8("09"),
    
    /** 10 - Asimilados acciones */
    @XmlEnumValue("10")
    VALUE_9("10"),
    
    /** 11 - Asimilados otros */
    @XmlEnumValue("11")
    VALUE_10("11"),
    
    /** 99 - Otro Regimen */
    @XmlEnumValue("99")
    VALUE_11("99");
    
    private final String value;

    C_TipoRegimen(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static C_TipoRegimen fromValue(String v) {
        for (C_TipoRegimen c: C_TipoRegimen.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
