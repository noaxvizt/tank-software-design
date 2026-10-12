package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class FileLevelProviderTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void shouldReadLevelFromFile() throws IOException {
        File file = createFile("T_", "_X");
        FileLevelProvider provider = new FileLevelProvider(file.toPath());

        Level level = provider.createLevel(2, 2);

        assertEquals(new GridPoint2(1, 0), level.getTankCoordinates());
        assertEquals(
                Collections.singletonList(new GridPoint2(0, 1)),
                level.getTreeCoordinates()
        );
    }

    @Test
    public void shouldRejectWrongHeight() throws IOException {
        File file = createFile("_X");
        FileLevelProvider provider = new FileLevelProvider(file.toPath());

        assertThrows(IllegalArgumentException.class, () -> provider.createLevel(2, 2));
    }

    @Test
    public void shouldRejectWrongWidth() throws IOException {
        File file = createFile("T__", "_X_");
        FileLevelProvider provider = new FileLevelProvider(file.toPath());

        assertThrows(IllegalArgumentException.class, () -> provider.createLevel(2, 2));
    }

    @Test
    public void shouldRejectUnknownSymbol() throws IOException {
        File file = createFile("A_", "_X");
        FileLevelProvider provider = new FileLevelProvider(file.toPath());

        assertThrows(IllegalArgumentException.class, () -> provider.createLevel(2, 2));
    }

    @Test
    public void shouldRequireTankPosition() throws IOException {
        File file = createFile("T_", "__");
        FileLevelProvider provider = new FileLevelProvider(file.toPath());

        assertThrows(IllegalArgumentException.class, () -> provider.createLevel(2, 2));
    }

    @Test
    public void shouldRejectSeveralTankPositions() throws IOException {
        File file = createFile("X_", "_X");
        FileLevelProvider provider = new FileLevelProvider(file.toPath());

        assertThrows(IllegalArgumentException.class, () -> provider.createLevel(2, 2));
    }

    private File createFile(String... lines) throws IOException {
        File file = temporaryFolder.newFile();
        Files.write(file.toPath(), Arrays.asList(lines), StandardCharsets.UTF_8);
        return file;
    }
}
