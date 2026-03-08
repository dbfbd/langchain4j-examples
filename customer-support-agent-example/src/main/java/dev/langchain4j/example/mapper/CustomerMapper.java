package dev.langchain4j.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import dev.langchain4j.example.entity.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
}
