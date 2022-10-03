package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {

    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    private StudentRepository studentRepository;
    private AvatarRepository avatarRepository;

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.debug("The {} method was called.", "uploadAvatar");
        Student student = studentRepository.getById(studentId);
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(generateImagePriviev(filePath));
        avatarRepository.save(avatar);
    }

    private byte[] generateImagePriviev(Path filePath) throws IOException{
        logger.debug("The {} method was called.", "generateImagePriviev");
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100,height, image.getType());
            Graphics2D grafics = preview.createGraphics();
            grafics.drawImage(image,0,0,100, height, null);
            grafics.dispose();

            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar findAvatar(Long studentId){
        logger.debug("The {} method was called.", "findAvatar");
        if(avatarRepository.findByStudentId(studentId) != null){
            return avatarRepository.findByStudentId(studentId);
        } else{
            return new Avatar();
        }
    }

    public void deleteAvatar(Long studentId) {
        logger.debug("The {} method was called.", "deleteAvatar");
        Avatar newAvatar = avatarRepository.findByStudentId(studentId);
        if(newAvatar != null){
            avatarRepository.deleteById(newAvatar.getId());
        }
    }

    public ResponseEntity<Collection<Avatar>> getAll(Integer pageNamber, Integer pageSize){
        logger.debug("The {} method was called.", "getAllAvatars");
        PageRequest pageRequest = PageRequest.of(pageNamber - 1, pageSize);
        Collection<Avatar> avatarCollection = avatarRepository.findAll(pageRequest).getContent();
        if(avatarCollection.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(avatarCollection);
    }
}
