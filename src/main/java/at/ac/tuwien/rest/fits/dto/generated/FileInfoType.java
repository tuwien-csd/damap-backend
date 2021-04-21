package at.ac.tuwien.rest.fits.dto.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java-Klasse für fileInfoType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="fileInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;group ref="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}fileInfoElements" maxOccurs="unbounded" minOccurs="0"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fileInfoType", propOrder = {
    "fileInfoElements"
})
public class FileInfoType {

    @XmlElementRefs({
        @XmlElementRef(name = "filepath", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "filename", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "size", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "md5checksum", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "lastmodified", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "fslastmodified", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "created", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "creatingApplicationName", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "creatingApplicationVersion", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "inhibitorType", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "inhibitorTarget", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "rightsBasis", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "copyrightBasis", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "copyrightNote", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "creatingos", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<FitsMetadataType>> fileInfoElements;

    /**
     * Gets the value of the fileInfoElements property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fileInfoElements property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFileInfoElements().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<FitsMetadataType>> getFileInfoElements() {
        if (fileInfoElements == null) {
            fileInfoElements = new ArrayList<JAXBElement<FitsMetadataType>>();
        }
        return this.fileInfoElements;
    }

}
