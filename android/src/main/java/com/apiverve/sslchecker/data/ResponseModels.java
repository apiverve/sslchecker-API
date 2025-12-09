// Converter.java

// To use this code, add the following Maven dependency to your project:
//
//
//     com.fasterxml.jackson.core     : jackson-databind          : 2.9.0
//     com.fasterxml.jackson.datatype : jackson-datatype-jsr310   : 2.9.0
//
// Import this package:
//
//     import com.apiverve.data.Converter;
//
// Then you can deserialize a JSON string with
//
//     SSLCertificateCheckerData data = Converter.fromJsonString(jsonString);

package com.apiverve.sslchecker.data;

import java.io.IOException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Converter {
    // Date-time helpers

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_INSTANT)
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetDateTime parseDateTimeString(String str) {
        return ZonedDateTime.from(Converter.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
    }

    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
            .parseDefaulting(ChronoField.YEAR, 2020)
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetTime parseTimeString(String str) {
        return ZonedDateTime.from(Converter.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
    }
    // Serialize/deserialize helpers

    public static SSLCertificateCheckerData fromJsonString(String json) throws IOException {
        return getObjectReader().readValue(json);
    }

    public static String toJsonString(SSLCertificateCheckerData obj) throws JsonProcessingException {
        return getObjectWriter().writeValueAsString(obj);
    }

    private static ObjectReader reader;
    private static ObjectWriter writer;

    private static void instantiateMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                String value = jsonParser.getText();
                return Converter.parseDateTimeString(value);
            }
        });
        mapper.registerModule(module);
        reader = mapper.readerFor(SSLCertificateCheckerData.class);
        writer = mapper.writerFor(SSLCertificateCheckerData.class);
    }

    private static ObjectReader getObjectReader() {
        if (reader == null) instantiateMapper();
        return reader;
    }

    private static ObjectWriter getObjectWriter() {
        if (writer == null) instantiateMapper();
        return writer;
    }
}

// SSLCertificateCheckerData.java

package com.apiverve.sslchecker.data;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;

public class SSLCertificateCheckerData {
    private Issuer subject;
    private Issuer issuer;
    private String subjectaltname;
    private Map<String, String[]> infoAccess;
    private boolean ca;
    private long bits;
    private String validFrom;
    private String validTo;
    private String serialNumber;
    private String domain;

    @JsonProperty("subject")
    public Issuer getSubject() { return subject; }
    @JsonProperty("subject")
    public void setSubject(Issuer value) { this.subject = value; }

    @JsonProperty("issuer")
    public Issuer getIssuer() { return issuer; }
    @JsonProperty("issuer")
    public void setIssuer(Issuer value) { this.issuer = value; }

    @JsonProperty("subjectaltname")
    public String getSubjectaltname() { return subjectaltname; }
    @JsonProperty("subjectaltname")
    public void setSubjectaltname(String value) { this.subjectaltname = value; }

    @JsonProperty("infoAccess")
    public Map<String, String[]> getInfoAccess() { return infoAccess; }
    @JsonProperty("infoAccess")
    public void setInfoAccess(Map<String, String[]> value) { this.infoAccess = value; }

    @JsonProperty("ca")
    public boolean getCA() { return ca; }
    @JsonProperty("ca")
    public void setCA(boolean value) { this.ca = value; }

    @JsonProperty("bits")
    public long getBits() { return bits; }
    @JsonProperty("bits")
    public void setBits(long value) { this.bits = value; }

    @JsonProperty("valid_from")
    public String getValidFrom() { return validFrom; }
    @JsonProperty("valid_from")
    public void setValidFrom(String value) { this.validFrom = value; }

    @JsonProperty("valid_to")
    public String getValidTo() { return validTo; }
    @JsonProperty("valid_to")
    public void setValidTo(String value) { this.validTo = value; }

    @JsonProperty("serialNumber")
    public String getSerialNumber() { return serialNumber; }
    @JsonProperty("serialNumber")
    public void setSerialNumber(String value) { this.serialNumber = value; }

    @JsonProperty("domain")
    public String getDomain() { return domain; }
    @JsonProperty("domain")
    public void setDomain(String value) { this.domain = value; }
}

// Issuer.java

package com.apiverve.sslchecker.data;

import com.fasterxml.jackson.annotation.*;

public class Issuer {
    private String c;
    private String st;
    private String l;
    private String o;
    private String cn;

    @JsonProperty("C")
    public String getC() { return c; }
    @JsonProperty("C")
    public void setC(String value) { this.c = value; }

    @JsonProperty("ST")
    public String getSt() { return st; }
    @JsonProperty("ST")
    public void setSt(String value) { this.st = value; }

    @JsonProperty("L")
    public String getL() { return l; }
    @JsonProperty("L")
    public void setL(String value) { this.l = value; }

    @JsonProperty("O")
    public String getO() { return o; }
    @JsonProperty("O")
    public void setO(String value) { this.o = value; }

    @JsonProperty("CN")
    public String getCN() { return cn; }
    @JsonProperty("CN")
    public void setCN(String value) { this.cn = value; }
}