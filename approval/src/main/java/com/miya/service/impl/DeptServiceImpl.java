package com.miya.service.impl;

import com.miya.entity.Dept;
import com.miya.mapper.DeptMapper;
import com.miya.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门信息 服务实现类
 * </p>
 *
 * @author Caixiaowei
 * @since 2021-02-23
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

}
