package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.SkuDTO;
import io.github.talelin.latticy.model.SkuDO;
import io.github.talelin.latticy.model.SkuDetailDO;
import io.github.talelin.latticy.service.SkuService;
import io.github.talelin.latticy.service.SkuSpecService;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author generator@TaleLin
 * @since 2020-05-23
 */
@RestController
@RequestMapping("/v1/sku")
@Validated
@PermissionModule("SKU")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @Autowired
    private SkuSpecService skuSpecService;

    @PostMapping("")
    @PermissionMeta("创建SKU")
//    @GroupRequired
    public CreatedVO create(@RequestBody @Validated SkuDTO dto) {
        skuService.create(dto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新SKU")
//    @GroupRequired
    public UpdatedVO update(@RequestBody @Validated SkuDTO dto,
                            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        skuService.update(dto, id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除SKU")
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        skuService.delete(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public SkuDetailDO getDetail(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        return skuService.getBaseMapper().getDetail(id);
    }

    @GetMapping("/by/spu/{id}")
//    @LoginRequired
    public List<SkuDO> getBySpuId(@PathVariable(value = "id") @Positive Integer spuId) {
        return this.skuService.lambdaQuery().eq(SkuDO::getSpuId, spuId).list();
    }

    @GetMapping("/page")
//    @LoginRequired
    public PageResponseVO<SkuDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<SkuDO> pager = new Page<>(page-1, count);
        IPage<SkuDO> paging = skuService.getBaseMapper().selectPage(pager, null);
        return PageUtil.build(paging);
    }

    @GetMapping("/spec-value-id")
    @LoginRequired
    public Map<String, Integer> getSpecValueId(
            @RequestParam(name = "key_id", required = false)
            @Positive(message = "{id}") Integer keyId,
            @RequestParam(name = "sku_id", required = false)
            @Positive(message = "{id}") Integer skuId
    ) {
        // 在spu下选择 spec_key 后，在相关 sku 在spec_key下选择 spec_value
        Integer specValueId = skuSpecService.getBaseMapper().getSpecValueId(keyId, skuId);
        HashMap<String, Integer> result = new HashMap<>(1);
        result.put("value_id", specValueId);
        return result;
    }

}
