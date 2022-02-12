package com.gistpetition.api.petition.dto;

import com.gistpetition.api.petition.domain.Agreement;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class AgreementResponse {

    private final Long id;
    private final String description;

    public static AgreementResponse of(Agreement agreement) {
        return new AgreementResponse(agreement.getId(), agreement.getDescription());
    }

    public static Page<AgreementResponse> pageOf(Page<Agreement> agreements) {
        return agreements.map(AgreementResponse::of);
    }
}
