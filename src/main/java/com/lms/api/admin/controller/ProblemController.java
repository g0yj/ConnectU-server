package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.company.ListCompanyResponse;
import com.lms.api.admin.controller.dto.company.ListCompanysRequest;
import com.lms.api.admin.service.CompanyService;
import com.lms.api.admin.service.dto.Company;
import com.lms.api.admin.service.dto.company.CompanyList;
import com.lms.api.admin.service.dto.company.SearchCompany;
import com.lms.api.common.controller.dto.PageResponse;
import com.lms.api.common.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/admin/v1/problem")
@RequiredArgsConstructor
public class ProblemController {


}
