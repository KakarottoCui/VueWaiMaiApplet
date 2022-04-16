package com.kbz1121.CarStore.dao;

import com.kbz1121.CarStore.entity.Orders;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Orders)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-13 06:54:47
 */
@Mapper
public interface OrdersDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Orders queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param orders 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Orders> queryAllByLimit(@Param("orders") Orders orders, @Param("pageable") Pageable pageable);
    
    /**
     * 查询指定行数据
     *
     * @param orders 查询条件
     * @return 对象列表
     */
    List<Orders> queryAll(Orders orders);

    /**
     * 统计总行数
     *
     * @param orders 查询条件
     * @return 总行数
     */
    long count(Orders orders);

    /**
     * 新增数据
     *
     * @param orders 实例对象
     * @return 影响行数
     */
    int insert(Orders orders);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Orders> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Orders> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Orders> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Orders> entities);

    /**
     * 修改数据
     *
     * @param orders 实例对象
     * @return 影响行数
     */
    int update(Orders orders);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

