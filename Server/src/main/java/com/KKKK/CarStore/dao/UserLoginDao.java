package com.kbz1121.CarStore.dao;

import com.kbz1121.CarStore.entity.UserLogin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (UserLogin)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-11 15:00:08
 */
@Mapper
public interface UserLoginDao {

    /**
     * 通过ID查询单条数据
     *
     * @param token 主键
     * @return 实例对象
     */
    UserLogin queryById(String token);

    /**
     * 查询指定行数据
     *
     * @param userLogin 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<UserLogin> queryAllByLimit(@Param("userLogin") UserLogin userLogin, @Param("pageable") Pageable pageable);
    
    /**
     * 查询指定行数据
     *
     * @param userLogin 查询条件
     * @return 对象列表
     */
    List<UserLogin> queryAll(UserLogin userLogin);

    /**
     * 统计总行数
     *
     * @param userLogin 查询条件
     * @return 总行数
     */
    long count(UserLogin userLogin);

    /**
     * 新增数据
     *
     * @param userLogin 实例对象
     * @return 影响行数
     */
    int insert(UserLogin userLogin);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserLogin> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserLogin> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserLogin> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<UserLogin> entities);

    /**
     * 修改数据
     *
     * @param userLogin 实例对象
     * @return 影响行数
     */
    int update(UserLogin userLogin);

    /**
     * 通过主键删除数据
     *
     * @param token 主键
     * @return 影响行数
     */
    int deleteById(String token);

}

