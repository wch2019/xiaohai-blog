package com.xiaohai.admin.confing.intercept;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 分页拦截器
 * @author wangchenghai
 *
 **/
public class PageableInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String currentPage = request.getParameter(Constants.PAGE_NUM);
        String pageSize = Optional.ofNullable(request.getParameter(Constants.PAGE_SIZE)).orElse(Constants.DEFAULT_SIZE);
        if (StringUtils.isNoneBlank(currentPage)) {
            PageUtils.setCurrentPage(new Page<>(Long.parseLong(currentPage), Long.parseLong(pageSize)));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        PageUtils.remove();
    }

}
