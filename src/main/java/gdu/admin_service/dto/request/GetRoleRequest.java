package gdu.admin_service.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetRoleRequest {
    private byte size;
    private byte page;
}
