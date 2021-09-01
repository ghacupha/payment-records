package io.github.erp.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link io.github.erp.domain.PaymentLabel} entity.
 */
public class PaymentLabelDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String description;

    private String comments;

    private LocalDate schedule;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getSchedule() {
        return schedule;
    }

    public void setSchedule(LocalDate schedule) {
        this.schedule = schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentLabelDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentLabelDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentLabelDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", comments='" + getComments() + "'" +
            ", schedule='" + getSchedule() + "'" +
            "}";
    }
}
