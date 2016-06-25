package io.irontest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.irontest.models.assertion.Assertion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Zheng on 7/07/2015.
 */
public class Teststep {
    public static final String TEST_STEP_TYPE_SOAP = "SOAP";
    public static final String TEST_STEP_TYPE_DB = "DB";
    public static final String TEST_STEP_TYPE_IIB = "IIB";
    public static final String TEST_STEP_TYPE_MQ = "MQ";
    public static final String TEST_STEP_TYPE_WAIT = "Wait";

    private long id;
    private long testcaseId;
    private short sequence;
    private String name;
    private String type;
    private String description;
    private Object request;
    private Endpoint endpoint;
    private List<Assertion> assertions = new ArrayList<Assertion>();
    private Date created;
    private Date updated;
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type", visible = true, defaultImpl = Properties.class)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = IIBTeststepProperties.class, name = Teststep.TEST_STEP_TYPE_IIB),
            @JsonSubTypes.Type(value = MQTeststepProperties.class, name = Teststep.TEST_STEP_TYPE_MQ),
            @JsonSubTypes.Type(value = WaitTeststepProperties.class, name = Teststep.TEST_STEP_TYPE_WAIT)})
    private Properties otherProperties;

    public Teststep() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTestcaseId() {
        return testcaseId;
    }

    public void setTestcaseId(long testcaseId) {
        this.testcaseId = testcaseId;
    }

    public short getSequence() {
        return sequence;
    }

    public void setSequence(short sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Properties getOtherProperties() {
        return otherProperties;
    }

    public void setOtherProperties(Properties otherProperties) {
        this.otherProperties = otherProperties;
    }

    public List<Assertion> getAssertions() {
        return assertions;
    }

    public void setAssertions(List<Assertion> assertions) {
        this.assertions = assertions;
    }

    @JsonIgnore
    public boolean isRequestBinary() {
        boolean result = false;
        if (otherProperties instanceof MQTeststepProperties) {
            MQTeststepProperties properties = (MQTeststepProperties) otherProperties;
            result = MQTeststepProperties.ACTION_TYPE_ENQUEUE.equals(properties.getAction()) &&
                    MQTeststepProperties.ENQUEUE_MESSAGE_TYPE_BINARY.equals(properties.getEnqueueMessageType());
        }
        return result;
    }
}