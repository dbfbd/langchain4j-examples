package dev.langchain4j.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import dev.langchain4j.example.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
