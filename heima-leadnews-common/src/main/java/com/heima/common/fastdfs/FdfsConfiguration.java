package com.heima.common.fastdfs;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ：lzy
 * @ Date       ：Created in 17:01 2021/7/30
 * @ Description：fastDfs配置类
 */
@Configuration
@Import(FdfsClientConfig.class) // 导入FastDFS-Client组件
@PropertySource("fast_dfs.properties")
public class FdfsConfiguration {
}