package ukma.springboot.nextskill.security;

import java.util.Arrays;
import java.util.List;

public enum FileType {

    IMAGE("jpg", "jpeg", "png", "gif", "bmp"),
    DOCUMENT("pdf", "doc", "docx", "txt", "rtf", "xls", "xlsx"),
    AUDIO("mp3", "wav", "flac", "aac"),
    VIDEO("mp4", "avi", "mkv", "mov", "webm"),
    ARCHIVE("zip", "tar", "gz", "rar", "7z"),
    OTHER();

    private final List<String> extensions;

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
}