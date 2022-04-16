package com.kbz1121.CarStore.dao;

import com.kbz1121.CarStore.entity.Conmment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Conmment)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-13 10:20:23
 */
@Mapper
public interface ConmmentDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Conmment queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param conmment 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Conmment> queryAllByLimit(@Param("conmment") Conmment conmment, @Param("pageable") Pageable pageable);
    
    /**
     * 查询指定行数据
     *
     * @param conmment 查询条件
     * @return 对象列表
     */
    List<Conmment> queryAll(Conmment conmment);

    /**
     * 统计总行数
     *
     * @param conmment 查询条件
     * @return 总行数
     */
    long count(Conmment conmment);

    /**
     * 新增数据
     *
     * @param conmment 实例对象
     * @return 影响行数
     */
    int insert(Conmment conmment);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Conmment> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Conmment> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Conmment> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Conmment> entities);

    /**
     * 修改数据
     *
     * @param conmment 实例对象
     * @return 影响行数
     */
    int update(Conmment conmment);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

