/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package radar;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class TransformedFlight extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -7372343916732469889L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TransformedFlight\",\"namespace\":\"radar\",\"fields\":[{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"date\",\"type\":\"string\"},{\"name\":\"from\",\"type\":\"string\"},{\"name\":\"departureAirportCode\",\"type\":\"string\"},{\"name\":\"arrivalAirportCode\",\"type\":\"string\"},{\"name\":\"departureTime\",\"type\":\"string\"},{\"name\":\"arrivalTime\",\"type\":\"string\"},{\"name\":\"departureTimestamp\",\"type\":\"long\"},{\"name\":\"arrivalTimestamp\",\"type\":\"long\"},{\"name\":\"duration\",\"type\":\"int\"},{\"name\":\"status\",\"type\":\"string\"},{\"name\":\"gate\",\"type\":\"string\"},{\"name\":\"airline\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<TransformedFlight> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<TransformedFlight> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<TransformedFlight> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<TransformedFlight> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<TransformedFlight> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this TransformedFlight to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a TransformedFlight from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a TransformedFlight instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static TransformedFlight fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private CharSequence id;
  private CharSequence date;
  private CharSequence from;
  private CharSequence departureAirportCode;
  private CharSequence arrivalAirportCode;
  private CharSequence departureTime;
  private CharSequence arrivalTime;
  private long departureTimestamp;
  private long arrivalTimestamp;
  private int duration;
  private CharSequence status;
  private CharSequence gate;
  private CharSequence airline;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public TransformedFlight() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param date The new value for date
   * @param from The new value for from
   * @param departureAirportCode The new value for departureAirportCode
   * @param arrivalAirportCode The new value for arrivalAirportCode
   * @param departureTime The new value for departureTime
   * @param arrivalTime The new value for arrivalTime
   * @param departureTimestamp The new value for departureTimestamp
   * @param arrivalTimestamp The new value for arrivalTimestamp
   * @param duration The new value for duration
   * @param status The new value for status
   * @param gate The new value for gate
   * @param airline The new value for airline
   */
  public TransformedFlight(CharSequence id, CharSequence date, CharSequence from, CharSequence departureAirportCode, CharSequence arrivalAirportCode, CharSequence departureTime, CharSequence arrivalTime, Long departureTimestamp, Long arrivalTimestamp, Integer duration, CharSequence status, CharSequence gate, CharSequence airline) {
    this.id = id;
    this.date = date;
    this.from = from;
    this.departureAirportCode = departureAirportCode;
    this.arrivalAirportCode = arrivalAirportCode;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.departureTimestamp = departureTimestamp;
    this.arrivalTimestamp = arrivalTimestamp;
    this.duration = duration;
    this.status = status;
    this.gate = gate;
    this.airline = airline;
  }

  @Override
  public SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return date;
    case 2: return from;
    case 3: return departureAirportCode;
    case 4: return arrivalAirportCode;
    case 5: return departureTime;
    case 6: return arrivalTime;
    case 7: return departureTimestamp;
    case 8: return arrivalTimestamp;
    case 9: return duration;
    case 10: return status;
    case 11: return gate;
    case 12: return airline;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value="unchecked")
  public void put(int field$, Object value$) {
    switch (field$) {
    case 0: id = (CharSequence)value$; break;
    case 1: date = (CharSequence)value$; break;
    case 2: from = (CharSequence)value$; break;
    case 3: departureAirportCode = (CharSequence)value$; break;
    case 4: arrivalAirportCode = (CharSequence)value$; break;
    case 5: departureTime = (CharSequence)value$; break;
    case 6: arrivalTime = (CharSequence)value$; break;
    case 7: departureTimestamp = (Long)value$; break;
    case 8: arrivalTimestamp = (Long)value$; break;
    case 9: duration = (Integer)value$; break;
    case 10: status = (CharSequence)value$; break;
    case 11: gate = (CharSequence)value$; break;
    case 12: airline = (CharSequence)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public CharSequence getId() {
    return id;
  }


  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(CharSequence value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'date' field.
   * @return The value of the 'date' field.
   */
  public CharSequence getDate() {
    return date;
  }


  /**
   * Sets the value of the 'date' field.
   * @param value the value to set.
   */
  public void setDate(CharSequence value) {
    this.date = value;
  }

  /**
   * Gets the value of the 'from' field.
   * @return The value of the 'from' field.
   */
  public CharSequence getFrom() {
    return from;
  }


  /**
   * Sets the value of the 'from' field.
   * @param value the value to set.
   */
  public void setFrom(CharSequence value) {
    this.from = value;
  }

  /**
   * Gets the value of the 'departureAirportCode' field.
   * @return The value of the 'departureAirportCode' field.
   */
  public CharSequence getDepartureAirportCode() {
    return departureAirportCode;
  }


  /**
   * Sets the value of the 'departureAirportCode' field.
   * @param value the value to set.
   */
  public void setDepartureAirportCode(CharSequence value) {
    this.departureAirportCode = value;
  }

  /**
   * Gets the value of the 'arrivalAirportCode' field.
   * @return The value of the 'arrivalAirportCode' field.
   */
  public CharSequence getArrivalAirportCode() {
    return arrivalAirportCode;
  }


  /**
   * Sets the value of the 'arrivalAirportCode' field.
   * @param value the value to set.
   */
  public void setArrivalAirportCode(CharSequence value) {
    this.arrivalAirportCode = value;
  }

  /**
   * Gets the value of the 'departureTime' field.
   * @return The value of the 'departureTime' field.
   */
  public CharSequence getDepartureTime() {
    return departureTime;
  }


  /**
   * Sets the value of the 'departureTime' field.
   * @param value the value to set.
   */
  public void setDepartureTime(CharSequence value) {
    this.departureTime = value;
  }

  /**
   * Gets the value of the 'arrivalTime' field.
   * @return The value of the 'arrivalTime' field.
   */
  public CharSequence getArrivalTime() {
    return arrivalTime;
  }


  /**
   * Sets the value of the 'arrivalTime' field.
   * @param value the value to set.
   */
  public void setArrivalTime(CharSequence value) {
    this.arrivalTime = value;
  }

  /**
   * Gets the value of the 'departureTimestamp' field.
   * @return The value of the 'departureTimestamp' field.
   */
  public long getDepartureTimestamp() {
    return departureTimestamp;
  }


  /**
   * Sets the value of the 'departureTimestamp' field.
   * @param value the value to set.
   */
  public void setDepartureTimestamp(long value) {
    this.departureTimestamp = value;
  }

  /**
   * Gets the value of the 'arrivalTimestamp' field.
   * @return The value of the 'arrivalTimestamp' field.
   */
  public long getArrivalTimestamp() {
    return arrivalTimestamp;
  }


  /**
   * Sets the value of the 'arrivalTimestamp' field.
   * @param value the value to set.
   */
  public void setArrivalTimestamp(long value) {
    this.arrivalTimestamp = value;
  }

  /**
   * Gets the value of the 'duration' field.
   * @return The value of the 'duration' field.
   */
  public int getDuration() {
    return duration;
  }


  /**
   * Sets the value of the 'duration' field.
   * @param value the value to set.
   */
  public void setDuration(int value) {
    this.duration = value;
  }

  /**
   * Gets the value of the 'status' field.
   * @return The value of the 'status' field.
   */
  public CharSequence getStatus() {
    return status;
  }


  /**
   * Sets the value of the 'status' field.
   * @param value the value to set.
   */
  public void setStatus(CharSequence value) {
    this.status = value;
  }

  /**
   * Gets the value of the 'gate' field.
   * @return The value of the 'gate' field.
   */
  public CharSequence getGate() {
    return gate;
  }


  /**
   * Sets the value of the 'gate' field.
   * @param value the value to set.
   */
  public void setGate(CharSequence value) {
    this.gate = value;
  }

  /**
   * Gets the value of the 'airline' field.
   * @return The value of the 'airline' field.
   */
  public CharSequence getAirline() {
    return airline;
  }


  /**
   * Sets the value of the 'airline' field.
   * @param value the value to set.
   */
  public void setAirline(CharSequence value) {
    this.airline = value;
  }

  /**
   * Creates a new TransformedFlight RecordBuilder.
   * @return A new TransformedFlight RecordBuilder
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Creates a new TransformedFlight RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new TransformedFlight RecordBuilder
   */
  public static Builder newBuilder(Builder other) {
    if (other == null) {
      return new Builder();
    } else {
      return new Builder(other);
    }
  }

  /**
   * Creates a new TransformedFlight RecordBuilder by copying an existing TransformedFlight instance.
   * @param other The existing instance to copy.
   * @return A new TransformedFlight RecordBuilder
   */
  public static Builder newBuilder(TransformedFlight other) {
    if (other == null) {
      return new Builder();
    } else {
      return new Builder(other);
    }
  }

  /**
   * RecordBuilder for TransformedFlight instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<TransformedFlight>
    implements org.apache.avro.data.RecordBuilder<TransformedFlight> {

    private CharSequence id;
    private CharSequence date;
    private CharSequence from;
    private CharSequence departureAirportCode;
    private CharSequence arrivalAirportCode;
    private CharSequence departureTime;
    private CharSequence arrivalTime;
    private long departureTimestamp;
    private long arrivalTimestamp;
    private int duration;
    private CharSequence status;
    private CharSequence gate;
    private CharSequence airline;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.date)) {
        this.date = data().deepCopy(fields()[1].schema(), other.date);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.from)) {
        this.from = data().deepCopy(fields()[2].schema(), other.from);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.departureAirportCode)) {
        this.departureAirportCode = data().deepCopy(fields()[3].schema(), other.departureAirportCode);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.arrivalAirportCode)) {
        this.arrivalAirportCode = data().deepCopy(fields()[4].schema(), other.arrivalAirportCode);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.departureTime)) {
        this.departureTime = data().deepCopy(fields()[5].schema(), other.departureTime);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (isValidValue(fields()[6], other.arrivalTime)) {
        this.arrivalTime = data().deepCopy(fields()[6].schema(), other.arrivalTime);
        fieldSetFlags()[6] = other.fieldSetFlags()[6];
      }
      if (isValidValue(fields()[7], other.departureTimestamp)) {
        this.departureTimestamp = data().deepCopy(fields()[7].schema(), other.departureTimestamp);
        fieldSetFlags()[7] = other.fieldSetFlags()[7];
      }
      if (isValidValue(fields()[8], other.arrivalTimestamp)) {
        this.arrivalTimestamp = data().deepCopy(fields()[8].schema(), other.arrivalTimestamp);
        fieldSetFlags()[8] = other.fieldSetFlags()[8];
      }
      if (isValidValue(fields()[9], other.duration)) {
        this.duration = data().deepCopy(fields()[9].schema(), other.duration);
        fieldSetFlags()[9] = other.fieldSetFlags()[9];
      }
      if (isValidValue(fields()[10], other.status)) {
        this.status = data().deepCopy(fields()[10].schema(), other.status);
        fieldSetFlags()[10] = other.fieldSetFlags()[10];
      }
      if (isValidValue(fields()[11], other.gate)) {
        this.gate = data().deepCopy(fields()[11].schema(), other.gate);
        fieldSetFlags()[11] = other.fieldSetFlags()[11];
      }
      if (isValidValue(fields()[12], other.airline)) {
        this.airline = data().deepCopy(fields()[12].schema(), other.airline);
        fieldSetFlags()[12] = other.fieldSetFlags()[12];
      }
    }

    /**
     * Creates a Builder by copying an existing TransformedFlight instance
     * @param other The existing instance to copy.
     */
    private Builder(TransformedFlight other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.date)) {
        this.date = data().deepCopy(fields()[1].schema(), other.date);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.from)) {
        this.from = data().deepCopy(fields()[2].schema(), other.from);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.departureAirportCode)) {
        this.departureAirportCode = data().deepCopy(fields()[3].schema(), other.departureAirportCode);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.arrivalAirportCode)) {
        this.arrivalAirportCode = data().deepCopy(fields()[4].schema(), other.arrivalAirportCode);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.departureTime)) {
        this.departureTime = data().deepCopy(fields()[5].schema(), other.departureTime);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.arrivalTime)) {
        this.arrivalTime = data().deepCopy(fields()[6].schema(), other.arrivalTime);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.departureTimestamp)) {
        this.departureTimestamp = data().deepCopy(fields()[7].schema(), other.departureTimestamp);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.arrivalTimestamp)) {
        this.arrivalTimestamp = data().deepCopy(fields()[8].schema(), other.arrivalTimestamp);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.duration)) {
        this.duration = data().deepCopy(fields()[9].schema(), other.duration);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.status)) {
        this.status = data().deepCopy(fields()[10].schema(), other.status);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.gate)) {
        this.gate = data().deepCopy(fields()[11].schema(), other.gate);
        fieldSetFlags()[11] = true;
      }
      if (isValidValue(fields()[12], other.airline)) {
        this.airline = data().deepCopy(fields()[12].schema(), other.airline);
        fieldSetFlags()[12] = true;
      }
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public CharSequence getId() {
      return id;
    }


    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public Builder setId(CharSequence value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public Builder clearId() {
      id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'date' field.
      * @return The value.
      */
    public CharSequence getDate() {
      return date;
    }


    /**
      * Sets the value of the 'date' field.
      * @param value The value of 'date'.
      * @return This builder.
      */
    public Builder setDate(CharSequence value) {
      validate(fields()[1], value);
      this.date = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'date' field has been set.
      * @return True if the 'date' field has been set, false otherwise.
      */
    public boolean hasDate() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'date' field.
      * @return This builder.
      */
    public Builder clearDate() {
      date = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'from' field.
      * @return The value.
      */
    public CharSequence getFrom() {
      return from;
    }


    /**
      * Sets the value of the 'from' field.
      * @param value The value of 'from'.
      * @return This builder.
      */
    public Builder setFrom(CharSequence value) {
      validate(fields()[2], value);
      this.from = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'from' field has been set.
      * @return True if the 'from' field has been set, false otherwise.
      */
    public boolean hasFrom() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'from' field.
      * @return This builder.
      */
    public Builder clearFrom() {
      from = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'departureAirportCode' field.
      * @return The value.
      */
    public CharSequence getDepartureAirportCode() {
      return departureAirportCode;
    }


    /**
      * Sets the value of the 'departureAirportCode' field.
      * @param value The value of 'departureAirportCode'.
      * @return This builder.
      */
    public Builder setDepartureAirportCode(CharSequence value) {
      validate(fields()[3], value);
      this.departureAirportCode = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'departureAirportCode' field has been set.
      * @return True if the 'departureAirportCode' field has been set, false otherwise.
      */
    public boolean hasDepartureAirportCode() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'departureAirportCode' field.
      * @return This builder.
      */
    public Builder clearDepartureAirportCode() {
      departureAirportCode = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'arrivalAirportCode' field.
      * @return The value.
      */
    public CharSequence getArrivalAirportCode() {
      return arrivalAirportCode;
    }


    /**
      * Sets the value of the 'arrivalAirportCode' field.
      * @param value The value of 'arrivalAirportCode'.
      * @return This builder.
      */
    public Builder setArrivalAirportCode(CharSequence value) {
      validate(fields()[4], value);
      this.arrivalAirportCode = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'arrivalAirportCode' field has been set.
      * @return True if the 'arrivalAirportCode' field has been set, false otherwise.
      */
    public boolean hasArrivalAirportCode() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'arrivalAirportCode' field.
      * @return This builder.
      */
    public Builder clearArrivalAirportCode() {
      arrivalAirportCode = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'departureTime' field.
      * @return The value.
      */
    public CharSequence getDepartureTime() {
      return departureTime;
    }


    /**
      * Sets the value of the 'departureTime' field.
      * @param value The value of 'departureTime'.
      * @return This builder.
      */
    public Builder setDepartureTime(CharSequence value) {
      validate(fields()[5], value);
      this.departureTime = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'departureTime' field has been set.
      * @return True if the 'departureTime' field has been set, false otherwise.
      */
    public boolean hasDepartureTime() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'departureTime' field.
      * @return This builder.
      */
    public Builder clearDepartureTime() {
      departureTime = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'arrivalTime' field.
      * @return The value.
      */
    public CharSequence getArrivalTime() {
      return arrivalTime;
    }


    /**
      * Sets the value of the 'arrivalTime' field.
      * @param value The value of 'arrivalTime'.
      * @return This builder.
      */
    public Builder setArrivalTime(CharSequence value) {
      validate(fields()[6], value);
      this.arrivalTime = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'arrivalTime' field has been set.
      * @return True if the 'arrivalTime' field has been set, false otherwise.
      */
    public boolean hasArrivalTime() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'arrivalTime' field.
      * @return This builder.
      */
    public Builder clearArrivalTime() {
      arrivalTime = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'departureTimestamp' field.
      * @return The value.
      */
    public long getDepartureTimestamp() {
      return departureTimestamp;
    }


    /**
      * Sets the value of the 'departureTimestamp' field.
      * @param value The value of 'departureTimestamp'.
      * @return This builder.
      */
    public Builder setDepartureTimestamp(long value) {
      validate(fields()[7], value);
      this.departureTimestamp = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'departureTimestamp' field has been set.
      * @return True if the 'departureTimestamp' field has been set, false otherwise.
      */
    public boolean hasDepartureTimestamp() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'departureTimestamp' field.
      * @return This builder.
      */
    public Builder clearDepartureTimestamp() {
      fieldSetFlags()[7] = false;
      return this;
    }

    /**
      * Gets the value of the 'arrivalTimestamp' field.
      * @return The value.
      */
    public long getArrivalTimestamp() {
      return arrivalTimestamp;
    }


    /**
      * Sets the value of the 'arrivalTimestamp' field.
      * @param value The value of 'arrivalTimestamp'.
      * @return This builder.
      */
    public Builder setArrivalTimestamp(long value) {
      validate(fields()[8], value);
      this.arrivalTimestamp = value;
      fieldSetFlags()[8] = true;
      return this;
    }

    /**
      * Checks whether the 'arrivalTimestamp' field has been set.
      * @return True if the 'arrivalTimestamp' field has been set, false otherwise.
      */
    public boolean hasArrivalTimestamp() {
      return fieldSetFlags()[8];
    }


    /**
      * Clears the value of the 'arrivalTimestamp' field.
      * @return This builder.
      */
    public Builder clearArrivalTimestamp() {
      fieldSetFlags()[8] = false;
      return this;
    }

    /**
      * Gets the value of the 'duration' field.
      * @return The value.
      */
    public int getDuration() {
      return duration;
    }


    /**
      * Sets the value of the 'duration' field.
      * @param value The value of 'duration'.
      * @return This builder.
      */
    public Builder setDuration(int value) {
      validate(fields()[9], value);
      this.duration = value;
      fieldSetFlags()[9] = true;
      return this;
    }

    /**
      * Checks whether the 'duration' field has been set.
      * @return True if the 'duration' field has been set, false otherwise.
      */
    public boolean hasDuration() {
      return fieldSetFlags()[9];
    }


    /**
      * Clears the value of the 'duration' field.
      * @return This builder.
      */
    public Builder clearDuration() {
      fieldSetFlags()[9] = false;
      return this;
    }

    /**
      * Gets the value of the 'status' field.
      * @return The value.
      */
    public CharSequence getStatus() {
      return status;
    }


    /**
      * Sets the value of the 'status' field.
      * @param value The value of 'status'.
      * @return This builder.
      */
    public Builder setStatus(CharSequence value) {
      validate(fields()[10], value);
      this.status = value;
      fieldSetFlags()[10] = true;
      return this;
    }

    /**
      * Checks whether the 'status' field has been set.
      * @return True if the 'status' field has been set, false otherwise.
      */
    public boolean hasStatus() {
      return fieldSetFlags()[10];
    }


    /**
      * Clears the value of the 'status' field.
      * @return This builder.
      */
    public Builder clearStatus() {
      status = null;
      fieldSetFlags()[10] = false;
      return this;
    }

    /**
      * Gets the value of the 'gate' field.
      * @return The value.
      */
    public CharSequence getGate() {
      return gate;
    }


    /**
      * Sets the value of the 'gate' field.
      * @param value The value of 'gate'.
      * @return This builder.
      */
    public Builder setGate(CharSequence value) {
      validate(fields()[11], value);
      this.gate = value;
      fieldSetFlags()[11] = true;
      return this;
    }

    /**
      * Checks whether the 'gate' field has been set.
      * @return True if the 'gate' field has been set, false otherwise.
      */
    public boolean hasGate() {
      return fieldSetFlags()[11];
    }


    /**
      * Clears the value of the 'gate' field.
      * @return This builder.
      */
    public Builder clearGate() {
      gate = null;
      fieldSetFlags()[11] = false;
      return this;
    }

    /**
      * Gets the value of the 'airline' field.
      * @return The value.
      */
    public CharSequence getAirline() {
      return airline;
    }


    /**
      * Sets the value of the 'airline' field.
      * @param value The value of 'airline'.
      * @return This builder.
      */
    public Builder setAirline(CharSequence value) {
      validate(fields()[12], value);
      this.airline = value;
      fieldSetFlags()[12] = true;
      return this;
    }

    /**
      * Checks whether the 'airline' field has been set.
      * @return True if the 'airline' field has been set, false otherwise.
      */
    public boolean hasAirline() {
      return fieldSetFlags()[12];
    }


    /**
      * Clears the value of the 'airline' field.
      * @return This builder.
      */
    public Builder clearAirline() {
      airline = null;
      fieldSetFlags()[12] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TransformedFlight build() {
      try {
        TransformedFlight record = new TransformedFlight();
        record.id = fieldSetFlags()[0] ? this.id : (CharSequence) defaultValue(fields()[0]);
        record.date = fieldSetFlags()[1] ? this.date : (CharSequence) defaultValue(fields()[1]);
        record.from = fieldSetFlags()[2] ? this.from : (CharSequence) defaultValue(fields()[2]);
        record.departureAirportCode = fieldSetFlags()[3] ? this.departureAirportCode : (CharSequence) defaultValue(fields()[3]);
        record.arrivalAirportCode = fieldSetFlags()[4] ? this.arrivalAirportCode : (CharSequence) defaultValue(fields()[4]);
        record.departureTime = fieldSetFlags()[5] ? this.departureTime : (CharSequence) defaultValue(fields()[5]);
        record.arrivalTime = fieldSetFlags()[6] ? this.arrivalTime : (CharSequence) defaultValue(fields()[6]);
        record.departureTimestamp = fieldSetFlags()[7] ? this.departureTimestamp : (Long) defaultValue(fields()[7]);
        record.arrivalTimestamp = fieldSetFlags()[8] ? this.arrivalTimestamp : (Long) defaultValue(fields()[8]);
        record.duration = fieldSetFlags()[9] ? this.duration : (Integer) defaultValue(fields()[9]);
        record.status = fieldSetFlags()[10] ? this.status : (CharSequence) defaultValue(fields()[10]);
        record.gate = fieldSetFlags()[11] ? this.gate : (CharSequence) defaultValue(fields()[11]);
        record.airline = fieldSetFlags()[12] ? this.airline : (CharSequence) defaultValue(fields()[12]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<TransformedFlight>
    WRITER$ = (org.apache.avro.io.DatumWriter<TransformedFlight>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<TransformedFlight>
    READER$ = (org.apache.avro.io.DatumReader<TransformedFlight>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.id);

    out.writeString(this.date);

    out.writeString(this.from);

    out.writeString(this.departureAirportCode);

    out.writeString(this.arrivalAirportCode);

    out.writeString(this.departureTime);

    out.writeString(this.arrivalTime);

    out.writeLong(this.departureTimestamp);

    out.writeLong(this.arrivalTimestamp);

    out.writeInt(this.duration);

    out.writeString(this.status);

    out.writeString(this.gate);

    out.writeString(this.airline);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.id = in.readString(this.id instanceof Utf8 ? (Utf8)this.id : null);

      this.date = in.readString(this.date instanceof Utf8 ? (Utf8)this.date : null);

      this.from = in.readString(this.from instanceof Utf8 ? (Utf8)this.from : null);

      this.departureAirportCode = in.readString(this.departureAirportCode instanceof Utf8 ? (Utf8)this.departureAirportCode : null);

      this.arrivalAirportCode = in.readString(this.arrivalAirportCode instanceof Utf8 ? (Utf8)this.arrivalAirportCode : null);

      this.departureTime = in.readString(this.departureTime instanceof Utf8 ? (Utf8)this.departureTime : null);

      this.arrivalTime = in.readString(this.arrivalTime instanceof Utf8 ? (Utf8)this.arrivalTime : null);

      this.departureTimestamp = in.readLong();

      this.arrivalTimestamp = in.readLong();

      this.duration = in.readInt();

      this.status = in.readString(this.status instanceof Utf8 ? (Utf8)this.status : null);

      this.gate = in.readString(this.gate instanceof Utf8 ? (Utf8)this.gate : null);

      this.airline = in.readString(this.airline instanceof Utf8 ? (Utf8)this.airline : null);

    } else {
      for (int i = 0; i < 13; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.id = in.readString(this.id instanceof Utf8 ? (Utf8)this.id : null);
          break;

        case 1:
          this.date = in.readString(this.date instanceof Utf8 ? (Utf8)this.date : null);
          break;

        case 2:
          this.from = in.readString(this.from instanceof Utf8 ? (Utf8)this.from : null);
          break;

        case 3:
          this.departureAirportCode = in.readString(this.departureAirportCode instanceof Utf8 ? (Utf8)this.departureAirportCode : null);
          break;

        case 4:
          this.arrivalAirportCode = in.readString(this.arrivalAirportCode instanceof Utf8 ? (Utf8)this.arrivalAirportCode : null);
          break;

        case 5:
          this.departureTime = in.readString(this.departureTime instanceof Utf8 ? (Utf8)this.departureTime : null);
          break;

        case 6:
          this.arrivalTime = in.readString(this.arrivalTime instanceof Utf8 ? (Utf8)this.arrivalTime : null);
          break;

        case 7:
          this.departureTimestamp = in.readLong();
          break;

        case 8:
          this.arrivalTimestamp = in.readLong();
          break;

        case 9:
          this.duration = in.readInt();
          break;

        case 10:
          this.status = in.readString(this.status instanceof Utf8 ? (Utf8)this.status : null);
          break;

        case 11:
          this.gate = in.readString(this.gate instanceof Utf8 ? (Utf8)this.gate : null);
          break;

        case 12:
          this.airline = in.readString(this.airline instanceof Utf8 ? (Utf8)this.airline : null);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










