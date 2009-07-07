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
package org.openehealth.ipf.platform.camel.ihe.xds.commons.transform.responses;

import static org.apache.commons.lang.Validate.notNull;

import org.openehealth.ipf.platform.camel.ihe.xds.commons.ebxml.EbXMLFactory;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.ebxml.EbXMLRetrieveDocumentSetResponse;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.responses.RetrievedDocumentSet;

/**
 * Transforms between a {@link EbXMLRetrieveDocumentSetResponse} and its ebXML representation. 
 * @author Jens Riemschneider
 */
public class RetrieveDocumentSetResponseTransformer {
    private final EbXMLFactory factory;
    
    /**
     * Constructs the transformer.
     * @param factory
     *          the factory for ebXML objects.
     */
    public RetrieveDocumentSetResponseTransformer(EbXMLFactory factory) {
        notNull(factory, "factory cannot be null");
        this.factory = factory;
    }
    
    /**
     * Transforms a {@link RetrievedDocumentSet} to a {@link EbXMLRetrieveDocumentSetResponse}.
     * @param response
     *          the response. Can be <code>null</code>.
     * @return the ebXML representation. <code>null</code> if the input was <code>null</code>.
     */
    public EbXMLRetrieveDocumentSetResponse toEbXML(RetrievedDocumentSet response) {
        if (response == null) {
            return null;
        }
        
        EbXMLRetrieveDocumentSetResponse ebXML = factory.createRetrieveDocumentSetResponse();
        ebXML.setErrors(response.getErrors());
        ebXML.setStatus(response.getStatus());
        ebXML.setDocuments(response.getDocuments());        
        return ebXML;
    }
    
    /**
     * Transforms a {@link EbXMLRetrieveDocumentSetResponse} to a {@link RetrievedDocumentSet}.
     * @param ebXML
     *          the ebXML representation. Can be <code>null</code>.
     * @return the response. <code>null</code> if the input was <code>null</code>.
     */
    public RetrievedDocumentSet fromEbXML(EbXMLRetrieveDocumentSetResponse ebXML) {
        if (ebXML == null) {
            return null;
        }
            
        RetrievedDocumentSet response = new RetrievedDocumentSet();
        response.getDocuments().addAll(ebXML.getDocuments());
        response.getErrors().addAll(ebXML.getErrors());
        response.setStatus(ebXML.getStatus());
        return response;
    }
}
