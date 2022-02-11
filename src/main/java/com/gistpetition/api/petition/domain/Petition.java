package com.gistpetition.api.petition.domain;

import com.gistpetition.api.common.persistence.BaseEntity;
import com.gistpetition.api.exception.petition.DuplicatedAgreementException;
import com.gistpetition.api.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Petition extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Boolean answered;
    private Boolean exposed;
    private Long userId;
    private Long agreeCount = 0L;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "petition_id")
    private final List<Agreement> agreements = new ArrayList<>();

    public static final Long REQUIRED_AGREEMENT_NUM = 5L;

    protected Petition() {
    }

    public Petition(String title, String description, Category category, Long userId) {
        this(null, title, description, category, false, false, userId);
    }

    public Petition(Long id, String title, String description, Category category, Boolean answered, Boolean exposed, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.answered = answered;
        this.exposed = exposed;
        this.userId = userId;
    }

    public void applyAgreement(User user) {
        for (Agreement agreement : agreements) {
            if (agreement.isAgreedBy(user.getId())) {
                throw new DuplicatedAgreementException();
            }
        }
        this.agreements.add(new Agreement(user.getId()));
        agreeCount += 1;
    }

    public boolean isAnswered() {
        return answered;
    }

    public boolean isAgreedBy(User user) {
        for (Agreement agreement : agreements) {
            if (agreement.isAgreedBy(user.getId())) {
                return true;
            }
        }
        return false;
    }

    public void setAnswered(boolean b) {
        this.answered = b;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExposed(boolean b) {
        this.exposed = b;
    }
}
