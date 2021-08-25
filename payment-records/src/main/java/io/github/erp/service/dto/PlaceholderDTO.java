package io.github.erp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link io.github.erp.domain.Placeholder} entity.
 */
public class PlaceholderDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String description;

    
    private String token;

    
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaceholderDTO)) {
            return false;
        }

        return id != null && id.equals(((PlaceholderDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaceholderDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", token='" + getToken() + "'" +
            "}";
    }
}
