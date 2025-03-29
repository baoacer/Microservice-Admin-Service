package gdu.admin_service.dto.request.product;

import gdu.admin_service.dto.model.ImagesDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    private String name;
    private short quantity;
    private String description;
    private BigDecimal price;
    private byte categoryId;
    private List<ImagesDto> images;
}
