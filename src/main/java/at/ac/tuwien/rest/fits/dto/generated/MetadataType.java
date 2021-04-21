package at.ac.tuwien.rest.fits.dto.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für metadataType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="metadataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="audio" type="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}technicalMetadata" minOccurs="0"/&gt;
 *         &lt;element name="container" type="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}technicalMetadata" minOccurs="0"/&gt;
 *         &lt;element name="document" type="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}technicalMetadata" minOccurs="0"/&gt;
 *         &lt;element name="image" type="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}technicalMetadata" minOccurs="0"/&gt;
 *         &lt;element name="text" type="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}technicalMetadata" minOccurs="0"/&gt;
 *         &lt;element name="video" type="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}technicalMetadata" minOccurs="0"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "metadataType", propOrder = {
    "audio",
    "container",
    "document",
    "image",
    "text",
    "video"
})
public class MetadataType {

    protected TechnicalMetadata audio;
    protected TechnicalMetadata container;
    protected TechnicalMetadata document;
    protected TechnicalMetadata image;
    protected TechnicalMetadata text;
    protected TechnicalMetadata video;

    /**
     * Ruft den Wert der audio-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TechnicalMetadata }
     *     
     */
    public TechnicalMetadata getAudio() {
        return audio;
    }

    /**
     * Legt den Wert der audio-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TechnicalMetadata }
     *     
     */
    public void setAudio(TechnicalMetadata value) {
        this.audio = value;
    }

    /**
     * Ruft den Wert der container-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TechnicalMetadata }
     *     
     */
    public TechnicalMetadata getContainer() {
        return container;
    }

    /**
     * Legt den Wert der container-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TechnicalMetadata }
     *     
     */
    public void setContainer(TechnicalMetadata value) {
        this.container = value;
    }

    /**
     * Ruft den Wert der document-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TechnicalMetadata }
     *     
     */
    public TechnicalMetadata getDocument() {
        return document;
    }

    /**
     * Legt den Wert der document-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TechnicalMetadata }
     *     
     */
    public void setDocument(TechnicalMetadata value) {
        this.document = value;
    }

    /**
     * Ruft den Wert der image-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TechnicalMetadata }
     *     
     */
    public TechnicalMetadata getImage() {
        return image;
    }

    /**
     * Legt den Wert der image-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TechnicalMetadata }
     *     
     */
    public void setImage(TechnicalMetadata value) {
        this.image = value;
    }

    /**
     * Ruft den Wert der text-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TechnicalMetadata }
     *     
     */
    public TechnicalMetadata getText() {
        return text;
    }

    /**
     * Legt den Wert der text-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TechnicalMetadata }
     *     
     */
    public void setText(TechnicalMetadata value) {
        this.text = value;
    }

    /**
     * Ruft den Wert der video-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TechnicalMetadata }
     *     
     */
    public TechnicalMetadata getVideo() {
        return video;
    }

    /**
     * Legt den Wert der video-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TechnicalMetadata }
     *     
     */
    public void setVideo(TechnicalMetadata value) {
        this.video = value;
    }

}
