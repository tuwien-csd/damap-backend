package at.ac.tuwien.rest.fits.dto.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java-Klasse für statisticsType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="statisticsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tool" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attGroup ref="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}nameVersionAttrGrp"/&gt;
 *                 &lt;attribute name="executionTime" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *                 &lt;attribute name="status" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="fitsExecutionTime" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "statisticsType", propOrder = {
    "tool"
})
public class StatisticsType {

    protected List<Tool> tool;
    @XmlAttribute(name = "fitsExecutionTime")
    protected BigInteger fitsExecutionTime;

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
     * Ruft den Wert der fitsExecutionTime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFitsExecutionTime() {
        return fitsExecutionTime;
    }

    /**
     * Legt den Wert der fitsExecutionTime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFitsExecutionTime(BigInteger value) {
        this.fitsExecutionTime = value;
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
     *       &lt;attGroup ref="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}nameVersionAttrGrp"/&gt;
     *       &lt;attribute name="executionTime" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
     *       &lt;attribute name="status" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Tool {

        @XmlAttribute(name = "executionTime")
        protected BigInteger executionTime;
        @XmlAttribute(name = "status")
        protected String status;
        @XmlAttribute(name = "toolname")
        protected String toolname;
        @XmlAttribute(name = "toolversion")
        protected String toolversion;

        /**
         * Ruft den Wert der executionTime-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getExecutionTime() {
            return executionTime;
        }

        /**
         * Legt den Wert der executionTime-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setExecutionTime(BigInteger value) {
            this.executionTime = value;
        }

        /**
         * Ruft den Wert der status-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatus() {
            return status;
        }

        /**
         * Legt den Wert der status-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatus(String value) {
            this.status = value;
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
