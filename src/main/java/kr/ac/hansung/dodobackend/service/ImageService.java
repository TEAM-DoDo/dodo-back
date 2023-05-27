package kr.ac.hansung.dodobackend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ImageService {
    String putFile(String storePath, MultipartFile file, String name);
    boolean putFiles(String id, List<MultipartFile> files) throws IOException;
    File getFile(String id, String fileName);
    List<String> getImageIdList(String id);
    boolean checkPath(String path);
    Path CreatePath(String path);
    int getLength(String id);
}
