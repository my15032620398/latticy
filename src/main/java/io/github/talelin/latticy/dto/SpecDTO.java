package io.github.talelin.latticy.dto;/**
 * @Description: java类作用描述
 * @Author: my
 * @CreateDate: 2020/9/8 - 8:34
 */

import io.github.talelin.latticy.model.SpecValueDO;
import lombok.Data;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: my
 * @CreateDate: 2020/9/8 8:34
 * @UpdateUser:
 * @UpdateDate: 2020/9/8 8:34
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@Data
public class SpecDTO {
    private String specKey;
    private List<SpecValueDO> specValues;
    private Integer selectedKey;

}
