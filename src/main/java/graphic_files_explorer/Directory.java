package graphic_files_explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Directory {
    private File directory;
    private List<ImageFile> imageFiles;

    public Directory(File directory) {
        this.directory = directory;
    }

    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }

    public List<ImageFile> getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(List<ImageFile> imageFiles) {
        this.imageFiles = imageFiles;
    }

    public List<File> getSubdirectories() {
        List<File> subdirectories = new ArrayList<>();
        Arrays.asList(Objects.requireNonNull(directory.listFiles())).forEach(file -> {
            if (file.isDirectory())
                subdirectories.add(file);
            else
                addImageFile(file);
        });
        return subdirectories;
    }

    public void loadOrReloadImageFiles() {
        imageFiles.clear();
        Arrays.stream(Objects.requireNonNull(directory.listFiles())).forEach(this::addImageFile);
    }

    private void addImageFile(File file) {
        if (file.isFile() && (file.getPath().endsWith(".png") || file.getPath().endsWith(".jpg"))) {
            if (imageFiles == null)
                imageFiles = new ArrayList<>();
            imageFiles.add(new ImageFile(file));
        }
    }

    @Override
    public String toString() {
        return directory.getName();
    }
}
