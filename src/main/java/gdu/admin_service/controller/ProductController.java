package gdu.admin_service.controller;

import gdu.admin_service.dto.model.ProductDto;
import gdu.admin_service.dto.request.product.CreateProductRequest;
import gdu.admin_service.dto.request.product.UpdateProductRequest;
import gdu.admin_service.dto.response.PageResponse;
import gdu.admin_service.dto.response.UpdateProductResponse;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Controller
@RequestMapping("/admin/product")
@AllArgsConstructor
public class ProductController {
    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8081/api/v1/product";

    @GetMapping
    public ResponseEntity<PageResponse<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "20") byte size,
            @RequestParam(defaultValue = "0") byte page
    ) {
        String fullUrl = url + "?size=" + size + "&page=" + page;
        ResponseEntity<PageResponse<ProductDto>> response = restTemplate.exchange(
                fullUrl,
                HttpMethod.GET,
                null, // request headers, body...
                new ParameterizedTypeReference<PageResponse<ProductDto>>() {}
        );

        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody CreateProductRequest request,
            Model model
    ) {
        ProductDto response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                null,
                ProductDto.class,
                request
        ).getBody();
        model.addAttribute("product", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<UpdateProductResponse> updateProduct(
            @RequestBody UpdateProductRequest request
    ) {

        HttpEntity<UpdateProductRequest> requestEntity = new HttpEntity<>(request);

        ResponseEntity<UpdateProductResponse> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                UpdateProductResponse.class
        );

        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }

    @DeleteMapping
    public ResponseEntity<Void> getAllProducts(
            @RequestParam short id
    ) {
        String fullUrl = url + "?id=" + id;
        ResponseEntity<Void> response = restTemplate.exchange(
                fullUrl,
                HttpMethod.DELETE,
                null,
                Void.class,
                id
        );

        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }
}
