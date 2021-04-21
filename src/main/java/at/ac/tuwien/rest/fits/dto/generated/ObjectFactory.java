package at.ac.tuwien.rest.fits.dto.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.harvard.hul.ois.xml.ns.fits.fits_output package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FileStatusTypeWellFormed_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "well-formed");
    private final static QName _FileStatusTypeValid_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "valid");
    private final static QName _FileStatusTypeMessage_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "message");
    private final static QName _FileInfoTypeFilepath_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "filepath");
    private final static QName _FileInfoTypeFilename_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "filename");
    private final static QName _FileInfoTypeSize_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "size");
    private final static QName _FileInfoTypeMd5Checksum_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "md5checksum");
    private final static QName _FileInfoTypeLastmodified_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "lastmodified");
    private final static QName _FileInfoTypeFslastmodified_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "fslastmodified");
    private final static QName _FileInfoTypeCreated_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "created");
    private final static QName _FileInfoTypeCreatingApplicationName_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "creatingApplicationName");
    private final static QName _FileInfoTypeCreatingApplicationVersion_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "creatingApplicationVersion");
    private final static QName _FileInfoTypeInhibitorType_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "inhibitorType");
    private final static QName _FileInfoTypeInhibitorTarget_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "inhibitorTarget");
    private final static QName _FileInfoTypeRightsBasis_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "rightsBasis");
    private final static QName _FileInfoTypeCopyrightBasis_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "copyrightBasis");
    private final static QName _FileInfoTypeCopyrightNote_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "copyrightNote");
    private final static QName _FileInfoTypeCreatingos_QNAME = new QName("http://hul.harvard.edu/ois/xml/ns/fits/fits_output", "creatingos");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.harvard.hul.ois.xml.ns.fits.fits_output
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StatisticsType }
     * 
     */
    public StatisticsType createStatisticsType() {
        return new StatisticsType();
    }

    /**
     * Create an instance of {@link IdentificationType }
     * 
     */
    public IdentificationType createIdentificationType() {
        return new IdentificationType();
    }

    /**
     * Create an instance of {@link Fits }
     * 
     */
    public Fits createFits() {
        return new Fits();
    }

    /**
     * Create an instance of {@link FileInfoType }
     * 
     */
    public FileInfoType createFileInfoType() {
        return new FileInfoType();
    }

    /**
     * Create an instance of {@link FileStatusType }
     * 
     */
    public FileStatusType createFileStatusType() {
        return new FileStatusType();
    }

    /**
     * Create an instance of {@link MetadataType }
     * 
     */
    public MetadataType createMetadataType() {
        return new MetadataType();
    }

    /**
     * Create an instance of {@link ToolOutputType }
     * 
     */
    public ToolOutputType createToolOutputType() {
        return new ToolOutputType();
    }

    /**
     * Create an instance of {@link Tool }
     * 
     */
    public Tool createTool() {
        return new Tool();
    }

    /**
     * Create an instance of {@link Version }
     * 
     */
    public Version createVersion() {
        return new Version();
    }

    /**
     * Create an instance of {@link ExternalIdentifier }
     * 
     */
    public ExternalIdentifier createExternalIdentifier() {
        return new ExternalIdentifier();
    }

    /**
     * Create an instance of {@link FitsMetadataType }
     * 
     */
    public FitsMetadataType createFitsMetadataType() {
        return new FitsMetadataType();
    }

    /**
     * Create an instance of {@link TechnicalMetadata }
     * 
     */
    public TechnicalMetadata createTechnicalMetadata() {
        return new TechnicalMetadata();
    }

    /**
     * Create an instance of {@link StatisticsType.Tool }
     * 
     */
    public StatisticsType.Tool createStatisticsTypeTool() {
        return new StatisticsType.Tool();
    }

    /**
     * Create an instance of {@link IdentificationType.Identity }
     * 
     */
    public IdentificationType.Identity createIdentificationTypeIdentity() {
        return new IdentificationType.Identity();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "well-formed", scope = FileStatusType.class)
    public JAXBElement<FitsMetadataType> createFileStatusTypeWellFormed(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileStatusTypeWellFormed_QNAME, FitsMetadataType.class, FileStatusType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "valid", scope = FileStatusType.class)
    public JAXBElement<FitsMetadataType> createFileStatusTypeValid(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileStatusTypeValid_QNAME, FitsMetadataType.class, FileStatusType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "message", scope = FileStatusType.class)
    public JAXBElement<FitsMetadataType> createFileStatusTypeMessage(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileStatusTypeMessage_QNAME, FitsMetadataType.class, FileStatusType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "filepath", scope = FileInfoType.class)
    public JAXBElement<FitsMetadataType> createFileInfoTypeFilepath(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileInfoTypeFilepath_QNAME, FitsMetadataType.class, FileInfoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "filename", scope = FileInfoType.class)
    public JAXBElement<FitsMetadataType> createFileInfoTypeFilename(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileInfoTypeFilename_QNAME, FitsMetadataType.class, FileInfoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "size", scope = FileInfoType.class)
    public JAXBElement<FitsMetadataType> createFileInfoTypeSize(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileInfoTypeSize_QNAME, FitsMetadataType.class, FileInfoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "md5checksum", scope = FileInfoType.class)
    public JAXBElement<FitsMetadataType> createFileInfoTypeMd5Checksum(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileInfoTypeMd5Checksum_QNAME, FitsMetadataType.class, FileInfoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "lastmodified", scope = FileInfoType.class)
    public JAXBElement<FitsMetadataType> createFileInfoTypeLastmodified(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileInfoTypeLastmodified_QNAME, FitsMetadataType.class, FileInfoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "fslastmodified", scope = FileInfoType.class)
    public JAXBElement<FitsMetadataType> createFileInfoTypeFslastmodified(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileInfoTypeFslastmodified_QNAME, FitsMetadataType.class, FileInfoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "created", scope = FileInfoType.class)
    public JAXBElement<FitsMetadataType> createFileInfoTypeCreated(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileInfoTypeCreated_QNAME, FitsMetadataType.class, FileInfoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "creatingApplicationName", scope = FileInfoType.class)
    public JAXBElement<FitsMetadataType> createFileInfoTypeCreatingApplicationName(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileInfoTypeCreatingApplicationName_QNAME, FitsMetadataType.class, FileInfoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "creatingApplicationVersion", scope = FileInfoType.class)
    public JAXBElement<FitsMetadataType> createFileInfoTypeCreatingApplicationVersion(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileInfoTypeCreatingApplicationVersion_QNAME, FitsMetadataType.class, FileInfoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "inhibitorType", scope = FileInfoType.class)
    public JAXBElement<FitsMetadataType> createFileInfoTypeInhibitorType(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileInfoTypeInhibitorType_QNAME, FitsMetadataType.class, FileInfoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "inhibitorTarget", scope = FileInfoType.class)
    public JAXBElement<FitsMetadataType> createFileInfoTypeInhibitorTarget(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileInfoTypeInhibitorTarget_QNAME, FitsMetadataType.class, FileInfoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "rightsBasis", scope = FileInfoType.class)
    public JAXBElement<FitsMetadataType> createFileInfoTypeRightsBasis(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileInfoTypeRightsBasis_QNAME, FitsMetadataType.class, FileInfoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "copyrightBasis", scope = FileInfoType.class)
    public JAXBElement<FitsMetadataType> createFileInfoTypeCopyrightBasis(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileInfoTypeCopyrightBasis_QNAME, FitsMetadataType.class, FileInfoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "copyrightNote", scope = FileInfoType.class)
    public JAXBElement<FitsMetadataType> createFileInfoTypeCopyrightNote(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileInfoTypeCopyrightNote_QNAME, FitsMetadataType.class, FileInfoType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FitsMetadataType }{@code >}
     */
    @XmlElementDecl(namespace = "http://hul.harvard.edu/ois/xml/ns/fits/fits_output", name = "creatingos", scope = FileInfoType.class)
    public JAXBElement<FitsMetadataType> createFileInfoTypeCreatingos(FitsMetadataType value) {
        return new JAXBElement<FitsMetadataType>(_FileInfoTypeCreatingos_QNAME, FitsMetadataType.class, FileInfoType.class, value);
    }

}
