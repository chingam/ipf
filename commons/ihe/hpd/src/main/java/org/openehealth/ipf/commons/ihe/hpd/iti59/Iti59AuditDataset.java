/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openehealth.ipf.commons.ihe.hpd.iti59;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.openehealth.ipf.commons.audit.codes.EventActionCode;
import org.openehealth.ipf.commons.audit.codes.EventOutcomeIndicator;
import org.openehealth.ipf.commons.audit.codes.ParticipantObjectTypeCode;
import org.openehealth.ipf.commons.ihe.ws.cxf.audit.WsAuditDataset;

/**
 * Audit dataset for the ITI-59 transaction.
 * @author Dmytro Rud
 */
public class Iti59AuditDataset extends WsAuditDataset {

    @RequiredArgsConstructor
    public static class RequestItem {
        @Getter private final String requestId;
        @Getter private final EventActionCode actionCode;

        @Getter @Setter private String uid;
        @Getter @Setter private String newUid;    // only for operation Rename
        @Getter @Setter private ParticipantObjectTypeCode participantObjectTypeCode;

        @Getter @Setter private EventOutcomeIndicator outcomeCode;
        @Getter @Setter private String outcomeDescription;
    }

    @Getter @Setter private RequestItem[] requestItems;

    public Iti59AuditDataset(boolean serverSide) {
        super(serverSide);
    }
}
