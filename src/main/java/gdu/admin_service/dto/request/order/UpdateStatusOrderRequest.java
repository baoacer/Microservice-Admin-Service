package gdu.admin_service.dto.request.order;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateStatusOrderRequest {
    private short orderId;
    private String status;
}

