package gdu.admin_service.controller;

import gdu.admin_service.dto.model.CategoryDto;
import gdu.admin_service.dto.model.RoleDto;
import gdu.admin_service.dto.model.UserDto;
import gdu.admin_service.dto.request.CreateUserRequest;
import gdu.admin_service.dto.response.ObjectResponse;
import gdu.admin_service.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
@Validated
public class UserController {

    private final RestTemplate restTemplate;
    private final String urlUser = "http://localhost:8082/api/v1/user";
    private final String urlRole = "http://localhost:8082/api/v1/role";

    @GetMapping("/user/home")
    public String getAllUsers(
            @RequestParam(defaultValue = "20") byte size,
            @RequestParam(defaultValue = "0") byte page,
            Model model
    ) {
        String fullUrl = urlUser + "?size=" + size + "&page=" + page;
        ResponseEntity<ObjectResponse<UserResponse>> response = restTemplate.exchange(
                fullUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ObjectResponse<UserResponse>>() {}
        );

        ObjectResponse<UserResponse> responseBody = response.getBody();

        if (responseBody != null) {
            List<UserResponse> users = responseBody.getContent();
            model.addAttribute("users", users);
        }
        model.addAttribute("activePage", "users");

        return "admin/admin-user";
    }

    @GetMapping("/user/new")
    public String newUser(
            Model model
    ) {
        model.addAttribute("request", new CreateUserRequest());
        ResponseEntity<List<RoleDto>> rolesResponse = restTemplate.exchange(
                urlRole,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RoleDto>>() {}
        );
        List<RoleDto> roles = rolesResponse.getBody();
        model.addAttribute("roles", roles);

        return "admin/admin-new-user";
    }

    @PostMapping("/user/create")
    public String createUser(
            @Valid @ModelAttribute("request") CreateUserRequest request,
            Model model
    ) {
        HttpEntity<CreateUserRequest> requestEntity = new HttpEntity<>(request);
        restTemplate.exchange(
                urlUser,
                HttpMethod.POST,
                requestEntity,
                UserDto.class
        );
        return "redirect:/admin/user/home";
    }
//
//    @PutMapping
//    public ResponseEntity<UserDto> updateUser(
//            @RequestBody UpdateUserRequest request
//    ) {
//        HttpEntity<UpdateUserRequest> requestEntity = new HttpEntity<>(request);
//        ResponseEntity<UserDto> response = restTemplate.exchange(
//                urlUser,
//                HttpMethod.PUT,
//                requestEntity,
//                UserDto.class
//        );
//        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
//    }
//
    @PostMapping("/user/delete/{id}")
    public String deleteUser(
            @PathVariable Short id
    ) {
        String fullUrl = urlUser + "?id=" + id;
        ResponseEntity<?> response = restTemplate.exchange(
                fullUrl,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        return "redirect:/admin/user/home";
    }
}

