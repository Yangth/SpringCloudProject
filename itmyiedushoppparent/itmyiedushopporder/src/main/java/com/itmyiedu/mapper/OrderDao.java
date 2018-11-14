package com.itmyiedu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderDao {

	@Update("update order_info set isPay=#{isPay} ,payId=#{aliPayId},updated=CURRENT_TIMESTAMP where orderNumber=#{orderNumber};")
	public int updateOrder(@Param("isPay") Long isPay, @Param("aliPayId") String aliPayId, @Param("orderNumber") String orderNumber);

	@Insert("insert into order_info(orderNumber,userId)value(#{orderNumber},#{userId})")
	public int insertOrder(@Param("orderNumber") String orderNumber,@Param("userId") String userId);

}
