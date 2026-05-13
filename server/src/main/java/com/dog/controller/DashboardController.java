package com.dog.controller;

import com.dog.common.Result;
import com.dog.dto.DashboardStats;
import com.dog.mapper.AdoptionMapper;
import com.dog.mapper.PetMapper;
import com.dog.mapper.UserMapper;
import com.dog.service.AdoptionService;
import com.dog.service.PetService;
import com.dog.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 仪表盘控制器 - 提供后台首页统计数据
 */
@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    private final UserService userService;
    private final PetService petService;
    private final AdoptionService adoptionService;
    private final UserMapper userMapper;
    private final PetMapper petMapper;
    private final AdoptionMapper adoptionMapper;

    public DashboardController(UserService userService, PetService petService,
                               AdoptionService adoptionService, UserMapper userMapper,
                               PetMapper petMapper, AdoptionMapper adoptionMapper) {
        this.userService = userService;
        this.petService = petService;
        this.adoptionService = adoptionService;
        this.userMapper = userMapper;
        this.petMapper = petMapper;
        this.adoptionMapper = adoptionMapper;
    }

    /**
     * 获取仪表盘统计数据
     */
    @GetMapping
    public Result<DashboardStats> getStats() {
        DashboardStats stats = new DashboardStats();

        stats.setTotalUsers(userService.countTotal());
        stats.setTotalPets(petService.countTotal());
        stats.setPendingAdoptions(adoptionService.countPending());
        stats.setTotalAdoptions(adoptionService.countTotal());

        // 当前年份的月度数据
        int year = java.time.LocalDate.now().getYear();
        List<Map<String, Object>> userMonthly = userMapper.countByMonth(year);
        List<Map<String, Object>> adoptionMonthly = adoptionMapper.countByMonth(year);
        List<Map<String, Object>> petCategory = petMapper.countByCategory();

        stats.setUserRegistrationsByMonth(userMonthly);
        stats.setAdoptionsByMonth(adoptionMonthly);
        stats.setPetsByCategory(petCategory);

        return Result.success(stats);
    }
}
