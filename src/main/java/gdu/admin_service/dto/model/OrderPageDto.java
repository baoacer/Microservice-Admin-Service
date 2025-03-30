package gdu.admin_service.dto.model;

import gdu.admin_service.dto.request.order.GetAllOrderOutputDTO;
import lombok.Data;

import java.util.List;

@Data
public class OrderPageDto {
    private List<GetAllOrderOutputDTO> content;
    private int number;
    private int size;
    private long totalElements;
    private long totalPages;
}
