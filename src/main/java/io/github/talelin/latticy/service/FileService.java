package io.github.talelin.latticy.service;

import io.github.talelin.latticy.bo.FileBO;
import io.github.talelin.latticy.model.FileDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author pedro@TaleLin
 */
public interface FileService extends IService<FileDO> {

    /**
     * 上传文件
     *
     * @param fileMap 文件map
     * @return 文件数据
     */
    List<FileBO> upload(MultiValueMap<String, MultipartFile> fileMap);

    /**
     * 通过md5检查文件是否存在
     *
     * @param md5 md5
     * @return true 表示已存在
     */
    boolean checkFileExistByMd5(String md5);

    /**
     * 删除已上传文件
     * @param name
     */
    void deleteImg(Integer name) throws IOException;

    /**
     * 根据名字删除文件
     * @param name
     */
    void deleteImgByName(String name);
}
