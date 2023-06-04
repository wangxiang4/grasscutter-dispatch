package com.tianli.dispatch.util

import com.tianli.dispatch.GrasscutterDispatchApplication
import com.tianli.dispatch.logger
import java.io.IOException
import java.net.URISyntaxException
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

class FileUtil {

    companion object {

        @Throws(URISyntaxException::class)
        fun getPathsFromResource(folder: String?): List<Path>? {
            return try {
                // file walks JAR
                Files.walk(Path.of(GrasscutterDispatchApplication::class.java.getResource(folder).toURI()))
                    .filter { path: Path? ->
                        Files.isRegularFile(
                            path
                        )
                    }
                    .collect(Collectors.toList())
            } catch (e: IOException) {
                // Eclipse puts resources in its bin folder
                try {
                    Files.walk(Path.of(System.getProperty("user.dir"), folder))
                        .filter { path: Path? ->
                            Files.isRegularFile(
                                path
                            )
                        }
                        .collect(Collectors.toList())
                } catch (ignored: IOException) {
                    null
                }
            }
        }

        fun read(path: Path): ByteArray? {
            try {
                return Files.readAllBytes(path)
            } catch (e: IOException) {
                logger.warn("Failed to read file: $path")
            }
            return ByteArray(0)
        }

    }

}
