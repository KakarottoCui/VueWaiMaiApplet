package com.kbz1121.CarStore.dao;

import com.kbz1121.CarStore.entity.GoodsType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (GoodsType)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-11 15:00:07
 */
@Mapper
public interface GoodsTypeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    GoodsType queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param goodsType 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<GoodsType> queryAllByLimit(@Param("goodsType") GoodsType goodsType, @Param("pageable") Pageable pageable);
    
    /**
     * 查询指定行数据
     *
     * @param goodsType 查询条件
     * @return 对象列表
     */
    List<GoodsType> queryAll(GoodsType goodsType);

    /**
     * 统计总行数
     *
     * @param goodsType 查询条件
     * @return 总行数
     */
    long count(GoodsType goodsType);

    /**
     * 新增数据
     *
     * @param goodsType 实例对象
     * @return 影响行数
     */
    int insert(GoodsType goodsType);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<GoodsType> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<GoodsType> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<GoodsType> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<GoodsType> entities);

    /**
     * 修改数据
     *
     * @param goodsType 实例对象
     * @return 影响行数
     */
    int update(GoodsType goodsType);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

