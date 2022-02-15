package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.payload.AdminInfoResponse;
import com.chompfooddeliveryapp.payload.AppStatisticsResponse;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<AdminInfoResponse> getAdminInfo(String email);
    AppStatisticsResponse getStatistics(Integer pageNo, Integer pageSize);

    Long allUsersByGender(String gender);

    Long ordersByStatus(String status);
}
