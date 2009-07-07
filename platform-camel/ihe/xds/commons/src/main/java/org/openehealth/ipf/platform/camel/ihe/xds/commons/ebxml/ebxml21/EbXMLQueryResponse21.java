/*
 * Copyright 2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openehealth.ipf.platform.camel.ihe.xds.commons.ebxml.ebxml21;

import static org.apache.commons.lang.Validate.notNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openehealth.ipf.platform.camel.ihe.xds.commons.ebxml.EbXMLQueryResponse;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.ebxml.EbXMLObjectLibrary;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.metadata.ObjectReference;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.responses.ErrorCode;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.responses.ErrorInfo;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.responses.Severity;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.responses.Status;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.stub.ebrs21.query.AdhocQueryResponse;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.stub.ebrs21.rim.ObjectRefType;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.stub.ebrs21.rim.RegistryObjectListType;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.stub.ebrs21.rs.RegistryError;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.stub.ebrs21.rs.RegistryErrorList;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.stub.ebrs21.rs.RegistryResponse;

/**
 * Encapsulation of {@link RegistryResponse}.
 * @author Jens Riemschneider
 */
public class EbXMLQueryResponse21 extends EbXMLBaseObjectContainer21 implements EbXMLQueryResponse {
    private final RegistryResponse regResponse;
    
    /**
     * Constructs the response wrapper.
     * @param regResponse
     *          the ebXML 2.1 response object to wrap. 
     * @param objectLibrary
     *          the object library to use.
     */
    public EbXMLQueryResponse21(RegistryResponse regResponse, EbXMLObjectLibrary objectLibrary) {
        super(objectLibrary);
        notNull(regResponse, "regResponse cannot be null");
        this.regResponse = regResponse;
    }
    
    /**
     * Constructs the response wrapper using a new {@link EbXMLObjectLibrary}.
     * <p>
     * The object library is filled with the objects from the given response.
     * @param regResponse
     *          the ebXML 2.1 response object to wrap. 
     */
    public EbXMLQueryResponse21(RegistryResponse regResponse) {
        this(regResponse, new EbXMLObjectLibrary());
        fillObjectLibrary();        
    }

    @Override
    protected List<Object> getContents() {
        AdhocQueryResponse adhocQueryResponse = regResponse.getAdhocQueryResponse();
        if (adhocQueryResponse == null) {
            return Collections.emptyList();
        }
        RegistryObjectListType list = adhocQueryResponse.getSQLQueryResult();
        if (list == null) {
            return Collections.emptyList();
        }
        return list.getObjectRefOrAssociationOrAuditableEvent();
    }

    @Override
    public void setStatus(Status status) {
        regResponse.setStatus(Status.getOpcode21(status));
    }
    
    @Override
    public Status getStatus() {
        return Status.valueOfOpcode(regResponse.getStatus());
    }
    
    @Override
    public List<ErrorInfo> getErrors() {
        RegistryErrorList list = regResponse.getRegistryErrorList();
        if (list == null) {
            return Collections.emptyList();
        }
        
        List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
        for (RegistryError regError : list.getRegistryError()) {
            ErrorInfo error = new ErrorInfo();
            error.setCodeContext(regError.getCodeContext());
            error.setLocation(regError.getLocation());
            error.setErrorCode(ErrorCode.valueOfOpcode(regError.getErrorCode()));
            error.setSeverity(Severity.valueOfEbXML21(regError.getSeverity()));
            errors.add(error);
        }
        
        return errors;
    }

    @Override
    public void setErrors(List<ErrorInfo> errors) {
        RegistryErrorList value = EbXMLFactory21.RS_FACTORY.createRegistryErrorList();
        regResponse.setRegistryErrorList(value);
        List<RegistryError> list = value.getRegistryError();
        for (ErrorInfo error : errors) {
            RegistryError regError = EbXMLFactory21.RS_FACTORY.createRegistryError();
            regError.setErrorCode(ErrorCode.getOpcode(error.getErrorCode()));
            regError.setCodeContext(error.getCodeContext());
            regError.setSeverity(Severity.getEbXML21(error.getSeverity()));
            regError.setLocation(error.getLocation());
            list.add(regError);
        }
    }
    
    @Override
    public void addReference(ObjectReference ref) {
        if (ref != null) {
            ObjectRefType objectRef = EbXMLFactory21.RIM_FACTORY.createObjectRefType();
            objectRef.setId(ref.getId());
            // Home not supported in 2.1
            getContents().add(objectRef);
        }
    }

    @Override
    public RegistryResponse getInternal() {
        return regResponse;
    }
}
