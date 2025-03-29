package gdu.admin_service.dto.request.order;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetProductInfoOutputDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private InventoryDTO inventory;
    private List<ImageDTO> images;

    @Data
    @Builder
    public static class InventoryDTO {
        private Long id;
        private Integer stock;
        private String status;
        private String updatedAt;
    }

    @Data
    @Builder
    public static class ImageDTO {
        private Long id;
        private String src;
        private Integer position;
    }
}
