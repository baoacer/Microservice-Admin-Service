package gdu.admin_service.dto.request.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllProductRequest {
    private byte size;
    private byte page;
}
