package gdu.admin_service.dto.response;

import gdu.admin_service.dto.model.ImagesDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UpdateProductResponse {
    private short id;
    private String name;
    private String description;
    private BigDecimal price;
    private Byte categoryId;
    private Short stock;
    private List<ImagesDto> images;
}
