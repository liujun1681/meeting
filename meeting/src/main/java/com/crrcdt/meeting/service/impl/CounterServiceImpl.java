package com.crrcdt.meeting.service.impl;

import com.crrcdt.meeting.entity.Counter;
import com.crrcdt.meeting.mapper.CounterMapper;
import com.crrcdt.meeting.service.CounterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujun
 * @since 2020-09-20
 */
@Service
public class CounterServiceImpl extends ServiceImpl<CounterMapper, Counter> implements CounterService {

}
