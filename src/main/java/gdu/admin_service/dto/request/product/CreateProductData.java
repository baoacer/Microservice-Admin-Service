package gdu.admin_service.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductData {
    private String name;
    private Short quantity;
    private String description;
    private BigDecimal price;
    private Byte categoryId;
    private String imageSrc;
}
