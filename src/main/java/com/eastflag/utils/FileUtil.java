package com.eastflag.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by eastflag on 2016-05-10.
 */
public final class FileUtil {

    /**
     * 단건 파일 업로드
     * @param file
     * @param path
     * @throws IllegalStateException
     * @throws IOException
     */
    public static String fileUpload(MultipartFile file, String path) throws IllegalStateException, IOException{
        String uploadFileName = null;
        File dir = new File(path);

        //디렉토리 생성
        if(!dir.exists()){
            dir.mkdirs();
        }
        if(!file.isEmpty()){
            String originalFileName = file.getOriginalFilename();
            String extention = originalFileName.substring(originalFileName.lastIndexOf(".")+1).toLowerCase();
            uploadFileName = doMakeUniqueFileName(path,extention);

            File saveFile = new File(path, uploadFileName);
            file.transferTo(saveFile);
        }
        return uploadFileName;
    }

    /**
     * 멀티 파일 업로드 : 유니크한 랜덤 파일명으로 저장
     * @param files
     * @param path
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
/*    public static List<AttachVO> fileUpload(List<MultipartFile> files, String path) throws IllegalStateException, IOException {
        List<AttachVO> list = new ArrayList<>();
        File dir = new File(path);

        //디렉토리 생성
        if(!dir.exists()){
            dir.mkdirs();
        }

        //첨부파일 랜덤 화일명으로 저장후 저장목록을 컬렉션으로 리턴
        for(int i=0;i<files.size();i++){
            if(!files.get(i).isEmpty()){
                String originalFileName = files.get(i).getOriginalFilename();
                String extention = originalFileName.substring(originalFileName.lastIndexOf(".")+1).toLowerCase();
                String uploadFileName = doMakeUniqueFileName(path,extention);

                File saveFile = new File(path, uploadFileName);
                files.get(i).transferTo(saveFile);

                AttachVO attach = new AttachVO();
                attach.setOrg_name(originalFileName);
                attach.setUpd_name(uploadFileName);

                list.add(attach);
            }
        }
        return list;
    }*/

    /**
     * 멀티 파일 업로드[원본 이름으로 저장]
     * @param files
     * @param path
     * @throws IllegalStateException
     * @throws IOException
     */
/*    public static void fileUploadOriginalName(List<MultipartFile> files, String path) throws IllegalStateException, IOException{
        File dir = new File(path);

        //디렉토리 생성
        if(!dir.exists()){
            dir.mkdirs();
        }

        //첨부파일 등록
        for(int i=0;i<files.size();i++){
            if(!files.get(i).isEmpty()){
                String originalFileName = files.get(i).getOriginalFilename();
                File saveFile = new File(path, originalFileName);
                files.get(i).transferTo(saveFile);
            }
        }
    }*/

    public static String doMakeUniqueFileName(String path, String extension) {
        String uniqueFileName = null;
        boolean flag = true;
        while (flag) {
            uniqueFileName = getUniqueFileName();
            flag = doCheckFileExists(path,uniqueFileName+"."+extension);
        }
        return uniqueFileName+"."+extension;
    }

    private static String getUniqueFileName() {
        return  UUID.randomUUID().toString();
    }

    private static boolean doCheckFileExists(String path, String filename) {
        return new File(path,filename).exists();
    }
}
