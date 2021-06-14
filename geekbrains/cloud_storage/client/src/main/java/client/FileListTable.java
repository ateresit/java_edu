package client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FileListTable {
    public enum FileType {
        FILE("F"), DIRECTORY("D");
        private String name;

        FileType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

        private String fileName;
        private long fileSize;
        private FileType fileType;
        private LocalDateTime fileLastModified;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public long getFileSize() {
            return fileSize;
        }

        public void setFileSize(long fileSize) {
            this.fileSize = fileSize;
        }

        public FileType getFileType() {
            return fileType;
        }

        public void setFileType(FileType fileType) {
            this.fileType = fileType;
        }

        public LocalDateTime getFileLastModified() {
            return fileLastModified;
        }

        public void setFileLastModified(LocalDateTime fileLastModified) {
            this.fileLastModified = fileLastModified;
        }

        public FileListTable(Path path){
            try {
                this.fileName = path.getFileName().toString();
                this.fileSize = Files.size(path);
                this.fileType = Files.isDirectory(path) ? FileType.DIRECTORY : FileType.FILE;

                if (this.fileType == FileType.DIRECTORY){
                    this.fileSize = -1L;
                }
                this.fileLastModified = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneOffset.ofHours(3));

            } catch (IOException e) {
                throw new RuntimeException("Unable to create file info from path.");
            }

        }
}
