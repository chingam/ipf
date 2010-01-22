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
package org.openehealth.ipf.platform.camel.ihe.pdqv3.iti47.component;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.openehealth.ipf.commons.ihe.ws.ItiClientFactory;
import org.openehealth.ipf.commons.ihe.ws.ItiServiceFactory;
import org.openehealth.ipf.platform.camel.ihe.pdqv3.iti47.Iti47;
import org.openehealth.ipf.platform.camel.ihe.pdqv3.iti47.service.Iti47Service;
import org.openehealth.ipf.platform.camel.ihe.ws.DefaultItiConsumer;
import org.openehealth.ipf.platform.camel.ihe.ws.DefaultItiEndpoint;
import org.openehealth.ipf.platform.camel.ihe.ws.DefaultItiWebService;

import java.net.URISyntaxException;

/**
 * The Camel endpoint for the ITI-47 transaction.
 */
public class Iti47Endpoint extends DefaultItiEndpoint {
    /**
     * Constructs the endpoint.
     * @param endpointUri
     *          the endpoint URI.
     * @param address
     *          the endpoint address from the URI.
     * @param iti47Component
     *          the component creating this endpoint.
     * @throws URISyntaxException
     *          if the endpoint URI was not a valid URI.
     */
    public Iti47Endpoint(
            String endpointUri, 
            String address, 
            Iti47Component iti47Component) throws URISyntaxException 
    {
        super(endpointUri, address, iti47Component);
    }

    public Producer createProducer() throws Exception {
        ItiClientFactory clientFactory = 
            Iti47.getClientFactory(isAudit(), isAllowIncompleteAudit(), getServiceUrl());
        return new Iti47Producer(this, clientFactory);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        ItiServiceFactory serviceFactory = 
            Iti47.getServiceFactory(isAudit(), isAllowIncompleteAudit(), getServiceAddress());
        ServerFactoryBean serverFactory =
            serviceFactory.createServerFactory(Iti47Service.class);
        Server server = serverFactory.create();
        DefaultItiWebService service = (DefaultItiWebService) serverFactory.getServiceBean();
        return new DefaultItiConsumer(this, processor, service, server);
    }
}