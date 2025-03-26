package gdu.admin_service.dto.request.inventory;

import gdu.admin_service.utils.InventoryStatus;
import lombok.Data;

@Data
public class CreateInventoryRequest {
    private Short productId;
    private Short quantity;
    private InventoryStatus status;
}
