package com.dog.service;

import com.dog.entity.Banner;
import java.util.List;

/**
 * 轮播图服务接口
 */
public interface BannerService {

    /** 查询启用的轮播图 */
    List<Banner> listActive();

    /** 查询所有轮播图 */
    List<Banner> listAll();

    /** 根据ID查询 */
    Banner getById(Long id);

    /** 添加轮播图 */
    void save(Banner banner);

    /** 更新轮播图 */
    void update(Banner banner);

    /** 删除轮播图 */
    void delete(Long id);
}
