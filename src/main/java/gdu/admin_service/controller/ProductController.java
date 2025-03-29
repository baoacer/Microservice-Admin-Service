package gdu.admin_service.controller;

import gdu.admin_service.dto.model.CategoryDto;
import gdu.admin_service.dto.model.ImagesDto;
import gdu.admin_service.dto.model.ProductDto;
import gdu.admin_service.dto.request.product.CreateProductData;
import gdu.admin_service.dto.request.product.CreateProductRequest;
import gdu.admin_service.dto.request.product.UpdateProductRequest;
import gdu.admin_service.dto.response.ObjectResponse;
import gdu.admin_service.dto.response.UpdateProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class ProductController {
    private final RestTemplate restTemplate;
    private final String urlProduct = "http://localhost:8081/api/v1/product";
    private final String urlCategory = "http://localhost:8081/api/v1/category";

    @GetMapping("/product/new")
    public String newProduct(
            Model model
    ) {
        model.addAttribute("request", new CreateProductData());
        ResponseEntity<List<CategoryDto>> categoryResponse = restTemplate.exchange(
                urlCategory,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CategoryDto>>() {}
        );

        List<CategoryDto> categories = categoryResponse.getBody();
        model.addAttribute("categories", categories);
        return "admin/admin-new-product";
    }

    @GetMapping("/product/home")
    public String getAllProducts(
            @RequestParam(defaultValue = "20") byte size,
            @RequestParam(defaultValue = "0") byte page,
            Model model
    ) {
        String fullUrl = urlProduct + "?size=" + size + "&page=" + page;
        ResponseEntity<ObjectResponse<ProductDto>> response = restTemplate.exchange(
                fullUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ObjectResponse<ProductDto>>() {}
        );

        ObjectResponse<ProductDto> responseBody = response.getBody();

        if (responseBody != null) {
            List<ProductDto> products = responseBody.getContent();
            model.addAttribute("products", products);
        }
        model.addAttribute("activePage", "products");

        return "admin/admin-product";
    }

    @GetMapping("/product/edit/{id}")
    public String showEditForm(
            @PathVariable Short id,
            Model model
    ) {
        String fullUrl = urlProduct + "/" + id;

        ResponseEntity<ProductDto> response = restTemplate.exchange(
                fullUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ProductDto>() {}
        );

        ResponseEntity<List<CategoryDto>> categoryResponse = restTemplate.exchange(
                urlCategory,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CategoryDto>>() {}
        );

        List<CategoryDto> categories = categoryResponse.getBody();
        model.addAttribute("categories", categories);

        ProductDto product = response.getBody();
        model.addAttribute("product", product);

        return "admin/edit-product";
    }

    @PostMapping("/product/update")
    public String updateProduct(
            @ModelAttribute ProductDto productDto,
            @RequestParam(name = "newImage", required = false) String newImage
    ) {
        productDto.setImages(null);

        if (newImage != null && !newImage.isEmpty()) {
            List<ImagesDto> images = new ArrayList<>();
            images.add(new ImagesDto(newImage));
            productDto.setImages(images);
        }

        UpdateProductRequest request = UpdateProductRequest.builder()
                .productId(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .images(productDto.getImages())
                .price(productDto.getPrice())
                .quantity(productDto.getInventory().getStock())
                .category(productDto.getCategory())
                .build();

        HttpEntity<UpdateProductRequest> requestEntity = new HttpEntity<>(request);

        restTemplate.exchange(
                urlProduct,
                HttpMethod.PUT,
                requestEntity,
                UpdateProductResponse.class
        );

        return "redirect:/admin/product/home";
    }

    @PostMapping("/product/create")
    public String createProduct(
            @ModelAttribute CreateProductData data,
            Model model
    ) {
        CreateProductRequest request = CreateProductRequest.builder()
                .name(data.getName())
                .description(data.getDescription())
                .price(data.getPrice())
                .categoryId(data.getCategoryId())
                .quantity(data.getQuantity())
                .build();

        List<ImagesDto> images = new ArrayList<>();
        images.add(new ImagesDto(data.getImageSrc()));
        request.setImages(images);

        HttpEntity<CreateProductRequest> requestEntity = new HttpEntity<>(request);
        ResponseEntity<ProductDto> response = restTemplate.exchange(
                urlProduct,
                HttpMethod.POST,
                requestEntity,
                ProductDto.class
        );
        model.addAttribute("product", response);

        return "redirect:/admin/product/home";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(
            @PathVariable short id
    ) {
        String fullUrl = urlProduct + "?id=" + id;
        ResponseEntity<Void> response = restTemplate.exchange(
                fullUrl,
                HttpMethod.DELETE,
                null,
                Void.class,
                id
        );
        return "redirect:/admin/product/home";
    }
}
