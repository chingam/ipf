/*
 * Copyright 2012 the original author or authors.
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
package org.openehealth.ipf.commons.ihe.xds.rad69;

import org.openehealth.ipf.commons.ihe.core.atna.AuditorManager;
import org.openehealth.ipf.commons.ihe.xds.core.audit.XdsIRetrieveAuditStrategy30;
import org.openehealth.ipf.commons.ihe.xds.core.audit.XdsNonconstructiveDocumentSetRequestAuditDataset;
import org.openehealth.ipf.commons.ihe.xds.core.audit.XdsNonconstructiveDocumentSetRequestAuditDataset.Status;

/**
 * Audit strategy for RAD-69.
 * @author Clay Sebourn
 */
public class Rad69AuditStrategy extends XdsIRetrieveAuditStrategy30 {

    public Rad69AuditStrategy(boolean serverSide) {
        super(serverSide);
    }


    @Override
    public void doAudit(XdsNonconstructiveDocumentSetRequestAuditDataset auditDataset) {
        for (Status status : Status.values()) {
            if (auditDataset.hasDocuments(status)) {
                AuditorManager.getCustomXdsAuditor().auditRad69(
                        isServerSide(),
                        auditDataset.getEventOutcomeCode(status),
                        auditDataset.getUserId(),
                        auditDataset.getUserName(),
                        auditDataset.getServiceEndpointUrl(),
                        auditDataset.getClientIpAddress(),
                        auditDataset.getStudyInstanceIds(status),
                        auditDataset.getSeriesInstanceIds(status),
                        auditDataset.getDocumentIds(status),
                        auditDataset.getRepositoryIds(status),
                        auditDataset.getHomeCommunityIds(status),
                        auditDataset.getPatientId(),
                        auditDataset.getPurposesOfUse());    
            }
        }
    }

}
