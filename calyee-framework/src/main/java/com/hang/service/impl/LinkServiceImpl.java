package com.hang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hang.constants.SystemConstants;
import com.hang.entity.Link;
import com.hang.mapper.LinkMapper;
import com.hang.result.ResponseResult;
import com.hang.service.LinkService;
import com.hang.utils.BeanCopyUtils;
import com.hang.vo.LinkVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Link)表服务实现类
 *
 * @author makejava
 * @since 2023-08-05 20:33:01
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_SUCCESS);
        List<Link> list = list(queryWrapper);
        List<LinkVo> linkVo = BeanCopyUtils.copyBeanList(list, LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }
}

