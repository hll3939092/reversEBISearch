package cn.ncbsp.omicsdi.solr.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * <p>Java class for entryType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="entryType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="name"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                 &lt;attribute name="boost"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float"&gt;
 *                       &lt;minInclusive value="1"/&gt;
 *                       &lt;maxInclusive value="3"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="authors" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="keywords" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dates" type="{}datesType"/&gt;
 *         &lt;element name="cross_references" type="{}cross_referencesType" minOccurs="0"/&gt;
 *         &lt;element name="additional_fields" type="{}additional_fieldsType"/&gt;
 *       &lt;/all&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="acc" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entryType", propOrder = {

})
public class Entry
        implements Serializable, IDataObject {

    private final static long serialVersionUID = 105L;
    @XmlElement(required = true)
    protected Entry.Name name;
    protected String description;
    protected String authors;
    protected String keywords;
    @XmlElement(required = true)
    protected DatesType dates;


    protected FilesType files;

    @XmlElement(name = "cross_references")
    protected CrossReferences crossReferences;
    @XmlElement(name = "additional_fields", required = true)
    protected AdditionalFields additionalFields;
    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "acc")
    protected String acc;

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link Entry.Name }
     */
    public Entry.Name getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link Entry.Name }
     */
    public void setName(Entry.Name value) {
        this.name = value;
    }

    public void setName(String name) {
        this.name = new Name();
        this.name.setValue(name);
    }

    /**
     * Gets the value of the description property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the authors property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAuthors() {
        return authors;
    }

    /**
     * Sets the value of the authors property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAuthors(String value) {
        this.authors = value;
    }

    /**
     * Gets the value of the keywords property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * Sets the value of the keywords property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setKeywords(String value) {
        this.keywords = value;
    }

    /**
     * Gets the value of the dates property.
     *
     * @return possible object is
     * {@link DatesType }
     */
    public DatesType getDates() {
        return dates;
    }

    /**
     * Sets the value of the dates property.
     *
     * @param value allowed object is
     *              {@link DatesType }
     */
    public void setDates(DatesType value) {
        this.dates = value;
    }

    /**
     * Gets the value of the crossReferences property.
     *
     * @return possible object is
     * {@link CrossReferences }
     */
    public CrossReferences getCrossReferences() {
        return crossReferences;
    }

    /**
     * Sets the value of the crossReferences property.
     *
     * @param value allowed object is
     *              {@link CrossReferences }
     */
    public void setCrossReferences(CrossReferences value) {
        this.crossReferences = value;
    }

    public void setCrossReferences(List<Reference> references) {
        if (references != null && !references.isEmpty()) {
            crossReferences.setRef(references);
        }
    }

    /**
     * Gets the value of the additionalFields property.
     *
     * @return possible object is
     * {@link AdditionalFields }
     */
    public AdditionalFields getAdditionalFields() {
        return additionalFields;
    }

    /**
     * Sets the value of the additionalFields property.
     *
     * @param value allowed object is
     *              {@link AdditionalFields }
     */
    public void setAdditionalFields(AdditionalFields value) {
        this.additionalFields = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the acc property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAcc() {
        return acc;
    }

    /**
     * Sets the value of the acc property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAcc(String value) {
        this.acc = value;
    }

    public FilesType getFiles() {
        return files;
    }

    public void setFiles(FilesType files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "name=" + name +
                ", description='" + description + '\'' +
                ", authors='" + authors + '\'' +
                ", keywords='" + keywords + '\'' +
                ", dates=" + dates +
                ", crossReferences=" + crossReferences +
                ", additionalFields=" + additionalFields +
                ", id='" + id + '\'' +
                ", acc='" + acc + '\'' +
                '}';
    }

    /**
     * Return the unique value of the present key
     *
     * @param key
     * @return
     */
    public String getAdditionalFieldValue(String key) {
        String value = null;
        if (additionalFields != null && !additionalFields.isEmpty()) {
            for (Field field : additionalFields.getField())
                if (field != null && field.getName() != null && field.getName().equalsIgnoreCase(key))
                    value = field.getValue();
        }
        return value;
    }

    /**
     * Return of a key all the values in the Entry fot the given key
     *
     * @param key the key of the Field
     * @return a list with all the values for the corresponding key
     */
    public List<String> getAdditionalFieldValues(String key) {
        List<String> value = new ArrayList<>();
        if (additionalFields != null && !additionalFields.isEmpty()) {
            for (Field field : additionalFields.getField())
                if (field != null && field.getName() != null && field.getName().equalsIgnoreCase(key))
                    value.add(field.getValue());
        }
        return value;
    }

    public void addAdditionalField(String name, String value) {
        if (name != null && value != null) {
            if (additionalFields == null || additionalFields.field == null) {
                additionalFields = new AdditionalFields();
                additionalFields.field = new ArrayList<>();
            }
            Field field = new Field();
            field.setName(name);
            field.setValue(value);
            additionalFields.field.add(field);
        }
    }

    /**
     * Return a list of values for specific cross-reference database
     *
     * @param key the cross-reference database
     * @return the list of values
     */
    public List<String> getCrossReferenceFieldValue(String key) {
        List<String> values = new ArrayList<>();
        if (crossReferences != null && !crossReferences.isEmpty()) {
            for (Reference ref : crossReferences.getRef()) {
                if (ref != null && ref.getDbname() != null && ref.getDbname().equalsIgnoreCase(key)) {
                    values.add(ref.getDbkey());
                }
            }
        }
        return values;
    }

    /**
     * Add a new cross-reference key/value to the corss-references list
     *
     * @param key   the key of the corss-reference: for example pubmed
     * @param value the values of the cross-reference for example a pubmedID
     */
    public void addCrossReferenceValue(String key, String value) {
        if (crossReferences == null) {
            crossReferences = new CrossReferences();
            crossReferences.ref = new ArrayList<>();
        }
        List<Reference> fields = crossReferences.getRef();
        if (key != null && value != null) {
            if (fields == null)
                fields = new ArrayList<>();
            Reference field = new Reference();
            field.setDbkey(value);
            field.setDbname(key);
            fields.add(field);
        }
        crossReferences.setRef(fields);
    }

    /**
     * Add a new cross-reference key/value to the corss-references list
     *
     * @param key    the key of the corss-reference: for example pubmed
     * @param values the values of the cross-reference for example a pubmedID
     */
    public void addCrossReferenceValue(String key, Set<String> values) {
        if (crossReferences == null) {
            crossReferences = new CrossReferences();
            crossReferences.ref = new ArrayList<>();
        }
        List<Reference> fields = crossReferences.getRef();
        if (key != null && values != null) {
            if (fields == null)
                fields = new ArrayList<>();
            for (String value : values) {
                Reference field = new Reference();
                field.setDbkey(value);
                field.setDbname(key);
                fields.add(field);
            }
        }
        crossReferences.setRef(fields);
    }

    public void removeCrossReferences(String key) {
        if (crossReferences != null && crossReferences.getRef() != null && !crossReferences.getRef().isEmpty()) {
            for (int i = 0; i < crossReferences.getRef().size(); i++) {
                Reference reference = crossReferences.getRef().get(i);
                if (reference != null && reference.dbkey != null && reference.dbname.equalsIgnoreCase(key)) {
                    crossReferences.getRef().remove(i);
                    i--;
                }
            }
        }
    }

    public void addDate(Date date) {
        if (date != null) {
            if (dates == null || dates.getDate() == null) {
                dates = new DatesType();
                dates.date = new ArrayList<>();
            }
            dates.date.add(date);
        }
    }

    public void addDate(String type, String value) {
        Date date = new Date();
        date.setType(type);
        date.setValue(value);
        addDate(date);
    }

    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *       &lt;attribute name="boost"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}float"&gt;
     *             &lt;minInclusive value="1"/&gt;
     *             &lt;maxInclusive value="3"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class Name
            implements Serializable, IDataObject {

        private final static long serialVersionUID = 105L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "boost")
        protected Float boost;

        /**
         * Gets the value of the value property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the boost property.
         *
         * @return possible object is
         * {@link Float }
         */
        public Float getBoost() {
            return boost;
        }

        /**
         * Sets the value of the boost property.
         *
         * @param value allowed object is
         *              {@link Float }
         */
        public void setBoost(Float value) {
            this.boost = value;
        }

        @Override
        public String toString() {
            return "Name{" +
                    "value='" + value + '\'' +
                    ", boost=" + boost +
                    '}';
        }
    }

//    public String getRepository(){
//        if(additionalFields != null && !additionalFields.isEmpty()){
//            return getAdditionalFieldValue(uk.ac.ebi.ddi.xml.validator.utils.Field.REPOSITORY.getName());
//        }
//        return null;
//    }
}
