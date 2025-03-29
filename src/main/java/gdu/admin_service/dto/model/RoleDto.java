package gdu.admin_service.dto.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {
    private int id;
    private String name;
}
