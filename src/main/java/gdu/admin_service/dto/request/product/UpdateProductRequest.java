package gdu.admin_service.dto.request.product;

import gdu.admin_service.dto.model.ImagesDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class UpdateProductRequest {
    private short productId;
    private String name;
    private String description;
    private String category;
    private short quantity;
    private BigDecimal price;
    private List<ImagesDto> images;
}
