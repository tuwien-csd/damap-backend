package at.ac.tuwien.rest.fits.dto.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java-Klasse für identificationType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="identificationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identity" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element ref="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}tool" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element ref="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}version" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element ref="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}externalIdentifier" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="format" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="mimetype" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="toolname" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="toolversion" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="status" type="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}statusType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "identificationType", propOrder = {
    "identity"
})
public class IdentificationType {

    protected List<Identity> identity;
    @XmlAttribute(name = "status")
    protected StatusType status;

    /**
     * Gets the value of the identity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the identity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdentity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Identity }
     * 
     * 
     */
    public List<Identity> getIdentity() {
        if (identity == null) {
            identity = new ArrayList<Identity>();
        }
        return this.identity;
    }

    /**
     * Ruft den Wert der status-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Legt den Wert der status-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element ref="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}tool" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element ref="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}version" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element ref="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}externalIdentifier" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="format" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="mimetype" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="toolname" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="toolversion" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "tool",
        "version",
        "externalIdentifier"
    })
    public static class Identity {

        protected List<Tool> tool;
        protected List<Version> version;
        protected List<ExternalIdentifier> externalIdentifier;
        @XmlAttribute(name = "format")
        protected String format;
        @XmlAttribute(name = "mimetype")
        protected String mimetype;
        @XmlAttribute(name = "toolname")
        protected String toolname;
        @XmlAttribute(name = "toolversion")
        protected String toolversion;

        /**
         * Gets the value of the tool property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the tool property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTool().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Tool }
         * 
         * 
         */
        public List<Tool> getTool() {
            if (tool == null) {
                tool = new ArrayList<Tool>();
            }
            return this.tool;
        }

        /**
         * Gets the value of the version property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the version property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVersion().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Version }
         * 
         * 
         */
        public List<Version> getVersion() {
            if (version == null) {
                version = new ArrayList<Version>();
            }
            return this.version;
        }

        /**
         * Gets the value of the externalIdentifier property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the externalIdentifier property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getExternalIdentifier().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ExternalIdentifier }
         * 
         * 
         */
        public List<ExternalIdentifier> getExternalIdentifier() {
            if (externalIdentifier == null) {
                externalIdentifier = new ArrayList<ExternalIdentifier>();
            }
            return this.externalIdentifier;
        }

        /**
         * Ruft den Wert der format-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFormat() {
            return format;
        }

        /**
         * Legt den Wert der format-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFormat(String value) {
            this.format = value;
        }

        /**
         * Ruft den Wert der mimetype-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMimetype() {
            return mimetype;
        }

        /**
         * Legt den Wert der mimetype-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMimetype(String value) {
            this.mimetype = value;
        }

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

}
