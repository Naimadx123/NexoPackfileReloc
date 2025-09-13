package zone.vao.nexoPackfileReloc

import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class NexoPackfileReloc : JavaPlugin() {

    override fun onEnable() {
        saveDefaultConfig()
        server.pluginManager.registerEvents(PackRelocListener(this), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun copyPackZip(copyToPath: String) {
        val serverRoot = File(server.getPluginManager().getPlugin("Nexo")!!.dataFolder, "pack")

        val source = File(serverRoot, "pack.zip")

        val normalizedDestPath = copyToPath.removePrefix("/")
        val dest = File(serverRoot, normalizedDestPath)

        if (!source.exists()) {
            throw IOException("Source file not found: ${source.absolutePath}")
        }
        if (!source.isFile) {
            throw IOException("Source is not a file: ${source.absolutePath}")
        }

        dest.parentFile?.let { parent ->
            if (!parent.exists() && !parent.mkdirs()) {
                throw IOException("Unable to create destination directory: ${parent.absolutePath}")
            }
        }

        Files.copy(
            source.toPath(),
            dest.toPath(),
            StandardCopyOption.REPLACE_EXISTING
        )
    }
}
