package at.ac.tuwien.rest.fits.dto.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java-Klasse für fileStatusType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="fileStatusType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;group ref="{http://hul.harvard.edu/ois/xml/ns/fits/fits_output}fileStatusElements" maxOccurs="unbounded" minOccurs="0"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fileStatusType", propOrder = {
    "fileStatusElements"
})
public class FileStatusType {

    @XmlElementRefs({
        @XmlElementRef(name = "well-formed", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "valid", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "message", namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<FitsMetadataType>> fileStatusElements;

    /**
     * Gets the value of the fileStatusElements property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fileStatusElements property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFileStatusElements().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<FitsMetadataType>> getFileStatusElements() {
        if (fileStatusElements == null) {
            fileStatusElements = new ArrayList<JAXBElement<FitsMetadataType>>();
        }
        return this.fileStatusElements;
    }

}
