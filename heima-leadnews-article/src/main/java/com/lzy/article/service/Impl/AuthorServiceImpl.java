package com.lzy.article.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.heima.model.article.pojos.ApAuthor;
import com.lzy.article.mapper.AuthorMapper;
import com.lzy.article.service.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, ApAuthor> implements AuthorService {
}