package com.miya.service.impl;

import com.miya.entity.Emp;
import com.miya.mapper.EmpMapper;
import com.miya.service.IEmpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工信息 服务实现类
 * </p>
 *
 * @author Caixiaowei
 * @since 2021-02-23
 */
@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements IEmpService {

}
