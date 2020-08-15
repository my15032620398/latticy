package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.enumeration.CategoryRootOrNotEnum;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.CategoryDTO;
import io.github.talelin.latticy.model.CategoryDO;
import io.github.talelin.latticy.service.CategoryService;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

/**
* @author generator@TaleLin
* @since 2020-05-27
*/
@RestController
@RequestMapping("/v1/category")
@Validated
@PermissionModule("分类")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    @PermissionMeta(value = "创建分类")
    @GroupRequired
    public CreatedVO create(@Validated @RequestBody CategoryDTO dto) {
        CategoryDO categoryDO = new CategoryDO();
        BeanUtils.copyProperties(dto, categoryDO);
        categoryService.save(categoryDO);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta(value = "更新分类")
    public UpdatedVO update(
            @RequestBody @Validated CategoryDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        categoryService.updateCategory(dto, id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta(value = "删除分类")
    @GroupRequired
    public DeletedVO delete(
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        categoryService.deleteCategory(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
//    @LoginRequired
    public CategoryDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/page")
//    @LoginRequired
    public PageResponseVO<CategoryDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page,
            @Min(value = 0) @Max(value = 1) Integer root
    ) {
        IPage<CategoryDO> paging = categoryService.getCategoriesByPage(count, page-1, root);
        return PageUtil.build(paging);
    }

    @GetMapping("/sub-page")
//    @LoginRequired
    public PageResponseVO<CategoryDO> subPage(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page,
            @RequestParam(name = "id") @Positive(message = "{id}") Integer id
    ) {
        IPage<CategoryDO> paging = categoryService.getSubCategoriesByPage(count, page-1, id);
        return PageUtil.build(paging);
    }
    
    @GetMapping("/list")
    //@LoginRequired
    public List<CategoryDO> getList() {
        val notRoot = CategoryRootOrNotEnum.NOT_ROOT;
        return this.categoryService.lambdaQuery().eq(CategoryDO::getIsRoot, notRoot).list();
    }

}
