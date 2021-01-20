package com.yu.mapper;

import com.yu.pojo.book;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class userMapperImpl implements userMapper {
    SqlSessionTemplate sqlSession;
    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<book> select() {
        return sqlSession.getMapper(userMapper.class).select();
    }
}
