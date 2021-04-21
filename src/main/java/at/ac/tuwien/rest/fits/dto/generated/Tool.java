package at.ac.tuwien.rest.fits.dto.generated;

import javax.xml.bind.annotation.*;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attGroup ref="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}nameVersionAttrGrp"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "tool")
public class Tool {

    @XmlAttribute(name = "toolname")
    protected String toolname;
    @XmlAttribute(name = "toolversion")
    protected String toolversion;

    /**
     * Ruft den Wert der toolname-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToolname() {
        return toolname;
    }

    /**
     * Legt den Wert der toolname-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToolname(String value) {
        this.toolname = value;
    }

    /**
     * Ruft den Wert der toolversion-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToolversion() {
        return toolversion;
    }

    /**
     * Legt den Wert der toolversion-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToolversion(String value) {
        this.toolversion = value;
    }

}
