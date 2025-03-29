package gdu.admin_service.dto.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private int userId;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
}
