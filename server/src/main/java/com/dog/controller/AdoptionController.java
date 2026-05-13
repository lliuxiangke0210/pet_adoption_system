package com.dog.controller;

import com.dog.common.Result;
import com.dog.entity.Adoption;
import com.dog.security.JwtTokenProvider;
import com.dog.service.AdoptionService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 领养管理控制器
 */
@RestController
public class AdoptionController {

    private final AdoptionService adoptionService;
    private final JwtTokenProvider jwtTokenProvider;

    public AdoptionController(AdoptionService adoptionService, JwtTokenProvider jwtTokenProvider) {
        this.adoptionService = adoptionService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * 提交领养申请（用户端）
     */
    @PostMapping("/api/adoption")
    public Result<Void> apply(@RequestBody Adoption adoption,
                              @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        adoption.setUserId(userId);
        adoptionService.apply(adoption);
        return Result.success("申请已提交", null);
    }

    /**
     * 查询我的领养记录（用户端）
     */
    @GetMapping("/api/adoption/my")
    public Result<List<Adoption>> myAdoptions(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        return Result.success(adoptionService.listByUserId(userId));
    }

    /**
     * 分页查询领养记录（管理端）
     */
    @GetMapping("/api/admin/adoption/list")
    public Result<Map<String, Object>> list(@RequestParam(defaultValue = "") String keyword,
                                            @RequestParam(required = false) String status,
                                            @RequestParam(defaultValue = "1") int pageNum,
                                            @RequestParam(defaultValue = "10") int pageSize) {
        List<Adoption> list = adoptionService.list(keyword, status, pageNum, pageSize);
        long total = adoptionService.count(keyword, status);
        Map<String, Object> result = new HashMap<>();
        result.put("records", list);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }

    /**
     * 审批领养（管理端）
     * @param id 领养记录ID
     * @param status 审批结果: approved / rejected
     */
    @PutMapping("/api/admin/adoption/{id}")
    public Result<Void> approve(@PathVariable Long id, @RequestParam String status) {
        adoptionService.approve(id, status);
        return Result.success("审批完成", null);
    }
}
