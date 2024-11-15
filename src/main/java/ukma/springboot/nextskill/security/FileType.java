package ukma.springboot.nextskill.security;

import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FileType {

    IMAGE("jpg", "jpeg", "png", "gif", "bmp"),
    DOCUMENT("pdf", "doc", "docx", "txt", "rtf", "xls", "xlsx"),
    AUDIO("mp3", "wav", "flac", "aac"),
    VIDEO("mp4", "avi", "mkv", "mov", "webm"),
    ARCHIVE("zip", "tar", "gz", "rar", "7z"),
    OTHER();

    private final List<String> extensions;
    private static final Map<String, MediaType> MIME_TYPES = new HashMap<>();

    static {
        MIME_TYPES.put("jpg", MediaType.IMAGE_JPEG);
        MIME_TYPES.put("jpeg", MediaType.IMAGE_JPEG);
        MIME_TYPES.put("png", MediaType.IMAGE_PNG);
        MIME_TYPES.put("gif", MediaType.IMAGE_GIF);
        MIME_TYPES.put("bmp", MediaType.valueOf("image/bmp"));

        MIME_TYPES.put("pdf", MediaType.APPLICATION_PDF);
        MIME_TYPES.put("doc", MediaType.valueOf("application/msword"));
        MIME_TYPES.put("docx", MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        MIME_TYPES.put("txt", MediaType.TEXT_PLAIN);
        MIME_TYPES.put("rtf", MediaType.valueOf("application/rtf"));
        MIME_TYPES.put("xls", MediaType.valueOf("application/vnd.ms-excel"));
        MIME_TYPES.put("xlsx", MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

        MIME_TYPES.put("mp3", MediaType.valueOf("audio/mpeg"));
        MIME_TYPES.put("wav", MediaType.valueOf("audio/wav"));
        MIME_TYPES.put("flac", MediaType.valueOf("audio/flac"));
        MIME_TYPES.put("aac", MediaType.valueOf("audio/aac"));

        MIME_TYPES.put("mp4", MediaType.valueOf("video/mp4"));
        MIME_TYPES.put("avi", MediaType.valueOf("video/x-msvideo"));
        MIME_TYPES.put("mkv", MediaType.valueOf("video/x-matroska"));
        MIME_TYPES.put("mov", MediaType.valueOf("video/quicktime"));
        MIME_TYPES.put("webm", MediaType.valueOf("video/webm"));

        MIME_TYPES.put("zip", MediaType.valueOf("application/zip"));
        MIME_TYPES.put("tar", MediaType.valueOf("application/x-tar"));
        MIME_TYPES.put("gz", MediaType.valueOf("application/gzip"));
        MIME_TYPES.put("rar", MediaType.valueOf("application/vnd.rar"));
        MIME_TYPES.put("7z", MediaType.valueOf("application/x-7z-compressed"));
    }

    FileType(String... extensions) {
        this.extensions = Arrays.asList(extensions);
    }

    public List<String> getExtensions() {
        return extensions;
    }

    public static FileType fromExtension(String extension) {
        for (FileType type : FileType.values()) {
            if (type.getExtensions().contains(extension.toLowerCase())) {
                return type;
            }
        }
        return OTHER;
    }

    public static MediaType getContentType(String extension) {
        return MIME_TYPES.getOrDefault(extension.toLowerCase(), MediaType.APPLICATION_OCTET_STREAM);
    }
}