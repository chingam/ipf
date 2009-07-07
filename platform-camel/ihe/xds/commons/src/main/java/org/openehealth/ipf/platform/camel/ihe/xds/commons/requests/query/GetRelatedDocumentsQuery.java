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
package org.openehealth.ipf.platform.camel.ihe.xds.commons.requests.query;

import java.util.ArrayList;
import java.util.List;

import org.openehealth.ipf.platform.camel.ihe.xds.commons.metadata.Association;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.metadata.AssociationType;

/**
 * Represents a stored query for GetRelatedDocuments.
 * <p>
 * All non-list members of this class are allowed to be <code>null</code>.
 * The lists are pre-created and can therefore never be <code>null</code>.
 * @author Jens Riemschneider
 */
public class GetRelatedDocumentsQuery extends GetByIDQuery {
    private final List<AssociationType> associationTypes = new ArrayList<AssociationType>();

    /**
     * Constructs the query.
     */
    public GetRelatedDocumentsQuery() {
        super(QueryType.GET_RELATED_DOCUMENTS);
    }

    /**
     * @return the types used for filtering {@link Association#getAssociationType()}.
     */
    public List<AssociationType> getAssociationTypes() {
        return associationTypes;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
