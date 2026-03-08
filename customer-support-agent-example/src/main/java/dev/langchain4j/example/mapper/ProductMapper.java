package dev.langchain4j.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import dev.langchain4j.example.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
