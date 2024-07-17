package com.id3.controller;

import com.id3.model.LeaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/ui/employee")
public class EmployeeController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{userId}")
    public String getEmployeePage(@PathVariable("userId") int userId, Model model) {
        String url = "http://leave-request-service/leave-request/{userId}";
        LeaveRequest[] leaveRequests = restTemplate.getForObject(url, LeaveRequest[].class, userId);
        List<LeaveRequest> leaveRequestList = Arrays.asList(leaveRequests);

        model.addAttribute("leaveRequests", leaveRequestList);
        return "employee-page";
    }
}
