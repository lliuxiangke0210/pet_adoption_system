package com.dog.service.impl;

import com.dog.common.BusinessException;
import com.dog.common.ResultCode;
import com.dog.entity.Banner;
import com.dog.mapper.BannerMapper;
import com.dog.service.BannerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播图服务实现
 */
@Service
public class BannerServiceImpl implements BannerService {

    private final BannerMapper bannerMapper;

    public BannerServiceImpl(BannerMapper bannerMapper) {
        this.bannerMapper = bannerMapper;
    }

    @Override
    public List<Banner> listActive() {
        return bannerMapper.selectActive();
    }

    @Override
    public List<Banner> listAll() {
        return bannerMapper.selectAll();
    }

    @Override
    public Banner getById(Long id) {
        Banner banner = bannerMapper.selectById(id);
        if (banner == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "轮播图不存在");
        }
        return banner;
    }

    @Override
    public void save(Banner banner) {
        if (banner.getStatus() == null) {
            banner.setStatus(1);
        }
        if (banner.getSortOrder() == null) {
            banner.setSortOrder(0);
        }
        bannerMapper.insert(banner);
    }

    @Override
    public void update(Banner banner) {
        getById(banner.getId());
        bannerMapper.update(banner);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        bannerMapper.deleteById(id);
    }
}
