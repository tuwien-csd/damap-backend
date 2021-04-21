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
 *       &lt;sequence&gt;
 *         &lt;element name="identification" type="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}identificationType" minOccurs="0"/&gt;
 *         &lt;element name="fileinfo" type="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}fileInfoType" minOccurs="0"/&gt;
 *         &lt;element name="filestatus" type="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}fileStatusType" minOccurs="0"/&gt;
 *         &lt;element name="metadata" type="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}metadataType" minOccurs="0"/&gt;
 *         &lt;element name="toolOutput" type="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}toolOutputType" minOccurs="0"/&gt;
 *         &lt;element name="statistics" type="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}statisticsType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="timestamp" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "identification",
    "fileinfo",
    "filestatus",
    "metadata",
    "toolOutput",
    "statistics"
})
@XmlRootElement(name = "fits")
public class Fits {

    protected IdentificationType identification;
    protected FileInfoType fileinfo;
    protected FileStatusType filestatus;
    protected MetadataType metadata;
    protected ToolOutputType toolOutput;
    protected StatisticsType statistics;
    @XmlAttribute(name = "version")
    protected String version;
    @XmlAttribute(name = "timestamp")
    protected String timestamp;

    /**
     * Ruft den Wert der identification-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link IdentificationType }
     *     
     */
    public IdentificationType getIdentification() {
        return identification;
    }

    /**
     * Legt den Wert der identification-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificationType }
     *     
     */
    public void setIdentification(IdentificationType value) {
        this.identification = value;
    }

    /**
     * Ruft den Wert der fileinfo-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link FileInfoType }
     *     
     */
    public FileInfoType getFileinfo() {
        return fileinfo;
    }

    /**
     * Legt den Wert der fileinfo-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link FileInfoType }
     *     
     */
    public void setFileinfo(FileInfoType value) {
        this.fileinfo = value;
    }

    /**
     * Ruft den Wert der filestatus-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link FileStatusType }
     *     
     */
    public FileStatusType getFilestatus() {
        return filestatus;
    }

    /**
     * Legt den Wert der filestatus-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link FileStatusType }
     *     
     */
    public void setFilestatus(FileStatusType value) {
        this.filestatus = value;
    }

    /**
     * Ruft den Wert der metadata-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MetadataType }
     *     
     */
    public MetadataType getMetadata() {
        return metadata;
    }

    /**
     * Legt den Wert der metadata-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MetadataType }
     *     
     */
    public void setMetadata(MetadataType value) {
        this.metadata = value;
    }

    /**
     * Ruft den Wert der toolOutput-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ToolOutputType }
     *     
     */
    public ToolOutputType getToolOutput() {
        return toolOutput;
    }

    /**
     * Legt den Wert der toolOutput-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ToolOutputType }
     *     
     */
    public void setToolOutput(ToolOutputType value) {
        this.toolOutput = value;
    }

    /**
     * Ruft den Wert der statistics-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link StatisticsType }
     *     
     */
    public StatisticsType getStatistics() {
        return statistics;
    }

    /**
     * Legt den Wert der statistics-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link StatisticsType }
     *     
     */
    public void setStatistics(StatisticsType value) {
        this.statistics = value;
    }

    /**
     * Ruft den Wert der version-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Legt den Wert der version-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Ruft den Wert der timestamp-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Legt den Wert der timestamp-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimestamp(String value) {
        this.timestamp = value;
    }

}
