package gdu.admin_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllUserRequest {
    private byte size;
    private byte page;
}
