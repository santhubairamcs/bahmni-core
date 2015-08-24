package org.bahmni.module.bahmnicoreui.contract;

import org.openmrs.*;

import java.io.Serializable;
import java.util.Date;

public class BahmniFormElement implements OpenmrsObject, Auditable, Voidable, Serializable {
    private Integer bahmniFormElementId;
    private Boolean voided;
    private ConceptSet conceptSetId;
    private Integer multiple;
    private Boolean conciseText;
    private Boolean durationRequired;
    private Boolean multiSelect;
    private Boolean computeDrugs;
    private Boolean allowAddMore;
    private Boolean freeTextAutocomplete;
    private Boolean grid;
    private Boolean buttonSelect;
    private Boolean required;
    private Boolean autocomplete;
    private Boolean showPreviousButton;
    private Integer numberOfVisits;
    private Concept answerConcept;
    private User creator;
    private Date dateCreated;
    private User changedBy;
    private Date dateChanged;
    private String uuid;
    private User voidedBy;
    private Date dateVoided;
    private String voidReason;

    public BahmniFormElement() {
    }

    public Concept getAnswerConcept() {
        return answerConcept;
    }

    public void setAnswerConcept(Concept answerConcept) {
        this.answerConcept = answerConcept;
    }

    public Integer getNumberOfVisits() {
        return numberOfVisits;
    }

    public void setNumberOfVisits(Integer numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }

    public Boolean getShowPreviousButton() {
        return showPreviousButton;
    }

    public void setShowPreviousButton(Boolean showPreviousButton) {
        this.showPreviousButton = showPreviousButton;
    }

    public Boolean getAutocomplete() {
        return autocomplete;
    }

    public void setAutocomplete(Boolean autocomplete) {
        this.autocomplete = autocomplete;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getButtonSelect() {
        return buttonSelect;
    }

    public void setButtonSelect(Boolean buttonSelect) {
        this.buttonSelect = buttonSelect;
    }

    public Boolean getGrid() {
        return grid;
    }

    public void setGrid(Boolean grid) {
        this.grid = grid;
    }

    public Boolean getFreeTextAutocomplete() {
        return freeTextAutocomplete;
    }

    public void setFreeTextAutocomplete(Boolean freeTextAutocomplete) {
        this.freeTextAutocomplete = freeTextAutocomplete;
    }

    public Boolean getAllowAddMore() {
        return allowAddMore;
    }

    public void setAllowAddMore(Boolean allowAddMore) {
        this.allowAddMore = allowAddMore;
    }

    public Boolean getComputeDrugs() {
        return computeDrugs;
    }

    public void setComputeDrugs(Boolean computeDrugs) {
        this.computeDrugs = computeDrugs;
    }

    public Boolean getMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(Boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    public Boolean getDurationRequired() {
        return durationRequired;
    }

    public void setDurationRequired(Boolean durationRequired) {
        this.durationRequired = durationRequired;
    }

    public Boolean getConciseText() {
        return conciseText;
    }

    public void setConciseText(Boolean conciseText) {
        this.conciseText = conciseText;
    }

    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    public ConceptSet getConceptSetId() {
        return conceptSetId;
    }

    public void setConceptSetId(ConceptSet conceptSetId) {
        this.conceptSetId = conceptSetId;
    }

    public Boolean getVoided() {
        return voided;
    }

    public Integer getBahmniFormElementId() {
        return bahmniFormElementId;
    }

    public void setBahmniFormElementId(Integer bahmniFormElementId) {
        this.bahmniFormElementId = bahmniFormElementId;
    }


    @Override
    public User getCreator() {
        return creator;
    }

    @Override
    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Override
    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public User getChangedBy() {
        return changedBy;
    }

    @Override
    public void setChangedBy(User changedBy) {
        this.changedBy = changedBy;
    }

    @Override
    public Date getDateChanged() {
        return dateChanged;
    }

    @Override
    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }

    @Override
    public Integer getId() {
        return getBahmniFormElementId();
    }

    @Override
    public void setId(Integer id) {
        setBahmniFormElementId(id);
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public Boolean isVoided() {
        return voided;
    }

    @Override
    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

    @Override
    public User getVoidedBy() {
        return voidedBy;
    }

    @Override
    public void setVoidedBy(User voidedBy) {
        this.voidedBy = voidedBy;
    }

    @Override
    public Date getDateVoided() {
        return dateVoided;
    }

    @Override
    public void setDateVoided(Date dateVoided) {
        this.dateVoided = dateVoided;
    }

    @Override
    public String getVoidReason() {
        return voidReason;
    }

    @Override
    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }
}
