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

    public List<ImageFile> getImageFiles() {
        return imageFiles;
    }

    public List<File> getSubdirectories() {
        List<File> subdirectories = new ArrayList<>();
        if (directory.listFiles() != null) {
            Arrays.asList(Objects.requireNonNull(directory.listFiles())).forEach(file -> {
                if (file.isDirectory())
                    subdirectories.add(file);
                else
                    addImageFile(file);
            });
        }
        return subdirectories;
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
