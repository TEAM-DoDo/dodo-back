package kr.ac.hansung.dodobackend.service.Impl;

import jakarta.servlet.ServletContext;
import kr.ac.hansung.dodobackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {
    private final String imagePath = "/resources/images/";
    @Autowired
    ServletContext context;

    @Override
    public String putFile(String storePath,MultipartFile file,String name)
    {
        if (file == null) return null;
        if (file.isEmpty()) return null;
        //do 가 존재하는지 확인하는 무결성 코드 필요
        Path folderPath = CreatePath(imagePath+storePath);
        if (folderPath==null) return null;

        //향후 데이터 베이스와 저장된 이미지를 연동하는 코드가 필요함
        String storeName = file.getOriginalFilename();
//        if (name != null){
//            String[] names = storeName.split("\\.");
//            storeName = name + "." + names[1];
//        }
        Path path = Paths.get(folderPath+ "/" +storeName).toAbsolutePath();
        try{
            //System.out.println(path);
            file.transferTo(path.toFile());
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("[Ignored]" + path);
            return null;
        }
        System.out.println("[Saved]" + path);
        return storeName;
    }

    @Override
    //호출 전에 두 존재 여부를 확인했다고 가정한 뒤 작성되는 코드
    public boolean putFiles(String id,List<MultipartFile> files) throws IOException{
        if (files.size() == 0){
            return true;
        }
        //do 가 존재하는지 확인하는 무결성 코드 필요
        Path storagePath = CreatePath(imagePath);
        if (storagePath==null){
            throw new IOException("Path is not exist");
        }
        Path folderPath = CreatePath(imagePath+id);
        if (folderPath==null){
            throw new IOException("Path is not exist");
        }
        for(var file : files){
            //데이터 저장 경로 알고리즘 제작 필요
            if (file == null){
                System.out.println("파일 null");
                continue;
            }
            if (file.isEmpty()){
                System.out.println("파일 empty");
                continue;
            }
            //향후 데이터 베이스와 저장된 이미지를 연동하는 코드가 필요함
            String orgFileName = file.getOriginalFilename();
            Path path = Paths.get(folderPath+ "/" +orgFileName).toAbsolutePath();
            try{
                //System.out.println(path);
                file.transferTo(path.toFile());
            } catch (IOException e){
                e.printStackTrace();
                System.out.println("[Ignored]" + path);
                continue;
            }
            System.out.println("[Saved]" + path);
        }
        return true;
    }

    @Override
    public File getFile(String id, String fileName)
    {

        var path = imagePath+ id;
        if (!checkPath(path)){
            return null;//저장공간이 생성되지 않음
        }
        Path folderPath = CreatePath(path);
        if (folderPath==null) return null;
        System.out.println(folderPath + "/" + fileName);
        var file = new File(folderPath+"/"+fileName);
        System.out.println("getFile에서 호출 : " + file.getAbsolutePath());
        if (!file.exists()){
            System.out.println("File not exist[path : " + file.getAbsolutePath() + "]");
            return null;
        }
        return file;
    }

    @Override
    public List<String> getImageIdList(String id)
    {
        var path = imagePath+ id;
        if (!checkPath(path)){
            return null;//저장공간이 생성되지 않음
        }
        Path folderPath = CreatePath(path);
        if (folderPath==null) return null;
        var files = (new File(folderPath.toUri())).listFiles();
        if (files == null) return null;
        List<String> result = new ArrayList<>();
        for (var file : files){
            result.add(file.getName());
        }
        return result;
    }

    @Override
    public boolean checkPath(String path)
    {
        Path source = Paths.get(context.getRealPath("/"));
        Path dirPath = Paths.get(source.toAbsolutePath()+path);
        //System.out.println(dirPath.toAbsolutePath());
        return Files.exists(dirPath);
    }

    @Override
    public Path CreatePath(String path){
        Path source = Paths.get(context.getRealPath("/"));
        Path dirPath = Paths.get(source.toAbsolutePath()+path);
        if (Files.exists(dirPath)){
            return dirPath;
        }
        try{
            Files.createDirectories(dirPath);
            System.out.println("Directory not found, create file : " + source.toAbsolutePath()+path);
            return dirPath;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getLength(String id)
    {
        String path = imagePath+id;
        //System.out.println(path);
        if (!checkPath(path)){
            return -1;
        }
        Path folderPath = CreatePath(path);
        if (folderPath==null){
            System.out.println("Failed to create path");
            return -1;//저장곤간이 생성되지 않음
        }
        var files = (new File(folderPath.toUri())).listFiles();
        if (files == null){
            System.out.println("No files found in path : " + folderPath);
            return -1;
        }
        return files.length;
    }
}
