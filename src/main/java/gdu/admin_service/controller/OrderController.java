package gdu.admin_service.controller;


import gdu.admin_service.dto.model.*;
import gdu.admin_service.dto.request.order.GetAllOrderOutputDTO;
import gdu.admin_service.dto.request.order.UpdateStatusOrderRequest;
import gdu.admin_service.dto.request.product.CreateProductData;
import gdu.admin_service.dto.request.product.CreateProductRequest;
import gdu.admin_service.dto.request.product.UpdateProductRequest;
import gdu.admin_service.dto.response.ObjectResponse;
import gdu.admin_service.dto.response.UpdateProductResponse;
import gdu.admin_service.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class OrderController {

    private final RestTemplate restTemplate;
    private final String urlOrder = "http://localhost:8051/api/order";

    @GetMapping("/order/home")
    public String getAllUsers(
            @RequestParam(defaultValue = "5") byte size,
            @RequestParam(defaultValue = "0") byte page,
            Model model
    ) {

        String fullUrl = urlOrder + "?size=" + size + "&page=" + page;

        ResponseEntity<OrderPageDto> response = restTemplate.exchange(
                fullUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<OrderPageDto>() {}
        );

        OrderPageDto responseBody = response.getBody();
        assert responseBody != null;

        Page<GetAllOrderOutputDTO> pageResponse = new PageImpl<>(
                responseBody.getContent(),
                PageRequest.of(responseBody.getNumber(), responseBody.getSize()),
                responseBody.getTotalElements()
        );

        List<OrderDto> orders = pageResponse.getContent().stream().map(order -> OrderDto
                        .builder()
                        .orderId(order.getId())
                        .status(order.getStatus())
                        .orderTime(order.getOrderDate())
                        .totalPrice(order.getTotalAmount())
                        .build()).toList();

        model.addAttribute("size", size);
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", responseBody.getTotalPages());
        model.addAttribute("activePage", "orders");

        return "admin/admin-order";
    }


    @PostMapping("/order/status/{orderId}/{status}")
    public String updateOrderStatus(
            @PathVariable String status,
            @PathVariable Short orderId
    ) {
        String fullUrl = urlOrder + "/status";
        UpdateStatusOrderRequest request = UpdateStatusOrderRequest.builder()
                .orderId(orderId)
                .status(status)
                .build();
        HttpEntity<UpdateStatusOrderRequest> requestEntity = new HttpEntity<>(request);
        restTemplate.exchange(
                fullUrl,
                HttpMethod.POST,
                requestEntity,
                Void.class
        );

        return "redirect:/admin/order/home";
    }

}
