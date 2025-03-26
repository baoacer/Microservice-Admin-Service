package gdu.admin_service.dto.request.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCategoryRequest {
    @NotEmpty
    private String name;
}
