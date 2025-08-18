package bartender.bartenderback.global.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FileUtils {

    public static String saveFile(String uploadDir, MultipartFile file) throws IOException {
        Path path = Paths.get(uploadDir);
        if(!Files.exists(path)) {
            Files.createDirectories(path);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String newFileName = timestamp + "_" + UUID.randomUUID().toString().substring(0, 8) + extension;

        Path filePath = path.resolve(newFileName);
        file.transferTo(filePath);

        return filePath.toString();
    }
}
