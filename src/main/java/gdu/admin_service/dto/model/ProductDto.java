package gdu.admin_service.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private short id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private InventoryDto inventory;
    private List<ImagesDto> images;
}
