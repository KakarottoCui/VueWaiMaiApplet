package com.kbz1121.CarStore.dao;

import com.kbz1121.CarStore.entity.Pic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Pic)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-12 08:42:36
 */
@Mapper
public interface PicDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Pic queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param pic 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Pic> queryAllByLimit(@Param("pic") Pic pic, @Param("pageable") Pageable pageable);
    
    /**
     * 查询指定行数据
     *
     * @param pic 查询条件
     * @return 对象列表
     */
    List<Pic> queryAll(Pic pic);

    /**
     * 统计总行数
     *
     * @param pic 查询条件
     * @return 总行数
     */
    long count(Pic pic);

    /**
     * 新增数据
     *
     * @param pic 实例对象
     * @return 影响行数
     */
    int insert(Pic pic);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Pic> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Pic> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Pic> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Pic> entities);

    /**
     * 修改数据
     *
     * @param pic 实例对象
     * @return 影响行数
     */
    int update(Pic pic);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

